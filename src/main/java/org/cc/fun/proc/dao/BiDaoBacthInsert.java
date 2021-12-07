package org.cc.fun.proc.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.cc.db.DBCmd;
import org.cc.db.DBCmdField;
import org.cc.fun.db.FSQLInsert;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;
import org.cc.model.CCCMParams;
import org.cc.model.CCProcObject;
import org.cc.model.ICCModule;

/**
 * 提供 bacth insert , update 
 */
public class BiDaoBacthInsert extends BiDaoBase {

    protected FSQLInsert fsql = new FSQLInsert();
    private CCProcObject proc;
    private JSONArray data; // List<JSONObject> 
    private String cmdString;
    private int[] ret ; 
    private PreparedStatement ps ; 
    private int blockSize = 1000 ;
    private DBCmd cmd ;


    public BiDaoBacthInsert(CCProcObject proc, JSONArray data, String cmdString){
        this.proc = proc;
        this.data = data;
        this.cmdString = cmdString;
        int[] ret = data!=null ? new int[data.length()] : new int[0];
    }  

    public int[] executeBatch(){  
        try{
           proc_bacth();
        } catch(Exception e){
            e.printStackTrace();
        }
        return ret ; 
    }

    private void proc_bacth() throws Exception {
        proc.db().connection().setAutoCommit(false); // 如果要記前次有點不合理表示非完整批次作業
        try{
            proc_batch_begin();
            proc_batch_each();
            proc.db().connection().commit();
        } catch(Exception e) {
            e.printStackTrace();
            proc.db().connection().rollback();
        } finally {
            if(ps!=null){
                ps.close();
            }
            proc.db().connection().setAutoCommit(true); // 回到real time 作業
        }
    }

    private void proc_batch_each() throws SQLException {
        int idx = 1 ; 
        for(Object item : data ){ // for this version JSONArray support 
            if(idx % blockSize == 0 ){
                int retBlock[] = ps.executeBatch();
                System.arraycopy(ret, idx-blockSize, retBlock, 0, retBlock.length);
            }
            JSONObject p = (JSONObject)item;
            proc_item(p);
            idx++;
        }
        if(idx>1){
            int retBlock[] = ps.executeBatch();
            System.arraycopy(ret, idx-blockSize, retBlock, 0, retBlock.length);
        }

    }

    private void proc_item(JSONObject item) throws SQLException {
        for(DBCmdField fld : cmd.qrFileds()){
            fld.setValue(null); // clear 
            fld.setValue(getFieldValue(fld,item));
        }
        cmdFill.accept(ps, cmd.qrFileds());
        ps.addBatch();
    }

    private void proc_batch_begin() throws SQLException {
        CCCMParams cmp = CCCMParams.newInstance(cmdString);
        ICCModule md = proc.module(cmp.mid());
        String sql = fsql.apply(md.dbFields(cmp.aid()));
        proc.put("$",data.get(0)); // Get first item setting sql pattern
        cmd = new DBCmd(proc,sql);
        ps = proc.db().connection().prepareStatement(cmd.sqlString());
    }

    private Object getFieldValue(DBCmdField fld, JSONObject p){
        Object value = null;
        if (p.containsKey(fld.getId())) {
            value = fld.getType().value(p.get(fld.getId()));
        }
        if (value == null && fld.getAlias() != null && p.containsKey(fld.getAlias() )) {
            value =  fld.getType().value(p.get(fld.getAlias()));
        }
        return value;
    }

    
}
