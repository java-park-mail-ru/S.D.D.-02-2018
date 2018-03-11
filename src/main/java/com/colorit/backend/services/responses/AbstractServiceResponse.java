package com.colorit.backend.services.responses;

import com.colorit.backend.entities.IEntity;
import com.colorit.backend.services.statuses.IStatus;

public abstract class AbstractServiceResponse {
    private IStatus status;
    private IEntity data;

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

    IEntity getData() {
        return this.data;
    }

    void setData(IEntity data) {
        this.data = data;
    }
}