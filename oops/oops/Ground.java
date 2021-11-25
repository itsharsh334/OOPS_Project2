package oops;

public class Ground {
	private double theta;//for angle of projection and angle of slope
	private double g;
	private double mu;
	private double e;
	
	public double getTheta() {
		return theta;
	}

	public double getG() {
		return g;
	}

	public double getMu() {
		return mu;
	}

	public double getE() {
		return e;
	}
	public Ground(double theta, double mu, double e) {
		this.theta = theta;
		this.g = 9.8;
		this.mu = mu;
		this.e = e;
	}
	public Ground(Ground ground) {
		this(ground.theta,ground.mu,ground.e);
	}
}