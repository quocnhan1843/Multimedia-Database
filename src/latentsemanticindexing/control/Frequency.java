/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latentsemanticindexing.control;

/**
 *
 * @author quocn
 */
public class Frequency {
    public static Frequency instance = null;
    public static Frequency getInstance(){
           if(instance == null) instance = new Frequency();
           return instance;
    }
}
