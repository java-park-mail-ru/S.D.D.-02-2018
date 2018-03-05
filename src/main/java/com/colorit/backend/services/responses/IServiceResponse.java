package com.colorit.backend.services.responses;

import com.colorit.backend.services.statuses.IStatus;

public abstract class IServiceResponse<T> {
    IStatus status;
    T data;

    void setStatus(IStatus istatus) {
        this.status = istatus;
    }

    IStatus getStatus() {
        return this.status;
    }

    boolean isValid() {
        return true;
    }

    T getData() {
        return null;
    }

    void setData(T data) {
        this.data = data;
    }
}