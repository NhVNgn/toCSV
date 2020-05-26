package ca.cmpt213.as2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
// GroupHolder.java
// Array for different students in a group
public class GroupHolder implements Iterable<String> {
    List<String> Student_list = new ArrayList<>();
    public void add(String sfu_email){
        for (String student_email : Student_list){
            if (sfu_email.equals(student_email))
                return;
        }
        Student_list.add(sfu_email);
    }
    boolean CheckIfStudentBelongToGroup(targetStudent aStudent){
        for (String sfu_email : Student_list){
            if (aStudent.getEmail().equals(sfu_email))
                return true;
        }
        return false;
    }
    @Override
    public Iterator<String> iterator() {
        return Student_list.iterator();
    }
    public void Print(){
        for (String sfu_email : Student_list){
            System.out.println(sfu_email);
        }
    }
}
