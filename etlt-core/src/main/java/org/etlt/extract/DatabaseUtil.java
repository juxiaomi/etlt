package org.etlt.extract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DatabaseUtil {

    protected static Object getObject(ResultSet resultSet, int index, int type) throws SQLException {
        if(type == Types.TIMESTAMP){
            return resultSet.getTimestamp(index);
        }
        else return resultSet.getObject(index);
    }
}
