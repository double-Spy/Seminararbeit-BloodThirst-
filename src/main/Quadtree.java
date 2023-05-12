package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import entity.GameObject;

public class Quadtree {
	private int MAX_OBJECTS = 5;
	private int MAX_LEVELS = 5;
	private int level; //wie tief man im tree ist
	private List<GameObject> objects;
	private Rectangle bounds;
	private Quadtree[] nodes;

	public Quadtree(int pLevel, Rectangle pBounds) {
		level = pLevel;
		objects = new ArrayList<GameObject>();
		bounds = pBounds;
		nodes = new Quadtree[4];
	}

//	public void update(List<WorldObject> allObjects) {
//		
//	}

//	 Clears the quadtree 

	public void draw(Graphics g, int xLevelOffset, int yLevelOffset) {
		// Draw this node
		g.drawRect(bounds.x - xLevelOffset, bounds.y - yLevelOffset, bounds.width, bounds.height);

		// Recursively draw all child nodes
		if (nodes[0] != null) {
			for (Quadtree node : nodes) {
				node.draw(g, xLevelOffset, yLevelOffset);
			}
		}
	}

	public void clear() {
		objects.clear();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

//	  Splits the node into 4 subnodes 

	private void split() {
//		System.out.println("split called");
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();
		nodes[0] = new Quadtree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new Quadtree(level + 1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new Quadtree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new Quadtree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
	}

	/*
	 * Determine which node the object belongs to. -1 means object cannot completely
	 * fit within a child node and is part of the parent node
	 */
	
	private int getIndex(GameObject pRect) {
		int index = -1;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);
		// Object can completely fit within the top quadrants
		boolean topQuadrant = (pRect.getHitBox().getY() < horizontalMidpoint
				&& pRect.getHitBox().getY() + pRect.getHitBox().getHeight() < horizontalMidpoint);
		// Object can completely fit within the bottom quadrants
		boolean bottomQuadrant = (pRect.getHitBox().getY() > horizontalMidpoint);
		// Object can completely fit within the left quadrants
		if (pRect.getHitBox().getX() < verticalMidpoint
				&& pRect.getHitBox().getX() + pRect.getHitBox().getWidth() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1;
			} else if (bottomQuadrant) {
				index = 2;
			}
		}
		// Object can completely fit within the right quadrants
		else if (pRect.getHitBox().getX() > verticalMidpoint) {
			if (topQuadrant) {
				index = 0;
			} else if (bottomQuadrant) {
				index = 3;
			}
		}
		return index;
	}

	/*
	 * Insert the object into the quadtree. If the node exceeds the capacity, it
	 * will split and add all objects to their corresponding nodes.
	 */
	public void insert(GameObject pRect) {
		if (nodes[0] != null) {
			int index = getIndex(pRect);
			if (index != -1) {
				nodes[index].insert(pRect);
				return;
			}
		}
		objects.add(pRect);
		if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if (nodes[0] == null) {
				split();
			}
			int i = 0;
			while (i < objects.size()) {
				int index = getIndex(objects.get(i));
				if (index != -1) {
					nodes[index].insert(objects.remove(i));
				} else {
					i++;
				}
			}
		}

//		print();
	}

	public List<GameObject> retrieve(List<GameObject> returnObjects, GameObject pRect) {
		int index = getIndex(pRect);
		if (nodes[0] != null) {
			// Object can completely fit within a child quadrant
			if (index != -1) {
				nodes[index].retrieve(returnObjects, pRect);
			} else {
				// Object overlaps multiple quadrants
				for (Quadtree node : nodes) {
					node.retrieve(returnObjects, pRect);
				}
			}
		}
		returnObjects.addAll(objects);
		return returnObjects;
	}

	public Quadtree[] getNodes() {
		return nodes;
	}

	public void setNodes(Quadtree[] nodes) {
		this.nodes = nodes;
	}

	public void preOrderTraversal(int level, Quadtree[] node) {

		if (node == null) {
			return;
		}

		String line = "│   ".repeat(level);

		String str = "";
		if (level > 1) {
			str += line;
		}
		str += (level == 1) ? "Tree" : "├── ";

//		if (node[0].isEmpty()) {
//			str += node + " (no obj)";
//		} else {
//			str += node;
//		}
		if (!str.contains("Tree"))
			System.out.println(str);

		// Recursively traverse the children in pre-order
		for (int i = 0; i < node.length; i++) {
			if (node[i] != null) {
//				if (node[i].)
				String str2 = line + "├── " + node[i] + ((node[i].objects.isEmpty()) ? " (no obj)" : "");
				if (!str2.contains("obj")) {
					System.out.println(str2);

					for (int j = 0; j < node[i].objects.size(); j++) {
						String str3 = line + "│   ├── " + node[i].objects.get(j);
						System.out.println(str3);
					}
				} else {
					System.out.println(str2);
				}

				preOrderTraversal(level + 1, node[i].getNodes());
			}
		}

//		if (node[1] != null) {
//			System.out.println(line + "├── " + node[1] + ((node[1].objects.isEmpty()) ? " (no obj)" : node[1].objects));
//			preOrderTraversal(level + 1, node[1].getNodes());
//		}
//		if (node[2] != null) {
//			System.out.println(line + "├── " + node[2] + ((node[2].objects.isEmpty()) ? " (no obj)" : node[2].objects));
//			preOrderTraversal(level + 1, node[2].getNodes());
//		}
//		if (node[3] != null) {
//			System.out.println(line + "└── " + node[3] + ((node[3].objects.isEmpty()) ? " (no obj)" : node[3].objects));
//			preOrderTraversal(level + 1, node[3].getNodes());
//		}
	}
}