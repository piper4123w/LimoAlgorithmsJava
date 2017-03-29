import javafx.scene.paint.Color;

public class GreedyLimo extends Limo {

	public static final double WEIGHT = .1;

	public GreedyLimo(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.GREEN;
	}

	public void update() {
		double closestDistCL = Integer.MAX_VALUE;
		int CLi = 0;
		double closestDistPL = Integer.MAX_VALUE;
		int PLi = 0;

		// Add weight to all callers
		addWeight();

		// Find the closet destination
		for (Caller c : callerList) {
			double dist = Math.sqrt(Math.pow(x - c.x,2) + Math.pow(y - c.y,2)) - c.weight;
			if (dist < closestDistCL) {
				closestDistCL = dist;
				CLi = callerList.indexOf(c);
			}
		}

		for (Caller p : passengerList) {
			double dist = Math.sqrt(Math.pow(x - p.destX,2) + Math.pow(y - p.destY,2)) - p.weight;
			if (dist < closestDistPL) {
				closestDistPL = dist;
				PLi = passengerList.indexOf(p);
			}
		}

		if (closestDistCL < closestDistPL && !callerList.isEmpty() && passengerList.size() < capacity) {
			targetX = callerList.get(CLi).x;
			targetY = callerList.get(CLi).y;
			if (x == callerList.get(CLi).x && y == callerList.get(CLi).y) {
				passengerList.add(callerList.get(CLi));
				callerList.remove(CLi);

			} else
				move();
		} else if (!passengerList.isEmpty()) {
			targetX = passengerList.get(PLi).destX;
			targetY = passengerList.get(PLi).destY;
			if (x == passengerList.get(PLi).destX && y == passengerList.get(PLi).destY) {
				finishedCallers.add(passengerList.get(PLi));
				passengerList.remove(PLi);
			} else
				move();
		}

	}

	private void addWeight() {
		for (Caller p : passengerList) {
			p.weight += WEIGHT;
		}
		for (Caller c : callerList) {
			c.weight += WEIGHT;
		}
	}

	public void addCaller(Caller c) {
		c.setColor(color);
		callerList.add(c);
	}

}
