
public class GreedyLimo extends Limo {

	public double WEIGHT;

	protected void addWeight() {
		for (Caller p : passengerList) {
			p.weight += WEIGHT;
		}
		for (Caller c : callerList) {
			c.weight += WEIGHT;
		}
	}
}
