// all the imports needed to implement a GUI
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;  // Needed for listeners
import java.util.Random;

public class WordPuzzle extends JFrame implements ActionListener // can respond to key entries
{
 
  //static String answer = "MY PUZZLE", puzzle = "M- -U----"; // original testing strings
  
  String answer, puzzle = ""; // strings to store answer and question 
  String gueStr = "";
  
  // Declaring RNG
   static Random num = new Random ();
  
  DrawArea board; // graphics output class defined inside this class
  
  // JComponents 
   JButton alphabet[] = new JButton[26];
   JButton vowel, consonant, phrase;
   JPanel north, south, center; 
   JLabel title, theMoney, attemptsleft; 
   JTextField guess;
  
  // Arrays containing database of different phrases 
   String SongTitles [] = {"A WHOLE NEW WORLD", "SWEET HOME ALABAMA", "PARTY IN THE USA", "CRY ME A RIVER", "ALL I WANT FOR CHRISTMAS IS YOU"};
   String Things [] = {"A DEGREE IN COMPUTER SCIENCE", "A BIG HINT", "A LATE ASSIGNMENT", "GRAPHIC USER INTERFACE", "FRESH START"};
   String AroundtheHouse [] = {"LEAKY FAUCET", "A VASE FULL OF FLOWERS", "BABY WIPES", "CERAMIC BIRDBATH", "WIRELESS INTERNET"};
   String Slogan [] = {"EASY BREEZY BEAUTIFUL COVERGIRL", "THAT WAS EASY", "THE CURIOUSLY STRONG MINTS", "TASTES AWFUL BUT IT WORKS", "JUST DO IT"};
   String OnTheMap [] = {"AMAZON RAINFOREST", "NEW YORK CITY", "MOUNT KILIMANJARO", "THE EIFFEL TOWER", "CHRIST THE REDEEMER"};
  
  // Miscellaneous Game Implementation variables
 
   String category; // String to store header
   
   boolean correct = false, solved = false; // boolean to determine if an error header should be displayed or not 
                                            // boolean to determine if the puzzle has been solved or not
   
   boolean firstround = true; // boolean to determine if the spin was the first round or not
   
   private int random1, random2, random3a, random3b; // values to store random numbers
   protected int prizemoney, value, counter = 0; // variables to calculate and display earnings or losses
   protected int striker = 7; // variable for determining how many times the user guessed
                               // set to 7 for actual game play
   int letterNum;
   
   private int strikercounter = striker; // counter to be displayed on window
   
   Skeleton spinner; // object that inherits JFrame spinner (which calls this WordPuzzle object) 
   Timer timer; // timer used to switch between windows 
   Timer solvedtimer; // timer used to restart when solution is solved 
   //Timer redflash; // timer used to make header red for set amount of time when wrong input is given

  
  public WordPuzzle (int money, Skeleton main)
  { 
    super(); // inherits value determined by the spin. 
    nonmonetaryvaluechecker(); // checks for non-monetary values and performs according actions
    
    // System.out.print(money); for developer to test if the variable passing works
    
    spinner = main; // passes the jFrame object (window displaying spinner) 
    value = main.consonantgain;  // passes the value of the wedge spun
    System.out.print("VALUE: " + value);
    prizemoney = value; // the initial spin determines the starting value of the user
    
    puzzlegenerator(); // calls on method that generates the puzzle 
    
    // INITIALIZE COMPONNENTS 
    
     // 3 Buttons
      consonant = new JButton ("Guess a Consonnant");
      vowel = new JButton("Buy A Vowel");
      phrase = new JButton("Guess The Whole Phrase");
     
     // set the  commands for each button    
      consonant.setActionCommand( "1" );   // sets to 1, 2 and 3 respectively
      vowel.setActionCommand( "2" );   
      phrase.setActionCommand( "3" ); 
     
     // set action listeners for Buttons
      consonant.addActionListener( this );
      vowel.addActionListener( this );
      phrase.addActionListener( this );
     
     // Drawing Board for Components from DrawArea Class below
     board = new DrawArea (400, 300); // Custom panel to use as graphics output area
       // add(board); // add this board to the panel
     
     // Keyboard Buttons for User Input
     
     for(int x = 0; x < 26 ; x++) // loop 26 times for the 26 letters of the alphabet
     {
       alphabet[x] = new JButton("" + (char)(x+65)); // create letter button (cast to string) 
       board.add(alphabet[x]); // add respective letter
       alphabet[x].addActionListener(this); // link to listener
     }
     
     for(int x=0; x < 26; x++) // go through the button array to disable all of them
     {
       alphabet[x].setEnabled(false); // by default disable all the buttons
     }
     
     title = new JLabel(category); // sets title as category the phrase is in 
     title.setFont(new Font("Just Smile", Font.BOLD, 65));
     title.setForeground(fonts.turquoise);
     
     //Money Display
      theMoney = new JLabel("Money: " + prizemoney + ""); // create new JLabel
      theMoney.setFont(new Font("Futura", Font.PLAIN, 32)); // set formatting 
      theMoney.setForeground(fonts.orange2);
      theMoney.setHorizontalAlignment(JLabel.CENTER); // center it
      theMoney.setBorder(new EmptyBorder(0,0,30,0));//top,left,bottom,right slight padding adjustments
     
     //Text field        
      guess = new JTextField("ENTER YOUR GUESS"); // Default text inside the JField
                    // User has to delete this text themselves
      guess.setActionCommand( "4" ); // set action command as "4"
      guess.addActionListener(this); // set action listener
     
  // attempts left 
      attemptsleft = new JLabel("ATTEMPTS LEFT : " + strikercounter);
      attemptsleft.setFont(new Font("Futura", Font.PLAIN, 20)); // set formatting 
      attemptsleft.setForeground(fonts.orange);
      attemptsleft.setBorder(new EmptyBorder(5,0,20,0));//top,left,bottom,right slight padding adjustments
      attemptsleft.setHorizontalAlignment(JLabel.CENTER); // center it 
    
    // CONTENT PANE / LAYOUT
    
     setLayout (new BorderLayout ()); // Use BorderLayout for this panel
     
     north = new JPanel ();  // new north JPanel
     north.setLayout (new FlowLayout (FlowLayout.CENTER)); // Use FlowLayout for Header Text
     
     south = new JPanel ();  // new south JPanel
     south.setLayout(new FlowLayout (FlowLayout.CENTER)); // Use FlowLayout to manage Button
     
     center = new JPanel (); // new center JPanel
     center.setLayout(new BorderLayout ()); // use BorderLayout to vertically center wheel
    
    // ADDING CONTENT
    
     // North Section 
      north.add (title); // Create, add label
        add (north, "North"); // add north panel to north section of BorderLayout
     
     // South Section
      south.add(consonant); // add consonant button
      south.add(vowel); // add vowel button
      south.add(phrase); // add guess phrase button
         add (south, "South"); // add south panel to south section of BorderLayout
     
     // Center Section
      //center.add(guess, BorderLayout.CENTER); // text-field doesn't need to be added yet
      center.add(board, BorderLayout.CENTER); // add the puzzle 
      center.add(theMoney, BorderLayout.SOUTH); // add the current money
      center.add(attemptsleft, BorderLayout.NORTH);
        add (center, "Center"); // add center panel to center section of BorderLayout
    
    // WINDOW ATTRIBUTES
    
  pack(); // layout manager is in charge of frame sizing
     setTitle ("Word Puzzle"); // Title of window
     setSize(1050,600); // set size of frame
     setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); // default closing operation
     setLocationRelativeTo (null); // Center this window
     
         
      // TIMER FOR WINDOW SIWTCHING
         timer = new Timer(250, new ActionListener() // speed and action listener for when timer events go off
                                           // https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html
       {
           @Override // action for when user has finished with the puzzle window 
           public void actionPerformed(ActionEvent ae)  // action performed when timer is fired
             {
              // Former striker system
                    //striker = striker - 1; // subtract one from striker
                    //System.out.println("Striker: " + striker); // used to debug counter function
                    //System.out.println("Solved: " + solved); // debug solved 
                    
                    //for checking in consonant button
                    /*if(striker <= 0) 
                    {
                     System.out.print("MAXIMUM ROUNDS REACHED");
                     restart("Puzzle not solved in 7 rounds");
           
                    }
                    
                    if(puzzle.equals(answer))
                    {
                        restart("CONGRATULATIONS! YOU SOLVED THE PUZZLE!");
                    }
                    
                    if(striker >= 0 || solved == false)
                    {
                     windowswitch(); // timer fires this event (switching windows when every 1500 seconds once the timer is started) 
                     //striker--;
                    } */
              
               windowswitch(); // switches window
              timer.stop(); // stop the timer from continuing to run in the background
                
             }
       });
         
      // TIMER FOR Solved puzzle
         solvedtimer = new Timer(500, new ActionListener() // speed and action listener for when timer events go off
                                           // https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html
       {
           @Override // action for when user has finished with the puzzle window 
           public void actionPerformed(ActionEvent ae)  // action performed when timer is fired
             {
  
               restart("CONGRATULATIONS! YOU SOLVED THE PUZZLE!"); //sets to restart with congratulations message
              solvedtimer.stop(); // stop the timer from continuing to run in the background
                
             }
       });
         /*
         redflash = new Timer(500, new ActionListener()
           
         {
             @Override // action for when user has finished with the puzzle window 
             public void actionPerformed(ActionEvent ae)  // action performed when timer is fired
               {
    
                title.setForeground(fonts.turquoise);
                timer.stop();
                  
               }
         }); */
         

    }
  
  @Override
    public void actionPerformed (ActionEvent e)
    {
      title.setText(category); // header is the category acquired formerly 
      title.setForeground(fonts.turquoise);
      
      char letter = e.getActionCommand().charAt(0); // find which letter was pressed
        
        
        if ( e.getActionCommand().equals( "1" ) ) // if user presses guess consonant button
        {
            
            for(int x = 0; x < 26; x++) // goes through all the buttons 
            {
                if(x != 0 && x != 4 && x != 8 && x != 14 && x != 20 ) // if not a vowel
                {
                  alphabet[x].setEnabled(true); // enable
                }  else
                  alphabet[x].setEnabled(false); // disable all vowel buttons 
            }
            
            // Disables other button options 
              vowel.setEnabled(false);
              phrase.setEnabled(false);
              consonant.setEnabled(false);
              
             firstround = false; // if this button was pressed, set the first round as false 
             
             timer.start(); // starts the timer (delay of 1500 milliseconds before running the window-switch function) 
             
             nonmonetaryvaluechecker(); // checks if free play or lose a turn was spun
            
        }
             
        else if ( e.getActionCommand().equals( "2" ) ) // if e.getActionCommand yields the second button (buy vowel's) set action command
        {
            
            for (int x=0; x < 26; x++) // loop through all buttons 
            {
                if(x != 0 && x != 4 && x != 8 && x != 14 && x != 20) // if not a vowel
                {
                  alphabet[x].setEnabled(false); // disable consonants
                } else
                  {
                   alphabet[x].setEnabled(true); //enable vowels
                  }
            }
            
            // Disables other button options 
             consonant.setEnabled(false);
             phrase.setEnabled(false);
             
           // System.out.println("Striker: " + striker); // used to debug counter function
           
               
             if(puzzle.equals(answer))
             {
               restart("CONGRATULATIONS! YOU SOLVED THE PUZZLE!");
             } else if (striker <= 0)
              {
                prizemoney = 0; // lose all prize money
                restart("Puzzle not solved in 7 tries");
              }
         
           firstround = false; // no longer the first round
           value = -250; // vowel costs 250 to buy  
        }
        
        else if ( e.getActionCommand().equals("3")) // third button (enter phrase)
        {
   
           for(int x=0; x < 26; x++) // All the Letters are disabled
              {
                  alphabet[x].setEnabled(false);
              }
              
              guess.setVisible(true); // set the text entry field to be visible
              guess.setEnabled(true); // enable it
              center.add(guess, BorderLayout.SOUTH); // add to GUI text-field 
              validate(); // resize components if necessary
              
              firstround = false; // no longer the first round
            
            //disable all buttons
              consonant.setEnabled(false);
              vowel.setEnabled(false);
              phrase.setEnabled(false);
        }
        
      else if ( e.getActionCommand().equals("4")) // activates when user presses enter on text field 
     {
       //Text-field guess
        gueStr = guess.getText(); // grab the text 
        gueStr = gueStr.toUpperCase(); // convert it to an upper-case in case user enters in lower-case
       
       striker = striker - 1; // update striker
       updateattempts();// update the striker count 
       
       // System.out.println("Striker: " + striker); // used to debug counter function
           
       if(gueStr.equals(answer))
        {
         puzzle = answer;
         correct = true;
         solved = true;
         revealallletters();
         validate();
    
         solvedtimer.start();
        } else 
          {
            if(correct == false) // otherwise if correct was not changed to true
               {
                //  System.out.print("Wrong answer"); // used for developer debugging 
              title.setForeground(Color.red);
              title.setText("WRONG"); // turn header into "WRONG"
              //redflash.start(); unecessary
               } else
                 {
                   correct = false; // otherwise, set it back to false (otherwise it will always stay true if a single correct letter is entered). 
                 }

            if (striker <= 0) // checks if 7 rounds has passed 
            {
              prizemoney = 0; // player loses their money 
              restart("Puzzle not solved in 7 tries"); // reset with failure message
            }
          }
       
        guess.setEnabled(false); // always disable & hide text field after one try
        guess.setVisible(false);  // hide it 
        
        //re-enable the three buttons after entry
         enableallbuttons();
     
        repaint(); // repaint the draw-area if necessary 
        
        disableallletter(); //disable the alphabetical buttons 
        
        
     }
        
        // THE FOLLOWING IS FOR WHEN ANY OF THE LETTERS ARE PRESSED
        
         alphabet[(int)letter-65].setEnabled(false); // disable button
 
         //letter guess
         for(int x=0; x < answer.length(); x++) // go through answer
         {
             if(letter == answer.charAt(x) && puzzle.charAt(x) == '-') //if the letter pressed matches a character in answer that is not already given
             {
               puzzle = puzzle.substring(0,x) + letter + puzzle.substring(x+1); // substitute in letter on the board 
               correct = true; // set boolean to true
               if(letter != 'A' || letter != 'E' || letter != 'I' || letter != 'O' || letter != 'U') //
                {
                  counter++; // adds one to the counter for every consonant that is correct
                }
               // System.out.print("Right!"); // used for developer debugging
             } 
         } 
         
         striker = striker - 1;  // remove one from the striker (since a letter has been guessed) 
         updateattempts(); // update the attempt counter
         
         // add any earning onto your prize money || or subtract if vowels were bought 
         if(letter == 'A' || letter == 'E' || letter == 'I' || letter == 'O' || letter == 'U') //if letter is a vowel
         {
               prizemoney = prizemoney + value; //add value (-250) to prizemoney
         }
         else if(letter != 'A' || letter != 'E' || letter != 'I' || letter != 'O' || letter != 'U') //if letter is a consonant
         {      
                prizemoney = prizemoney + counter * value; //mulitply counter to each correct consonant
         }
           
         
         if(puzzle.equals(answer)) // checks if the puzzle has been solved 
         {
            solvedtimer.start();
        
         } 
         //if money is less than 0, player is bankrupt
         else if (prizemoney < 0) 
         {
             restart("Bankruptcy"); //Game Over
         }
         
         else if (striker <= 0) // otherwise checks if all the strikes have been deducted (7 rounds)
          {
               prizemoney = 0; // player loses prizemoney 
               restart("Puzzle not solved in 7 tries"); // then sets to restart 
          }
         
         counter = 0; // reset counter back to 0 for next round
         
         //Update Money Display
          moneyupdate();
         
          // Developer Checkpoints for logic 
           // System.out.println("value: " + value);
           // System.out.println("prize money: " + prizemoney);
           
         
             
         if(correct == false) // otherwise if correct was not changed to true
         {
          //  System.out.print("Wrong answer"); // used for developer debugging 
          title.setForeground(Color.red);
          this.title.setText("WRONG"); // turn header into "WRONG" so user knows you were incorrect
         } else
           {
             correct = false; // otherwise, set it back to false (otherwise it will always stay true if a single correct letter is entered). 
           }
 
         disableallletter(); // disable all alphabetical letters 
         enableallbuttons();  // re-enable all buttons 
         
          
    }
  
  public void puzzlegenerator()
  {
    int random1 = num.nextInt(4);  // random number generates from 0-4
    int random2 = num.nextInt(4); // " " same as above
    
    if (random1 == 4) // if 4 is generated
    {
      category = "Song Titles"; // use the song titles category
      answer = SongTitles[random2]; // take a random phrase from respective array
    } else if (random1 == 3) // if 3 ... 
    {
      category = "Things"; // use the things category
      answer = Things[random2]; // take a random phrase from respective array
    } else if (random1 == 2)
    {
      category = "AroundtheHouse"; // use the "around the house" category
      answer = AroundtheHouse[random2]; // take a random phrase from respective array
    } else if (random1 == 1)
    {
      category = "Slogan"; // use the "slogan" category
      answer = Slogan[random2]; // take a random phrase from respective array
    } else if (random1 == 0)
    {
      category = "OnTheMap"; // use the "on the map" category
      answer = OnTheMap[random2]; // take a random phrase from respective array
    }
    
    int length = answer.length(); // find length of randomly selected phrase
    
    random3a = num.nextInt(length-1); // RNG the one of the index values
    
    do 
    {
      random3b = num.nextInt(length-1); // RNG a second value
    } while (random3b == random3a); // loops back in-case RNG is same for both values
    
    for(int i = 0; i < length; i++) // loops through all index values
    {
      if(i == random3a || i == random3b)
      {
        puzzle = puzzle + answer.charAt(i); // gives 2 letters from the answer as a hint
      } else if (answer.charAt(i) == ' ')
      {
        puzzle = puzzle + ' ';
      }else 
      {
        puzzle = puzzle + "-"; // adds unknown characters for the puzzle
      }
    }
    
    // For developer checking in the console: 
    
    System.out.println(puzzle);
    System.out.println(answer);
    
    
  }
  
  public void nonmonetaryvaluechecker() //checks for non-monetary sections on the wheel (exclude free-play)
  {
    if(value == -1) // lose a turn
    {
      striker = striker - 1; //takes away 1 chance of guessing
      updateattempts(); // update the lost attempt 
      
      value = 0; // set value to 0
      disableallletter();// disable alphabetical buttons 
    } 
    else if (value == -2) // bankruptcy
     {
       restart("Bankruptcy"); // restart with unlucky bankruptcy message
       this.dispose(); // close current window
     }
 }
  
  public void windowswitch() 
  {
    spinner.randomizationvalues(); // re-randomize values 
    spinner.setVisible(true); // set the spinner window back to visible 
    this.setVisible(false); // make the current window invisible
    
    // striker = striker - 1;  former striker system that didn't work 
  }
  
  
  public void revealallletters() // method to reveal all the letters 
  {
    for(int x=0; x < answer.length(); x++) // go through answer
    {
      puzzle = puzzle.substring(0,x) + answer.charAt(x) + puzzle.substring(x+1); // substitute in letter on the board 
    }  // sub in all the letters in the draw area 
    
  }
  
  public void disableallletter() //char letter)
  {
    // alphabet[(int)letter-65].setEnabled(false); // disable button
    
    for( int x =0; x < 26; x++) // go through all the alphabet buttons 
    {
      alphabet[x].setEnabled(false); // disable all of them 
    }
    
      enableallbuttons(); // set the three main buttons back to enabled
    
    //  guess.setEnabled(false); don't need this anymore
  }
  
  public void restart(String LostGame) 
  {
    Restart window = new Restart (LostGame, prizemoney, answer); // create a restart window with appropriate "lostgame" message and money earned
    window.getContentPane().setBackground(Color.WHITE); // set background as white 
    window.setVisible (true); // set it as visible 
    this.dispose(); // dispose current window 
  }
  
  public void enableallbuttons() // method used to enable the three main buttons 
  {
   consonant.setEnabled(true); // for consonant 
   vowel.setEnabled(true); // for vowel 
   phrase.setEnabled(true); // for the phrase guess button
  }
  
  public void updateattempts() // method to update the striker counter 
  {
   strikercounter = striker; // set striker counter as the new striker
  
   // System.out.println("attempts: " + strikercounter); used for debugging purposes 
 
     attemptsleft.setVisible(false); // set current striker counter as invisible 
     attemptsleft = new JLabel("Attempts left : " + strikercounter);
     attemptsleft.setFont(new Font("Futura", Font.PLAIN, 32)); // set formatting 
     attemptsleft.setForeground(fonts.orange); // set to custom color
     attemptsleft.setBorder(new EmptyBorder(5,0,20,0));//top,left,bottom,right slight padding adjustments
     attemptsleft.setHorizontalAlignment(JLabel.CENTER); // center it
     center.add(attemptsleft, BorderLayout.NORTH); // add it back in 
     validate();
     // don't need this stuff
      //attemptsleft.setVisible(true); // set it to visible
        //add (center, "Center"); // add center panel to center section of BorderLayout
  }
  
  public void moneyupdate() 
  {
      theMoney.setVisible(false); //hide old money display
      theMoney = new JLabel("Money: " + prizemoney + ""); // create a new one with new value
      theMoney.setFont(new Font("Futura", Font.PLAIN, 32)); // set same formatting 
      theMoney.setForeground(fonts.orange2); // set to custom colors
      theMoney.setBorder(new EmptyBorder(0,0,30,0));//top,left,bottom,right slight padding adjustments
      theMoney.setHorizontalAlignment(JLabel.CENTER); // set same horizontal alignment again
      center.add(theMoney, BorderLayout.SOUTH); // add it back into the JFrame 
      validate(); // validate in case any new reformatting is required
  }
  
  class DrawArea extends JPanel // class to draw the puzzle
  {
    public DrawArea (int width, int height)  // Create panel of given size
    {
      this.setPreferredSize (new Dimension (width, height)); // constructor
    }
    
    public void paintComponent (Graphics g)  // import graphics object
    {
      Color col; // color of the box to be determined by spaces, letters, solved, etc
      char letter; // used to temporarily store the character in the puzzle 
      
      g.setFont( new Font("futura", Font.PLAIN,24)); // font style for letters
      
      for(int x=0; x < puzzle.length(); x++) // loop through puzzle
      {
        letter = puzzle.charAt(x);// temporary letter
        
        if(letter == '-' || letter == ' ') // not a letter
        {
          if(letter == '-')
          {
            col = fonts.purple; // purple box for blanks
          } else // it's a space
           {
             col = Color.white; // white box for spaces
           }
          
          g.setColor(col); // set box color depending on result of above if statements
          g.fillRect(x*30+(525 - (15*puzzle.length())),200,30,30); // create rectangles
        } else // otherwise set the box to black 
         {
           g.setColor(Color.black); // default box 
           g.drawString(""+letter,x*30+(532 - (15*puzzle.length())),223); // draw the letter in normal box
         }
        
        g.setColor(Color.black);  // black border
        g.drawRect(x*30+(525 - (15*puzzle.length())),200,30,30); // draw surrounding border
      }
      
    }
  }
  
}