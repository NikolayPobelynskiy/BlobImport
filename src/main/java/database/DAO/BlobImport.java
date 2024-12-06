package database.DAO;

import database.Entity.BlobImportEntity;
import database.MysqlConnection;
import database.QueryResultHandler.HandlerInterface;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class BlobImport {
    private MysqlConnection connection;

    public BlobImport(MysqlConnection connection) {
        this.connection = connection;
    }

    /**
     * Получить все записи из blob-importв виде списка сущностей
     */
    public List<BlobImportEntity> findAll() {
        HandlerInterface<List<BlobImportEntity>> handler = (result) -> {
            List<BlobImportEntity> queryResultAsList = new ArrayList<BlobImportEntity>();
            while (result.next()) {
                Integer id = result.getInt("id");
                Blob data = result.getBlob("data");
                BlobImportEntity newEntity = new BlobImportEntity(id, data);

                queryResultAsList.add(newEntity);
            }
            return queryResultAsList;
        };

        String sql = "SELECT * FROM `blob-import`";
        List<BlobImportEntity> selectedData = this.connection.getResult(sql, handler);
        return selectedData;
    }
}
