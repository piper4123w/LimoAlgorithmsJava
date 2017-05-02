import java.io.FileWriter;
import java.io.IOException;

public class FileRunner {

	public static void main(String[] args) throws IOException {
		LimoFileOutput fi = new LimoFileOutput();
		int[] callerSweep = { 10, 100, 1000 };
		int[] capacityList = { 1, 5, 10, 100 };
		int chanceSweepAmnt = 5;
		FileWriter writer = new FileWriter("data.csv");
		String sb = "";
		for (int cap : capacityList) {
			for (int callerAmnt : callerSweep) {
				sb += ("Callers," + callerAmnt + ",Capacity," + cap + "\n");
				sb += (",FCFS " + cap + ",,,CF" + cap + ",,,Greed" + cap + "\n");
				sb += ("chance,max,total,average,max,total,average,max,total,average\n");
				for (int chanceP = 5; chanceP < 100; chanceP += chanceSweepAmnt) {
					sb += (chanceP + ",");
					fi.initialize(chanceP, callerAmnt, cap);
					boolean allDone = false;
					while (!allDone) {
						// System.out.println("Running");
						allDone = true;
						for (Limo l : fi.limos) {
							// System.out.println(l.getClass().toGenericString()
							// +
							// "@" + l.x + "," + l.y);
							if (!l.Done) {
								allDone = false;
							}
						}
						System.out.println();
						fi.update();
					}
					for (Limo l : fi.limos) {
						sb += (l.maxWaitTime + "," + l.totalWaitTime + "," + l.averageWaitTime + ",");
						System.out.print(l.maxWaitTime + "," + l.totalWaitTime + ",");
					}
					sb += "\n";
					System.out.println();
				}
				sb += "\n\n";
			}
		}
		writer.append(sb);
		writer.close();
		System.out.println("Done");
	}
}
