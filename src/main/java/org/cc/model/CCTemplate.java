package org.cc.model;

import java.util.Map;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.MapContext;
import org.cc.json.JSONObject;
import org.cc.tml.JSONContext;




/**
 *
 * @author william
 */
public class CCTemplate {
	
	public static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
	public static final JexlBuilder builder = new JexlBuilder().silent(false).lexical(true).lexicalShade(true).cache(512).strict(true);

	
    public static Object eval(String text , Map<String,Object> m){
    	JexlScript  script =     jexl.createScript(text);
    	
    	JexlContext context = new MapContext(m);
    	Object o = script.execute(context);
		return o;
    }
    
    public static Object eval(String text , JSONObject jo){
    	JxltEngine jxlt =builder.create().createJxltEngine();
    	JxltEngine.Expression expr = jxlt.createExpression(text);
    	JSONContext jctx = new JSONContext(jo);
		return expr.evaluate(jctx);
    }
        
}
