package model;

import java.util.ArrayList;

public class Tree<T extends Comparable<T>>{
	private TreeNode<T> root;
	public ArrayList<Integer> intTree = new ArrayList<Integer>();
	
	public Tree(){
		root = null;
	}
	
	public void insertNode( T insertValue ){
		
		if( root == null )
			root = new TreeNode<T>(insertValue);
		else
			root.insert(insertValue);
	}
	
	public void inOrderTraversal(){
		inOrderAlgo(root);
	}
	
	public void inOrderAlgo( TreeNode<T> node){
		if(node == null)
			return;
		inOrderAlgo(node.leftNode);
		intTree.add((Integer)node.data);
		inOrderAlgo(node.rightNode);
		System.out.printf("%s", node.data);
	}
}

