/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author quocn
 */
public class WindowsUI extends JPanel{
    
    private ClassAnlysis topPanel;
    private NoName centerPanel;
    
    public WindowsUI(){
        init();
        setLayout();
        setComponent();
        setListener();        
    }
    
    private void init(){
        topPanel = new ClassAnlysis();
        centerPanel = NoName.createPanel(topPanel.getType());
    }
    
    private void setLayout(){
        //this.setLayout(new GridLayout(2, 1));
        this.setLayout(new BorderLayout());
    }
    
    private void setComponent(){
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }
    
    private void setListener(){
        this.topPanel.getComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                centerPanel = (NoName) TableAnlysis.getWindows(topPanel.getComboBox().getSelectedItem().toString());
            }
        });
    }
    
    public void loadTable(List listIdDocument, List listIdTermWord, String databaseName){
         centerPanel.loadTable(listIdDocument, listIdTermWord, databaseName);
    }
}
