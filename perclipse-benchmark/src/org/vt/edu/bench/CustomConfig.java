package org.vt.edu.bench;

import java.util.HashSet;
import java.util.Set;

import org.perfidix.AbstractConfig;
import org.perfidix.annotation.BenchmarkConfig;
import org.perfidix.element.KindOfArrangement;
import org.perfidix.meter.AbstractMeter;
import org.perfidix.meter.Time;
import org.perfidix.meter.TimeMeter;
import org.perfidix.ouput.AbstractOutput;
import org.perfidix.ouput.TabularSummaryOutput;

/**
 * This class is responsible for setting up the default configurations for all
 * the benchmarks in this project.
 * 
 * @author shivam.maharshi
 */
@BenchmarkConfig
public class CustomConfig extends AbstractConfig {

	private static int RUNS = 50;
	private static Set<AbstractMeter> TIME_METER = new HashSet<AbstractMeter>();
	private static Set<AbstractOutput> OUTPUT = new HashSet<AbstractOutput>();
	private static KindOfArrangement ARRANGEMENT = KindOfArrangement.ShuffleArrangement;
	private static double GARBAGE_COLLECTION = 1;

	static {
		TIME_METER.add(new TimeMeter(Time.MilliSeconds));
		OUTPUT.add(new TabularSummaryOutput());
	}

	public CustomConfig() {
		super(RUNS, TIME_METER, OUTPUT, ARRANGEMENT, GARBAGE_COLLECTION);
	}

}
