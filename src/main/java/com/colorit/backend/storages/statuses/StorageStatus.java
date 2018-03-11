package com.colorit.backend.storages.statuses;

public enum StorageStatus implements IStatus {
    OK_STATE(0),
    ERROR_STATE(1);

    private Integer id;

    StorageStatus(Integer id) {
        this.id = id;
    }

    @Override
    public boolean isError() {
        return id != 0;
    }
}
