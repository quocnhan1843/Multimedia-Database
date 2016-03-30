/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author ASUS
 */
public abstract class Tree {
    
    private int size;
    private int numOfDimension;
    private int xMin, xMax, yMin, yMax;
    private Graphics2D g2d;
    
    //public static enum STATE_INSERT{SUCCESSFUL
    
    public abstract Node getRoot();
    public abstract void insertNode(String label, Point points, boolean paint);
    public abstract void deleteNodeLabel(String label, boolean paint);
    public abstract void deleteNodePoint(Point point, boolean paint);
    public abstract String getName();
    public abstract void setEmpty();
    public abstract boolean isEmpty();
    public abstract void searchLabelAndPaint(String label, boolean paint);
    public abstract void searchPointAndPaint(Point point, boolean paint);
    public abstract void paintTree();
    public abstract boolean checkLabel(String str);
    public abstract boolean checkPoint(Point point);
    public abstract Node findNode(MouseEvent e);
    public abstract void resetColor();
    public abstract void setColor(Node node);
    
    public Tree(){
        size = 0;
        numOfDimension = 2;
    }
    
    public  void setSizeUp(){
        size++;
    }
    public void setSizeDown(){
        size = Math.max(size-1, 0);
    }
    public int getNumOfDimension(){
        return numOfDimension;
    }
    public void setNumOfDimension(int num){
        this.numOfDimension = num;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setGraphics(Graphics g){
        this.g2d = (Graphics2D) g;
    }    
    
    public Graphics2D getGraphics2D(){
        return this.g2d;
    }

    public int getxMin() {
        return xMin;
    }

    public void setxMin(int xMin) {
        this.xMin = xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public void setxMax(int xMax) {
        this.xMax = xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public void setyMin(int yMin) {
        this.yMin = yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }   
}
