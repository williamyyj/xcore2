package org.cc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import lombok.extern.log4j.Log4j2;


/**
 *
 * @author william
 */
@Log4j2
public class ProcBase {

    public void __release(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
    }

    public void __release(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
    }

    public void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
    }

}
