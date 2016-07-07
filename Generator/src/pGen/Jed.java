package pGen;

import java.util.ArrayList;

public class Jed implements formula{
	private variable[] vars;
	private double p;
	public Jed(double p) {
		this.p = p;
		vars = new variable[6];
		vars[0] = new variable(2, 7, 1);
		vars[1] = new variable(2, 7, 1);
		vars[2] = new variable(1.25, 2, 0.25);
		vars[3] = new variable(1.25, 2, 0.25);
		vars[4] = new variable(0, 1, vars[0]);
		vars[5] = new variable(0, 1, vars[1]);
	}
	public boolean inRange(double[] allVal, double p) {
		double sum = computeSum(allVal);
		if (p >= sum && p * 0.8 <= sum) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<double[]> winnow(ArrayList<double[]> posSol, double p) {
		for (int i = 0; i < posSol.size(); i++) {
			if (!inRange(posSol.get(i), p)) {
				posSol.remove(i);
			}
		}
		return posSol;
	}

	@Override
	public double[] randdomize(ArrayList<double[]> posSol) {
		int rand = (int)(posSol.size() * Math.random());
		return posSol.get(rand);
	}

	@Override
	public double computeSum(double[] allVal) {
		if (allVal.length != 6) {
			throw new IllegalArgumentException("bads");
		}
		return (allVal[0] + allVal[1]) * (allVal[2] + allVal[3]) + allVal[4] * allVal[5];
	}
	
	public variable[] getVar() {
		return vars;
	}
	public double getP() {
		return p;
	}
}
