/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Data.Data;
import de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import latentsemanticindexing.control.AddNewCollection;
import latentsemanticindexing.control.AddQuery;
import latentsemanticindexing.control.DataDocument;
import latentsemanticindexing.control.DocumentsManagement;
import latentsemanticindexing.control.StopWords;
import latentsemanticindexing.control.WindowsUI;

/**
 *
 * @author ASUS
 */
public class LatentSemanticAnalysis extends javax.swing.JFrame {

    /**
     * Creates new form LatentSemanticAnalysis
     */
    
    //private String nameDocument = null;
    
    public LatentSemanticAnalysis() {
        startMySQL();
        initComponents();
        myInit();
        loadData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        comboBoxLang = new javax.swing.JComboBox();
        buttonAddDocuments = new javax.swing.JButton();
        buttonAddQuery = new javax.swing.JButton();
        comboBoxCollection = new javax.swing.JComboBox<>();
        panelMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Latent Semantic Analysis");

        comboBoxLang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "English", "Tiếng việt" }));
        comboBoxLang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxLangItemStateChanged(evt);
            }
        });

        buttonAddDocuments.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonAddDocuments.setText("Add Documents");
        buttonAddDocuments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddDocumentsActionPerformed(evt);
            }
        });

        buttonAddQuery.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonAddQuery.setText("Add Query");
        buttonAddQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddQueryActionPerformed(evt);
            }
        });

        comboBoxCollection.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboBoxCollection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxCollection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxCollectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addComponent(buttonAddDocuments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAddQuery)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 537, Short.MAX_VALUE)
                .addComponent(comboBoxLang, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxLang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAddDocuments)
                    .addComponent(buttonAddQuery)
                    .addComponent(comboBoxCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        panelMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 524, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxLangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxLangItemStateChanged
        // TODO add your handling code here:
        if(evt.getItem().equals("English")){
            MultiDimensionalDataStructureUI.lang = 1;
        }else{
            MultiDimensionalDataStructureUI.lang = 2;
        }
        changeLang();
    }//GEN-LAST:event_comboBoxLangItemStateChanged

    private void buttonAddDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddDocumentsActionPerformed
        try {
            // TODO add your handling code here:
            DocumentsManagement.getInstance().setVisible(true);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_buttonAddDocumentsActionPerformed

    private void buttonAddQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddQueryActionPerformed
        // TODO add your handling code here:
        new AddQuery(this).setVisible(true);
    }//GEN-LAST:event_buttonAddQueryActionPerformed

    private void comboBoxCollectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxCollectionActionPerformed
        // TODO add your handling code here:
        if(comboBoxCollection.getSelectedIndex() == comboBoxCollection.getItemCount() - 1 
                && comboBoxCollection.getSelectedIndex() > 0){
                AddNewCollection.getIntance(comboBoxCollection).setVisible(true);
        }
    }//GEN-LAST:event_comboBoxCollectionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(new SyntheticaBlueLightLookAndFeel());
        } catch (ParseException | UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StopWords();
                new LatentSemanticAnalysis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAddDocuments;
    private javax.swing.JButton buttonAddQuery;
    private javax.swing.JComboBox<String> comboBoxCollection;
    private javax.swing.JComboBox comboBoxLang;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    private WindowsUI panelLeft;
    private WindowsUI panelRight;    
    
    private void myInit() {
        init();
        setComponent();
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        panelMain.setLayout(new GridLayout(1, 2));
        
        panelMain.add(panelLeft);
        panelMain.add(panelRight);
        
        //nameDocument = (String) comboBoxCollection.getSelectedItem();
        
        //panelLeft.setBorder(BorderFactory.createLineBorder(Color.yellow));
    }
    
    private void init(){
        panelLeft = new WindowsUI();
        panelRight = new WindowsUI();
    }
    
    private void setComponent(){
        panelLeft.setBorder(BorderFactory.createLineBorder(Color.black));
        panelRight.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void changeLang() {
        this.setTitle(Dictionary.Words.LATENT_SEMANTIC_ANALYSIS.getString());
    }    
    private void loadData(){
        DefaultComboBoxModel com = new DefaultComboBoxModel();
        try{
            String sql = "select name_collection from information";
            ResultSet res = Data.getResultsetQuery(sql, "lsi");
            while(res.next()){
                com.addElement(res.getString(1));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(com.getSize() == 0) com.addElement("");
        com.addElement(Dictionary.Words.ADD_NEW_DOCUMENT.getString() + "...");
        this.comboBoxCollection.setModel(com);
    }

    public void loadDataTable(HashMap map) {
        TreeSet treeSet = new TreeSet();
        for (Object e:map.keySet()){
            treeSet.add(e);
        }
        String[] strings =  getWordInDocument();
        
        for(String st:strings){
            treeSet.add(st);
        }
        
        List listIDTermWord = new ArrayList();
        List listIDDocument = getListIDDocument();
        String databaseName = getDatabaseName((String) comboBoxCollection.getSelectedItem());
        for(Object tr: treeSet){
            listIDTermWord.add(getIDTermWord(tr.toString()
                    , databaseName));
        }
        
        panelLeft.loadTable(listIDDocument, listIDTermWord, databaseName);
    }
    
    private String[] getWordInDocument(){
        Vector vt = new Vector();
        String sql = "SELECT DISTINCT word FROM terms ORDER BY word ASC";
        try{
            String databaseName = getDatabaseName((String) comboBoxCollection.getSelectedItem());
            ResultSet rs = Data.getResultsetQuery(sql, databaseName );
            while(rs.next()){
                vt.add((rs.getString(1)));
            }
        }catch(Exception exception){
            //exception.printStackTrace();
            return null;
        }
        return (String[]) vt.toArray(new String[vt.size()]);
    }
    private String getDatabaseName(String collectionName){
        String sql = "select name_database from information where name_collection = '" + collectionName + "'";
        try{
            ResultSet res =  Data.getResultsetQuery(sql, "lsi");
            res.next();
            return res.getString(1);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private String getIDTermWord(String wordString, String databaseName){
        String sql = "select id from terms where word = '" + wordString + "'";
        try{
            ResultSet res = Data.getResultsetQuery(sql, databaseName);
            res.next();
            return res.getString(1);
        }catch(Exception exception){
            return "0";
        }
    }

    private List getListIDDocument() {
        String sql = "select id, name from documents";
        String databaseName = getDatabaseName((String) comboBoxCollection.getSelectedItem());
        List list = new ArrayList();
        try{
            ResultSet rs = Data.getResultsetQuery(sql
                    , databaseName);
            
            while(rs.next()){
                String id = rs.getString(1);
                String name = rs.getString(2);
                
                list.add(new DataDocument(id, name));
            }
        }catch(Exception ex){
        }
        return list;
    }

    private void startMySQL() {
        try {
            String commandStart = "C:/xampp/mysql/bin/mysqld";
            Process mysqlProc = Runtime.getRuntime().exec(commandStart);
            //System.out.println("MySQL server started successfully!");
        } catch (IOException e) {
            System.out.println("Start thất bại");
        }
    }
}
