package org.cc.model;

import org.cc.CCTest;
import org.junit.Test;

public class CCModuleTest {

    @Test
    public void test_cmdString() {
        CCCmdModuleString cmd = CCCmdModuleString.newInstance("abc");
        // cmd = new CCCmdModuleJAString("[abc]");
        System.out.println("===== mid :" + cmd.mid());
        System.out.println("===== aid :" + cmd.aid());
        cmd = CCCmdModuleString.newInstance("{mid:abc,aid:def}");
        System.out.println("===== mid :" + cmd.mid());
        System.out.println("===== aid :" + cmd.aid());
    }

    @Test
    public void test_loadModule() {
        try (CCProcObject proc = new CCProcObject(CCTest.project + "\\stock", false);) {
            ICCModule md = proc.module("twse");
            System.out.println(md.cfg().toString(4));
            md.dbFields("mstock").forEach(o->{
                System.out.println(o);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
