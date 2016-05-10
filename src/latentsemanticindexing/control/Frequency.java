/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import UI.Dictionary;
import com.aliasi.xml.XHtmlWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author quocn
 */
public class Frequency extends NoName{
    
    private JTabbedPane tabbedPane;
    
    private JTable tableStep1;
    private JPanel panelStep1;
    private JScrollPane scrollPaneStep1;
    
    private JTable tableStep2;
    private JPanel panelStep2;
    private JScrollPane scrollPaneStep2;
    
    public Frequency(){
        //super();
        init();
    }

    private void init() {
        
        tabbedPane = new JTabbedPane();
        this.setLayout(new GridLayout(1, 1));
        this.add(tabbedPane);
        
        initStep1();
        initStep2();
        
        this.tabbedPane.add(panelStep1);
        this.tabbedPane.add(panelStep2);
        
        
        
        loadTabName();
        
        this.setBorder(BorderFactory.createLineBorder(Color.red));
    }
    
    private void initStep1(){
        tableStep1 = new JTable();
        panelStep1 = new JPanel();
        panelStep1.setLayout(new GridLayout(1, 1));
        setScrollPaneStep1();
        this.panelStep1.add(scrollPaneStep1);
    }
    
    private void initStep2(){
        tableStep2 = new JTable();
        panelStep2 = new JPanel();
        panelStep2.setLayout(new GridLayout(1, 1));
        setScrollPaneStep2();
        this.panelStep2.add(scrollPaneStep2);
    }

    private void setScrollPaneStep1() {
        scrollPaneStep1 = new JScrollPane(tableStep1
                , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                , JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tableStep1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }    
    private void setScrollPaneStep2() {
        scrollPaneStep2 = new JScrollPane(tableStep2
                , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                , JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tableStep2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }  

    @Override
    public void loadTable(List<DataDocument> listIdDocument, 
            List<DataTermWord> listIdTermWord, HashMap listWordQR, String databaseName) {
        
        double[][] arr = new double[listIdDocument.size()][listIdTermWord.size()];
        int sz = 0;
        
        for(DataDocument idDocument: listIdDocument){
            Vector vec = new Vector();
            for(DataTermWord idTermWord:listIdTermWord){
                String id= idTermWord.getId();
                if(!id.equals("0")){
                    double num = getNumber(id,idDocument, databaseName);
                    vec.add(num);
                }else{
                    vec.add(0.0);
                }
            }
            for(int i=0; i<vec.size(); i++){
                arr[sz][i] = Double.valueOf(vec.get(i).toString());
            }
            sz++;
        }
        
        delAllCol(tableStep1);
        addTblCol(tableStep1, "Term Words/ Documents");
        for(DataTermWord ob:listIdTermWord){
            addTblCol(tableStep1, ob.getName());
        }
        
        for(int i=0; i<arr.length; i++){
            Vector vector = new Vector();
            vector.addElement(listIdDocument.get(i).getName());
            for(int j = 0; j<arr[i].length; j++){
                vector.addElement(arr[i][j]);
            }
            addTblRow(tableStep1, vector);
        }
        Vector v= new Vector();
        v.add("#QUERY");
        for(DataTermWord ob:listIdTermWord){
            String word = ob.getName();
            if(listWordQR.containsKey(ob.getName())){
                v.add( Double.valueOf(listWordQR.get(word).toString()));
            }else{
                v.add(0.0);
            }
        }
        
        addTblRow(tableStep1, v);
    }

    private double getNumber(String id, DataDocument idDocument, String databaseName) {
        String sql = "select count from term_document where id_term = '"
                   + id + "' and id_document = '" + idDocument.getId() + "'";
        try{
            ResultSet res = Data.Data.getResultsetQuery(sql, databaseName);
            if(res.next()){
                return res.getDouble(1);
            }
        }catch(Exception ex){
            return 0;
        }
        return 0;
    }
    
    private void addTblCol(JTable table,String name) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        TableColumn col = new TableColumn(model.getColumnCount());

        col.setHeaderValue(name);
        table.addColumn(col);
        model.addColumn(name);
    };
    private void delTblCol(JTable table,int index) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        TableColumn col = table.getColumnModel().getColumn(index);
        table.removeColumn(col);
        table.revalidate();
    };
    
    private void delAllCol(JTable table){
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.getDataVector().removeAllElements();
        model.setColumnCount(0);
       
        table.revalidate();
    }
    
    private void addTblRow(JTable table, Vector vector){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(vector);
        table.revalidate();
    }

    private void loadTabName() {
        this.tabbedPane.setTitleAt(0, "Step 1");
        this.tabbedPane.setTitleAt(1, "Step 2");
    }
}
