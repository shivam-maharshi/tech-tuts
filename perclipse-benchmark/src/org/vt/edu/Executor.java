package org.vt.edu;

/**
 * This class is responsible for providing a dummy functionality under
 * evaluation.
 * 
 * @author shivam.maharshi
 */
public class Executor {

	public int calculateSum(int[] arr) {
		int res = 0;
		for (int i = 0; i < arr.length; i++)
			res += arr[i];
		return res;
	}

	public int multiplication(int a, int b) {
		return a * b;
	}

}
