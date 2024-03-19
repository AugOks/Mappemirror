package org.ntnu.IDATA2003.mappe5.Ui;


import java.util.ArrayList;
import org.ntnu.IDATA2003.mappe5.ChaosGame;

public class ChaosGameUi {

  private ChaosGame mainGame;
  private final ChaosGameController controller;
  private final static  int SIERPINSKI=1;
  private final static  int MANDELBROT =2;
  private final static int QUIT=3;

  //TODO this should be defined on how large the users screen is.
  private static final int height = 30;
  private static final int width =70;
  public ChaosGameUi(){
    this.controller = new ChaosGameController(this);
  }

/**
 * The main Application screen. Starts the application and gets user input.
 */
  public void start() {
    boolean finished = false;
    while (!finished) {
      int menuChoice = displayMenu();
      switch (menuChoice) {
        case SIERPINSKI:
          this.printFractal(menuChoice);
          break;
        case MANDELBROT:
          this.printFractal(menuChoice);
          break;
        case QUIT:
          System.out.println("* Thank you for using Chaos Game *");
          finished = true;
          break;

        default:
          System.out.println("Invalid choice - Please pick a number from the menu;)");
      }
    }
  }
  private int displayMenu() {
    System.out.println("Pick an option");
    System.out.println("1. Sierpinski triangle");
    System.out.println("2. Mandelbrot");
    System.out.println("3. Quit");

    return this.controller.parseInput();
  }

  private void printCanvas(){
    int index_i = mainGame.getCanvas().getHeight();
    int index_j =  mainGame.getCanvas().getWidth();
    int[][] canvas = mainGame.getCanvas().getCanvasArray();
    ArrayList<String> canvasConsole = new ArrayList<>();
    String line="";

    for(int i = 0; i < index_i; i++){
      for(int j = 0; j < index_j; j++){
        if(canvas[i][j]==0){
          line += "-";
        } else{
          line+="X";
        }
      }
      canvasConsole.add(line);
      line="";
    }
    for (String s : canvasConsole) {
      System.out.println(s);
    }
  }

  public void printFractal(int menuChoice){
    System.out.println("How many steps to run?");
    int steps = this.controller.parseInput();
    if(menuChoice==1) {
      this.mainGame = new ChaosGame(controller.createSierpinksi(), height, width);
    }
    if (menuChoice==2){
      this.mainGame = new ChaosGame(controller.createMandelbrot(), height, width);
    }
    this.mainGame.runSteps(steps);
    printCanvas();

  }
  public  void  testfilesfile(){

    this.controller.createMandelbrot();

  }
}
