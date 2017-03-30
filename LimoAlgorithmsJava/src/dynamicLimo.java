import java.util.ArrayList;

import javafx.scene.paint.Color;

public class dynamicLimo extends Limo {
	Node fastestPath;
	Node nextDest;
	Node root;

	public dynamicLimo(double x, double y) {
		this.x = x;
		this.y = y;
		color = Color.DARKGRAY;
	}

	public void updateChild() {
		if (nextDest != null) {
			targetX = nextDest.x;
			targetY = nextDest.y;
			move();
			if (x == targetX && y == targetY) {
				if (nextDest.isCaller) {
					passengerList.add(nextDest.caller);
				} else {
					passengerList.remove(nextDest.caller);
				}
				nextDest = getNextMove();
			}
		}

	}

	public void addCaller(Caller c) {
		callerList.add(c);
		solvePath();
		nextDest = getNextMove();
	}

	private Node getNextMove() {
		Node n = fastestPath;
		while (n.parent.parent != null) {
			n = n.parent;
		}
		return n;
	}

	private void solvePath() {
		root = new Node(x, y, null, 0);
		root.isCaller = false;
		for (Caller c : callerList) {
			Node n = new Node(c.x, c.y, root, c.weight);
			n.caller = c;
			n.isCaller = true;
			root.childList.add(n);
		}
		for (Caller c : passengerList) {
			Node n = new Node(c.x, c.y, root, c.weight);
			root.childList.add(n);
		}
		getPath(root);
		System.out.println("Got dat path boiiiii");
	}

	public void getPath(Node node) {
		if (!node.childList.isEmpty()) {
			// node.sortChildren();
			for (Node n : node.childList) {
				for (Node c : node.childList) {
					if (!c.equals(n)) {
						n.childList.add(c);
					}
				}

				double timeOnTravel = Math.sqrt(Math.pow(node.x - n.x, 2) + Math.pow(node.y - n.y, 2)) / speed;
				double weight = n.weight + timeOnTravel;

				if (!n.childList.isEmpty()) {
					for (Node c : n.childList) {
						c.weight += weight;
					}
				}
				getPath(n);
			}
		} else {
			if (fastestPath == null || fastestPath.weight > node.weight)
				fastestPath = node;
		}
	}

	private Node getHightestPriorityNode(ArrayList<Node> possibleDests) {
		Node next = null;
		double maxDist = Double.MAX_VALUE;
		for (Node c : possibleDests) {
			double dist = Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2)) - c.weight;
			if (dist < maxDist) {
				next = c;
			}
		}
		return next;
	}

}
