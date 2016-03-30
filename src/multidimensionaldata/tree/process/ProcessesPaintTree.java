/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree.process;

import UI.Dictionary;
import multidimensionaldata.control.MultiDimensionalDataStructure;
import multidimensionaldata.control.MyTable;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import multidimensionaldata.control.ControlTreePanel;
import multidimensionaldata.tree.InfoNode;
import multidimensionaldata.tree.KDimensionalTree;
import multidimensionaldata.tree.Point;
import multidimensionaldata.tree.Point2D;
import multidimensionaldata.tree.PointQuadTree;
import multidimensionaldata.tree.Tree;

public class ProcessesPaintTree implements Runnable{

    public static void addQueueInsert(InfoNode infoNode) {
        processInsertNode.add(infoNode);
    }

    public static void setNodeDelete(InfoNode nodeObject, InfoNode nodeTarget){
        processDeleteNode.setInfo(nodeObject, nodeTarget);
    }
    
    public static void setNodeSearchAndDelete(InfoNode infoNode){
        processSearchAndDelete.setInfo(infoNode);
    }
    
    public static void setNodeSearch(InfoNode infoNode){
        processSearchNode.setInfo(infoNode);
    }
    
    public static void removeNodeLabel(String label) {
        treeMain.deleteNodeLabel(label, true);
    }

    public static void removeNodePoint(Point p) {
        treeMain.deleteNodePoint(p, true);
    }

    public static void searchLabelAndPaint(String label) {
        treeMain.searchLabelAndPaint(label, true);
    }

    public static void searchPointAndPaint(Point p) {
        treeMain.searchPointAndPaint(p, true);
    }

    public static void clearComponents() {
        processInsertNode.clear();
    }

    public static void setTreePaint(Tree tree) {
        treeMain= tree;
        if(tree.getName().equals(Dictionary.Words.NAME_KDIMENSIONALTREE.getString())){
            treePaint = new KDimensionalTree(tree.getNumOfDimension());
        }else if(tree.getName().equals(Dictionary.Words.NAME_POINTQUADTREE.getString())){
            treePaint = new PointQuadTree();
        }
    }

    public static void addPointInsert(Point2D point2D, String string) {
        processInsertNode.addPoint(point2D, string);
    }
    
    public static void resetColor() {
        treePaint.resetColor();
    }

    public static void addPointSwap(Point2D point2DObject, Point2D pointTarget) {
        processDeleteNode.addPoint(point2DObject, pointTarget);
    }

    public static void addPointSearch(Point2D point2D) {
        processSearchNode.add(point2D);
    }

    public static void addPointSearchAndDelete(Point2D point2D) {
        processSearchAndDelete.add(point2D);
    }

    public Tree getTreeMain() {
        return treeMain;
    }

    public static enum STATE{WAITING, INSERTING, DELETING, SEARCHING, SEARCH_AND_DELETE};
    public static STATE stateRun = STATE.WAITING;
    
    private static Tree treeMain;
    public static Tree treePaint;
    
    private Graphics2D g2d;
    
    public static int timeSpeed = 1;
    
    private JPanel panelPaint;
    
    private static ProcessInsertNode processInsertNode = new ProcessInsertNode();
    private static ProcessDeleteNode processDeleteNode = new ProcessDeleteNode();
    private static ProcessSearchNode processSearchNode = new ProcessSearchNode();
    private static ProcessSearchAndDelete processSearchAndDelete = new ProcessSearchAndDelete();
    
    private static long timeStart = System.currentTimeMillis();

    public ProcessesPaintTree(Tree tree, JPanel panel) {
        this.panelPaint = panel;
        this.treeMain = tree;
        this.treePaint = new KDimensionalTree(tree.getNumOfDimension());
        Thread t = new Thread(this);
        t.start();
    }
    
    public void paintNode(Graphics2D g2d)  {
        if(treeMain.isEmpty()) treePaint.setEmpty();
        treePaint.paintTree();
        
        if(stateRun == ProcessesPaintTree.STATE.INSERTING){
            processInsertNode.paint(g2d);
        }else if(stateRun == ProcessesPaintTree.STATE.SEARCHING){
            MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
            processSearchNode.paint(g2d);
        }else if(stateRun == ProcessesPaintTree.STATE.DELETING){
            MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
            processDeleteNode.paint(g2d);
        }else if(stateRun == ProcessesPaintTree.STATE.SEARCH_AND_DELETE){
            MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
            processSearchAndDelete.paint(g2d);
        }
    }
    
    @Override
    public void run() {
        try {
            while (true) {
               if(stateRun == ProcessesPaintTree.STATE.WAITING){
                   if(processInsertNode.canNext()){
                       processInsertNode.next(treeMain);
                   }else if(processSearchNode.canNext()){
                       stateRun = STATE.SEARCHING;
                   }else if(processDeleteNode.canNext()){
                       stateRun = STATE.DELETING;
                   }else if(processSearchAndDelete.canNext()){
                       stateRun = STATE.SEARCH_AND_DELETE;
                   }
                   else{
                       if(MultiDimensionalDataStructure.buttonPlayPause != null)
                            MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PLAY.getString()));
                   }
               }else{
                   if(timeSpeed == 1){
                       MultiDimensionalDataStructure.resetLabelSpeed();
                   }
                   if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.SKIPFORWARD){
                       timeSpeed = 1;
                       MultiDimensionalDataStructure.labelSpeed.setText("");
                   }else if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.FORWARD){
                       Thread.sleep(8/timeSpeed);
                   }else if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.BACKWARD){
                       Thread.sleep(8*timeSpeed);
                   }else if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.NOTHING){
                       Thread.sleep(8);
                   }else if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.SKIPBACKWARD){
                       timeSpeed = 1;
                       MultiDimensionalDataStructure.labelSpeed.setText("");
                       Thread.sleep(8);
                   }else if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.PLAY){
                       MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
                       Thread.sleep(8);
                   }
               }
                if(System.currentTimeMillis() - timeStart > 1000){
                    MyTable.num += 1;
                    MyTable.num %= 2;
                    timeStart = System.currentTimeMillis();
                }
                go();
                if(ControlTreePanel.tableQueue != null)
                    ControlTreePanel.tableQueue.repaint();
                panelPaint.repaint();
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }
    private void go(){
        if(stateRun == ProcessesPaintTree.STATE.INSERTING) processInsertNode.go(treePaint);
        else if(stateRun == ProcessesPaintTree.STATE.DELETING) processDeleteNode.go(treePaint);
        else if(stateRun == ProcessesPaintTree.STATE.SEARCHING) processSearchNode.go(treePaint);
        else if(stateRun == ProcessesPaintTree.STATE.SEARCH_AND_DELETE) processSearchAndDelete.go(treePaint);
    }
    
    private void changeButtonPlayPause(){
        MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
        //ProcessesPaintTree.stateRun = ProcessesPaintTree.STATE.INSERTING;
    }
    
    public void setGraphics(Graphics2D g2d){
        this.treePaint.setGraphics(g2d);
    }
    
    private ImageIcon getIcon(String iconStr){
        return new ImageIcon(this.getClass().getResource("/image/" + iconStr));
    }
    
    public Tree getTreePaint(){
        return treePaint;
    }
}
