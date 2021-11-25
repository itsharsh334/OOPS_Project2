package oops;
import java.util.Scanner;
public class SlidingObjects extends Object implements Driver{
	private double length;
	private double height;
	public SlidingObjects(Object obj,int choice,int subchoice) {
		super(obj);
		length=0;
		height=0;
	}
	public SlidingObjects(Object obj,double l, double h) {
		super(obj);
		this.length=l;
		this.height=h;
	}
	public void simulate(int choice,int subchoice,Scanner input) {
		System.out.println("Enter the timeslice and total time respectively : ");
		double ts = input.nextDouble();
		input.nextLine();
		while(ts<0) {
			System.out.println("time cannot be negative...please re enter ");
			ts = input.nextDouble();
			input.nextLine();
		}
		double t = input.nextDouble();
		input.nextLine();
		while(t<0||t<ts) {
			System.out.println("time cannot be negative or less than time slice...please re enter ");
			t = input.nextDouble();
			input.nextLine();
		}
		if(choice==1&&subchoice==1) {
			planeGround(ts,t);
		}
		else if(choice==1&&subchoice==2) {
			double v,x,y,g,theta,e,vX,vY;
			x=super.getCoordinates().getX();
			y=super.getCoordinates().getY();
			v=super.getV();
			g=super.getG();
			e=super.getE();
			theta=super.getTheta();
			vX=v*Math.cos(theta);
			vY=v*Math.sin(theta);
			projectile(vX,vY,x,y,g,ts,t,e,theta);
			
		}
		else if(choice==1&&subchoice==3) {
			inclinedPlane(ts,t);
		}
		else if(choice==4&&subchoice==1) {
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
			planeSpring(ts,t,k,x0,input);
		}
	}
	public void simulate(int choice,int subchoice,Scanner input, SlidingObjects so2)
	{
		double x1,y1,v1,mu,g,x2,v2,v1f,vcm,m1,m2,e,v2f,x1AtTime,t1,x2AtTime;
		System.out.println("Enter the timeslice and total time respectively : ");
		double ts = input.nextDouble();
		input.nextLine();
		double t = input.nextDouble();
		input.nextLine();
		x1=super.getCoordinates().getX();
		y1=super.getCoordinates().getY();
		x2 = so2.getCoordinates().getX();
		v1 = this.getV();
		v2 = so2.getV();
		m1 = this.getWeight();
		m2 = so2.getWeight();
		e = this.getE();
		mu=super.getMu();
		g=super.getG();
		if(x2-x1 > 0 && v2 - v1 < 0)
		{
			double col_time = (x2-x1)/(v1-v2);
			t1 = v1/mu*g;
			if(v2>=0)
			{
				if(t1 <= col_time)
				{
					System.out.println("Objects will not collide");
				}
				else
				{
					for(double i = 0; i < t; i+=ts)
					{
						if(i < col_time)
						{
							x1AtTime = v1*i+0.5*((-1)*mu*g)*i*i+x1;
							x2AtTime = v2*i +0.5*(-1)*mu*g*i*i + x2;
							System.out.println("coordinates of the object1 after "+i+" seconds is ("+ x1AtTime + ","+y1+").");
							System.out.println("coordinates of the object2 after "+i+" seconds is ("+ x2AtTime + ","+y1+").");
						}
					}
					v1 = v1-mu*g*col_time;
					v2 = v2-mu*g*col_time;
					vcm = (m1*v1 + m2*v2)/(m1+m2);
					v1f = (1+e)*vcm -e*v1;
					v2f = (1+e)*vcm -e*v2;
					double xc1,xc2;
					System.out.println("Objects will collide at " +col_time+ "seconds");
					xc1 = v1*col_time+0.5*((-1)*mu*g)*col_time*col_time+x1;
					xc2 = v2*col_time+0.5*((-1)*mu*g)*col_time*col_time+x2;
					System.out.println("coordinates of the object1 after "+col_time+" seconds is ("+ xc1 + ","+y1+").");
					System.out.println("coordinates of the object2 after "+col_time+" seconds is ("+ xc2 + ","+y1+").");
					for(double i = col_time + ts; i < t; i+=ts)
					{
						double q = i-col_time;
						x1AtTime = xc1 + v1f*q -0.5*mu*g*q*q;
						x2AtTime = xc2 + v2f*q -0.5*mu*g*q*q;
						System.out.println("coordinates of the object1 after "+i+" seconds is ("+ x1AtTime + ","+y1+").");
						System.out.println("coordinates of the object2 after "+i+" seconds is ("+ x2AtTime + ","+y1+").");
					}
				}
			}
			
		}
		
	}
	public void plane(int subchoice,double ts, double t) {
		double a,v,x,y,mu,g,xAtTime;
		x=super.getCoordinates().getX();
		y=super.getCoordinates().getY();
		mu=super.getMu();
		v=super.getV();
		a =super.getA();
		g=super.getG();
		if(a<mu*g)
		{
			double stoppingtime = -v/(-mu*g+a);
			for(double i=0.0 ;i<t&&i<stoppingtime;i+=ts ) {
				xAtTime=v*i+0.5*(a-mu*g)*i*i+x;
				System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+y+").");
				}
				xAtTime=v*stoppingtime+0.5*(a-mu*g)*stoppingtime*stoppingtime+x;
				if(stoppingtime>=t)
					x=v*t+0.5*(a-mu*g)*t*t+x;
				else
					x=xAtTime;
				System.out.println("Object comes to rest after "+stoppingtime+" seconds and at coordinates (" +xAtTime+ ","+y+").");
				
		}
		if(a>mu*g) {
			for(double i=0.0 ;i<t;i+=ts ) {
				xAtTime=v*i+0.5*(a-mu*g)*i*i+x;
				System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+y+").");
				}
				x=v*t+0.5*(a-mu*g)*t*t+x;
		}
			
		super.getCoordinates().setX(x);
		System.out.println("the position after time "+t+" is ("+x+","+y+").");
	}

	public void projectile(double vX,double vY, double x, double y,double g,double ts, double t,double e,double theta) {
		double tof = (2*vY)/g;
		double xAtTime=0,yAtTime,rangeAtTime=0,tof2=tof;
		double range=tof*vX;
		for(double i=0;i<t;i+=ts) {
			if(i<=tof) {
				xAtTime=vX*i;
				yAtTime=(xAtTime-rangeAtTime)*Math.tan(theta)*(1-((xAtTime-rangeAtTime)/range));
				System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+yAtTime+").");
			}
			else if(i>tof) {
				tof2=e*tof2;
				System.out.println("Object hits the ground at "+ tof +" seconds at coordinates (" +vX*tof +",0).");
				tof=tof+tof2;
				rangeAtTime+=range;
				range=e*range;
			}
		}
		if(ts>tof2)
		System.out.println("Time Slice too large in comparison to current time of flight for x and y to be calculated");
	}
	public void planeGround(double ts, double t) {
		double a,v,x,y,mu,g,xAtTime;
		x=super.getCoordinates().getX();
		y=super.getCoordinates().getY();
		mu=super.getMu();
		v=super.getV();
		a =super.getA();
		g=super.getG();
		if(a<mu*g)
		{
			double stoppingtime = -v/(-mu*g+a);
			for(double i=0.0 ;i<t&&i<stoppingtime;i+=ts ) {
				xAtTime=v*i+0.5*(a-mu*g)*i*i+x;
				System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+y+").");
				}
				xAtTime=v*stoppingtime+0.5*(a-mu*g)*stoppingtime*stoppingtime+x;
				if(stoppingtime>=t)
					x=v*t+0.5*(a-mu*g)*t*t+x;
				else
					x=xAtTime;
				System.out.println("Object comes to rest after "+stoppingtime+" seconds and at coordinates (" +xAtTime+ ","+y+").");
				
		}
		if(a>mu*g) {
			for(double i=0.0 ;i<t;i+=ts ) {
				xAtTime=v*i+0.5*(a-mu*g)*i*i+x;
				System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+y+").");
				}
				x=v*t+0.5*(a-mu*g)*t*t+x;
		}
			
		super.getCoordinates().setX(x);
		System.out.println("the final position after time "+t+" is ("+x+","+y+").");
	}
	public void planeSpring(double ts, double t,double k,double x0,Scanner input) {
		double v,x,y,m,xAtTime;
		x=super.getCoordinates().getX();
		y=super.getCoordinates().getY();
		v=super.getV();
		m=super.getWeight();
		double amp=Math.abs(v*Math.sqrt(m/k));
		double angF=Math.sqrt(k/m);
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
	public void inclinedPlane(double ts, double t) {
			double v,x,y,g,xAtTime,yAtTime,theta,e;
			x=super.getCoordinates().getX();
			y=super.getCoordinates().getY();
			System.out.println("Initial coordinates are ("+x+","+y+")");
			v=super.getV();
			g=super.getG();
			e=super.getE();
			theta=super.getTheta();
			double l=Math.abs(x/Math.cos(theta));// l is length of slope
			double rootd=Math.sqrt(v*v+2*l*g*Math.sin(theta));
			double stoppingtime=(-v+rootd)/(g*Math.sin(theta));
			System.out.println("Object leaves the inclined surface at "+stoppingtime+" seconds");
			for(int i=0;i<t;i+=ts) {
				if(i<=stoppingtime) {
					 l=v*i+0.5*g*i*i*Math.sin(theta);
					 xAtTime=x+l*Math.cos(theta);
					 yAtTime=y-l*Math.sin(theta);
					 System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+yAtTime+").");
				}
				else if(i>stoppingtime) {
					xAtTime=e*v*(i-stoppingtime);
					yAtTime=0;
					System.out.println("coordinates of the object after "+i+" seconds is ("+ xAtTime + ","+yAtTime+").");
				}
			}
	}
	public void topple(double force) {
		double mu,m;
		m=super.getWeight();
		mu=super.getMu();
		double g=super.getG();
		double hmin = m*g*length/(2*force);
		if(height<hmin&&force<mu*m*g) {
			System.out.println("Object neither slides nor topples ");
		}
		else if(height<hmin&&force>=mu*m*g) {
			System.out.println("Object slides but does not topple ");
		}
		else if(height==hmin&&force==mu*m*g) {
			System.out.println("Object just about to slide and topple both ");
		}
		else if(height>=hmin) {
			System.out.println("Object topples ");
		}
	}

}


