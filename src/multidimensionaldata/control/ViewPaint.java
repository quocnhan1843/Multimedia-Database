/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.control;

import java.awt.Point;
import javax.swing.JScrollPane;
import multidimensionaldata.tree.Tree;

/**
 *
 * @author ASUS
 */
public class ViewPaint extends JScrollPane{ 

    private Tree tree;
    private Paint paintView;
    private MyViewport viewport;
    public ViewPaint(Tree tr) {
        this.tree = tr;
        this.viewport = new MyViewport();
        this.paintView = new Paint(this.tree);
        this.viewport.setView(paintView);
        this.setViewport(viewport);
        
        this.getViewport().setViewPosition(new Point(12*3000, 0));
    }
    public void setPositionViewPaint(int x, int y){
        this.getViewport().setViewPosition(new Point(x, y));
    }
    
    public Paint getPaint(){
        return paintView;
    }
}
