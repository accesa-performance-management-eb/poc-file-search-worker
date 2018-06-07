package com.biroas.poc.file.search.worker;

import com.biroas.poc.file.search.worker.service.index.FileIndexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableAutoConfiguration
public class FileSearchWorkerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(FileSearchWorkerApplication.class);
    }

    @Inject
    FileIndexService fileIndexService;

    ExecutorService executorService;


    public void run(String... strings) throws Exception {
        executorService = Executors.newCachedThreadPool();

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {

            printOptions();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    indexDirectory(scanner, false);
                    break;
                case "2":
                    indexDirectory(scanner, true);
                    break;
                case "3":
                    deleteAllFiles(scanner);
                    break;
                case "4":
                    getWorkerStatus(scanner);
                    break;
                case "5":
                    exit = true;
                    break;
                default:
                    System.err.println("Invalid option");
            }
        }
    }

    private void printOptions() {
        System.out.println("---- Choose option ------");
        System.out.println("\n");
        System.out.println("1. Index directory (using REST Client)");
        System.out.println("\n");
        System.out.println("2. Index directory (using ActiveMQ)");
        System.out.println("\n");
        System.out.println("3. Delete all files");
        System.out.println("\n");
        System.out.println("4. Get worker status");
        System.out.println("\n");
        System.out.println("5. Exit program");
        System.out.println("\n");
        System.out.println("----**------");


    }

    private void indexDirectory(Scanner scanner, boolean useActiveMQ) {

        try {
            System.out.println("Enter directory path:");
            String directory = scanner.nextLine();
            if (new File(directory).exists()) {

                startIndexingThread(directory, useActiveMQ);

            } else {
                System.err.println("Invalid directory");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    private void startIndexingThread(String directory, boolean useActiveMQ) {
        Runnable runnable = () -> {
            try {
                    fileIndexService.indexDirectory(directory, true, useActiveMQ);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());

            }
        };
        executorService.execute(runnable);
    }

    private void deleteAllFiles(Scanner scanner) {
        try {
            System.out.println("Are you sure you want to delete all files?");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                fileIndexService.deleteAllFiles();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void getWorkerStatus(Scanner scanner) {
        System.out.println("\n");
        System.out.println("-- Tasks in progress: " + ((ThreadPoolExecutor) executorService).getActiveCount());
        System.out.println("-- Tasks completed: " + ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
        scanner.nextLine();
    }
}
