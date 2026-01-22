package org.example.carrental.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.view.components.TopBar;

/**
 * My Account view for end users
 */
public class MyAccountView extends BorderPane {

    private TopBar topBar;
    private TabPane tabPane;

    // Account Details tab
    private Label nameLabel;
    private Label emailLabel;
    private Label phoneLabel;
    private Label licenseLabel;
    private Label addressLabel;

    // My Bookings tab
    private VBox bookingListContainer;
    private ScrollPane bookingScrollPane;

    // My Invoices tab
    private VBox invoiceListContainer;
    private ScrollPane invoiceScrollPane;

    public MyAccountView() {
        setupUI();
    }

    private void setupUI() {
        setStyle("-fx-background-color: #2e2e2e;");

        // Top bar
        topBar = new TopBar();
        setTop(topBar);

        // Tab pane
        tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #2e2e2e;");

        // Account Details tab
        Tab accountDetailsTab = new Tab("Account Details");
        accountDetailsTab.setClosable(false);
        accountDetailsTab.setContent(createAccountDetailsPane());

        // My Bookings tab
        Tab myBookingsTab = new Tab("My Bookings");
        myBookingsTab.setClosable(false);
        myBookingsTab.setContent(createMyBookingsPane());

        // My Invoices tab
        Tab myInvoicesTab = new Tab("My Invoices");
        myInvoicesTab.setClosable(false);
        myInvoicesTab.setContent(createMyInvoicesPane());

        tabPane.getTabs().addAll(accountDetailsTab, myBookingsTab, myInvoicesTab);

        setCenter(tabPane);
    }

    private VBox createAccountDetailsPane() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.TOP_CENTER);
        container.setStyle("-fx-background-color: #2e2e2e;");

        Label titleLabel = new Label("Account Details");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        VBox detailsBox = new VBox(15);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        detailsBox.setMaxWidth(500);
        detailsBox.setPadding(new Insets(20));
        detailsBox.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 10; " +
                "-fx-border-color: #ff7c0a; -fx-border-width: 2; -fx-border-radius: 10;");

        nameLabel = createDetailLabel("Name: ");
        emailLabel = createDetailLabel("Email: ");
        phoneLabel = createDetailLabel("Phone: ");
        licenseLabel = createDetailLabel("Driver License: ");
        addressLabel = createDetailLabel("Address: ");

        detailsBox.getChildren().addAll(nameLabel, emailLabel, phoneLabel, licenseLabel, addressLabel);
        container.getChildren().addAll(titleLabel, detailsBox);

        return container;
    }

    private ScrollPane createMyBookingsPane() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #2e2e2e;");

        Label titleLabel = new Label("My Bookings");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        bookingListContainer = new VBox(15);
        bookingListContainer.setAlignment(Pos.TOP_CENTER);

        container.getChildren().addAll(titleLabel, bookingListContainer);

        bookingScrollPane = new ScrollPane(container);
        bookingScrollPane.setFitToWidth(true);
        bookingScrollPane.setStyle("-fx-background: #2e2e2e; -fx-background-color: #2e2e2e;");

        return bookingScrollPane;
    }

    private ScrollPane createMyInvoicesPane() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #2e2e2e;");

        Label titleLabel = new Label("My Invoices");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        invoiceListContainer = new VBox(15);
        invoiceListContainer.setAlignment(Pos.TOP_CENTER);

        container.getChildren().addAll(titleLabel, invoiceListContainer);

        invoiceScrollPane = new ScrollPane(container);
        invoiceScrollPane.setFitToWidth(true);
        invoiceScrollPane.setStyle("-fx-background: #2e2e2e; -fx-background-color: #2e2e2e;");

        return invoiceScrollPane;
    }

    private Label createDetailLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        label.setStyle("-fx-text-fill: white;");
        return label;
    }

    // Getters
    public TopBar getTopBar() {
        return topBar;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public Label getEmailLabel() {
        return emailLabel;
    }

    public Label getPhoneLabel() {
        return phoneLabel;
    }

    public Label getLicenseLabel() {
        return licenseLabel;
    }

    public Label getAddressLabel() {
        return addressLabel;
    }

    public VBox getBookingListContainer() {
        return bookingListContainer;
    }

    public VBox getInvoiceListContainer() {
        return invoiceListContainer;
    }
}