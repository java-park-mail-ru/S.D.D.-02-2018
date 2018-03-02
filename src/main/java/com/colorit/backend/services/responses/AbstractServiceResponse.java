package com.colorit.backend.services.responses;

// TODO abstract has only statusCode, maybe
// TODO add scalar type to store data
// TODO Or there maybe can add work with data, then
// TODO in responseMaker check entity, entities, Data, Or make Scalar entity;
// TODO after that can cange update user and update photo, after tests
// TODO after can create new project with its pom and work with webSockets

import com.colorit.backend.services.statuses.IStatus;

public interface AbstractServiceResponse<T> {
    void setStatus(IStatus iStatus);
    IStatus getStatus();
    boolean isValid();
    T getData();
    void setData(T data);
}
//
//public class AbstractServiceResponse<T> {
//    private AbstractEntity entity;
//    @SuppressWarnings("unused")
//    private List<AbstractEntity> entities;
//
//    private ScalarEntity<T> data;
//
//    public T getData() {
//        return data.getData();
//    }
//
//    public void setData(ScalarEntity<T> data) {
//        this.data = data;
//    }
////    private ScalarEntity scalarEntityntity;
//
//    public AbstractEntity getEntity() {
//        return entity;
//    }
//
//    public void setEntity(AbstractEntity entity) {
//        this.entity = entity;
//    }
//
//}
