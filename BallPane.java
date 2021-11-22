/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book_14;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

/**
 *
 * @author Simona
 */
public class BallPane extends Pane {

    private Timeline animation;

    //creating a layer in Scene2
    int ballPaneHight = 500;
    int ballPaneWidth = 670;
    private final Rectangle layer2 = new Rectangle(1, 2, ballPaneWidth, ballPaneHight);

    //creating an image object
    private final Image image1;
    private final ImageView img = new ImageView();

    //ball parameters
    public double radius = 25;
    private double x = radius, y = radius;
    private double dx = 2, dy = 2;
    private final Circle circle = new Circle(x, y, radius);

    //paddle parameters 
    int paddleWidth = 90;
    final double PADDLE_Y = ballPaneHight - radius;
    private Rectangle paddle = new Rectangle(200, PADDLE_Y, paddleWidth, 10);

    //score parameters
    private int currentScore = 20;
    Text scoreBoardMessage, scoreAsText;

    int timesBallAndPaddleIncreased = 0;
    int maximumScore = 40;
    int angleOfBallHitingWall = 45;
    int pointsFromMissedBalls = 0;
    int pointsFromBallHitsToPaddle = 0;
    int changingColorPoints = 0;
    int paddleHit = 0;
    int buttomHit = 0;
    int paddlePoints = 0;
    int missedPoints = 0;

    public BallPane() throws FileNotFoundException {
        //Background layer color
        layer2.setStroke(Color.CHARTREUSE);
        layer2.setFill(Color.CADETBLUE);

        //Background image
        this.image1 = new Image(new FileInputStream("D:\\JAVA CSC_239\\Book_14\\src\\book_14\\b1.gif"));
        img.setImage(image1);

        //Setting the position of the image
        img.setX(11);
        img.setY(11);
        img.setFitHeight(480);
        img.setFitWidth(650);

        //Ball colors
        circle.setFill(Color.DEEPPINK);
        circle.setStroke(Color.HOTPINK);
        circle.setStrokeWidth(2.0);

        //Paddle colors and additional parameters
        paddle.setStroke(Color.DARKORANGE);
        paddle.setFill(Color.GOLD);
        paddle.setArcWidth(5);
        paddle.setArcHeight(25);
        paddle.setOnMouseDragged(e -> {
            paddle.setX(e.getX());
        });

        //SCORE BOARD
        //1. Text parameters
        scoreBoardMessage = new Text("Your Score:");
        scoreBoardMessage.setFont(Font.font("Chiller", FontPosture.REGULAR, 33));
        scoreBoardMessage.setStroke(Color.DIMGRAY);
        scoreBoardMessage.setFill(Color.DIMGRAY);
        scoreBoardMessage.setLayoutX(190);
        scoreBoardMessage.setLayoutY(210);

        //2. Score parameters
        scoreAsText = new Text(String.valueOf(getCurrentScore()));
        scoreAsText.setFont(Font.font("Chiller", FontPosture.REGULAR, 40));
        scoreAsText.setStroke(Color.DIMGRAY);
        scoreAsText.setFill(Color.DIMGRAY);
        scoreAsText.setLayoutX(230);
        scoreAsText.setLayoutY(250);

        //Placing all the objects into pane
        getChildren().addAll(layer2, img, scoreBoardMessage, scoreAsText,
                circle, paddle);

        //Create an animation for moving the ball after the start game button will be clicked
        animation = new Timeline(new KeyFrame(Duration.millis(40), e2 -> moveBall()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    // Start animation
    public void play() {
        animation.play();
    }

    public void pause() {
        animation.pause();
    }

    public void increaseSpeed() {
        animation.setRate(animation.getRate() + 0.5);
    }

    public void decreaseSpeed() {
        animation.setRate(
                animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
    }

    public DoubleProperty rateProperty() {
        return animation.rateProperty();
    }

//Check boundaries for the ball
    protected void moveBall() {

        //Calculating the intersecting point position: 
        if (x < radius || x > ballPaneWidth - radius) {
            dx *= -1; // Change ball move direction
        }
        //Checking the bottom boundaries
        if (y < radius || y > ballPaneHight - radius) {
            if (y > 499 - radius && y > radius) {
                decrementScore();
                processScore();
                pointsFromMissedBalls += 1;
                calculateBallHits(pointsFromMissedBalls);
            }
            dy *= -1; // Change ball move direction
        }

        //The angle hiting the walls is 45 degrees (except the paddle)
        changeAngleOfBall(angleOfBallHitingWall);

        //Direction of ball movement in x and y axes
        x += dx;
        y += dy;
        circle.setCenterX(x);
        circle.setCenterY(y);

        //Method to give a score after ball hits the paddle
        hitThePaddle();
    }

    private void hitThePaddle() {
        //The ball touches the paddle 
        if ((y >= paddle.getY() - radius)
                && (x >= paddle.getX())
                && (x <= paddle.getX() + paddle.getWidth())) {
            dy *= -1;
            changeAngleOfBall(10 + Math.random() * 170);
            incrementScore();
            pointsFromBallHitsToPaddle -= 1;
            calculateBallHits(pointsFromBallHitsToPaddle);

            //get a current score
            int currentScore = getCurrentScore();
        }
    }

    private void changeAngleOfBall(double angle) {
        double speed = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double radians = angle * Math.PI / 180;

        dx = Math.signum(dx) * speed * Math.cos(radians);
        dy = Math.signum(dy) * speed * Math.sin(radians);

        //Direction of ball movement in x and y axes
        x += dx;
        y += dy;
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    private void calculateBallHits(int hit) {
        //Identify if the ball hit the paddle
        if (hit < 0) {
            paddleHit = hit;
            paddlePoints++;
            missedPoints = 0;
            if (paddlePoints % 5 == 0) {
                changingColorPoints = -5;
            }
        }

        //Identify if the ball hit the bottom
        if (hit > 0) {
            buttomHit = hit;
            missedPoints++;
            paddlePoints = 0;
        }

        if (missedPoints == 3) {
            incrementSize();
            missedPoints = 0;
            timesBallAndPaddleIncreased++;
        }
        if (changingColorPoints == -5) {
            changeColor();
            changingColorPoints = 0;
        }
        if (paddlePoints == 9 && 0 < timesBallAndPaddleIncreased) {
            increaseSpeed();
            decrementSize();
        }
    }

    //Changing a color of the ball
    private void changeColor() {
        circle.setFill(Color.rgb((int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256)));
    }

    //Increment the radius of ball and paddle side
    private void incrementSize() {
        radius += 1;
        paddleWidth += 10;
    }

    //Decrement the radius of ball and paddle side
    private void decrementSize() {
        radius -= 1;
        paddleWidth += 10;
    }

    private void incrementScore() {
        if (currentScore == maximumScore) {
            return;
        }
        currentScore++;
        scoreAsText.setText(String.valueOf(currentScore));
        processScore();
    }

    private void decrementScore() {
        if (currentScore == 0) {
            return;
        }
        currentScore--;
        scoreAsText.setText(String.valueOf(currentScore));
        processScore();
    }

    private int getCurrentScore() {
        return currentScore;
    }

    /* Process score: if current - activate getCurrentScore method;
    if reached maximum or minimum score - pause the game and send the results 
    the superclass to change the scene */
    private void processScore() {
        int score = getCurrentScore();

        //Winning situation
        if (score == maximumScore) {
            pause();
            sendMaxScore();
        }

        //Loosing situation         
        if (score == 0) {
            pause();
            sendMinScore();
        }
    }

    public int sendMaxScore() {
        return maximumScore;
    }

    public int sendMinScore() {
        return 0;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
