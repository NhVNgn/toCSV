package ca.cmpt213.as2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
// Group.java
// Class that sort targetStudent list by email and divide them into section
public class Group implements Iterable<targetStudent> {
    private ArrayList<targetStudent> targets = new ArrayList<>();
    private ArrayList<Section> sections = new ArrayList<>();

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void DivideIntoSection(){
        targetStudent next = targets.get(0);
        Section newSection = new Section();
        int i = 0;
        while (i < targets.size()){
            if (i == targets.size() - 1){
                newSection.add(targets.get(i));
                Section toBeAdded = newSection;
                toBeAdded.reArrange();
                sections.add(toBeAdded);
                i++;
            }
            else if (next.getEmail().equals(targets.get(i).getEmail())){
                newSection.add(targets.get(i));
                i++;
            }
            else if (!next.getEmail().equals(targets.get(i).getEmail())){
                Section toBeAdded = newSection;
                toBeAdded.reArrange();
                sections.add(toBeAdded);
                next = targets.get(i);
                newSection = new Section();
            }

        }
    }
    public void add(targetStudent aStudent){
       targets.add(aStudent);
    }
    public ArrayList<targetStudent> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<targetStudent> targets) {
        this.targets = targets;
    }
    public void sortByTargetEmail(){
        Comparator<targetStudent> targetSorter = new Comparator<targetStudent>() {
            @Override
            public int compare(targetStudent t1, targetStudent t2) {
                if (t1.getEmail().compareTo(t2.getEmail()) < 0)
                    return -1;
                else if (t1.getEmail().compareTo(t2.getEmail()) == 0)
                    return 0;
                else
                    return 1;
            }
        };
        targets.sort(targetSorter);
    }

    @Override
    public Iterator<targetStudent> iterator() {
        return targets.iterator();
    }

    public void Print(){
        for (targetStudent aStudent : targets){
            System.out.println(aStudent.getEmail());
        }
    }


}
