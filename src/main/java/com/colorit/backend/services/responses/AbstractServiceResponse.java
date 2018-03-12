package com.colorit.backend.services.responses;

import com.colorit.backend.services.statuses.IStatus;

public abstract class AbstractServiceResponse<T> {
    private IStatus status;
    private T data;

    AbstractServiceResponse(IStatus istatus) {
        this.status = istatus;
    }

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
        return this.data;
    }

    void setData(T data) {
        this.data = data;
    }
}