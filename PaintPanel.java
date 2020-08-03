/**
 * PaintPanel.java
 *
 * A class to make graphics easier for CSCI 235 students
 * 
 * @author Thomas VanDrunen (mod by CGG)
 * CSCI 235, Wheaton College
 * 
 */
import java.awt.*;

public class PaintPanel extends Component {

    /**
     * The object which will do the actual 
     * "painting" of the graphics.
     */
    private Painter painter;

    /**
     * The width and height (in pixels) of this panel.
     */
    private int uWidth, uHeight;

    /**
     * Constructor.
     * @param uWidth The width of this panel
     * @param uHeight The height of this panel
     */
    public PaintPanel(int uWidth, int uHeight) {
        super();
        this.uWidth = uWidth;
        this.uHeight = uHeight;
        setSize(uWidth, uHeight);
        setPreferredSize(new Dimension(uWidth, uHeight));
    }

    /**
     * Get the width.
     * @return The width
     */
    public int width() { return uWidth; }

    /**
     * Get the height.
     * @return The height
     */
    public int height() { return uHeight; }

    /**
     * Set the object to paint this panel.
     * @param painter The painter for this panel
     */
    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    /*
     * Call this.repaint() to update the displayed panel.
     */

    /**
     * Paint (change the display) of this panel.
     * For this we rely on the painter.
     * @param g The object controlling the graphics
     */
    public void paint(Graphics g) {
        super.paint(g);
        if (painter != null) {
	    painter.paint(g);
	} else {
	    g.drawString("no painter", 20, 20);
	}
    }

}
