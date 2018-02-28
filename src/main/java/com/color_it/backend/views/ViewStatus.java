package  com.color_it.backend.views;

import java.util.ArrayList;

public class ViewStatus {
    private final ArrayList<ViewStatusCode> arrayOfErrors;

    ViewStatus() {
        arrayOfErrors = new ArrayList<>();
    }

    public boolean isValid() {
        return arrayOfErrors.isEmpty();
    }

    public ArrayList<ViewStatusCode> getErrors() {
        return arrayOfErrors;
    }

    public void addStatusCode(ViewStatusCode status) {
        arrayOfErrors.add(status);
    }
}