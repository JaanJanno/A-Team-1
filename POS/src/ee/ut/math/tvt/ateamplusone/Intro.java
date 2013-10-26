package ee.ut.math.tvt.ateamplusone;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

public class Intro extends JFrame{

	private static final long serialVersionUID = 1L;
	static Logger log4j = Logger.getLogger("test.program");

	public Intro(){
		
		super();
		this.setVisible(true);
		JPanel ui = new MainUI();
		this.add(ui);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 400);
	}

	public static void main(String[] args) {
		log4j.debug("Creating screen");
		new Intro();
	}
}
