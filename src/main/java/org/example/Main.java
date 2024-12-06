package org.example;

import java.util.List;
import database.DAO.BlobImport;
import database.Entity.BlobImportEntity;
import database.MysqlConnection;

public class Main {
    public static void main(String[] args) {
        List<BlobImportEntity> selectedData = (new BlobImport(new MysqlConnection())).findAll();

        BlobImportEntity entity = selectedData.get(0);
        System.out.println(entity.dataAsString());
    }
}