import javafx.scene.paint.Color;

public class ClumpLimo extends Limo {
	public double WEIGHT;
	
	boolean choosingDest = true;

	protected void addWeight() {
		for (Caller p : passengerList) {
			p.weight += WEIGHT;
		}
		for (Caller c : callerList) {
			c.weight += WEIGHT;
		}
	}

	public ClumpLimo(int x, int y, double d, Color clr) {
		this.x = x;
		this.y = y;
		color = clr;
		WEIGHT = d;

	}

	public void updateChild() {
		double closestDistCL = Integer.MAX_VALUE;
		int CLi = 0;
		double closestDistPL = Integer.MAX_VALUE;
		int PLi = 0;
		
		double prioCL = Integer.MIN_VALUE;
		double prioPL = Integer.MIN_VALUE;

		// Add weight to all callers
		addWeight();

		// Find the closet destination
		
		// priority should be increased for the following reasons:
		// (priority meaning subtracting from distance)
		// + if destination has a close distance to current location
		// + if caller / passenger has been waiting a long time
		// - if distance from all current passenger destinations is
		// - distance from 
		if(choosingDest == true){
			if (!passengerList.isEmpty()){
				choosingDest = false;
			}
			for (Caller c : callerList) {
				//double dist = Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2)) - c.weight;
				
				double cPrio = 0;
				double distToCaller = Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2));
				double waitTimeCaller = c.weight;
				
				double clumpWeight = 0;
				if (passengerList.size() < capacity)
				{
					for (Caller otherC : callerList){
						// if callers are nearby, they should have a higher proportional weight
						double distCallerToCaller = Math.sqrt(Math.pow(c.x - otherC.x, 2) + Math.pow(c.y - otherC.y, 2));
						if (distCallerToCaller != 0)
							clumpWeight += 100 / distCallerToCaller;
					}
				}
				for (Caller otherP : passengerList){
					// if passenger destinations are nearby, they should have a higher proportional weight
					double distCallerToDest = Math.sqrt(Math.pow(c.x - otherP.destX, 2) + Math.pow(c.y - otherP.destY, 2));
					if (distCallerToDest != 0)
						clumpWeight += 100 / distCallerToDest;
				}
				
				cPrio = waitTimeCaller + clumpWeight - 5*distToCaller;
				
				if (cPrio > prioCL)
				{
					prioCL = cPrio;
					CLi = callerList.indexOf(c);
				}
				
				/*if (dist < closestDistCL) {
					closestDistCL = dist;
					CLi = callerList.indexOf(c);
				}*/
			}

			for (Caller p : passengerList) {
				//double dist = Math.sqrt(Math.pow(x - p.destX, 2) + Math.pow(y - p.destY, 2)) - p.weight;
				
				double pPrio = 0;
				double distToDest = Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
				double waitTimeCaller = p.weight;
				
				double clumpWeight = 0;
				if(passengerList.size() < capacity){
					for (Caller otherC : callerList){
						// if callers are nearby, they should have a higher proportional weight
						double distCallerToCaller = Math.sqrt(Math.pow(p.destX - otherC.x, 2) + Math.pow(p.destY - otherC.y, 2));
						if (distCallerToCaller != 0)
							clumpWeight += 100 / distCallerToCaller;
					}
				}
				for (Caller otherP : passengerList){
					// if passenger destinations are nearby, they should have a higher proportional weight
					double distCallerToDest = Math.sqrt(Math.pow(p.destX - otherP.destX, 2) + Math.pow(p.destY - otherP.destY, 2));
					if (distCallerToDest != 0)
						clumpWeight += 100 / distCallerToDest;
				}
				
				pPrio = waitTimeCaller + clumpWeight - distToDest;
				
				if (pPrio > prioCL)
				{
					prioPL = pPrio;
					PLi = passengerList.indexOf(p);
				}
				
				/*if (dist < closestDistPL) {
					closestDistPL = dist;
					PLi = passengerList.indexOf(p);
				}*/
			}
		}
		
		//if (closestDistCL < closestDistPL && !callerList.isEmpty() && passengerList.size() < capacity) {
		if (prioCL > prioPL && !callerList.isEmpty() && passengerList.size() < capacity) {
			targetX = callerList.get(CLi).x;
			targetY = callerList.get(CLi).y;
			if (x == callerList.get(CLi).x && y == callerList.get(CLi).y) {
				choosingDest = true;
				passengerList.add(callerList.get(CLi));
				callerList.remove(CLi);

			} else
				move();
		} else if (!passengerList.isEmpty()) {
			targetX = passengerList.get(PLi).destX;
			targetY = passengerList.get(PLi).destY;
			if (x == passengerList.get(PLi).destX && y == passengerList.get(PLi).destY) {
				choosingDest = true;
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
