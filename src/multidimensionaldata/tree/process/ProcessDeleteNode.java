
package multidimensionaldata.tree.process;

import UI.Dictionary;
import java.awt.Graphics2D;
import java.util.Vector;
import multidimensionaldata.control.MultiDimensionalDataStructure;
import multidimensionaldata.tree.InfoNode;
import multidimensionaldata.tree.Point2D;
import multidimensionaldata.tree.Tree;

public class ProcessDeleteNode {
    
    private Vector vectorObject;
    private Vector vectorTarget;
    
    private int index = 0;
    
    private InfoNode infoObject;
    private InfoNode infoTarget;
    
    private int xObject, yOject, xTarget, yTarget;
    
    public ProcessDeleteNode(){
        vectorObject = new Vector();
        vectorTarget = new Vector();
        
        xObject = yOject = xTarget = yTarget = -200;
    }
    public void setInfo(InfoNode nodeObject, InfoNode nodeTarget){
        infoObject = nodeObject;
        infoTarget = nodeTarget;
    }
    
    public void reset(){
        vectorObject.clear();
        vectorTarget.clear();
        index = 0;
    }
    
    private boolean isComplete(){
        return (index >= getSize());
    }

    private int getSize(){
        return Math.min(vectorObject.size(), vectorTarget.size());
    }
    
    public void go(Tree treePaint) {        
        if(isComplete()){
            MultiDimensionalDataStructure.status = MultiDimensionalDataStructure.STATE.NOTHING;
            ProcessesPaintTree.stateRun = ProcessesPaintTree.STATE.WAITING;
            treePaint.deleteNodeLabel(infoObject.getLabel(), false);
            reset();
            return;
        }
        
        if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.PAUSE){
            return;
        }
        ProcessesPaintTree.stateRun = ProcessesPaintTree.STATE.DELETING;
        try{
            if(index < getSize()){
                Point2D p2dObject = (Point2D) vectorObject.get(index);
                Point2D p2dTarget = (Point2D) vectorTarget.get(index);
                if(p2dObject == null || p2dTarget == null){
                    ProcessesPaintTree.stateRun = ProcessesPaintTree.STATE.WAITING;
                    vectorObject.clear();
                    vectorTarget.clear();
                    index = 0;
                    return;
                }
                xObject = p2dObject.getX();
                yOject = p2dObject.getY();
                
                xTarget = p2dTarget.getX();
                yTarget = p2dTarget.getY();
            }else{
                reset();
                index = 0;
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

    public void addPoint(Point2D pointObject, Point2D pointTarget) {
        vectorObject.addElement(pointObject);
        vectorTarget.addElement(pointTarget);
    }
    
    public void paint(Graphics2D g2d){
        
        drawNode(g2d, xObject, yOject, infoObject);
        drawNode(g2d, xTarget, yTarget, infoTarget);
    }
    
    private void drawNode(Graphics2D g2d, int x, int y, InfoNode info){
                
        try{

            int dx = 120/ProcessesPaintTree.treePaint.getNumOfDimension();
            int dy = 14;
            
            Vector v = info.getPoint().getLocation();
            
            //Fill color background
            g2d.setColor(Dictionary.COLOR.BACKGROUND_NODE_WHEN_RUN.getColor());

            g2d.fillRect(x, y, 120, dy);

            for (int i = 0; i < v.size(); i++) {
                g2d.fillRect(x + i*dx, y + dy, dx, dy);
            }

            g2d.fillRect(x, y + 2*dy, 60, dy);
            g2d.fillRect(x + 60, y + 2*dy, 60, dy);

            //draw box		
            g2d.setColor(Dictionary.COLOR.BOX_NODE.getColor());
            int s = g2d.getFont().getSize();
            g2d.drawRect(x, y, 120, dy);        

            for (int i = 0; i < v.size(); i++) {
                g2d.drawRect(x + i*dx, y + dy, dx, dy);
            }

            g2d.drawRect(x, y + 2*dy, 60, dy);
            g2d.drawRect(x + 60, y + 2*dy, 60, dy);

            //Draw String2d
            g2d.setColor(Dictionary.COLOR.TEXT_NODE.getColor());
            g2d.drawString(info.getLabel(), (Math.max(120 -  info.getLabel().length()*s,4))/2 + (x + 4) , y + dy - 1 );
            for (int i = 0; i < v.size(); i++) {
                g2d.drawString(String.valueOf(v.get(i)), x + (i)*dx + 4, y + 2*dy - 1);
            }

            g2d.setColor(Dictionary.COLOR.DEFAULT.getColor());
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean canNext() {
        return Math.min(vectorObject.size(), vectorTarget.size()) > 0;
    }
    
}
