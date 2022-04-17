// all the imports needed to implement a GUI
	import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;  // Needed for listeners
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

class Skeleton extends JFrame implements ActionListener 
{
 // Declaring RNG
  static Random num = new Random ();
 
 // JComponennts 
  JLabel wheel, pointer, title; 
  JButton spinBtn;
 
 // Importing the wheel as an image icon
  ImageIcon image = new ImageIcon(getClass().getResource("wheeloffortune.png")); 
  ImageIcon arrow = new ImageIcon(getClass().getResource("arrow.png"));
 
 // JPanels for Border Layout
  JPanel north, south, center; 
 
 // Integers 
  int x = 1, random, random360;
  int animationcounter = 0;
 
 // Wheel Animation 
  Timer timer;  // timer used for animating wheel
  boolean b = true;   // for starting and stopping animation
  
 // Array 
  int numbers[] = new int[24]; // 24 element array to be filled in later. 
  
  int wedgevalue[] = {10000, -2, 3000, 775, 500, 1500, 850, -1, 700, 4500, 650, -2, 950, 6000, 550, 1000, 500, 0, 500, 800, 625, 7500, 900, 500};
	   //  0 - free play
	   // -1 - lose a turn
	   // -2 - bankrupt
  
 // Game Implementation Variables 
  
  public static int consonantgain; // integer to store amount of money to be earned from guessing correct consonant 
  int counter = 7; // user has 7 rounds to guess each phrase
  
  WordPuzzle puzzlegame; // WordPuzzle object to be used in main 
	Skeleton window; // Skeleton object to be used in main
  
  boolean newpuzzle = false; // variable to determine whether or not a new puzzle should be made (if puzzle is solved) 
  

  
 public Skeleton(WordPuzzle entry) 
 {
	 
     // INITIALIZE COMPONNENTS 
		 puzzlegame = entry;
		      
		 spinBtn = new JButton ("Spin"); 
		 spinBtn.addActionListener (this); // Connect button to listener class
		   
		 wheel = new JLabel (image);  // Add first image icon to JLabel
		 pointer = new JLabel (arrow); // puts arrow icon into JLabel
		 title = new JLabel ("WHEEL OF FORTUNE"); // header of the spinner window
	     title.setFont(new Font("Just Smile", Font.PLAIN, 140)); // set the font to custom settings
	     title.setBorder(new EmptyBorder(15,0,-10,0));//top,left,bottom,right slight padding adjustments
	     title.setForeground(fonts.purple);
		   
	     for(int i = 0; i < 24; i++) // fills array of numbers
	     {
	    	 	numbers[i] = i + 1; // 0th index contains 1, 1st contains 2, etc etc. 
	     }
		   
	     randomizationvalues(); // puts in random values to determine which wedge will be spun to and puts it's corresponding value into consonant gain using arrays
         
  
     // CONTENT PANE // LAYOUT 
	 	 setLayout (new BorderLayout ()); // Use BorderLayout for overall window
		    
		 north = new JPanel (); // creates new north JPanel 
		 north.setLayout (new FlowLayout (FlowLayout.CENTER)); // Use FlowLayout for Header Text 
		    
		 south = new JPanel (); // creates new south JPanel
		 south.setLayout(new FlowLayout (FlowLayout.CENTER)); // Use FlowLayout to manage the spin button
		    
		 center = new JPanel (); // creates new center JPanel
		 center.setLayout(new BorderLayout ()); // use border layout to vertically center wheel
   
	 // ADDING CONTENT
    
	    // North Section 
		    north.add (title); // Add the header to the north panel
		     add (north, "North"); // Add north panel to the north section of the border-layout
	    
	    // South Section
		    south.add(spinBtn); // Add the "spin" button to the south panel
		     add (south, "South"); // Add south panel in the south section of the border-layout
		   
	    // Center Section
		    center.add(wheel, BorderLayout.CENTER); // add the wheel in the center panel south section
		    center.add(pointer, BorderLayout.NORTH); // add the pointer in the center panel north section
		     add (center, "Center"); // Add center panel in the center section of the border-layout 
   
   // Setting Window Attributes
     
      pack (); // layout manager is in charge of frame size
      setTitle ("Wheel of Fortune"); // title of window
      setSize(800,800); // create 800x800 frame 
      setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); // default closing operation 
      setLocationRelativeTo (null);     // Centers the new window
     
     // TIMER FOR ANIMATION 
      timer = new Timer(90, new ActionListener() // speed and action listener for when timer events go off
                  								// https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html
    		  										// fires every 90ms 
		  {
		      @Override // action for animation (rotating wheel) 
		      public void actionPerformed(ActionEvent ae)  // action performed when timer
		       {
		       
		        // Modify circle
		        // use public rotation class
				    Rotation ri = new Rotation(image, 15*x); // rotates in increments of 15 degree (1 wedge). (360/ 24 wedges) 
				    wheel.setIcon (ri); // set the wheel as the new icon
				    x = x + 1; // add one to the variable x so it adds an extra 15 for every iteration
		 
		           repaint(); // overwrite previous wheel using predefined repaint method
		           animationcounter++; // adds one to the animation counter 
		           
		           if (animationcounter >= 24*(random360) + numbers[random]) // timer continues to fire until it reaches the rotation set by randomization method
		        	   															// adds a random360 to the wheel so it'll spin at least one full 360 before landing. 
		           {
		        	   		timer.stop(); // stops animation
		        	   		
		        	   		// resets counter values and re-randomizes for next spin 
				              animationcounter = 0;
				              x = 1;
				              
				        // spinner randomization will be done after the word puzzle so the monetary counter doesn't mess up
		          
				          try {
				                  Thread.sleep(3000); // creates a pause for better game flow
				                  // usually thread isn't ideal in java, but it works in this situation and makes it so that we don't need another timer 
				               } 
				          catch (InterruptedException e) 
				               {
				                  return;
				               }
				           
				        //check for non-monetary sections, in case the first wedge is bankruptcy       
				        if(consonantgain == -1) // lose a turn
				        {
				          consonantgain = 0; //change value in word puzzle
				          
				          
				          puzzlegame.striker = puzzlegame.striker - 1; // loses a turn 
				          puzzlegame.updateattempts();  // update the attempts 
				          
				          puzzlegame.enableallbuttons(); // enable all buttons
				          puzzlegame.vowel.setEnabled(false);  // but make vowel button false (no $$$) 
				          puzzlegame.disableallletter(); // disable the letters so they can't choose a consonant
				          
				          openwordpuzzle(puzzlegame); // opens up the word puzzle
				        } 
				        else if (consonantgain == -2) // bankruptcy
					        {
					          restart("Bankruptcy"); //instead of opening word puzzle, opens the bankrupt screen
					        } 
					        else
						    {
					        	if(consonantgain == 0) // freeplay
					        			{
					        			      puzzlegame.striker = puzzlegame.striker + 1; // gain a turn 
									      puzzlegame.updateattempts();  // update the attempts 
									      puzzlegame.vowel.setEnabled(false);  // make vowel button false
					        			}
						          openwordpuzzle(puzzlegame); // opens up the word puzzle
						        }  
		           }
		  }});
}


  public void restart(String LostGame) 
  {
    Restart window = new Restart (LostGame, 0, "unlucky...."); // bankruptcy
    window.getContentPane().setBackground(Color.WHITE);
    window.setVisible (true);
    this.dispose();
  }
   
 // Action event for when spin button is invoked. 
 @Override
 public void actionPerformed (ActionEvent e)
 {
  timer.start(); // start spinning (animation)  
 }
 
 public void openwordpuzzle(WordPuzzle puzzlegame)
     {
      if(newpuzzle == true)
      {
       puzzlegame = new WordPuzzle(window.consonantgain, window);
      }
      
      puzzlegame.setVisible(true); // sets hidden window to visible
      this.setVisible(false); // hide the spinner window to ensure user plays on right JFrame
      wheel.setIcon(image); // resetting the wheel to default position makes the developer's job easier 
            // no accumulative offset needed to be calculated using absolute value and extra temporary variable if we just reset position
      
      System.out.println("Previous value: " + puzzlegame.value);
      puzzlegame.value = this.consonantgain;
      System.out.println("After value: " + puzzlegame.value);
      
      if(puzzlegame.firstround == true) // if it's still the first round 
      {
    	  	puzzlegame.prizemoney = consonantgain; // set the prize money as the first spin 
    	  	
    	  	 /*puzzlegame.theMoney.setVisible(false); // remove initial construct 
    	  	 puzzlegame.theMoney = new JLabel("Money: " + puzzlegame.prizemoney + ""); // reconstruct the JLabel with new value
    	  	 puzzlegame.theMoney.setFont(new Font("courier", Font.BOLD, 32)); // set same formatting 
    	  	 puzzlegame.theMoney.setForeground(Color.BLUE);
    	  	 puzzlegame.theMoney.setHorizontalAlignment(JLabel.CENTER); // set same alignment
    	  	 
             puzzlegame.center.add(puzzlegame.theMoney, BorderLayout.SOUTH); // re-add it 
             puzzlegame.validate(); // validate */
    	  	
    	  		puzzlegame.moneyupdate();
             
         puzzlegame.updateattempts(); // update striker
      }
      
      puzzlegame.spinner = this;
      
     }
 
 public void randomizationvalues()
 {
  random = num.nextInt(24); // random number from 0 - 23
  	// System.out.print(random); used for developer testing
  
  random360 = num.nextInt (1) + 1; // random number from 1-2
  
  consonantgain = wedgevalue[random];
  System.out.println("consonantgain: " + consonantgain);
 }
 
/*
 // MAIN METHOD
 public static void main(String[] args) 
 {
    window = new Skeleton (puzzlegame);
    window.getContentPane().setBackground(Color.WHITE);
    window.setVisible (true);
        
    puzzlegame = new WordPuzzle(window.consonantgain, window); // creates puzzle window but does not make it visible yet
    puzzlegame.getContentPane().setBackground(Color.WHITE);
 } */
 


}