package org.cc.model;

import lombok.extern.log4j.Log4j2;
import org.cc.ICCField;
import org.cc.ICCInit;
import org.cc.json.CCJSON;
import org.cc.json.CCPath;
import org.cc.json.JSONArray;
import org.cc.json.JSONObject;


/**
 * @author william
 */
@Log4j2
public class CCFF {

    public static String prefix = "$ff";

    public static String pkg = "org.cc.ff";

    public static String pffId(String id) {
        return prefix + "_" + id;
    }

    public static ICCFF<?> getRequestFF(CCProcObject proc, String id) {
        return (ICCFF<?>) proc.get(CCProcObject.attr_request, pffId(id), null);
    }

    public static void setRequestFF(CCProcObject proc, String id, ICCFF<?> ff) {
        proc.set(CCProcObject.attr_request, pffId(id), ff);
    }

    public static ICCFF<?> ff_create(CCProcObject proc, String ffId, JSONObject cfg) {
        try {
            String classId = pkg + "." + ffId;
            Class<?> cls = Class.forName(classId);
            Object o = cls.getDeclaredConstructor().newInstance();
            ICCInit<JSONObject> init = (ICCInit<JSONObject>) o;
            init.__init__(cfg);
            ((ICCFF<?>) o).init(proc);

            return (ICCFF<?>) o;
        } catch (Exception ex) {
            log.error("Can't find " + cfg, ex);
        }
        return null;
    }

    public static ICCFF<?> ff_create(CCProcObject proc, CCMetadataOld md, Object line) {
        JSONObject cfg = CCFF.config(md, line);
        String classId = cfg.optString("$ff");
        return ff_create(proc, classId, cfg);
    }

    public static JSONObject config(CCMetadataOld md, Object line) {
        JSONObject ff = CCJSON.line(line);
        String fid = ff.optString("$id");
        ICCField fld = (md != null) ? md.fields().get(fid) : null;
        if (fld != null) {
            ff.put("$fld", fld.cfg());
        }
        return ff;
    }

    public static JSONObject meta_ff(CCProcObject proc, String metaId) {
        JSONObject ffMap =  proc.optJSONObject("$ff");
        JSONArray ffs = proc.metadata(metaId).cfg().optJSONArray("$ff");
        if (ffs != null) {
            for (int i = 0; i < ffs.length(); i++) {
                ICCFF<?> ff = ff_create(proc, proc.metadata(metaId), ffs.get(i));
                ffMap.put(ff.cfg().optString("$id"), ff);
            }
        }
        return ffMap;
    }

    public static ICCFF<?> proc_ff(CCProcObject proc, Object line) {
        JSONObject cfg = CCFF.config(null, line);
        ICCFF<?> ff = ff(proc, cfg.optString("$id"));
        if (ff == null) {
            String classId = cfg.optString("$ff");
            ff = ff_create(proc, classId, cfg);
        } else {
            log.info("Dup load  ff : "+line);
        }
        CCPath.set(proc, CCProcObject.pre_ff + ":" + ff.cfg().optString("$id"), ff);
        return ff;
    }

    public static ICCFF<?> ff(CCProcObject proc, String id) {
        return (ICCFF<?>) proc.optJSONObject(CCProcObject.pre_ff).get(id);
    }

    public static String dv(Object fv, String dv) {
        return (fv != null) ? fv.toString().trim() : dv;
    }
}
