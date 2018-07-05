package com.biroas.poc.file.search.worker.service.index;

import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.file.FileAttributes;
import com.biroas.poc.file.search.api.model.file.FileType;
import com.biroas.poc.file.search.api.model.result.IndexResult;
import com.biroas.poc.file.search.worker.jms.FileIndexRequestSender;
import com.biroas.poc.file.search.worker.service.index.client.FileIndexRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;

@Service
public class FileIndexService {

    private final Logger logger = LoggerFactory.getLogger(FileIndexService.class);
    @Inject
    FileIndexRequestSender fileIndexRequestSender;
    @Inject
    FileIndexRestClient fileIndexRestClient;
    @Value("${file.index.extractContentFileTypes}")
    String[] extractContentFileTypes;

    public IndexResult indexDirectory(String path, boolean recursive, boolean useActiveMQ) throws IOException {
        IndexResult indexResult = new IndexResult();

        java.io.File folder = new java.io.File(path);
        java.io.File[] listOfFiles = folder.listFiles();
        long count = 0;

        if (listOfFiles == null) {
            return indexResult;
        }

        for (java.io.File diskFile : listOfFiles) {
            File file = new File();
            file.setSystemName(System.getProperty("user.name") + "-" + System.getProperty("os.name"));
            file.setFileName(diskFile.getName());
            file.setParentDirectory(diskFile.getParentFile().getAbsolutePath());
            file.setDirectory(diskFile.isDirectory());
            file.setId(getId(file));

            if (recursive && file.isDirectory()) {
                count += indexDirectory(file.getParentDirectory() + java.io.File.separator + file.getFileName(), true, useActiveMQ)
                        .getIndexedDocuments();
            }

            if (!file.isDirectory()) {
                file.setFileAttributes(getFileAttributes(diskFile));
                file.setFileType(getFileType(diskFile));

                if (Arrays.asList(extractContentFileTypes).contains(file.getFileType().getExtension())) {
                    file.setFileContent(extractFileContent(diskFile.toPath()));
                }
            }

            if (useActiveMQ) {
                fileIndexRequestSender.sendFileIndexRequest(file);
            } else {
                fileIndexRestClient.indexFile(file);
            }
            count++;
        }

        indexResult.setIndexedDocuments(count);
        return indexResult;
    }

    public void deleteAllFiles() {
        fileIndexRestClient.deleteAllFiles();
    }

    private FileAttributes getFileAttributes(java.io.File file) throws IOException {
        FileAttributes fileAttributes = new FileAttributes();
        BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

        fileAttributes.setCreationDate(new Date(basicFileAttributes.creationTime().toMillis()));
        fileAttributes.setLastModifiedDate(new Date(basicFileAttributes.lastModifiedTime().toMillis()));
        fileAttributes.setLastAccessDate(new Date(basicFileAttributes.lastAccessTime().toMillis()));
        fileAttributes.setSize(basicFileAttributes.size());
        return fileAttributes;
    }

    private FileType getFileType(java.io.File file) {
        FileType fileType = new FileType();
        String extension = com.google.common.io.Files.getFileExtension(file.getName());
        fileType.setExtension(extension);
        return fileType;
    }

    private String extractFileContent(Path path) {
        try {
            StringBuilder stringBuilder = new StringBuilder();

            Files.readAllLines(path)
                    .stream()
                    .forEach(line -> stringBuilder.append(line).append(" "));

            return stringBuilder.toString();
        } catch (Exception ex) {
            logger.error("Exception when extracting file content: {}", path.getFileName());
        }

        return null;
    }

    private String getId(File file) { //TODO consider to replace with checksum, study best way to generate id and avoid collision
        int hashCode = (file.getSystemName() + file.getParentDirectory() + file.getFileName())
                .replaceAll("[^A-Za-z0-9]", "").toLowerCase().hashCode();
        return String.valueOf(hashCode);
    }
}
