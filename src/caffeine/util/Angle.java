package caffeine.util;

public class Angle {
	protected double theta;

	public Angle() {theta = 0;}

	public Angle(double angle){theta = theta(angle);}

	public double radians(){
		return Math.toRadians(theta);}

	public static Angle random(){return new Angle((int) (Math.random()*360));}

	public double sin(){return Math.sin(radians());}
	public double cos(){return Math.cos(radians());}
	public double tan(){return Math.tan(radians());}

	public double theta(){return theta;}

	public String toString(){return ""+theta+"deg";}

	public static double theta(double theta){return theta % 360;}

	public void add(double i){theta = (theta + i) % 360;}

	public void add(Angle a){add(a.theta());}

	public Angle bisect(Angle a){
		return new Angle((a.theta() + theta)/2);
	}

}
