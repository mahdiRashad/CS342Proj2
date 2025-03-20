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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import weather.Period;
import weather.WeatherAPI;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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
		returnButton1.setId("fetch_button");
		fadeFeature(returnButton1);
		returnButton1.setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(null);
			alert.setContentText("Already on the home screen.");
			alert.showAndWait();
		});




		Button exitButton1 = new Button("Exit");

		exitButton1.setId("fetch_button");
		fadeFeature(exitButton1);
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


		Button firstDay = shortForecasteBox(forecast, 0, dayOfTheWeek(0));
		Button secondDay = shortForecasteBox(forecast, 2, dayOfTheWeek(2));
		Button thirdDay = shortForecasteBox(forecast, 4, dayOfTheWeek(4));
		VBox collapseInfo = collapseButton(forecast);


		HBox unite = new HBox(45, firstDay ,secondDay ,thirdDay);
		unite.setAlignment(Pos.BOTTOM_CENTER);


		Button returnButton2 = new Button("Return");
		Button exitButton2 = new Button("Exit");

		exitButton2.setId("fetch_button");
		returnButton2.setId("fetch_button");

		fadeFeature(returnButton2);
		fadeFeature(exitButton2);


		HBox bottomButtons2 = new HBox(20, returnButton2, exitButton2);

		returnButton2.setOnAction(e -> primaryStage.setScene(mainScene));
		exitButton2.setOnAction(e -> System.exit(0));

		bottomButtons2.setAlignment(Pos.BOTTOM_CENTER);
		bottomButtons2.setPadding(new Insets(20, 0, 20, 0));

		VBox vbox4 = new VBox(20,currentDay, unite, collapseInfo, bottomButtons2);

		vbox4.setId("main_color");
		return vbox4;
	}


	public VBox otherLocation(Stage primaryStage, Scene mainScene){

		TextField latitudeField;
		TextField longitudeField;

		VBox currentDay = new VBox();
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

		returnButton3.setId("fetch_button");

		fadeFeature(returnButton3);


		returnButton3.setOnAction(e -> {primaryStage.setScene(mainScene);});

		Button exitButton3 = new Button("Exit");

		exitButton3.setId("fetch_button");
		returnButton3.setId("fetch_button");


		fadeFeature(exitButton3);

		exitButton3.setOnAction(e -> System.exit(0));

		HBox bottomButtons3 = new HBox(20, returnButton3, exitButton3);
		bottomButtons3.setAlignment(Pos.BOTTOM_CENTER);


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



			Button newFirstDay = shortForecasteBox(forecastLocation, 0, dayOfTheWeek(0));
			Button newSecondDay = shortForecasteBox(forecastLocation, 2, dayOfTheWeek(2));
			Button newThirdDay = shortForecasteBox(forecastLocation, 4, dayOfTheWeek(4));
			VBox newCurrentDay = startDisplay(forecastLocation);
			VBox newCollapseInfo = collapseButton(forecastLocation);

			HBox newUnite = new HBox(20, newFirstDay, newSecondDay, newThirdDay);
			newUnite.setAlignment(Pos.CENTER);


			javafx.application.Platform.runLater(() -> {
				currentDay.getChildren().setAll(newCurrentDay.getChildren());
				unite.getChildren().setAll(newUnite.getChildren());
				collapseInfo.getChildren().setAll(newCollapseInfo.getChildren());

				currentDay.setVisible(true);
				unite.setVisible(true);
				collapseInfo.setVisible(true);
			});
		});

//		VBox newUnite2 = new VBox(20, currentDay, unite, collapseInfo); // Reduce spacing
//		newUnite2.setAlignment(Pos.CENTER);

		currentDay.setAlignment(Pos.CENTER);
		currentDay.setSpacing(15);
		unite.setAlignment(Pos.CENTER);
		collapseInfo.setAlignment(Pos.CENTER);
		unite.setSpacing(45);

		VBox prompt1 = new VBox(10, latitudeLabel, latitudeField);
		prompt1.setId("labels");

		VBox prompt2 = new VBox(10, longitudeLabel, longitudeField);
		prompt2.setId("labels");

		HBox prompts = new HBox(35, prompt1, prompt2);
		prompts.setAlignment(Pos.CENTER);

		// Fetch button adjustements //

		VBox fetch = new VBox(fetchButton);
		fetchButton.setId("fetch_button");
		fetch.setId("fetch_button_box");

		FadeTransition fadeIn = new FadeTransition(Duration.millis(250), fetch);
		fadeIn.setFromValue(1.0);
		fadeIn.setToValue(0.8);


		FadeTransition fadeOut = new FadeTransition(Duration.millis(250), fetch);
		fadeOut.setFromValue(0.8);
		fadeOut.setToValue(1.0);

		fetchButton.setOnMouseEntered(e -> {
			fadeIn.play();
		});

		fetchButton.setOnMouseExited(e -> {
			fadeOut.play();
		});


		VBox vbox = new VBox(30 , prompts, fetch, currentDay, unite, collapseInfo, bottomButtons3);
		vbox.setPadding(new Insets(70, 10, 10, 10)); // Add 20px padding at the top


		vbox.setId("main_color");


		return vbox;
	}

	/////////////////////// End of Mahdi's Functions ///////////////////////////



	// Mohammad's Functions, DO NOT TOUCH //

	// The functon that returns a specific weather image based on the icon //
	private ImageView getWeatherImage(String shortForecast, int size) {
		shortForecast = shortForecast.toLowerCase(); // Convert to lowercase for easier comparison

		if (shortForecast.contains("sunny") || shortForecast.contains("clear")) {
			return imageView("src/main/resources/s.png", size, true); // ☀️ Sunny or Clear
    	}
		else if (shortForecast.contains("cloudy") || shortForecast.contains("cloud") || shortForecast.contains("clouds")|| shortForecast.contains("overcast")) {
			return imageView("src/main/resources/c.png", size, true);  // ☁️ Cloudy or Mostly Cloudy
		}
		else if (shortForecast.contains("partly sunny")) {
			return imageView("src/main/resources/crs.png", size, true);  // mix weather image
		}
		else if (shortForecast.contains("rain") || shortForecast.contains("showers")|| shortForecast.contains("shower")) {
			return imageView("src/main/resources/rainy.png", size, true);  // 🌧️ Rain or Showers
		}
		else if (shortForecast.contains("thunderstorm") || shortForecast.contains("storm") || shortForecast.contains("thunder")) {
			return imageView("src/main/resources/ts.png", size, true);  // ⛈️ Thunderstorm
		}
		else if (shortForecast.contains("snow") || shortForecast.contains("flurries")|| shortForecast.contains("sleet") || shortForecast.contains("freezing")) {
			return imageView("src/main/resources/sn.png", size, true);  // ❄️ Snow or Flurries
		}
		else if (shortForecast.contains("tornado") || shortForecast.contains("Severe storm")) {
			return imageView("src/main/resources/ts.png", size, true);  // ☁️ Cloudy or Mostly Cloudy
		}
		else {
			return imageView("src/main/resources/s.png", size, true);  // 🔄 Default image for unknown weather
		}
	}

	// The function that fades button //
	private void fadeFeature (Button btn){
		FadeTransition fadeIn = new FadeTransition(Duration.millis(250), btn);
		fadeIn.setFromValue(1.0);
		fadeIn.setToValue(0.8);

		FadeTransition fadeOut = new FadeTransition(Duration.millis(250), btn);
		fadeOut.setFromValue(0.8);
		fadeOut.setToValue(1.0);

		btn.setOnMouseEntered(e -> {
			fadeIn.play();
		});

		btn.setOnMouseExited(e -> {
			fadeOut.play();
		});
	}


	// The function that returns true if I'm in a particular scene and false otherwise
	private boolean isCurrentScene(Scene scene) {
		return scene.getWindow() != null && scene.getWindow().getScene() == scene;
	}

	// Thw function that returns a picture based on the url //
	private ImageView imageView(String URL, int width, boolean bool){

		ImageView imageView = new ImageView(new Image(new File(URL).toURI().toString()));
		imageView.setFitHeight(width);
		imageView.setPreserveRatio(bool);
		return imageView;
	}

	// The function to give the day of week based on the parameter //
	private String dayOfTheWeek(int i){
		if(i == 0){
			return "Today";
		} else {
			LocalDate today = LocalDate.now();
			LocalDate futureDate = today.plusDays(i/2);
			DayOfWeek dayOfWeek = futureDate.getDayOfWeek();
			return capitalizeFirstLetter(dayOfWeek.toString().substring(0, 3));
		}
	}

	// The function that displays the current day weather information //
	private VBox startDisplay(ArrayList<Period> forecast){


		ImageView weatherSymbol = getWeatherImage(forecast.getFirst().shortForecast, 120);
		Text currentTemperature = new Text(forecast.getFirst().temperature + "°");
		currentTemperature.setFont(Font.font(45));
		Text weatherCurrent = new Text(forecast.getFirst().shortForecast);


		VBox currentDay = new VBox(28, weatherSymbol, currentTemperature, weatherCurrent);
		currentDay.setAlignment(Pos.CENTER);
		currentDay.setMinHeight(300);
		currentTemperature.setFont(new Font("Bison", 38));
		weatherCurrent.setFont(new Font("Bison", 20));

		return currentDay;
	}

	// The function that slides up a new scene //
	private void applySlideTransition(ScrollPane newScene) {

		// Ensure the new scene is only added once per pane
//		otherLocation, chicagoScene;
		if (isCurrentScene(chicagoScene)) {
			mainPane1.getChildren().add(newScene);

		} else if (isCurrentScene(otherLocation)) {
			mainPane2.getChildren().add(newScene);
		}

		// Reset position and opacity before applying animation
		newScene.setTranslateY(300);
		newScene.setOpacity(0.0);

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

		// Remove the previous scene after the transition (except the new one)
		slideUp.setOnFinished(e -> {
			if (mainPane1.getChildren().size() > 1) {
				mainPane1.getChildren().remove(0);
			}
			else if (mainPane2.getChildren().size() > 1) {
				mainPane2.getChildren().remove(0);
			}
		});
	}

	// The function that slides down a new scene //
	private void applySlideTransitionDown(ScrollPane newScene, ScrollPane newScene2, ScrollPane oldScene) {

		FadeTransition fadeIn = null;

		if (isCurrentScene(chicagoScene)) {
			mainPane1.getChildren().add(newScene);
			fadeIn = new FadeTransition(Duration.millis(500), newScene);

		} else if (isCurrentScene(otherLocation)) {
			mainPane2.getChildren().add(newScene2);
			fadeIn = new FadeTransition(Duration.millis(500), newScene2);
		}


		// Slide Up Transition
		TranslateTransition slideDown = new TranslateTransition(Duration.millis(250), oldScene);
		slideDown.setFromY(0);
		slideDown.setToY(300);

		// Fade In Transition
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);

		// Play animations
		slideDown.play();
		fadeIn.play();

		// Remove the previous scene after the transition (except the background)
		slideDown.setOnFinished(e -> {
			if (mainPane1.getChildren().size() > 1) {
				mainPane1.getChildren().remove(0); // Remove the previous scene
			} else if (mainPane2.getChildren().size() > 1){
				mainPane2.getChildren().remove(0);
			}
		});
	}

	// The function that returns a new scene //
	private ScrollPane getDetailedScene(ArrayList<Period> forecast, int num, String day) {
		ScrollPane pane = new ScrollPane();

		Button button = new Button("Close");
		button.getStyleClass().add("close_button");


		LocalDate date = LocalDate.now().plusDays(num/2);
		Text dateDesc = new Text(day + "    " + date);
		dateDesc.setFont(new Font("Bison", 15));
		dateDesc.setStyle("-fx-fill: #ffffff");
		HBox descDateHbox = new HBox(dateDesc);
		descDateHbox.getStyleClass().add("desc-date-hbox");


		Text info = new Text(forecast.get(num).temperature + "°");
		info.setFont(new Font("Bison", 20));
		info.setStyle("-fx-fill: #ffffff");

		ImageView weatherSymbol = getWeatherImage(forecast.get(num).shortForecast, 120);

		VBox weatherSymbolVBox = new VBox(28, weatherSymbol, info);
		weatherSymbolVBox.setAlignment(Pos.CENTER);


		// Text info2 adjustment //
		Text info2 = new Text(forecast.get(num).detailedForecast);
		info2.getStyleClass().add("text_style");
		TextFlow textFlow = new TextFlow(info2);
		textFlow.getStyleClass().add("text_style");

		VBox info2Vbox = new VBox(textFlow);
		info2Vbox.getStyleClass().add("vbox_background");


		ImageView weatherSymbol2 = imageView("src/main/resources/wind.png", 17, true);
		Text info3 = new Text("Wind            " + forecast.get(num).windSpeed);
		Text info4 = new Text("Direction      " + forecast.get(num).windDirection);

		info3.setStyle("-fx-fill: #e3e1f4");
		info4.setStyle("-fx-fill: #e3e1f4");

		VBox weatherSymbolVBox2 = new VBox(weatherSymbol2);
		weatherSymbolVBox2.setAlignment(Pos.CENTER);
		VBox info3Vbox = new VBox(info3);
		info3Vbox.setAlignment(Pos.CENTER);
		VBox info4Vbox = new VBox(info4);
		info4Vbox.setAlignment(Pos.CENTER);

		VBox gatherInfoVBox = new VBox(10, weatherSymbolVBox2, info3Vbox, info4Vbox);
		gatherInfoVBox.setId("wind_box");

		ImageView weatherSymbol3 = imageView("src/main/resources/water.png", 25, true);
		Text info5 = new Text(forecast.get(num).probabilityOfPrecipitation.value + "%");
		info5.setStyle("-fx-fill: #e3e1f4; -fx-font-size: 10pt; -fx-font-weight: bold");
		HBox precInfo = new HBox(15, weatherSymbol3, info5);

		precInfo.setId("wind_box");

		HBox gatherMore = new HBox(20, precInfo, gatherInfoVBox);

		gatherMore.setAlignment(Pos.CENTER);

		info.setFont(new Font("Bison", 40));

		button.setId("fetch_button");
		fadeFeature(button);

		VBox vbox = new VBox(50, descDateHbox, weatherSymbolVBox, info2Vbox, gatherMore, button);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getStyleClass().add("detailed_scene");

		pane.setContent(vbox);
		pane.setFitToHeight(true);
		pane.setFitToWidth(true);

		button.setOnAction(e -> {
			applySlideTransitionDown(scrollPane1, scrollPane2, pane);
		});

		return pane;
	}

	// The first box function //
	// This function shows the weather information of the first day //
	// The box is clickable. It's a button.
	private Button shortForecasteBox(ArrayList<Period> forecast, int num, String weekDay){

		txt1 = new Text(weekDay);
		ImageView weatherSymbol =  getWeatherImage(forecast.get(num).shortForecast, 20);
		temperature = new Text(forecast.get(num).temperature + "°");

		txt1.setStyle("-fx-fill: #f3eded; -fx-font-size: 12pt");
		temperature.setStyle("-fx-fill: #f3eded; -fx-font-size: 12pt");

		VBox vbox1 = new VBox(13, txt1, weatherSymbol, temperature);
		vbox1.setAlignment(Pos.CENTER);

		Button button = new Button();
		button.setGraphic(vbox1);
		button.getStyleClass().add("box_button");
		Rectangle clip = new Rectangle(100, 150);
		clip.setArcWidth(80);  // 2 * radius (20px radius = 40px arc width)
		clip.setArcHeight(80); // 2 * radius
		button.setClip(clip);

		fadeFeature(button);

		ScrollPane detailedScene = getDetailedScene(forecast, num, weekDay);

		button.setOnAction(e -> {
			applySlideTransition(detailedScene);
		});

		return button;
	}

	// The function that returns the VBox that contains additional forecast information for the next //
	// 7 days //
	private String capitalizeFirstLetter(String day) {
		return day.charAt(0) + day.substring(1).toLowerCase();
	}

	// The function that returns the collapsable button //
	private VBox collapseButton(ArrayList<Period> forecast) {
		Text textInfo;
		Text textInfo2;
		HBox hbox;
		ImageView weatherSymbol = null;

		VBox container = new VBox(10);

		Button collapseButton = new Button("Next Seven Days Forecast");
		collapseButton.setMaxWidth(Double.MAX_VALUE);

		VBox collapsibleContent = new VBox(10);
		collapsibleContent.setAlignment(Pos.CENTER);
		collapsibleContent.setManaged(false);
		collapsibleContent.setScaleY(0);

		for (int i = 2; i <= 14; i += 2) {

			if(forecast.get(i - 2).probabilityOfPrecipitation.value > 0){
				String weatherCondition = forecast.get(i - 2).shortForecast.toLowerCase();

				if(weatherCondition.contains("snow") || weatherCondition.contains("flurries")|| weatherCondition.contains("sleet") || weatherCondition.contains("freezing")){

					weatherSymbol = imageView("src/main/resources/sf.png", 20, true);

				} else if (weatherCondition.contains("rain") || weatherCondition.contains("showers")|| weatherCondition.contains("shower") ||
						weatherCondition.contains("thunderstorm") || weatherCondition.contains("storm") || weatherCondition.contains("thunder")) {

					weatherSymbol = imageView("src/main/resources/water.png", 20, true);

				}

				textInfo = new Text(dayOfTheWeek(i - 2) + "   ");
				textInfo2 = new Text(forecast.get(i - 2).probabilityOfPrecipitation.value + "%" + "               " +
						forecast.get(i - 1).temperature + "°" + " - " + forecast.get(i - 2).temperature + "°");

			} else {
				weatherSymbol = getWeatherImage(forecast.get(i - 2).shortForecast, 20);
				textInfo = new Text(dayOfTheWeek(i - 2));
				textInfo2 = new Text("           " + forecast.get(i - 1).temperature + "°" + " - " + forecast.get(i - 2).temperature + "°");
			}

			textInfo.setTextAlignment(TextAlignment.LEFT);
			textInfo.setStyle("-fx-font-size: 12pt");

			textInfo2.setTextAlignment(TextAlignment.RIGHT);
			textInfo2.setStyle("-fx-font-size: 12pt");

			textInfo.setWrappingWidth(100);
			textInfo2.setWrappingWidth(170);

			hbox = new HBox(-12, textInfo, weatherSymbol, textInfo2);
			hbox.setAlignment(Pos.CENTER);

			hbox.setBorder(new Border(new BorderStroke(
					Color.BLACK, // Border color
					BorderStrokeStyle.SOLID, // Border style
					CornerRadii.EMPTY, // No rounded corners
					new BorderWidths(0, 0, 0.5, 0)
			)));

			hbox.setMaxWidth(340);
			hbox.setMinWidth(340);
			hbox.setPadding(new Insets(0, 0, 6, 0));
			textInfo.setFont(Font.font("Bison", 20));

			collapsibleContent.getChildren().add(hbox);
		}

		// Smooth Transition Animation
		ScaleTransition expandTransition = new ScaleTransition(Duration.millis(200), collapsibleContent);
		expandTransition.setFromY(0);
		expandTransition.setToY(1);

		ScaleTransition collapseTransition = new ScaleTransition(Duration.millis(200), collapsibleContent);
		collapseTransition.setFromY(1);
		collapseTransition.setToY(0);


		double collapsedHeight = 0;
		double expandedHeight = 295;

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

			collapseButton.setText(isExpanded ? "Next Seven Days Forecast" : "≡");
		});

		collapseButton.setId("collapse_button");
		fadeFeature(collapseButton);

		container.getChildren().addAll(collapseButton, collapsibleContent);
		container.setAlignment(Pos.CENTER);
		return container;
	}


	//
	//
	//
	//

	/////////////// End of Mohammad's Functions ////////////////////



	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		//////////////// Mahdi Yahya Code Starts Here ////////////////
		ImageView mainImage = imageView("src/main/resources/main.gif", 300, true);

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
		buttons.setAlignment(Pos.TOP_CENTER);
		buttons.setPadding(new Insets(40, 0, 0, 0));


		VBox mainVBox = new VBox(15, mainImage, mainTextVBox, buttons, mainSceneBottomButtons());
		mainVBox.setAlignment(Pos.TOP_CENTER);
		mainVBox.setPadding(new Insets(0, 0, 0, 0));
		mainVBox.setStyle("-fx-background-color: #33495f;");

		mainScene = new Scene(mainVBox, 520, 780);


		scrollPane2 = new ScrollPane(otherLocation(primaryStage, mainScene));
		scrollPane2.setFitToWidth(true);
		scrollPane2.setFitToHeight(false);
		mainPane2 = new StackPane();
		mainPane2.getChildren().add(scrollPane2);
		otherLocation = new Scene(mainPane2, 520, 780);
		otherLocation.getStylesheets().add(getClass().getResource("style.css").toExternalForm());



		scrollPane1 = new ScrollPane(ChicagoWeatherScene(primaryStage, mainScene));
		scrollPane1.setFitToWidth(true);
		scrollPane1.setFitToHeight(false);
		mainPane1 = new StackPane();
		mainPane1.getChildren().add(scrollPane1);


		chicagoScene = new Scene(mainPane1, 520, 780);

		chicagoScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		primaryStage.setScene(mainScene);
		primaryStage.show();
		//////////////// END OF Mahdi Yahya Code ////////////////
	}
}
