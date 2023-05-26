package org.cc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

import org.cc.ICCType;
import org.cc.json.CCJSON;
import org.cc.json.JSONObject;

public class BiFetchFields implements BiFunction<ICCModule, Object, List<CCField>> {
	
	@Override
	public List<CCField> apply(ICCModule cm, Object o) {
		List<CCField> ret = new ArrayList<>();
		if (o instanceof Collection) {
			proc_jo(cm, ret, (Collection) o);
		} else {
			proc_string(cm, ret, (String) o);
		}
		return ret;
	}

	private void proc_jo(ICCModule cm, List<CCField> ret, Collection items) {
		for (Object item : items) {
			ret.add(getField(cm, item));
		}
	}

	private void proc_string(ICCModule cm, List<CCField> ret, String line) {
		String[] items = line.split(",");
		for (String item : items) {
			ret.add(getField(cm, item));
		}
	}

	public CCField getField(ICCModule cm, Object item) {
		if (item instanceof JSONObject) {
			return getFromJO(cm, (JSONObject) item);
		} else if (item instanceof String) {
			String line = item.toString();
			if (line.length() > 0 && line.charAt(0) == '{') {
				return getFromJO(cm, CCJSON.line(line));
			} else {
				String[] items = ((String) line).split(":");
				String id = items[0].trim();
				String args = (items.length > 1) ? items[1].trim() : null;
				return getFromString(cm, id, args);
			}
		}
		return null;
	}

	public CCField getFromJO(ICCModule cm, JSONObject jo) {
		String id = jo.optString("id");
		CCField fld = cm.fldMap().get(jo.optString("id"));
		JSONObject cfg = (fld != null) ? new JSONObject(fld.cfg()) : new JSONObject();
		cfg.putAll(jo);
		return newInstance(cm,cfg);
	}

	public CCField getFromString(ICCModule cm, String id, String alias) {
		CCField fld = cm.fldMap().get(id);
		CCField afld = cm.fldMap().get(alias);
		JSONObject cfg = (fld != null) ? new JSONObject(fld.cfg()) : new JSONObject();
		cfg.put("id", id);
		cfg.put("alias", alias);
		cfg.put("$fld", (afld!=null) ? afld.cfg() : new JSONObject());
		return newInstance(cm,cfg);
	}
	
	public CCField newInstance(ICCModule cm,JSONObject cfg) {
		CCField fld = new CCField();
		String dt = cfg.optString("dt","string");
		ICCType<?> type = cm.proc().db().types().type(dt);
		cfg.put("type", type );
		fld.__init__(cfg);
		return fld;
	}
}
