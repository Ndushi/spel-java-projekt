package core;

/**
 *
 * @author johannes
 */
public class Vertex {
    public Vector position=new Vector();
    public Vector Worldposition=new Vector();
    public Vector normal=new Vector();
    public Vertex(){}
    public Vertex(Vector v){
        this.position.copy(v);
    }
    public Vertex(Vector pos,Vector norm){
        this.position.copy(pos);
        this.normal.copy(norm);
    }
    public Vertex(double x,double y,double z){
        this.position.copy(x,y,z);
    }
    public Vertex(double x,double y,double z,double nx,double ny,double nz){
        this.position.copy(x,y,z);
        this.normal.copy(nx,ny,nz);
    }
    
}
