// all the imports needed to implement the main (color)
 import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;


public class main 
{
 
 static Skeleton window; // object for spinner window 
 static WordPuzzle puzzlegame; // object for puzzle window 
 
 
 
 public static void main(String[] args) 
 {

  //access fonts class in order to load custom fonts 

  try {
   fonts.main(null);
  } catch (FontFormatException | IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  
  
     puzzlegame = new WordPuzzle(window.consonantgain, window); // creates puzzle window but does not make it visible yet
     puzzlegame.getContentPane().setBackground(Color.WHITE); // sets background color 
     
    window = new Skeleton (puzzlegame); // new skeleton window (spinner)  
     window.getContentPane().setBackground(Color.WHITE); // set background color as white 
     window.setVisible (true); // set it as visible
         
 }

}
