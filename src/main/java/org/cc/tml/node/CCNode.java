package org.cc.tml.node;

import org.cc.tml.ICCNode;

public abstract class CCNode implements ICCNode {
	
	protected int cBegin ;
	
	protected int cEnd ;
	
	
	public int cBegin() {
		return this.cBegin;
	}
	
	public int cEnd() {
		return this.cEnd;
	}
	
}
