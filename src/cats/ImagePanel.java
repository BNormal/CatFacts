package cats;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel{

    private BufferedImage image = null;

    public ImagePanel(String fileName, int width, int height) {
    	try {
    		image = ImageIO.read(GUI.class.getResource("/cats/images/" + fileName));
    		image = resizeImage(image, width, height);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, this);        
    }
    
    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }
    
}