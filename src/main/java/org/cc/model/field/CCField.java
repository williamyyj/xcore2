package org.cc.model.field;


import org.cc.ICCType;
import org.cc.json.JSONObject;


public class CCField extends JSONObject implements ICCField {

    private static final long serialVersionUID = -403809901759836838L;
    
    protected ICCType<?> type;


    public CCField() {

    }

    public CCField(String op, String name, ICCType<?> type, String alias) {
        put("id", name.toLowerCase());
        put("name", name);
        this.type = type;
        put("dt", type.dt());
        put("alias", alias);
        put("op", op);
    }

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        putAll(cfg);
    }

    /**
     * 資料庫欄位名
     *
     * @return
     */
    @Override
    public String name() {
        return optString("name", optString("id"));
    }

    /**
     * 系統識別值 （唯一)
     *
     * @return
     */
    @Override
    public String id() {
        return optString("id");
    }

    @Override
    public String dt() {
        return optString("dt");
    }

    @Override
    public String label() {
        return optString("label");
    }

    @Override
    public int dtSize() {
        return optInt("size", 0);
    }

    public String label(String lang) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * DB : P -> pk , F-> FK , I -> index , N -> not null
     *
     * @return
     */
    @Override
    public String ct() {
        return optString("ct", null);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String ft() {
        return optString("ft", null);
    }

    @Override
    public ICCType<?> type() {
        return this.type;
    }

    public JSONObject idxMap(){
        return optJSONObject("im");
    }

    @Override
    public String alias() {
        return optString("alias");
    }

    @Override
    public String fmt() {
        return optString("fmt");
    }

    @Override
    public String notes() {
        return optString("notes");
    }

    @Override
    public void setFieldValue(JSONObject row, Object value) {
        if (row != null) {
            row.put(name(), type().value(value));
        }
    }

    @Override
    public JSONObject cfg() {
        return this;
    }



    @Override
    public Object getFieldValue(JSONObject row) {
        Object ret = null;
        if (row != null) {
            ret = row.get( optString("alias"));
            if (ret == null) {
                ret = row.get(optString("id"));
            }
            if (ret == null) {
                ret = row.get(optString("name"));
            }
            return ret;
        } else {
            return ret;
        }
    }

}
