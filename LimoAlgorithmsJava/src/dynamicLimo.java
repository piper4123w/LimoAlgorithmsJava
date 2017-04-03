import java.util.ArrayList;
import javafx.scene.paint.Color;

public class dynamicLimo extends Limo {
	Node fastestPath;
	Node nextDest;
	Node root;

	public dynamicLimo(double x, double y, Color clr) {
		this.x = x;
		this.y = y;
		color = clr;
	}

	public void updateChild() {
		if (nextDest != null && !nextDest.isRoot) {
			targetX = nextDest.x;
			targetY = nextDest.y;
			move();
			if (x == targetX && y == targetY) {
				if (nextDest.isCaller) {
					callerList.remove(nextDest.caller);
					nextDest.isCaller = false;
					passengerList.add(nextDest.caller);
				} else {
					passengerList.remove(nextDest.caller);
					finishedCallers.add(nextDest.caller);
				}
				// debugPrintRideList();
				solvePath();
				nextDest = getNextMove();
			}
		}

	}

	public void addCaller(Caller c) {
		// System.out.println("New Caller");
		callerList.add(c);
		// debugPrintRideList();
		solvePath();
		nextDest = getNextMove();
	}

	private Node getNextMove() {
		Node n = fastestPath;
		if (fastestPath != null) {
			while (n.parent.isRoot != true) {
				n = n.parent;
			}
			// System.out.println("NextDest:" + n.caller.toString());
			return n;
		} else {
			return null;
		}

	}

	private void solvePath() {
		fastestPath = null;
		root = new Node(x, y, null, 0);
		root.isCaller = false;
		root.isRoot = true;
		for (Caller c : callerList) {
			Node n = new Node(c.x, c.y, root, c.waitTime);
			n.caller = c;
			// System.out.println("Made node " + n.toString() + " for " +
			// c.toString());
			n.isCaller = true;
			root.childList.add(n);
		}
		for (Caller c : passengerList) {
			Node n = new Node(c.destX, c.destY, root, c.waitTime);
			n.caller = c;
			n.isCaller = false;
			// System.out.println("Made node " + n.toString() + " for " +
			// c.toString());
			root.childList.add(n);
		}
		getPath(root);
		// System.out.println("Got dat path boiiiii!! fastestPath:" +
		// fastestPath.toString());
	}

	public void getPath(Node node) {
		if (!node.childList.isEmpty()) {
			// node.sortChildren();
			for (Node n : node.childList) {
				for (Node c : node.childList) {
					if (!c.equals(n)) {
						double timeOnTravel = Math.sqrt(Math.pow(node.x - n.x, 2) + Math.pow(node.y - n.y, 2)) / speed;
						double weight = n.weight + timeOnTravel;
						c.weight += weight;
						n.childList.add(new Node(c));
					}
				}

				getPath(n);
			}
		} else { // no more children
			if (fastestPath == null) {
				if (!node.isRoot) {
					fastestPath = node; // node is not root, but there is no
										// previous fastest path
				} else {
					fastestPath = null;
				}
			} else {
				if (fastestPath.weight > node.weight) {
					fastestPath = node; // node has less total weight
				}
			}
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
