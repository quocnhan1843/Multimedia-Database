/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

import UI.Dictionary;
import multidimensionaldata.control.Paint;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 *
 * @author ASUS
 */
public abstract class Node {
    
    private Point point;
    private int xPos, yPos;
    private Color color;
    private String label;
    
    public Node(){
        point = new Point();
        label = "";
        xPos = 12*3000 + 500;
        yPos = 50;
        this.color = Dictionary.COLOR.BACKGROUND_NODE.getColor();
    }

    public Node(String label, Point point, int xPos, int yPos, Color color) {
        this.point = point;
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
        this.label = label;
    }
    
    public Node(String label, Point point){
        this.label = label;
        this.point = point;
        this.color = Dictionary.COLOR.BACKGROUND_NODE.getColor();
    }
   
    public void setColor(){
        this.color = Dictionary.COLOR.BACKGROUND_NODE.getColor();
    }
    public void setColor(Color c){
        this.color = c;
    }
    public Color getColor(){
        return this.color;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public void setPos(int x, int y){
        this.setxPos(x);
        this.setyPos(y);
    }
    public void swapNode(Node node){
        String labelNode = node.getLabel();
        node.setLabel(this.label);
        this.label = labelNode;
        
        Vector v = node.getPoint().getLocation();
        node.setPoint(point);
        this.point =new Point(v);
    }
    public boolean less(Node node, int k) {
        return this.getPoint().lessPoint(node.getPoint(),k);
    }
    public boolean greaterNode(Node node, int k) {
        return point.greaterPoint(node.getPoint(), k);
    }
    public boolean contains(MouseEvent ev){
        if(this.getxPos() <= ev.getX() / Paint.getK() && ev.getX()/ Paint.getK() <= this.getxPos() + 120){
                if(this.getyPos() <= ev.getY()/ Paint.getK() && ev.getY()/ Paint.getK() <= this.getyPos() + 3*14) return true;
                return false;
        }
        return false;
    }
}
