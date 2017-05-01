import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LimoFileOutput {
	public final static int WIDTH = 750;
	public final static int HEIGHT = 500;

	public static boolean allAtOnce = false;

	public GraphicsContext gc;

	static int callers;
	static double chance;

	static ArrayList<Limo> limos = new ArrayList<Limo>();

	static void initialize(double ch) {
		chance = ch;
		/*Scanner sc = new Scanner(System.in);
		System.out.println("enter number of callers");
		callers = sc.nextInt();
		System.out.println("Enter % chance of call per update : DOUBLE");
		chance = sc.nextDouble();*/
		Limo l = new FCFSLimo(WIDTH / 2, HEIGHT / 2);
		limos.add(l);
		l = new ClosestFirstLimo(WIDTH / 2, HEIGHT / 2);
		limos.add(l);
		l = new GreedyCF(WIDTH / 2, HEIGHT / 2, 2, Color.GREEN);
		limos.add(l);
		// l = new dynamicLimo(WIDTH / 2, HEIGHT / 2);
		// limos.add(l);
		// l = new GreedyCF(WIDTH / 2, HEIGHT / 2, 100, Color.HOTPINK);
		// limos.add(l);
		if (allAtOnce)
			addAllCallers();

	}

	private static void addAllCallers() {
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

	static void update() {
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

	public static void main(String[] args) throws IOException {
		int[] callerSweep = { 10, 100, 1000, 10000 };
		int chanceSweepAmnt = 10;
		FileWriter writer = new FileWriter("data.csv");
		StringBuilder sb = new StringBuilder();
		for (int callerAmnt : callerSweep) {
			sb.append("Callers," + callerAmnt + "/n");
			sb.append(",FCFS,CF,Greed/n");
			sb.append("chance,max,total,max,total,max,total\n");
			for (int chanceP = 0; chanceP < 100; chanceP += chanceSweepAmnt) {
				sb.append(chanceP + ",");
				initialize(chanceP);
				boolean allDone = false;
				while (!allDone) {
					// System.out.println("Running");
					allDone = true;
					for (Limo l : limos) {
						if (!l.Done) {
							allDone = false;
							break;
						}
					}
					update();

				}
				
				for(Limo l : limos){
					sb.append(l.maxWaitTime + "," + l.totalWaitTime +"\n");
				}
			}
		}
		writer.close();
		System.out.println("Done");
	}
}