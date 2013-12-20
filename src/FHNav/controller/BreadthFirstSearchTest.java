package FHNav.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;

@SuppressWarnings("all")
public class BreadthFirstSearchTest {

	/**
	 * Implementiert den BreadthFirstSearch Algorithmus. Auch der komplette
	 * Graph wird hier zusammen gesetzt.
	 * 
	 * @author Moritz Wiechers
	 * 
	 */
	public class Node {

		List neighbors;
		Node pathParent;
		String name;
		float x;
		float y;

		public Node(String name) {
			this.name = name;
			neighbors = new LinkedList();
		}

		public Node(String name, float x, float y) {
			this.name = name;
			neighbors = new LinkedList();
			this.x = x;
			this.y = y;
		}

		public Node(String name, float x, float y, List l) {
			this.name = name;
			neighbors = new LinkedList();
			this.x = x;
			this.y = y;
			l.add(this);
		}

		public void addNeighbor(Node n) {
			neighbors.add(n);
			n.neighbors.add(this);
		}

		public String getName() {
			return name;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setX(float x) {
			this.x = x;
		}

		public void setY(float y) {
			this.y = y;
		}

		public String toString() {
			return name;
		}

	}

	List nodes;
	// Node Eingang;
	// Node Eingangshalle;
	// Node CE31;
	// Node CE31f;

	List path;

	Node from;
	Node to;

	private Bitmap bmp;

	protected List constructPath(Node node) {
		LinkedList path = new LinkedList();
		while (node.pathParent != null) {
			path.addFirst(node);
			node = node.pathParent;
		}
		path.addFirst(node);
		return path;
	}

	public Bitmap getBMP() {
		// TODO Auto-generated method stub
		return bmp;
	}

	public Node getFrom() {
		return from;
	}

	public Node getNodeFromListByString(String s) {
		Node n = null;

		for (int i = 0; i < nodes.size(); i++) {
			Node ntmp = (Node) nodes.get(i);
			if (ntmp.getName().equals(s)) {
				n = ntmp;
			}
		}

		return n;
	}

	public List getNodes() {
		return nodes;
	}

	public List getPath() {
		return path;
	}

	public Node getTo() {
		return to;
	}

	public void initGraph() {

		// float x_c_li=350;
		// float x_c_flur=435;
		// float x_c_re=520;
		//
		// float y_b_li=1225;
		// float y_b_flur=1260;
		// float y_b_re=1295;

		float x_c_li = 350;
		float x_c_flur = 412;
		float x_c_re = 500;

		float y_b_li = 1200;
		float y_b_flur = 1241;
		float y_b_re = 1320;

		nodes = new LinkedList<Node>();

		// Eingangshalle
		Node Eingang = new Node("Eingang", 140, 955, nodes);
		Node Eingangshalle = new Node("Eingangshalle", x_c_flur, 955);
		Node FlurSued = new Node("FlurSued", x_c_flur, y_b_flur);
		Node Nebeneingang = new Node("Nebeneingang", 555, 950, nodes);
		Node Nebeneingangf = new Node("NebeneingangFlur", 555, 896);

		// Hörsäle
		Node AE01 = new Node("A.E.01", 218, 1136, nodes);
		Node AE01T1 = new Node("A.E.01T1", 218, 993);
		Node AE01T1f = new Node("A.E.01T1Flur", 218, 955);
		Node AE01T2 = new Node("A.E.01T2", 341, 1136);
		Node AE01T2f = new Node("A.E.01T2Flur", x_c_flur, 1136);

		Node AE02 = new Node("A.E.02", 294, 825, nodes);
		Node AE02T1 = new Node("A.E.02T1", 294, 910);
		Node AE02T1f = new Node("A.E.02T1Flur", 322, 955);
		Node AE02T2 = new Node("A.E.02T2", 377, 825);
		Node AE02T2f = new Node("A.E.02T2Flur", x_c_flur, 854);

		Node AE03 = new Node("A.E.03", 520, 785, nodes);
		Node AE03T1 = new Node("A.E.03T1", 520, 872);
		Node AE03T1f = new Node("A.E.03T1Flur", 520, 896);
		Node VorTreppenhaus = new Node("VorTreppenhaus", 520, 955);
		Node AE03T2 = new Node("A.E.03T2", 435, 736);
		Node AE03T2f = new Node("A.E.03T2Flur", x_c_flur, 736);

		// Räume links B-Trakt
		Node BE01 = new Node("B.E.01", 475, y_b_li, nodes);
		Node BE01f = new Node("B.E.01Flur", 475, y_b_flur);

		Node BE02 = new Node("B.E.02", 555, y_b_li, nodes);
		Node BE02f = new Node("B.E.02Flur", 555, y_b_flur);

		Node BE03 = new Node("B.E.03", 625, y_b_li, nodes);
		Node BE03f = new Node("B.E.03Flur", 625, y_b_flur);

		Node BE04 = new Node("B.E.04", 685, y_b_li, nodes);
		Node BE04f = new Node("B.E.04Flur", 685, y_b_flur);

		Node BE06 = new Node("B.E.06", 835, y_b_li, nodes);
		Node BE06f = new Node("B.E.06Flur", 835, y_b_flur);

		Node BE07 = new Node("B.E.07", 925, y_b_li, nodes);
		Node BE07f = new Node("B.E.07Flur", 925, y_b_flur);

		Node BE08 = new Node("B.E.08", 988, y_b_li, nodes);
		Node BE08f = new Node("B.E.08Flur", 988, y_b_flur);

		Node BE09 = new Node("B.E.09", 1070, y_b_li, nodes);
		Node BE09f = new Node("B.E.09Flur", 1070, y_b_flur);

		Node BE10 = new Node("B.E.10", 1150, y_b_li, nodes);
		Node BE10f = new Node("B.E.10Flur", 1150, y_b_flur);

		// Räume rechts B-Trakt
		Node BE20 = new Node("B.E.20", 585, y_b_re, nodes);
		Node BE20f = new Node("B.E.20Flur", 585, y_b_flur);

		Node BE21 = new Node("B.E.21", 700, y_b_re, nodes);
		Node BE21f = new Node("B.E.21Flur", 700, y_b_flur);

		Node BE23 = new Node("B.E.23", 1025, y_b_re, nodes);
		Node BE23f = new Node("B.E.23Flur", 1025, y_b_flur);

		Node BE24 = new Node("B.E.24", 1105, y_b_re, nodes);
		Node BE24f = new Node("B.E.24Flur", 1105, y_b_flur);

		// Räume rechts C-Trakt
		Node CE31 = new Node("C.E.31", x_c_re, 617, nodes);
		Node CE31f = new Node("C.E.31Flur", x_c_flur, 617);

		Node CE312 = new Node("C.E.31.2", x_c_re, 383, nodes);
		Node CE312f = new Node("C.E.31.2Flur", x_c_flur, 383);

		Node CE32 = new Node("C.E.32", x_c_re, 310, nodes);
		Node CE32f = new Node("C.E.32Flur", x_c_flur, 310);

		Node CE321 = new Node("C.E.32.1", x_c_re, 87, nodes);
		Node CE321f = new Node("C.E.32.1Flur", x_c_flur, 87);

		// Räume links C-Trakt
		Node CE40 = new Node("C.E.40", x_c_li, 626, nodes);
		Node CE40f = new Node("C.E.40Flur", x_c_flur, 626);

		Node CE41 = new Node("C.E.41", x_c_li, 492, nodes);
		Node CE41f = new Node("C.E.41Flur", x_c_flur, 492);

		Node CE42 = new Node("C.E.42", x_c_li, 339, nodes);
		Node CE42f = new Node("C.E.42Flur", x_c_flur, 339);

		Node CE43 = new Node("C.E.43", x_c_li, 218, nodes);
		Node CE43f = new Node("C.E.43Flur", x_c_flur, 218);

		Node CE44 = new Node("C.E.44", x_c_li, 165, nodes);
		Node CE44f = new Node("C.E.44Flur", x_c_flur, 165);

		Node CE45 = new Node("C.E.45", x_c_li, 55, nodes);
		Node CE45f = new Node("C.E.45Flur", x_c_flur, 55);

		// Eingang.addNeighbor(Eingangshalle);
		// Neighbors

		Nebeneingang.addNeighbor(Nebeneingangf);
		Nebeneingangf.addNeighbor(AE03T1f);

		Eingang.addNeighbor(AE01T1f);
		AE01T1f.addNeighbor(AE02T1f);
		AE01T1f.addNeighbor(AE01T1);

		AE01T1.addNeighbor(AE01);
		AE01.addNeighbor(AE01T2);
		// AE01T2f.addNeighbor(AE02T2f);
		AE01T2f.addNeighbor(FlurSued);

		AE01T2f.addNeighbor(AE01T2);

		AE02T1f.addNeighbor(AE02T1);
		AE02T1.addNeighbor(AE02);
		AE02.addNeighbor(AE02T2);
		AE02T2.addNeighbor(AE02T2f);
		AE02T2f.addNeighbor(AE03T2f);

		AE03T2f.addNeighbor(CE40f);
		Eingangshalle.addNeighbor(AE01T2f);
		Eingangshalle.addNeighbor(AE02T2f);
		Eingangshalle.addNeighbor(VorTreppenhaus);
		VorTreppenhaus.addNeighbor(AE03T1f);

		Eingangshalle.addNeighbor(AE02T1f);
		AE03T1f.addNeighbor(AE03T1);
		AE03T1.addNeighbor(AE03);
		AE03.addNeighbor(AE03T2);
		AE03T2.addNeighbor(AE03T2f);

		CE40f.addNeighbor(CE31f);
		CE40f.addNeighbor(CE40);

		CE31f.addNeighbor(CE41f);
		CE31f.addNeighbor(CE31);

		CE41f.addNeighbor(CE312f);
		CE41f.addNeighbor(CE41);

		CE312f.addNeighbor(CE42f);
		CE312f.addNeighbor(CE312);

		CE42f.addNeighbor(CE32f);
		CE42f.addNeighbor(CE42);

		CE32f.addNeighbor(CE43f);
		CE32f.addNeighbor(CE32);

		CE43f.addNeighbor(CE44f);
		CE43f.addNeighbor(CE43);

		CE44f.addNeighbor(CE321f);
		CE44f.addNeighbor(CE44);

		CE321f.addNeighbor(CE45f);
		CE321f.addNeighbor(CE321);

		CE45f.addNeighbor(CE45);

		FlurSued.addNeighbor(BE01f);
		BE01f.addNeighbor(BE02f);
		BE01f.addNeighbor(BE01);

		BE02f.addNeighbor(BE20f);
		BE02f.addNeighbor(BE02);

		BE20f.addNeighbor(BE03f);
		BE20f.addNeighbor(BE20);

		BE03f.addNeighbor(BE04f);
		BE03f.addNeighbor(BE03);

		BE04f.addNeighbor(BE21f);
		BE04f.addNeighbor(BE04);

		BE21f.addNeighbor(BE06f);
		BE21f.addNeighbor(BE21);

		BE06f.addNeighbor(BE07f);
		BE06f.addNeighbor(BE06);

		BE07f.addNeighbor(BE08f);
		BE07f.addNeighbor(BE07);

		BE08f.addNeighbor(BE23f);
		BE08f.addNeighbor(BE08);

		BE23f.addNeighbor(BE09f);
		BE23f.addNeighbor(BE23);

		BE09f.addNeighbor(BE24f);
		BE09f.addNeighbor(BE09);

		BE24f.addNeighbor(BE10f);
		BE24f.addNeighbor(BE24);

		BE10f.addNeighbor(BE10);

		from = Eingang;
		to = Eingang;

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

	public void setBMP(Bitmap bmp) {
		this.bmp = bmp;

	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public void setNodes(List nodes) {
		this.nodes = nodes;
	}

	public void setPath(List path) {
		this.path = path;
	}

	public void setTo(Node to) {
		this.to = to;
	}

}
