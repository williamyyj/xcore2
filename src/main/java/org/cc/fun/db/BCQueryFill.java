package org.cc.fun.db;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.BiConsumer;
import org.cc.db.QueryField;

/**
 * 
 */
public class BCQueryFill implements BiConsumer<PreparedStatement, List<QueryField> > {

    @Override
    public void accept(PreparedStatement ps, List<QueryField> fields) {
 
        
    }
    
}
