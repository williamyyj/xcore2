package org.cc.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.cc.json.JSONObject;


/**
 * @author william
 * @param <E>
 */
public interface ICCDataSource<E>{
    public void init(JSONObject jo) ; 
    public String id();
    public E getDataSource();
    public Connection getConnection()  throws SQLException ;
    public void close()   throws Exception  ;
    public String info();
}
