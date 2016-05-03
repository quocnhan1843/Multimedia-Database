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
public class Analysis {
    private double[][] TERM_DOCUMENT_MATRIX;
    private String[] TERMS;
    private String[] DOCS;
    
    private double cosine(double[] xs, double[] ys, double[] scales) {
	double product = 0.0;
	double xsLengthSquared = 0.0;
	double ysLengthSquared = 0.0;
	for (int k = 0; k < xs.length; ++k) {
	    double sqrtScale = Math.sqrt(scales[k]);
	    double scaledXs = sqrtScale * xs[k];
	    double scaledYs = sqrtScale * ys[k];
	    xsLengthSquared += scaledXs * scaledXs;
	    ysLengthSquared += scaledYs * scaledYs;
	    product += scaledXs * scaledYs;
	}
	return product / Math.sqrt(xsLengthSquared * ysLengthSquared);
    }
}
