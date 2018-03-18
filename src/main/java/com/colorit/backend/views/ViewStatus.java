package com.colorit.backend.views;

import java.util.ArrayList;

public class ViewStatus {
    private final ArrayList<ViewStatusCode> arrayOfErrors = new ArrayList<>();

    public boolean isNotValid() {
        return !arrayOfErrors.isEmpty();
    }

    public ArrayList<ViewStatusCode> getErrors() {
        return arrayOfErrors;
    }

    public void addStatusCode(ViewStatusCode status) {
        arrayOfErrors.add(status);
    }
}