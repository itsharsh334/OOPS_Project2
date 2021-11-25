package oops;
import java.util.Scanner;

public class Strings implements Driver {
	private SlidingObjects o1;
	private SlidingObjects o2;
	public Strings(SlidingObjects o1, SlidingObjects o2) {
		super();
		this.o1 = o1;
		this.o2 = o2;
	}
	@Override
	public void simulate(int a, int b, Scanner input){ 
		// TODO Auto-generated method stub
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
		double m1 = o1.getWeight();
		double m2 = o2.getWeight();
		double g = o1.getG();
		double x1,x2,y1,y2,v1,v2,a1,a2;
		if(m1 > m2)
		{
			a1 = ((m1-m2)*g)/(m1+m2);
			o1.setA(a1);
			o2.setA(a1*(-1));
			for(double i = 0.0; i < t; i+=ts)
			{
				x1=o1.getCoordinates().getX();
				y1=o1.getCoordinates().getY();
				
				v1=o1.getV();
				a1 = o1.getA();
				y1 = y1 + v1*ts + (0.5)*a1*ts*ts;
				System.out.println("coordinates of the object1 after "+i+" seconds is ("+ x1 + ","+y1+").");
				Coordinates c1 = new Coordinates(x1,y1);
				o1.setCoordinates(c1);
				o1.setV(a1*ts);
				
				x2=o2.getCoordinates().getX();
				y2=o2.getCoordinates().getY();
				v2=o2.getV();
				a2 = o2.getA();
				y2 = y2 + v2*ts + (0.5)*a2*ts*ts;
				System.out.println("coordinates of the object2 after "+i+" seconds is ("+ x2 + ","+y2+").");
				Coordinates c2 = new Coordinates(x1,y1);
				o2.setCoordinates(c2);
				o2.setV(a2*ts);
			}
		}
		else
		{
			a1 = ((m2-m1)*g)/(m1+m2);
			o1.setA(a1);
			o2.setA(a1*(-1));
			for(double i = 0.0; i < t; i+=ts)
			{
				x1=o1.getCoordinates().getX();
				y1=o1.getCoordinates().getY();
				
				v1=o1.getV();
				a1 = o1.getA();
				y1 = y1 + v1*ts + (0.5)*a1*ts*ts;
				System.out.println("coordinates of the object1 after "+i+" seconds is ("+ x1 + ","+y1+").");
				Coordinates c1 = new Coordinates(x1,y1);
				o1.setCoordinates(c1);
				o1.setV(a1*ts);
				
				x2=o2.getCoordinates().getX();
				y2=o2.getCoordinates().getY();
				v2=o2.getV();
				a2 = o2.getA();
				y2 = y2 + v2*ts + (0.5)*a2*ts*ts;
				System.out.println("coordinates of the object2 after "+i+" seconds is ("+ x2 + ","+y2+").");
				Coordinates c2 = new Coordinates(x1,y1);
				o2.setCoordinates(c2);
				o2.setV(a2*ts);
			}
		}
	}
	@Override
	public void simulate(int a, int b, Scanner s, SlidingObjects so) {
		// TODO Auto-generated method stub
		
	}

}