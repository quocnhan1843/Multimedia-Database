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
    
}
