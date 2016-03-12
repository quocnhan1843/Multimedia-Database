package multidimensionaldata.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JViewport;

public class MyViewport extends JViewport {
	
	private static final int TILE = 6;
    public MyViewport() {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(6 * TILE, 6 * TILE));
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}