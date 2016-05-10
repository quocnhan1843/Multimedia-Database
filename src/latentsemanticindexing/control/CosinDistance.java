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
public class CosinDistance {
    
    private static double[] tableFrequency;// = new double[];
    private static double[] vectorQuery;
   
    public static double getDistance(double[] vector, double[] table){
        
        
        tableFrequency = table;
        vectorQuery = vector;

//        for(int i=0; i<vector.length; ++i){
////                vectorQuery[i] = vector[i];
////                tableFrequency[i] = table[i];
//
//            System.out.println(vectorQuery[i]);
//            System.out.println(tableFrequency[i]);
//        }

        double tuso = getNumerator();
        double mau1 = Math.sqrt(calculate(vectorQuery));
        double mau2 = Math.sqrt(calculate(tableFrequency));
            
        return tuso/(mau1*mau2);
    }
    private static double getNumerator(){
        return calculate(vectorQuery, tableFrequency);
    }
    
    private static double getDenominator(){
        return calculate(vectorQuery)*calculate(tableFrequency);
    }
    private static double calculate(double[] arr){
        double result = 0.0;
        for(int i=0; i<arr.length; ++i){
            result += arr[i]*arr[i];
        }
        return result;
    }
    
    private static double calculate(double[] vector, double[] frequency){
        double result = 0.0;
        for(int i=0; i<vector.length; ++i){
            result += tich(vector[i], frequency[i]);
        }
        return result;
    }
     private static double tich(double a, double b){
        return a*b;
    }
    
}
