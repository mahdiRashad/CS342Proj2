import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import weather.Period;
import weather.WeatherAPI;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;


public class JavaFX extends Application {
	private Stage primaryStage;
	private Scene mainScene, otherLocation, chicagoScene;
	private StackPane mainPane1;
	private StackPane mainPane2;
	private ScrollPane scrollPane1;
	private ScrollPane scrollPane2;

	Text txt1, temperature, weather;
	Text txt2, txt3, temperature2, temperature3;
	Text weather2, weather3;

	public static void main(String[] args) {

		launch(args);
	}

	// Mahdi's Functions, DO NOT TOUCH //
	public HBox mainSceneBottomButtons(){
		Button returnButton1 = new Button("Home");
		returnButton1.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		returnButton1.setFont(new Font("Bison", 28));
		returnButton1.setMinWidth(150);
		returnButton1.setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(null);
			alert.setContentText("Already on the home screen.");
			alert.showAndWait();
		});

		Button exitButton1 = new Button("Exit");
		exitButton1.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white;");
		exitButton1.setFont(new Font("Bison", 28));
		exitButton1.setMinWidth(150);
		exitButton1.setOnAction(e -> System.exit(0));

		HBox bottomButtons1 = new HBox(20, returnButton1, exitButton1);
		bottomButtons1.setAlignment(Pos.BOTTOM_CENTER);
		bottomButtons1.setPadding(new Insets(110, 0, 20, 0));
		return bottomButtons1;
	}


	///  CHICAGO FUNCTION //
	public VBox ChicagoWeatherScene(Stage primaryStage, Scene mainScene){
		primaryStage.setTitle("Weather App");
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);


		///////////// Mohammed Kamil Code: Daily Weather, DO NOT TOUCH ////////////////


		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		// The function call for current day information //
		VBox currentDay = startDisplay(forecast);


		Button firstDay = firstBox(forecast);
		Button secondDay = secondBox(forecast);
		Button thirdDay = thirdBox(forecast);
		VBox collapseInfo = collapseButton(forecast);


		HBox unite = new HBox(45, firstDay ,secondDay ,thirdDay);
		unite.setAlignment(Pos.BOTTOM_CENTER);


		Button returnButton2 = new Button("Return");
		Button exitButton2 = new Button("Exit");
		HBox bottomButtons2 = new HBox(20, returnButton2, exitButton2);

		returnButton2.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		returnButton2.setFont(new Font("Bison", 28));
		returnButton2.setMinWidth(150);
		returnButton2.setOnAction(e -> primaryStage.setScene(mainScene));

		exitButton2.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white;");
		exitButton2.setFont(new Font("Bison", 28));
		exitButton2.setMinWidth(150);
		exitButton2.setOnAction(e -> System.exit(0));

		bottomButtons2.setAlignment(Pos.BOTTOM_CENTER);
		bottomButtons2.setPadding(new Insets(20, 0, 20, 0));
		bottomButtons2.setManaged(false);
		bottomButtons2.setLayoutX(270);
		bottomButtons2.setLayoutY(870);

		VBox vbox4 = new VBox(20,currentDay, unite, collapseInfo, bottomButtons2);

		return vbox4;
	}


	public VBox otherLocation(Stage primaryStage, Scene mainScene){
		TextField latitudeField;
		TextField longitudeField;

		VBox currentDay = new VBox();
		Button firstDay = new Button();
		Button secondDay = new Button();
		Button thirdDay = new Button();
		VBox collapseInfo = new VBox();
		HBox unite = new HBox();

		currentDay.setVisible(false);
		collapseInfo.setVisible(false);
		unite.setVisible(false);


		Label latitudeLabel = new Label("Enter Latitude:");
		latitudeField = new TextField();

		Label longitudeLabel = new Label("Enter Longitude:");
		longitudeField = new TextField();

		Button returnButton3 = new Button("Home");
		returnButton3.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		returnButton3.setFont(new Font("Bison", 28));
		returnButton3.setMinWidth(150);
		returnButton3.setOnAction(e -> {primaryStage.setScene(mainScene);});

		Button exitButton3 = new Button("Exit");
		exitButton3.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white;");
		exitButton3.setFont(new Font("Bison", 28));
		exitButton3.setMinWidth(150);
		exitButton3.setOnAction(e -> System.exit(0));

		HBox bottomButtons3 = new HBox(20, returnButton3, exitButton3);
		bottomButtons3.setAlignment(Pos.BOTTOM_CENTER);
		bottomButtons3.setManaged(false);
		bottomButtons3.setLayoutX(270);
		bottomButtons3.setLayoutY(870);


		Button fetchButton = new Button("Get Forecast");
		fetchButton.setOnAction(e -> {

			String latitude = latitudeField.getText();
			String longitude = longitudeField.getText();

			GridInformation gridInfo = MyWeatherAPI.getGridInfo(latitude, longitude);
			if (gridInfo == null) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Failed to retrieve grid information.");
				alert.setHeaderText(null);
				alert.setContentText("""
						Try again with different latitude and longitude, wrong information was given.
						For Example:
						New York City, Central Park (KNYC) 40.78° -73.97°
						LOS ANGELES DOWNTOWN (FHMC1) 34.06° -118.33°
						""");
				alert.showAndWait();
				return;
			}
			ArrayList<Period> forecastLocation = WeatherAPI.getForecast(gridInfo.gridId, gridInfo.gridX, gridInfo.gridY);
			if (forecastLocation == null) {
				throw new RuntimeException("Forecast did not load");
			}

			VBox newCurrentDay = startDisplay(forecastLocation);
			Button newFirstDay = firstBox(forecastLocation);
			Button newSecondDay = secondBox(forecastLocation);
			Button newThirdDay = thirdBox(forecastLocation);
			VBox newCollapseInfo = collapseButton(forecastLocation);

			HBox newUnite = new HBox(40, newFirstDay, newSecondDay, newThirdDay);
			newUnite.setAlignment(Pos.BOTTOM_CENTER);

			javafx.application.Platform.runLater(() -> {
				currentDay.getChildren().setAll(newCurrentDay.getChildren());
				unite.getChildren().setAll(newUnite.getChildren());
				collapseInfo.getChildren().setAll(newCollapseInfo.getChildren());

				currentDay.setVisible(true);
				unite.setVisible(true);
				collapseInfo.setVisible(true);
			});
		});

		VBox vbox = new VBox(5, latitudeLabel, latitudeField, longitudeLabel, longitudeField, fetchButton,currentDay,unite,collapseInfo,bottomButtons3);
		vbox.setPadding(new Insets(15));
		return vbox;
	}
	/////////////////////// End of Mahdi's Functions ///////////////////////////



	// Mohammad's Functions, DO NOT TOUCH //
	// The function that displays the current day weather information //
	public VBox startDisplay(ArrayList<Period> forecast){
		Text today = new Text("Current Temperature");
		Text currentTemperature = new Text(forecast.getFirst().temperature + "°");
		Text weatherCurrent = new Text("Wind speed: " + forecast.getFirst().windSpeed +
				"\nWind direction: " + forecast.getFirst().windDirection);

		ImageView iconr = new ImageView(new Image(new File("src/main/resources/rc.png").toURI().toString()));
		iconr.setFitWidth(50);
		iconr.setPreserveRatio(true);
		Text precipitationProbability = new Text(forecast.getFirst().probabilityOfPrecipitation.value + "%");

		VBox currentDay = new VBox(10, today, currentTemperature, weatherCurrent, iconr, precipitationProbability);
		currentDay.setAlignment(Pos.CENTER);
		currentDay.setMinHeight(300);
		today.setFont(new Font("Bison", 20));
		currentTemperature.setFont(new Font("Bison", 20));
		weatherCurrent.setFont(new Font("Bison", 20));

		return currentDay;
	}




	///////////
	///
	///
	///

	private void applySlideTransition(ScrollPane newScene) {
		if (!mainPane1.getChildren().contains(newScene)) {
			newScene.setTranslateY(300); // Start below
			mainPane1.getChildren().add(newScene); // Add it to the stack
		}

		// Slide Up Transition
		TranslateTransition slideUp = new TranslateTransition(Duration.millis(300), newScene);
		slideUp.setFromY(300);
		slideUp.setToY(0);

		// Fade In Transition
		FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newScene);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);

		// Play animations
		slideUp.play();
		fadeIn.play();

		// Remove the previous scene after the transition (except the background)
		slideUp.setOnFinished(e -> {
			if (mainPane1.getChildren().size() > 1) {
				mainPane1.getChildren().remove(0); // Remove the previous scene
			}
		});
	}


	private void applySlideTransitionDown(ScrollPane newScene, ScrollPane oldScene) {
		if (!mainPane1.getChildren().contains(newScene)) {
			newScene.setTranslateY(0); // Start up
			mainPane1.getChildren().add(newScene); // Add it to the stack
		}

		// Slide Up Transition
		TranslateTransition slideDown = new TranslateTransition(Duration.millis(250), oldScene);
		slideDown.setFromY(0);
		slideDown.setToY(300);

		// Fade In Transition
		FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newScene);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);

		// Play animations
		slideDown.play();
		fadeIn.play();

		// Remove the previous scene after the transition (except the background)
		slideDown.setOnFinished(e -> {
			if (mainPane1.getChildren().size() > 1) {
				mainPane1.getChildren().remove(0); // Remove the previous scene
			}
		});

	}

	private ScrollPane getDetailedScene(ArrayList<Period> forecast, int num) {
		ScrollPane pane = new ScrollPane();
		Button button = new Button("Close");
		button.setMinWidth(100);
		button.setMinHeight(50);
		button.setStyle("-fx-background-radius: 20px");

		Text today = new Text("Today");
		today.setFont(new Font("Bison", 20));
		Text info = new Text(forecast.get(num).temperature + "°");

		Text info2 = new Text("Wind Direction: " + forecast.get(num).windSpeed + "\n" +
				forecast.get(num).windDirection);

		info.setFont(new Font("Bison", 20));

		VBox vbox = new VBox(20, today, info, info2, button);
		vbox.setAlignment(Pos.CENTER);

		pane.setContent(vbox);
		pane.setFitToHeight(true);
		pane.setFitToWidth(true);

		button.setOnAction(e -> {
			applySlideTransitionDown(scrollPane1, pane);
		});

		return pane;
	}


	// The first box function //
	// This function shows the weather information of the first day //
	// The box is clickable. It's a button.
	public Button firstBox(ArrayList<Period> forecast){
		txt1 = new Text("Today");
		temperature = new Text(forecast.getFirst().temperature + "°");
		VBox vbox1 = new VBox(7,txt1,temperature);
		vbox1.setAlignment(Pos.CENTER);

		Button button = new Button();
		button.setGraphic(vbox1);
		button.setMaxWidth(100);
		button.setMinWidth(100);
		button.setMaxHeight(150);
		button.setMinHeight(150);
		button.setStyle("-fx-background-radius: 41px");

		ScrollPane detailedScene = getDetailedScene(forecast, 0);

		button.setOnAction(e -> {
			applySlideTransition(detailedScene);
		});

		return button;
	}

	// The second box function //
	// This function shows the weather information of the second day //
	// The box is clickable. It's a button.
	public Button secondBox(ArrayList<Period> forecast){

		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
		String dayOfWeek = dayFormat.format(forecast.get(1).endTime);

		txt2 = new Text(dayOfWeek);
		txt2.setTextAlignment(TextAlignment.CENTER);
		temperature2 = new Text(forecast.get(1).temperature + "°");

		VBox vbox1 = new VBox(7,txt2,temperature2);
		vbox1.setAlignment(Pos.CENTER);

		Button button = new Button();
		button.setGraphic(vbox1);
		button.setMaxWidth(100);
		button.setMinWidth(100);
		button.setMaxHeight(150);
		button.setMinHeight(150);
		button.setStyle("-fx-background-radius: 41px");
		return button;
	}


	// The third box function //
	// This function shows the weather information of the third day //
	// The box is clickable. It's a button.
	public Button thirdBox(ArrayList<Period> forecast){

		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
		String dayOfWeek = dayFormat.format(forecast.get(2).endTime);

		txt3 = new Text(dayOfWeek);
		txt3.setTextAlignment(TextAlignment.CENTER);
		temperature3 = new Text(forecast.get(2).temperature + "°");
		VBox vbox1 = new VBox(7,txt3,temperature3);
		vbox1.setAlignment(Pos.CENTER);

		Button button = new Button();
		button.setGraphic(vbox1);
		button.setGraphic(vbox1);
		button.setMaxWidth(100);
		button.setMinWidth(100);
		button.setMaxHeight(150);
		button.setMinHeight(150);
		button.setStyle("-fx-background-radius: 41px");
		return button;
	}


	// The function that returns the VBox that contains additional information for the next //
	// 7 days //

	private static String capitalizeFirstLetter(String day) {
		return day.charAt(0) + day.substring(1).toLowerCase();
	}

	public VBox collapseButton(ArrayList<Period> forecast) {

		VBox container = new VBox(10);
		LocalDate today = LocalDate.now();

		// Toggle Button
		Button collapseButton = new Button("▼"); // Default: Down Arrow
		collapseButton.setMaxWidth(Double.MAX_VALUE);

		// Collapsible Content
		VBox collapsibleContent = new VBox(10);
		collapsibleContent.setAlignment(Pos.CENTER);
		collapsibleContent.setManaged(false); // Prevent empty space when collapsed
		collapsibleContent.setScaleY(0); // Start fully collapsed

		for (int i = 0; i < 7; i++) {
			Text textInfo;
			if (i == 0) {
				textInfo = new Text("Today " + forecast.get(i).temperature + "°");
			} else {
				LocalDate futureDate = today.plusDays(i);
				DayOfWeek dayOfWeek = futureDate.getDayOfWeek();
				textInfo = new Text(capitalizeFirstLetter(dayOfWeek.toString()) + " " +
						forecast.get(i).temperature + "°");
			}
			textInfo.setFont(Font.font("Bison", 20));
			collapsibleContent.getChildren().add(textInfo);
		}

		// Smooth Transition Animation
		ScaleTransition expandTransition = new ScaleTransition(Duration.millis(200), collapsibleContent);
		expandTransition.setFromY(0);
		expandTransition.setToY(1);

		ScaleTransition collapseTransition = new ScaleTransition(Duration.millis(200), collapsibleContent);
		collapseTransition.setFromY(1);
		collapseTransition.setToY(0);


		double collapsedHeight = 0;
		double expandedHeight = 245;

		Rectangle clip = new Rectangle();
		clip.setWidth(200);  // Adjust width based on layout
		clip.setHeight(collapsedHeight);

		collapsibleContent.setMinHeight(0);
		collapsibleContent.setMaxHeight(0);

		collapseButton.setOnAction(event -> {

			boolean isExpanded = collapsibleContent.getMaxHeight() > 0;

			if (isExpanded) {
				collapseTransition.play();
			} else {
				collapsibleContent.setManaged(true);
				expandTransition.play();
			}

			expandTransition.setOnFinished(e -> collapsibleContent.setManaged(true));
			collapseTransition.setOnFinished(e -> collapsibleContent.setManaged(false));


			double targetHeight = isExpanded ? collapsedHeight : expandedHeight;
			Timeline timeline = new Timeline(
					new KeyFrame(Duration.millis(200),
							new KeyValue(clip.heightProperty(), targetHeight),
							new KeyValue(collapsibleContent.maxHeightProperty(), targetHeight))
			);
			timeline.play();

			collapseButton.setText(isExpanded ? "▼" : "≡");
		});

		container.getChildren().addAll(collapseButton, collapsibleContent);
		container.setAlignment(Pos.CENTER);
		return container;
	}
	/////////////// End of Mohammad's Functions ////////////////////


	//////////////// Mahdi Yahya Code Starts Here ////////////////
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		Image mainImage = new Image(new File("src/main/resources/main.gif").toURI().toString());
		ImageView imageview = new ImageView(mainImage);
		imageview.setFitWidth(400);
		imageview.setPreserveRatio(true);

		Text mainTitle = new Text("Weather");
		mainTitle.setFont(new Font("Bison", 64));
		mainTitle.setFill(Color.WHITE);

		Text mainTitle1 = new Text("ForeCasts");
		mainTitle1.setFont(new Font("Bison", 64));
		mainTitle1.setFill(Color.YELLOW);

		VBox mainTextVBox = new VBox(mainTitle, mainTitle1);
		mainTextVBox.setAlignment(Pos.TOP_CENTER);

		Button roundButton = new Button("Chicago");
		roundButton.setFont(new Font("Bison", 28));
		roundButton.setTextFill(Color.WHITE);
		roundButton.setStyle("-fx-background-color: #008CBA; -fx-background-radius: 50; -fx-padding: 10 20;");
		roundButton.setOnAction(e -> primaryStage.setScene(chicagoScene));

		Button roundButton1 = new Button("Other Locations");
		roundButton1.setFont(new Font("Bison", 28));
		roundButton1.setTextFill(Color.WHITE);
		roundButton1.setStyle("-fx-background-color: #008CBA; -fx-background-radius: 50; -fx-padding: 10 20;");
		roundButton1.setOnAction(e -> {primaryStage.setScene(otherLocation);});

		HBox buttons = new HBox(10, roundButton, roundButton1);
		buttons.setAlignment(Pos.BOTTOM_CENTER);
		buttons.setPadding(new Insets(100, 0, 0, 0));


		VBox mainVBox = new VBox(15, imageview, mainTextVBox, buttons, mainSceneBottomButtons());
		mainVBox.setAlignment(Pos.TOP_CENTER);
		mainVBox.setPadding(new Insets(0, 0, 0, 0));
		mainVBox.setStyle("-fx-background-color: #33495f;");

		mainScene = new Scene(mainVBox, 520, 882);


		VBox otherLocationVBox = otherLocation(primaryStage, mainScene);
		scrollPane2 = new ScrollPane(otherLocationVBox);
		scrollPane2.setFitToWidth(true); // Optional: Ensure the content fits the width
		scrollPane2.setFitToHeight(false); // Allow vertical scrolling
		mainPane2 = new StackPane();
		mainPane2.setStyle("-fx-background: LIGHTSKYBLUE");
		mainPane2.getChildren().add(scrollPane2);
		otherLocation = new Scene(mainPane2, 520, 882);

		VBox chicagoVBox= ChicagoWeatherScene(primaryStage, mainScene);
		scrollPane1 = new ScrollPane(chicagoVBox);
		scrollPane1.setFitToWidth(true); // Optional: Ensure the content fits the width
		scrollPane1.setFitToHeight(false); // Allow vertical scrolling
		scrollPane1.setPannable(true);
		mainPane1 = new StackPane();
		mainPane1.setStyle("-fx-background: LIGHTSKYBLUE");
		mainPane1.getChildren().add(scrollPane1);
		chicagoScene = new Scene(mainPane1, 520, 882);

		primaryStage.setScene(mainScene);
		primaryStage.show();
		//////////////// END OF Mahdi Yahya Code ////////////////
	}
}