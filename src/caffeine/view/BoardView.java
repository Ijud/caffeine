package caffeine.view;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import caffeine.CaffeineGame;
/**
   A panel for displaying the board.
 */

@SuppressWarnings("serial")
public class BoardView extends JPanel{
  //private BufferedImage view = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
  BufferedImage view;
  private Camera cam;

  public BoardView() {
    cam = new Camera(getPreferredSize());
    try{
      view = ImageIO.read(new File("res/thief.png"));
    } catch (Exception e){};
  }

  public Camera getCamera(){
    return cam;
  }

  /**
       Paints the panel.
   */
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    CaffeineGame.instance().world().paint(g2);
  }

  /**
       Returns the preferred size of this panel.
   */
  public Dimension getPreferredSize() {
    return new Dimension(600, 400);
  }
}

