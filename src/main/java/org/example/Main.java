package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.DAO.BlobImport;
import database.Entity.BlobImportEntity;
import database.MysqlConnection;

public class Main {
    public static void main(String[] args) {
        List<BlobImportEntity> selectedData = (new BlobImport(new MysqlConnection())).findAll();

        String substring = "searchedSubStr";
        int numThreads = Runtime.getRuntime().availableProcessors(); // Используем количество доступных ядер
        System.out.println(selectedData.size());

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunkSize = selectedData.size() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? selectedData.size() : start + chunkSize;
            executor.submit(() -> searchSubstring(selectedData.subList(start, end), substring));
            //System.out.println(executor.submit(() -> searchSubstring(selectedData.subList(start, end), substring)));
        }

        executor.shutdown();
    }

    private static Object searchSubstring(List<BlobImportEntity> blobImportEntities, String substring) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < blobImportEntities.size(); i++) {
            System.out.println(blobImportEntities.get(i).dataAsString());
            if (blobImportEntities.get(i).dataAsString().contains(substring)) {
                System.out.println(blobImportEntities.get(i).getId());
                indices.add(i);
            }
        }
        return indices;
    }
}