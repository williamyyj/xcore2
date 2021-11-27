package org.cc.model;

import java.util.Map;

import org.mvel2.templates.TemplateRuntime;

/**
 *
 * @author william
 */
public class CCTemplate {
    public static Object eval(String expression , Map<String,Object> m){
        return TemplateRuntime.eval(expression,m);
    }
}
