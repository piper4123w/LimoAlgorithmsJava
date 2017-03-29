import javafx.scene.paint.Color;

public class ClosestFirstLimo extends Limo {

	public ClosestFirstLimo(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.RED;
	}

	public void update() {
		double closestDistCL = Integer.MAX_VALUE;
		int CLi = 0;
		double closestDistPL = Integer.MAX_VALUE;
		int PLi = 0;

		// Find the closet destination
		for (Caller c : callerList) {
			double dist = Math.sqrt(Math.abs(x - c.x) + Math.abs(y - c.y));
			if (dist < closestDistCL) {
				closestDistCL = dist;
				CLi = callerList.indexOf(c);
			}
		}

		for (Caller p : passengerList) {
			double dist = Math.sqrt(Math.abs(x - p.destX) + Math.abs(y - p.destY));
			if (dist < closestDistPL) {
				closestDistPL = dist;
				PLi = passengerList.indexOf(p);
			}
		}

		if (closestDistCL < closestDistPL && !callerList.isEmpty()) {
			targetX = callerList.get(CLi).x;
			targetY = callerList.get(CLi).y;
			if (x == callerList.get(CLi).x && y == callerList.get(CLi).y) {
				passengerList.add(callerList.get(CLi));
				callerList.remove(CLi);

			} else
				move();
		} else if (closestDistCL >= closestDistPL && !passengerList.isEmpty()) {
			targetX = passengerList.get(PLi).destX;
			targetY = passengerList.get(PLi).destY;
			if (x == passengerList.get(PLi).destX && y == passengerList.get(PLi).destY) {
				finishedCallers.add(passengerList.get(PLi));
				passengerList.remove(PLi);
			} else
				move();
		}

	}

	public void addCaller(Caller c) {
		c.setColor(color);
		callerList.add(c);
	}

}
