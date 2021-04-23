package org.cc.fun.proc.dao;

import java.util.function.BiFunction;
import org.cc.IAProxyClass;
import org.cc.model.CCProcObject;

@IAProxyClass(id="dao.add")
public class BiDaoRowAdd implements BiFunction<CCProcObject,String,Long>{

    @Override
    public Long apply(CCProcObject proc, String cmd) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
