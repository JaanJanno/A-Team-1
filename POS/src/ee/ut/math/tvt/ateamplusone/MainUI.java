package ee.ut.math.tvt.ateamplusone;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;

public class MainUI extends JPanel {
	
	public MainUI(){
		
		ArrayList a = readFile("version.properties");
		System.out.println(a.get(0));
		
		repaint();
	}
	
	public ArrayList<String> readFile(String address){
		
		ArrayList<String> vastus = new ArrayList<>();
		
		try {
			
			Scanner sc = new java.util.Scanner(new File(address));
			
			while (sc.hasNextLine()) {
				
			    vastus.add(sc.nextLine());
			}
			
			return vastus;
			
		} catch (Exception e) {
			
			return null;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setColor(new Color(255,0,0));
		g.fillRect(10, 10, 300, 300);
		//g.drawRect(100, 100, 100, 100);
		g.setColor(new Color(0,0,0));
		g.drawString("Team name: A-Team+1", 100, 100);
		g.drawString("Team leader: Jaan Janno", 100, 150);
		g.drawString("Leader's e-mail: jaan911@gmail.com", 100, 200);
		g.drawString("Our epic teammembers:", 100, 250);
		g.drawString("Jaan Janno", 100, 300);
		g.drawString("Tõnis Kasekamp", 100, 350);
		g.drawString("Silver Mazko", 100, 400);
		g.drawString("Juhan-Rasmus Risti", 100, 450);
		g.drawString("Sander Tiganik", 100, 500);
		
		g.drawString("Version number: 123", 100, 550);
	}
	
}
