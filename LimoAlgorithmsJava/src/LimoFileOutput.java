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

	static ArrayList<Limo> limos;

	static void initialize(double ch, int calls) {
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
		int[] callerSweep = { 10 };
		int chanceSweepAmnt = 10;
		FileWriter writer = new FileWriter("data.csv");
		String sb = "";
		for (int callerAmnt : callerSweep) {
			sb += ("Callers," + callerAmnt + "\n");
			sb += (",FCFS,,CF,,Greed\n");
			sb += ("chance,max,total,max,total,max,total\n");
			for (int chanceP = 0; chanceP < 100; chanceP += chanceSweepAmnt) {
				sb += (chanceP + ",");
				initialize(chanceP, callerAmnt);
				boolean allDone = false;
				while (!allDone) {
					// System.out.println("Running");
					allDone = true;
					for (Limo l : limos) {
						System.out.println(l.x + "," + l.y);
						if (!l.Done) {
							allDone = false;
							break;
						}
					}
					update();
				}
				for (Limo l : limos) {
					sb += (l.maxWaitTime + "," + l.totalWaitTime + ",");
					System.out.print(l.maxWaitTime + "," + l.totalWaitTime + ",");
				}
				sb += "\n";
				System.out.println();
			}
			sb += "\n\n";
		}
		writer.append(sb);
		writer.close();
		System.out.println("Done");
	}
}