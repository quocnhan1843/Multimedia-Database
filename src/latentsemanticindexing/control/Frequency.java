/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author quocn
 */
public class Frequency extends NoName{
    private JTable table;
    private JScrollPane scrollPane;
    
    public Frequency(){
        init();
    }

    private void init() {
        table = new JTable(3, 4);
        
        setScrollPane();
        
        this.add(scrollPane);
    }

    private void setScrollPane() {
        scrollPane = new JScrollPane(table
                , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
    
}
