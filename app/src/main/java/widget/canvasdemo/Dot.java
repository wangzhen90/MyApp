package widget.canvasdemo;

/**
 * Created by dell on 2015/12/28.
 */
public class Dot {

    public int x;
    public int y;
    public int vol = 30;

    public int targetX;
    public int targetY;

    public Dot(int x, int y, int targetX,int targetY){
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
    }
    public Dot update(){

        if(y < targetY){
            y += vol;
        }else if(y > targetY){
            y -= vol;
        }

        if(Math.abs(targetY - y) < vol){
            y = targetY;
        }
        return this;
    }
}
