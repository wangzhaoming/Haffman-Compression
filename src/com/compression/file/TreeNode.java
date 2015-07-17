package com.compression.file;

public class TreeNode {
	private double value;//�ڵ��ֵ
	private int leftChild;//����
	private int rightChild;//�Һ���
	private int parent;//���ڵ�
	
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
