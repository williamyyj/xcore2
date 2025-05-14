package org.cc.tml;

import java.io.File;

public interface ICCDocument extends ICCElement {

	public File fileSource();
	
	public char[] cBuf();
	
	public ICCNode get(int index);
	
	
	
}
