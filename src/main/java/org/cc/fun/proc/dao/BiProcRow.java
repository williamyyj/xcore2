package org.cc.fun.proc.dao;

import java.util.function.BiFunction;
import org.cc.IAProxyClass;
import org.cc.db.DBCmd;
import org.cc.json.JSONObject;
import org.cc.model.CCActObject;
import org.cc.model.CCCMParams;
import org.cc.model.CCProcObject;
import org.cc.model.ICCModule;

/**
 * example : 
 *  CCProcUtil.exec(proc,"row@metaId,actId");
 */
@IAProxyClass(id="row")
public class BiProcRow extends BiDaoBase implements BiFunction<CCProcObject,String,JSONObject> {

    @Override
    public JSONObject apply(CCProcObject proc, String cmdString) {
        CCCMParams cmp = CCCMParams.newInstance(cmdString);
        ICCModule cm = proc.module(cmp.mid());
        CCActObject ao = new CCActObject(cm, cmp.aid());
        DBCmd cmd = new DBCmd(proc, ao.cfg().opt("$cmd"));
        if(ao.cfg().has("$orderby")){
        	cmd.sql().append(" order by ").append(ao.cfg().optString("$orderby"));
        }
        return row(proc,cmd); 
    }
    
}
