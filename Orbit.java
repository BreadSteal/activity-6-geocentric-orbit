import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Animates and object orbiting around the Earth.
 *
 * @author CS121 Instructors
 * @author Caleb Schiers
 */
@SuppressWarnings("serial")
public class Orbit extends JPanel
{
	private final int INIT_WIDTH = 600;
	private final int INIT_HEIGHT = 600;
	private final int DELAY = 10; //milliseconds
	
	private final ImageIcon EARTH_ICON = new ImageIcon("earth.png");
	
	private int orbitTheta;
	private int orbitTDelta;
	
	/**
	 * Constructor. Sets the initial dimensions and starts the animation.
	 */
	public Orbit()
	{
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
		
		startAnimation();

		// TODO: Generate random theta delta value from -10 to 20.
        orbitTDelta = 2;
	}

	/**
	 * Display the square at a new location.
	 * @param page Graphics context
	 */
	public void paintComponent(Graphics page)
	{
		int width = getWidth();
		int height = getHeight();
		
		// Define earth radius and position.
        page.setColor(Color.BLACK);
        page.fillRect(height, height, width, height);
		int earthDiameter = Math.min(width/5, height/5);
		int earthX = width / 2;
		int earthY = height / 2;

		// HINT: If you end up with a solid circle of objects surrounding your earth instead of a 
		// single object smoothly circling it, try drawing (below) a filled rectangle to blank the 
		// window each time paintComponent() is called.
		
		// Draw earth
        page.clearRect(0, 0, width, height);
		page.drawImage(EARTH_ICON.getImage(), earthX - earthDiameter, earthY - earthDiameter, 
        earthDiameter * 2, earthDiameter * 2, null);
		
        page.drawOval(earthX - earthDiameter * 2, earthY - earthDiameter * 2, earthDiameter * 4 , earthDiameter * 4);

        int earthRadius = (earthDiameter / 2);
        int orbitDiamter = (earthDiameter * 2);
        //int moonX = (earthX / 3);
        //int moonY = (earthY / 3);

        Random gen = new Random();
        int genR = gen.nextInt(256);
        int genG = gen.nextInt(256);
        int genB = gen.nextInt(256);

        Color randcolor = new Color(genR, genG, genB, 125);
        page.setColor(randcolor);

        double moonX = earthX + orbitDiamter * Math.cos(Math.toRadians(orbitTheta));
        double moonY = earthY - orbitDiamter * Math.sin(Math.toRadians(orbitTheta));
        double moonX2 = earthX + orbitDiamter * Math.cos(Math.toRadians(orbitTheta * 2));
        double moonY2 = earthY - orbitDiamter * Math.sin(Math.toRadians(orbitTheta * 2));

		orbitTheta = orbitTheta + orbitTDelta;
        page.drawImage(EARTH_ICON.getImage(), (int)moonX - earthRadius / 2, (int)moonY - earthRadius / 2, earthRadius, earthRadius, null);
		page.fillOval((int)moonX - earthRadius / 2, (int)moonY - earthRadius / 2, earthRadius, earthRadius);

        page.drawImage(EARTH_ICON.getImage(), (int)moonX2 - earthRadius / 2, (int)moonY2 - earthRadius / 2, earthRadius, earthRadius, null);
		page.fillOval((int)moonX2 - earthRadius / 2, (int)moonY2 - earthRadius / 2, earthRadius, earthRadius);

        System.out.println(orbitTheta);

		// TODO: Define the radius of your object

		
		// TODO: Calculate x and y using Math.sin and Math.cos.
		// HINT: The Math.sin and Math.cos methods use radians for the parameter units. orbitTheta is in degrees.  
		//       Try using the Math.toRadians() method to convert orbitTheta from degrees to radians. This will 
		//       smooth out the orbit of your object.

		
		// TODO: Create a random color and draw your object as an oval with that random color.
		
		// Make the animation smoother
		Toolkit.getDefaultToolkit().sync();
	}

    /**
	 * sets up a JFrame and the RandomMovement panel
	 * @param args unused
	 */
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Geocentric Orbit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Orbit());
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Create an animation thread that runs periodically
	 */
	private void startAnimation()
	{
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		};
		new Timer(DELAY, taskPerformer).start();
	}
}