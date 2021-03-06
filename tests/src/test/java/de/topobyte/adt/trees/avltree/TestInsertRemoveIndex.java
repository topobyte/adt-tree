/**
 * Copyright (C) 2013 Sebastian Kürten.
 */
package de.topobyte.adt.trees.avltree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class TestInsertRemoveIndex
{
	@Test
	public void test()
	{
		int t = 10000; // number of operations
		int max = 10000; // maximum value for elements
		int pa = 2, pb = 3; // (pa/pb) is the propabilty for an insertion

		boolean print = false;

		System.out.println("TEST: Insertion and removal by index");
		System.out.println("performing " + t + " insertions / deletions");

		AvlTree<Integer> tree = new AvlTree<>();
		List<Integer> list = new ArrayList<>();

		Random random = new Random();
		random.setSeed(2);

		// perform some insertions and deletions
		for (int i = 0; i < t; i++) {
			int p = random.nextInt(pb);
			if (p < pa || list.isEmpty()) {
				// insert
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
			} else {
				// remove
				int index = random.nextInt(list.size());
				int r1 = tree.remove(index);
				int r2 = list.remove(index);
				if (r1 != r2) {
					Assert.fail("removed different elements");
				}
			}

			check(tree, list, print);
		}

		// now remove all elements
		while (list.size() > 0) {
			// remove
			int index = random.nextInt(list.size());
			int value = list.get(index);
			tree.removeElement(value);
			list.remove(new Integer(value));

			check(tree, list, print);
		}

		System.out.println("done");
	}

	private static void check(AvlTree<Integer> tree, List<Integer> list,
			boolean print)
	{
		if (print) {
			System.out.println("list: " + TestHelper.print(list));
			System.out.println(
					"tree: " + TestHelper.print(tree.elementsAsList()));
			System.out.println("tree: " + tree.toFoldedString());
		}

		Assert.assertTrue("balanced", tree.checkBalanced());

		TestHelper.assertEqual(list, tree);
	}

}
