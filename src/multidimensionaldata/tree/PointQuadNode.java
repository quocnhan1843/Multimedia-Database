/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

import UI.Dictionary;
import java.awt.Graphics2D;
import java.util.Vector;

/**
 *
 * @author ASUS
 */
public class PointQuadNode extends Node{

    private PointQuadNode nodeNW;
    private PointQuadNode nodeNE;
    private PointQuadNode nodeSE;
    private PointQuadNode nodeSW;
    private PointQuadNode parent;
    
    public PointQuadNode() {
        super();
        nodeNW = null;
        nodeNE = null;
        nodeSE = null;
        nodeSW = null;
    }
    
    public PointQuadNode(String label, Point point) {
        super(label, point);
        nodeNW = null;
        nodeNE = null;
        nodeSE = null;
        nodeSW = null;
    }

    public PointQuadNode(Node node) {
        super();
        this.setPoint(node.getPoint());
        this.setColor(node.getColor());
        this.setLabel(node.getLabel());
        this.setPos(node.getxPos(), node.getyPos());
        nodeNW = null;
        nodeNE = null;
        nodeSE = null;
        nodeSW = null;
    }

    public PointQuadNode getNodeNW() {
        return nodeNW;
    }

    public void setNodeNW(PointQuadNode nodeNW) {
        this.nodeNW = nodeNW;
    }

    public PointQuadNode getNodeNE() {
        return nodeNE;
    }

    public void setNodeNE(PointQuadNode nodeNE) {
        this.nodeNE = nodeNE;
    }

    public PointQuadNode getNodeSE() {
        return nodeSE;
    }

    public void setNodeSE(PointQuadNode nodeSE) {
        this.nodeSE = nodeSE;
    }

    public PointQuadNode getNodeSW() {
        return nodeSW;
    }

    public void setNodeSW(PointQuadNode nodeSW) {
        this.nodeSW = nodeSW;
    }    

    public PointQuadNode getParent() {
        return parent;
    }

    public void setParent(PointQuadNode parent) {
        this.parent = parent;
    }    
    public boolean isLeaves() {
        return this.nodeNW == null && this.nodeNE == null 
                && this.nodeSE == null && this.nodeSW == null;
    }
    public void paint(Graphics2D g){
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
        g.drawString(this.getLabel(), (Math.max(width -  this.getLabel().length()*s,4))/2 + (x + 4) , y + dy - 1 );
        for (int i = 0; i < v.size(); i++) {
            g.drawString(String.valueOf(v.get(i)), x + (i)*dx + 4, y + 2*dy - 1);
        }
        
        g.setColor(Dictionary.COLOR.DEFAULT.getColor());
    }
}
