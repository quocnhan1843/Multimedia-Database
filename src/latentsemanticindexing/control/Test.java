/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

/**
 *
 * @author ASUS
 */
public class Test {
    public static void main(String[] args) {
        StopWords.createStopWords();

        //String string = "loving";
        String string = "bad worse worst";
        System.out.println(RemoveStopWord.getList(string));
        //System.out.println(TermWord.getTermWord("teacher"));
    }
}
