package pl.edu.agh.metal.spw.kfilesviewer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 740;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public BorderPane borderPane;
    private Group rootGroup;


    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        try {
            Data data = new Data(file);
            visualize(data);
        } catch (CustomException ex) {
            raiseErrorAlert(ex.getMessage());
        }
    }


    private void visualize(Data data) {
        data.getElementDefList().forEach(elementDef -> {
            if (elementDef.getType() == ElementDef.Type.SOLID) {
                handleSolidElementData(data, elementDef);
            } else {
                handleShellElementData(data, elementDef);
            }
        });
    }

    private void handleShellElementData(Data data, ElementDef elementDef) {
        int[] indices = elementDef.getNodesCoordinatesIndices();
        float[] points = new float[12];

        int i = 0;
        for (int index : indices) {
            Point3D coords = data.getNodesCoordinates().get(index - 1);
            points[i++] = (float) coords.getX();
            points[i++] = (float) coords.getY();
            points[i++] = (float) coords.getZ();
        }

        addElementToGroup(new Shell(points));
    }

    private void handleSolidElementData(Data data, ElementDef elementDef) {
        int[] indices = elementDef.getNodesCoordinatesIndices();
        float[] points = new float[24];

        int i = 0;
        for (int index : indices) {
            Point3D coords = data.getNodesCoordinates().get(index - 1);
            points[i++] = (float) coords.getX();
            points[i++] = (float) coords.getY();
            points[i++] = (float) coords.getZ();
        }

        addElementToGroup(new Hexahedron(points));
    }


    private void addElementToGroup(MeshView element) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.ROYALBLUE);
        element.setMaterial(material);
        rootGroup.getChildren().add(element);
    }


    private void raiseErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not load the file");
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    public void clearView() {
        rootGroup.getChildren().clear();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootGroup = new Group();
        SubScene scene = new SubScene(rootGroup, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(new PerspectiveCamera());
        GroupMouseControl.init(anchorPane, rootGroup);
        rootGroup.translateXProperty().set(WIDTH >> 1);
        rootGroup.translateYProperty().set(HEIGHT >> 1);
        rootGroup.translateZProperty().set(-600);
        borderPane.setCenter(scene);
    }


    public void closeApp() {
        Platform.exit();
    }
}
