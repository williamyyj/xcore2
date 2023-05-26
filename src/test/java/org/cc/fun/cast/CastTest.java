package org.cc.fun.cast;

import java.util.Date;

import org.cc.model.CCFunc;
import org.junit.Test;

public class CastTest {
	
	@Test
	public void test_date() {
		Date d = CCFunc.apply("cast.date","110/02/01");
		System.out.println(d);
	}
	
	@Test
	public void test_num() {
		Double d = CCFunc.apply("cast.num","   100,000.05   ");
		System.out.println(d);
	}
}
