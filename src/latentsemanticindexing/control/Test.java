/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author ASUS
 */
public class Test {
    public static void main(String[] args) {
        StopWords stopword = new StopWords();
        try {
            //System.out.println(stopword.size());
            BufferedReader br = new BufferedReader(
                    new FileReader("D:\\MyProject\\Multimedia-Database\\lib\\stopword.txt"));
            String stringCurrentLine = "";
            while ((stringCurrentLine = br.readLine()) != null) {
                if(stopword.isStopWord(stringCurrentLine))
                    System.out.println(stringCurrentLine + " is stop word");
                else
                    System.out.println(stringCurrentLine + " is not stop word");
            }
        } catch (FileNotFoundException ex) {
        } catch(IOException iOException){
        } catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }
}
