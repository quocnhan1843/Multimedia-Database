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
    
    private double[] tableFrequency;
    private double[] vectorQuery;

    public CosinDistance() {
    }    
    public CosinDistance(double[] vector, double[] table){
        for(int i=0; i<vector.length; ++i){
            vectorQuery[i] = vector[i];
            tableFrequency[i] = table[i];
        }
    }
    public double getDistance(){
        return Math.sqrt(getNumerator())/(Math.sqrt(calculate(vectorQuery))
                *Math.sqrt(calculate(tableFrequency)));
    }
    private double getNumerator(){
        return calculate(vectorQuery, tableFrequency);
    }
    
    private double getDenominator(){
        return calculate(vectorQuery)*calculate(tableFrequency);
    }
    private double calculate(double[] arr){
        double result = 0.0;
        for(int i=0; i<arr.length; ++i){
            result += arr[i]*arr[i];
        }
        return result;
    }
    
    private double calculate(double[] vector, double[] frequency){
        double result = 0.0;
        for(int i=0; i<vector.length; ++i){
            result += tich(vector[i], frequency[i]);
        }
        return result;
    }
     private double tich(double a, double b){
        return a*b;
    }
    
}
