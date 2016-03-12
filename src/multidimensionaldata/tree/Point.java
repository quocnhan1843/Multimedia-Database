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
public class Point {
    private final Vector location;

    public Point() {
        location = new Vector<>();
    }
    
    public Point(Vector v) {
        location = new Vector<>();
        v.stream().forEach((v1) -> {
            location.addElement(v1);
        });
    }
    
    public Point(int size){
        location = new Vector<>(size);
    }
    
    public void setPoint(){
        for(int i=0; i<location.size(); i++){
            location.set(i, 0);
        }
    }
    
    public boolean equalPoint(Point point){
        for(int i=0; i<location.size(); ++i){
            if(location.elementAt(i) != point.location.elementAt(i)) return false;
        }
        return true;
    }
    
    public boolean greaterPoint(Point point, int k){
        try{
            return Integer.valueOf(location.elementAt(k).toString()).intValue()
                    >= Integer.valueOf(point.location.elementAt(k).toString()).intValue();
        }catch(Exception e){return false;}
    }
    
    public boolean lessPoint(Point point, int k){
        try{
            return Integer.valueOf(location.elementAt(k).toString()).intValue()
                    < Integer.valueOf(point.location.elementAt(k).toString()).intValue();
        }catch(Exception e) {return false;}
    }

    public Vector getLocation() {
        return location;
    }
    
    public int getSize(){
        return location.size();
    }
    
}