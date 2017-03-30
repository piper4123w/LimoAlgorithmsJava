import javafx.scene.paint.Color;

public class FCFSLimo extends Limo {

	public FCFSLimo(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.YELLOW;
	}

	public void updateChild() {
		if (passengerList.isEmpty() && !callerList.isEmpty()) {
			// if no one is on board, pick up next caller
			if (x == callerList.get(0).x && y == callerList.get(0).y) {
				// System.out.println("picked up passenger");
				passengerList.add(callerList.get(0));
				callerList.remove(0);
			} else {
				// System.out.println("heading to passenger's dest");
				targetX = callerList.get(0).x;
				targetY = callerList.get(0).y;
				move();
			}

		}

		else if (!passengerList.isEmpty()) { // if there are passengers go to
												// their destinations
			if (x == passengerList.get(0).destX && y == passengerList.get(0).destY) {
				// System.out.println("Dropped off passenger");
				finishedCallers.add(passengerList.get(0));
				passengerList.remove(0);
			} else {
				// if target not reached, continue to target location
				targetX = passengerList.get(0).destX;
				targetY = passengerList.get(0).destY;
				move();
			}

		}

	}

	public void addCaller(Caller c) {
		c.setColor(color);
		callerList.add(c);
	}

}
