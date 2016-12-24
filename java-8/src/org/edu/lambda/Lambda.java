package org.edu.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class uses Lambda functions for various use cases. Previously in Java to
 * get even one small function, we must create a class. If we did not want the
 * reference to this class then we could use anonymous class. However that is
 * too much code. With the use of Lambda functions, this can be written in a
 * single line. This is the closest to functional programming that Java has
 * been.
 * 
 * @author shivam.maharshi
 */
public class Lambda {

	/*
	 * Functional interface just has one method. To be used as lambda, the
	 * interface must be a functional interface.
	 */

	public static void runnableExample() {
		Runnable r = () -> System.out.println("In run method.");
		new Thread(r).start();
	}

	public static void execute(CustomFunctionalInterface executor) {
		executor.execute();
	}

	public static void printList(List<Integer> list) {
		list.forEach(n -> System.out.print(n + " "));
	}

	public static void printEvenList(List<Integer> list) {
		System.out.println();
		list.forEach(n -> {
			if (n % 2 == 0) {
				System.out.print(n + " ");
			}
		});
	}

	public static void main(String[] args) {
		runnableExample();

		// Invoke execute method with anonymous class.
		execute(new CustomFunctionalInterface() {
			@Override
			public void execute() {
				System.out.println("In the execute method.");
			}
		});

		// Invoke execute method with Lambda. Feel the difference.
		execute(() -> System.out.println("In the execute method, using Lambda."));

		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		printList(list);

		// Print even numbers
		printEvenList(list);

		// Print even numbers.
		evaluate(list, n -> n % 2 == 0);
	}

	public static void evaluate(List<Integer> list, Predicate<Integer> predicate) {
		System.out.println();
		// This is a lambda within a lambda.
		list.forEach(n -> {
			if (predicate.test(n))
				System.out.print(n + " ");
		});
		System.out.println();
		for (Integer n : list) {
			if (predicate.test(n))
				System.out.print(n + " ");
		}
	}

}
