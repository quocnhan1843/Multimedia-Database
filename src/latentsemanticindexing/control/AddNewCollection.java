/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import Data.Data;
import UI.Dictionary;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author quocn
 */
public class AddNewCollection extends javax.swing.JFrame {

    
    private JComboBox comboBox;
    private static AddNewCollection instance = null;
    
    public AddNewCollection(JComboBox comboBox) {
        this.comboBox = comboBox;
        initComponents();
    }
    
    public static AddNewCollection getIntance(JComboBox comboBox){
        if(instance == null){
            instance = new AddNewCollection(comboBox);
        }
        return instance;
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
        setResizable(false);

        labelAddNewCollection.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelAddNewCollection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAddNewCollection.setText("ADD NEW COLLECTION");

        labelNameCollection.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelNameCollection.setText("Name Collection:");

        textFieldNameCollection.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldNameCollectionKeyPressed(evt);
            }
        });

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
         addNew();
    }//GEN-LAST:event_buttonOkeActionPerformed

    private void textFieldNameCollectionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldNameCollectionKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            addNew();
        }
    }//GEN-LAST:event_textFieldNameCollectionKeyPressed
    
    private void addNew(){
        
        if(textFieldNameCollection.getText().isEmpty()){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, Dictionary.MESSAGE.NAME_NOT_EMPTY.getString()
                    , Dictionary.ERROR.ERROR.getString(), JOptionPane.ERROR_MESSAGE );
            return;
        }
        
        boolean ok = true;
        try {
            String nameDatabase = getNameDatabase();
            String nameCollection = textFieldNameCollection.getText();
            
            String sql = "INSERT INTO information VALUES ('"
                    + nameDatabase + "','" + nameCollection + "');";
            
            Data.setResultsetUpdate(sql, "lsi");
            ok = true;
            createDatabase(nameDatabase);
        } catch (Exception ex) {
            ex.printStackTrace();
            ok = false;
            
        }finally{
            if(ok){
                labelNotification.setForeground(Dictionary.COLOR.SUCCESSFUL.getColor());
                labelNotification.setText(Dictionary.Words.SUCCESSFUL.getString() + ".");
                textFieldNameCollection.setText("");
                loadDataCollection();
            }else{
                Toolkit.getDefaultToolkit().beep();
                labelNotification.setForeground(Dictionary.COLOR.FAIL.getColor());
                labelNotification.setText(Dictionary.Words.NAME_COLLECTION_ALREADY_EXISTS.getString() + ".");
            }
        }
    }
    
    private void createDatabase(String databaseName){
        Data.createDatabase(databaseName);
        String createDocument = "CREATE TABLE documents(id int NOT NULL AUTO_INCREMENT,"
                + "name varchar(500) NOT NULL, text text "
                + "CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, "
                + "PRIMARY KEY (id), UNIQUE(name));";
        
        String createTermWord = "CREATE TABLE terms (" +
                                "id int NOT NULL AUTO_INCREMENT," +
                                "word varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL" +
                                ", PRIMARY KEY (id), UNIQUE(word));";
        String createTerm_Document = "CREATE TABLE term_document (\n" +
                                    "id_term int(11) NOT NULL," +
                                    "id_document int(11) NOT NULL," +
                                    "count int(11) NOT NULL default 0," +
                                    "tf_idf double NOT NULL default 0.0, " +
                                    "CONSTRAINT pk_id PRIMARY KEY (id_term, id_document)," +
                                    "CONSTRAINT con_document FOREIGN KEY (id_document) REFERENCES documents(id)," +
                                    "CONSTRAINT con_term FOREIGN KEY (id_term) REFERENCES terms(id)," + 
                                    "CONSTRAINT unique_key UNIQUE(id_term, id_document));";
        try {
            Data.setResultsetUpdate(createDocument, databaseName);
            Data.setResultsetUpdate(createTermWord, databaseName);
            Data.setResultsetUpdate(createTerm_Document, databaseName);
        } catch (SQLException ex) {
        }
    }
    

    private String getNameDatabase() throws Exception {
        String sql = "SELECT information.name_database "
                + "FROM information ORDER by information.name_database "
                + "DESC LIMIT 1";

        ResultSet res = Data.getResultsetQuery(sql, "lsi");
        String id = "001";
        while(res.next()){
            String name = res.getString(1);
            id = getID(name.substring(10));
        }
        return "collection" + id;
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
            String sql = "select name_collection from information";

            ResultSet res = Data.getResultsetQuery(sql, "lsi");
            
            Vector listCollection = new Vector();
            
            while(res.next()){
                listCollection.addElement(res.getString(1));
                //comboBox.addItem(res.getString(1));
            }
            //comboBox.addItem(Dictionary.Words.ADD_NEW_DOCUMENT.getString() + "...");
            listCollection.addElement(Dictionary.Words.ADD_NEW_DOCUMENT.getString() + "...");
            comboBox.setModel(new DefaultComboBoxModel(listCollection));
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
