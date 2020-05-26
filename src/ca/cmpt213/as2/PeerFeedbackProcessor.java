// Student: Nhan-Vy Nguyen
// SFU ID: 301384945
// PeerFeedbackProcessor.java
// main class for search and deserialize json files
package ca.cmpt213.as2;

import com.google.gson.*;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class PeerFeedbackProcessor {
    public static void recursiveSearch(List<File> jsonArray, File folder, FileFilter filter) {
        if (folder.isDirectory()) {
            for (File subFile : folder.listFiles(filter)) {
                jsonArray.add(subFile);
            }
            for (File subFile : folder.listFiles()) {
                if (subFile.isDirectory()) {
                    recursiveSearch(jsonArray, subFile, filter);
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Gson gson = new Gson();
        ErrorHandle errorHandle = new ErrorHandle();
        // Process arguments from command lines
        if (args.length != 2) {
            System.out.println("Error: Program expects two arguments");
            final int FAILURE = -1;
            System.exit(FAILURE);
        }
        File folderIn = new File(args[0]);
        File folderOut = new File(args[1]);
        if (!folderIn.exists() || !folderOut.exists()) {
            if (!folderIn.exists())
                System.out.println("Error: input folder does not exist");
            if (!folderOut.exists())
                System.out.println("Error: output folder does not exist");
            final int FAILURE = -1;
            System.exit(FAILURE);
        }

        /* Create an anonymous class (filter) */
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".json");
            }
        };
        List<File> jsonArray = new ArrayList<>();
        recursiveSearch(jsonArray, folderIn, filter);
        // a jsonFile array is now created
        // Initialize a GroupArray, TargetArray, SourceArray
        GroupManager GroupArr = new GroupManager();
        targetManager tarArr = new targetManager();
        GroupHolderManager aHolderManager = new GroupHolderManager();
        // extracting json file elements
        if (jsonArray.size() == 0) {
                System.out.println("Error: No json file are found");
            final int FAILURE = -1;
            System.exit(FAILURE);
        }
        for (File jsonFile : jsonArray) {
            try {
                int numberOfMembers = 0;
                ArrayList<Double> scoreArray = new ArrayList<>();
                GroupHolder aHolder = new GroupHolder();
                boolean isDetected = false; // in each file, email of sourceStudent is detected only one time
                String SourceStudent_email = "blank";
                JsonElement fileElement = JsonParser.parseReader(new FileReader(jsonFile.getAbsolutePath()));
                JsonObject fileObject = fileElement.getAsJsonObject();
                errorHandle.MissingFieldCheck(fileObject, "confidential_comments", jsonFile);
                String confidential_comment = fileObject.get("confidential_comments").getAsString();
                // working on json object:
                // create a json array of students
                errorHandle.MissingFieldCheck(fileObject, "group", jsonFile);
                JsonArray jsonArrOfStudent = fileObject.get("group").getAsJsonArray();
                // working on each students
                for (JsonElement studentElement : jsonArrOfStudent) {
                    numberOfMembers++;
                    JsonObject studentObject = studentElement.getAsJsonObject();
                    errorHandle.MissingFieldCheck(studentObject, "sfu_email", jsonFile);
                    errorHandle.MissingFieldCheck(studentObject, "contribution", jsonFile);
                    // Extract data from each student feedback
                    String sfu_email = studentObject.get("sfu_email").getAsString().trim();
                    JsonObject contributionObject = studentObject.get("contribution").getAsJsonObject();
                    errorHandle.MissingFieldCheck(contributionObject, "comment", jsonFile);
                    errorHandle.MissingFieldCheck(contributionObject, "score", jsonFile);
                    String message = contributionObject.get("comment").getAsString();
                    Double score = contributionObject.get("score").getAsDouble();
                    scoreArray.add(score);
                    errorHandle.negativeCheck(score, jsonFile);
                    // Create an target object
                    if (!isDetected) {
                        SourceStudent_email = sfu_email;
                        targetStudent aTarget = new targetStudent(sfu_email, SourceStudent_email, score, message);
                        aTarget.setConfidential_Comment(confidential_comment);
                        aHolder.add(aTarget.getEmail());
                        tarArr.add(aTarget);
                        isDetected = true;
                    } else {
                        targetStudent aTarget = new targetStudent(sfu_email, SourceStudent_email, score, message);
                        aHolder.add(aTarget.getEmail());
                        tarArr.add(aTarget);
                    }
                }
                Double sum = 0.0;
                for (Double score : scoreArray) {
                    sum += score;
                }
                if (Math.abs(sum - (20 * numberOfMembers)) >= 0.1) {
                    System.out.println("Error: Sum of score in the files is not (20 * student number) with 0.1 of difference");
                    final int FAILURE = -1;
                    System.exit(FAILURE);
                }


                aHolderManager.add(aHolder);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (GroupHolder aHolder : aHolderManager) {
            Group newGroup = new Group();
            for (targetStudent aStudent : tarArr) {
                if (aHolder.CheckIfStudentBelongToGroup(aStudent)) {
                    newGroup.add(aStudent);
                }
            }
            newGroup.sortByTargetEmail();
            GroupArr.add(newGroup);
        }
        for (Group g : GroupArr) {
            g.DivideIntoSection(); // DivideIntoSection() will call reArrage() to arrange every part of a section before adding it into Section Array
        }

        // Check if a student is mentioned in different group
        for (Group g1 : GroupArr) {
            for (Group g2 : GroupArr) {
                if (g1 != g2)
                    for (targetStudent g1t : g1) {
                        for (targetStudent g2t : g2) {
                            if (g1t.getEmail() == g2t.getEmail()) {
                                System.out.println("Error: Student: " + g1t.getEmail() + "  is mentioned in other group");
                                final int FAILURE = -1;
                                System.exit(FAILURE);
                            }
                        }
                    }
            }
        }
        csvExporter exporter = new csvExporter(GroupArr, folderOut);
        exporter.Run();


    }
}
