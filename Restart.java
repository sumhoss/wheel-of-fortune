// all the imports needed to implement a GUI
	import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;  // Needed for listeners
import java.io.IOException;


public class Restart extends JFrame implements ActionListener 
{
	
	// J COMPONNENTS 
		  JLabel ReasonforOver, Restart, phrase;  // labels for text
		  JLabel moneyDisp; // label for money 
		  JButton yes, no; // yes and no buttons 
		  JPanel north, south, center;  // panel sections 
		  
		  String Reason = ""; // string to store the reason for the user (lose or win) 
		  String rank;
		  String rightanswer; 
	  
  public Restart (String theReason, int moneyearned, String correct)
  {
    // INITIALIZE COMPONNENTS 
    
		    super(); // inherit anything needed 
		    Reason = theReason;
		    int pMoney = moneyearned;
		    rightanswer = correct; 

		    //Ranking
		    if(pMoney < 2000)
		    {
			    	if(pMoney <= 0) //in debt or bankrupt
			    	{
			    		rank = "...";
			    	} else
			    		rank = "Rookie";
		    }
		    else if (pMoney > 2000 && pMoney < 10000)
		      rank = "Advanced";
		    else
		      rank = "Professional";
    
          //Gets the amount of money won and stores as JLabel
	    String money = pMoney + "";
	   
	    if(pMoney < 0) //if the money is negative
	   {
	    moneyDisp = new JLabel("You lost $" + pMoney*-1  + ", Rank: " + rank);
	   }
	   else //if money is positive or 0
	   {
	    moneyDisp = new JLabel("You won $" + money + ", Rank: " + rank);
	   }
	   
	    moneyDisp.setFont(new Font("HKNova-Medium", Font.PLAIN, 32));
	    moneyDisp.setForeground(fonts.orange2);
    
    //Set up the Play/Quit buttons
	    yes = new JButton ("Play"); // play button 
	    yes.setForeground(fonts.turquoise);
	    yes.setPreferredSize(new Dimension(300, 100)); // set button dimensions 
	    no = new JButton ("Quit"); // quit button
	    no.setPreferredSize(new Dimension(300, 100)); // set button dimensions 
	    no.setForeground(fonts.orange);
	    
	    yes.addActionListener(this); // set action listeners 
	    no.addActionListener(this); 
	    
	    yes.setActionCommand("1"); // set action command 
	    no.setActionCommand("2");
    
    //prints the reason for losing or win
	    ReasonforOver = new JLabel (Reason); // JLabel with the passed reason
	    ReasonforOver.setFont(new Font("Futura", Font.PLAIN, 32)); // set font 
	    ReasonforOver.setForeground(fonts.orange); // set color 
	    ReasonforOver.setBorder(new EmptyBorder(50,0,-0,0));//top,left,bottom,right slight padding adjustments
	    
	// prints the right phrase
	    phrase = new JLabel(rightanswer);
	    phrase.setFont(new Font("Just Smile", Font.PLAIN, 50));
	    phrase.setForeground(fonts.turquoise);
	    phrase.setBorder(new EmptyBorder(45,0,-0,0));//top,left,bottom,right slight padding adjustments
    
    //Prints "Play Again?"
	    Restart = new JLabel ("Play Again?"); // play again prompt. 
	    Restart.setFont(new Font("Just Smile", Font.PLAIN, 45));  // set font 
	    Restart.setForeground(fonts.orange2); // set color 
	    Restart.setBorder(new EmptyBorder(0,0,20,0));//top,left,bottom,right slight padding adjustments
    
    // CONTENT PANE // LAYOUT 
	    setLayout (new BorderLayout ()); // Use BorderLayout for panel
	    
	    north = new JPanel ();  // new north JPanel 
	    north.setLayout (new BorderLayout ()); // use border layout to vertically center 
	    
	    south = new JPanel ();  // new south JPanel 
	    south.setLayout(new FlowLayout (FlowLayout.CENTER)); // Use FlowLayout to manage Button
	    
	    center = new JPanel (); // new center JPanel 
	    center.setLayout(new BorderLayout ()); // use border layout to vertically center 
    
    // ADDING CONTENT
    
	    // North Section 
	    phrase.setHorizontalAlignment(JLabel.CENTER); // set horizontal alignment for right phrase
	    ReasonforOver.setHorizontalAlignment(JLabel.CENTER); // set horizontal alignment for text
	    north.add(ReasonforOver, BorderLayout.NORTH); // add it to north section of BorderLayout
	    north.add(phrase, BorderLayout.CENTER);
	    add (north, "North"); // add north area 
	    
	    // South Section
		    south.add(yes); // add yes button
		    south.add(no); // add no button
		       add (south, "South"); // add south area
	    
	    // Display money
		    moneyDisp.setHorizontalAlignment(JLabel.CENTER); // horizontally center 
		    center.add(moneyDisp, BorderLayout.CENTER); // add it to center section of BorderLayout
		    
	    // Play Again Message
		    Restart.setHorizontalAlignment(JLabel.CENTER); // horizontally center
		    center.add(Restart, BorderLayout. PAGE_END);  // add it to center section
	
	    add (center, "Center"); // add center section of BorderLayout 
    
    // Setting Window Attributes
    
	      pack (); // layout manager is in charge of frame size
	      setTitle ("Wheel of Fortune"); // title of window
	      setSize(800,800); // create 800x800 frame 
	      setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); // default closing operation 
	      setLocationRelativeTo (null);     // Centers the new window
    
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    if ( e.getActionCommand().equals( "1" ) )
    {   
    		//main asdfj = new main(); // create a  new main 
    		main.main(null); // run it 
    	
        this.dispose(); // closes restart window
      
    } else if ( e.getActionCommand().equals("2"))
	    {
	      this.dispose(); // close this window and terminate program
	    }
  }
  public static void main(String[] args) throws FontFormatException, IOException {
	  
	  try {
			fonts.main(null);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	  Restart test = new Restart("String 1", 1000, "thistheright"); 
	  test.getContentPane().setBackground(Color.WHITE); // set background color as white 
	  test.setVisible (true); // set it as visible
	  
  }
  
}