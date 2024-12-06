package com.blob.decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.zip.InflaterInputStream;

public class TextBlobDecoder {
    public String decode(byte[] blobBytes) {
        byte[] decodedBytes = base64Decode(blobBytes);
        byte[] unzip = gzcompress(decodedBytes);

        return new String(unzip);
    }
    
    public String decode(Blob blob) {
        byte[] blobBytes = new byte[0];
        try {
            blobBytes = blob.getBytes(1, (int) blob.length());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.decode(blobBytes);
    }

    private byte[] base64Decode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    /**
     *
     * !!! ZLIB не то же самое, что и gzip-сжатие, которое включает в себя некоторые данные заголовка
     *
     * @param compressed
     * @return
     */
    private byte[] gzcompress(byte[] compressed) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
            InflaterInputStream iis = new InflaterInputStream(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = iis.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            iis.close();
            bis.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
