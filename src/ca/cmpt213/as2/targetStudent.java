package ca.cmpt213.as2;

import java.util.ArrayList;
// targetStudent.java
// Class for Student whose name appeared at target Column
public class targetStudent {
    private String email;
    private Double score;
    private String comment;
    private String getCommentedBy;
    private String confidential_Comment;

    public void setConfidential_Comment(String confidential_Comment) {
        this.confidential_Comment = confidential_Comment;
    }

    public String getConfidential_Comment() {
        return confidential_Comment;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public targetStudent(String email, String getCommentedBy, Double score, String comment) {
        this.email = email;
        this.getCommentedBy = getCommentedBy;
        this.score = score;
        this.comment = comment;
    }


    public String getEmail() {
        if (email == null)
        {
            System.out.println("Null exception error !");
            final int FAILURE = -1;
            System.exit(FAILURE);
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGetCommentedBy() {
        return getCommentedBy;
    }

    public void setGetCommentedBy(String getCommentedBy) {
        this.getCommentedBy = getCommentedBy;
    }
    public void Print(){
        System.out.println(email);
    }

}
