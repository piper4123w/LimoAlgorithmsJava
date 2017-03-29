import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Limo extends World {
	public int capacity = 15;

	public int targetX;
	public int targetY;

	public int x;
	public int y;

	public int width = 6;
	public int height = 6;

	public double distTraveled;

	public Color color;
	public boolean noMoreCallers = false;
	public boolean Done = false;
	boolean dataPrinted = false;

	public ArrayList<Caller> callerList = new ArrayList<Caller>();
	public ArrayList<Caller> passengerList = new ArrayList<Caller>(capacity);
	public ArrayList<Caller> finishedCallers = new ArrayList<Caller>();

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

	public void updateData() {
		if (noMoreCallers && callerList.isEmpty() && passengerList.isEmpty()) {
			Done = true;
			if (!dataPrinted)
				printData();
			dataPrinted = true;
		} else {
			for (Caller c : callerList) {
				c.waitTime++;
			}
			for (Caller p : passengerList) {
				p.waitTime++;
			}
		}
	}

	public void printData() {
		double totalWaitTime = 0;
		double maxWaitTime = 0;
		double minWaitTime = Double.MAX_VALUE;
		System.out.println(this.getClass().toString() + " DONE:");
		System.out.println("Distance Traveled = " + distTraveled);
		for (Caller c : finishedCallers) {
			totalWaitTime += c.waitTime;
			if (c.waitTime > maxWaitTime)
				maxWaitTime = c.waitTime;
			if (c.waitTime < minWaitTime)
				minWaitTime = c.waitTime;
		}
		System.out.println("total time for riders = " + totalWaitTime);
		System.out.println("Max wait for riders = " + maxWaitTime);
		System.out.println("Min wait for riders = " + minWaitTime);
		System.out.println("");
	}

}
