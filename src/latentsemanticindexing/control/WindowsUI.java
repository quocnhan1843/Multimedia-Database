/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import UI.Dictionary;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author quocn
 */
public class WindowsUI extends JPanel{
    
    private ClassAnlysis topPanel;
    private JPanel centerPanel;
    
    public WindowsUI(){
        init();
        setLayout();
        setComponent();
        setListener();
        
    }
    
    private void init(){
        topPanel = new ClassAnlysis();
        centerPanel = new JPanel();
    }
    
    private void setLayout(){
        this.setLayout(new GridLayout(2, 1));
    }
    
    private void setComponent(){
        this.add(topPanel);
        this.add(centerPanel);
    }
    
    private void setListener(){
        this.topPanel.getComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                centerPanel = TableAnlysis.getWindows((Dictionary.TYPE) topPanel.getComboBox().getSelectedItem());
            }
        });
    }
}
