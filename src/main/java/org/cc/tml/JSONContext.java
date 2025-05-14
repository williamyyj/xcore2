package org.cc.tml;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl3.JexlContext;
import org.cc.json.JSONObject;

public class JSONContext extends JSONObject implements JexlContext {

	private static final long serialVersionUID = 1L;

	public JSONContext() {
        super();
    }

    public JSONContext(final Map<String, Object> vars) {
        super(vars);
    }
    
    @Override
    public boolean has(final String name) {
        return super.has(name);
    }

    @Override
    public Object get(final String name) {
        return super.get(name);
    }

    @Override
    public void set(final String name, final Object value) {
    	super.put(name,value);
    }

    /**
     * Clears all variables.
     */
    public void clear() {
    	super.clear();
    }
}
