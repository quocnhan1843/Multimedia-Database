/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import UI.Dictionary;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author quocn
 */
public class ClassAnlysis extends JPanel{
    public ClassAnlysis(){
        init();
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(comboBox);
    }
    
    private JComboBox comboBox;
    
    private void init(){
        comboBox = new JComboBox();
        addItem();
    }

    private void addItem() {
        comboBox.addItem( (String) Dictionary.TYPE.NORMAL.getString());
        comboBox.addItem( (String) Dictionary.TYPE.IF_IDF.getString());
        comboBox.addItem( (String) Dictionary.TYPE.SVD.getString());
        comboBox.addItem( (String) Dictionary.TYPE.IF_IDF_SVD.getString());
    }
    
    public JComboBox getComboBox(){
        return this.comboBox;
    }
    public String getType(){
        return comboBox.getSelectedItem().toString();
    }
}
