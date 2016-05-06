/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author ASUS
 */
public class StopWords {
    
    private static Vector treeStopWord;
    private static StopWords instance;
    
    public StopWords(){
        init();
        createList();
    }   
    private void init(){
        treeStopWord = new Vector();
        treeStopWord.addElement(new Node());
    }
    public static void createStopWords(){
        if(instance == null) instance = new StopWords();
    }
    private void createList(){
        BufferedReader br = null;

        try {
            String stringCurrentLine;

            br = new BufferedReader(new FileReader(ClassLoader
                    .getSystemResource(".//resource//stopword.txt").getPath()));

            while ((stringCurrentLine = br.readLine()) != null) {
                   addString(stringCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    private void addString(String string){   
        int id = 0;
        for(int i=0; i<string.length(); i++){
            char c = string.charAt(i);
            int indexString = Node.getIndexChar(c);
            int index = id;
            Node node = (Node) treeStopWord.get(index);
            id = node.getNext(indexString);
            
            if(id == -1){
                treeStopWord.addElement(new Node());
                id = treeStopWord.size()-1;
                node.setNext(indexString, treeStopWord.size()-1);
            }
            if(i == string.length() - 1){
                Node tempNode = (Node) (treeStopWord.get(id));
                tempNode.setEnd();
            }
        }
    }
    public int size(){
        return treeStopWord.size();
    }
    public static boolean isStopWord(String string){
        int id = 0;
        for(int i=0; i<string.length(); i++){
            char c = string.charAt(i);
            int indexString = Node.getIndexChar(c);
            int index = id;
            Node node = (Node) treeStopWord.get(index);
            id = node.getNext(indexString);
            
            if(id == -1){
                return false;
            }
            node = (Node) treeStopWord.get(id);
            if(node.isEnd() && i == string.length() - 1) return true;            
        }
        return false;
    }
}
class Node{
    private Vector vectorNext;
    private boolean endWord;
    public Node(){
        init();
        setContent();
    }
    private void init(){
        vectorNext = new Vector();
        endWord = false;
    }
    private void setContent(){
        for(int i=0; i<=32; i++){
            vectorNext.addElement(-1);
        }
    }
    public boolean isEnd(){
        return endWord == true;
    }
    public void setEnd(){
        endWord = true;
    }
    public static int getIndexChar(char c){
        //if('0' <= c && c <= '9') return c - '0';
        if('a' <= c && c <= 'z') return (c - 'a');
        if(c == '\'') return 27;
        return -1;
    }
    public int getNext(int k){
        return (int) vectorNext.get(k);
    }
    public void setNext(int index, int k){
        vectorNext.set(index, k);
    }
}
