package org.vt.dlrl;

import java.io.File;

import org.vt.dlrl.utility.FileUtils;

import norsys.netica.Net;
import norsys.netica.NodeList;
import norsys.netica.Streamer;

/**
 * This class is responsible for simulating random cases using the probability
 * distribution given by our bayesian network.
 * 
 * @author shivam.maharshi
 */
public class CaseSimulator {

	private static Net net = null;

	/*
	 * Initiates the class by loading the network for which random cases have to
	 * be generated.
	 */
	public static void init(String filePath) {
		net = FileUtils.getNetwork(filePath);
	}

	@SuppressWarnings("deprecation")
	public static void generateRandomCases(int noOfCases, String fileName) {
		System.out.println("Generating random cases....");
		try {
			NodeList nodes = net.getNodes();
			// Delete in case previous is existing.
			new File(fileName).delete();
			Streamer caseFile = new Streamer(fileName);
			net.compile();
			// Generating random cases.
			for (int i = 0; i < noOfCases; i++) {
				net.retractFindings();
				// Uses Netica's internal global RandomGenerator.
				int res = net.generateRandomCase(nodes, 0, 20, null);
				if (res > -1) {
					net.writeCase(nodes, caseFile, i, -1.0);
				}
			}
			System.out.println("Succesfully generated " + noOfCases + " random cases to : " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CaseSimulator.init("AsiaEx.dne");
		CaseSimulator.generateRandomCases(20000, "AsiaEx.cas");
	}

}
