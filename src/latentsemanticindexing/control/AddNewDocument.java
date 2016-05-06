/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import Data.Data;
import UI.Dictionary;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author quocn
 */
public class AddNewDocument extends javax.swing.JFrame {

    
    private JComboBox comboBox;
    
    
    public AddNewDocument() {
        this.comboBox = comboBox;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelAddNewCollection = new javax.swing.JLabel();
        labelNameCollection = new javax.swing.JLabel();
        textFieldNameCollection = new javax.swing.JTextField();
        buttonOke = new javax.swing.JButton();
        labelNotification = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelAddNewCollection.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelAddNewCollection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAddNewCollection.setText("ADD NEW COLLECTION");

        labelNameCollection.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelNameCollection.setText("Name Collection:");

        buttonOke.setText("OK");
        buttonOke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkeActionPerformed(evt);
            }
        });

        labelNotification.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelNotification.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAddNewCollection, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelNameCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(textFieldNameCollection)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelNotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonOke)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelAddNewCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNameCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldNameCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonOke, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelNotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOkeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkeActionPerformed
        // TODO add your handling code here:
        boolean ok = true;
        try {
            String nameDatabase = getNameDatabase();
            String nameCollection = textFieldNameCollection.getText();
            
            String sql = "INSERT INTO information VALUES ('"
                    + nameDatabase + "','" + nameCollection + "');";
            
            Data.setResultsetUpdate(sql, "lsi");
            ok = true;
            
        } catch (Exception ex) {
            ok = false;
            
        }finally{
            if(ok){
                labelNotification.setForeground(Dictionary.COLOR.SUCCESSFUL.getColor());
                labelNotification.setText(Dictionary.Words.SUCCESSFUL.getString() + ".");
                loadDataCollection();
            }else{
                Toolkit.getDefaultToolkit().beep();
                labelNotification.setForeground(Dictionary.COLOR.FAIL.getColor());
                labelNotification.setText(Dictionary.Words.NAME_COLLECTION_ALREADY_EXISTS.getString() + ".");
            }
        }
        
    }//GEN-LAST:event_buttonOkeActionPerformed

    

    private String getNameDatabase() throws Exception {
        String sql = "SELECT information.name_database "
                + "FROM information ORDER by information.name_database "
                + "DESC LIMIT 1";

        ResultSet res = Data.getResultsetQuery(sql, "lsi");
        String id = "001";
        while(res.next()){
            String name = res.getString(1);
            id = getID(name.substring(8));
        }
        return "document" + id;
    }
    
    private String getID(String stringCurrentID){
        Integer num = Integer.valueOf(stringCurrentID) + 1;
        
        String stringValue = num.toString();
        while(stringValue.length() < 3){
            stringValue = "0" + stringValue;
        }
        return stringValue;
    }
    
    private void loadDataCollection(){
        
        try{
        
            comboBox.removeAllItems();

            String sql = "select name_collection from information";

            ResultSet res = Data.getResultsetQuery(sql, "lsi");
            while(res.next()){
                comboBox.addItem(res.getString(1));
            }
            comboBox.addItem(Dictionary.Words.ADD_NEW_DOCUMENT.getString() + "...");
        }catch(Exception ex){
            
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonOke;
    private javax.swing.JLabel labelAddNewCollection;
    private javax.swing.JLabel labelNameCollection;
    private javax.swing.JLabel labelNotification;
    private javax.swing.JTextField textFieldNameCollection;
    // End of variables declaration//GEN-END:variables
}
