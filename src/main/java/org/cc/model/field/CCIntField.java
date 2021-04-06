package org.cc.model.field;

import org.cc.IAProxyClass;
import org.cc.json.JSONObject;
import org.cc.type.CCIntType;





/**
 *
 * @author William
 */
@IAProxyClass(id="field.int")
public class CCIntField extends CCNumberField {
    
    private static final long serialVersionUID = -7722291968087905846L;

    @Override
     public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new CCIntType();
    }
    
}
