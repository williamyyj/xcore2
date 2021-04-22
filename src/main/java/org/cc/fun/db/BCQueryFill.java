package org.cc.fun.db;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.BiConsumer;
import org.cc.db.DBCmdField;

/**
 * 
 */
public class BCQueryFill implements BiConsumer<PreparedStatement, List<DBCmdField> > {

    @Override
    public void accept(PreparedStatement ps, List<DBCmdField> fields) {
 
        
    }
    
}
