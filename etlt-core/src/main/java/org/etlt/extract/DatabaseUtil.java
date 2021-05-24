package org.etlt.extract;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.*;

public class DatabaseUtil {

    protected static Object getObject(ResultSet resultSet, int index, int type) throws SQLException {
        switch (type) {
            case Types.TIMESTAMP:
            case Types.TIMESTAMP_WITH_TIMEZONE:
            case -101:
                return resultSet.getTimestamp(index);
            case Types.BLOB:
                return resultSet.getBlob(index).getBinaryStream();
            case Types.CLOB:
                return resultSet.getClob(index).getCharacterStream();
            default:
                return resultSet.getObject(index);
        }
    }

    public static void setObject(PreparedStatement statement, int index, Object object) throws SQLException {
        if (object instanceof Blob) {
            statement.setBlob(index, (InputStream) object);
            return;
        }
        if (object instanceof Clob) {
            statement.setClob(index, (Reader) object);
            return;
        } else
            statement.setObject(index, object);
    }
}
