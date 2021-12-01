package org.cc.fun.proc.dao;

import java.sql.PreparedStatement;
import java.util.function.BiFunction;

import org.cc.fun.db.FSQLUpdate;
import org.cc.model.CCCMParams;
import org.cc.model.CCProcObject;
import org.cc.model.ICCModule;
import org.cc.db.DBCmd;
import org.cc.db.ICCDB;

public class BiDaoRowEdit extends BiDaoBase implements BiFunction<CCProcObject,String,Long>{

    private FSQLUpdate fsql = new FSQLUpdate();

    @Override
    public  Long apply(CCProcObject proc, String cmdString) {
        CCCMParams cmp = CCCMParams.newInstance(cmdString);
        ICCModule md = proc.module(cmp.mid());
        String sql = fsql.apply(md.dbFields(cmp.aid()));
        DBCmd cmd = new DBCmd(proc,sql);
        ICCDB db = proc.db();
        try (PreparedStatement ps = db.connection().prepareStatement(cmd.sqlString())) {
            cmdFill.accept(ps, cmd.qrFileds());
            return (long) ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }
    
}
