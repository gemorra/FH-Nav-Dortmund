package FHNav.controller;
import java.util.*;

public class BreadthFirstSearchTest {

	public static class Node {
		List neighbors;
		Node pathParent;
		String name;

		public Node(String name) {
			this.name = name;
			neighbors = new LinkedList();
		}

		public String toString() {
			return name;
		}
	}

	public static void main(String[] args) {
		// Erdgeschoss

		// Eingangshalle
		Node Eingangshalle = new Node("Eingangshalle");

		// Hoersaele
		Node AE01 = new Node("A.E.01");
		Node AE02 = new Node("A.E.02");

		// Gruppenraum
		Node AE03 = new Node("A.E.03");

		// alle anderen Räume
		Node BE01 = new Node("B.E.01");
		Node BE02 = new Node("B.E.02");
		Node BE03 = new Node("B.E.03");
		Node BE04 = new Node("B.E.04");
		Node BE06 = new Node("B.E.06");
		Node BE07 = new Node("B.E.07");
		Node BE08 = new Node("B.E.08");
		Node BE09 = new Node("B.E.09");
		Node BE10 = new Node("B.E.10");
		Node BE20 = new Node("B.E.20");
		Node BE21 = new Node("B.E.21");
		Node BE23 = new Node("B.E.23");
		Node BE24 = new Node("B.E.24");

		BE01.neighbors.add(BE02);
		BE02.neighbors.add(BE03);
		BE02.neighbors.add(BE01);
		BE03.neighbors.add(BE04);
		BE03.neighbors.add(BE02);
		BE04.neighbors.add(BE06);
		BE04.neighbors.add(BE03);
		BE06.neighbors.add(BE07);
		BE06.neighbors.add(BE04);
		BE07.neighbors.add(BE08);
		BE07.neighbors.add(BE06);
		BE08.neighbors.add(BE09);
		BE08.neighbors.add(BE07);
		BE09.neighbors.add(BE10);
		BE09.neighbors.add(BE08);
		BE10.neighbors.add(BE09);

		BE20.neighbors.add(BE21);
		BE21.neighbors.add(BE23);
		BE21.neighbors.add(BE20);
		BE23.neighbors.add(BE24);
		BE23.neighbors.add(BE21);
		BE24.neighbors.add(BE23);

		Node CE31 = new Node("C.E.31");
		Node CE312 = new Node("C.E.31.2");
		Node CE32 = new Node("C.E.32");
		Node CE321 = new Node("C.E.32.1");
		Node CE45 = new Node("C.E.45");
		Node CE44 = new Node("C.E.44");
		Node CE43 = new Node("C.E.43");
		Node CE42 = new Node("C.E.42");
		Node CE41 = new Node("C.E.41");
		Node CE40 = new Node("C.E.40");

		CE31.neighbors.add(CE312);
		CE312.neighbors.add(CE32);
		CE312.neighbors.add(CE31);
		CE32.neighbors.add(CE321);
		CE32.neighbors.add(CE312);
		CE321.neighbors.add(CE32);

		CE40.neighbors.add(CE41);
		CE41.neighbors.add(CE42);
		CE41.neighbors.add(CE40);
		CE42.neighbors.add(CE43);
		CE42.neighbors.add(CE41);
		CE43.neighbors.add(CE44);
		CE43.neighbors.add(CE42);
		CE44.neighbors.add(CE45);
		CE44.neighbors.add(CE43);
		CE45.neighbors.add(CE44);

		Eingangshalle.neighbors.add(BE01);
		BE01.neighbors.add(Eingangshalle);
		Eingangshalle.neighbors.add(CE40);
		CE40.neighbors.add(Eingangshalle);

		Eingangshalle.neighbors.add(CE31);
		CE31.neighbors.add(Eingangshalle);

		BE20.neighbors.add(Eingangshalle);
		Eingangshalle.neighbors.add(BE20);

		// Treppenhaus
		Node node29 = new Node("Treppenhaus.E");

		BreadthFirstSearchTest bfs = new BreadthFirstSearchTest();
		String test = (String) bfs.search(BE10, BE24).toString();
		System.out.println("From BE10 to CE41: " + test);
		System.out.println();
		// String myString = test;
		
		char[] myCharArray = test.toCharArray();
		int y = 0;
		for (int i = 0; i < myCharArray.length; i++) {
			if (myCharArray[i] == '[' | myCharArray[i] == ','
					| myCharArray[i] == ']') {
				y++;

			}
		}

		char[] raum = new char[test.length() - y];
		int z = 0;

		for (int i = 1; i < myCharArray.length - 1; i++) {
			if (myCharArray[i] != ',') {
				raum[z] = myCharArray[i];
				// System.out.print(myCharArray[i]);
				z++;
			}
		}
		String raeume = new String(raum);
		String[] route = new String[13];
		StringTokenizer tokenizer;
		tokenizer = new StringTokenizer(raeume);
		int t = 0;
		while (tokenizer.hasMoreTokens()) {
			// System.out.println(tokenizer.nextToken());
			//System.out.println(route[t] = tokenizer.nextToken());
			route[t] = tokenizer.nextToken();
			t++;
		}
		System.out.println();
		for (int i = 0; i < route.length; i++) {
			System.out.println(route[i]);
		}

	}

	protected List constructPath(Node node) {
		LinkedList path = new LinkedList();
		while (node.pathParent != null) {
			path.addFirst(node);
			node = node.pathParent;
		}
		return path;
	}

	public List search(Node startNode, Node goalNode) {
		// list of visited nodes
		LinkedList closedList = new LinkedList();

		// list of nodes to visit (sorted)
		LinkedList openList = new LinkedList();
		openList.add(startNode);
		startNode.pathParent = null;

		while (!openList.isEmpty()) {
			Node node = (Node) openList.removeFirst();
			if (node == goalNode) {
				// path found!
				return constructPath(goalNode);
			} else {
				closedList.add(node);

				Iterator i = node.neighbors.iterator();
				while (i.hasNext()) {
					Node neighborNode = (Node) i.next();
					if (!closedList.contains(neighborNode)
							&& !openList.contains(neighborNode)) {
						neighborNode.pathParent = node;
						openList.add(neighborNode);
					}
				}
			}
		}
		// no path found
		return null;
	}

}
