package org.example.carrental.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.view.components.TopBar;

/**
 * Main view (Home page) displaying car listings
 */
public class MainView extends BorderPane {

    private TopBar topBar;
    private VBox carListContainer;
    private ScrollPane scrollPane;

    public MainView() {
        setupUI();
    }

    private void setupUI() {
        // Set background color
        setStyle("-fx-background-color: #2e2e2e;");

        // Top bar
        topBar = new TopBar();
        setTop(topBar);

        // Main content area
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        // Page title
        Label titleLabel = new Label("Available Cars");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        // Car list container
        carListContainer = new VBox(15);
        carListContainer.setAlignment(Pos.TOP_CENTER);
        carListContainer.setPadding(new Insets(10));

        mainContent.getChildren().addAll(titleLabel, carListContainer);

        // Scroll pane for car listings
        scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #2e2e2e; -fx-background-color: #2e2e2e;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        setCenter(scrollPane);
    }

    public TopBar getTopBar() {
        return topBar;
    }

    public VBox getCarListContainer() {
        return carListContainer;
    }

    /**
     * Clears all car cards from the container
     */
    public void clearCarList() {
        carListContainer.getChildren().clear();
    }
}