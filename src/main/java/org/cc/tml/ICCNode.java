package org.cc.tml;

import java.util.Map;

public interface ICCNode {
    
	public Object execute(Map<String,Object> m);
	
	public String source();
	
	public int cBeing();
	
	public int cEnd();
	
}
