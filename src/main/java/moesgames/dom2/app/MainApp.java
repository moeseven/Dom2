package moesgames.dom2.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import moesgames.dom2.gui.MagicPathBuilder;
import moesgames.dom2.models.PretenderConfiguration;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

    	MagicPathBuilder root = new MagicPathBuilder(new PretenderConfiguration());
        root.setSpacing(10);

//        Button btn = new Button("Click Me");
//        root.getChildren().add(btn);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
