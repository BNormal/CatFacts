package cats;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class CatFacts {
	public static SoundManager sm;
	public static GUI window;
	public static boolean soundOn;
	public static int factType = 0;
	public static ArrayList<String> factList = new ArrayList<String>();
	public static HashMap<String, URL> soundFiles = new HashMap<String, URL>();
	
	public static void main(String[] args) {
		try {
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
			logger.setUseParentHandlers(false);
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		soundOn = true;
		sm = new SoundManager();
		sm.loadSounds();
		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new CatFacts();
					startUpMessage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public CatFacts() {
		if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
		window = new GUI();
		window.getFrame().setVisible(true);
        final PopupMenu popup = new PopupMenu();
        Image image = null;
		try {
			image = ImageIO.read(CatFacts.class.getResource("/cats/images/kitty.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        final TrayIcon trayIcon = new TrayIcon(image, "Cat Facts");
        trayIcon.setImageAutoSize(true);
        final SystemTray tray = SystemTray.getSystemTray();
       
        /*// Create a pop-up menu components
        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");
       
        //Add components to pop-up menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);*/
        
        // Create a pop-up menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem menuItem = new MenuItem("Menu");
        MenuItem exitItem = new MenuItem("Exit");
        
        // Create a listeners for menu components
        aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
					    "~ key: Get random cat fact copied to clipboard.\n" +
					    "SCROLL LOCK: Exit program.\n" +
					    "F2: Clear unique fact list ~ program will meow once list has 100 items.\n" +
					    "Random cat facts provided by https://catfact.ninja/",
					    "Cat Facts",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
        menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.getFrame().setVisible(true);
			}
		});
        exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
        
        //Add components to pop-up menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(menuItem);
        popup.add(exitItem);
       
        trayIcon.setPopupMenu(popup);
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
	}
	
	public static void startUpMessage() {
		/*JOptionPane.showMessageDialog(null,
			    "To get started click your ~ key to get a random cat fact copied to your clipboard.\n" +
			    "To close the program, click your SCROLL LOCK key or exit from the system tray. " +
			    "Have fun!",
			    "Cat Facts",
			    JOptionPane.PLAIN_MESSAGE);*/
	}
}
