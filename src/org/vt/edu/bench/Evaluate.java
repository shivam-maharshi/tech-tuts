package org.vt.edu.bench;

import org.vt.edu.Executor;
import org.perfidix.annotation.Bench;

/**
 * This class is responsible for executing the methods under performance
 * evaluation phase.
 * 
 * @author shivam.maharshi
 *
 */
public class Evaluate {

	@Bench(runs = 50)
	public void benchCalculateSum() {
		Executor exe = new Executor();
		int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		exe.calculateSum(arr);
	}

	@Bench
	public void benchMultiplication() {
		Executor exe = new Executor();
		exe.multiplication(199, 200001);
	}

}
