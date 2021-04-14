package org.cc.fun.db;

import org.cc.db.ProcBase;
import org.cc.json.JSONObject;

import java.util.List;
import java.util.function.Function;
import org.cc.model.CCProcObject;


/**
 *
 * @author william
 */
public class proc_mssql_page extends ProcBase implements Function<CCProcObject, List<JSONObject> > {

    @Override
    public List<JSONObject> apply(CCProcObject proc) {
        return null;
    }
    
}
