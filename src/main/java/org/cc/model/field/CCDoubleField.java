package org.cc.model.field;

import org.cc.IAProxyClass;
import org.cc.json.JSONObject;
import org.cc.type.CCDoubleType;


/**
 *
 * @author William
 */
@IAProxyClass(id = "field.double")
public class CCDoubleField extends CCNumberField {

    private static final long serialVersionUID = -732210132055174635L;

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new CCDoubleType();
    }


}
