package pGen;

public class variable {
	private double min, max;
	private double difference;
	private double curVal;
	private variable upperLimit;

	public variable(double min, double max, double difference) {
		this.min = min;
		this.max = max;
		this.difference = difference;
	}

	// variable dependent variable
	public variable(double min, double difference, variable upperLimit) {
		this.min = min;
		this.difference = difference;
		this.upperLimit = upperLimit;
	}

	public boolean ifViolate() {
		if (upperLimit != null) {
			if (!(upperLimit.curVal() >= curVal)) {
				return true;
			}
		}
		return false;
	}

	public double curVal() {
		return curVal;
	}

	public void setCurVal(double curVal) {
		this.curVal = curVal;
	}

	public double[] allVal() {
		double[] ret = new double[(int) ((max - min) / difference + 1)];
		int j = 0;
		for (double i = min; i <= max; i += difference) {
			System.out.println(i);
			ret[j] = i;
			j++;
		}
		return ret;
	}
}
