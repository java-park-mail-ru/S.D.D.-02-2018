package com.colorit.backend.storages.statuses;

public enum StorageStatus implements IStatus {
    OK_STATE(0, null),
    ERROR_STATE(1, "server_error"),
    SERVICE_ERROR_STATE(3, "server_error"),
    INCORRECT_FILE_TYPE_STATE(4, "invalid_file_type");

    private Integer id;
    private String message;

    StorageStatus(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean isError() {
        return id != 0;
    }
}
