package tests;

import java.text.DecimalFormat;
import java.util.function.DoubleUnaryOperator;

import ToolBox.LookupTable;

public class TestLookupTable {

	public void runTests() {
		//DoubleUnaryOperator uoTanH = (x) -> {return Math.tanh(x);};
		//LookupTable tableTanH = new LookupTable(-1,1,1000,uo);
				
	}
	
	public void runPerformanceTests() {		
		long numTests = 50000;
		testFunction(numTests, 300, "TanH", (x) -> {return Math.tanh(x);});
		testFunction(numTests, 300, "Sin", (x) -> {return Math.sin(x);});
		testFunction(numTests, 300, "Square", (x) -> {return x*x;});
		testFunction(numTests, 300, "Sqrt", (x) -> {return Math.sqrt(x);});
		testFunction(numTests, 300, "double", (x) -> {return x+x;});
		
		for (int i=1;i<20;i++)
			testFunction(numTests, 1000, "TanH", (x) -> {return Math.tanh(x);});
	}
	
	public void testFunction(long numTests, int tableSize, String name, DoubleUnaryOperator uo) {
		
		//DoubleUnaryOperator uoTanH = (x) -> {return Math.tanh(x);};
		LookupTable tableTanH = new LookupTable(-1,1,tableSize,uo);
		 
		long timeStart = System.nanoTime();
		long timeEnd = System.nanoTime();
		long durationOriginal = 0;
		long durationTable = 0;
		double v = 0;
		
		timeStart = System.nanoTime();
		for (int i=0;i<numTests;i++) {
			//v += Math.tanh(i*0.000001);
			v+=uo.applyAsDouble(i*0.0001);
		}
		timeEnd = System.nanoTime();
		durationOriginal = (timeEnd-timeStart)/1000;
		timeStart = System.nanoTime();
		for (int i=0;i<numTests;i++) {
			v += tableTanH.getValue(i*0.0001);
		}
		timeEnd = System.nanoTime();
		durationTable = (timeEnd-timeStart)/1000;
		
		DecimalFormat df = new DecimalFormat("#.000"); 
		
		double ratio = ((double)durationTable / (double)durationOriginal)*100.0; 
		System.out.println(name+" numTests:"+numTests+" Original:"+durationOriginal+" Table:"+durationTable+" ratio:"+df.format(ratio)+"%");
	}
}
