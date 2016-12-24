package org.vt.dlrl;

import org.vt.dlrl.utility.FileUtils;

import norsys.netica.Net;
import norsys.netica.Node;
import norsys.netica.NodeList;
import norsys.netica.Streamer;

/**
 * This class is responsible for generating a model i.e. building Conditional
 * Probability Tables for a given a network structure using a data set.
 * 
 * @author shivam.maharshi
 */
public class ModelGenerator {

	private static Net net = null;

	/*
	 * Initiates the class by loading the network on which inferences are to be
	 * performed.
	 */
	public static void init(String filePath) {
		// Reading the created network for using its structure.
		net = FileUtils.getNetwork(filePath);
	}

	/*
	 * It reads the data set to learn CPT tables thereby generating a bayesian
	 * model using the provided structure.
	 */
	public static void learnCPT(String dataSet, String generatedModel) {
		System.out.println("Initializing CPT Learning....");
		try {
			NodeList nodes = net.getNodes();
			int noOfNodes = nodes.size();
			// Removing CPT for all nodes.
			for (int i = 0; i < noOfNodes; i++) {
				Node node = (Node) nodes.get(i);
				node.deleteTables();
			}
			System.out.println("Using the provided Data Set : " + dataSet);
			// Reading the data set to learn CPT.
			Streamer caseFile = new Streamer(dataSet);
			net.reviseCPTsByCaseFile(caseFile, nodes, 1.0);
			// Writing the new model learned using the data set.
			net.write(new Streamer(generatedModel));
			System.out.println("Conditional Probability tables learnt succesfully !!");
			System.out.println("Generated Bayesian Model stored in : " + generatedModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ModelGenerator.init("AsiaEx.dne");
		ModelGenerator.learnCPT("AsiaEx.cas", "LearnedAsiaEx.dne");
	}

}
