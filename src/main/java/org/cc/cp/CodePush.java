package org.cc.cp;

import org.cc.App;

public class CodePush {
    public static void main(String[] args){
        String base = App.base("baphiq");
        
        CodePushModel m = new CodePushModel(base, "D:\\hyweb\\codepush\\baphiq\\20220307收貨管理","webpos");
        System.out.println(m.getCfg().toString(4));
        m.execute();
    }
}
