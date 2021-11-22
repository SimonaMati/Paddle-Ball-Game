/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book_14;
/*Task: 
Create a "Paddle-ball for One" game - a rectangular "paddle" moves back and forth 
via mouse drag along the bottom of the pane; the bottom of the paddle should be 
about 1/2 the diameter of the ball off the bottom.If the ball connects with the 
paddle, then it bounces back into the pane space. 
1. The angle that the ball bounces should be logical, but not the same angle 
every time.  
2. If the ball misses the paddle, then the score is decremented by 1.  
3. The game ends when 20 points are lost. 
4. The game court should be longer than it is wide (or taller than it is wide, 
depending on how you look at it). 
5. A label or a text that displays the score that is 20 at game start, changes with
each point lost until it is zero at game end.
6. For every 10 paddle connections in a row, the ball moves faster and gets smaller 
if it got larger
7. For every 5 paddle connections in a row, the ball changes color
8. For every 3 paddle misses in a row, the paddle grows in length and the ball gets 
larger.

Simona Matiukaite
08/26/2021
 */

import java.io.FileInputStream;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;

/**
 *
 * @author Simona
 */
public class Paddleball_game extends Application {

    Stage window;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;

    @Override
    //creating Stage 
    public void start(Stage primaryStage) throws Exception {

//SCENE1 ////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
        window = primaryStage;
        Pane layout1 = new Pane();

        Rectangle layer1 = new Rectangle(1, 2, 670, 500);
        layer1.setStroke(Color.CHARTREUSE);
        layer1.setFill(Color.CADETBLUE);
        layout1.getChildren().add(layer1);

        //Background image
        Image image1 = new Image(new FileInputStream("D:\\JAVA CSC_239\\Book_14\\src\\book_14\\start.gif"));
        ImageView pav1 = new ImageView();
        pav1.setImage(image1);
        layout1.getChildren().add(pav1);

        //Setting the position of the image
        pav1.setX(11);
        pav1.setY(11);
        pav1.setFitHeight(480);
        pav1.setFitWidth(650);

        //Welcoming text
        Text welcome_message = new Text("Welcome to the Paddle-ball game!");
        welcome_message.setFont(Font.font("Chiller", FontPosture.REGULAR, 35));
        welcome_message.setFill(Color.DEEPPINK);
        welcome_message.setStroke(Color.DEEPPINK);
        welcome_message.setLayoutX(190);
        welcome_message.setLayoutY(80);

        Button start_button = new Button("Start the game");
        start_button.setLayoutX(280);
        start_button.setLayoutY(380);
        start_button.setPrefHeight(30);

        //Button parameters: YELLOW 1
        start_button.setPrefWidth(140);
        start_button.setStyle("-fx-background-color: \n"
                + "        linear-gradient(#ffd65b, #e68400),\n"
                + "        linear-gradient(#ffef84, #f2ba44),\n"
                + "        linear-gradient(#ffea6a, #efaa22),\n"
                + "        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n"
                + "        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n"
                + "    -fx-background-radius: 30;\n"
                + "    -fx-background-insets: 0,1,2,3,0;\n"
                + "    -fx-text-fill: #654b00;\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-font-size: 14px;\n"
                + "    -fx-padding: 10 20 10 20;");

        //Activating a start a game button
        start_button.setOnAction(e -> onStartClick(e));

        layout1.getChildren().addAll(welcome_message, start_button);
        scene1 = new Scene(layout1, 673, 504);

//SCENE2/////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////       
        BallPane ballPane = new BallPane();
        if (ballPane.sendMaxScore() == 22) {
            primaryStage.setScene(scene3);
        }
        if (ballPane.sendMinScore() == 0) {
            primaryStage.setScene(scene4);
        }

      scene2 = new Scene(ballPane, 673, 500);

//SCENE3: winning ////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
        Pane layout3 = new Pane();
        
        Rectangle layer3 = new Rectangle(1, 2, 670, 500);
        layer3.setStroke(Color.CHARTREUSE);
        layer3.setFill(Color.CADETBLUE);
        layout3.getChildren().add(layer3);

        //Image image2 = new Image(new FileInputStream("D:\\JAVA CSC_239\\Book_14\\src\\icons\\winning3.gif"));
        Image image3 = new Image(new FileInputStream("D:\\JAVA CSC_239\\Book_14\\src\\book_14\\win1.gif"));
        //Image image2 = new Image(new FileInputStream("D:\\JAVA CSC_239\\Book_14\\src\\icons\\winning2.gif"));
        ImageView pav3 = new ImageView();
        pav3.setImage(image3);

        //setting the position of the image
        pav3.setX(11);
        pav3.setY(11);
        pav3.setFitHeight(480);
        pav3.setFitWidth(650);

        Text winning_message = new Text("It's time to party!!");
        winning_message.setFont(Font.font("Chiller", FontPosture.REGULAR, 33));
        winning_message.setStroke(Color.BLACK);
        winning_message.setLayoutX(140);
        winning_message.setLayoutY(80);

        //Button2: PLAY AGAIN
        Button play_again_button = new Button("Play again");
        play_again_button.setLayoutX(190);
        play_again_button.setLayoutY(410);
        play_again_button.setPrefWidth(120);
        play_again_button.setPrefHeight(30);
        play_again_button.setStyle("-fx-background-color: \n"
                + "#c3c4c4,\n" + "linear-gradient(#d6d6d6 50%, white 100%),\n"
                + "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n"
                + "-fx-background-radius: 30;\n" + "-fx-background-insets: 0,1,1;\n"
                + "-fx-text-fill: black;\n"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        play_again_button.setOnAction(e -> onStartClick(e));
 
        //Button2: QUIT
        Button quit_button = new Button("Quit");
        quit_button.setLayoutX(390);
        quit_button.setLayoutY(410);
        quit_button.setPrefWidth(120);
        quit_button.setPrefHeight(30);
        quit_button.setStyle("-fx-background-color: \n"
                + "#c3c4c4,\n" + "linear-gradient(#d6d6d6 50%, white 100%),\n"
                + "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n"
                + "-fx-background-radius: 30;\n" + "-fx-background-insets: 0,1,1;\n"
                + "-fx-text-fill: black;\n"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        QuitHandlerClass handler3 = new QuitHandlerClass();
        quit_button.setOnAction(handler3);
        
        // Place all the objects from Scene3 into pane
        layout3.getChildren().addAll(pav3, winning_message, play_again_button,
                quit_button);

        scene3 = new Scene(layout3, 673, 504);

//SCENE4: loosing ///////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
        Pane layout4 = new Pane();
        Rectangle layer4 = new Rectangle(1, 2, 670, 500);
        layer4.setStroke(Color.CHARTREUSE);
        layer4.setFill(Color.CADETBLUE);
        layout4.getChildren().add(layer3);

        Image image4 = new Image(new FileInputStream("D:\\JAVA CSC_239\\Book_14\\src\\book_14\\loosing2.gif"));
        ImageView pav4 = new ImageView();
        pav4.setImage(image4);
        // pav1.setPreserveRatio(true);        
        //setting the position of the image
        
        pav4.setX(11);
        pav4.setY(11);
        pav4.setFitHeight(480);
        pav4.setFitWidth(650);

        Text loosing_message = new Text("The ball just walked away...");
        loosing_message.setFont(Font.font("Chiller", FontPosture.REGULAR, 33));
        loosing_message.setStroke(Color.BLACK);
        loosing_message.setLayoutX(140);
        loosing_message.setLayoutY(80);

        //Button2: PLAY AGAIN
        Button play__again_button = new Button("Play again");
        play__again_button.setLayoutX(190);
        play__again_button.setLayoutY(410);
        play__again_button.setPrefWidth(120);
        play__again_button.setPrefHeight(30);
        play__again_button.setStyle("-fx-background-color: \n"
                + "#c3c4c4,\n" + "linear-gradient(#d6d6d6 50%, white 100%),\n"
                + "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n"
                + "-fx-background-radius: 30;\n" + "-fx-background-insets: 0,1,1;\n"
                + "-fx-text-fill: black;\n"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        play__again_button.setOnAction(e -> onStartClick(e));

        //Button2: QUIT
        Button quit__button = new Button("Quit");
        quit__button.setLayoutX(390);
        quit__button.setLayoutY(410);
        quit__button.setPrefWidth(120);
        quit__button.setPrefHeight(30);
        quit__button.setStyle("-fx-background-color: \n"
                + "#c3c4c4,\n" + "linear-gradient(#d6d6d6 50%, white 100%),\n"
                + "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n"
                + "-fx-background-radius: 30;\n" + "-fx-background-insets: 0,1,1;\n"
                + "-fx-text-fill: black;\n"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        QuitHandlerClass handler4 = new QuitHandlerClass();
        quit_button.setOnAction(handler4);

        // Place all the objects from Scene4 into this pane
        layout4.getChildren().addAll(pav4, loosing_message, play__again_button,
                quit__button);
        scene4 = new Scene(layout4, 673, 504);

//setting the stage
        primaryStage.setTitle("Simona Matiukaite. \"Paddle-ball for One\" ");
        primaryStage.setScene(scene2);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Must request focus after the primary stage is displayed
      //  ballPane.requestFocus();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private static class LayoutSample {
       public LayoutSample() {
      }
    }

    protected void onStartClick(Event e) {
        window.setScene(scene2);
    }
}

class QuitHandlerClass implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
        System.exit(0);
    }
}
