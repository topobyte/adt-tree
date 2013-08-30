/**
 * Copyright (C) 2013 Sebastian Kürten.
 */
package de.topobyte.adt.avltree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.topobyte.adt.tree.BinaryTreeNode;

public class TestTreeInterface
{
	public static void main(String[] args)
	{
		int t = 16; // number of insertions
		int max = 99; // maximum value for elements

		System.out.println("TEST: Tree interface");
		System.out.println("performing " + t + " insertions");

		AvlTree<Integer> tree = new AvlTree<Integer>();
		List<Integer> list = new ArrayList<Integer>();

		Random random = new Random(2);

		for (int i = 0; i < t; i++) {
			int n = 0;
			while (true) {
				n = random.nextInt(max);
				if (!list.contains(n)) {
					break;
				}
			}
			tree.insertElement(n);
			list.add(n);
			Collections.sort(list);

		}
		check(tree, list);
	}

	private static void check(AvlTree<Integer> tree, List<Integer> list)
	{
		if (tree.size() != list.size()) {
			System.out.println("size error");
			System.exit(1);
		}
		if (!TestHelper.identical(tree.elementsAsList(), list)) {
			System.out.println("equality error");
			System.out.println(TestHelper.print(tree.elementsAsList()));
			System.out.println(TestHelper.print(list));
			System.exit(1);
		}

		BinaryTreeNode<Integer> root = tree.getBinaryRoot();

		TestTreeInterface test = new TestTreeInterface();
		test.print(root);
	}

	private List<List<Integer>> rows = new ArrayList<List<Integer>>();

	private void print(BinaryTreeNode<Integer> root)
	{
		int h = height(root);
		for (int level = 0; level < h; level++) {
			rows.add(new ArrayList<Integer>());
		}

		traverse(root, 0);

		for (int level = 0; level < h; level++) {
			// height of the current level
			int height = h - level - 1;
			// # chars to print before the first node of the level on the line
			int before = spaceBefore(height);
			// # chars between two subsequent nodes on this level
			int inter = spaceInter(height);
			// # chars between two subsequent nodes on the next level
			int interNext = 0;
			// # number of underscore to represent a link from this
			// level to the next
			int link = 0;
			// # number of chars between two nodes on this level minus
			// the underscore to depict the links to the next level
			int space = 0;
			if (height != 0) {
				interNext = spaceInter(height - 1);
				space = interNext + 4;
				link = (inter - space) / 2;
			}

			int startingSpace = before - link;

			print(startingSpace, " ");
			print(link, "_");

			List<Integer> row = rows.get(level);
			for (int i = 0; i < row.size(); i++) {
				Integer value = row.get(i);
				printElement(value);
				if (i == row.size() - 1) {
					break;
				}
				if (height == 0) {
					print(inter, " ");
				} else {
					print(link, "_");
					print(space, " ");
					print(link, "_");
				}
			}
			print(link, "_");

			System.out.println();
		}
	}

	private void traverse(BinaryTreeNode<Integer> node, int level)
	{
		if (node == null) {
			if (level < rows.size()) {
				traverse(null, level + 1);
			}
		} else {
			traverse(node.getLeft(), level + 1);
		}
		handle(node, level);
		if (node == null) {
			if (level < rows.size()) {
				traverse(null, level + 1);
			}
		} else {
			traverse(node.getRight(), level + 1);
		}
	}

	private void handle(BinaryTreeNode<Integer> node, int level)
	{
		if (level >= rows.size()) {
			return;
		}
		if (node == null) {
			rows.get(level).add(null);
		} else {
			rows.get(level).add(node.getElement());
		}
	}

	private void print(int n, String c)
	{
		for (int i = 0; i < n; i++) {
			System.out.print(c);
		}
	}

	private void printElement(Integer value)
	{
		if (value == null) {
			System.out.print("[]");
		} else {
			System.out.print(String.format("%2d", value));
		}
	}

	private int height(BinaryTreeNode<Integer> node)
	{
		if (node == null) {
			return 0;
		}
		return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
	}

	private int spaceInter(int height)
	{
		if (height == 0) {
			return 2;
		}
		return spaceInter(height - 1) * 2 + 2;
	}

	private int spaceBefore(int height)
	{
		if (height == 0) {
			return 0;
		}
		return spaceBefore(height - 1) * 2 + 2;
	}
}
