//package ControllerPackage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;
    private StackPane root;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.root = new StackPane();
        this.stage = primaryStage;

        this.controller = new Controller(this.stage, this.root);
        controller.initDisplay();

        primaryStage.setTitle("ManHelp");
        primaryStage.setScene(new Scene(this.root, 1000, 600));

        this.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
