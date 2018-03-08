package com.colorit.backend.storages.responses;

import com.colorit.backend.storages.statuses.StorageStatus;

public class CloudinaryStorageResponse<T> extends AbstractStorageResponse<T> {
    @Override
    public StorageStatus getStatus() {
        return (StorageStatus) super.getStatus();
    }
}
