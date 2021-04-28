package org.cc.fun.proc.dao;

import java.util.function.BiFunction;
import org.cc.IAProxyClass;
import org.cc.model.CCCmdModuleString;
import org.cc.model.CCProcObject;
import org.cc.model.ICCModule;

@IAProxyClass(id="dao.add")
public class BiDaoRowAdd implements BiFunction<CCProcObject,String,Long>{

    @Override
    public Long apply(CCProcObject proc, String cmdString) {
        ICCModule md = proc.module(cmdString);

        return null;
    }
    
}
