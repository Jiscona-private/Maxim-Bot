/**
 *
 * Beschreibung;
 * Maxim-Bot zum Loesen von "Vier gewinnt" 3D
 * @version 1.2 vom 07.03.2022
 * @author Jiscona-private
 */
 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 

public class connect4 {
  static short maxim = 2;
  static short opponent = 1;     
  
  static String gamemasterName = "";

  // Creating the game field
  static short[][] gameField = new short[16][4];
  static boolean first = false;
  
  static short[] minmax(short[][] funcField, short place, short depth, short player) {   
    short[] simulation = new short[2];
    simulation[1] = depth;
    //get height of "stack" on specific place
    short mmHeight = 0;    
    while (mmHeight <= 3 && funcField[place][mmHeight] != 0) { 
      mmHeight++;
      } // end of while   
                
    if (mmHeight == 4) {
      mmHeight = 0;
      simulation[0] = 3;
      return simulation;
    } // end of if
    
    short[][] simField = new short[16][4];
    for (short i=0;i<16;i++) {
      for (short j=0;j<4;j++) {
        simField[i][j] = funcField[i][j];
      } // end of for
    } // end of for
                                         
    simField[place][mmHeight] = player;

    //checks if game is won with manipulated tree
    boolean fourFound = evaluate(simField, player);
    
    if (fourFound == true) {
      if (player==opponent) {
        simulation[0] = 0;
      } // end of if
      else {
        simulation[0] = 2;
        } // end of if {
      return simulation;
    } // end of if     
          
    if (depth == 0) {
      simulation[0] = 1;
      return simulation;        
    } // end of if
      
      
    else {
      if (player == maxim) {   
        //opponent's turn  
        //recursion for every place on the board   
        short[] simResult = new short[2];        
        boolean oneFound = false; 
           
        for (short p=0;p<16;p++) { //p = place
          simResult = minmax(simField, p,(short) (depth-1), opponent);
          
          if (simResult[0] == 0) {
            return simResult;
          } // end of if
          
          if (simResult[0] == 1) {
            oneFound = true;        
          } // end of if
          } // end of for        
            
        //if 0 not found
        if (oneFound==true) {
          simResult[0] = 1;
          return simResult;
        } // end of if
          
        simResult[0] = 2;
        return simResult;
      } // end of if
      
      else {       
        //maxim's turn  
        //recursion for every place on the board   
        short[] simResult = new short[2];      
        boolean oneFound = false; 
           
        for (short p=0;p<16;p++) { //p = place
          simResult = minmax(simField, p,(short) (depth-1), maxim);
          
          if (simResult[0] == 2) {
            return simResult;
          } // end of if
          
          if (simResult[0] == 1) {
            oneFound = true;
          } // end of if
          } // end of for        
            
        //place if 2 not found
        if (oneFound==true) {
          simResult[0] = 1;
          return simResult;
        } // end of if
          
        simResult[0] = 0;

        return simResult;
        } // end of if-else
    } // end of if-else
    }
  
  static boolean evaluate(short[][] evaField, short player) { //evaluating if four can be found in given fild
    boolean found = true; //Reihe gefunden
    
    /*
    u|    -ve
    p|   -rt
     |  -ic
     | -al
     |-______________
     horizontal
    */
    for (short v1=0;v1<4;v1++) {
      for (short v2=0;v2<4;v2++) {
        //==== Suchen nach Reihen in einer Dimension (nicht diagonal) ====//
        for (short v3=0;v3<4;v3++) {
          if (evaField[((v2*4)+v3)][v1]!=player) { //durchlaeuft fuer jede Ebene jedes vertikale mit allen horizontalen
            found = false;
          } // end of if  
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v2+(v3*4))][v1]!=player) { //durchlaeuft fuer jede Ebene jedes horizonale mit allen vertikalen
            found = false;
          } // end of if
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[((v1*4)+v2)][v3]!=player) { //durchlaeuft fuer alle vertikalen und horizontalen Werte alle Ebenen
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;
        
        //==== Suchen nach diagonalen Reihen ===//
        // Zwischen den Ecken an Seiten
        for (short v3=0;v3<4;v3++) {
          if (evaField[v2+(v3*4)][v3]!=player) { //durchlaeuft fuer alle vertikalen Ebenen die eine der moeglichen Diagonalen (vorn=oben)
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[v2+(v3*4)][3-v3]!=player) { //durchlaeuft fuer alle vertikalen Ebenen die zweite der moeglichen Diagonalen (hinten=oben)
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true; 
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v2*4)+v3][v3]!=player) { //durchlaeuft fuer alle horizontalen Ebenen die eine der moeglichen Diagonalen (links=unten)
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;     
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v2*4)+v3][3-v3]!=player) { //durchlaeuft fuer alle horizontalen Ebenen die zweite der moeglichen Diagonalen (links=oben)
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v3*5)][v2]!=player) { //durchlaeuft fuer alle up Ebenen die eine der moeglichen Diagonalen (hinten=links)
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;     
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v3*3)+3][v2]!=player) { //durchlaeuft fuer alle up Ebenen die zweite der moeglichen Diagonalen (hinten=rechts)
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;  
        
        //Zwischen gegenueberliegnden Ecken (im Wuerfel)
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v3*5)][v3]!=player) { //durch den Koerper: unten hinten links bis vorn oben rechts
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v3*3)+3][v3]!=player) { //durch den Koerper: unten hinten rechts bis vorn oben links
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true; 
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v3*5)][(3-v3)]!=player) { //durch den Koerper: oben hinten links bis vorn unten rechts
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;
        
        for (short v3=0;v3<4;v3++) {
          if (evaField[(v3*3)+3][(3-v3)]!=player) { //durch den Koerper: oben hinten rechts bis unten vorne links
            found = false;
          } // end of if   
        } // end of for
        if (found == true) {
          return true;
        } // end of if
        found = true;  
      } // end of for      
    } // end of for   
    return false;
    }
  
  static short getMove(short oppMove) {    
    short gMHeight = 0;
    if (first == false) {
      while (gameField[oppMove][gMHeight] != 0 && gMHeight < 3) { //get height of "stack" on specific place
        ++gMHeight;
      } // end of while
      gameField[oppMove][gMHeight] = opponent;
    } // end of if    
           
    short[] result = new short[2];     
    short onePlace = 16;
    short twoPlace = 16;
    short winDepth = -4;
    short zeroPlace = 16;
    short loseDepth = 5;
    
    for (short p=0;p<16;p++) {
      System.out.println("Trying "+p);
      result = minmax(gameField, p,(short) 4, maxim);
      System.out.println(result[0]);
      if (result[0] == 2 && result[1] > winDepth) {    
        twoPlace = p;
        winDepth = result[1];
      } // end of if
      else if (result[0] == 1) {
        onePlace = p;
      } // end of if
      else if (result[0] == 0 && result[1] <= loseDepth) {
        zeroPlace = p;
        loseDepth = result[1];
      }
    } // end of for
     
    System.out.println(result[0]);
    
    short setMove = 0;
    if (twoPlace<16) {
      setMove = twoPlace;
    } // end of if
    else if (onePlace<16) {
      setMove = onePlace;
    } // end of if-else
    else {
      setMove = zeroPlace;
    }
    
    
    gMHeight = 0;
    while (gameField[setMove][gMHeight] != 0 && gMHeight < 3) { //get height of "stack" on specific place
      ++gMHeight;
    } // end of while
    gameField[setMove][gMHeight] = maxim;
    
    return setMove;
    }
  
  public static void writeMove(short move) {
    try {
      File gamemaster = new File(gamemasterName);
      FileWriter fw = new FileWriter(gamemaster.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(">"+move);
      bw.close();    
                 
      System.out.println("Done with "+move+"!");
    } catch (IOException e) {
      e.printStackTrace();
    } 
    }
  
  public static void main(String[] args) {  
   gamemasterName = args[0];
   
    short opponentMove;
    String move = "";
    
    File gamemaster = new File(gamemasterName);

    for (;;) {
      try {
        Scanner reader = new Scanner(gamemaster);
      
    
        while (!reader.hasNext()) {
          try {
            reader = new Scanner(gamemaster);
            Thread.sleep(50);
          }
          catch(InterruptedException ex)
          {
            Thread.currentThread().interrupt();
          }
        }
        
        //get opponent's move
          
        move = reader.nextLine();
        reader.close();
          
        
        //check possible messages 
        if (move.equals("<end")) {   
          break;
        } // end of if   
        

        if (move.equals("<start")) {   
          opponentMove = 0;
          first = true;
          System.out.println("First move!");
          
          //get maxim's move
          short maximMove = 0;
          maximMove = getMove(opponentMove);
          
          //write maxim's move
          writeMove(maximMove);        
          first = false; 
        } // end of if                    
        
        else if (move.charAt(0) == '<') {
          //split
          String[] split = move.split("<");
          opponentMove = Short.parseShort(split[1]);
          System.out.println("Opponent move: "+opponentMove);
          
          //get maxim's move
          short maximMove = 0;
          maximMove = getMove(opponentMove);
          
          //write maxim's move
          writeMove(maximMove);         
        } // end of if
        else {
        }
      }

      catch (FileNotFoundException e) { //needed for Scanner reader
        System.out.println("An error occurred.");
        e.printStackTrace();
      }  
    } // end of for
    
  System.out.println("Stopping...");
  System.exit(0);
    
  } // end of main

} // end of class connnect4
