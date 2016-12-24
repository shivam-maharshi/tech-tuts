package org.vt.dlrl.utility;

import norsys.netica.Environ;
import norsys.netica.Net;
import norsys.netica.NeticaException;
import norsys.netica.Streamer;

public class FileUtils {

	/*
	 * Returns a network object instantiation on which operations are to be
	 * performed, by loading its network file.
	 */
	@SuppressWarnings("unused")
	public static Net getNetwork(String filePath) {
		Net network = null;
		try {
			Environ env = new Environ(null);
			network = new Net(new Streamer(filePath));
		} catch (NeticaException e) {
			e.printStackTrace();
		}
		return network;
	}

}
