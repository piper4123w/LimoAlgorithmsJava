import java.util.ArrayList;

import javafx.scene.paint.Color;

public class LimoFileOutput {
	public final static int WIDTH = 7500;
	public final static int HEIGHT = 5000;

	public boolean allAtOnce = false;

	int callers;
	double chance;

	ArrayList<Limo> limos;

	void initialize(double ch, int calls, int cap) {
		chance = ch;
		callers = calls;
		/*
		 * Scanner sc = new Scanner(System.in);
		 * System.out.println("enter number of callers"); callers =
		 * sc.nextInt();
		 * System.out.println("Enter % chance of call per update : DOUBLE");
		 * chance = sc.nextDouble();
		 */
		limos = new ArrayList<Limo>();
		limos.removeAll(limos);
		Limo l = new FCFSLimo(WIDTH / 2, HEIGHT / 2, Color.YELLOW);
		l.capacity = cap;
		limos.add(l);
		
		l = new ClosestFirstLimo(WIDTH / 2, HEIGHT / 2, Color.RED);
		l.capacity = cap;
		limos.add(l);
		
		l = new GreedyCF(WIDTH / 2, HEIGHT / 2, 2, Color.GREEN);
		l.capacity = cap;
		limos.add(l);
		// l = new dynamicLimo(WIDTH / 2, HEIGHT / 2);
		// limos.add(l);
		// l = new GreedyCF(WIDTH / 2, HEIGHT / 2, 100, Color.HOTPINK);
		// limos.add(l);
		if (allAtOnce)
			addAllCallers();

	}

	private void addAllCallers() {
		while (callers > 0) {
			Caller c = new Caller();
			for (Limo l : limos) {
				if (l instanceof FCFSLimo) // need this for all algorithms
					((FCFSLimo) l).addCaller(c);
				if (l instanceof ClosestFirstLimo)
					((ClosestFirstLimo) l).addCaller(c);
				if (l instanceof GreedyCF)
					((GreedyCF) l).addCaller(c);
				if (l instanceof dynamicLimo)
					((dynamicLimo) l).addCaller(c);
			}
			callers--;
		}
	}

	void update() {
		for (Limo l : limos) {
			l.update();
			l.updateData();
			// l.draw();
			if (callers <= 0) {
				l.noMoreCallers = true;
			}
		}

		if (!allAtOnce && Math.random() * 100 < chance && callers > 0) {
			Caller c = new Caller();
			for (Limo l : limos) {
				if (l instanceof FCFSLimo) // need this for all algorithms
					((FCFSLimo) l).addCaller(c);
				if (l instanceof ClosestFirstLimo)
					((ClosestFirstLimo) l).addCaller(c);
				if (l instanceof GreedyCF)
					((GreedyCF) l).addCaller(c);
				if (l instanceof dynamicLimo)
					((dynamicLimo) l).addCaller(c);
			}
			callers--;
		}

	}

}