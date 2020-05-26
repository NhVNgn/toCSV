package ca.cmpt213.as2;

import com.google.gson.JsonObject;

import java.io.File;
// ErrorHandle.java
// Class for returning message and exiting program with -1 in some cases
public class ErrorHandle {
    public void MissingFieldCheck(JsonObject anObject, String requiredField, File jsonFile) {
        if (anObject.get(requiredField) == null) {
            System.out.println("Error Missing field at: " + requiredField);
            System.out.println("Display problematic file: " + jsonFile.getAbsolutePath());
            final int FAILURE = -1;
            System.exit(FAILURE);
        }
    }
    public void negativeCheck(Double score, File jsonFile){
        if (score < 0){
            System.out.println("Error: score cannot be negative. Displaying problematic file: " + jsonFile.getAbsolutePath());
            final int FAILURE = -1;
            System.exit(FAILURE);
        }
    }
}
