package chatapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ChatAppFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Sidebar (placeholder for contacts)
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #075e54;");
        Label contactsLabel = new Label("Chats");
        contactsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10;");
        sidebar.getChildren().add(contactsLabel);

        // Top bar
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #128c7e; -fx-padding: 10;");
        Label userLabel = new Label("User");
        userLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button settingsBtn = new Button("⋮");
        topBar.getChildren().addAll(userLabel, spacer, settingsBtn);

        // Chat area
        VBox chatArea = new VBox();
        chatArea.setStyle("-fx-background-color: #ece5dd; -fx-padding: 10;");
        chatArea.setSpacing(8);
        ScrollPane chatScroll = new ScrollPane(chatArea);
        chatScroll.setFitToWidth(true);
        chatScroll.setStyle("-fx-background: #ece5dd;");

        // Input area
        HBox inputBar = new HBox(8);
        inputBar.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");
        TextField inputField = new TextField();
        inputField.setPromptText("Type a message");
        inputField.setPrefWidth(400);
        Button emojiBtn = new Button("😊");
        Button attachBtn = new Button("📎");
        Button sendBtn = new Button("Send");
        inputBar.getChildren().addAll(emojiBtn, attachBtn, inputField, sendBtn);

        // Main layout
        BorderPane mainPane = new BorderPane();
        mainPane.setLeft(sidebar);
        VBox centerBox = new VBox(topBar, chatScroll, inputBar);
        VBox.setVgrow(chatScroll, Priority.ALWAYS);
        mainPane.setCenter(centerBox);

        // Send message action
        sendBtn.setOnAction(e -> {
            String msg = inputField.getText().trim();
            if (!msg.isEmpty()) {
                Label msgLabel = new Label(msg);
                msgLabel.setStyle(
                        "-fx-background-color: #dcf8c6; -fx-padding: 8 12 8 12; -fx-background-radius: 12; -fx-font-size: 14px;");
                chatArea.getChildren().add(msgLabel);
                inputField.clear();
            }
        });
        inputField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                sendBtn.fire();
        });

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("ChatApp - Modern UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
