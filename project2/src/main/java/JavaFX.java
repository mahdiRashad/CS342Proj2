import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import javafx.scene.image.Image;


public class JavaFX extends Application {
	TextField temperature,weather;


	public static void main(String[] args) {

		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {

		Button returnButton2=new Button("Return");
		Button exitButton2=new Button("Exit");
		HBox bottomButtons2=new HBox(20,returnButton2,exitButton2);

		primaryStage.setTitle("Daily Weather");
		//int temp = WeatherAPI.getTodaysTemperature(77,70);
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}
		temperature = new TextField();
		weather = new TextField();
		temperature.setText("Today's weather is: "+String.valueOf(forecast.get(0).temperature));
		weather.setText(forecast.get(0).shortForecast);

		Scene scene = new Scene(new VBox(temperature,weather,bottomButtons2), 520,780);



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

		Button roundButton = new Button("Daily Weather");
		roundButton.setFont(new Font("Bison", 28));
		roundButton.setTextFill(Color.WHITE);
		roundButton.setStyle("-fx-background-color: #008CBA; -fx-background-radius: 50; -fx-padding: 10 20;");
		roundButton.setOnAction(e -> primaryStage.setScene(scene) );

		Button roundButton1 = new Button("Location Based");
		roundButton1.setFont(new Font("Bison", 28));
		roundButton1.setTextFill(Color.WHITE);
		roundButton1.setStyle("-fx-background-color: #008CBA; -fx-background-radius: 50; -fx-padding: 10 20;");

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
