package database.Entity;

import com.blob.decoder.TextBlobDecoder;

import java.sql.Blob;
import java.sql.SQLException;

public class BlobImportEntity {
    private Integer id;
    private Blob data;
    public BlobImportEntity(Integer id, Blob data) {
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public String dataAsString() {
        return (new TextBlobDecoder()).decode(this.data);
    }
}
