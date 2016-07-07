package pGen;

import java.util.ArrayList;

public class pGen {
	private variable[] vars;
	private int num;
	private double p, sum; // p >= sum >=0.8p
	private ArrayList<double[]> posSol;
	private double[] randSol;
	private formula curForm;

	public pGen(formula curForm) {
		this.vars = curForm.getVar();
		this.curForm = curForm;
		this.p = curForm.getP();
		this.posSol = new ArrayList<double[]>();
		this.num = vars.length - 1;
		double[] res = new double[num + 1];
		curse(vars[0].allVal(), res, num);
		solve();
	}

	private void curse(double[] allVal, double[] res, int time) {
		if (time == 0) {
			posSol.add(res);
			return;
		}
		for (int i = 0; i < vars[num - time].allVal().length; i++) {
			res[num - time] = 
					vars[num - time].
					allVal()[i];
			vars[num - time].setCurVal(res[num - time]);
			if (!vars[num - time].ifViolate()) {
				curse(vars[num - time].allVal(), res, time - 1);
			}
		}
	}

	public void solve() {
		posSol = curForm.winnow(posSol, p);
		System.out.println("size of solutions is: " + posSol.size());
		randSol = curForm.randdomize(posSol);
		sum = curForm.computeSum(randSol);
	}
	
	public String toString() {
		String ret = "sum " + sum + "\n";
		for (int i = 0; i < randSol.length; i++) {
			ret += "var" + i + " is " + randSol[i];
			ret += "\n";
		}
		return ret;
	}

	public static void main(String[] args) {
		double p = (int)(Math.random() * 30) + 35;
		Jed theGreat = new Jed(p);
		System.out.println("p is " + p);
		System.out.println(new pGen(theGreat));
	}
}
