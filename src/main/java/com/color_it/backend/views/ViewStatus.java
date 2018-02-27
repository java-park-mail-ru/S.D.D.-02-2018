package  com.color_it.backend.views;

import java.util.ArrayList;

public class ViewStatus {
    final private ArrayList<ViewStatusCode> arrayOfErrors;

    ViewStatus() {
        arrayOfErrors = new ArrayList<>();
    }

    public boolean isValid() {
        return arrayOfErrors.size() == 0;
    }

    public ArrayList<ViewStatusCode> getErrors() {
        return arrayOfErrors;
    }

    public void addStatusCode(ViewStatusCode status) {
        arrayOfErrors.add(status);
    }
}