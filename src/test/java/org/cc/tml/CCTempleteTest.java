package org.cc.tml;

import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.MapContext;
import org.cc.json.JSONObject;
import org.cc.model.CCTemplate;
import org.junit.Test;

public class CCTempleteTest {

	public void jexl_eval_01() {
		JSONObject jo = new JSONObject();
		jo.put("G1", 100);
		jo.put("G2", 200);
		jo.put("G3", 300);
		jo.put("G4", 50);
		Object o = CCTemplate.eval("  x = ((G1 + G2 + G3) * 0.1) + G4 ; x = x +10 ;  ", jo);
		System.out.println(o);
		System.out.println(jo.opt("x"));
	}

	public void jexl_eval_02() {
		JexlExpression expr = CCTemplate.jexl.createExpression("x");

		JSONObject jo = new JSONObject();
		jo.put("G1", 100);
		jo.put("G2", 200);
		jo.put("G3", 300);
		jo.put("G4", 50);
		jo.put("x", "11112233");
		Object o = expr.evaluate(new MapContext(jo));
		System.out.println(o);
		System.out.println(jo.opt("x"));
	}

	@Test
	public void jxlt_eval_01() {
		JexlBuilder builder = new JexlBuilder().silent(false).lexical(true).lexicalShade(true).cache(128).strict(true);
		JexlEngine engine = builder.create();
		JxltEngine jxlt = engine.createJxltEngine();
		JexlContext ctx = new JSONContext();
		//ctx.set("math", java.lang.Math.class);
		JSONObject jo = new JSONObject();
		jo.put("x", 100);
		jo.put("y", 150);
		jo.put("z", 50);
		jo.put("id", "xml_id");
		ctx.set("p",jo);
		ctx.set("jo",jo);
		StringBuilder sb = new StringBuilder();
		sb.append( "$$ var fun = (fld) -> {").append("\n");
		sb.append("<input name=\"${fld.id}\" id=\"${fld.id}\" value=\"\\${fp[${fld.id}]}\" />").append("\n");
		sb.append( "$$ }").append("\n");
		sb.append( "(${p.x},${p.y},${p.z})").append("\n");
		sb.append( "$$ for( var x=0;x<5; x++) {").append("\n");
		sb.append( "<td>${x}</td>").append("\n");
		sb.append( "$$ }").append("\n");
		sb.append( "$$ fun(jo);");
		System.out.println(sb.toString());
		System.out.println("-------------------------------------------------------");
		JxltEngine.Template t = jxlt.createTemplate("$$", new StringReader(sb.toString()));
		final StringWriter strw = new StringWriter();
		t.evaluate(ctx, strw);
		final String output = strw.toString();
		System.out.println(output);
	}
	
	@Test
	public void test_eval_old() {
		String text = "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=json&date=${dp1}&type=ALL";
		//StringBuilder sb = new StringBuilder();
		//sb.append("Hello ${dp1} !!!!");
		JexlBuilder builder = new JexlBuilder().silent(false).lexical(true).lexicalShade(true).cache(128).strict(true);
		JexlEngine engine = builder.create();
		JxltEngine jxlt = engine.createJxltEngine();
		JxltEngine.Template t = jxlt.createTemplate(text);
		JxltEngine.Expression expr = jxlt.createExpression(text);
		JSONContext ctx = new JSONContext();
		ctx.put("dp1", "20240105");
		Object ret = expr.evaluate(ctx);
		System.out.println(ret);
	}
	
}
