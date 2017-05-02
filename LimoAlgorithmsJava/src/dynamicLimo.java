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
				System.out.println("Reached target isCaller=" + nextDest.isCaller);
				if (nextDest.isCaller) {
					nextDest.isCaller = false;
					callerList.remove(nextDest.caller);
					nextDest.isCaller = false;
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
		// System.out.println("New Caller");
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

	}

	private void solvePath() {
		fastestPath = null;
		root = new Node(x, y, null, 0);
		root.isCaller = false;
		nodeID = 1;
		System.out.println("there are " + callerList.size() + " callers & " + passengerList.size() + " passengers");
		for (Caller c : callerList) {
			Node n = new Node(c.x, c.y, root, c.waitTime);
			n.caller = c;
			// System.out.println("Made node " + n.toString() + " for " +
			// c.toString());
			n.isCaller = true;
			n.id = nodeID++;
			root.childList.add(n);
		}
		for (Caller c : passengerList) {
			Node n = new Node(c.destX, c.destY, root, c.waitTime);
			n.caller = c;
			n.isCaller = false;
			// System.out.println("Made node " + n.toString() + " for " +
			// c.toString());
			n.caller = c;
			n.id = nodeID++;
			root.childList.add(n);
		}
		System.out.println("There are " + (nodeID - 1) + " possible destinations");
		getPath(root);
		// System.out.println("Got dat path boiiiii!! fastestPath:" +
		// fastestPath.toString());
	}

	public void getPath(Node node) {
		System.out.println("node :" + node.toString() + " id=" + node.id);
		if (!node.childList.isEmpty()) {
			node.sortChildren();
			for (Node n : node.childList) {
				System.out.println("Child in questions = " + n.id + " addr: " + n.toString());
				for (Node c : node.childList) {
					if (c.id != n.id) {
						Node newChild = new Node(c);
						c.parent = n;
						n.childList.add(newChild);
						System.out.println("added " + c.id + " as " + newChild.toString());
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
