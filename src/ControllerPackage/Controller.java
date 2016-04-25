// Jacob Hackett

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {

    // stage and root variables
    private Stage stage;
    private StackPane root;

    // button variables
    private Button userCommandsButton;
    private Button systemCallsButton;
    private Button libraryFunctionsButton;
    private Button devicesButton;
    private Button filesButton;
    private Button overviewsButton;
    private Button superuserButton;
    private Button allButton;

    //list view to store commands
    private ListView<String> commands;

    //arraylist to store manpages
    private ArrayList<ManPage> manpages;

    //boolean to control clicking the listview cells
    private boolean inPage;

    //constructor
    public Controller(Stage stage, StackPane root) {
        this.stage = stage;
        this.root = root;

        // parses manpages from xml into an arraylist of ManPage objects
        this.manpages = new ArrayList<ManPage>();
        this.setupManPages();

        //setup stylesheet and id of root
        this.root.getStylesheets().add("custom.css");
        this.root.setId("background");

        //inPage boolean is false at the start
        inPage = false;
    }

    /*
     * initDisplay initializes the display
     * @param void
     * @return void
     */
    public void initDisplay() {
        // setup boxes
        VBox vbox = new VBox();
        HBox tophbox = new HBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox instructionsHBox = new HBox();

        //initialize buttons
        initButtons();

        // top label
        Label topLabel = new Label("ManHelp");
        topLabel.setId("title");
        topLabel.setTextFill(Paint.valueOf("#FFFFFF"));

        // add label to the top box
        tophbox.getChildren().addAll(topLabel);

        // label to show some instructions
        Label instructionsLabel = new Label("Select a command below to see details and sort the commands using the buttons");
        instructionsLabel.setId("instructions");
        instructionsLabel.setTextFill(Paint.valueOf("#FFFFFF"));

        // add label to box and edit box alignment and spacing
        instructionsHBox.getChildren().addAll(instructionsLabel);
        instructionsHBox.setAlignment(Pos.CENTER);
        instructionsHBox.setSpacing(10);

        // add buttons to boxes
        hbox1.getChildren().addAll(userCommandsButton, systemCallsButton, libraryFunctionsButton, devicesButton, filesButton);
        hbox2.getChildren().addAll(overviewsButton, superuserButton, allButton);

        // setup box alignments and spacings
        tophbox.setSpacing(10);
        tophbox.setAlignment(Pos.TOP_CENTER);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setSpacing(10);
        hbox2.setAlignment(Pos.CENTER);

        // create the listview
        this.createCommandsListView();

        // add listview to the box and edit box alignment and spacing
        hbox3.getChildren().addAll(this.commands);
        hbox3.setSpacing(10);
        hbox3.setAlignment(Pos.CENTER);

        // add boxes to big vbox and edit alignment and spacing
        vbox.getChildren().addAll(tophbox, instructionsHBox, hbox1, hbox2, hbox3);

        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);

        // add box to the root
        this.root.getChildren().addAll(vbox);
    }

    /*
     * initButtons initializes all buttons on the interface
     * @param void
     * @return void
     */
    public void initButtons() {

        // inits the buttons with given text
        userCommandsButton = new Button("User Commands");
        systemCallsButton = new Button("System Calls");
        libraryFunctionsButton = new Button("Library Functions");
        devicesButton = new Button("Devices");
        filesButton = new Button("Files");
        overviewsButton = new Button("Overviews, Conventions and Misc.");
        superuserButton = new Button("Superuser and System Admin");
        allButton = new Button("Show All");

        // event handlers below to handle the button clicks
        userCommandsButton.setOnMouseClicked(e -> {
            // display all user command man pages
            handleButtonClick("user");
            inPage = false;
        });

        systemCallsButton.setOnMouseClicked(e -> {
            // display all system call man pages
            handleButtonClick("system");
            inPage = false;
        });

        libraryFunctionsButton.setOnMouseClicked(e -> {
            // display all library function man pages
            handleButtonClick("library");
            inPage = false;
        });

        devicesButton.setOnMouseClicked(e -> {
            // display all devices man pages
            handleButtonClick("devices");
            inPage = false;
        });

        filesButton.setOnMouseClicked(e -> {
            // display all files man
            handleButtonClick("files");
            inPage = false;
        });

        overviewsButton.setOnMouseClicked(e -> {
            // display all overview man pages
            handleButtonClick("overviews");
            inPage = false;
        });

        superuserButton.setOnMouseClicked(e -> {
            // display all super user man pages
            handleButtonClick("superuser");
            inPage = false;
        });

        allButton.setOnMouseClicked(e -> {
            handleButtonClick("all");
            inPage = false;
        });
    }

    // clears the listview
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
                        text.setWrappingWidth(500);
                        setGraphic(text);
                    }
                }
            });
    }

    // creates the listview for commands to be stored
    public void createCommandsListView() {
        this.commands = new ListView<String>();
        this.commands.setId("list-view-id");
        ArrayList<String> commandsList = new ArrayList<>();

        for(ManPage item : this.manpages) {
            commandsList.add(item.getName());
        }

        this.setCommands(commandsList);

        // set up event handler for clicking on a list cell
        this.commands.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!inPage) {
                    handleListCellClick(commands.getSelectionModel().getSelectedItem());
                    inPage = true;
                }
            }
        });
    }

    // set the commands in the listview to this given arraylist of strings
    public void setCommands(ArrayList<String> commands) {
        this.clearCommands();
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(commands);
        Collections.sort(data);
        this.commands.setItems(data);
    }

    // button click handler
    // sets the listview to have the correct commands in there based on the button clicked
    public void handleButtonClick(String type) {
        ArrayList<String> newCommands = new ArrayList<>();

        for (ManPage item : this.manpages) {
            if (item.compareType(type) || type.equals("all"))
                newCommands.add(item.getName());
        }
        this.setCommands(newCommands);
    }

    // listcell click handler
    // sets the listview to have the information about the clicked command
    public void handleListCellClick(String command) {
        ArrayList<String> newCommands = new ArrayList<>();

        for(ManPage item : this.manpages) {
            if(item.getName().equals(command))
                newCommands.add(item.toString());
        }
        this.setCommands(newCommands);
    }

    //parses man pages from xml file man.xml
    public void setupManPages() {
        //XML parser code from:
        //http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        try {
            File fXmlFile = new File("ControllerPackage/man.xml"); // fuck relative paths
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //also useful: http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("page");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String synopsis = eElement.getElementsByTagName("synopsis").item(0).getTextContent();
                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    String type = eElement.getElementsByTagName("type").item(0).getTextContent();

                    ManPage page = new ManPage(name, synopsis, description, type);
                    this.manpages.add(page);
                    //System.out.println(page);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
