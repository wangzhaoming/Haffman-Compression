package com.compression.file;

import java.util.HashMap;

public class Tree {
	private TreeNode[] node;
	private int leafNum;
	private int nodeNum;
	private int p1, p2;// 存放节点下标
	private HashMap<Integer, String> map;
	private double[][] value;

	public Tree(double[][] value) {// 使用存储权值的数组初始化
		p1 = p2 = 0;
		leafNum = value.length;
		nodeNum = 2 * leafNum - 1;
		node = new TreeNode[nodeNum];
		this.value = value;
		map = new HashMap<Integer, String>();

		for (int i = 0; i < nodeNum; i++) {
			node[i] = new TreeNode();
		}

		for (int i = 0; i < value.length; i++) {
			node[i].setValue(value[i][1]);
		}

		for (int i = leafNum; i < nodeNum; i++) {
			selectMin(i);
			node[p1].setParent(i);
			node[p2].setParent(i);
			node[i].setLeftChild(p1);
			node[i].setRightChild(p2);
			node[i].setValue(node[p1].getValue() + node[p2].getValue());
		}
	}

	private void selectMin(int n) {
		int i, j;
		for (i = 0; i < n; i++) {
			if (node[i].getParent() == -1) {
				p1 = i;
				break;
			}
		}
		for (j = i + 1; j < n; j++) {
			if (node[j].getParent() == -1) {
				p2 = j;
				break;
			}
		}
		for (i = 0; i < n; i++) {
			if (node[i].getParent() == -1
					&& node[p1].getValue() > node[i].getValue() && i != p2) {
				p1 = i;
			}
		}
		for (j = 0; j < n; j++) {
			if (node[j].getParent() == -1
					&& node[p2].getValue() > node[j].getValue() && j != p1) {
				p2 = j;
			}
		}
	}

	public HashMap<Integer, String> getHashMap() {
		traverse(node[nodeNum - 1], "", nodeNum - 1);
		return map;
	}

	private void traverse(TreeNode node, String str, int n) {
		if (node.getLeftChild() != -1) {
			traverse(this.node[node.getLeftChild()], str + "0",
					node.getLeftChild());
		}
		if (node.getRightChild() != -1) {
			traverse(this.node[node.getRightChild()], str + "1",
					node.getRightChild());
		}
		if (node.getLeftChild() == -1 && node.getRightChild() == -1) {
			if (nodeNum==1) {
				str="0";
			}
			map.put((int)value[n][0], str);
		}
	}
}
