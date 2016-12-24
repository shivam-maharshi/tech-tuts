package org.vt.dlrl;

import norsys.netica.*;
import norsys.neticaEx.aliases.Node;

/**
 * This class has two responsibilities. Firstly, it builds a bayesian network.
 * Secondly, it persistently stores that network by writing onto a file.
 * 
 * @author shivam.maharshi
 */
public class NetworkBuilder {

	public static void buildNetwork() {
		try {
			Node.setConstructorClass("norsys.neticaEx.aliases.Node");
			Environ env = new Environ(null);
			Net network = new Net(env);
			// Creating nodes.
			Node tuberculosis = new Node("tuberculosis", "present, absent", network);
			Node smoking = new Node("smoking", "smoker, nonsmoker", network);
			Node cancer = new Node("cancer", "present, absent", network);
			Node tbOrCa = new Node("tbOrCa", "true, false", network);
			Node xRay = new Node("xRay", "abnormal, normal", network);
			Node visitAsia = new Node("visitAsia", "visit, no_visit", network);
			// Setting title.
			visitAsia.setTitle("Visit to Asia");
			cancer.setTitle("Lung Cancer");
			tbOrCa.setTitle("Tuberculosis or Cancer");
			xRay.setTitle("X-Ray");
			smoking.setTitle("Smoking");
			tuberculosis.setTitle("Tuberculosis");
			// Adding links.
			// VisitAsia is a parent of Tuberculosis.
			tuberculosis.addLink(visitAsia);
			cancer.addLink(smoking);
			tbOrCa.addLink(cancer);
			tbOrCa.addLink(tuberculosis);
			xRay.addLink(tbOrCa);

			// Setting up the parameters.
			visitAsia.setCPTable(0.01, 0.99);
			smoking.setCPTable(0.5, 0.5);

			tuberculosis.setCPTable("visit", 0.05, 0.95);
			tuberculosis.setCPTable("no_visit", 0.01, 0.99);

			cancer.setCPTable("smoker", 0.1, 0.9);
			cancer.setCPTable("nonsmoker", 0.01, 0.99);

			xRay.setCPTable("true", 0.98, 0.02);
			xRay.setCPTable("false", 0.05, 0.95);

			// Setting up the parameters for this node using equations.
			tbOrCa.setEquation("tbOrCa (tuberculosis, cancer) = tuberculosis || cancer");
			tbOrCa.equationToTable(1, false, false);

			// Write the network model to output file.
			Streamer streamer = new Streamer("AsiaEx.dne");
			network.write(streamer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		NetworkBuilder.buildNetwork();
	}

}
