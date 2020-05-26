package ca.cmpt213.as2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
// csvExporter.java
// class that exports PeerFeedback.csv file to specified folder
public class csvExporter {
    private GroupManager GroupArr;
    private File folderOut;
    public csvExporter(GroupManager groupArr, File folderOut) {
        GroupArr = groupArr;
        this.folderOut = folderOut;
    }
    public void Run() throws FileNotFoundException {
        try {
            File outFile = new File(folderOut.getAbsolutePath() + "/PeerFeedback.csv"); // new csv file contained in a directory
            PrintWriter pw = new PrintWriter(outFile);
            pw.println("#Group,Source Student,Target Student,Score,Comment,,Private");
            Integer i = 1;
            for (Group group : GroupArr)
            {
                pw.println("Group "+ i.toString());
                ArrayList<Section> sections = group.getSections();
                for (int j = 0; j < sections.size(); j++){
                    // for each section in sectionsArray, there are 3 parts: 1.Array of normal targets with different sources 2. Student who comment himself 3. evaluation for source student
                    List<targetStudent> arr = sections.get(j).getPart1();
                    targetStudent SelfCommentStudent = sections.get(j).getSelfCommentStudent();
                    targetStudent StudentWithAvg = sections.get(j).getSelfCommentStudent_score();
                    int StudentNumber = 0;
                    for (targetStudent NormalStudent : arr)
                    {
                        StringBuffer s3 = new StringBuffer();
                        for (int k = 0; k < NormalStudent.getComment().length(); k++){
                            if (NormalStudent.getComment().charAt(k) == '\"')
                                s3.append("'");
                            else
                                s3.append(NormalStudent.getComment().charAt(k));
                        }
                        pw.printf(",%s,%s,%.1f,\"%s\",,%n", NormalStudent.getGetCommentedBy(), NormalStudent.getEmail(), NormalStudent.getScore(), s3);
                        StudentNumber++;
                    }
                    StringBuffer s1 = new StringBuffer();
                    StringBuffer s2 = new StringBuffer();
                    for (int k = 0; k < SelfCommentStudent.getComment().length(); k++){
                        if (SelfCommentStudent.getComment().charAt(k) == '\"')
                            s1.append("'");
                        else
                            s1.append(SelfCommentStudent.getComment().charAt(k));
                    }
                    for (int k = 0; k < StudentWithAvg.getConfidential_Comment().length(); k++){
                        if (StudentWithAvg.getConfidential_Comment().charAt(k) == '\"')
                            s2.append("'");
                        else
                            s2.append(StudentWithAvg.getConfidential_Comment().charAt(k));
                    }
                    pw.printf(",-->,%s,%.1f,\"%s\"%n", SelfCommentStudent.getEmail(),SelfCommentStudent.getScore(),s1);
                    pw.printf(",-->,%s,avg %.1f /%d,,,\"%s\",,%n", StudentWithAvg.getEmail(), StudentWithAvg.getScore(), StudentNumber, s2);
                }
                i++;
                pw.print("\n");
            }
            pw.flush();
            pw.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }



}
