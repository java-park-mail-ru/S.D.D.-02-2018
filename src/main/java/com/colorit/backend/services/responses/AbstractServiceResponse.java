package com.colorit.backend.services.responses;

// TODO abstract has only statusCode, maybe
// TODO add scalar type to store data
// TODO Or there maybe can add work with data, then
// TODO in responseMaker check entity, entities, Data, Or make Scalar entity;
// TODO after that can cange update user and update photo, after tests
// TODO after can create new project with its pom and work with webSockets

import com.colorit.backend.services.statuses.IStatus;

public interface AbstractServiceResponse<T> {
    void setStatus(IStatus istatus);

    IStatus getStatus();

    boolean isValid();

    T getData();

    void setData(T data);
}