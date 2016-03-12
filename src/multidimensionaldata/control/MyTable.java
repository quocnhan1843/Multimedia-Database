/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.control;

import static multidimensionaldata.control.ControlTreePanel.tableQueue;
import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import multidimensionaldata.tree.ProcessesPaintTree;
import multidimensionaldata.tree.Tree;

/**
 *
 * @author ASUS
 */
public class MyTable extends JTable{
    
    public Color[] color = {this.getBackground(), Color.PINK};
    public static int num = 0;
    private Tree tree;
    public MyTable(Object[][] rowData,
              Object[] columnNames) {
        super(rowData, columnNames);
    }

    public  MyTable(Tree tree){
        super();
        this.setEnabled(false);
        this.tree = tree;
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component stamp = super.prepareRenderer(renderer, row, column);
        if (row == tree.getSize() - 1 && ProcessesPaintTree.stateRun == ProcessesPaintTree.STATE.INSERTING){
            stamp.setBackground(color[num]);
        }
        else{
            stamp.setBackground(this.getBackground());
        }
        if(row == this.getSelectedRow()){
            stamp.setForeground(Color.black);
        }
        return stamp;
    }
    
    public void swapValue(int row1, int row2){
        for(int col=0; col<this.getColumnCount(); col++){
                Object o1 = this.getValueAt(row1, col);
                Object o2 = this.getValueAt(row2, col);
                this.setValueAt(o1, row2, col);
                this.setValueAt(o2, row1, col);
            }
    }
    
    public void removeLabel(String label){
        for(int i=0; i<this.getRowCount(); i++){
            if(this.getValueAt(i, 0).toString().equals(label)){
                ((DefaultTableModel) this.getModel()).removeRow(i);
                return;
            }
        }
    }
    public void removePoint(Vector vectorTmp){
        for(int i=0; i<this.getRowCount(); i++){
            boolean ok = true;
            for(int j=1; j<this.getColumnCount(); j++){
                if(this.getValueAt(i, j).equals(vectorTmp.get(j-1)) == false){
                    ok = false;
                }
            }
            if(ok){
                ((DefaultTableModel) this.getModel()).removeRow(i);
                break;
            }
        }
    }
}
