import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
	Text txt1, temperature, weather;
	Text txt2, txt3, temperature2, temperature3;
	Text weather2, weather3;


	public static void main(String[] args) {

		launch(args);
	}


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


	// The first box function //
	// This function shows the weather information of the first day //
	// The box is clickable. It's a button.
	public Button firstBox(ArrayList<Period> forecast){
		txt1 = new Text("Today");
		temperature = new Text(forecast.getFirst().temperature + "°");
		weather = new Text("Wind speed: " + forecast.getFirst().windSpeed +
				"\n Wind direction: " + forecast.getFirst().windDirection);
		VBox vbox1 = new VBox(7,txt1,temperature,weather);
		vbox1.setAlignment(Pos.CENTER);

		Button button = new Button();
		button.setGraphic(vbox1);

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
		weather2 = new Text("Wind speed: " + forecast.get(1).windSpeed +
				"\n Wind direction: " + forecast.get(1).windDirection);
		VBox vbox2 = new VBox(7,txt2,temperature2,weather2);
		vbox2.setAlignment(Pos.CENTER);

		Button button2 = new Button();
		button2.setGraphic(vbox2);
		return button2;
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
		weather3 = new Text("Wind speed: " + forecast.get(2).windSpeed +
				"\n Wind direction: " + forecast.get(2).windDirection);
		VBox vbox3 = new VBox(7,txt3,temperature3, weather3);
		vbox3.setAlignment(Pos.CENTER);

		Button button3 = new Button();
		button3.setGraphic(vbox3);
		return button3;
	}



	// The function that returns the VBox that contains additional information for the next //
	// 7 days //

	private static String capitalizeFirstLetter(String day) {
		return day.charAt(0) + day.substring(1).toLowerCase();
	}

	public VBox collapseButton(ArrayList<Period> forecast){
		VBox container = new VBox(10);
		LocalDate today = LocalDate.now();


		// Button to toggle visibility
		Button collapseButton = new Button("▼"); // Default down arrow
		collapseButton.setMaxWidth(Double.MAX_VALUE);
		VBox collapsibleContent = new VBox(10);
		collapsibleContent.setAlignment(Pos.CENTER);

		// Sample content inside collapsible area

		for (int i = 0; i < 7; i++) {

			Text textInfo;
			SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
			if(i == 0){
				textInfo = new Text("Today " + forecast.get(i).temperature + "°");
				textInfo.setFont(Font.font("Bison", 20));

			} else {
				LocalDate futureDate = today.plusDays(i);
				DayOfWeek dayOfWeek = futureDate.getDayOfWeek();
				textInfo = new Text(capitalizeFirstLetter(dayOfWeek.toString()) + " " +
						forecast.get(i).temperature + "°");
				textInfo.setFont(Font.font("Bison", 20));
			}

			collapsibleContent.getChildren().addAll(textInfo);
		}


		collapsibleContent.setVisible(false);


		collapseButton.setOnAction(event -> {
			boolean isVisible = collapsibleContent.isVisible();
			collapsibleContent.setVisible(!isVisible);
			collapseButton.setText(isVisible ? "▼" : "≡"); // Change button text
		});

		container.getChildren().addAll(collapseButton, collapsibleContent);
		container.setAlignment(Pos.CENTER);
		return container;
	}

/////////////////////// End of Mohammad's Functions ///////////////////////////

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Daily Weather");

		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);

		Button returnButton2 = new Button("Return");
		Button exitButton2 = new Button("Exit");

		HBox bottomButtons2 = new HBox(20,returnButton2,exitButton2);

	///////////// Mohammed Kamil Code: Daily Weather, DO NOT TOUCH ////////////////


		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		// The function call for current day information //
		VBox currentDay = startDisplay(forecast);


//		Button button = new Button("Hover Me");

//		// Default button style
//		button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
//
//		// Hover transition
//		FadeTransition fadeIn = new FadeTransition(Duration.millis(300), button);
//		fadeIn.setFromValue(1.0);
//		fadeIn.setToValue(0.8);
//
//		FadeTransition fadeOut = new FadeTransition(Duration.millis(300), button);
//		fadeOut.setFromValue(0.8);
//		fadeOut.setToValue(1.0);
//
//		button.setOnMouseEntered(e -> {
//			button.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
//			fadeIn.playFromStart();
//		});
//
//		button.setOnMouseExited(e -> {
//			button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
//			fadeOut.playFromStart();
//		});

		Button firstDay = firstBox(forecast);
		Button secondDay = secondBox(forecast);
		Button thirdDay = thirdBox(forecast);
		VBox collapseInfo = collapseButton(forecast);


		HBox unite = new HBox(20, firstDay ,secondDay ,thirdDay);
		unite.setAlignment(Pos.BOTTOM_CENTER);

		HBox bottomButtons3 = new HBox(20,returnButton2,exitButton2);
		bottomButtons3.setAlignment(Pos.BOTTOM_CENTER);

		VBox vbox4 = new VBox(10,currentDay, unite, collapseInfo, bottomButtons3);

		Scene scene = new Scene(vbox4, 600,780);


		//////////////// END OF Mohammed Kamil Code //////////////////






		//////////////////////////////////////////////////////////////

		//////////////// Mahdi Yahya Code Starts Here ////////////////

		TextField latitudeField;
		TextField longitudeField;
		TextArea resultArea;
		resultArea = new TextArea();
		resultArea.setEditable(false);
		resultArea.setWrapText(true);
		Label latitudeLabel = new Label("Enter Latitude:");
		latitudeField = new TextField();

		Label longitudeLabel = new Label("Enter Longitude:");
		longitudeField = new TextField();

		Button fetchButton = new Button("Get Forecast");
		fetchButton.setOnAction(e ->{

			String latitude = latitudeField.getText();
			String longitude = longitudeField.getText();

			GridInfo gridInfo = MyWeatherAPI.getGridInfo(latitude, longitude);
			if (gridInfo == null) {
				System.out.println("Failed to retrieve grid information.");
				return;
			}
			ArrayList<Period> forecastLocation = WeatherAPI.getForecast(gridInfo.gridId,gridInfo.gridX,gridInfo.gridY);
			if (forecastLocation == null){
				throw new RuntimeException("Forecast did not load");
			}
			resultArea.setText(forecastLocation.get(0).shortForecast + "Today's weather is: "+String.valueOf(forecastLocation.get(0).temperature));

		});

		VBox vbox = new VBox(10, latitudeLabel, latitudeField, longitudeLabel, longitudeField, fetchButton, resultArea);
		vbox.setPadding(new Insets(15));

		Scene otherLocation = new Scene( vbox, 520,780);


		primaryStage.setTitle("Weather App");
		//int temp = WeatherAPI.getTodaysTemperature(77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}
		TextField temperatureLocation = new TextField();
		TextField weatherLocation = new TextField();
		temperatureLocation.setText("Today's weather is: "+String.valueOf(forecast.get(0).temperature));
		weatherLocation.setText(forecast.get(0).shortForecast);

		Scene sceneLocation = new Scene(new VBox(temperatureLocation,weatherLocation,bottomButtons2), 520,780);



		Image image = new Image(new File("src/main/resources/main.gif").toURI().toString());
		ImageView imageview = new ImageView(image);
		imageview.setFitWidth(400);
		imageview.setPreserveRatio(true);

		Text title = new Text("Weather");
		title.setFont(new Font("Bison", 64));
		title.setFill(Color.WHITE);

		Text title1 = new Text("ForeCasts");
		title1.setFont(new Font("Bison", 64));
		title1.setFill(Color.YELLOW);

		VBox textVBox =  new VBox(title,title1);
		textVBox.setAlignment(Pos.TOP_CENTER);

		Button roundButton = new Button("Chicago");
		roundButton.setFont(new Font("Bison", 28));
		roundButton.setTextFill(Color.WHITE);
		roundButton.setStyle("-fx-background-color: #008CBA; -fx-background-radius: 50; -fx-padding: 10 20;");
		roundButton.setOnAction(e -> primaryStage.setScene(scene) );

		Button roundButton1 = new Button("Other Locations");
		roundButton1.setFont(new Font("Bison", 28));
		roundButton1.setTextFill(Color.WHITE);
		roundButton1.setStyle("-fx-background-color: #008CBA; -fx-background-radius: 50; -fx-padding: 10 20;");
		roundButton1.setOnAction(e -> {primaryStage.setScene(otherLocation);});

		HBox buttons = new HBox(10,roundButton, roundButton1);
		buttons.setAlignment(Pos.TOP_CENTER);
		buttons.setPadding(new Insets(40, 0, 0, 0));

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
		exitButton1.setOnAction(e -> System.exit(0)); // Exit app

		HBox bottomButtons1 = new HBox(20, returnButton1, exitButton1);
		bottomButtons1.setAlignment(Pos.BOTTOM_CENTER);
		bottomButtons1.setPadding(new Insets(20, 0, 20, 0));

		VBox mainVBox =  new VBox(15,imageview,textVBox,buttons,bottomButtons1);
		mainVBox.setAlignment(Pos.TOP_CENTER);
		mainVBox.setPadding(new Insets(0, 0, 0, 0));
		mainVBox.setStyle("-fx-background-color: #33495f;");

		Scene mainScene = new Scene(mainVBox, 520,780);



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



		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

}
			//////////////// END OF Mahdi Yahya Code ////////////////
