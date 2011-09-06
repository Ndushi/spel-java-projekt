package core;

/**
 *
 * @author Johannes Lindén
 */
public class Vector {
	public double x,y,z;
	public Vector(){
		this.z=0;
		this.y=0;
		this.x=0;
	}
	public Vector(double x,double y,double z){
		this.z=z;
		this.y=y;
		this.x=x;
	}
	public Vector(Vector v){
		this.copy(v);
	}
        public Vector copy(Vector v){
		this.x=v.x;
		this.y=v.y;
		this.z=v.z;
		return this;
	}
	public Vector copy(double x,double y,double z){
		this.x=x;
		this.y=y;
		this.z=z;
		return this;
	}
	public Vector add(Vector v){
		this.x+=v.x;
		this.y+=v.y;
		this.z+=v.z;
		return this;
	}
	public Vector add(double x,double y,double z){
		this.x+=x;
		this.y+=y;
		this.z+=z;
		return this;
	}
	public Vector add(double s){
		this.x-=s;
		this.y-=s;
		this.z-=s;
		return this;
	}
	public Vector sub(Vector v){
		this.x-=v.x;
		this.y-=v.y;
		this.z-=v.z;
		return this;
	}
	public Vector sub(double x,double y,double z){
		this.x-=x;
		this.y-=y;
		this.z-=z;
		return this;
	}
	public Vector sub(double s){
		this.x-=s;
		this.y-=s;
		this.z-=s;
		return this;
	}
	public Vector multiply(Vector v){
		this.x*=v.x;
		this.y*=v.y;
		this.z*=v.z;
		return this;
	}
	public Vector multiply(double x,double y,double z){
		this.x*=x;
		this.y*=y;
		this.z*=z;
		return this;
	}
	public Vector multiply(double s){
		this.x*=s;
		this.y*=s;
		this.z*=s;
		return this;
	}
	public Vector divide(Vector v){
		this.x/=v.x;
		this.y/=v.y;
		this.z/=v.z;
		return this;
	}
	public Vector divide(double x,double y,double z){
		this.x/=x;
		this.y/=y;
		this.z/=z;
		return this;
	}
	public Vector divide(double s){
		this.x/=s;
		this.y/=s;
		this.z/=s;
		return this;
	}
	public double length(){
		return Math.sqrt(this.x*this.x+this.y*this.y+this.z*this.z);
	}
	public double dot(Vector v){
		return this.x*v.x+this.y*v.y+this.z*v.z;
	}
	public double dot(double x,double y,double z){
		return this.x*x+y*this.y+z*this.z;
	}
	public Vector cross(Vector v){
		return new Vector(
				this.y * v.z - v.y * this.z,
				this.z * v.x - v.z * this.x,
				this.x * v.y - v.x * this.y
		);
	}
	public void normalize(){
		double m=this.length();
		if(m>0)this.divide(m);
	}
        public String toString(){
            return "Vector:"+this.x+", "+this.y+", "+this.z;
        }
        static Vector sub(Vector v1,Vector v2){
            return new Vector(v1.x-v2.x,v1.y-v2.y,v1.z-v2.z);
        }
}
