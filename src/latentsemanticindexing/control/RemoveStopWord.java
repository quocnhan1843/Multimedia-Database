/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author ASUS
 */
public class RemoveStopWord {
    
    private static char[] listStopChar = {'\'','-'};
    
    public static Vector getList(String string){        
        string = string.toLowerCase();
        
        Vector vectorWord = getListWord(string);
        Vector vectorResult = new Vector();
        for(int i=0; i<vectorWord.size(); ++i){
            String word = (String) vectorWord.get(i);
            if(!StopWords.isStopWord(word)){
                String s = TermWord.getTermWord(word);
                if(s.equals("")) s = word;
                vectorResult.addElement(s);
            }
        }
        
        Collections.sort(vectorResult);
        return vectorResult;
    }
    
    private static Vector getListWord(String string){
        Vector vectorWord = new Vector();
        String word = "";
        for(int i=0; i<string.length(); ++i){
            if(!validChar(string.charAt(i))){
                word = word.replaceAll(" ", "");
                if(!word.equals("")) vectorWord.add(word);
                //vectorWord.addElement(word);
                word = "";
            }else{
                word += string.charAt(i);
            }
        }
        word = word.replaceAll(" ", "");
        if(!word.equals("")) vectorWord.add(word);
        return vectorWord;
    }
    
    private static boolean validChar(char c){
        for(char ch='a'; ch<='z'; ++ch){
            if(c == ch) return true;
        }
        for(int i=0; i<listStopChar.length; i++){
            if(c == listStopChar[i]) return true;
        }
        return false;
    }
}
