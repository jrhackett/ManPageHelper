package ControllerPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

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
        //XML parser code from:
        //http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        try {
            File fXmlFile = new File("src/ControllerPackage/man.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //also useful: http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("page");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("man page : " + eElement.getAttribute("page"));
                    System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Synopsis : " + eElement.getElementsByTagName("synopsis").item(0).getTextContent());
                    System.out.println("Description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
                    System.out.println("Options : " + eElement.getElementsByTagName("options").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }
}
