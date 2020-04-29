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
    private Group group;


    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        try {
            Data data = new Data(file);
            visualize(data);
        } catch (CustomException ex) {
            raiseErrorAlert(ex.getMessage());
        } catch (Exception ignored) {
        }
    }


    private void visualize(Data data) {
        data.getSolidElementDefList().forEach(solidElementDef -> {
            int[] indices = solidElementDef.getNodesCoordinatesIndices();
            float[] points = new float[24];

            int i = 0;
            for (int index : indices) {
                Point3D coords = data.getNodesCoordinates().get(index);
                points[i++] = (float) coords.getX();
                points[i++] = (float) coords.getY();
                points[i++] = (float) coords.getZ();
            }

            addHexahedronToGroup(points);
        });
    }


    private void addHexahedronToGroup(float[] points) {
        Hexahedron hexahedron = new Hexahedron(points);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.ROYALBLUE);
        hexahedron.setMaterial(material);
        group.getChildren().add(hexahedron);
    }


    private void raiseErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not load the file");
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    public void clearView() {
        group.getChildren().clear();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        group = new Group();
        SubScene scene = new SubScene(group, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(new PerspectiveCamera());
        GroupMouseControl.init(anchorPane, group);
        group.translateXProperty().set(WIDTH >> 1);
        group.translateYProperty().set(HEIGHT >> 1);
        group.translateZProperty().set(-600);
        borderPane.setCenter(scene);
    }


    public void closeApp() {
        Platform.exit();
    }
}
