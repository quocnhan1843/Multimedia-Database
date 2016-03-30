/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree.process;

import UI.Dictionary;
import java.awt.Graphics2D;
import java.util.Vector;
import multidimensionaldata.control.MultiDimensionalDataStructure;
import multidimensionaldata.tree.InfoNode;
import multidimensionaldata.tree.Point;
import multidimensionaldata.tree.Point2D;
import multidimensionaldata.tree.Tree;

/**
 *
 * @author ASUS
 */
public class ProcessSearch {

    private Vector vectorSearch;
    private InfoNode infoNode;
    
    private int xPos, yPos;
    private int index ;

    public ProcessSearch() {
        vectorSearch = new Vector();
        infoNode = new InfoNode(new String(), new Point());
        xPos =  yPos = 0;
        index = 0;
    }
    
    private boolean isComplete(){
        return index >= vectorSearch.size();
    }
    
    private void reset(){
        vectorSearch.clear();
        index = 0;
        xPos = yPos = 0;
    }

    public int getSize(){
        return vectorSearch.size();
    }
    
    public void go(Tree treePaint) {
        if(isComplete()){
            MultiDimensionalDataStructure.status = MultiDimensionalDataStructure.STATE.NOTHING;
            Process.stateRun = Process.STATE.WAITING;
            treePaint.searchLabelAndPaint(infoNode.getLabel(), false);
            reset();
            return;
        }
        
        if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.PAUSE){
            return;
        }
        Process.stateRun = Process.STATE.SEARCHING;
        try{
            if(index < getSize()){
                Point2D p2dObject = (Point2D) vectorSearch.get(index);
                if(p2dObject == null){
                    Process.stateRun = Process.STATE.WAITING;
                    reset();
                    return;
                }
                xPos = p2dObject.getX();
                yPos = p2dObject.getY();
            }else{
                reset();
            }
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
        if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.SKIPBACKWARD){
            MultiDimensionalDataStructure.status = MultiDimensionalDataStructure.STATE.PLAY;
            index = 0;
        }else{
            index++;
        }
    }

    public void add(Point2D point2D) {
        vectorSearch.addElement(point2D);
    }

    public void paint(Graphics2D g2d) {        
        try{            
            int x = (int) xPos;
            int y = (int) yPos;

            int dx = 120/Process.treePaint.getNumOfDimension();
            int dy = 14;
            
            Vector v = infoNode.getPoint().getLocation();
            
            //Fill color background
            g2d.setColor(Dictionary.COLOR.BACKGROUND_NODE_WHEN_RUN.getColor());

            g2d.fillRect(x, y, 120, dy);

            for (int i = 0; i < Process.treePaint.getNumOfDimension(); i++) {
                g2d.fillRect(x + i*dx, y + dy, dx, dy);
            }

            g2d.fillRect(x, y + 2*dy, 60, dy);
            g2d.fillRect(x + 60, y + 2*dy, 60, dy);

            //draw box		
            g2d.setColor(Dictionary.COLOR.BOX_NODE.getColor());
            int s = g2d.getFont().getSize();
            g2d.drawRect(x, y, 120, dy);        

            for (int i = 0; i < Process.treePaint.getNumOfDimension(); i++) {
                g2d.drawRect(x + i*dx, y + dy, dx, dy);
            }

            g2d.drawRect(x, y + 2*dy, 60, dy);
            g2d.drawRect(x + 60, y + 2*dy, 60, dy);

            //Draw String2d
            g2d.setColor(Dictionary.COLOR.TEXT_NODE.getColor());
            g2d.drawString(infoNode.getLabel(), (Math.max(120 
                    -  infoNode.getLabel().length()*s,4))/2 
                    + (x + 4) , y + dy - 1 );
            for (int i = 0; i < v.size(); i++) {
                g2d.drawString(String.valueOf(v.get(i)), x + (i)*dx + 4, y + 2*dy - 1);
            }

            g2d.setColor(Dictionary.COLOR.DEFAULT.getColor());
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean canNext() {
        return vectorSearch.size() > 0;
    }

    public void setInfo(InfoNode infoNode) {
        this.infoNode = infoNode;
    }
    
}
