package org.cc.model.field;



import org.cc.IAProxyClass;
import org.cc.json.JSONObject;
import org.cc.type.CCDateType;

@IAProxyClass(id = "field.date")
public class CCDateField extends CCField {

    private static final long serialVersionUID = -2698441831039877995L;

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new CCDateType();
    }
}
