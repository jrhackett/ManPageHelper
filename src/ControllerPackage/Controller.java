package ControllerPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {

    private Stage stage;
    private StackPane root;

    private Button userCommandsButton;
    private Button systemCallsButton;
    private Button libraryFunctionsButton;
    private Button devicesButton;
    private Button filesButton;
    private Button overviewsButton;
    private Button superuserButton;

    private ListView<String> commands;


    public Controller(Stage stage, StackPane root) {
        this.stage = stage;
        this.root = root;
    }

    /*
     * initDisplay initializes the display
     * @param void
     * @return void
     */
    public void initDisplay() {
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();

        initButtons();

        hbox1.getChildren().addAll(userCommandsButton, systemCallsButton, libraryFunctionsButton, devicesButton, filesButton, overviewsButton, superuserButton);

        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER);

        this.createCommandsListView();

        hbox2.getChildren().addAll(this.commands);

        hbox2.setSpacing(10);
        hbox2.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(hbox1, hbox2);

        vbox.setSpacing(25);
        vbox.setAlignment(Pos.CENTER);

        this.root.getChildren().addAll(vbox);
    }

    /*
     * initButtons initializes all buttons on the interface
     * @param void
     * @return void
     */
    public void initButtons() {

        userCommandsButton = new Button("User Commands");
        systemCallsButton = new Button("System Calls");
        libraryFunctionsButton = new Button("Library Functions");
        devicesButton = new Button("Devices");
        filesButton = new Button("Files");
        overviewsButton = new Button("Overviews, Conventions and Miscellaneous");
        superuserButton = new Button("Superuser and System Administration Commands");

        userCommandsButton.setOnMouseClicked(e -> {
            // display all user command man pages
            handleButtonClick();
        });

        systemCallsButton.setOnMouseClicked(e -> {
            // display all system call man pages
            handleButtonClick();
        });

        libraryFunctionsButton.setOnMouseClicked(e -> {
            // display all library function man pages
            handleButtonClick();
        });

        devicesButton.setOnMouseClicked(e -> {
            // display all devices man pages
            handleButtonClick();
        });

        filesButton.setOnMouseClicked(e -> {
            // display all files man
            handleButtonClick();
        });

        overviewsButton.setOnMouseClicked(e -> {
            // display all overview man pages
            handleButtonClick();
        });

        superuserButton.setOnMouseClicked(e -> {
            // display all super user man pages
            handleButtonClick();
        });
    }

    public void clearCommands() {
        this.commands.setCellFactory((ListView<String> lv) ->
            new ListCell<String>() {
                @Override
                public void updateItem(String in, boolean empty) {
                    super.updateItem(in, empty);
                    setGraphic(null);
                }
            });

        this.commands.setItems(FXCollections.observableArrayList());

        this.commands.setCellFactory((ListView<String> lv) ->
            new ListCell<String>() {
                @Override
                public void updateItem(String in, boolean empty) {
                    super.updateItem(in, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        Text text = new Text(in.toString());
                        text.setWrappingWidth(150);
                        setGraphic(text);
                    }
                }
            });
    }

    public void createCommandsListView() {
        this.commands = new ListView<String>();
    }

    public void setCommands(ArrayList<String> commands) {
        this.clearCommands();
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(commands);
        this.commands.setItems(data);
    }

    public void handleButtonClick() {

    }
}
