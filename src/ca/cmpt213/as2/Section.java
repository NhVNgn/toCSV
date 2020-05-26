package ca.cmpt213.as2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
// Section.java
// in a Section there will be 3 parts:
// 1st: TargetStudent Part1 array that is alphabetically sorted by its sourceStudent order
// 2nd: part that student comment about themselves will be the next one
// 3rd: final part will display source Student avg score and private comment
public class Section implements Iterable<targetStudent>{

    private List<targetStudent> arr = new ArrayList<>();
    private List<targetStudent> Part1 = new ArrayList<>(); // for 1st
    private targetStudent SelfCommentStudent; // for 2nd
    private targetStudent SelfCommentStudent_score; // for 3rd
    public List<targetStudent> getArr() {
        return arr;
    }
    public List<targetStudent> getPart1(){
        return Part1;
    }

    public targetStudent getSelfCommentStudent() {
        return SelfCommentStudent;
    }

    public targetStudent getSelfCommentStudent_score() {
        return SelfCommentStudent_score;
    }
    public void SortBySourceEmail(){
        Comparator<targetStudent> EmailSorter = new Comparator<targetStudent>() {
            @Override
            public int compare(targetStudent t1, targetStudent t2) {
                if (t1.getGetCommentedBy().compareTo(t2.getGetCommentedBy()) < 0)
                    return -1;
                else if (t1.getGetCommentedBy().compareTo(t2.getGetCommentedBy()) == 0)
                    return 0;
                else
                    return 1;
            }
        };
        java.util.Collections.sort(Part1, EmailSorter);
    }

    public void add(targetStudent aStudent){
        arr.add(aStudent);
    }
    public void reArrange(){
        // reArrange section in appropriate format
        // find the self_comment student and the self evaluation with avg score
        Double avg = 0.0;
        Double number_of_student = 0.0;
        for (targetStudent s : arr){
            if (s.getEmail().equals(s.getGetCommentedBy())){
                this.SelfCommentStudent = s;
            }
            else {
                // if it is other students
                Part1.add(s);
                avg += s.getScore();
                number_of_student++;
            }
        }
        avg = avg / number_of_student;
        double rounded_avg = Math.round(avg*10) / 10.0;
        avg = rounded_avg;
        // create the self evaluation with avg score
        try {
            SelfCommentStudent_score = new targetStudent(SelfCommentStudent.getEmail(), SelfCommentStudent.getEmail(), avg, "");
            SelfCommentStudent_score.setConfidential_Comment(SelfCommentStudent.getConfidential_Comment());
        }
        catch (NullPointerException e){
            System.out.println("ERROR: Email typo error");
            final int FAILURE = -1;
            System.exit(FAILURE);
        }
        // Sort part1 by name of sourceStudent
        SortBySourceEmail();

    }

    @Override
    public Iterator<targetStudent> iterator() {
        return Part1.iterator();
    }
}
