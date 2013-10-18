package ee.ut.math.tvt.ateamplusone;


import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
* Log4j logger
*/

public class Intro extends JFrame{

	private static final long serialVersionUID = 1L;
	static Logger log4j = Logger.getLogger("test.program");

	public Intro(){
		
		super();
		this.setSize(0, 0);
		this.setVisible(true);
		
		JPanel ui = new MainUI();
		this.add(ui);

		this.setSize(900, 400);
	}

	public static void main(String[] args) {
		log4j.debug("Creating screen");
		new Intro();
	}
}
