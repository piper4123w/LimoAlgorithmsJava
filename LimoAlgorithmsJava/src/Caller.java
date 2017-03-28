//Class handles the callers and their destinations
//Pauls here to play babeeeyyy
import javafx.scene.paint.Color;

public class Caller extends World {
	public int r = 3;
	
	public int x;
	public int y;
	
	public int destX, destY;
	
	public Color color;
	
	public Caller(){
		x = (int) (Math.random() * WIDTH);
		y = (int) (Math.random() * HEIGHT);
		
		destX = (int) (Math.random() * WIDTH);
		destY = (int) (Math.random() * HEIGHT);
		
	}

	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.color = color;
	}
	
}
