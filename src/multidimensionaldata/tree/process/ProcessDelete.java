/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree.process;

import java.awt.Graphics2D;
import multidimensionaldata.tree.InfoNode;
import multidimensionaldata.tree.Point2D;
import multidimensionaldata.tree.Tree;

/**
 *
 * @author ASUS
 */
public interface ProcessDelete {
    public void setInfo(InfoNode nodeObject, InfoNode nodeTarget);
    public void setInfo(InfoNode infoNode);
    public void reset();
    public boolean isComplete();
    public int getSize();
    public void go(Tree treePaint);
    public void addPoint(Point2D pointObject, Point2D pointTarget);   
    public void addPoint(Point2D point2D);
    public void paint(Graphics2D g2d);
    public boolean canNext();    
}
