package widget.canvasdemo2;



public class PointBean {
	public double x;
	public double y;

	public PointBean(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static double length(PointBean a, PointBean b) {
		return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
}
