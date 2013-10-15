package ee.ut.math.tvt.ateamplusone;

import javax.swing.JFrame;

public class Intro extends JFrame{

	public Intro(){
		
		super();
		this.setSize(800, 600);
		this.setVisible(true);
		
		this.add(new MainUI());
		
		
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Intro();
	}
}
