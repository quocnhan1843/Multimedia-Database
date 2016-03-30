/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree.process;

import UI.Dictionary;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Vector;
import javax.swing.ImageIcon;
import multidimensionaldata.control.ControlTreePanel;
import multidimensionaldata.control.MultiDimensionalDataStructure;
import multidimensionaldata.tree.InfoNode;
import multidimensionaldata.tree.Point2D;
import multidimensionaldata.tree.Tree;

/**
 *
 * @author ASUS
 */
public class ProcessInsert {

    private double xInsert=-200, yInsert = -200;
    
    private Queue queueInfoNode = new ArrayDeque<>();
    private Vector queuePoint = new Vector();
    private Vector vectorString = new Vector();
    
    private int index = 0;
    
    private InfoNode info;
    
    public void reset(){
        queuePoint.clear();
        vectorString.clear();
        index = 0;
    }
    
    public void next(Tree tree){
        info = (InfoNode) queueInfoNode.poll();
        Process.stateRun = Process.STATE.INSERTING;
        MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
        tree.insertNode(info.getLabel(), info.getPoint(), true);
        ControlTreePanel.scrollPane.getViewport()
                        .setViewPosition(new java.awt.Point(0, 21*(tree.getSize() - 1)));
        index = 0;
    }

    public void go(Tree treePaint) {
        if(queuePoint.isEmpty()){
            treePaint.insertNode(info.getLabel(), info.getPoint(), false);
            if(queueInfoNode.isEmpty()){
                MultiDimensionalDataStructure.status = MultiDimensionalDataStructure.STATE.NOTHING;
            }
            Process.stateRun = Process.STATE.WAITING;
            index = 0;
            return;
        }
        
        if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.PAUSE){
            return;
        }
        Process.stateRun = Process.STATE.INSERTING;
        try{
            if(index < queuePoint.size()){
                Point2D p2d = (Point2D) queuePoint.get(index);
                if(p2d == null){
                    Process.stateRun = Process.STATE.WAITING;
                    index = 0;
                    return;
                }
                xInsert = p2d.getX();
                yInsert = p2d.getY();
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

    boolean canNext() {
        return !queueInfoNode.isEmpty();
    }

    void paint(Graphics2D g2d) {
        try{
            
            int x = (int) xInsert;
            int y = (int) yInsert;

            int height = Dictionary.SIZE.HEIGHT.getValue();
            int width = Dictionary.SIZE.WIDTH.getValue();
            
            int dx = width/Process.treePaint.getNumOfDimension();
            int dy = height/3;
            
            Vector v = info.getPoint().getLocation();
            
            //Fill color background
            g2d.setColor(Dictionary.COLOR.BACKGROUND_NODE_WHEN_RUN.getColor());

            g2d.fillRect(x, y, width, height);

            //draw box		
            g2d.setColor(Dictionary.COLOR.BOX_NODE.getColor());
            int s = g2d.getFont().getSize();
            g2d.drawRect(x, y, width, dy);        

            for (int i = 0; i < v.size(); i++) {
                g2d.drawRect(x + i*dx, y + dy, dx, dy);
            }

            g2d.drawRect(x, y + 2*dy, width/2, dy);
            g2d.drawRect(x + width/2, y + 2*dy, width/2, dy);

            //Draw String2d
            g2d.setColor(Dictionary.COLOR.TEXT_NODE.getColor());
            g2d.drawString(info.getLabel(), (Math.max(width -  info.getLabel()
                    .length()*s,4))/2 + (x + 4) , y + dy - 1 );
            for (int i = 0; i < v.size(); i++) {
                g2d.drawString(String.valueOf(v.get(i)), x + (i)*dx + 4, y + 2*dy - 1);
            }

            g2d.setColor(Dictionary.COLOR.DEFAULT.getColor());
            
            g2d.setFont(new Font(Dictionary.Font.DEFAULT.getString()
                    , Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
            g2d.drawString((String) vectorString.get(index), x + width + 10, y + 20);
            
        }catch(Exception ex){}
    }

    void add(InfoNode infoNode) {
        queueInfoNode.add(infoNode);
    }

    void addPoint(Point2D point2D, String string) {
        queuePoint.add(point2D);
        vectorString.addElement(string);
    }

    void clear() {
        queueInfoNode.clear();
        queuePoint.clear();
        vectorString.clear();
        index = 0;
    }
    private ImageIcon getIcon(String iconStr){
        return new ImageIcon(this.getClass().getResource("/image/" + iconStr));
    }
    
}
