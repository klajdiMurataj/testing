package org.example.carrental;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Manages navigation between different views in the application
 */
public class AppNavigator {

    private static AppNavigator instance;
    private Stage primaryStage;
    private Scene scene;

    private AppNavigator() {
    }

    /**
     * Gets the singleton instance
     */
    public static AppNavigator getInstance() {
        if (instance == null) {
            instance = new AppNavigator();
        }
        return instance;
    }

    /**
     * Initializes the navigator with the primary stage
     */
    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Navigates to a new view
     */
    public void navigateTo(Pane view) {
        if (scene == null) {
            scene = new Scene(view, 1200, 800);
            primaryStage.setScene(scene);
        } else {
            scene.setRoot(view);
        }
    }

    /**
     * Gets the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Gets the current scene
     */
    public Scene getScene() {
        return scene;
    }
}