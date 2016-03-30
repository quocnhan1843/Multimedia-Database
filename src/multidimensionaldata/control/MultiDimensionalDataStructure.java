/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.control;

import UI.Dictionary;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import multidimensionaldata.tree.KDimensionalTree;
import multidimensionaldata.tree.PointQuadTree;
import multidimensionaldata.tree.process.Process;
import multidimensionaldata.tree.Tree;

/**
 *
 * @author ASUS
 */
public class MultiDimensionalDataStructure extends JPanel{
    
    private Tree tree;
    
    private static final long serialVersionUID = 1L;
    private JPanel  centerPanel, rightPanel;
    private static PaintTop topPanel;
    private ControlTreePanel controll;
    private JLabel labelZoom;
    private ViewPaint viewPaint;
    private JPanel p;
    private JPanel pLeft;
    private JPanel pCenter;
    private JPanel pRieght;
    private JButton buttonZoomOut;
    private JButton buttonZoomIn;
    private JComboBox comboBoxTree;
    private JComboBox comboBoxDimension;
    public static JButton buttonPlayPause;
    private JButton buttonSkipForward;
    private JButton buttonForward;
    private JButton buttonBackward;
    private JButton buttonSkipBackward;
    private JLabel labelTreeName;
    private JLabel labelNumOfDimension;
    
    public static enum STATE{NOTHING, PAUSE, PLAY, FORWARD, BACKWARD, SKIPFORWARD, SKIPBACKWARD};
    public static STATE status = STATE.NOTHING;
    public static JLabel labelSpeed;

    public MultiDimensionalDataStructure() {
        initialize();
        addControl();
        addCenter();
        
    }

    public static int getTopWidth(){
        return topPanel.getSize().width;
    }
    private void initialize() {
        tree = new KDimensionalTree(2);
        setLayout(new GridBagLayout());
        
        viewPaint = new ViewPaint(tree);
        centerPanel = new JPanel();
        topPanel = new PaintTop(tree);
        rightPanel = new JPanel();
        controll = new ControlTreePanel(tree,centerPanel);
        
        p = new JPanel(new BorderLayout());
        pLeft = new JPanel(new FlowLayout());
        pCenter = new JPanel(new FlowLayout());
        pRieght = new JPanel(new FlowLayout());
        
        buttonZoomOut = new JButton(getIcon(Dictionary.Icons.ZOOM_OUT.getString()));
        buttonZoomIn = new JButton(getIcon(Dictionary.Icons.ZOOM_IN.getString()));
        
        Object item[] = {Dictionary.Words.K_DIMENSIONNAL_TREE.getString()
                ,Dictionary.Words.POINT_QUADTREE.getString()
                ,Dictionary.Words.MATRIX_QUADTREE.getString()};
        Integer numItem[] = {2, 3, 4, 5};
        
        comboBoxTree = new JComboBox(item);
        comboBoxDimension = new JComboBox(numItem);
        
        labelTreeName = new JLabel(Dictionary.Words.SELECT_TREE.getString() + ":");
        labelNumOfDimension = new JLabel(Dictionary.Words.SELECT_DIMENSION.getString() + ":");
        
        
        
        buttonPlayPause = new JButton(getIcon(Dictionary.Icons.PLAY.getString()));
        buttonSkipForward = new JButton(getIcon(Dictionary.Icons.SKIP_FORWARD.getString()));
        buttonForward = new JButton(getIcon(Dictionary.Icons.FORWARD.getString()));
        buttonBackward = new JButton(getIcon(Dictionary.Icons.BACKWARD.getString()));
        buttonSkipBackward = new JButton(getIcon(Dictionary.Icons.SKIP_BACKWARD.getString()));
        
        labelZoom = new JLabel("100%");
        labelSpeed = new JLabel("");
        
        labelSpeed.setFont(new Font(Dictionary.Font.DEFAULT.getString()
                , Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        
        setView();
        setFontPanel();
        
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightPanel.setPreferredSize(new Dimension(150, centerPanel.getSize().height));
    }
    
    private void setView(){
                GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 85;
        gbc.weighty = 85;
        gbc.insets = new Insets(2, 2, 2, 2);
        add(centerPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 0;
        gbc.gridheight = 1;
        gbc.weightx = 20;
        gbc.weighty = 20;
        gbc.insets = new Insets(2, 2, 2, 2);
        add(topPanel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 15;
        gbc.weighty = 15;
        gbc.insets = new Insets(2, 2, 2, 2);
        
        add(rightPanel, gbc);
        rightPanel.setMinimumSize(new Dimension(600, 600));
    }
    
    private void addControl() {
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(controll);
    }

    private void addCenter() {        
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(viewPaint);
        
        pLeft.add(labelTreeName);
        pLeft.add(comboBoxTree);
        pLeft.add(labelNumOfDimension);
        pLeft.add(comboBoxDimension);
        
        pCenter.add(buttonZoomOut);
        pCenter.add(buttonZoomIn);
        pCenter.add(labelZoom);
        
        pRieght.add(buttonSkipBackward);
        pRieght.add(buttonBackward);
        pRieght.add(buttonPlayPause);
        pRieght.add(buttonForward);
        pRieght.add(buttonSkipForward);
        pRieght.add(labelSpeed);
        
        setActionListener();
        
        p.add(pLeft, BorderLayout.WEST);
        p.add(pCenter, BorderLayout.CENTER);
        p.add(pRieght, BorderLayout.EAST);
        
        p.setPreferredSize(new Dimension(80, 40));
        
        centerPanel.add(p, BorderLayout.SOUTH);
    }
    private void resetAll(Tree tree, int k){
        controll.clearTable();
        Process.setTreePaint(tree);
        Process.clearComponents();

        resetStatus();
        controll = new ControlTreePanel(tree, centerPanel, k);
        rightPanel.removeAll();
        rightPanel.add(controll);
        rightPanel.revalidate();
        topPanel.setTree(tree);
    }
    private void setActionListener(){
        buttonZoomOut.addActionListener((ActionEvent e) -> {
            setButtonZoomOut();
        });
        
        buttonZoomIn.addActionListener((ActionEvent e) -> {
            setButtonZoomIn();
        });
        comboBoxTree.addItemListener((ItemEvent e) -> {
            if(comboBoxTree.getSelectedIndex() == 0){
                comboBoxDimension.setEnabled(true);
                int k = Integer.valueOf(comboBoxDimension.getSelectedItem().toString()).intValue();
                        
                tree = new KDimensionalTree(k);
                resetAll(tree, k);
            }
            else{                
                comboBoxDimension.setSelectedIndex(0);
                comboBoxDimension.setEnabled(false);
                if(comboBoxTree.getSelectedIndex() == 1){
                    tree = new PointQuadTree();
                    resetAll(tree, 2);
                }else{
                    
                }
            }
        });
        
        comboBoxDimension.addItemListener((ItemEvent e) -> {
            int k = Integer.valueOf(comboBoxDimension.getSelectedItem().toString()).intValue();
                        
            tree = new KDimensionalTree(k);
            resetAll(tree, k);
//            controll.clearTable();
//            Process.setTreePaint(tree);
//            Process.clearComponents();
//            
//            resetStatus();
//            controll = new ControlTreePanel(tree, centerPanel, k);
//            rightPanel.removeAll();
//            rightPanel.add(controll);
//            rightPanel.revalidate();
        });
        buttonPlayPause.addActionListener((ActionEvent e) -> {
            if(Process.stateRun == Process.STATE.WAITING) return;
            changeStatusButtonPlayPause();
        });
        buttonForward.addActionListener((ActionEvent e)->{
            setButtonForward();
            status = STATE.FORWARD;
        });
        buttonBackward.addActionListener((ActionEvent e) ->{
            setButtonBackward();
            status = STATE.BACKWARD;
        });
        buttonSkipForward.addActionListener((ActionEvent e) ->{
            if(Process.stateRun == Process.STATE.WAITING) return;
            status = STATE.SKIPFORWARD;
        });
        buttonSkipBackward.addActionListener((ActionEvent e)->{
            if(Process.stateRun == Process.STATE.WAITING) return;
            status = STATE.SKIPBACKWARD;
        });
    }
    private void resetStatus(){
        status = STATE.NOTHING;
        Process.stateRun = Process.STATE.WAITING;
    }
    public static STATE saveStatus = STATE.NOTHING;
    private void changeStatusButtonPlayPause() {
        if(status == STATE.PAUSE){
            buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PAUSE.getString()));
            status = saveStatus;
        }else{
            buttonPlayPause.setIcon(getIcon(Dictionary.Icons.PLAY.getString()));
            saveStatus = status;
            status  =  STATE.PAUSE;
        }
    }
    private void setButtonForward(){
        if(status == STATE.BACKWARD){
            Process.timeSpeed = 1;
            labelSpeed.setText("");
            return;
        }
        if(Process.timeSpeed == 8) Process.timeSpeed = 1;
        else Process.timeSpeed *= 2;
        if(Process.timeSpeed == 1) labelSpeed.setText("");
        else labelSpeed.setText("x" + Process.timeSpeed);
    }
    
    private void setButtonBackward(){
        if(status == STATE.FORWARD){
            Process.timeSpeed = 1;
            labelSpeed.setText("");
            return;
        }
        if(Process.timeSpeed == 8) Process.timeSpeed = 1;
        else Process.timeSpeed *= 2;
        if(Process.timeSpeed == 1) labelSpeed.setText("");
        else labelSpeed.setText("x1/" + Process.timeSpeed);
    }
    
    private void setButtonZoomOut(){
        Paint.setK(Math.max(0.25,Paint.getK()-0.25));
        int k =   (int) (100*Paint.getK());
        viewPaint.setPositionViewPaint((int) (12*3000*Paint.getK()), 0);
        labelZoom.setText(Integer.valueOf(k).toString() + "%");
        centerPanel.repaint();
    }
    private void setButtonZoomIn(){
        Paint.setK(Math.min(2.0,Paint.getK()+0.25));
        int k =   (int) (100*Paint.getK());
        viewPaint.setPositionViewPaint((int) (12*3000*Paint.getK()), 0);
        labelZoom.setText(Integer.valueOf(k).toString() + "%");
        centerPanel.repaint();
    }
    
    private ImageIcon getIcon(String iconStr){
        return new ImageIcon(this.getClass().getResource("/image/" + iconStr));
    }
    
    public void changeState(){
        controll.changedState();
        labelTreeName.setText(Dictionary.Words.SELECT_TREE.getString() + ":");
        labelNumOfDimension.setText(Dictionary.Words.SELECT_DIMENSION.getString() + ":");
        comboBoxTree.removeAllItems();
        Object item[] = {Dictionary.Words.K_DIMENSIONNAL_TREE.getString()
                ,Dictionary.Words.POINT_QUADTREE.getString()
                ,Dictionary.Words.MATRIX_QUADTREE.getString()};
        for(int i=0; i<item.length; i++){
            comboBoxTree.addItem(item[i]);
        }
    }
    private void setFontPanel() {
        labelTreeName.setFont(new Font(Dictionary.Font.DEFAULT.getString(),
                                        Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        labelNumOfDimension.setFont(new Font(Dictionary.Font.DEFAULT.getString(),
                                        Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        comboBoxTree.setFont(new Font(Dictionary.Font.DEFAULT.getString(),
                                        Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        comboBoxDimension.setFont(new Font(Dictionary.Font.DEFAULT.getString(),
                                        Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
    }
    
    public static void resetLabelSpeed(){
        labelSpeed.setText("");
    }
}
