package org.cc.fun.proc.dao;

import java.util.function.BiFunction;
import org.cc.db.DBCmd;
import org.cc.model.CCProcObject;

public class BiDaoSQLInsert implements BiFunction<CCProcObject,String,DBCmd>{

    @Override
    public DBCmd apply(CCProcObject proc, String cmdString) {
        DBCmd cmd = new DBCmd(proc,cmdString);

        return null;
    }
    
}
