package pGen;

import java.util.ArrayList;

public interface formula {
	public boolean inRange(double[] allVal, double p);
	public ArrayList<double[]> winnow(ArrayList<double[]> posSol, double p);
	public double[] randdomize(ArrayList<double[]> posSol);
	public double computeSum(double[] allVal);
	public variable[] getVar();
	public double getP();
}
