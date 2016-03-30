/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.control;

import UI.Dictionary;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import multidimensionaldata.tree.InfoNode;
import multidimensionaldata.tree.Point;
import multidimensionaldata.tree.process.Process;
import multidimensionaldata.tree.Tree;

/**
 *
 * @author ASUS
 */
public class ControlTreePanel extends JTabbedPane {

    
    private Tree tree;

    private JPanel panelInsert;
    private JPanel panelDelete;
    private JPanel panelSearch;
    private JPanel panelDelTree;
    private JPanel panelLoadFile;
    private JPanel panelSaveFile;
    private JLabel labelPath;
    private JLabel labelLog;
    private JButton buttonChoosePath;
    private JButton buttonReadPath;
    private JButton buttonClearLog;
    private JTextArea textAreaLog;
    private JScrollPane jScrollPane1;
    private JTextField pathText;
    private final JPanel centerPanel;
    
    private static  Vector vectorInsertTextField;
    private static  Vector vectorDeleteTextField; 
    private static  Vector vectorSearchTextField;
    
    private static  Vector vectorInsertLabel;
    private static  Vector vectorDeleteLabel;
    private static  Vector vectorSearchLabel;
    private static  Vector vectorLoadFileLabel;
    
    private JLabel labelInsert;
    private JLabel labelDelete;
    private JLabel labelSearch;
    private JLabel labelLoadFile;
    private JTextField textLabelNodeInsert;
    private JTextField textLabelNodeDelete;
    private JTextField textLabelNodeSearch;
    private JLabel labelLabelNodeInsert;
    private JLabel labelLabelNodeDelete;
    private JLabel labelLabelNodeSearch;
    private JButton buttonInsert;
    private JButton buttonDelete;
    private JButton buttonSearch;
    private JButton buttonSave;
    
    private JTextField lastTextInsert;
    private JTextField lastTextDelete;
    private JTextField lastTextSearch;
    
    private JRadioButton radioButtonLabelDelete;
    private JRadioButton radioButtonPointDelete;
    private JRadioButton radioButtonLabelSearch;
    private JRadioButton radioButtonPointSearch;
    
    public static MyTable tableQueue;
    private JPanel panelQueue;
    public static JScrollPane scrollPane;
    
    private int dimension;
    
    public ControlTreePanel(Tree t, JPanel panel) {
        tree = t;
        this.centerPanel = panel;
        dimension = 2;
        init();
        setContents();
        addContents();
        addListener();
    }
    public ControlTreePanel(Tree t, JPanel panel, int k) {
        tree = t;
        this.centerPanel = panel;
        dimension = k;
        
        init();
        setContents();
        addContents();
        addListener();
    }
    private void init(){
        textLabelNodeInsert = new JTextField();
        textLabelNodeDelete = new JTextField();
        textLabelNodeSearch = new JTextField();
        labelLabelNodeInsert = new JLabel();
        labelLabelNodeDelete = new JLabel();
        labelLabelNodeSearch = new JLabel();
        panelInsert = new JPanel();
        panelDelete = new JPanel();
        panelSearch = new JPanel();
        panelDelTree = new JPanel();
        panelLoadFile = new JPanel();
        panelSaveFile = new JPanel();
        labelPath = new JLabel(Dictionary.Words.PATH.getString() + ":");
        labelLog = new JLabel(Dictionary.Words.LOG.getString() + ":");
        buttonChoosePath = new JButton("...");
        buttonClearLog = new JButton(Dictionary.Words.CLEAR_LOG.getString());
        buttonReadPath = new JButton(Dictionary.Words.OPEN.getString());
        buttonSave = new JButton(Dictionary.Words.SAVE.getString());
        textAreaLog = new JTextArea();
        jScrollPane1 = new JScrollPane();
        pathText = new JTextField();
        radioButtonLabelDelete = new JRadioButton();
        radioButtonPointDelete = new JRadioButton();
        radioButtonLabelSearch = new JRadioButton();
        radioButtonPointSearch = new JRadioButton();
        tableQueue = new MyTable(tree);
        panelQueue = new JPanel();
        vectorInsertTextField = new Vector();
        vectorDeleteTextField = new Vector();
        vectorSearchTextField = new Vector();
        vectorInsertLabel = new Vector();
        vectorDeleteLabel = new Vector();
        vectorSearchLabel = new Vector();
        vectorLoadFileLabel = new Vector();
        lastTextInsert = new JTextField();
        scrollPane =  new JScrollPane();
    }
    
    private void setContents(){
        this.setTabPlacement(JTabbedPane.RIGHT);
    }
    
    private void addListener(){
        this.addChangeListener((ChangeEvent e) -> {
            changedState();
            stateChangedTab(e);
            tree.resetColor();
            centerPanel.repaint();
        });
    }
    private void addContents() {
        addTab(Dictionary.Words.INSERT.getString(), panelInsert);
        addTab(Dictionary.Words.DELETE.getString(), panelDelete);
        addTab(Dictionary.Words.SEARCH.getString(), panelSearch);
        addTab(Dictionary.Words.DELETE_TREE.getString(), panelDelTree);
        addTab(Dictionary.Words.LOADFILE.getString(), panelLoadFile);
        addTab(Dictionary.Words.LOADFILE.getString(), panelSaveFile);
        
        setTab();
    }
    
    private void setTab(){
        preSetTab();
        
        setTapInsert();
        setTapSearch();
        setTapDelete();
        setTabLoadFile();
    }
    
    private void preSetTab(){
        setTab(0, Dictionary.Words.INSERT.getString()
                , Dictionary.Icons.INSERT.getString());
        setTab(1, Dictionary.Words.DELETE.getString()
                , Dictionary.Icons.DELETE.getString());
        setTab(2, Dictionary.Words.SEARCH.getString()
                , Dictionary.Icons.SEARCH.getString());
        setTab(3, Dictionary.Words.DELETE_TREE.getString()
                , Dictionary.Icons.DELETE_TREE.getString());
        setTab(4, Dictionary.Words.LOADFILE.getString()
                , Dictionary.Icons.LOADFILE.getString());
        setTab(5, Dictionary.Words.SAVE.getString()
                , Dictionary.Icons.SAVE.getString());
    }
    
    private void setTab(int index, String string, String iconStr) {       
        JLabel labTab = new JLabel(string);
        try{
            ImageIcon icon = new ImageIcon(
                    this.getClass().getResource("/image/" + iconStr));
            labTab.setIcon(icon);
        }catch(NullPointerException ex){
        }finally{
            labTab.setUI(new VerticalLabelUI(true)); // true/false to make it upwards/downwards
            labTab.setForeground(Dictionary.COLOR.TITLE_TAB.getColor());
            labTab.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    ,Font.BOLD, Dictionary.Font_Size.TITLE_TAB.getValue()));
            
            this.setTabComponentAt(index, labTab);
        }
    }
    private void setTabSelect(int index, String string, String iconStr) {
        JLabel labTab = new JLabel(string);
        try{
            ImageIcon icon = new ImageIcon(
                    this.getClass().getResource("/image/" + iconStr));
            labTab.setIcon(icon);
        }catch(NullPointerException ex){
        }finally{
            labTab.setUI(new VerticalLabelUI(true));
            labTab.setForeground(Dictionary.COLOR.TITLE_TAB_SELECT.getColor());
            labTab.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    ,Font.BOLD, Dictionary.Font_Size.TITLE_TAB.getValue()));
            
            this.setTabComponentAt(index, labTab);
        }
    }
    private void setTapInsert() {
        panelInsert.setLayout(new FlowLayout());
        labelInsert = new JLabel(Dictionary.Words.INSERT_TITLE.getString());
               labelInsert.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                       , Font.BOLD, Dictionary.Font_Size.TITLE_CONTROL.getValue()));
               labelInsert.setForeground(Dictionary.COLOR.FOREGROUND_LABEL.getColor());
        panelInsert.add(labelInsert);
        d = 1;
        setLabelNode(panelInsert, textLabelNodeInsert, labelLabelNodeInsert);
        setText(vectorInsertTextField, vectorInsertLabel, panelInsert);
        buttonInsert = addButton(vectorInsertTextField, panelInsert, textLabelNodeInsert);
        textLabelNodeInsert.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    JTextField t2 = (JTextField) vectorInsertTextField.get(0);
                    t2.requestFocusInWindow();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        buttonInsert.addActionListener((ActionEvent e) -> {
            insertNode();
        });
        JTextField t = (JTextField) vectorInsertTextField.get(vectorInsertTextField.size()-1);
        t.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                   textLabelNodeInsert.requestFocusInWindow();
                    
                    insertNode();
                }
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                    && (character != '\b') && character != '-' && character != '+')
                        e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        addQueue();
    }
    
    
    
    private void addQueue(){
        
        TableColumn column = new TableColumn();
        DefaultTableModel modelTable  = (DefaultTableModel) tableQueue.getModel();
        modelTable.addColumn(Dictionary.Words.LABEL.getString());
        for(int i=0; i < dimension; i++){
            modelTable.addColumn(Dictionary.Words.VALUE.getString() + " " + (i + 1));
        }
        
        tableQueue.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        scrollPane = new JScrollPane(tableQueue
                , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        TitledBorder border = new TitledBorder(Dictionary.Words.QUEUE.getString());
                    border.setTitleJustification(TitledBorder.CENTER);
                    border.setTitlePosition(TitledBorder.TOP);
                    border.setTitleFont(new Font(Dictionary.Font.DEFAULT.getString()
                                             , Font.PLAIN ,Dictionary.Font_Size.DEFAULT.getValue()));
        
        panelQueue.setLayout(new BorderLayout());
        tableQueue.setPreferredSize(new Dimension(80*tableQueue.getColumnCount()
                , Math.max(100, tableQueue.getRowCount()*25)));
        panelQueue.setPreferredSize(new Dimension(260, 190));
        
        panelQueue.setBorder(BorderFactory.createTitledBorder(border));
        
        panelQueue.add(scrollPane);
        
        panelInsert.add(panelQueue);
    }
    
    private void setTapSearch() {
        panelSearch.setLayout(new FlowLayout());
        labelSearch = new JLabel(Dictionary.Words.SEARCH_TITLE.getString());
               labelSearch.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                       , Font.BOLD, Dictionary.Font_Size.TITLE_CONTROL.getValue()));
               labelSearch.setForeground(Dictionary.COLOR.FOREGROUND_LABEL.getColor());
        panelSearch.add(labelSearch);
        d = 1;
        setSelect(radioButtonLabelSearch,radioButtonPointSearch
                    , textLabelNodeSearch, panelSearch, vectorSearchTextField);
        setLabelNode(panelSearch, textLabelNodeSearch, labelLabelNodeSearch);
        setText(vectorSearchTextField, vectorSearchLabel, panelSearch);
        buttonSearch = addButton(vectorSearchTextField, panelSearch, textLabelNodeSearch);
        
        for(int i=0; i<vectorSearchTextField.size(); i++){
            JTextField t = (JTextField) vectorSearchTextField.get(i);
            t.setEnabled(false);
            t.setBackground(Color.lightGray);
        }
        addKeyListenner(buttonSearch, textLabelNodeSearch, vectorSearchTextField);
    }
    
    private void setTapDelete() {
        panelDelete.setLayout(new FlowLayout());
        labelDelete = new JLabel(Dictionary.Words.DELETE_TITLE.getString());
               labelDelete.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                       , Font.BOLD, Dictionary.Font_Size.TITLE_CONTROL.getValue()));
               labelDelete.setForeground(Dictionary.COLOR.FOREGROUND_LABEL.getColor());
        panelDelete.add(labelDelete);
        d = 1;
        setSelect(radioButtonLabelDelete, radioButtonPointDelete, textLabelNodeDelete
                , panelDelete, vectorDeleteTextField);
        setLabelNode(panelDelete, textLabelNodeDelete, labelLabelNodeDelete);
        setText(vectorDeleteTextField, vectorDeleteLabel, panelDelete);
        buttonDelete = addButton(vectorDeleteTextField, panelDelete, textLabelNodeDelete);
        
        for(int i=0; i<vectorDeleteTextField.size(); i++){
            JTextField t = (JTextField) vectorDeleteTextField.get(i);
            t.setEnabled(false);
            t.setBackground(Color.lightGray);
        }
        
        addKeyListenner(buttonDelete, textLabelNodeDelete, vectorDeleteTextField);
    }
    
    private void completeButton(){
        if(this.getSelectedIndex() == 1){
            deleteNode();
        }
        else{
            searchNode();
        }
    }
    
    private void addKeyListenner(JButton button
            , JTextField textLabelNode, Vector vectorTextField){
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                completeButton();
            }
        });
        
        textLabelNode.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    completeButton();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        
        JTextField t = (JTextField) vectorTextField.get(vectorTextField.size()-1);
        t.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                   textLabelNode.requestFocusInWindow();
                    
                    completeButton();
                }
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                    && (character != '\b') && character != '-' && character != '+')
                        e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
    private void setSelect(JRadioButton radioButtonLabel
            , JRadioButton radioButtonPoint, JTextField textLabelNode
            , JPanel panel, Vector vectorTextField){
        JPanel tempPanel = new JPanel(new FlowLayout());
        radioButtonLabel.setText(Dictionary.Words.RADIO_LABEL.getString());
        radioButtonPoint.setText(Dictionary.Words.RADIO_POINT.getString());
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButtonLabel);
        bg.add(radioButtonPoint);
        tempPanel.add(radioButtonLabel);
        tempPanel.add(radioButtonPoint);
        tempPanel.setPreferredSize(new Dimension(200, 30));
        radioButtonLabel.setSelected(true);
        textLabelNode.setEnabled(true);
        panel.add(tempPanel);
        radioButtonLabel.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                 if(radioButtonLabel.isSelected()){
                    textLabelNode.setEnabled(true);
                    textLabelNode.setBackground(Color.WHITE);
                    for(int i=0; i<vectorTextField.size(); i++){
                        JTextField t = (JTextField) vectorTextField.get(i);
                        t.setEnabled(false);
                        t.setBackground(Color.lightGray);
                    }
                }else{
                    textLabelNode.setEnabled(false);
                    textLabelNode.setBackground(Color.lightGray);
                    for(int i=0; i<vectorTextField.size(); i++){
                        JTextField t = (JTextField) vectorTextField.get(i);
                        t.setEnabled(true);
                        t.setBackground(Color.WHITE);
                    }
                }
                panel.revalidate();
            }
        });
    }
    
    private void setLabelNode(JPanel tabPanel, JTextField text, JLabel label){
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        label.setText(Dictionary.Words.LABEL.getString() + ":");
        text.setPreferredSize(new Dimension(200, 30));
        
        label.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    , Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        text.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    , Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        
        tempPanel.add(label);
        tempPanel.add(text);
        tabPanel.add(tempPanel);
    }
    
    //<editor-fold defaultstate="collapsed" desc=" set Textfield ">
    public static int d = 1;
    private JTextField setText(Vector vectorText,Vector vectorLabel, JPanel tabPanel){
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel label = new JLabel(Dictionary.Words.VALUE.getString() 
                    + " " + d + ":");
        vectorLabel.add(label);
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(200, 30));
        
        label.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    , Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        text.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    , Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        
        tempPanel.add(label);
        tempPanel.add(text);
        vectorText.add(text);
        tabPanel.add(tempPanel);
        if(++d <= dimension) {
            JTextField t = setText(vectorText,vectorLabel,tabPanel);
            text.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    t.requestFocusInWindow();
                }
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                    && (character != '\b') && character != '-' && character != '+') {
                e.consume();
        }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        }
        
        return text;
    }
    //</editor-fold>
    
    private final Vector vectorButtonComplete = new Vector();
    private final Vector vectorButtonReset = new Vector();
    
    //<editor-fold defaultstate="collapsed" desc=" set Button ">
    private JButton addButton(Vector vector, JPanel tabPanel, JTextField textFieldLabel){
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton bt1 = new JButton(Dictionary.Words.COMPLETE.getString());
        JButton bt2 = new JButton(Dictionary.Words.RESET.getString());

        vectorButtonComplete.add(bt1);
        vectorButtonReset.add(bt2);
        
        tempPanel.add(bt1);
        tempPanel.add(bt2);

        bt1.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                , Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        bt2.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                , Font.PLAIN, Dictionary.Font_Size.DEFAULT.getValue()));
        
        bt2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldLabel.setText("");
                clearText(vector);
            }

            
        });
        
        tabPanel.add(tempPanel);
        return bt1;
    }
    //</editor-fold>
    private void stateChangedTab(ChangeEvent e) {
        
        int index = this.getSelectedIndex();
        
        if(index != 0 && index != 4 
                && Process.stateRun == Process.STATE.INSERTING){
            
            JOptionPane.showMessageDialog(this, Dictionary.Words.INSERTING.getString()
                    + "\n" +  Dictionary.Words.PLEASE_WAIT.getString()
                    , Dictionary.Words.MESSAGE.getString(), JOptionPane.INFORMATION_MESSAGE);
            this.setSelectedIndex(0);
            return;
        }else if(index != 1 && Process.stateRun == Process.STATE.DELETING){
            JOptionPane.showMessageDialog(this, Dictionary.Words.DELETING.getString()
                    + "\n" +  Dictionary.Words.PLEASE_WAIT.getString()
                    , Dictionary.Words.MESSAGE.getString(), JOptionPane.INFORMATION_MESSAGE);
            this.setSelectedIndex(1);
            return;
        }else if(index != 2 && Process.stateRun == Process.STATE.SEARCHING){
            JOptionPane.showMessageDialog(this, Dictionary.Words.SEARCHING.getString()
                    + "\n" +  Dictionary.Words.PLEASE_WAIT.getString()
                    , Dictionary.Words.MESSAGE.getString(), JOptionPane.INFORMATION_MESSAGE);
            this.setSelectedIndex(2);
            return;
        }
        
        Process.resetColor();
        
        if(index == 3){
            this.setSelectedIndex(0);
            int con = JOptionPane.showConfirmDialog(this
                    , Dictionary.CONFIRM.DETELE_TREE.getString()
                    , Dictionary.CONFIRM.CONFIRM.getString()
                    , JOptionPane.YES_NO_OPTION);
            if(con == JOptionPane.YES_OPTION)
            {
                tree.setEmpty();
                resetStatus();
                clearTable();
            }
        }
        if(this.getSelectedIndex() == 5){
            this.setSelectedIndex(0);
            saveTree();
        }
    }
    private void resetStatus(){
        Process.stateRun = Process.STATE.WAITING;
        MultiDimensionalDataStructure.status = MultiDimensionalDataStructure.STATE.NOTHING;
    }
    private static String pathSave = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
    private void saveTree(){
        String outputFile = readPathSave();
        
        if(!outputFile.equals("")){
            pathSave = outputFile;
        }else{
            return;
        }
        
	File file = new File(outputFile);	
        boolean alreadyExists = file.exists();
        if (!alreadyExists){
            writeFile(outputFile);
        }else{
            int con = JOptionPane.showConfirmDialog(this
                    , Dictionary.CONFIRM.ALREADY_EXISTS.getString() + "\n"
                            + Dictionary.CONFIRM.REPLACE.getString()
                    , Dictionary.CONFIRM.CONFIRM.getString()
                    , JOptionPane.YES_NO_OPTION);
            if(con == JOptionPane.YES_OPTION){
                file.delete();
                writeFile(outputFile);
            }
        }
        
    }
    private String readPathSave(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter csvFile = new FileNameExtensionFilter("Comma separated values (.csv)", "csv");
        chooser.addChoosableFileFilter(csvFile);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(new File(pathSave));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            if(path.substring(path.length() - 4).equals(".csv") == false) path += ".csv";
            return path;
        }
        return "";
    }
    private void writeFile(String outputFile){
        try {
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

            csvOutput.write(Dictionary.Words.LABEL.getString());
            
            for(int i=1; i<tableQueue.getColumnCount(); i++){
                csvOutput.write(Dictionary.Words.VALUE.getString() + i);
            }
            csvOutput.endRecord();
            
            DefaultTableModel defaultTableModel = (DefaultTableModel) tableQueue.getModel();

            for(int i = 0; i<defaultTableModel.getRowCount(); i++){
                for(int j=0; j<defaultTableModel.getColumnCount(); j++){
                    String str = String.valueOf(defaultTableModel.getValueAt(i, j));
                    csvOutput.write(str);
                }
                csvOutput.endRecord();
            }

            csvOutput.close();
        } catch (IOException e) {
               // e.printStackTrace();
        }
    }
    //<editor-fold defaultstate="collapsed" desc=" setTabLoadFile ">
    private void setTabLoadFile(){
        
        labelLoadFile = new JLabel(Dictionary.Words.LOADFILE_TITLE.getString(),JLabel.CENTER);
               labelLoadFile.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                       , Font.BOLD, Dictionary.Font_Size.TITLE_LOADFILE.getValue()));
               labelLoadFile.setForeground(Dictionary.COLOR.FOREGROUND_LABEL.getColor());
        
        labelPath.setFont(new java.awt.Font(Dictionary.Font.DEFAULT.getString(), 0, Dictionary.Font_Size.DEFAULT.getValue()));

        buttonChoosePath.setText("...");
        buttonChoosePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChoosePathActionPerformed(evt);
            }
        });

        buttonReadPath.setFont(new java.awt.Font(Dictionary.Font.DEFAULT.getString(), 0, Dictionary.Font_Size.DEFAULT.getValue()));
        buttonReadPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReadPathActionPerformed(evt);
            }

            private void buttonReadPathActionPerformed(ActionEvent evt) {
                String string = new String("");
                
                try {
			
			CsvReader products = new CsvReader(pathText.getText());
		
			products.readHeaders();
                        int headerCount = products.getHeaderCount();
                        
			while (products.readRecord())
			{
                            Vector vectorValue = new Vector();
                            String label = products.get(0);
                            string += (label + ":");

                            for(int i=1; i<headerCount ; i++){
                                String value = products.get(i);
                                vectorValue.addElement(Integer.valueOf(value));
                                string += value;
                                if(i == headerCount-1){
                                    string += " => ";
                                }else{
                                    string += " , ";
                                }                                   
                            }
                            Point p = new Point(vectorValue);
                            vectorValue.insertElementAt(label, 0);
                            String validNode = checkValidNode(label, p);
                            if(!validNode.equals("GOOD")){
                                string += validNode;
                                string += "\n";
                                continue;
                            }else{
                                string += Dictionary.Words.SUCCESSFUL.getString();
                                string += "\n";
                            }
                            
                            DefaultTableModel defaultTableModel = (DefaultTableModel) tableQueue.getModel();
                                              defaultTableModel.addRow(vectorValue);
                            resetSizeTable();
                            Process.addQueueInsert(new InfoNode(label,p));
			}
			products.close();
			textAreaLog.setText(string);
                        textAreaLog.setFont(new Font(Dictionary.Font.DEFAULT.getString()
                                , Font.PLAIN, Dictionary.Font_Size.FONT_LOG.getValue()));
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
            }
        });

        textAreaLog.setColumns(20);
        textAreaLog.setRows(5);
        jScrollPane1.setViewportView(textAreaLog);

        labelLog.setFont(new java.awt.Font(Dictionary.Font.DEFAULT.getString(), 0, Dictionary.Font_Size.DEFAULT.getValue())); // NOI18N
        //labelLog.setText("Log:");

        buttonClearLog.setFont(new java.awt.Font(Dictionary.Font.DEFAULT.getString(), 0, 13)); // NOI18N
        //buttonClearLog.setText("Xóa nhật ký");
        buttonClearLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearLogActionPerformed(evt);
            }

            private void buttonClearLogActionPerformed(ActionEvent evt) {
                textAreaLog.setText(null);
                pathText.setText(null);
            }
        });

        javax.swing.GroupLayout tabLoadFileLayout = new javax.swing.GroupLayout(panelLoadFile);
        panelLoadFile.setLayout(tabLoadFileLayout);
        tabLoadFileLayout.setHorizontalGroup(
            tabLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabLoadFileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabLoadFileLayout.createSequentialGroup()
                        .addComponent(labelPath, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(tabLoadFileLayout.createSequentialGroup()
                        .addComponent(pathText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonChoosePath, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabLoadFileLayout.createSequentialGroup()
                        .addComponent(labelLog, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonClearLog)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonReadPath))
                    .addComponent(jScrollPane1)
                    .addComponent(labelLoadFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        tabLoadFileLayout.setVerticalGroup(
            tabLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabLoadFileLayout.createSequentialGroup()
                .addComponent(labelLoadFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPath, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonChoosePath, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(pathText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabLoadFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLog)
                    .addComponent(buttonReadPath)
                    .addComponent(buttonClearLog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
        );
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Set Action Performd ">
    private void buttonChoosePathActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Comma separated values (.csv)", "csv"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pathText.setText(selectedFile.getPath());
        }
    }
    //</editor-fold>
    public void changedState() {
        preSetTab();
        changeStateTabSelect();
        changeStateTextField();
        changeStateLabel();
        changeStateLoadFile();
        changeRadioButton();
        changeTableQueue();
        
        for (Object vectorButtonComplete1 : vectorButtonComplete) {
            JButton bt = (JButton) vectorButtonComplete1;
            bt.setText(Dictionary.Words.COMPLETE.getString());
        }
        
        for (Object vectorButtonReset1 : vectorButtonReset) {
            JButton bt = (JButton) vectorButtonReset1;
            bt.setText(Dictionary.Words.RESET.getString());
        }
        
        labelLabelNodeInsert.setText(Dictionary.Words.LABEL.getString() + ":");
        labelLabelNodeDelete.setText(Dictionary.Words.LABEL.getString() + ":");
        labelLabelNodeSearch.setText(Dictionary.Words.LABEL.getString() + ":");
        
    }
    
    private void changeStateTabSelect() {
        int id = this.getSelectedIndex();
        if(id == 0) setTabSelect(0, Dictionary.Words.INSERT.getString()
                , Dictionary.Icons.INSERT.getString());
        if(id == 1) setTabSelect(1, Dictionary.Words.DELETE.getString()
                , Dictionary.Icons.DELETE.getString());
        if(id == 2) setTabSelect(2, Dictionary.Words.SEARCH.getString()
                , Dictionary.Icons.SEARCH.getString());
        if(id == 3) setTabSelect(3, Dictionary.Words.DELETE_TREE.getString()
                , Dictionary.Icons.DELETE_TREE.getString());
        if(id == 4) setTabSelect(4, Dictionary.Words.LOADFILE.getString()
                , Dictionary.Icons.LOADFILE.getString());
        if(id == 5) setTabSelect(5, Dictionary.Words.LOADFILE.getString()
                , Dictionary.Icons.LOADFILE.getString());
    }
    
    private void changeStateTextField(){
        changeStateTextField(vectorInsertLabel);
        changeStateTextField(vectorDeleteLabel);
        changeStateTextField(vectorSearchLabel);
    }

    private void changeStateTextField(Vector vectorLabel) {
        for(int i=0; i<vectorLabel.size(); ++i){
            JLabel lb = (JLabel) vectorLabel.get(i);
            lb.setText(Dictionary.Words.VALUE.getString() + " " + (i + 1) + ":");
        }
    }

    private void changeStateLabel() {
        labelInsert.setText(Dictionary.Words.INSERT_TITLE.getString());
        labelDelete.setText(Dictionary.Words.DELETE_TITLE.getString());
        labelSearch.setText(Dictionary.Words.SEARCH_TITLE.getString());
        labelLoadFile.setText(Dictionary.Words.LOADFILE_TITLE.getString());
    }

    private void changeStateLoadFile() {
        labelPath.setText(Dictionary.Words.PATH.getString());
        labelLog.setText(Dictionary.Words.LOG.getString());
        buttonClearLog.setText(Dictionary.Words.CLEAR_LOG.getString());
        buttonReadPath.setText(Dictionary.Words.OPEN.getString());
    }
    private void clearText(Vector vector) {
        for(int i=0; i<vector.size(); i++){
            JTextField t = (JTextField) vector.get(i);
            t.setText("");
        }
    }
    private void insertNode() {
        try{
            Vector vectorValue = new Vector();
            for(int i=0; i<vectorInsertTextField.size(); i++){
                JTextField textField = (JTextField) vectorInsertTextField.get(i);
                int value = Integer.valueOf(textField.getText()).intValue();
                vectorValue.addElement(value);
            }
            String label = textLabelNodeInsert.getText();
            Point p = new Point(vectorValue);
            
            String validNode = checkValidNode(label, p);
            if(!validNode.equals("GOOD")){
                JOptionPane.showMessageDialog(panelInsert
                        , validNode
                        , Dictionary.ERROR.ERROR.getString()
                        , JOptionPane.ERROR_MESSAGE);
                return;
            }   
            
            vectorValue.insertElementAt(label, 0);
            DefaultTableModel defaultTableModel = (DefaultTableModel) tableQueue.getModel();
                              defaultTableModel.addRow(vectorValue);
            resetSizeTable();
            Process.addQueueInsert(new InfoNode(label,p));
            
            textLabelNodeInsert.setText("");
            clearText(vectorInsertTextField);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(panelInsert, Dictionary.ERROR.ERROR.getString());
        }
    }
    
    private String checkValidNode(String label, Point p){
        if(tree.checkLabel(label) || checkLabelInQueue(label)){
            return Dictionary.ERROR.ERROR_LABEL_EXISTS.getString();
        }
        if(tree.checkPoint(p) || checkPointInQueue(p) ){
            return Dictionary.ERROR.ERROR_POINT_EXISTS.getString(); 
        }
        if(tree.getNumOfDimension()!= p.getSize()){
            return Dictionary.ERROR.ERROR_POINT_INVALID.getString();
        }
        return "GOOD";
    }
    
    private void resetSizeTable(){
        tableQueue.setPreferredSize(new Dimension(80*tableQueue.getColumnCount()
                                    , Math.max(100, tableQueue.getRowCount()*21)));
        scrollPane.getViewport().setViewPosition(new java.awt.Point(0, 21*(tree.getSize() - 1)));
    }
    private void deleteNode() {
        if(radioButtonLabelDelete.isSelected()){
            String label = textLabelNodeDelete.getText();
            if(label.equals("")){
                JOptionPane.showMessageDialog(panelDelete,"ban chua nhap ten nhan");
                return;
            }
            if(!tree.checkLabel(label)){
                JOptionPane.showMessageDialog(panelDelete
                        , Dictionary.ERROR.ERROR_LABEL_NOT_EXISTS.getString()
                        , Dictionary.ERROR.ERROR.getString()
                        , JOptionPane.ERROR_MESSAGE);
                return;
            }
            Process.removeNodeLabel(label);
            ((MyTable) tableQueue).removeLabel(label);
        }else{
            Point p = new Point();
            try{
                Vector vectorValue = new Vector();
                for(int i=0; i<vectorDeleteTextField.size(); i++){
                    JTextField textField = (JTextField) vectorDeleteTextField.get(i);
                    int value = Integer.valueOf(textField.getText()).intValue();
                    vectorValue.addElement(value);
                }

                p = new Point(vectorValue);


                if(vectorValue.size() == dimension && !tree.checkPoint(p)){
                    JOptionPane.showMessageDialog(panelDelete
                            , Dictionary.ERROR.ERROR_POINT_NOT_EXISTS.getString()
                            , Dictionary.ERROR.ERROR.getString()
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Process.removeNodePoint(p);
                ((MyTable) tableQueue).removePoint( p.getLocation());
            }catch(Exception ex){}
        }
        textLabelNodeDelete.setText("");
        clearText(vectorDeleteTextField);
        centerPanel.repaint();
    }

    private void changeRadioButton() {
        radioButtonLabelDelete.setText(Dictionary.Words.RADIO_LABEL.getString());
        radioButtonPointDelete.setText(Dictionary.Words.RADIO_POINT.getString());
    }

    private void searchNode() {
        if(radioButtonLabelSearch.isSelected()){
            String label = textLabelNodeSearch.getText();
            if(label.equals("")){
                JOptionPane.showMessageDialog(panelSearch,"ban chua nhap ten nhan");
                return;
            }
            if(!tree.checkLabel(label)){
                JOptionPane.showMessageDialog(panelSearch
                        , Dictionary.ERROR.ERROR_LABEL_NOT_EXISTS.getString()
                        , Dictionary.ERROR.ERROR.getString()
                        , JOptionPane.ERROR_MESSAGE);
                return;
            }
            Process.searchLabelAndPaint(label);
        }else{
            Point p = new Point();
            try{
                Vector vectorValue = new Vector();
                for(int i=0; i<vectorSearchTextField.size(); i++){
                    JTextField textField = (JTextField) vectorSearchTextField.get(i);
                    int value = Integer.valueOf(textField.getText()).intValue();
                    vectorValue.addElement(value);
                }

                p = new Point(vectorValue);

                if(vectorValue.size() == dimension && !tree.checkPoint(p)){
                    JOptionPane.showMessageDialog(panelSearch
                            , Dictionary.ERROR.ERROR_POINT_NOT_EXISTS.getString()
                            , Dictionary.ERROR.ERROR.getString()
                            , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Process.searchPointAndPaint(p);
            }catch(Exception ex){}
        }
        textLabelNodeSearch.setText("");
        clearText(vectorSearchTextField);
        centerPanel.repaint();
    }

    private void changeTableQueue() {
        try{
            
            TitledBorder border = (TitledBorder) panelQueue.getBorder();
                         border.setTitleJustification(TitledBorder.CENTER);
                         border.setTitlePosition(TitledBorder.TOP);
                         border.setTitle(Dictionary.Words.QUEUE.getString());
                         border.setTitleFont(new Font(Dictionary.Font.DEFAULT.getString()
                                 , Font.PLAIN ,Dictionary.Font_Size.DEFAULT.getValue()));
            
            panelQueue.setBorder(border);
            
            tableQueue.getColumnModel().getColumn(0)
                    .setHeaderValue(Dictionary.Words.LABEL.getString());
            for(int i=0; i<dimension; ++i){
                tableQueue.getColumnModel().getColumn(i+1)
                    .setHeaderValue(Dictionary.Words.VALUE.getString() + " " + (i + 1));
            }
        }catch(NullPointerException ex){}
    }

    private boolean checkLabelInQueue(String label) {
        for(int i=0; i<tableQueue.getRowCount(); ++i){
            if(tableQueue.getValueAt(i, 0).toString().equals(label)){
                return true;
            }
        }
        return false;
    }

    private boolean checkPointInQueue(Point p) {
        Vector vectorTmp = p.getLocation();
        for(int i=0; i<tableQueue.getRowCount(); ++i){
            boolean ok = true;
            for(int j=1; j<tableQueue.getColumnCount(); ++j){
                if(tableQueue.getValueAt(i, j).equals(vectorTmp.get(j-1)) == false){
                    ok = false;
                }
            }
            if(ok){
                return true;
            }
        }
        return false;
    }

    public void clearTable() {        
        while(tableQueue.getRowCount() > 0)
            ((DefaultTableModel) tableQueue.getModel()).removeRow(0);
        ControlTreePanel.scrollPane.getViewport()
                        .setViewPosition(new java.awt.Point(0, 0));
        
    }
    
    public static void swapValue(String label, String label0) {
        int row1,row2;
        row1 = row2 = -1;
        for(int i=0; i<tableQueue.getRowCount(); i++){
            if(tableQueue.getValueAt(i, 0).equals(label)) row1 = i;
            if(tableQueue.getValueAt(i, 0).equals(label0)) row2 = i;
            
            if(row1 != -1 && row2 != -1){
                tableQueue.swapValue(row1,row2);
                return;
            }
        }
    }
    
}

