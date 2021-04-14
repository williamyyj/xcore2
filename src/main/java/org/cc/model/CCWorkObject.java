package org.cc.model;


import org.cc.json.JSONObject;

/**
 * @author william
 */
public class CCWorkObject extends JSONObject {

    private static final long serialVersionUID = 8637353103087300963L;
    public final static String wo_sql = "$wo_sql";
    public final static String wo_fields = "$wo_fields";  //    ICCList 
    public final static String wo_params = "$wo_params"; // Object[] .... 
    public final static String wo_mq = "$wo_mq";
    public final static String wo_rs = "$wo_ResultSet";
    public final static String wo_ps = "$wo_PreparedStatement";
    public final static String wo_call = "$wo_ResultSet"; 
    public final static String orderby ="$orderby";

    private CCProcObject proc;
    private JSONObject p;
    //private ICCMap pp;  停用改用 event.$cfg 

    private JSONObject event;
    private CCMetadata metadata;

    public CCWorkObject(CCProcObject proc, String metaId, String eventId, JSONObject p) {
        super();
        this.proc = proc;
        if (metaId != null) {
            this.metadata = proc.metadata(metaId);
        }
        if (metadata != null) {
            this.event = metadata.event(eventId);
        }
        if (p == null) {
            this.p = proc.params();
        }
    }

    public CCWorkObject(CCProcObject proc, String metaId, String eventId) {
        this(proc, metaId, eventId, null);
    }

    public CCWorkObject(CCProcObject proc, JSONObject p) {
        this(proc, null, null, p);
    }

    public void reset(JSONObject p) {
        this.p = p;
    }

    public CCMetadata metadata() {
        return this.metadata;
    }

    public JSONObject event() {
        return this.event;
    }

    public void setEvent(JSONObject event) {
        this.event = event;
    }

    public JSONObject p() {
        return p;
    }

    public CCProcObject proc() {
        return this.proc;
    }
}
