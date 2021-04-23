package cats;

import eu.hansolo.custom.SteelCheckBox;
import eu.hansolo.tools.ColorDef;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class GUI {

	private JFrame frmMeowCat;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmMeowCat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}
	
	public JFrame getFrame() {
		return frmMeowCat;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMeowCat = new JFrame();
		frmMeowCat.getContentPane().setBackground(Color.DARK_GRAY);
		frmMeowCat.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/cats/images/kitty.png")));
		frmMeowCat.setTitle("CatFacts");
		frmMeowCat.setBounds(100, 100, 340, 280);
		frmMeowCat.setLocationRelativeTo(null);
		frmMeowCat.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmMeowCat.getContentPane().setLayout(new BorderLayout(0, 0));
		ImagePanel panel = new ImagePanel("cat_wallpaper.jpg", frmMeowCat.getWidth(), frmMeowCat.getHeight());
		panel.setBackground(Color.BLACK);
		frmMeowCat.getContentPane().add(panel);
		panel.setLayout(null);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFocusable(false);
		comboBox.setBounds(69, 12, 162, 20);
		panel.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CatFacts.factType = comboBox.getSelectedIndex();
				//CatFacts.factList.clear();
			}
		});
		comboBox.setBackground(Color.WHITE);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Cat Facts", "Panda Facts", "Donald Trump", "Dad Jokes"}));
		
		SteelCheckBox box = new SteelCheckBox();
		box.setFocusable(false);
		box.setBounds(241, 8, 100, 26);
		panel.add(box);
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CatFacts.soundOn = !CatFacts.soundOn;
				box.setSelected(CatFacts.soundOn);
			}
		});
		box.setFont(new Font("Arial", Font.BOLD, 11));
		box.setForeground(Color.WHITE);
		box.setOpaque(false);
		box.setColored(true);
		box.setSelected(CatFacts.soundOn);
		box.setText("Sound");
		box.setSelectedColor(ColorDef.GREEN);
		box.setRised(true);
		
		JLabel lblFacts = new JLabel("Fact Type:");
		lblFacts.setForeground(Color.WHITE);
		lblFacts.setBounds(10, 15, 71, 14);
		panel.add(lblFacts);
		
		JTextArea txtrKeyGet = new JTextArea();
		txtrKeyGet.setEditable(false);
		txtrKeyGet.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrKeyGet.setLineWrap(true);
		txtrKeyGet.setWrapStyleWord(true);
		txtrKeyGet.setText("Press Tilda ~ to get random cat fact copied to clipboard.\r\n\r\nSCROLL LOCK to exit program.\r\n\r\nF2: Clear unique fact list\r\n\r\nRandom cat facts provided by https://catfact.ninja/\r\nNote: Program will meow once list has 100 items.");
		txtrKeyGet.setForeground(Color.WHITE);
		txtrKeyGet.setOpaque(false);
		txtrKeyGet.setBounds(10, 40, 304, 190);
		panel.add(txtrKeyGet);
	}
}
