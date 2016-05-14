package multidimensionaldata.control;

import UI.Dictionary;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import multidimensionaldata.tree.Tree;

public class PaintTop extends JPanel implements Runnable{
    
    private Tree tree;
    
    private int x, y;
    private int w = 1300;
    private String s;
    public PaintTop(Tree tr) {
        tree = tr;
        s = tree.getName();
        this.x = 100;
        this.y = 30;
        
        Thread t = new Thread(this);
        t.start();
    }
    
    public void setTree(Tree tree){
        this.tree = tree;
    }

    public void setW(int w) {
        this.w = w;
    }    
    public void paintComponent(Graphics g){
        
        this.w = MultiDimensionalDataStructure.getTopWidth();
        s = tree.getName();
        if(s.equals(Dictionary.Words.NAME_KDIMENSIONALTREE.getString())){
            s = Dictionary.Words.K_DIMENSIONNAL_TREE.getString();
        }else if(s.equals(Dictionary.Words.NAME_POINTQUADTREE.getString())){
            s = Dictionary.Words.POINT_QUADTREE.getString();
        }else{
            s = Dictionary.Words.MATRIX_QUADTREE.getString();
        }
        s = s.toUpperCase();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
        drawTitle(g2d);
        drawTree(g2d);
    }

    private void drawTitle(Graphics2D g) {
        g.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                , Font.BOLD, Dictionary.Font_Size.TITLE.getValue()));
        g.setColor(Color.BLUE);
        
        g.drawString(s, x, y);
        g.drawString(s, x + w, y);
    }

    private void drawTree(Graphics2D g) {        
    }

    @Override
    public void run() {
        try {
            while (true) {
              go();
              repaint();
              Thread.sleep(500 / 24);
            }
        } catch (InterruptedException ie) {}
    }

    private void go() {
        x -= 1;
        if(x < -s.length()*Dictionary.Font_Size.TITLE.getValue()) x += w;
        x %= w;
    }
}
//<</editor-fold>