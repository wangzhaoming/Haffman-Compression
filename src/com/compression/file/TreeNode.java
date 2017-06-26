package com.compression.file;

public class TreeNode {
	private double value;//节点的值
	private int leftChild;//左孩子
	private int rightChild;//右孩子
	private int parent;//父节点
	
	public TreeNode() {
		this.value=0;
		leftChild=rightChild=parent=-1;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(int leftChild) {
		this.leftChild = leftChild;
	}

	public int getRightChild() {
		return rightChild;
	}

	public void setRightChild(int rightChild) {
		this.rightChild = rightChild;
	}
	
	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}
}
