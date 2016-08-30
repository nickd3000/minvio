package ToolBox;

import java.util.function.DoubleUnaryOperator;

/**
 * @author nick
 *
 */
public class LookupTable {

	int numItems;
	double [] values;
	double min;
	double max;
	double range;
	double numItems_range;
	
	public LookupTable(double min, double max, int numItems, DoubleUnaryOperator func) {
		this.min = min;
		this.max = max;
		this.numItems = numItems;
		this.range = max-min;
		numItems_range = numItems/range;
		values = new double[numItems];
		
		for (int i=0;i<numItems;i++) {
			double step = range/(double)numItems;
			double pos=min+(i*step);
			values[i] = func.applyAsDouble(pos);
		}
	}
	
	public double getValue(double position) {
		int ipos = (int)((position-min)*(numItems_range));
		if (ipos<0) ipos=0;
		if (ipos>=numItems) ipos=numItems-1;
		return values[ipos];
	}
	
}
