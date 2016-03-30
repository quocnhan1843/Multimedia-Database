package multidimensionaldata.control;

import UI.Dictionary;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import multidimensionaldata.tree.Node;
import multidimensionaldata.tree.process.Process;
import multidimensionaldata.tree.Tree;

public class Paint extends JPanel implements MouseListener,MouseMotionListener {
        private Node node;
        private Node nodePaint;
	private int preX, preY;
	private final int height;
	private static double k;
        private final Tree tree;
        private final Process runnablePaint;
    public Paint(Tree tr) {
        this.tree = tr;
        runnablePaint = new Process(tree, this);
    	k = 1.0;
    	this.setOpaque(false);
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int) screenSize.getHeight();
        this.setPreferredSize(new Dimension(12 * 6000, 16*height));
		addMouseListener(this);
		addMouseMotionListener(this);
        this.tree.setGraphics(this.getGraphics());       
    }
    
    public static void setK(double k1){
        k = k1;
    }
    
    public static double getK(){
        return k;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(k,k);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
        this.tree.setGraphics(g);
        runnablePaint.setGraphics(g2d);
        runnablePaint.paintNode(g2d);
    }
    
    public void updateLocation(MouseEvent e) {
        try{
            node.setPos(preX + (int) (e.getX() / Paint.getK()), preY + (int) (e.getY() / Paint.getK()) );
            nodePaint.setPos(preX + (int) (e.getX() / Paint.getK()), preY + (int) (e.getY() / Paint.getK()) );
            checkIn();
            repaint();
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(Process.stateRun != Process.STATE.WAITING) return;
        try{
            node = runnablePaint.getTreeMain().findNode(e);
            nodePaint = runnablePaint.getTreePaint().findNode(e);
            if(nodePaint == null) return;
            nodePaint.setColor(Dictionary.COLOR.BACKGROUND_NODE_WHEN_CHOOSE.getColor());
            preX = (int) (nodePaint.getxPos() - e.getX() / Paint.getK());
            preY = (int) (nodePaint.getyPos() - e.getY() / Paint.getK());

            if (nodePaint.contains(e))
                updateLocation(e);
        }catch(NullPointerException ex){

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(nodePaint != null) nodePaint.setColor(Dictionary.COLOR.BACKGROUND_NODE.getColor());
        nodePaint = null;
        node = null;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(nodePaint != null)
            updateLocation(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(nodePaint != null)
            updateLocation(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    private void checkIn() {
		
        if(nodePaint == null) return;

        if (0 < nodePaint.getxPos() && nodePaint.getxPos() + 120 < 24*3000) {
            if(0 < nodePaint.getyPos() && nodePaint.getyPos() + 28 < 16*height)
                    return;
        }
        int new_x = nodePaint.getxPos();
        int new_y = nodePaint.getyPos();

        if ((nodePaint.getxPos() + 120) > 24*3000) {
          new_x = 24*3000 - 120;
        }
        if (nodePaint.getxPos() < 0) {
          new_x = 0;
        }
        if ((nodePaint.getyPos() + 28) > 16*height) {
          new_y = 16*height - 28;
        }
        if (nodePaint.getyPos() < 0) {
          new_y = 0;
        }
        nodePaint.setPos(new_x, new_y);
        node.setPos(new_x, new_y);
    }
}
