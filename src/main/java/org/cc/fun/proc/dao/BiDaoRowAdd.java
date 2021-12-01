package org.cc.fun.proc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.BiFunction;
import org.cc.IAProxyClass;
import org.cc.db.DBCmd;
import org.cc.db.ICCDB;
import org.cc.fun.db.FSQLInsert;
import org.cc.model.CCCMParams;
import org.cc.model.CCProcObject;
import org.cc.model.ICCModule;

@IAProxyClass(id="add")
public class BiDaoRowAdd extends BiDaoBase implements BiFunction<CCProcObject,String,Integer>{

    private FSQLInsert fsql = new FSQLInsert();
    
    @Override
    public Integer apply(CCProcObject proc, String line) {
        CCCMParams cmp = CCCMParams.newInstance(line);
        ICCModule md = proc.module(cmp.mid());
        String sql = fsql.apply(md.dbFields(cmp.aid()));
        DBCmd cmd = new DBCmd(proc,sql);
        return insert(proc,cmd);
    }


    
}
