/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

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
}
