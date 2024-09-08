import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {

    private Button[][] buttons = new Button[3][3];
    private boolean xTurn = true; // true if X's turn, false if O's turn

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = getGameContent();
        Scene scene = new Scene(root, 330, 330);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox getGameContent() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setPrefWidth(100);
                button.setPrefHeight(100);
                button.setOnAction(e -> handleButtonClick(button));
                gridPane.add(button, j, i);
                buttons[i][j] = button;
            }
        }

        root.getChildren().add(gridPane);
        return root;
    }

    private void handleButtonClick(Button button) {
        if (!button.getText().isEmpty()) {
            return; // Button already clicked
        }

        if (xTurn) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        if (checkForWin()) {
            System.out.println((xTurn ? "X" : "O") + " wins!");
            disableAllButtons();
        } else if (isBoardFull()) {
            System.out.println("It's a draw!");
        } else {
            xTurn = !xTurn; // Switch turn
        }
    }

    private boolean checkForWin() {
        String[] lines = {
            // Horizontal
            buttons[0][0].getText() + buttons[0][1].getText() + buttons[0][2].getText(),
            buttons[1][0].getText() + buttons[1][1].getText() + buttons[1][2].getText(),
            buttons[2][0].getText() + buttons[2][1].getText() + buttons[2][2].getText(),
            // Vertical
            buttons[0][0].getText() + buttons[1][0].getText() + buttons[2][0].getText(),
            buttons[0][1].getText() + buttons[1][1].getText() + buttons[2][1].getText(),
            buttons[0][2].getText() + buttons[1][2].getText() + buttons[2][2].getText(),
            // Diagonal
            buttons[0][0].getText() + buttons[1][1].getText() + buttons[2][2].getText(),
            buttons[0][2].getText() + buttons[1][1].getText() + buttons[2][0].getText()
        };

        String player = xTurn ? "X" : "O";
        String winLine = player + player + player;

        for (String line : lines) {
            if (line.equals(winLine)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }
}
