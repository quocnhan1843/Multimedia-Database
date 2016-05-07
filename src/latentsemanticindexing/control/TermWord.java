/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import edu.sussex.nlp.jws.JWS;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class TermWord {
    
    
    private static JWS ws = new JWS("D:/Program Files (x86)/WordNet","2.1");  
    private static WordnetStemmer stem =  new WordnetStemmer(ws.getDictionary());
    
    public static String getTermWord(String string){
                
        List advArray = null;
        List adjArray = null;
        List nounArray = null;
        List verbArray = null;
        try{
            advArray = (List) stem.findStems(string, POS.ADVERB);
        }
        catch(Exception ex){
        }
        try{
            adjArray = (List) stem.findStems(string, POS.ADJECTIVE);
        }
        catch(Exception ex){}
        try{
            nounArray = (List) stem.findStems(string, POS.NOUN);
        }
        catch(Exception ex){}
        try{
            verbArray = (List) stem.findStems(string, POS.VERB);
        }
        catch(Exception ex){}
        
        
        String noun = "", verb = "", adj = "", adv = "";
        
//        System.out.println("=================================");
//        System.out.println(nounArray);
//        System.out.println(verbArray);
//        System.out.println(adjArray);
//        System.out.println(advArray);
//        System.out.println("=================================");
        
        noun = getWordTerm(nounArray);
        verb = getWordTerm(verbArray);
        adj = getWordTerm(adjArray);
        adv = getWordTerm(advArray);
        
        int len1 = noun.length();
        int len2 = verb.length();
        int len3 = adj.length();
        int len4 = adv.length();
        
        if(len1 == 0) len1 = 1000;
        if(len2 == 0) len2 = 1000;
        if(len3 == 0) len3 = 1000;
        if(len4 == 0) len4 = 1000;
        
        if(len1 > 0 && len1 < len2 && len1 < len3 && len1 < len4) return noun;
        if(len2 > 0 && len2 < len1 && len2 < len3 && len2 < len4) return verb;
        if(len3 > 0 && len3 < len2 && len3 < len1 && len3 < len4) return adj;
        if(len4 > 0 && len4 < len2 && len4 < len3 && len4 < len1) return adv;
        
        if(len1 > 0) return noun;
        if(len2 > 0) return verb;
        if(len3 > 0) return adj;
        return adv;
    }
    
    private static String getWordTerm(List<String> list){
        try{
            if(list.size() == 0) return "";
            return list.get(0);
        }catch(NullPointerException ex){
            return "";
        }
        
    }
}
