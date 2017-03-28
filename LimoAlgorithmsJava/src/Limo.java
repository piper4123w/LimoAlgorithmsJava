import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Limo extends World {
	public int targetX;
	public int targetY;

	public int x;
	public int y;

	public int width = 6;
	public int height = 6;

	public int distTraveled;

	public Color color;

	public ArrayList<Caller> callerList = new ArrayList<Caller>();
	public ArrayList<Caller> passengerList = new ArrayList<Caller>();

	public void move() {
		int xDir = (targetX - x);
		int yDir = (targetY - y);
		if (xDir > 0)
			x += 1;
		else if (xDir < 0)
			x -= 1;

		if (yDir > 0)
			y += 1;
		else if (yDir < 0)
			y -= 1;
		distTraveled++;
	}

}
