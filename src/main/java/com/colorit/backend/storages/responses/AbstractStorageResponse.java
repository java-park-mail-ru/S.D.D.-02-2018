package com.colorit.backend.storages.responses;

import com.colorit.backend.storages.statuses.IStatus;

public class AbstractStorageResponse<T> {
    private T data;
    private IStatus status;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(IStatus istatus) {
        this.status = istatus;
    }

    public IStatus getStatus() {
        return status;
    }

    public boolean isValid() {
        return !status.isError();
    }
}

