package pl.edu.agh.metal.spw.kfilesviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(this.getClass().getResource("/MainView.fxml"));
        Parent mainNode = mainLoader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle(".k Files Viewer");
        primaryStage.setScene(new Scene(mainNode));
        primaryStage.show();
    }
}
