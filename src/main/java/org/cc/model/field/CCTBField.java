package org.cc.model.field;

import org.cc.IAProxyClass;
import org.cc.type.CCStringType;



/**
 * @author william 視表 or 資料表欄位
 */
@IAProxyClass(id = "field.table")
public class CCTBField extends CCField {
    
    private static final long serialVersionUID = 742577963657815980L;

    public CCTBField() {

    }

    public CCTBField(String id, String name) {
        put("dt", "table");
        put("id", id);
        put("name", name);
        type = new CCStringType();
    }

  

}
