package org.cc.fun.cast;

import java.util.function.Function;

import org.cc.IAProxyClass;

@IAProxyClass(id="cast.num")
public class FNumToDouble implements Function<String,Double> {

	@Override
	public Double apply(String text) {
		text = text.replace(",", "").trim();
		return Double.parseDouble(text);
	}

}
