package org.cc.model.field;
import com.google.common.reflect.ClassPath;
import java.util.HashMap;
import java.util.Map;
import org.cc.IAProxyClass;
import org.cc.json.JSONObject;

public class CCFieldUtils {

    private static Map<String, Class<?>> _cache;



    private static Map<String, Class<?>> cache() {
        if (_cache == null) {
            _cache = new HashMap<String, Class<?>>(32);
            scanPackage(_cache, "org.cc.model.field");
        }
        return _cache;
    }

    private static void scanPackage(Map<String, Class<?>> c, String string) {
        try {
            ClassLoader load = Thread.currentThread().getContextClassLoader();

            ClassPath classpath = ClassPath.from(load);
            classpath.getTopLevelClasses("org.cc.model.field").stream().map((classInfo) -> classInfo.load()).forEach((cls) -> {
                IAProxyClass a = (IAProxyClass) cls.getAnnotation(IAProxyClass.class);
                if (a != null) {
                    c.put(a.id(), cls);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static ICCField newField(String classId) throws Exception {
        Class<?> cls = cache().get("field." + classId);
        cls = (cls == null) ? cache().get("field.obj") : cls;
        return (ICCField) cls.getDeclaredConstructor().newInstance();
    }

    public static ICCField newInstance(JSONObject cm) throws Exception {
        ICCField fld = newField(cm.optString("dt"));
        if (fld != null) {
            fld.__init__(cm);
        }
        return fld;
    }

    public static JSONObject mix(JSONObject mFields, String line) {
        if (line.charAt(0) == '{') {
            return mix(mFields, new JSONObject(line));
        } else {
            JSONObject ret = new JSONObject();
            String[] items = line.split(":");
            JSONObject p = mFields.optJSONObject(items[0]);
            if (p != null) {
                ret.putAll(p);
            }
            if (items.length > 1) {
                ret.put("alias", items[1]);
            }
            return ret;
        }
    }

    public static JSONObject mix(JSONObject mFields, Object o) {
        if (o instanceof JSONObject) {
            return mix(mFields, (JSONObject) o);
        } else if (o instanceof String) {
            return mix(mFields, (String) o);
        }
        return null;
    }

    public static JSONObject mix(JSONObject mFields, JSONObject m) {
        JSONObject ret = new JSONObject();
        JSONObject p = mFields.optJSONObject(m.optString("id"));
        if (p != null) {
            ret.putAll(p);
        }
        m.forEach((k, v) -> {
            ret.put(k, v);
        });
        return ret;
    }

}
