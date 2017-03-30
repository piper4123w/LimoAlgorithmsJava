import java.util.ArrayList;

import javafx.scene.paint.Color;

public class dynamicLimo extends Limo {
	Node fastestPath;
	Node nextDest;
	Node root;

	int nodeID = 1;

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
					callerList.remove(nextDest.caller);
					solvePath();
				} else {
					passengerList.remove(nextDest.caller);
					nextDest.parent = null;
				}

				nextDest = getNextMove();
			}
		}

	}

	public void addCaller(Caller c) {
		callerList.add(c);
		fastestPath = null;
		solvePath();
		nextDest = getNextMove();
	}

	private Node getNextMove() {
		Node n = fastestPath;
		if (n == null) {
			System.out.println("no moves");
		} else if (n.parent == null) {
			System.out.println("Only move");
			return n;
		}
		while (n.parent.parent != null) {
			System.out.println(n.toString() + "->");
			n = n.parent;
		}
		System.out.println();
		return n;
	}

	private void solvePath() {
		root = new Node(x, y, null, 0);
		root.isCaller = false;
		nodeID = 1;
		System.out.println("there are " + callerList.size() + " callers & " + passengerList.size() + " passengers");
		for (Caller c : callerList) {
			Node n = new Node(c.x, c.y, root, c.weight);
			n.caller = c;
			n.isCaller = true;
			n.id = nodeID++;
			root.childList.add(n);
		}
		for (Caller c : passengerList) {
			Node n = new Node(c.x, c.y, root, c.weight);
			n.id = nodeID++;
			root.childList.add(n);
		}
		System.out.println("There are " + (nodeID - 1) + " possible destinations");
		getPath(root);
		System.out.println("Got dat path boiiiii");
	}

	public void getPath(Node node) {
		// System.out.println("node :" + node.toString() + " id=" + node.id);
		if (!node.childList.isEmpty()) {
			// node.sortChildren();
			for (Node n : node.childList) {
				// System.out.println("Child in questions = " + n.id + " addr: "
				// + n.toString());
				for (Node c : node.childList) {
					if (c.id != n.id) {
						Node newChild = new Node(c);
						c.parent = n;
						n.childList.add(newChild);
						// System.out.println("added " + c.id + " as " +
						// newChild.toString());
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
			// System.out.println();
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
