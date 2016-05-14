/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

import UI.Dictionary;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author quocn
 */
public abstract class Anlysis extends JPanel{
    
    public Anlysis(){
    }
    
    public static Anlysis createPanel(String typeString){
        if(typeString.equals(Dictionary.TYPE.NORMAL.getString())){
            return new Frequency();
        }else if(typeString.equals(Dictionary.TYPE.IF_IDF.getString())){
            return new IF_IDF();
        }else if(typeString.equals(Dictionary.TYPE.IF_IDF_SVD.getString())){
            return new IF_IDF_SVD();
        }
        return new SVD();
    }
    
    public abstract void loadTable(List<DataDocument> listIdDocument, 
            List<DataTermWord>  listIdTermWord, HashMap listWordQR,
            String databaseName);
}
