package concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FeatureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Boolean> fileUploadFeature = executorService.submit(() -> {
            System.out.println("Uploading file");
            Thread.sleep(5000);
            System.out.println("File uploaded");
            return true;
        });

        while (true) {
            if (fileUploadFeature.isDone()) {
                Future<Boolean> fileReadFeature = executorService.submit(() -> {
                    System.out.println("Reading file as upload is completed");
                    Thread.sleep(500);
                    return true;
                });
                break;
            } else {
                System.out.println("checking file upload");
            }
            Thread.sleep(1000);
        }

        executorService.shutdown();
    }
}
