package org.cc.model.field;

import org.cc.IAProxyClass;
import org.cc.json.JSONObject;
import org.cc.type.CCStringType;

/**
 * @author William
 */
@IAProxyClass(id = "field.string")
public class CCStringField extends CCField {

    private static final long serialVersionUID = 1L;

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new CCStringType();
    }

   

}
