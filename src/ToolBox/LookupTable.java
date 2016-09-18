package ToolBox;

import java.util.function.DoubleUnaryOperator;

/**
 * @author Nick Donnelly
 * For creating fast lookup tables to replace mathematical functions, like Sin, Tan etc.
 * It's recommended to benchmark how you use this to ensure it's faster than
 * the operation you are replacing.
 */
public class LookupTable {

	int numItems;
	double [] values;
	double min, max, range;
	double numItems_range;
	
	/**
	 * @param min		Smallest possible input value.
	 * @param max		Largest possible input value.
	 * @param numItems	Capacity of lookup table, higher values increase accuracy.
	 * @param func		DoubleUnaryOperator Lambda function 
	 */
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
	
	public double getValue(double x) {
		int index = (int)((x-min)*(numItems_range));
		if (index<0) index=0;
		if (index>=numItems) index=numItems-1;
		return values[index];
	}
	
	// TODO: getInterpolatedValue()
	// TODO: getValue with no bounds checking?
	// TODO: getWrappedValue() - for repeating functions like sine etc.
	
}
