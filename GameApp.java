

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        // Create tabs for each game
        Tab ticTacToeTab = new Tab("Tic Tac Toe");
        Tab pingPongTab = new Tab("Ping Pong");
        Tab sudokuTab = new Tab("Sudoku");

        // Create game content for each tab
        ticTacToeTab.setContent(new TicTacToeGame().getGameContent());
        pingPongTab.setContent(new PingPongGame().getGameContent());
        

        // Add tabs to the tab pane
        tabPane.getTabs().addAll(ticTacToeTab, pingPongTab);

        // Create a border pane to hold the tab pane
        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        // Create a scene and set it to the primary stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}