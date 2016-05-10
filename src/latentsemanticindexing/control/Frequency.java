/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
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
        //super();
        init();
    }

    private void init() {
        table = new JTable(4,4);
        
        setScrollPane();
        
        this.setLayout(new GridLayout(1, 1));
        this.add(scrollPane);
        
        this.setBorder(BorderFactory.createLineBorder(Color.red));
    }

    private void setScrollPane() {
        scrollPane = new JScrollPane(table
                , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }    

    @Override
    public void loadTable(List<DataDocument> listIdDocument, List listIdTermWord, String databaseName) {
        
        double[][] arr = new double[listIdDocument.size()][listIdTermWord.size()];
        int sz = 0;
        
        for(DataDocument idDocument: listIdDocument){
            Vector vec = new Vector();
            for(Object idTermWord:listIdTermWord){
                String id= idTermWord.toString();
                if(!id.equals("0")){
                    double num = getNumber(id,idDocument, databaseName);
                    vec.add(num);
                }
            }
            for(int i=0; i<vec.size(); i++){
                arr[sz][i] = Double.valueOf(vec.get(i).toString());
            }
            sz++;
        }
        System.out.print("\t");
        for(Object ob:listIdTermWord){
            String sql = "select word from terms where id = '" + ob.toString() + "'";
            try{
                ResultSet res = Data.Data.getResultsetQuery(sql, databaseName);
                while(res.next()){
                    String term = res.getString(1);
                    System.out.print(term.substring(0, Math.min(5, term.length())) + "\t");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        System.out.println("");
        
        for(int i=0; i<arr.length; i++){
            System.out.print(listIdDocument.get(i).getName() + "\t");
            for(int j = 0; j<arr[i].length; j++){
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println("");
        }
        
        Data.Data.printSQL("Frequency 82");
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
    
    
}
