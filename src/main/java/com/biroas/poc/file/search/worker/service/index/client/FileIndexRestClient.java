package com.biroas.poc.file.search.worker.service.index.client;

import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.api.model.result.IndexResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@Service
public class FileIndexRestClient {

    @Inject
    private RestTemplate restTemplate;
    @Value("${file-search-api.service.url}")
    private String serviceUrl;


    public IndexResult indexFile(File file) {
        return restTemplate.postForObject(serviceUrl, file, IndexResult.class);
    }

    public void deleteAllFiles() {
        restTemplate.delete(serviceUrl);
    }
}
