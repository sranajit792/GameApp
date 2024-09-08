

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.util.Duration;

public class PingPongGame extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final double BALL_RADIUS = 10;
    private static final double PADDLE_WIDTH = 10;
    private static final double PADDLE_HEIGHT = 100;
    private static final double PADDLE_SPEED = 5;
    private static final double BALL_SPEED_X = 4;
    private static final double BALL_SPEED_Y = 3;

    private Circle ball;
    private Rectangle paddle1;
    private Rectangle paddle2;
    private double ballVelocityX = BALL_SPEED_X;
    private double ballVelocityY = BALL_SPEED_Y;
    private boolean upPressed, downPressed;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        VBox gameContent = getGameContent();
        root.setCenter(gameContent);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(e -> handleKeyPress(e));
        scene.setOnKeyReleased(e -> handleKeyRelease(e));

        primaryStage.setTitle("Ping Pong Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox getGameContent() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        // Create game components
        ball = new Circle(BALL_RADIUS, Color.RED);
        ball.setTranslateX(WIDTH / 2);
        ball.setTranslateY(HEIGHT / 2);

        paddle1 = new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLUE);
        paddle1.setTranslateX(30);
        paddle1.setTranslateY(HEIGHT / 2 - PADDLE_HEIGHT / 2);

        paddle2 = new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT, Color.GREEN);
        paddle2.setTranslateX(WIDTH - 30 - PADDLE_WIDTH);
        paddle2.setTranslateY(HEIGHT / 2 - PADDLE_HEIGHT / 2);

        // Create game logic
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            updateGame();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        root.getChildren().addAll(ball, paddle1, paddle2);
        return root;
    }

    private void updateGame() {
        // Update ball position
        ball.setTranslateX(ball.getTranslateX() + ballVelocityX);
        ball.setTranslateY(ball.getTranslateY() + ballVelocityY);

        // Ball collision with top and bottom walls
        if (ball.getTranslateY() - BALL_RADIUS < 0 || ball.getTranslateY() + BALL_RADIUS > HEIGHT) {
            ballVelocityY = -ballVelocityY;
        }

        // Ball collision with paddles
        if (ball.getTranslateX() - BALL_RADIUS < paddle1.getTranslateX() + PADDLE_WIDTH &&
            ball.getTranslateY() > paddle1.getTranslateY() &&
            ball.getTranslateY() < paddle1.getTranslateY() + PADDLE_HEIGHT) {
            ballVelocityX = -ballVelocityX;
        }

        if (ball.getTranslateX() + BALL_RADIUS > paddle2.getTranslateX() &&
            ball.getTranslateY() > paddle2.getTranslateY() &&
            ball.getTranslateY() < paddle2.getTranslateY() + PADDLE_HEIGHT) {
            ballVelocityX = -ballVelocityX;
        }

        // Ball out of bounds
        if (ball.getTranslateX() < 0 || ball.getTranslateX() > WIDTH) {
            // Reset ball position
            ball.setTranslateX(WIDTH / 2);
            ball.setTranslateY(HEIGHT / 2);
            ballVelocityX = -BALL_SPEED_X; // Reset ball direction
        }

        // Move paddles based on input
        if (upPressed && paddle1.getTranslateY() > 0) {
            paddle1.setTranslateY(paddle1.getTranslateY() - PADDLE_SPEED);
        }
        if (downPressed && paddle1.getTranslateY() < HEIGHT - PADDLE_HEIGHT) {
            paddle1.setTranslateY(paddle1.getTranslateY() + PADDLE_SPEED);
        }

        // Simple AI for paddle2
        if (ball.getTranslateY() > paddle2.getTranslateY() + PADDLE_HEIGHT / 2) {
            if (paddle2.getTranslateY() < HEIGHT - PADDLE_HEIGHT) {
                paddle2.setTranslateY(paddle2.getTranslateY() + PADDLE_SPEED / 2);
            }
        } else {
            if (paddle2.getTranslateY() > 0) {
                paddle2.setTranslateY(paddle2.getTranslateY() - PADDLE_SPEED / 2);
            }
        }
    }

    private void handleKeyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.UP) {
            upPressed = true;
        }
        if (e.getCode() == KeyCode.DOWN) {
            downPressed = true;
        }
    }

    private void handleKeyRelease(KeyEvent e) {
        if (e.getCode() == KeyCode.UP) {
            upPressed = false;
        }
        if (e.getCode() == KeyCode.DOWN) {
            downPressed = false;
        }
    }
}
