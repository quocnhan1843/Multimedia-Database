/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

import UI.Dictionary;
import UI.Dictionary.Words;
import java.awt.Graphics2D;
import java.util.Vector;

/**
 *
 * @author ASUS
 */
public class MXQuadNode extends Node{
    
    private MXQuadNode nodeNW;
    private MXQuadNode nodeNE;
    private MXQuadNode nodeSE;
    private MXQuadNode nodeSW;
    private MXQuadNode parent;

    public MXQuadNode() {
        this.nodeNW = null;
        this.nodeNE = null;
        this.nodeSE = null;
        this.nodeSW = null;
    }

    public MXQuadNode(String label, Point point) {
        super(label, point);
        
        this.nodeNW = null;
        this.nodeNE = null;
        this.nodeSE = null;
        this.nodeSW = null;
    }

    public MXQuadNode(MXQuadNode nodeNW, MXQuadNode nodeNE, 
            MXQuadNode nodeSE, MXQuadNode nodeSW, MXQuadNode parent) {
        this.nodeNW = nodeNW;
        this.nodeNE = nodeNE;
        this.nodeSE = nodeSE;
        this.nodeSW = nodeSW;
        this.parent = parent;
    }
    
    public MXQuadNode getNodeNW() {
        return nodeNW;
    }

    public void setNodeNW(MXQuadNode nodeNW) {
        this.nodeNW = nodeNW;
    }

    public MXQuadNode getNodeNE() {
        return nodeNE;
    }

    public void setNodeNE(MXQuadNode nodeNE) {
        this.nodeNE = nodeNE;
    }

    public MXQuadNode getNodeSE() {
        return nodeSE;
    }

    public void setNodeSE(MXQuadNode nodeSE) {
        this.nodeSE = nodeSE;
    }

    public MXQuadNode getNodeSW() {
        return nodeSW;
    }

    public void setNodeSW(MXQuadNode nodeSW) {
        this.nodeSW = nodeSW;
    }

    public MXQuadNode getParent() {
        return parent;
    }

    public void setParent(MXQuadNode parent) {
        this.parent = parent;
    }

    public boolean isLeaves() {
        return this.nodeNW == null && this.nodeNE == null 
                && this.nodeSE == null && this.nodeSW == null;
    }

    void paint(Graphics2D g) {
        if(this == null ) return;
        
        int x = this.getxPos();
        int y = this.getyPos();
        
        int height = Dictionary.SIZE.HEIGHT.getValue();
        int width = Dictionary.SIZE.WIDTH.getValue();
		
    	int dx = width/2;
    	int dy = height/3;
        
        Vector v = this.getPoint().getLocation();
        //Fill color background
        g.setColor(this.getColor());
        
        g.fillRect(x, y, width, 3*dy);
        
        //draw box		
        g.setColor(Dictionary.COLOR.DEFAULT.getColor());
        int s = g.getFont().getSize();
        g.drawRect(x, y, width, dy);        
        
        for (int i = 0; i < v.size(); i++) {
            g.drawRect(x + i*dx, y + dy, dx, dy);
        }
    	
        
        int d = width/4;
        
    	g.drawRect(x, y + 2*dy, d, dy);
        g.drawRect(x + d, y + 2*dy, d, dy);
        g.drawRect(x + 2*d, y + 2*dy, d, dy);
        g.drawRect(x + 3*d, y + 2*dy, d, dy);
        
        //Draw String
        g.setColor(Dictionary.COLOR.DEFAULT.getColor());
        if(this.getLabel().equals(Words.EMPTY_NODE.getString())){
            return;
        }
        //if(!this.getLabel().equals(Words.EMPTY_NODE.getString()))
        g.drawString(this.getLabel(), (Math.max(width -  this.getLabel().length()*s,4))/2 + (x + 4) , y + dy - 1 );
        for (int i = 0; i < v.size(); i++) {
            g.drawString(String.valueOf(v.get(i)), x + (i)*dx + 4, y + 2*dy - 1);
        }
        
        g.setColor(Dictionary.COLOR.DEFAULT.getColor());
    }

    int getxVal() {
        return (int) this.getPoint().getLocation().get(0);
    }

    int getyVal() {
        return (int) this.getPoint().getLocation().get(1);
    }

    void coppyNode(MXQuadNode matrixNode) {
        this.setLabel(matrixNode.getLabel());
        this.setPoint(new Point(matrixNode.getPoint().getLocation()) );
        this.setColor(matrixNode.getColor());
    }
    
}
