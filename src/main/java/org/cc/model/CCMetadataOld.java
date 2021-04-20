package org.cc.model;

import org.cc.ICCField;
import org.cc.json.CCCache;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;
import org.cc.model.field.CCFieldUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 只提供標準資料庫操作
 * 改使用 CCModuleMataMode 
 * 
 * @author william
 */
public class CCMetadataOld {

    private String base;

    private String prefix;

    private String alias;
    
    private JSONObject cfg;

    private Map<String, ICCField> _fields;


    public CCMetadataOld(String base, String prefix, String metaId) {
        this(base, prefix, metaId, null);
    }



    public CCMetadataOld(String base, String prefix, String metaId, String alias) {
        super();
        this.base = base;
        this.prefix = prefix;
        this.alias = alias;
        _fields = new LinkedHashMap<>();
        this.load_metadata(metaId);
    }

    private void load_metadata(String metaId) {
        cfg = CCCache.load(base + prefix, metaId);
        if (cfg.containsKey("meta")) {
            JSONArray mItems = cfg.optJSONArray("meta");
            mItems.forEach((Object o) -> {
                try {
                    JSONObject item = (JSONObject) o;
                    ICCField field = CCFieldUtils.newInstance(item);
                    _fields.put(item.optString("id"), field);
                    if (alias != null) {
                        _fields.put(alias + "." + item.optString("id"), field);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(System.out);
                }
            });
        }
    }

    public Map<String, ICCField> fields() {
        return _fields;
    }

    public List<ICCField> tbFields() {
        List<ICCField> ret = new ArrayList<>();
        String[] flds = cfg.optString("$tbFields").split(",");
        for (String fld : flds) {
            ret.add(_fields.get(fld));
        }
        return ret;
    }

    public JSONObject event(String id){
        return cfg.optJSONObject(id);
    }

    public JSONObject cfg() {
        return cfg;
    }

}
