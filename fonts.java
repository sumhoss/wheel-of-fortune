import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.omg.CORBA_2_3.portable.InputStream;

public class fonts {
	static Color purple, turquoise, orange, orange2; 
	
	public static void main(String[] args) throws FontFormatException, IOException {
		// TODO Auto-generated method stub
		
		// fonts
		
		// JUST SMILE
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Just Smile.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
		// HK-NOVA MEDIUM
		try {
		     GraphicsEnvironment ge1 = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge1.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("HKNOVA-Medium.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
		// FUTURA
		try {
		     GraphicsEnvironment ge2 = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge2.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Futura.ttc")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		// colors 
			purple = new Color(70,32,102);
			
			turquoise = new Color(0, 170, 160);
			
			orange = new Color(255, 122, 90);
			
			orange2 = new Color(255, 184, 95);
		
		
	
	}

}
