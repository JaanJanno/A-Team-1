package ee.ut.math.tvt.ateamplusone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IntroUI extends JFrame {

	private static final long serialVersionUID = 1L;

	ArrayList<String> versionInfo;
	ArrayList<String> appInfo;

	BufferedImage logo;
	// These are essentially dictionaries
	Properties application = new Properties();
	Properties version = new Properties();

	public IntroUI() {

		try {
			application.load(this.getClass().getResourceAsStream(
					"/resources/application.properties"));
			version.load(this.getClass().getResourceAsStream(
					"/resources/version.properties"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logo = readImage("/resources/logo.png");

		JPanel panel = new SplashScreen();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 400);
		this.add(panel);

	}

	public BufferedImage readImage(String aadress) {

		try {

			BufferedImage img = ImageIO.read(this.getClass()
					.getResourceAsStream(aadress));
			return img;

		} catch (Exception ex) {

			System.out.println("Ei leia faili! " + aadress);
			return null;
		}
	}

	private class SplashScreen extends JPanel {

		private static final long serialVersionUID = 1L;

		public SplashScreen() {
		}

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			g.setColor(new Color(255, 0, 0));
			g.fillRect(10, 10, 300, 300);
			g.drawImage(logo, 300, 10, null);
			g.setColor(new Color(0, 0, 0));
			g.drawString("Team name: " + application.getProperty("team.name"),
					25, 25);
			g.drawString(
					"Team leader: " + application.getProperty("team.leader"),
					25, 50);
			g.drawString(
					"Leader's e-mail: " + application.getProperty("team.email"),
					25, 75);
			g.drawString("Our teammembers:", 25, 100);
			g.drawString(application.getProperty("team.jaan"), 25, 150);
			g.drawString(application.getProperty("team.tk"), 25, 175);
			g.drawString(application.getProperty("team.sander"), 25, 200);
			g.drawString(application.getProperty("team.silver"), 25, 225);
			g.drawString(application.getProperty("team.juhan"), 25, 250);

			g.drawString(
					"Version number: "
							+ version.getProperty("build.major.number") + "."
							+ version.getProperty("build.minor.number") + "."
							+ version.getProperty("build.revision.number"), 25,
					300);
		}
	}

}
