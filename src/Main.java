import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;

public class Main extends Application {
  // Sets windows width and height
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 500;
  public static int numberOfLines = 0;
  public static int displayedQuestion = 0;
  public static int score = 0;
  public static String user_name_gloal;
  @Override
  public void start(Stage stage) {

    final Color BACKGROUND_COLOR = Color.rgb(33, 33, 33);
    final String TITLE_TEXT = "Quiz App";


    // Sets the default placement of the window on the screen when app is run
    final int WINDOW_PLACEMENT_DEFAULT_X = 250;
    final int WINDOW_PLACEMENT_DEFAULT_Y = 100;

    // To store Questions and Answers from file Questions.txt
    ArrayList<String> qAndAs = new ArrayList<>();

    /*
     * 
     * MAIN MENU 
     *           
     */
    
    Group mainMenu = new Group();
    Scene scene = new Scene(mainMenu, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND_COLOR);

    // Setting up the Header Text
    Text header = new Text("Quiz App");
    header.setX((WINDOW_WIDTH / 2) - 100);
    header.setY(50);
    header.setFont(Font.font("Verdana", 50));
    header.setFill(Color.BEIGE);
    mainMenu.getChildren().add(header);

    /* Buttons */

    // Start Button
    Button startB = new Button();
    startB.setText("Start Quiz");
    // button events are handled in a separate section below
    startB.setLayoutX(320);
    startB.setLayoutY(150);
    startB.setMinWidth(200);
    startB.setMinHeight(50);
    mainMenu.getChildren().add(startB);

    // Admin Button
    Button adminB = new Button();
    adminB.setText("Admin Login");
    // button events are handled in a separate section below
    adminB.setLayoutX(320);
    adminB.setLayoutY(250);
    adminB.setMinWidth(200);
    adminB.setMinHeight(50);
    mainMenu.getChildren().add(adminB);

    // Show Results button
    Button resultB = new Button("Show Results");
    resultB.setLayoutX(320);
    resultB.setLayoutY(350);
    resultB.setMinWidth(200);
    resultB.setMinHeight(50);
    mainMenu.getChildren().add(resultB);

    /*
     * 
     * Quiz Instructions Window
     * 
     */

    // layouts define the positions of both the text field and the text
    final int USER_NAME_INPUT_LAYOUT_X = 230;
    final int USER_NAME_INPUT_LAYOUT_Y = 50;
    Group quizMenu = new Group();
    Text quizText = new Text("Enter your name:");
    quizText.setFont(Font.font("Verdana", 20));
    quizText.setFill(Color.BEIGE);
    quizText.setLayoutX(USER_NAME_INPUT_LAYOUT_X);
    quizText.setLayoutY(USER_NAME_INPUT_LAYOUT_Y);
    quizMenu.getChildren().add(quizText);

    TextField userName = new TextField();
    userName.setLayoutX(USER_NAME_INPUT_LAYOUT_X + 185);
    userName.setLayoutY(USER_NAME_INPUT_LAYOUT_Y - 20);
    quizMenu.getChildren().add(userName);

    final int QUIZ_MENU_INSTRUCTIONS_LAYOUT_X = 100;
    final int QUIZ_MENU_INSTRUCTIONS_LAYOUT_Y = 80;
    TextArea instructions = new TextArea();
    instructions.setEditable(false);
    instructions.setWrapText(true);
    instructions.setLayoutX(QUIZ_MENU_INSTRUCTIONS_LAYOUT_X);
    instructions.setLayoutY(QUIZ_MENU_INSTRUCTIONS_LAYOUT_Y);
    instructions.setMinWidth(600);
    instructions.setMinHeight(300);
    quizMenu.getChildren().add(instructions);
    
    final int QUIZ_MENU_INSTRUCTIONS_BUTTON_LAYOUT_X = 200;
    final int QUIZ_MENU_INSTRUCTIONS_BUTTON_LAYOUT_Y = 400;
    Button backBQ = new Button("Back to Menu");
    backBQ.setLayoutX(QUIZ_MENU_INSTRUCTIONS_BUTTON_LAYOUT_X);
    backBQ.setLayoutY(QUIZ_MENU_INSTRUCTIONS_BUTTON_LAYOUT_Y);
    backBQ.setMinWidth(200);
    backBQ.setMinHeight(50);
    quizMenu.getChildren().add(backBQ);

    Button startQuizB = new Button("Start Quiz");
    startQuizB.setLayoutX(QUIZ_MENU_INSTRUCTIONS_BUTTON_LAYOUT_X + 205);
    startQuizB.setLayoutY(QUIZ_MENU_INSTRUCTIONS_BUTTON_LAYOUT_Y);
    startQuizB.setMinWidth(200);
    startQuizB.setMinHeight(50);
    quizMenu.getChildren().add(startQuizB);

    Text notifyUser = new Text();

    /*
     * 
     * Actual Quiz Area
     * 
     */
    Group quizArea = new Group();
    final int QUIZ_AREA_LAYOUT_X = 200;
    final int QUIZ_AREA_LAYOUT_Y = 200 - 20;
    Text questionNumber = new Text("Question 1:");
    questionNumber.setFont(Font.font("Verdana", 20));
    questionNumber.setFill(Color.BEIGE);
    questionNumber.setLayoutX(QUIZ_AREA_LAYOUT_X);
    questionNumber.setLayoutY(QUIZ_AREA_LAYOUT_Y);
    quizArea.getChildren().add(questionNumber);

    TextArea questionArea = new TextArea();
    questionArea.setLayoutX(QUIZ_AREA_LAYOUT_X);
    questionArea.setLayoutY(QUIZ_AREA_LAYOUT_Y + 10);
    questionArea.setMaxWidth(400);
    questionArea.setMaxHeight(50);
    questionArea.setEditable(false);
    quizArea.getChildren().add(questionArea);

    Text answerBoxText = new Text("Answer:");
    answerBoxText.setFont(Font.font("Verdana", 20));
    answerBoxText.setFill(Color.BEIGE);
    answerBoxText.setLayoutX(QUIZ_AREA_LAYOUT_X);
    answerBoxText.setLayoutY(QUIZ_AREA_LAYOUT_Y + 100 - 5);
    quizArea.getChildren().add(answerBoxText);

    TextField answerBox = new TextField();
    answerBox.setLayoutX(QUIZ_AREA_LAYOUT_X + 100 - 10);
    answerBox.setLayoutY(QUIZ_AREA_LAYOUT_Y + 100 - 20 - 5);
    answerBox.setMinWidth(310);
    quizArea.getChildren().add(answerBox);

    Button nextQB = new Button("Next Question");
    nextQB.setLayoutX(QUIZ_AREA_LAYOUT_X + 100);
    nextQB.setLayoutY(QUIZ_AREA_LAYOUT_Y + 130);
    nextQB.setMinWidth(200);
    nextQB.setMinHeight(50);
    quizArea.getChildren().add(nextQB);


    /*
     * 
     * Admin Window
     * 
     */
    Group adminMenu = new Group();
    
    // Question Text Field (This controls the relative positions of almost all other nodes)
    final int ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_X = 200;
    final int ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_Y = (WINDOW_HEIGHT / 2) - 50;
    final int ADMIN_WINDOW_QUESTION_TEXTFIELD_MIN_WIDTH = 250;
    TextField inputQuestionField = new TextField();
    inputQuestionField.setLayoutX(ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_X);
    inputQuestionField.setLayoutY(ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_Y);
    inputQuestionField.setMinWidth(ADMIN_WINDOW_QUESTION_TEXTFIELD_MIN_WIDTH);
    inputQuestionField.setPromptText("2(2+4x5)");
    adminMenu.getChildren().add(inputQuestionField);

    // Question Text Description
    Text adminQuestionText = new Text("Enter new question:");
    adminQuestionText.setFill(Color.BEIGE);
    adminQuestionText.setFont(Font.font("Verdana", 20));
    adminQuestionText.setLayoutX(ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_X);
    adminQuestionText.setLayoutY(ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_Y - 5);
    adminMenu.getChildren().add(adminQuestionText);

    // Answer Field
    final int ADMIN_WINNDOW_ANSWER_TEXTFIELD_LAYOUT_X = ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_X + ADMIN_WINDOW_QUESTION_TEXTFIELD_MIN_WIDTH + 10;
    final int ADMIN_WINNDOW_ANSWER_TEXTFIELD_LAYOUT_Y = ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_Y;
    TextField inputAnswerField = new TextField();
    inputAnswerField.setLayoutX(ADMIN_WINNDOW_ANSWER_TEXTFIELD_LAYOUT_X);
    inputAnswerField.setLayoutY(ADMIN_WINNDOW_ANSWER_TEXTFIELD_LAYOUT_Y);
    adminMenu.getChildren().add(inputAnswerField);

    // Answer Text
    Text adminAnswerText = new Text("Answer:");
    adminAnswerText.setFill(Color.BEIGE);
    adminAnswerText.setFont(Font.font("Verdana", 20));
    adminAnswerText.setLayoutX(ADMIN_WINNDOW_ANSWER_TEXTFIELD_LAYOUT_X);
    adminAnswerText.setLayoutY(ADMIN_WINNDOW_ANSWER_TEXTFIELD_LAYOUT_Y - 5);
    adminMenu.getChildren().add(adminAnswerText);

    // Confirmation Text
    Text confirmation = new Text();

    /* Buttons */
    final int ADMIN_WINDOW_BUTTON_LAYOUT_Y = ADMIN_WINDOW_QUESTION_TEXTFIELD_LAYOUT_Y + 30;
    // Submit button
    Button submitQuestion = new Button("Submit");
    submitQuestion.setLayoutX((WINDOW_WIDTH / 2) + 100);
    submitQuestion.setLayoutY(ADMIN_WINDOW_BUTTON_LAYOUT_Y);
    adminMenu.getChildren().add(submitQuestion);

    // Cancel button (goes back to main menu)
    Button backBA = new Button("Cancel");
    backBA.setLayoutX((WINDOW_WIDTH / 2) - 100);
    backBA.setLayoutY(ADMIN_WINDOW_BUTTON_LAYOUT_Y);
    adminMenu.getChildren().add(backBA);

    /*
     * 
     * Results Window
     * 
     */
    Group resultMenu = new Group();
    
    TextArea resultTextFromFile = new TextArea();
    final int RESULT_WINDOW_TEXTAREA_WIDTH = 400;
    final int RESULT_WINDOW_TEXTAREA_HEIGHT = 200;
    final int RESULT_WINDOW_TEXTAREA_LAYOUT_X = (WINDOW_WIDTH / 2) - (RESULT_WINDOW_TEXTAREA_WIDTH / 2);
    final int RESULT_WINDOW_TEXTAREA_LAYOUT_Y = (WINDOW_HEIGHT / 2) - (RESULT_WINDOW_TEXTAREA_HEIGHT / 2);
    resultTextFromFile.setEditable(false);
    resultTextFromFile.setWrapText(true);
    resultTextFromFile.setPrefRowCount(50);
    resultTextFromFile.setMaxWidth(RESULT_WINDOW_TEXTAREA_WIDTH);
    resultTextFromFile.setMaxHeight(RESULT_WINDOW_TEXTAREA_HEIGHT);
    resultTextFromFile.setLayoutX(RESULT_WINDOW_TEXTAREA_LAYOUT_X);
    resultTextFromFile.setLayoutY(RESULT_WINDOW_TEXTAREA_LAYOUT_Y);
    resultMenu.getChildren().add(resultTextFromFile);

    Button backBR = new Button("Go Back to Main Menu");
    final int RESULT_WINDOW_BACKBUTTON_WIDTH = 140;
    final int RESULT_WINDOW_BACKBUTTON_HEIGHT = 20;
    backBR.setLayoutX((WINDOW_WIDTH / 2) - (RESULT_WINDOW_BACKBUTTON_WIDTH / 2));
    backBR.setLayoutY(RESULT_WINDOW_BACKBUTTON_HEIGHT + 100);
    resultMenu.getChildren().add(backBR);

    //////////////////////////
    //*** Button Actions ***//
    //////////////////////////
    adminB.setOnAction(e -> onAdminClicked(scene, adminMenu));
    startB.setOnAction(e -> onStartClicked(scene, quizMenu, instructions, mainMenu));
    backBQ.setOnAction(e -> onBackClicked(scene, mainMenu));
    backBA.setOnAction(e -> onBackClicked(scene, mainMenu));
    submitQuestion.setOnAction(e -> onSubmitClicked(inputQuestionField, inputAnswerField, adminMenu, confirmation));
    resultB.setOnAction(e -> onResultClicked(scene, resultMenu, resultTextFromFile));
    backBR.setOnAction(e -> onBackClicked(scene, mainMenu));
    startQuizB.setOnAction(e -> onStartQuizClicked(scene, quizMenu, quizArea, userName, notifyUser, qAndAs, nextQB, questionArea, mainMenu, answerBox, questionNumber));

    stage.setTitle(TITLE_TEXT);
    stage.setResizable(false);
    stage.setScene(scene);
    stage.setX(WINDOW_PLACEMENT_DEFAULT_X);
    stage.setY(WINDOW_PLACEMENT_DEFAULT_Y);
    stage.show();
  }

  public static void onStartClicked(Scene scene, Group quizMenu, TextArea instructions, Group mainMenu) {
    File fileChecker = new File("Questions.txt");
    if (fileChecker.exists()) {
      try {
        // First, calculate the number of questions
        numberOfLines = 0;
        Scanner questions = new Scanner(new FileInputStream("Questions.txt"));
        while (questions.hasNextLine()) {
          numberOfLines++;
          questions.nextLine();
        }
        questions.close();
        // Then, write the contents of the instructions on the TextArea
        File file = new File("instructions.txt");
        if (file.createNewFile())
          System.out.println("File created: " + file.getName());
        Scanner myfile = new Scanner(new FileInputStream("instructions.txt"));
        instructions.clear();
        boolean addedQNumber = false;
        while (myfile.hasNextLine()) {
          if (!addedQNumber) {
            // A stupid way to add number of questions to the instructions
            for (int i = 0; i < 6; i++) {
              instructions.appendText(myfile.next() + " ");
            }
            instructions.appendText(String.valueOf(numberOfLines) + " ");
            myfile.next();
            for (int i = 0; i < 4; i++) {
              instructions.appendText(myfile.next() + " ");
            }
            instructions.appendText("\n");
            for (int i = 0; i < 7; i++) {
              instructions.appendText(myfile.next() + " ");
            }
            instructions.appendText(String.valueOf(numberOfLines) + " ");
            myfile.next();
            for (int i = 0; i < 11; i++) {
              instructions.appendText(myfile.next() + " ");
            }
            addedQNumber = true;
          }
          instructions.appendText(myfile.nextLine() + "\n");
        }
        myfile.close();
      } catch (IOException e) {
        System.out.println("An error occured");
        System.out.println(e.getStackTrace());
      } catch (Exception ex) {
        System.out.println("Error");
        System.out.println(ex.getMessage());
      }
      // System.out.println(numberOfLines); // Prints the number of lines from Questions.txt
      scene.setRoot(quizMenu);
    } else {
      Group fileNotFoundMenu = new Group();
      Text text = new Text("Questions.txt file does not exist. Ask admin to add questions.");
      text.setFont(Font.font("Verdana", 30));
      text.setFill(Color.BEIGE);
      text.setWrappingWidth(600);
      text.setTextAlignment(TextAlignment.CENTER);
      text.setLayoutX((WINDOW_WIDTH - 600) / 2);
      text.setLayoutY((WINDOW_HEIGHT / 2) - 20);
      fileNotFoundMenu.getChildren().add(text);

      Button back = new Button("Back To Menu");
      back.setLayoutX((WINDOW_WIDTH / 2) - (200 / 2));
      back.setLayoutY(WINDOW_HEIGHT - 200);
      back.setMinWidth(200);
      back.setMinHeight(50);
      fileNotFoundMenu.getChildren().add(back);
      back.setOnAction(e -> onBackClicked(scene, mainMenu));
      scene.setRoot(fileNotFoundMenu);
    }
  }

  public static void onAdminClicked(Scene scene, Parent adminMenu) {
    // System.out.println("Admin Button Clicked!");
    scene.setRoot(adminMenu);
  }

  public static void onBackClicked(Scene scene, Parent mainMenu) {
    // System.out.println("Back Button Clicked!");
    scene.setRoot(mainMenu);
  }

  public static void onSubmitClicked(TextField inputQuestionField, TextField inputAnswerField, Group adminMenu, Text text) {
    adminMenu.getChildren().remove(text); // Remove previous text
    String question = inputQuestionField.getText().trim();
    String answer = inputAnswerField.getText().trim();
    if (question.length() <= 0 || answer.length() <= 0) {
      text.setFill(Color.RED);
      text.setText("Please do not leave text fields empty");
    } else {
      try {
        File myFile = new File("Questions.txt");
        if (myFile.createNewFile())
            System.out.println("File created: " + myFile.getName());
        FileWriter fw = new FileWriter("Questions.txt", true);
        fw.write(question + ":" + answer + "\n");
        fw.close();
        text.setFill(Color.LIME);
        text.setText("Question and answer stored in Questions.txt");
      } catch (IOException e) {
        System.out.println("An error occured");
        text.setText("An error occured");
        e.printStackTrace();
      }
    }
    
    text.setLayoutX(200);
    text.setLayoutY((WINDOW_HEIGHT / 2) + 30);
    adminMenu.getChildren().add(text);
  }

  public static void onResultClicked(Scene scene, Parent resultMenu, TextArea resultTextFromFile) {
    String resultantString = "";
    try {
      Scanner results = new Scanner(new FileInputStream("Results.txt"));
      while (results.hasNextLine()) {
        String lineOfText = results.nextLine();
        resultantString += lineOfText + "\n";
      }
      resultTextFromFile.setText(resultantString);
      results.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    scene.setRoot(resultMenu);
  }

  public static void onStartQuizClicked(Scene scene, Group quizMenu, Group quizArea, TextField userName, Text notifyUser, ArrayList<String> qAndAs, Button nextQB, TextArea questionArea, Parent mainMenu, TextField answerBox, Text questionNumber) {
    notifyUser.setFill(Color.RED);
    notifyUser.setFont(Font.font("Verdana", 10));
    notifyUser.setLayoutX(340);
    notifyUser.setLayoutY(72);
    notifyUser.setText("");
    if (userName.getText().trim().length() > 0) {
      user_name_gloal = userName.getText();
      try {
        Scanner file = new Scanner(new FileInputStream("Questions.txt"));
        qAndAs.clear();
        for (int i = 0; i < numberOfLines; i++) {
          qAndAs.add(file.nextLine());
        }
        // Shuffling the questions around to get random questions
        Collections.shuffle(qAndAs);
        String[] arr = qAndAs.toArray(new String[0]);
        // qAndAsArr[0][0] First Question
        // qAndAsArr[0][1] First Answer
        String[][] qAndAsArr = new String[arr.length][2];
        for (int i = 0; i < qAndAsArr.length; i++) {
          qAndAsArr[i] = arr[i].split(":");
        }
        nextQB.setOnAction(e -> onNextQuestionClicked(scene, qAndAsArr, questionArea, mainMenu, answerBox, questionNumber));
        displayFirstQuestion(scene, qAndAsArr, questionArea);
        file.close();
      } catch (IOException e) {
        System.out.println("Errorr");
      }
      scene.setRoot(quizArea);
    } else {
      quizMenu.getChildren().remove(notifyUser);
      notifyUser.setText("Please enter a username");
      quizMenu.getChildren().add(notifyUser);
    }
  }

  public static void displayFirstQuestion(Scene scene, String[][] qAndAsArr, TextArea questionArea) {
    questionArea.setText(qAndAsArr[0][0]);
  }

  public static void onNextQuestionClicked(Scene scene, String[][] qAndAsArr, TextArea questionArea, Parent mainMenu, TextField answerBox, Text questionNumber) {
    // System.out.println(Arrays.deepToString(qAndAsArr));
    try {
      String userAnswer = answerBox.getText().trim();
      // System.out.println(userAnswer.equalsIgnoreCase(qAndAsArr[displayedQuestion][1]));
      if (userAnswer.equalsIgnoreCase(qAndAsArr[displayedQuestion][1])) {
        score++;
      }
      questionArea.setText(qAndAsArr[displayedQuestion + 1][0]);
      answerBox.clear();
      displayedQuestion++;
      questionNumber.setText("Question " + (displayedQuestion + 1));
    } catch (ArrayIndexOutOfBoundsException ex) {
      displayedQuestion = 0;
      questionNumber.setText("Question " + (displayedQuestion + 1));
      questionArea.clear();
      // System.out.println("Number of lines: " + numberOfLines);
      // System.out.println("Score: " + score);
      int percentage = (int) ((score / (float) numberOfLines) * 100);
      
      Group quizFinishedScreen = new Group();
      Text quizFinishedText = new Text("The Quiz has finished!\nYou Scored " + percentage + "% in 5 minutes");
      quizFinishedText.setFill(Color.BEIGE);
      quizFinishedText.setFont(Font.font("Verdana", 30));
      quizFinishedText.setTextAlignment(TextAlignment.CENTER);
      quizFinishedText.setLayoutX((WINDOW_WIDTH - 600) / 2);
      quizFinishedText.setLayoutY(WINDOW_HEIGHT / 2);
      quizFinishedText.setWrappingWidth(600);
      quizFinishedScreen.getChildren().add(quizFinishedText);
      scene.setRoot(quizFinishedScreen);

      Button goBackToMain = new Button("Go Back To Main Menu");
      goBackToMain.setMinWidth(200);
      goBackToMain.setMinHeight(50);
      goBackToMain.setLayoutX((WINDOW_WIDTH / 2) - (200 / 2));
      goBackToMain.setLayoutY((WINDOW_HEIGHT / 2) + 50);
      quizFinishedScreen.getChildren().add(goBackToMain);
      goBackToMain.setOnAction(e -> onBackClicked(scene, mainMenu));

      try {
        FileWriter fw = new FileWriter(new File("Results.txt"), true);
        fw.write("Name: " + user_name_gloal + "\n" + "Score: " + score + "\n\n");
        fw.close();
      } catch (IOException e) {
        System.out.println("Error in file Results.txt");
      }
      score = 0;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
