package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import multidimensionaldata.control.MultiDimensionalDataStructure;
import de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MultiDimensionalDataStructureActivity extends javax.swing.JFrame{

    public static int lang = 1;
    private MultiDimensionalDataStructure multiDimensionalDataStructure;
    public MultiDimensionalDataStructureActivity() {
        initComponents();
        setSizeMainUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        scrollPane = new JScrollPane();
        multiDimensionalDataStructure = new  MultiDimensionalDataStructure();
        JPanel jp = new JPanel(new BorderLayout());
               String lang[] = {"English", "Tiếng việt"};
        JComboBox cb = new JComboBox(lang);
               cb.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getItem().equals("English")){
                    MultiDimensionalDataStructureActivity.lang = 1;
                }else{
                    MultiDimensionalDataStructureActivity.lang = 2;
                }
                changeLang();
            }
        });
               jp.add(cb, BorderLayout.EAST);
        JPanel p = new JPanel(new BorderLayout());
               p.add(jp,BorderLayout.NORTH);
               p.add(multiDimensionalDataStructure, BorderLayout.CENTER);
        
        scrollPane.setViewportView(p);
        p.setPreferredSize(new Dimension(1346, 700));
        
        this.add(scrollPane);
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       try {
            UIManager.setLookAndFeel(new SyntheticaBlueLightLookAndFeel());
        } catch (ParseException | UnsupportedLookAndFeelException ex) {
        }
        java.awt.EventQueue.invokeLater(() -> {
            MultiDimensionalDataStructureActivity mainUI = new MultiDimensionalDataStructureActivity();
            mainUI.setVisible(true);
       });
    }

    private JScrollPane scrollPane;        

    private void setSizeMainUI() {
        this.setMinimumSize(new Dimension(600,400));
        setTitle(Dictionary.Words.MAIN_TITLE.getString());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        
        setLocation((width - this.getSize().width)/2, (height - this.getSize().height)/2);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        this.setMaximumSize(new Dimension(width, height));
    }
    
    private void setTab(int index, String string, String iconStr) {       
        JLabel labTab = new JLabel(string);
        try{
            ImageIcon icon = new ImageIcon(
                    this.getClass().getResource("/image/" + iconStr));
            labTab.setIcon(icon);
        }catch(NullPointerException ex){
        }finally{
            labTab.setForeground(Dictionary.COLOR.TITLE_TAB.getColor());
            labTab.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    ,Font.BOLD, Dictionary.Font_Size.TITLE_TAB.getValue()));
            
            //tabbedPane.setTabComponentAt(index, labTab);
        }
    }
    private void setTabChange(int index, String string, String iconStr) {
        JLabel labTab = new JLabel(string);
        try{
            ImageIcon icon = new ImageIcon(
                    this.getClass().getResource("/image/" + iconStr));
            labTab.setIcon(icon);
        }catch(NullPointerException ex){
        }finally{
            labTab.setForeground(Dictionary.COLOR.TITLE_TAB_SELECT.getColor());
            labTab.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    ,Font.BOLD, Dictionary.Font_Size.TITLE_TAB.getValue()));
        }
    }

    public static int getLang() {
        return lang;
    }
    private void changeLang() {
           this.setTitle(Dictionary.Words.MAIN_TITLE.getString());
           multiDimensionalDataStructure.changeState();
    }
}