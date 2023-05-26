package org.cc.fun.cast;

import java.util.Date;
import java.util.function.Function;

import org.cc.IAProxyClass;
import org.cc.util.CCDateUtils;

@IAProxyClass(id="cast.date")
public class FCDateToString implements Function<String,Date> {

	@Override
	public Date apply(String text) {
		return CCDateUtils.toDate(text);
	}

}
