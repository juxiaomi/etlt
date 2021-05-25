package org.etlt.extract;

import java.io.*;
import java.sql.*;

public class DatabaseUtil {

    protected static Object getObject(ResultSet resultSet, int index, int type) throws SQLException {
        switch (type) {
            case Types.TIMESTAMP:
            case Types.TIMESTAMP_WITH_TIMEZONE:
            case -101:
                return resultSet.getTimestamp(index);
            case Types.BLOB:
                return resultSet.getBlob(index);
            case Types.CLOB:
                return resultSet.getClob(index);
            default:
                return resultSet.getObject(index);
        }
    }

    public static void setObject(PreparedStatement statement, int index, Object object) throws SQLException {
        if (object instanceof Blob) {
            InputStream is = ((Blob)object).getBinaryStream();
            try {
                byte[] bytes = inputStreamToByte(is );
                statement.setBytes(index, bytes);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (object instanceof Clob) {
            Reader reader = ((Clob)object).getCharacterStream();
            try {
                statement.setObject(index, readerToCharseq(reader));
                return;
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        } else
            statement.setObject(index, object);
    }

    private static byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();

        return imgdata;
    }

    private static CharSequence readerToCharseq(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for(int ch = reader.read(); ch != -1; ch = reader.read()){
            stringBuilder.append((char) ch);
        }
        return stringBuilder.toString();
    }
}
