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
public class KDimensionalNode extends Node{
    private int level;
    private KDimensionalNode parent;
    private KDimensionalNode leftChild;
    private KDimensionalNode rightChild;

    public KDimensionalNode() {
        super();
        this.level = -1;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }
    
    public KDimensionalNode(int level, KDimensionalNode parent
            , KDimensionalNode leftChild
            , KDimensionalNode rightChild) {
        super();
        this.level = level;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
    
    public KDimensionalNode(String label, Point p) {
        super(label,p);
        this.level = -1;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }
    
    public KDimensionalNode(Node node, int level, KDimensionalNode leftChild
            , KDimensionalNode rightChild){
        super();
        this.setPoint(node.getPoint());
        this.setColor(node.getColor());
        this.setLabel(node.getLabel());
        this.setLevel(level);
        this.setLeftChild(leftChild);
        this.setRightChild(rightChild);
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public KDimensionalNode getParent() {
        return parent;
    }

    public void setParent(KDimensionalNode parent) {
        this.parent = parent;
    }

    public KDimensionalNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(KDimensionalNode leftChild) {
        this.leftChild = leftChild;
        if(leftChild != null) leftChild.setParent(this);        
    }

    public KDimensionalNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(KDimensionalNode rightChild) {
        this.rightChild = rightChild;
        if(rightChild != null) rightChild.setParent(this);
    }
    
    public boolean isLeaves() throws NullPointerException{
        return (this.leftChild == null && this.rightChild == null);
    }

    boolean isLeftChild(KDimensionalNode current) {
        if(current == null) return false;
        if(current.getLeftChild() == null) return false;
        return current.getLeftChild().equals(this);
    }
    public void paint(Graphics2D g, int num){
        if(this == null ) return;
        
        int x = this.getxPos();
        int y = this.getyPos();
        
        int height = Dictionary.SIZE.HEIGHT.getValue();
        int width = Dictionary.SIZE.WIDTH.getValue();
		
    	int dx = width/num;
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
    	
    	g.drawRect(x, y + 2*dy, width/2, dy);
        g.drawRect(x + width/2, y + 2*dy, width/2, dy);
        
        //Draw String
        g.setColor(Dictionary.COLOR.DEFAULT.getColor());
        g.drawString(this.getLabel(), (Math.max(width -  this.getLabel().length()*s,4))/2 + (x + 4) , y + dy - 1 );
        for (int i = 0; i < v.size(); i++) {
            g.drawString(String.valueOf(v.get(i)), x + (i)*dx + 4, y + 2*dy - 1);
        }
        
        g.setColor(Dictionary.COLOR.DEFAULT.getColor());
        
        if(this.getLeftChild() != null){
        	g.drawLine(this.getxPos() + width/4, this.getyPos() + dy*3
                        , this.getLeftChild().getxPos() + width/2, this.getLeftChild().getyPos());
        }
        if(this.getRightChild() != null){
        	g.drawLine(this.getxPos() + 3*(width/4), this.getyPos() + dy*3
                        , this.getRightChild().getxPos() + width/2, this.getRightChild().getyPos());
        }
    }
}
