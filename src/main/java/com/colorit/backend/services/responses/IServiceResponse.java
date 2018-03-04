package com.colorit.backend.services.responses;

import com.colorit.backend.services.statuses.IStatus;

public interface IServiceResponse<T> {
    void setStatus(IStatus istatus);

    IStatus getStatus();

    boolean isValid();

    T getData();

    void setData(T data);
}