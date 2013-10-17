package ee.ut.math.tvt.ateamplusone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainUI extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<String> versionInfo;
	ArrayList<String> appInfo;
	
	BufferedImage logo;
	
	
	public MainUI(){
		
		appInfo     = readFile("application.properties");
		versionInfo = readFile("version.properties");
		
		logo = readImage("logo.png");
		
		
	}
	
	public ArrayList<String> readFile(String address){
		
		ArrayList<String> vastus = new ArrayList<>();
		
		try {
			
			Scanner sc = new java.util.Scanner(new File(address));
			
			while (sc.hasNextLine()) {
				
			    vastus.add(sc.nextLine());
			}
			
			sc.close();
			
			return vastus;
			
		} catch (Exception e) {
			
			return null;
		}
	}
	
	public BufferedImage readImage(String aadress){
		
		try {
			
			BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream(aadress));
			return img;
			
		} catch (Exception ex) {
		    	
			System.out.println("Ei leia faili! " + aadress);
			return null;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setColor(new Color(255,0,0));
		g.fillRect(10, 10, 300, 300);
		g.drawImage(logo, 300, 10, null);
		g.setColor(new Color(0,0,0));
		g.drawString("Team name: " + appInfo.get(1), 		25,  25);
		g.drawString("Team leader: " + appInfo.get(3), 		25,  50);
		g.drawString("Leader's e-mail: " + appInfo.get(5), 	25,  75);
		g.drawString("Our teammembers:", 				25, 100);
		g.drawString(""+ appInfo.get(7),					25, 150);
		g.drawString(""+ appInfo.get(8), 					25, 175);
		g.drawString(""+ appInfo.get(9), 					25, 200);
		g.drawString(""+ appInfo.get(10), 					25, 225);
		g.drawString(""+ appInfo.get(11), 					25, 250);

		
		g.drawString("Version number: " + versionInfo.get(1) + "." + versionInfo.get(3) + "." +  versionInfo.get(5), 25, 300);
	}
	
}
