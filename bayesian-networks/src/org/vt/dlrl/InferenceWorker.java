package org.vt.dlrl;

import java.util.HashMap;
import java.util.Map;

import org.vt.dlrl.utility.FileUtils;

import norsys.netica.Net;
import norsys.netica.Node;

/**
 * This class is responsible for calculating beliefs for events from the
 * constructed bayesian network.
 * 
 * @author shivam.maharshi
 */
public class InferenceWorker {

	private static Net net = null;

	/*
	 * Initiates the class by loading the network on which inferences are to be
	 * performed.
	 */
	public static void init(String filePath) {
		net = FileUtils.getNetwork(filePath);
	}

	@SuppressWarnings("deprecation")
	public static void getBelief(BeliefInput input, Map<String, String> findings) {
		try {
			Node beliefNode = net.getNode(input.getNode());
			// Entering the findings for the parent nodes.
			for (String findingNode : findings.keySet()) {
				Node node = net.getNode(findingNode);
				node.enterFinding(findings.get(findingNode));
			}
			net.compile();
			// Get belief for the input node for the value given the findings.
			double belief = beliefNode.getBelief(input.getValue());
			String output = "Given";
			int i = 0;
			// Printing belief for the given the findings.
			for (String findingNode : findings.keySet()) {
				output += " \n " + ++i + " " + findingNode + " is " + findings.get(findingNode);
			}
			output += " \n ==> Probability of " + input.getNode() + " being " + input.getValue() + " is " + belief;
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// init("C:Windows\\Users\\Sam\\Workspace\\BayesianNetworksTutorial\\AsiaEx.dne");
		init("AsiaEx.dne");
		BeliefInput input = new BeliefInput("tuberculosis", "present");
		Map<String, String> findings = new HashMap<String, String>();
		findings.put("xRay", "abnormal");
		findings.put("visitAsia", "visit");
		InferenceWorker.getBelief(input, findings);
	}

}

class BeliefInput {

	String node;
	String value;

	public BeliefInput(String node, String value) {
		super();
		this.node = node;
		this.value = value;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
