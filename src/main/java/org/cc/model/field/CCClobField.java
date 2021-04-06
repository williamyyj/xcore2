package org.cc.model.field;

import org.cc.IAProxyClass;
import org.cc.json.JSONObject;
import org.cc.type.CCClobType;

/**
 *
 * @author william
 */




@IAProxyClass(id = "field.clob")
public class CCClobField extends CCField {

    private static final long serialVersionUID = -5143253533237420204L;

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new CCClobType();
    }
    
}
