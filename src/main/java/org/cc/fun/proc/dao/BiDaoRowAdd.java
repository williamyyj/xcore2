package org.cc.fun.proc.dao;

import java.util.function.BiFunction;
import org.cc.IAProxyClass;
import org.cc.db.DBCmd;
import org.cc.fun.db.FSQLInsert;
import org.cc.model.CCCMParams;
import org.cc.model.CCProcObject;
import org.cc.model.ICCModule;

@IAProxyClass(id="dao_add")
public class BiDaoRowAdd extends BiDaoBase implements BiFunction<CCProcObject,String,Long>{

    private FSQLInsert fsql = new FSQLInsert();
    
    @Override
    public Long apply(CCProcObject proc, String line) {
        CCCMParams cmp = CCCMParams.newInstance(line);
        ICCModule md = proc.module(cmp.mid());
        String sql = fsql.apply(md.dbFields(cmp.aid()));
        DBCmd cmd = new DBCmd(proc,sql);
        return insert(proc,cmd);
    }
    
}
