package FHNav.controller;
import java.util.*;

import android.graphics.Bitmap;

public class BreadthFirstSearchTest {

	public class Node {
		List neighbors;
		Node pathParent;
		String name;
		float x;
		float y;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Node(String name) {
			this.name = name;
			neighbors = new LinkedList();
		}
		public Node(String name,float x, float y) {
			this.name = name;
			neighbors = new LinkedList();
			this.x = x;
			this.y = y;
		}
		
		public Node(String name,float x, float y, List l) {
			this.name = name;
			neighbors = new LinkedList();
			this.x = x;
			this.y = y;
			l.add(this);
		}
		
		public void addNeighbor(Node n)
		{
			neighbors.add(n);
			n.neighbors.add(this);
		}
		
		public String toString() {
			return name;
		}
		public float getX() {
			return x;
		}
		public void setX(float x) {
			this.x = x;
		}
		public float getY() {
			return y;
		}
		public void setY(float y) {
			this.y = y;
		}
		
		
	}

	List nodes;
//	Node Eingang;
//	Node Eingangshalle;
//	Node CE31;
//	Node CE31f;
	
	List path;
	
	Node from;
	Node to;
	
	
	
	public Node getFrom() {
		return from;
	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public Node getTo() {
		return to;
	}

	public void setTo(Node to) {
		this.to = to;
	}

	public List getNodes() {
		return nodes;
	}

	public void setNodes(List nodes) {
		this.nodes = nodes;
	}

	public List getPath() {
		return path;
	}

	public void setPath(List path) {
		this.path = path;
	}

	
	public Node getNodeFromListByString(String s)
	{
		Node n = null;
		
		for(int i=0; i<nodes.size();i++)
		{
			Node ntmp = (Node) nodes.get(i);
			if(ntmp.getName().equals(s))
				n = ntmp;
		}
		
		return n;
	}
	
	
	public void initGraph() {

//		float x_c_li=400;
//		float x_c_flur=435;
//		float x_c_re=470;
//		
//		float y_b_li=1225;
//		float y_b_flur=1260;
//		float y_b_re=1295;
		
		float x_c_li=350;
		float x_c_flur=435;
		float x_c_re=520;
		
		float y_b_li=1200;
		float y_b_flur=1260;
		float y_b_re=1320;


		nodes = new LinkedList<Node>();
		
		// Eingangshalle
		Node Eingang = new Node("Eingang",185,950,nodes);	
		Node Eingangshalle = new Node("Eingangshalle",435,950);
		Node FlurSued = new Node("FlurSued",x_c_flur,y_b_flur);
		Node Nebeneingang = new Node("Nebeneingang",586,920,nodes);
		Node Nebeneingangf = new Node("NebeneingangFlur",586,890);
		
		//Hörsäle
		Node AE01 = new Node("A.E.01",220,1110,nodes);
		Node AE01T1 = new Node("A.E.01T1",220,990);
		Node AE01T1f = new Node("A.E.01T1Flur",220,950);
		Node AE01T2 = new Node("A.E.01T2",400,1110);
		Node AE01T2f = new Node("A.E.01T2Flur",435,1110);

		Node AE02 = new Node("A.E.02",280,770,nodes);
		Node AE02T1 = new Node("A.E.02T1",280,915);
		Node AE02T1f = new Node("A.E.02T1Flur",280,950);
		Node AE02T2 = new Node("A.E.02T2",400,770);
		Node AE02T2f = new Node("A.E.02T2Flur",435,770);
		
		Node AE03 = new Node("A.E.03",530,800,nodes);
		Node AE03T1 = new Node("A.E.03T1",510,840);
		Node AE03T1f = new Node("A.E.03T1Flur",555,890);
		Node VorTreppenhaus = new Node("VorTreppenhaus",555,950);
		
		Node AE03T2 = new Node("A.E.03T2",470,745);
		Node AE03T2f = new Node("A.E.03T2Flur",435,745);		
		
		//Räume links B-Trakt
		Node BE01 = new Node("B.E.01",500,y_b_li,nodes);
		Node BE01f = new Node("B.E.01Flur",500,y_b_flur);
		
		Node BE02 = new Node("B.E.02",570,y_b_li,nodes);
		Node BE02f = new Node("B.E.02Flur",570,y_b_flur);
		
		Node BE03 = new Node("B.E.03",645,y_b_li,nodes);
		Node BE03f = new Node("B.E.03Flur",645,y_b_flur);
		
		Node BE04 = new Node("B.E.04",700,y_b_li,nodes);
		Node BE04f = new Node("B.E.04Flur",700,y_b_flur);
	
		Node BE06 = new Node("B.E.06",845,y_b_li,nodes);
		Node BE06f = new Node("B.E.06Flur",845,y_b_flur);
		
		Node BE07 = new Node("B.E.07",940,y_b_li,nodes);
		Node BE07f = new Node("B.E.07Flur",940,y_b_flur);
		
		Node BE08 = new Node("B.E.08",995,y_b_li,nodes);
		Node BE08f = new Node("B.E.08Flur",995,y_b_flur);
		
		Node BE09 = new Node("B.E.09",1080,y_b_li,nodes);
		Node BE09f = new Node("B.E.09Flur",1080,y_b_flur);
		
		Node BE10 = new Node("B.E.10",1160,y_b_li,nodes);
		Node BE10f = new Node("B.E.10Flur",1160,y_b_flur);
		
		//Räume rechts B-Trakt
		Node BE20 = new Node("B.E.20",590,y_b_re,nodes);
		Node BE20f = new Node("B.E.20Flur",590,y_b_flur);
		
		Node BE21 = new Node("B.E.21",710,y_b_re,nodes);
		Node BE21f = new Node("B.E.21Flur",710,y_b_flur);
		
		Node BE23 = new Node("B.E.23",1025,y_b_re,nodes);
		Node BE23f = new Node("B.E.23Flur",1025,y_b_flur);
		
		Node BE24 = new Node("B.E.24",1110,y_b_re,nodes);
		Node BE24f = new Node("B.E.24Flur",1110,y_b_flur);
		
		//Räume rechts C-Trakt
		Node CE31 = new Node("C.E.31",x_c_re,600,nodes);
		Node CE31f = new Node("C.E.31Flur",x_c_flur,600);
		
		Node CE312 = new Node("C.E.31.2",x_c_re,390,nodes);
		Node CE312f = new Node("C.E.31.2Flur",x_c_flur,390);

		Node CE32 = new Node("C.E.32",x_c_re,320,nodes);
		Node CE32f = new Node("C.E.32Flur",x_c_flur,320);
				
		Node CE321 = new Node("C.E.32.1",x_c_re,105,nodes);
		Node CE321f = new Node("C.E.32.1Flur",x_c_flur,105);

		//Räume links C-Trakt
		Node CE40 = new Node("C.E.40",x_c_li,620,nodes);
		Node CE40f = new Node("C.E.40Flur",x_c_flur,620);
		
		Node CE41 = new Node("C.E.41",x_c_li,500,nodes);
		Node CE41f = new Node("C.E.41Flur",x_c_flur,500);

		Node CE42 = new Node("C.E.42",x_c_li,350,nodes);
		Node CE42f = new Node("C.E.42Flur",x_c_flur,350);
				
		Node CE43 = new Node("C.E.43",x_c_li,230,nodes);
		Node CE43f = new Node("C.E.43Flur",x_c_flur,230);
		
		Node CE44 = new Node("C.E.44",x_c_li,185,nodes);
		Node CE44f = new Node("C.E.44Flur",x_c_flur,185);
		
		Node CE45 = new Node("C.E.45",x_c_li,50,nodes);
		Node CE45f = new Node("C.E.45Flur",x_c_flur,50);
		
//		Eingang.addNeighbor(Eingangshalle);
		//Neighbors
		
		Nebeneingang.addNeighbor(Nebeneingangf);
		Nebeneingangf.addNeighbor(AE03T1f);
		
		
		Eingang.addNeighbor(AE01T1f);
		AE01T1f.addNeighbor(AE02T1f);
		AE01T1f.addNeighbor(AE01T1);
		
		
		AE01T1.addNeighbor(AE01);
		AE01.addNeighbor(AE01T2);
//		AE01T2f.addNeighbor(AE02T2f);
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
		
		from=Eingang;
		to=Eingang;

		
		
//		Eingangshalle.addNeighbor(CE31f);
//		
//		CE31f.addNeighbor(CE31);
//		CE31f.addNeighbor(CE312f);
//		CE312f.addNeighbor(CE312);
//		CE312f.addNeighbor(CE32f);
//		CE32f.addNeighbor(CE32);
//		
		
		
//		path = search(Eingang, CE32);
		
//		// Hoersaele
//		Node AE01 = new Node("A.E.01");
//		Node AE02 = new Node("A.E.02");
//
//		// Gruppenraum
//		Node AE03 = new Node("A.E.03");
//
//		// alle anderen Räume
//		Node BE01 = new Node("B.E.01");
//		Node BE02 = new Node("B.E.02");
//		Node BE03 = new Node("B.E.03");
//		Node BE04 = new Node("B.E.04");
//		Node BE06 = new Node("B.E.06");
//		Node BE07 = new Node("B.E.07");
//		Node BE08 = new Node("B.E.08");
//		Node BE09 = new Node("B.E.09");
//		Node BE10 = new Node("B.E.10");
//		Node BE20 = new Node("B.E.20");
//		Node BE21 = new Node("B.E.21");
//		Node BE23 = new Node("B.E.23");
//		Node BE24 = new Node("B.E.24");
//
//		BE01.neighbors.add(BE02);
//		BE02.neighbors.add(BE03);
//		BE02.neighbors.add(BE01);
//		BE03.neighbors.add(BE04);
//		BE03.neighbors.add(BE02);
//		BE04.neighbors.add(BE06);
//		BE04.neighbors.add(BE03);
//		BE06.neighbors.add(BE07);
//		BE06.neighbors.add(BE04);
//		BE07.neighbors.add(BE08);
//		BE07.neighbors.add(BE06);
//		BE08.neighbors.add(BE09);
//		BE08.neighbors.add(BE07);
//		BE09.neighbors.add(BE10);
//		BE09.neighbors.add(BE08);
//		BE10.neighbors.add(BE09);
//
//		BE20.neighbors.add(BE21);
//		BE21.neighbors.add(BE23);
//		BE21.neighbors.add(BE20);
//		BE23.neighbors.add(BE24);
//		BE23.neighbors.add(BE21);
//		BE24.neighbors.add(BE23);
//
//		
//		Node CE312 = new Node("C.E.31.2");
//		Node CE32 = new Node("C.E.32");
//		Node CE321 = new Node("C.E.32.1");
//		Node CE45 = new Node("C.E.45");
//		Node CE44 = new Node("C.E.44");
//		Node CE43 = new Node("C.E.43");
//		Node CE42 = new Node("C.E.42");
//		Node CE41 = new Node("C.E.41");
//		Node CE40 = new Node("C.E.40");
//
//		CE31.neighbors.add(CE312);
//		CE312.neighbors.add(CE32);
//		CE312.neighbors.add(CE31);
//		CE32.neighbors.add(CE321);
//		CE32.neighbors.add(CE312);
//		CE321.neighbors.add(CE32);
//
//		CE40.neighbors.add(CE41);
//		CE41.neighbors.add(CE42);
//		CE41.neighbors.add(CE40);
//		CE42.neighbors.add(CE43);
//		CE42.neighbors.add(CE41);
//		CE43.neighbors.add(CE44);
//		CE43.neighbors.add(CE42);
//		CE44.neighbors.add(CE45);
//		CE44.neighbors.add(CE43);
//		CE45.neighbors.add(CE44);
//
//		Eingangshalle.neighbors.add(BE01);
//		BE01.neighbors.add(Eingangshalle);
//		Eingangshalle.neighbors.add(CE40);
//		CE40.neighbors.add(Eingangshalle);
//
//		Eingangshalle.neighbors.add(CE31);
//		CE31.neighbors.add(Eingangshalle);
//
//		BE20.neighbors.add(Eingangshalle);
//		Eingangshalle.neighbors.add(BE20);
//
//		// Treppenhaus
//		Node node29 = new Node("Treppenhaus.E");

//		BreadthFirstSearchTest bfs = new BreadthFirstSearchTest();
//		
//		String test = (String) path.toString();
//		System.out.println("From BE10 to CE41: " + test);
//		System.out.println();
//		// String myString = test;
//		
//		char[] myCharArray = test.toCharArray();
//		int y = 0;
//		for (int i = 0; i < myCharArray.length; i++) {
//			if (myCharArray[i] == '[' | myCharArray[i] == ','
//					| myCharArray[i] == ']') {
//				y++;
//
//			}
//		}
//
//		char[] raum = new char[test.length() - y];
//		int z = 0;
//
//		for (int i = 1; i < myCharArray.length - 1; i++) {
//			if (myCharArray[i] != ',') {
//				raum[z] = myCharArray[i];
//				// System.out.print(myCharArray[i]);
//				z++;
//			}
//		}
//		String raeume = new String(raum);
//		String[] route = new String[path.size()];
//		StringTokenizer tokenizer;
//		tokenizer = new StringTokenizer(raeume);
//		int t = 0;
//		while (tokenizer.hasMoreTokens()) {
//			// System.out.println(tokenizer.nextToken());
//			//System.out.println(route[t] = tokenizer.nextToken());
//			route[t] = tokenizer.nextToken();
//			t++;
//		}
//		System.out.println();
//		for (int i = 0; i < route.length; i++) {
//			System.out.println(route[i]);
//		}

	}

	protected List constructPath(Node node) {
		LinkedList path = new LinkedList();
		while (node.pathParent != null) {
			path.addFirst(node);
			node = node.pathParent;
		}
		path.addFirst(node);
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
	private Bitmap bmp;

	public Bitmap getBMP() {
		// TODO Auto-generated method stub
		return bmp;
	}

	public void setBMP(Bitmap bmp) {
		this.bmp = bmp;
		
	}

}
