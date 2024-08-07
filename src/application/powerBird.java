package application;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Duration;

public class powerBird extends Application {
	public static double gravity = 0.25;
	public static double yvel = 0;
	public static double speed;
	public static double PHspeed;
	public static int score = 0;
	public static int powerDur;
	public static boolean scoredNow = true;
	public static boolean stopMoving = false;
	public static boolean powerUp = false;
	public static boolean pcooldown = false;
	public static int heightT = random();
	public static int yCoordPipeB = heightT + 150; // Calculates the height and location of the bottom pipe
	public static int height = 500 - yCoordPipeB;
	public static int heightT2 = random();
	public static int yCoordPipeB2 = heightT2 + 150; // Calculates the height and location of the bottom pipe
	public static int height2 = 500 - yCoordPipeB2;
	public static int heightT3 = random();
	public static int yCoordPipeB3 = heightT3 + 150; // Calculates the height and location of the bottom pipe
	public static int height3 = 500 - yCoordPipeB3;
	public static int easyHS;
	public static int normalHS;
	public static int hardHS;
	public static Pane rootGame = new Pane();
	public static Scene sceneGame = new Scene(rootGame, 500, 500);
	public static Circle bird;
	public static Rectangle[] pipeT = new Rectangle[3];
	public static Rectangle[] pipeB = new Rectangle[3];
	public static Text scoring = new Text("Score: " + score);
	public static Button restart = new Button();
	public static Rectangle frame = new Rectangle(20, 20, 50, 30);
	public static Rectangle pfill = new Rectangle(20, 20, 1, 30);
	public static Line pmeter = new Line(20, 20, 20, 50);
	public static Rectangle fill = new Rectangle(20, 20, 1, 30);
	public static Line meter = new Line(20, 20, 20, 50);
	public static TranslateTransition transition = new TranslateTransition();
	public static TranslateTransition transition1 = new TranslateTransition();

	// Method that will outputs the score to the screen
	public static void scored() {
		scoring.setText("Score: " + ++score);
	}

	// Method that gives a random number
	public static int random() {
		int x = (int) (Math.random() * 275 + 25);
		return x;
	}

	// Method that switches between if the player has scored or not
	public static void switching() {
		scoredNow = !scoredNow;
	}

	// Method that creates a timer for how long the power up will last
	public static void powerTime() {
		rootGame.getChildren().addAll(pfill, pmeter);
		transition.setNode(pmeter);
		transition.setDuration(Duration.millis(5000));
		transition.setToX(frame.getWidth());
		transition.play();
		powerUp = true;
		pcooldown = true;
	}

	// Method that creates a timer for the cooldown of the power up
	public static void cooldown() {
		rootGame.getChildren().removeAll(pfill, pmeter);
		rootGame.getChildren().addAll(fill, meter);
		transition1.setNode(meter);
		transition1.setDuration(Duration.millis(powerDur));
		transition1.setToX(frame.getWidth());
		transition1.play();
		pcooldown = true;
	}

	// Method that resets all the variables to their original state
	public static void restartVars() {
		gravity = 0.25;
		yvel = 0;
		score = 0;
		speed = 0;
		scoredNow = true;
		stopMoving = false;
		powerUp = false;
		pcooldown = false;
	}

	// Method that starts up the game
	public static void game(Stage primaryStage) {

		// Sets the properties of the game's pane and scene
		rootGame.setStyle("-fx-background-color: skyblue;");
		primaryStage.setScene(sceneGame);
		primaryStage.setTitle("Power Bird");
		primaryStage.show();

		// Creates the end pane and scene and sets its properties
		Pane rootEndScreen = new Pane();
		Scene gameOver = new Scene(rootEndScreen, 500, 500);
		rootEndScreen.setStyle("-fx-background-color: skyblue;");

		// Creates text which tells the player how to start the game
		Text starting = new Text("Press SPACE to Start");
		starting.setFont(Font.font("ARIAL", FontWeight.BOLD, 20));
		starting.setFill(Color.GREY);
		starting.setTranslateX(150);
		starting.setTranslateY(150);

		// Changes the properties of the scoring text
		scoring.setFont(Font.font("ARIAL", FontWeight.BOLD, 20));
		scoring.setFill(Color.GREY);
		scoring.setTranslateX(400);
		scoring.setTranslateY(25);
		scoring.setText("Score: " + score);

		// Creates text which tells the player that the game is over
		Text gameO = new Text("Game Over");
		gameO.setFont(Font.font("ARIAL", FontWeight.BOLD, 50));
		gameO.setFill(Color.RED);
		gameO.setTranslateX(123);
		gameO.setTranslateY(190);

		// Sets the properties of the restart button
		restart.setText("RESTART");
		restart.setFont(Font.font("ARIAL", FontWeight.BOLD, 25));
		restart.setTextFill(Color.BLACK);
		restart.setStyle("-fx-background-color: transparent");
		restart.setTranslateX(350);
		restart.setTranslateY(0);

		// Sets the properties of the power up bar and its filling
		frame.setFill(Color.SKYBLUE);
		frame.setStroke(Color.BLACK);
		frame.setStrokeWidth(3);
		pfill.setFill(Color.YELLOW);
		pfill.setStroke(Color.BLACK);
		pfill.setStrokeWidth(3);
		fill.setFill(Color.RED);
		fill.setStroke(Color.BLACK);
		fill.setStrokeWidth(3);

		// Creates a circle which is the bird
		bird = new Circle(0, 0, 10, Color.YELLOW);
		bird.setStroke(Color.BROWN);
		bird.setStrokeWidth(3);
		bird.setTranslateX(30);
		bird.setTranslateY(250);

		// Creates rectangles which are the pipes
		pipeT[0] = new Rectangle(0, 0, 20, heightT);
		pipeT[0].setFill(Color.DARKGREEN);
		pipeT[0].setStroke(Color.BLACK);
		pipeT[0].setStrokeWidth(3);

		pipeB[0] = new Rectangle(0, yCoordPipeB, 20, height);
		pipeB[0].setFill(Color.DARKGREEN);
		pipeB[0].setStroke(Color.BLACK);
		pipeB[0].setStrokeWidth(3);

		pipeT[1] = new Rectangle(0, 0, 20, heightT2);
		pipeT[1].setFill(Color.DARKGREEN);
		pipeT[1].setStroke(Color.BLACK);
		pipeT[1].setStrokeWidth(3);

		pipeB[1] = new Rectangle(0, yCoordPipeB2, 20, height2);
		pipeB[1].setFill(Color.DARKGREEN);
		pipeB[1].setStroke(Color.BLACK);
		pipeB[1].setStrokeWidth(3);

		pipeT[2] = new Rectangle(0, 0, 20, heightT3);
		pipeT[2].setFill(Color.DARKGREEN);
		pipeT[2].setStroke(Color.BLACK);
		pipeT[2].setStrokeWidth(3);

		pipeB[2] = new Rectangle(0, yCoordPipeB3, 20, height3);
		pipeB[2].setFill(Color.DARKGREEN);
		pipeB[2].setStroke(Color.BLACK);
		pipeB[2].setStrokeWidth(3);

		// Renders objects onto scene
		rootGame.getChildren().addAll(bird, pipeT[0], pipeB[0], pipeT[1], pipeB[1], pipeT[2], pipeB[2], starting,
				scoring, frame);

		// Moves the pipes to specific starting locations
		pipeT[0].setTranslateX(300);
		pipeB[0].setTranslateX(300);

		pipeT[1].setTranslateX(500);
		pipeB[1].setTranslateX(500);

		pipeT[2].setTranslateX(700);
		pipeB[2].setTranslateX(700);

		// An event handler where if the space bar has been pressed, make the bird jump
		sceneGame.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.SPACE) {
				// If the power up is active, make the jump of the bird smaller otherwise make
				// it the regular jump height
				if (powerUp) {
					yvel = -4;
				} else {
					yvel = -6.5;
				}
			}
		});

		// An event handler where if the x key has been pressed, activate the power up
		sceneGame.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			// If the cooldown is not active, allow the player to press the x key
			if (!pcooldown) {
				// If the x key has been pressed, slow down the speed of the pipes and activate
				// the powerTime method
				if (event.getCode() == KeyCode.X) {
					speed = 1;
					powerTime();
				}
			}
		});

		// Creates an animation timer where it acts like a loop and loops through the
		// code at 60 fps
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {

				// Text for the final score the player got
				Text fScored = new Text("Score: " + score);
				fScored.setFont(Font.font("ARIAL", FontWeight.BOLD, 50));
				fScored.setFill(Color.BLACK);
				fScored.setTranslateX(150);
				fScored.setTranslateY(260);

				// Fills the frame with color and returns the power up and the cooldown back to
				// its normal states
				fill.setWidth(meter.getTranslateX());
				pfill.setWidth(pmeter.getTranslateX());
				if (pfill.getWidth() == frame.getWidth()) {
					cooldown();
					pmeter.setTranslateX(frame.getTranslateX());
					powerUp = false;
				}
				if (fill.getWidth() == frame.getWidth()) {
					rootGame.getChildren().removeAll(meter, fill);
					pcooldown = false;
					meter.setTranslateX(frame.getTranslateX());
				}

				// Returns the speed to normal if the power up has expired
				if (!powerUp) {
					speed = PHspeed;
				}

				// Adds gravity to the bird
				bird.setTranslateY(bird.getTranslateY() + yvel);
				yvel += gravity;

				// Animates the pipes to move towards the bird at set speeds
				pipeT[0].setTranslateX(pipeT[0].getTranslateX() - speed);
				pipeB[0].setTranslateX(pipeB[0].getTranslateX() - speed);
				pipeT[1].setTranslateX(pipeT[1].getTranslateX() - speed);
				pipeB[1].setTranslateX(pipeB[1].getTranslateX() - speed);
				pipeT[2].setTranslateX(pipeT[2].getTranslateX() - speed);
				pipeB[2].setTranslateX(pipeB[2].getTranslateX() - speed);

				// Adds the correct amount of score no matter the speed of the pipes
				for (int i = 0; i < 3; i++) {
					if (pipeT[i].getTranslateX() <= 10 && pipeT[i].getTranslateX() >= 8 && scoredNow) {
						scored();
						scoredNow = false;
						System.out.println(score);
					}
					if (pipeT[i].getTranslateX() <= 5 && pipeT[i].getTranslateX() >= 1 && !scoredNow) {
						switching();
					}
				}

				// Randomizes pipes' height and translates it to the right of the screen
				if (pipeT[0].getTranslateX() < -20) {
					pipeT[0].setTranslateX(570);
					pipeB[0].setTranslateX(570);
					heightT = random();
					yCoordPipeB = heightT + 150;
					height = 500 - yCoordPipeB;
					pipeT[0].setHeight(heightT);
					pipeB[0].setY(0);
					pipeB[0].setTranslateY(yCoordPipeB);
					pipeB[0].setHeight(height);
				}

				if (pipeT[1].getTranslateX() < -20) {
					pipeT[1].setTranslateX(570);
					pipeB[1].setTranslateX(570);
					heightT2 = random();
					yCoordPipeB2 = heightT2 + 150;
					height2 = 500 - yCoordPipeB2;
					pipeT[1].setHeight(heightT2);
					pipeB[1].setY(0);
					pipeB[1].setTranslateY(yCoordPipeB2);
					pipeB[1].setHeight(height2);
				}

				if (pipeT[2].getTranslateX() < -20) {
					pipeT[2].setTranslateX(570);
					pipeB[2].setTranslateX(570);
					heightT3 = random();
					yCoordPipeB3 = heightT3 + 150;
					height3 = 500 - yCoordPipeB3;
					pipeT[2].setHeight(heightT3);
					pipeB[2].setY(0);
					pipeB[2].setTranslateY(yCoordPipeB3);
					pipeB[2].setHeight(height3);
				}

				// Ends the game if the bird hits the ceiling or the floor
				if (bird.getTranslateY() <= 0 || bird.getTranslateY() >= 480) {
					rootEndScreen.getChildren().clear();
					stop();
					stopMoving = true;
					primaryStage.setScene(gameOver);
					rootEndScreen.getChildren().addAll(gameO, fScored, restart);
					rootGame.getChildren().clear();
					pmeter.setTranslateX(frame.getTranslateX());
					transition.stop();
					meter.setTranslateX(frame.getTranslateX());
					transition1.stop();
				}

				// Collision detection between the bird and the top pipes and ends the game if
				// they intersect
				for (int i = 0; i < 3; i++) {
					if (pipeT[i].getBoundsInParent().intersects(bird.getBoundsInParent())) {
						rootEndScreen.getChildren().clear();
						stop();
						stopMoving = true;
						primaryStage.setScene(gameOver);
						rootEndScreen.getChildren().addAll(gameO, fScored, restart);
						rootGame.getChildren().clear();
						pmeter.setTranslateX(frame.getTranslateX());
						transition.stop();
						meter.setTranslateX(frame.getTranslateX());
						transition1.stop();
					}
				}

				// Collision detection between the bird and the bottom pipes and ends the game
				// if they intersect
				for (int j = 0; j < 3; j++) {
					if (pipeB[j].getBoundsInParent().intersects(bird.getBoundsInParent())) {
						rootEndScreen.getChildren().clear();
						stop();
						stopMoving = true;
						primaryStage.setScene(gameOver);
						rootEndScreen.getChildren().addAll(gameO, fScored, restart);
						rootGame.getChildren().clear();
						pmeter.setTranslateX(frame.getTranslateX());
						transition.stop();
						meter.setTranslateX(frame.getTranslateX());
						transition1.stop();
					}
				}
			}
		};

		// An event handler where if the space bar has been pressed, the game starts
		sceneGame.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			// If the bird is not moving, allow for the space bar to be pressed
			if (!stopMoving) {
				// If the space bar is pressed, start the game and remove the starting text
				if (event.getCode() == KeyCode.SPACE) {
					gameLoop.start();
					rootGame.getChildren().remove(starting);
				}
			}
		});
	}

	public void start(Stage startUp) {
		// Creating different panes to declare individual scenes for each (ex. root's
		// scene is scene1). This allows for button click transition between scenes.
		Pane root = new Pane();
		Pane root2 = new Pane();

		Pane rootInstructions = new Pane();

		// Setting Main Menu Scene and Mode Menu Scene.
		Scene scene1 = new Scene(root, 500, 500);
		root.setStyle("-fx-background-color: skyblue;");
		Scene scene2 = new Scene(root2, 500, 500);
		root2.setStyle("-fx-background-color: skyblue;");

		// Setting Instructions Scene.
		Scene Instructions = new Scene(rootInstructions, 500, 500);
		rootInstructions.setStyle("-fx-background-color: gray;");

		// Sets stage with Main Menu as its first scene.
		startUp.setScene(scene1);
		startUp.setTitle("POWER BIRD");

		// Decoration Variables + Bird Variables are shapes that create a grass
		// background, dirt underneath, a sun
		// and all body parts for the yellow bird displayed.
		Rectangle decorGround = new Rectangle(500, 20, Color.LIGHTGREEN);
		decorGround.setTranslateX(0);
		decorGround.setTranslateY(295);
		root.getChildren().add(decorGround);

		Rectangle decorDirt = new Rectangle(500, 50, Color.TAN);
		decorDirt.setTranslateX(0);
		decorDirt.setTranslateY(315);
		root.getChildren().add(decorDirt);

		Circle decorSun = new Circle(20, Color.YELLOW);
		decorSun.setTranslateX(450);
		decorSun.setTranslateY(200);
		root.getChildren().add(decorSun);

		Circle decorSunMiddle = new Circle(12, Color.ORANGE);
		decorSunMiddle.setTranslateX(450);
		decorSunMiddle.setTranslateY(200);
		root.getChildren().add(decorSunMiddle);

		Circle body = new Circle(50, Color.YELLOW);
		body.setTranslateX(250);
		body.setTranslateY(250);
		root.getChildren().add(body);

		Circle eye = new Circle(5, Color.BLACK);
		eye.setTranslateX(270);
		eye.setTranslateY(240);
		root.getChildren().add(eye);

		Polygon beak = new Polygon();
		beak.getPoints().addAll(70.0, 40.0, 60.0, 50.0, 50.0, 40.0);
		beak.setTranslateX(240);
		beak.setTranslateY(190);
		root.getChildren().add(beak);
		beak.setFill(Color.ORANGE);

		Polygon wing = new Polygon();
		wing.getPoints().addAll(140.0, 80.0, 120.0, 100.0, 100.0, 80.0);
		wing.setTranslateX(125);
		wing.setTranslateY(183);
		root.getChildren().add(wing);
		wing.setFill(Color.ORANGE);

		Rectangle leg1 = new Rectangle(3, 10, Color.BROWN);
		leg1.setTranslateX(250);
		leg1.setTranslateY(300);
		root.getChildren().add(leg1);

		Rectangle leg2 = new Rectangle(8, 3, Color.BROWN);
		leg2.setTranslateX(250);
		leg2.setTranslateY(310);
		root.getChildren().add(leg2);

		// Sets Title for Power Bird in scene1.
		Text powerBird = new Text("POWER BIRD");
		root.getChildren().add(powerBird);

		powerBird.setFont(Font.font("ARIAL", FontWeight.BOLD, 60));
		powerBird.setFill(Color.ROYALBLUE);

		powerBird.setX(50);
		powerBird.setY(65);

		// Declares the following buttons for scene transitions: Start and Quit
		Button start = new Button();
		start.setText("START");
		root.getChildren().add(start);

		start.setFont(Font.font("ARIAL", FontWeight.BOLD, 50));
		start.setTextFill(Color.ROYALBLUE);
		start.setStyle("-fx-background-color: transparent");

		start.setTranslateX(135);
		start.setTranslateY(100);

		Button quit = new Button();
		quit.setText("QUIT");
		root.getChildren().add(quit);

		quit.setFont(Font.font("ARIAL", FontWeight.BOLD, 30));
		quit.setTextFill(Color.ROYALBLUE);
		quit.setStyle("-fx-background-color: transparent");

		quit.setTranslateX(195);
		quit.setTranslateY(400);

		// When quit button is pressed, exit the game.
		quit.setOnAction(e -> Platform.exit());

		// When start button is pressed, transition to mode menu (scene2).
		start.setOnAction(e -> startUp.setScene(scene2));

		// All body parts for the red bird are declared for display + shapes to create
		// the sun.
		Circle body2 = new Circle(50, Color.RED);
		body2.setTranslateX(250);
		body2.setTranslateY(265);
		root2.getChildren().add(body2);

		Circle eye2 = new Circle(5, Color.BLACK);
		eye2.setTranslateX(270);
		eye2.setTranslateY(255);
		root2.getChildren().add(eye2);

		Polygon beak2 = new Polygon();
		beak2.getPoints().addAll(70.0, 40.0, 60.0, 50.0, 50.0, 40.0);
		beak2.setTranslateX(240);
		beak2.setTranslateY(205);
		beak2.setFill(Color.ORANGE);
		root2.getChildren().add(beak2);

		Polygon wing2 = new Polygon();
		wing2.getPoints().addAll(140.0, 80.0, 120.0, 100.0, 100.0, 80.0);
		wing2.setTranslateX(125);
		wing2.setTranslateY(198);
		wing2.setFill(Color.ORANGE);
		root2.getChildren().add(wing2);

		Circle decorSun2 = new Circle(20, Color.YELLOW);
		decorSun2.setTranslateX(450);
		decorSun2.setTranslateY(200);
		root2.getChildren().add(decorSun2);

		Circle decorSunMiddle2 = new Circle(12, Color.ORANGE);
		decorSunMiddle2.setTranslateX(450);
		decorSunMiddle2.setTranslateY(200);
		root2.getChildren().add(decorSunMiddle2);

		// Declares Instructions Button and adds it to scene2.
		Button instructions = new Button();
		instructions.setText("Instructions");
		root2.getChildren().add(instructions);

		instructions.setFont(Font.font("ARIAL", FontWeight.BOLD, 30));
		instructions.setTextFill(Color.ROYALBLUE);
		instructions.setStyle("-fx-background-color: transparent");

		instructions.setTranslateX(0);
		instructions.setTranslateY(0);

		// When Instructions Button is clicked, transition from scene2 to Instructions
		// Scene.
		instructions.setOnAction(e -> startUp.setScene(Instructions));

		// Button "Mode Menu" declared and set to Instructions Scene.
		Button goBackInstructions = new Button();
		goBackInstructions.setText("Mode Menu");
		rootInstructions.getChildren().add(goBackInstructions);

		goBackInstructions.setFont(Font.font("ARIAL", FontWeight.BOLD, 30));
		goBackInstructions.setTextFill(Color.ROYALBLUE);
		goBackInstructions.setStyle("-fx-background-color: transparent");

		goBackInstructions.setTranslateX(150);
		goBackInstructions.setTranslateY(0);

		// All instructions text is declared and is filled with string values that
		// describe how the game works.
		Text rule1 = new Text("1. Move the circle through the top and bottom walls to get more points");

		rule1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		rule1.setFill(Color.ORANGE);

		rule1.setTranslateX(25);
		rule1.setTranslateY(75);

		Text rule2 = new Text(
				"2. DO NOT hit the walls, ground or top of the screen with the circle.\n             if you touch these, you will lose and the game will end");

		rule2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		rule2.setFill(Color.ORANGE);

		rule2.setTranslateX(32);
		rule2.setTranslateY(125);

		Text rule3 = new Text(
				"3. The user has control of the circle only in the up and down directions\n                                       (no horizontal movement)");

		rule3.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		rule3.setFill(Color.ORANGE);

		rule3.setTranslateX(26);
		rule3.setTranslateY(175);

		Text rule4 = new Text("Aim for the highest score!");

		rule4.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		rule4.setFill(Color.ORANGE);

		rule4.setTranslateX(171);
		rule4.setTranslateY(250);

		Text controlTitle = new Text("Controls");
		controlTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		controlTitle.setFill(Color.ORANGE);

		controlTitle.setTranslateX(175);
		controlTitle.setTranslateY(320);

		Text control1 = new Text("Press SPACE to jump!");
		control1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		control1.setFill(Color.ORANGE);

		control1.setTranslateX(178);
		control1.setTranslateY(360);

		Text control2 = new Text("Press X to use the slow down POWER-UP!");
		control2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		control2.setFill(Color.ORANGE);

		control2.setTranslateX(110);
		control2.setTranslateY(400);

		// All instruction text is added to the Instructions Scene.
		rootInstructions.getChildren().addAll(rule1, rule2, rule3, rule4, controlTitle, control1, control2);

		// When Mode Menu is clicked, scene2 is set again.
		goBackInstructions.setOnAction(e -> startUp.setScene(scene2));

		// Makes a Button that displays the message "Main Menu" in the top corner of
		// scene2.
		Button goBack = new Button();
		goBack.setText("Main Menu");
		root2.getChildren().add(goBack);

		goBack.setFont(Font.font("ARIAL", FontWeight.BOLD, 30));
		goBack.setTextFill(Color.ROYALBLUE);
		goBack.setStyle("-fx-background-color: transparent");

		goBack.setTranslateX(305);
		goBack.setTranslateY(0);

		// When Main Menu button is pressed, go back to scene1 / Main Menu from scene2.
		goBack.setOnAction(e -> startUp.setScene(scene1));

		// Declares a button called Select Your Mode to prompt the player to pick
		// whether they want to play the easy, normal or hard mode of Power Bird.
		Button selectYourMode = new Button();
		selectYourMode.setText("Select Your Mode");
		root2.getChildren().add(selectYourMode);

		selectYourMode.setFont(Font.font("ARIAL", FontWeight.BOLD, 40));
		selectYourMode.setTextFill(Color.ROYALBLUE);
		selectYourMode.setStyle("-fx-background-color: transparent");

		selectYourMode.setTranslateX(50);
		selectYourMode.setTranslateY(100);

		// Easy button declared to transition to easy mode scene of the game.
		Button easy = new Button();
		easy.setText("EASY");
		root2.getChildren().add(easy);

		easy.setFont(Font.font("ARIAL", FontWeight.BOLD, 35));
		easy.setTextFill(Color.GREEN);
		easy.setStyle("-fx-background-color: transparent");

		easy.setTranslateX(-5);
		easy.setTranslateY(400);

		// When easy button is pressed, a new scene for the mode is created with the
		// specified properties of the game. For example, the speed at which the pipes
		// come at the player's bird is slower than that of normal or hard mode.
		easy.setOnAction(e -> {
			rootGame = new Pane();
			sceneGame = new Scene(rootGame, 500, 500);
			speed = 1.5;
			PHspeed = 1.5;
			powerDur = 10000;
			restartVars();
			game(startUp);
		});

		// Normal button declared to transition to normal mode scene of the game.
		Button normal = new Button();
		normal.setText("NORMAL");
		root2.getChildren().add(normal);

		normal.setFont(Font.font("ARIAL", FontWeight.BOLD, 35));
		normal.setTextFill(Color.ORANGE);
		normal.setStyle("-fx-background-color: transparent");

		normal.setTranslateX(147);
		normal.setTranslateY(400);

		// When normal button is pressed, a new scene for the mode is created with the
		// specified properties of the game. For example, the speed at which the pipes
		// come at the player's bird is faster than that of easy mode but slower than
		// that of hard mode.
		normal.setOnAction(e -> {
			rootGame = new Pane();
			sceneGame = new Scene(rootGame, 500, 500);
			speed = 2;
			PHspeed = 2;
			powerDur = 6000;
			restartVars();
			game(startUp);
		});

		// Button for hard mode is declared and displayed to the Mode Menu.
		Button hard = new Button();
		hard.setText("HARD");
		root2.getChildren().add(hard);

		hard.setFont(Font.font("ARIAL", FontWeight.BOLD, 35));
		hard.setTextFill(Color.RED);
		hard.setStyle("-fx-background-color: transparent");

		hard.setTranslateX(355);
		hard.setTranslateY(400);

		// When hard button is pressed, a new scene for the mode is created with the
		// specified properties of the game. For example, the speed at which the pipes
		// come at the player's bird is faster than that of normal and hard mode.
		hard.setOnAction(e -> {
			rootGame = new Pane();
			sceneGame = new Scene(rootGame, 500, 500);
			speed = 4;
			PHspeed = 4;
			powerDur = 4000;
			restartVars();
			game(startUp);
		});

		// Variable restart that was declared at the beginning of the program is coded
		// so that when it is clicked, it returns the player to the main menu to start
		// and choose their mode again.
		restart.setOnAction(e -> startUp.setScene(scene1));

		// Allows the stage to pop-up successfully.
		startUp.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}