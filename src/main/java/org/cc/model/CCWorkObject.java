package org.cc.model;


import org.cc.json.JSONObject;

/**
 * @author william
 */
public class CCWorkObject extends JSONObject {

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

    public CCWorkObject(CCProcObject proc, String metaId, String eventId, ICCMap p) {
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

    public CCWorkObject(CCProcObject proc, ICCMap p) {
        this(proc, null, null, p);
    }

    public void reset(ICCMap p) {
        this.p = p;
    }

    public CCMetadata metadata() {
        return this.metadata;
    }

    public ICCMap event() {
        return this.event;
    }

    public void setEvent(ICCMap event) {
        this.event = event;
    }

    public ICCMap p() {
        return p;
    }

    public CCProcObject proc() {
        return this.proc;
    }
}
