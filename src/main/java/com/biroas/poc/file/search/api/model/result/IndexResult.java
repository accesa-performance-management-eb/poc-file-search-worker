package com.biroas.poc.file.search.api.model.result;

public class IndexResult {

    private long indexedDocuments;
    private long deletedDocuments;

    public long getIndexedDocuments() {
        return indexedDocuments;
    }

    public void setIndexedDocuments(long indexedDocuments) {
        this.indexedDocuments = indexedDocuments;
    }

    public long getDeletedDocuments() {
        return deletedDocuments;
    }

    public void setDeletedDocuments(long deletedDocuments) {
        this.deletedDocuments = deletedDocuments;
    }
}
