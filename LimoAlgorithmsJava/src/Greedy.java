import javafx.scene.paint.Color;

public class Greedy extends Limo {

	public Greedy(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.GREEN;
	}

	public void update() {
		int closestDistCL = Integer.MAX_VALUE;
		int CLi = 0;
		int closestDistPL = Integer.MAX_VALUE;
		int PLi = 0;
		// Find the closet destination
		for (int i = 0; i < callerList.size(); i++) {
			int dist = (int) Math.sqrt(Math.abs(x-callerList.get(i).x) + Math.abs(y-callerList.get(i).y));
			if(dist < closestDistCL){
				closestDistCL = dist;
				CLi = i;
			}
		}
		for (int i = 0; i < passengerList.size(); i++) {
			int dist = (int) Math.sqrt(Math.abs(x-passengerList.get(i).destX) + Math.abs(y-passengerList.get(i).destY));
			if(dist < closestDistPL){
				closestDistPL = dist;
				PLi = i;
			}
		}
		
		System.out.println("distances are : " + closestDistCL + " " + closestDistPL);
		if (closestDistCL < closestDistPL && !callerList.isEmpty()) {
			targetX = callerList.get(CLi).x;
			targetY = callerList.get(CLi).y;
			if (targetX == callerList.get(CLi).x && targetY == callerList.get(CLi).y) {
				passengerList.add(callerList.get(CLi));
				callerList.remove(CLi);

			} else
				move();
		} else if (closestDistCL >= closestDistPL && !passengerList.isEmpty()) {
			targetX = passengerList.get(PLi).destX;
			targetY = passengerList.get(PLi).destY;
			if (targetX == passengerList.get(PLi).destX && targetY == passengerList.get(PLi).destY)
				passengerList.remove(CLi);
			else
				move();
		}

	}

	public void addCaller(Caller c) {
		c.setColor(color);
		callerList.add(c);
	}

}
