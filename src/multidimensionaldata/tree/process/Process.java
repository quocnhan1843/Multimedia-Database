/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree.process;

import UI.Dictionary;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import multidimensionaldata.control.ControlTreePanel;
import multidimensionaldata.control.MultiDimensionalDataStructure;
import multidimensionaldata.control.MyTable;
import multidimensionaldata.tree.InfoNode;
import multidimensionaldata.tree.KDimensionalTree;
import multidimensionaldata.tree.Point;
import multidimensionaldata.tree.Point2D;
import multidimensionaldata.tree.PointQuadTree;
import multidimensionaldata.tree.Tree;

/**
 *
 * @author ASUS
 */
public class Process implements Runnable{
    
    //Phan khai bao
    
     public static enum STATE{WAITING, INSERTING, DELETING, SEARCHING};
    public static STATE stateRun = STATE.WAITING;
    
    private static Tree treeMain; //Cay chinh dung de cau truc
    public static Tree treePaint; //Cay ve ra man hinh
    
    private Graphics2D g2d; //Thu vien ve
    
    public static int timeSpeed = 1; //Toc do chay cua tien trinh
    
    private JPanel panelPaint;
    
    private static ProcessInsert processInsert = new ProcessInsert();
    private static ProcessDelete processDelete = new ProcessDeleteDimensional();
    private static ProcessSearch processSearch = new ProcessSearch();
    
    private static long timeStart = System.currentTimeMillis();
    
    //Ket thuc phan khai bao
    
    public static void addQueueInsert(InfoNode infoNode) {
        processInsert.add(infoNode);
    }

    public static void setNodeDelete(InfoNode nodeObject, InfoNode nodeTarget){
        processDelete.setInfo(nodeObject, nodeTarget);
    }
    
    public static void setNodeDelete(InfoNode nodeObject){
        processDelete.setInfo(nodeObject);
    }
    
    public static void setNodeSearch(InfoNode infoNode){
        processSearch.setInfo(infoNode);
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
        processInsert.clear();
    }

    public static void setTreePaint(Tree tree) {
        treeMain= tree;
        if(tree.getName().equals(Dictionary.Words.NAME_KDIMENSIONALTREE.getString())){
            treePaint = new KDimensionalTree(tree.getNumOfDimension());
            processDelete = new ProcessDeleteDimensional();
        }else if(tree.getName().equals(Dictionary.Words.NAME_POINTQUADTREE.getString())){
            treePaint = new PointQuadTree();
            processDelete = new ProcessDeletePointQuad();
        }
    }

    public static void addPointInsert(Point2D point2D, String string) {
        processInsert.addPoint(point2D, string);
    }
    
    public static void resetColor() {
        treePaint.resetColor();
    }

    public static void addPointSwap(Point2D point2DObject, Point2D pointTarget) {
        processDelete.addPoint(point2DObject, pointTarget);
    }
    
    public static void addPointDelete(Point2D point2D){
        processDelete.addPoint(point2D);
    }

    public static void addPointSearch(Point2D point2D) {
        processSearch.add(point2D);
    }

    public Tree getTreeMain() {
        return treeMain;
    }

    public Process(Tree tree, JPanel panel) {
        this.panelPaint = panel;
        this.treeMain = tree;
        this.treePaint = new KDimensionalTree(tree.getNumOfDimension());
        Thread t = new Thread(this);
        t.start();
    }
    
    public void paintNode(Graphics2D g2d)  {
        if(treeMain.isEmpty()) treePaint.setEmpty();
        treePaint.paintTree();
        
        if(stateRun == Process.STATE.INSERTING){
            processInsert.paint(g2d);
        }else if(stateRun == Process.STATE.SEARCHING){
            MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
            processSearch.paint(g2d);
        }else if(stateRun == Process.STATE.DELETING){
            MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
            processDelete.paint(g2d);
        }
    }
    
    @Override
    public void run() {
        try {
            while (true) {
               if(stateRun == Process.STATE.WAITING){
                   if(processInsert.canNext()){
                       processInsert.next(treeMain);
                   }else if(processSearch.canNext()){
                       stateRun = STATE.SEARCHING;
                   }else if(processDelete.canNext()){
                       stateRun = STATE.DELETING;
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
                       Thread.sleep(8/timeSpeed);
                   }else if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.SKIPBACKWARD){
                       timeSpeed = 1;
                       MultiDimensionalDataStructure.labelSpeed.setText("");
                       Thread.sleep(8/timeSpeed);
                   }else if(MultiDimensionalDataStructure.status == MultiDimensionalDataStructure.STATE.PLAY){
                       MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
                       Thread.sleep(8/timeSpeed);
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
        if(stateRun == Process.STATE.INSERTING) processInsert.go(treePaint);
        else if(stateRun == Process.STATE.DELETING) processDelete.go(treePaint);
        else if(stateRun == Process.STATE.SEARCHING) processSearch.go(treePaint);
    }
    
    private void changeButtonPlayPause(){
        MultiDimensionalDataStructure.buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
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
