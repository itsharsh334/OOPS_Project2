package oops;
import java.util.Scanner;

public class RollingObjects extends Object implements Driver{
	private double radius;	
	private double radiusOfGyration;
	private double omega;
	public RollingObjects(Ground ground,int objectid, double weight, double v, Coordinates coordinates,double a,
			double radius, double radiusOfGyration, double alpha, double omega) {
		super(ground, objectid, weight, v, coordinates, a);
		this.radius = radius;
		this.radiusOfGyration = radiusOfGyration;
		this.omega = omega;
	}
	public RollingObjects(Object obj,double radius, double radiusOfGyration, double alpha, double omega) {
		super(obj);
		this.radius=radius;
		this.radiusOfGyration=radiusOfGyration;
		this.omega=omega;
	}
	public RollingObjects(Object obj, int choice, int subchoice) {
		super(obj);
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public double getRadiusOfGyration() {
		return radiusOfGyration;
	}
	public void setRadiusOfGyration(double radiusOfGyration) {
		this.radiusOfGyration = radiusOfGyration;
	}
	public double getOmega() {
		return omega;
	}
	public void setOmega(double omega) {
		this.omega = omega;
	}
	@Override
	public void simulate(int choice, int subchoice, Scanner input) {
		if(choice==2) {
			System.out.println("Please enter the type of object for simulation \n1. Cylinder 2. Sphere \n 3. Ring");
			int z = input.nextInt();
			input.nextLine();
			System.out.println("Please enter initial angular velocity:- ");
			this.setOmega(input.nextDouble());
			input.nextLine();
			{
				System.out.println("Enter radius");
				this.radius = input.nextDouble();
				input.nextLine();
				if(z == 1)
				{
					radiusOfGyration = Math.sqrt(0.5)*radius;
				}
				if(z==2)
				{
					radiusOfGyration = Math.sqrt(1.4)*radius;
				}
				else radiusOfGyration = radius;
			}
			System.out.println("Enter the timeslice and total time respectively : ");
			//Scanner input=new Scanner(System.in);
			double ts = input.nextDouble();
			input.nextLine();
			while(ts<0) {
				System.out.println("Please re-enter timeslice as it cant be negative ");
			}
			double t = input.nextDouble();
			input.nextLine();
			while(t<0||t<ts) {
				System.out.println("Please re-enter total time as it cant be negative or less than timeslice");
			}
			if(subchoice==1) {
				plane_roll(subchoice,ts,t);
			}
			else if(subchoice == 2)
			{
				slope_roll(subchoice,ts,t);
			}
			else {
				System.out.println("in the making");
			}
			
		}
		else {
			System.out.println("Enter the timeslice and total time respectively : ");
			double ts = input.nextDouble();
			input.nextLine();
			while(ts<0) {
				System.out.println("time cannot be negative...please re-enter ");
				ts = input.nextDouble();
				input.nextLine();
			}
			double t = input.nextDouble();
			input.nextLine();
			while(t<0||t<ts) {
				System.out.println("total time cannot be negative or less than timeslice...please re-enter ");
				t = input.nextDouble();
				input.nextLine();
			}
			if(choice==4) {
				System.out.println("please enter the spring coefficient : - ");
				double k=input.nextDouble();
				input.nextLine();
				while(k<0) {
					System.out.println("spring constant cannot be negative...please re-enter ");
					k = input.nextDouble();
					input.nextLine();
				}
				System.out.println("Please enter initial displacement from mean position x = "+ super.getCoordinates().getX() +" at t=0 seconds ");
				double x0=input.nextDouble();
				input.nextLine();
				if(subchoice==2)
				rollingspring(ts,t,k,x0,input);
				else 
				System.out.println("Invalid Option");
			}
		}
	}
	public void rollingspring(double ts, double t,double k,double x0,Scanner input) {
		double v,x,y,xAtTime,m;
		x=super.getCoordinates().getX();
		y=super.getCoordinates().getY();
		v=super.getV();
		m=super.getWeight();
		double amp=Math.abs(v*Math.sqrt((m*(1+radiusOfGyration))/k)); 
		double angF=Math.sqrt(k/((1+radiusOfGyration)*m));
		while(Math.abs(x0)>amp) {
			System.out.println("Initial displacement cant be more than the amplitude which is "+amp+" , Please Re-enter Your initial displacement:- ");
			x0=input.nextDouble();
			input.nextLine();
		}
		double phase = Math.asin(x0/amp);
		for(double i=0.0;i<t;i+=ts) {
			xAtTime=x+amp*Math.sin(angF*i+phase);
			System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+y+").");
		}
		x=x+amp*Math.sin(angF*t+phase);
		System.out.println("coordinates of the object after "+t+" seconds is ("+ x + ","+y+").");  
	}
	@Override
	public void simulate(int a, int b, Scanner s, SlidingObjects so) {
		// stub
	}
	public void plane_roll(int subchoice,double ts, double t) {
		// TODO Auto-generated method stub
		int b = -1;
		double v,x,y,mu,g,xAtTime,r,k;
		r=radius;
		k=radiusOfGyration;
		x=super.getCoordinates().getX();
		y=super.getCoordinates().getY();
		mu=super.getMu();
		v=super.getV();
		g=super.getG();
		if(v>r*omega) b=1;
		double q = (v-r*omega)/(mu*g*(((r*r)/(k*k))+1));
		for(double i = 0.0; i < t; i+=ts)
		{
			if(v==r*omega)
			{
				xAtTime = x + v*i;
				System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+y+").");
			}
			else
			{
				if(i < q*b)
				{
					xAtTime = x + v*i - mu*g*i*i*(1/2)*b;
					System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+y+").");
				}
				else
				{
					xAtTime = x + v*q*b - mu*g*q*q*(1/2)*b + (v - mu*g*q)*(i-q*b);
					System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+y+").");
				}
			}
		}
		}
	public void slope_roll(int subchoice, double ts, double t)
	{
		double v,x,mu,g,xAtTime,yAtTime,r,k;
		r=radius;
		k=radiusOfGyration;
		x = super.getCoordinates().getX();
		mu=super.getMu();
		v=super.getV();
		g=super.getG();
		double q = 1 + (r*r)/(k*k);
		if(Math.tan(super.getTheta()) <= mu*q)
		{
			super.setA(g*Math.sin(super.getTheta())/q);
			double a = super.getA();
			for(double i = 0.0; i < t; i+=ts)
			{
				
				xAtTime=-v*i-0.5*(a)*i*i+x;
				if(xAtTime>0)
					yAtTime = xAtTime*Math.tan(super.getTheta());
				else
					yAtTime=0;
				System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+yAtTime+").");
			}
		}
		else
		{
			super.setA(g*Math.sin(super.getTheta()) - mu*g*Math.cos(super.getTheta()));
			double a = super.getA();
			for(double i = 0.0; i < t; i+=ts)
			{
				xAtTime=-v*i-0.5*(a)*i*i+x;
				if(xAtTime>0)
					yAtTime = xAtTime*Math.tan(super.getTheta());
				else
					yAtTime=0;
				System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+yAtTime+").");
			}
		}
	}
}