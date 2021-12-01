package org.cc.fun.proc.dao;

import java.util.function.BiFunction;
import org.cc.IAProxyClass;
import org.cc.db.DBCmd;
import org.cc.fun.db.FSQLPK;
import org.cc.json.JSONObject;
import org.cc.model.CCCMParams;
import org.cc.model.CCProcObject;
import org.cc.model.ICCModule;


@IAProxyClass(id="pk_row")
public class BiDaoPKRow extends BiDaoBase implements BiFunction<CCProcObject,String,JSONObject>{
    private FSQLPK fsql = new FSQLPK();
    @Override
    public JSONObject apply(CCProcObject proc, String cmdString) {
        CCCMParams cmp = CCCMParams.newInstance(cmdString);
        ICCModule cm = proc.module(cmp.mid());
        String sql = fsql.apply(cm.dbFields(cmp.aid()));
        DBCmd cmd = new DBCmd(proc,sql);
        return row(proc,cmd);
    }

}
