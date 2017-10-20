package vo;

public class Point {
	private String id;
	private double []attrs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double[] getAttrs() {
		return attrs;
	}
	public void setAttrs(double[] attrs) {
		this.attrs = attrs;
	}
	public Point(String id, double[] attrs) {
		super();
		this.id = id;
		this.attrs = attrs;
	}
	public Point() {
		super();
	}	
	
}
