/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

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
}
