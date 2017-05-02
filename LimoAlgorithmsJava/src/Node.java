import java.util.ArrayList;

public class Node {
	Node parent;
	ArrayList<Node> childList;
	public Caller caller;
	double x;
	double y;
	double weight;
	public boolean isCaller = false;
	public int id;
	public boolean isRoot = false;

	public Node(double x2, double y2, Node p, double w) {
		this.x = x2;
		this.y = y2;
		parent = p;
		weight = w;
		childList = new ArrayList<Node>();
	}

	public Node(Node c) {
		x = c.x;
		y = c.y;
		id = c.id;
		weight = c.weight;
		isCaller = c.isCaller;
		parent = c.parent;
		childList = new ArrayList<Node>();
	}

	public void sortChildren() {
		ArrayList<Node> sorted = new ArrayList<Node>();
		for (int i = 0; i < childList.size(); i++) {
			double min = Integer.MAX_VALUE;
			int minInd = 0;
			for (int j = i; j < childList.size(); j++) {
				if (childList.get(j).weight < min) {
					min = childList.get(j).weight;
					minInd = j;
				}
			}
			sorted.add(childList.get(minInd));
		}

		childList = sorted;
	}
}
