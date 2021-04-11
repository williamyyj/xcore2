package org.cc.model;

import org.cc.db.DB;
import org.cc.db.ICCDB;
import org.cc.json.CCPath;
import org.cc.json.JSONObject;
import org.cc.model.field.ICCField;
import java.io.Closeable;
import java.io.IOException;


/**
 * @author william
 */
public class CCProcObject extends JSONObject implements Closeable {

    private static final long serialVersionUID = -7752353211951757328L;
    public final static String pre_metadata = "$metadata";
    public final static String pre_module = "$module";
    public final static String pre_cfg = "$cfg";
    public final static String pre_ff = "$ff"; //  field function 
    public final static String pre_fp = "$fp"; //  表單回傳或初始資料
    public final static String pre_fields = "$fields"; //  表單回傳或初始資料
    public final static String act = "$act";
    public final static String cmd = "$cmd";
    public final static String prjPrefix = "/module/$meta"; //  專案開發
    public final static String prodPrefix = "/dp/metadata"; // 產品
    public final static int attr_self = 0;
    public final static int attr_params = 1;
    public final static int attr_request = 2;
    public final static int attr_session = 3;
    public final static int attr_app = 4;

    protected String base;
    protected String dbId;
    public CCProcObject(String base) {
        this(base,"db");
    }

    public CCProcObject(String base, String dbId) {
        this.base = base;
        this.dbId = dbId;
        put(pre_fp, new JSONObject());
        put(pre_ff, new JSONObject());
        put(pre_fields, new JSONObject());
    }

    @Override
    public void close() throws IOException {
        //db().release();
    }

    public JSONObject fp() {
        return optJSONObject(pre_fp);
    }

    /*
     *   monk object 
     */
    public Object get(int fld, String name, Object dv) {
        switch (fld) {
            case attr_self:
                return (containsKey(name)) ? get(name) : dv;
            case attr_params:
                return fp().containsKey(name) ? fp().get(name) : dv;
            case attr_request:
                return containsKey("$req_" + name) ? get("$req_" + name) : dv;
            case attr_session:
                return containsKey("$sess_" + name) ? get("$sess_" + name) : dv;
            case attr_app:
                return containsKey("$app_" + name) ? get("$app_" + name) : dv;
        }
        return dv;
    }

    public Object set(int fld, String name, Object value) {
        switch (fld) {
            case attr_self:
                return put(name, value);
            case attr_params:
                return fp().put(name, value);
            case attr_request:
                return put("$req_" + name, value);
            case attr_session:
                return put("$sess_" + name, value);
            case attr_app:
                return put("$app_" + name, value);
        }
        return null;
    }

    public String base() {
        return this.base;
    }

    public ICCDB db() {
        return (ICCDB) this.getOrDefault(dbId, init_db());
    }

    private Object init_db() {
        ICCDB db = (ICCDB) get(dbId);
        if (db == null) {
            db = new DB(base,dbId);
            put(dbId, db);
        }
        return db;
    }

    /**
     * metaId metaId:alias perfix:metaId:alias
     *
     * @param line
     * @return
     */
    public CCMetadata metadata(String line) {
        String[] items = line.split(":");
        switch (items.length) {
            case 1:
                return proc_metadata(items[0]);
            case 2:
                return proc_metadata(items[0], items[1]);
            case 3:
                return proc_metadata(items[0], items[1], items[3]);
            default:
                return null;
        }

    }

    private CCMetadata proc_metadata(String metaId) {
        String id = pre_metadata + ":" + metaId;
        CCMetadata md = (CCMetadata) CCPath.path(this, id);
        if (md == null) {
            md = new CCMetadata(base, metaId);
            CCPath.set(this, id, md);
            map(pre_fields).putAll(md.fields());
        }
        return md;
    }

    private CCMetadata proc_metadata(String metaId, String alias) {
        String id = pre_metadata + ":" + metaId;
        CCMetadata md = (CCMetadata) CCPath.path(this, id);
        if (md == null) {
            md = new CCMetadata(base, metaId, alias);
            CCPath.set(this, id, md);
            map(pre_fields).putAll(md.fields());
        }
        return md;
    }

    private CCMetadata proc_metadata(String prefix, String metaId, String alias) {
        String id = pre_metadata + ":" + metaId;
        CCMetadata md = (CCMetadata) CCPath.path(this, id);
        if (md == null) {
            if (prefix.length() == 0) {
                md = new CCMetadata(base, metaId, alias);
            } else {
                md = new CCMetadata(base, prefix, metaId, alias);
            }
            CCPath.set(this, id, md);
            optJSONObject(pre_fields).putAll(md.fields());
        }
        return md;
    }

   

    public JSONObject params() { //
        if (!this.containsKey("$")) {
            put("$", new JSONObject());
        }
        return optJSONObject("$");
    }

    public ICCField field(String fid) {
        return (ICCField) optJSONObject(pre_fields).get(fid);
    }

}
