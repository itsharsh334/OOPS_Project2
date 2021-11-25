package oops;
import java.util.*;
public class Main{
		static int counter=0;
		static Scanner input;
		static int choice=0;
		public static void main(String[] args) throws Exception{//IO exception for buffered reader
			
			input = new Scanner(System.in);
			outerloop:	
			while(counter>=0) {//asks user to keep choosing option until user exits the simulation
					if(counter>=1) {
						System.out.println("Press enter to proceed to Menu");
						input.nextLine();
					} 
				    counter++;
				    System.out.println("\t\t\tSPRING, ACTION AND FORCES SIMULATOR");
				    System.out.println("what would you like to do?");
				    System.out.println("1.No Roll(Sliding and projectile) \n2.Roll \n3.Collide \n4.Spring \n5.String \n6.Toppling from external Force\n7.Exit");
				    System.out.println("Enter Your Choice: ");
		    		choice=input.nextInt();
		    		input.nextLine();
		    	    int choice1,choice2;
		    	    Ground ground;
		    	    Object obj;
		    	    SlidingObjects so;
		    	    RollingObjects ro;
					switch(choice) {
						case 1:	choice1=subchoice(choice);
								ground = new Ground(fillground(choice,choice1));
								obj = new Object(fillObject(choice,choice1,ground));
								so = new SlidingObjects(fillSO(obj,choice,choice1),choice,choice1);
								so.simulate(choice,choice1,input);
								break;
						case 2:	System.out.println("rolling");
								choice2 = subchoice(choice);
								Ground ground1 = new Ground(fillground(choice,choice2));
								Object obj2 = new Object(fillObject(choice,choice2,ground1));
								ro = new RollingObjects(obj2,choice,choice2);
								ro.simulate(choice,choice2,input);
								break;
						case 3:System.out.println("collide");
								int choice3 = subchoice(choice);
								Ground ground11 = new Ground(fillground(choice,choice3));
								System.out.println("For object 1");
								Object obj11 = new Object(fillObject(choice,choice3,ground11));
								SlidingObjects so1 = new SlidingObjects(fillSO(obj11,choice,choice3),choice,choice3);
								System.out.println("For object 2");
								Object obj21 = new Object(fillObject(choice,choice3,ground11));
								SlidingObjects so2= new SlidingObjects(fillSO(obj21,choice,choice3),choice,choice3);
								System.out.println("For object 1");
								so1.simulate(choice, choice3, input,so2);
								break;
						case 4: System.out.println("spring");
								choice1=subchoice(choice);
								ground = new Ground(fillground(choice,choice1));
								obj = new Object(fillObject(choice,choice1,ground));
								if(choice1==1) {
									so= new SlidingObjects(fillSO(obj,choice,choice1),choice,choice1);
									so.simulate(choice,choice1,input);
								}
								else if(choice1==2) {
									System.out.println("\nChoose an object \n1.Solid Sphere\n2.HollowSphere\n3.Disc or solid cylinder\n4.Custom radius of gyration");
									choice2=input.nextInt();
									input.nextLine();
									ro=fillRO(obj,choice,choice1,choice2);
									ro.simulate(choice, choice1, input);
								}
								else
									System.out.println("\n invalid option");
								
								break;
						case 5: System.out.println("string");
								int choice4 = subchoice(choice);
								Ground ground111 = new Ground(fillground(choice,choice4));
								System.out.println("For Object 1 ");
								Object obj111 = new Object(fillObject(choice,choice4,ground111));
								SlidingObjects so11 = new SlidingObjects(fillSO(obj111,choice,choice4),choice,choice4);
								System.out.println("For Object 2 ");
								Object obj3 = new Object(fillObject(choice,choice4,ground111));
								SlidingObjects so21 = new SlidingObjects(fillSO(obj3,choice,choice4),choice,choice4);
								Strings strings = new Strings(so11,so21);
								strings.simulate(choice, choice4, input);
								break;
						case 6: System.out.println("toppling from external force");
								choice1=subchoice(choice);
								ground = new Ground(fillground(choice,choice1));
								obj = new Object(fillObject(choice,choice1,ground));
								double l,h,force;
								System.out.println("Please enter length along which and height at which force is applied respectively ");
								l=input.nextDouble();
								input.nextLine();
								h=input.nextDouble();
								input.nextLine();  
								System.out.println("Please enter External Force applied ");
								force=input.nextDouble();
								input.nextLine(); 
								so = new SlidingObjects(obj,l,h);
								so.topple(force);
								break;
						case 7: System.out.println("Exiting the Menu...");
						        break outerloop; 
						default:System.out.println("Please choose a valid entry format");
								break;
					}
					
				}
			if(choice==7)
			input.close();
	} 
		public static int subchoice(int choice) {
			if(choice==1) {
				System.out.println("please choose a simulation to perform \n1.Object sliding on plane surface \n2.object doing projectile motion \n3.object on slope ");
				int subchoice=input.nextInt();
				input.nextLine();
				while(subchoice!=1&&subchoice!=2&&subchoice!=3) {
					System.out.println("Invalid choice enter again :- " );
					subchoice=input.nextInt();
					input.nextLine();
				}
				return subchoice;
			}
			else if(choice==2) {
				System.out.println("please choose a simulation to perform \n1.Object Rolling on plane surface \n2.object rolling on inclined surface ");
				int subchoice=input.nextInt();
				input.nextLine();
				while(subchoice!=1&&subchoice!=2&&subchoice!=3) {
					System.out.println("Invalid choice enter again :- " );
					subchoice=input.nextInt();
					input.nextLine();
				}
				return subchoice;
				
			}
			else if(choice == 3)
			{
				int choice1=1;
				return choice1;
			}
			else if(choice==4) {
				System.out.println("Please choose a category \n1.Without rolling\n2.With Rolling ");
				int subchoice = input.nextInt();
				input.nextLine();
				while(subchoice!=1&&subchoice!=2) {
					System.out.println("Invalid choice enter again :- " );
					subchoice=input.nextInt();
					input.nextLine();
				}
				return subchoice;
			}
			else if(choice==5)
			{
				int choice1=1;
				return choice1;
			}
			return 0;
			
		}
		public static Ground fillground(int choice,int choice1) { 
			
			double mu=0,theta=0,e=0;
			if((choice1==1&&choice==1)||choice==6) {
				System.out.println("Please enter the values of Coefficient of friction ");
				mu=input.nextDouble();
				input.nextLine();
				while(mu<0||mu>1) {
					System.out.println("Invalid value, coefficient of friction must be between zero to one ");
					mu=input.nextDouble();
					input.nextLine();
				}
				Ground ground = new Ground(theta,mu,e);
				return ground;
			}
			if(choice1==2&&choice==1) {
				System.out.println("please enter the values of angle of Projection (in Degrees) and Coefficient Of Restitution");
				//BufferedReader fin = new BufferedReader(new InputStreamReader(System.in));
				theta=Math.toRadians(input.nextDouble());
				input.nextLine();
				e=input.nextDouble();
				input.nextLine();
				while(e<0||e>1) {
					System.out.println("Invalid value, coefficient of restitution must be between zero to one ");
					e=input.nextDouble();
					input.nextLine();
				}
				Ground ground = new Ground(theta,mu,e);
				return ground;
			}
			if(choice1==3&&choice==1) {
				System.out.println("please enter the value of slope of the inclined plane and coefficient Of restitution");
				theta=Math.toRadians(input.nextDouble());
				input.nextLine();
				e=input.nextDouble();
				input.nextLine();
				while(e<0||e>1) {
					System.out.println("Invalid value, coefficient of restitution must be between zero to one ");
					e=input.nextDouble();
					input.nextLine();
				}
				Ground ground = new Ground(theta,mu,e);
				return ground;
			}
			if(choice==4) {
				Ground ground = new Ground(theta,mu,e);
				return ground;
			}
			if(choice1==1 && choice ==2)
			{
				System.out.println("Please enter the values of Coefficient of friction ");
				mu=input.nextDouble();
				input.nextLine();
				Ground ground = new Ground(theta,mu,e);
				return ground;
			}
			if(choice1==2 && choice == 2)
			{
				System.out.println("please enter the value of slope of the inclined plane");
				theta=Math.toRadians(input.nextDouble());
				input.nextLine();
				Ground ground = new Ground(theta,mu,e);
				return ground;
			}
			if(choice==3)
			{
				if(choice1 == 1)
				{
					System.out.println("Please enter the values of Coefficient of friction ");
					mu=input.nextDouble();
					System.out.println("Please enter the values of Coefficient of restitution ");
					e=input.nextDouble();
					input.nextLine();
					Ground ground = new Ground(theta,mu,e);
					return ground;
				}
			}
			if(choice==5)
			{
				Ground ground = new Ground(theta,mu,e);
				return ground;
			}
			return null;
		}
		public static Object fillObject(int choice,int subchoice,Ground ground){
			double x=0;
			if((choice==1&&subchoice==2)||choice==3) {
				System.out.println("please enter initial coordinates X");
			}
			else if(choice==6) {
				;
			}
			else if(choice==1&&subchoice==3) {
				System.out.println("please enter initial coordinates X, the slope starts towards the left of origin so enter X in negative");
			}
			else if(choice==2&&subchoice==2) {
				System.out.println("please enter initial coordinates X, the slope starts towards the right of origin so enter X in positive");
			}
			else
				System.out.println("please enter initial coordinates X and Y");
			if(choice!=6) {
				x = input.nextDouble();
				input.nextLine();
			}
			while(choice==1&&subchoice==3&&x>0) {
				System.out.println("Please re-Enter x in negative value");
				x = input.nextDouble();
				input.nextLine();
			}
			while(choice==2&&subchoice==2&&x<0) {
				System.out.println("Please re-Enter x in positive value");
				x = input.nextDouble();
				input.nextLine();
			}
			double y=0;
			if((subchoice==3&&choice==1)||(choice==2&&subchoice==2)) {
				y= Math.abs(x*Math.tan(ground.getTheta())); 
			}else if((subchoice==2&&choice==1)||choice==3) {
				y=0;
			}
			else if(choice==6) {
				;
			}
			else {
				y=input.nextDouble();
				input.nextLine();
			}
			Coordinates coordinates = new Coordinates(x,y);
			Object obj = new Object(ground,coordinates);
			if(choice==1||choice==4) {
				System.out.println("Please enter initial velocity:- ");
				obj.setV(input.nextDouble());
			    input.nextLine();
			}
			if(subchoice==1&&choice==1) {
				System.out.println("Please enter initial acceleration:- ");
				obj.setA(input.nextDouble());
				input.nextLine();
			}
			if(choice==4||choice==6) {
				System.out.println("Please enter mass:- ");
				obj.setWeight(input.nextDouble());
				input.nextLine();
				while(obj.getWeight()<0) {
					System.out.println("Invalid value, mass greater than zero required, re-enter ");
					obj.setWeight(input.nextDouble());
					input.nextLine();
				}
			}
			if(choice == 2)
			{
				System.out.println("Please enter initial velocity:- ");
				obj.setV(input.nextDouble());
				input.nextLine();
				if(subchoice == 2)
				{
					y= x*Math.tan(ground.getTheta());
				}
			}
			if(choice == 3)
			{
				System.out.println("Please enter initial velocity:- ");
				obj.setV(input.nextDouble());
				input.nextLine();
				System.out.println("Please enter mass of object");
				obj.setWeight(input.nextDouble());
				input.nextLine();
			}
			if(choice==5)
			{
				System.out.println("Please enter mass of object");
				obj.setWeight(input.nextDouble());
				input.nextLine();
				while(obj.getWeight()<0) {
					System.out.println("Invalid value, mass greater than zero required, re-enter ");
					obj.setWeight(input.nextDouble());
					input.nextLine();
				}
			}
			
			return obj;
		}
		public static SlidingObjects fillSO(Object obj,int choice,int subchoice) {
			SlidingObjects so = new SlidingObjects(obj,choice,subchoice);
			return so;
		}
		public static RollingObjects fillRO(Object obj,int choice,int subchoice,int subchoice2) {
			double r=0,rog=0,alpha=0,omega=0;
			RollingObjects ro = new RollingObjects(obj,r,rog,alpha,omega);	
			System.out.println("Please enter Radius in centimeters:- ");
			ro.setRadius(input.nextDouble()*0.01);
			input.nextLine();
			while(ro.getRadius()<0) {
				System.out.println("radius cannot be negative...please re-enter ");
				ro.setRadius(input.nextDouble()*0.01);
				input.nextLine();
			}
			omega=(obj.getV())/r;
			ro.setOmega(omega);
			if(choice==4&&subchoice==2&&subchoice2==1) {
				ro.setRadiusOfGyration(0.4);
			}
			else if(choice==4&&subchoice==2&&subchoice2==2) {
				ro.setRadiusOfGyration(0.67);
			}
			else if(choice==4&&subchoice==2&&subchoice2==3) {
				ro.setRadiusOfGyration(0.5);
			}
			else if(choice==4&&subchoice==2&&subchoice2==4) {
				System.out.println("Enter radius of gyration ");
				ro.setRadiusOfGyration(input.nextDouble());
				input.nextLine();
				while(ro.getRadiusOfGyration()<0) {
					System.out.println("radius of gyration can't be negative, Please re-enter ");
					ro.setRadiusOfGyration(input.nextDouble());
					input.nextLine();
				}
			}
			
			return ro;
		}
}

