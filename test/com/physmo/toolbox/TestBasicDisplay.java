package com.physmo.toolbox;

import org.junit.Test;

public class TestBasicDisplay {

	@Test
	public void testBasicDisplay() {
		BasicDisplay bd = new BasicDisplay();
		bd.drawFilledRect(10, 10, 10, 10);
		bd.refresh();
	}
}
