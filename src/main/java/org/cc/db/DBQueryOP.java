package org.cc.db;

public class DBQueryOP {

    private String op;
    private String cmd;
    private int group;
    
    public DBQueryOP(String op, String cmd, int group){
        this.op = op;
        this.cmd = cmd ;
        this.group = group ;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    
}
