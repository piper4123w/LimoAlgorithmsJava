import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Limo extends World {
	public int capacity = 10;
	public double speed = 10.0;

	public int targetX;
	public int targetY;

	public double x;
	public double y;

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
		double dx = targetX - x;
		double dy = targetY - y;
		double dist = Math.sqrt(dx * dx + dy * dy);
		if (dist < speed) {
			x = targetX;
			y = targetY;
		} else {
			x += dx * speed / dist;
			y += dy * speed / dist;
		}
		distTraveled += dist;
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
		String s = "";
		if (this instanceof GreedyCF)
			s += " weight:" + ((GreedyCF) this).WEIGHT;
		System.out.println(this.getClass().toString() + s + " DONE:");
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
