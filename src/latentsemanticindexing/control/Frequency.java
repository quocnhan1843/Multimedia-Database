/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import UI.Dictionary;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author quocn
 */
public class Frequency extends Anlysis{
    
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
        addLis();
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
        Vector v2 = new Vector();
        for(int i=1; i<v.size(); i++){
            v2.addElement(v.get(i));
        }
        
        setTableStep2(arr, v2, listIdDocument, databaseName);
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
    
    private void setTableStep2(double[][] arr, Vector v, List<DataDocument> listIdDocument, String databaseName) {
        delAllCol(tableStep2);
        addTblCol(tableStep2, Dictionary.Words.SCORE.getString());
        addTblCol(tableStep2, Dictionary.Words.NAME_DOCUMENT.getString());
        addTblCol(tableStep2, Dictionary.Words.TEXT.getString());
        
        loadSizeTable2();
        
        double[] vectroQuery = new double[v.size()];
        
        for(int i=0; i<v.size(); ++i){
            vectroQuery[i] = (double) v.get(i);
        }
        DefaultTableModel model = (DefaultTableModel) tableStep2.getModel();
        
        for(int i=0; i<arr.length; ++i){
            double num = CosinDistance.getDistance(vectroQuery, arr[i]);
            
            Vector<Object> vt = new Vector<>();
            vt.addElement(num);
            vt.addElement(listIdDocument.get(i).getName());
            vt.addElement(getText(listIdDocument.get(i).getId(), databaseName));
            
            model.addRow(vt);
        }
        
        tableStep2.revalidate();
    }

    private String getText(String id, String databaseName) {
        String sql = "select text from documents where id = '" + id + "';";
        String ans = "";
        try {
            ResultSet res = Data.Data.getResultsetQuery(sql, databaseName);
            res.next();
            ans = res.getString(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return ans;
    }

    private void addLis() {
        tableStep2.addMouseListener(new java.awt.event.MouseAdapter() {
            
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem itemView = new JMenuItem(Dictionary.Words.VIEW.getString());

            popupMenu.add(itemView);


            itemView.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        int row = tableStep2.getSelectedRow();

                        String text = tableStep2.getModel().getValueAt(row, 2).toString();
                        String name = tableStep2.getModel().getValueAt(row, 1).toString();;

                        JDialog dialog = new JDialog();
                        dialog.setTitle(name);
                        dialog.setModal(true);
                        dialog.setContentPane(new JScrollPane(new JTextArea(text)));
                        dialog.setSize(700, 500);
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                        dialog.setFont(new Font(Dictionary.Font.DEFAULT.getString()
                                ,Font.PLAIN , 30));
                    }catch(Exception ex){}
                }
            });

            if (evt.isPopupTrigger() && evt.getComponent() instanceof JTable ) {
                popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
            }
        });
    }
    
    private void loadSizeTable2(){
        tableStep2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableStep2.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableStep2.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableStep2.getColumnModel().getColumn(2).setPreferredWidth(5000);
    }
}
