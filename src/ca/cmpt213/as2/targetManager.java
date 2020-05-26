package ca.cmpt213.as2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

public class targetManager implements Iterable<targetStudent> {
    private List<targetStudent> targets = new ArrayList<>();

    public void remove(targetStudent aTarget){
        targets.remove(aTarget);
    }

    public void add(targetStudent aTarget){
        for (targetStudent targetObject : targets){
            if (targetObject.getComment().equals(aTarget.getComment()))
                return;
        }
        targets.add(aTarget);
    }
    // sort by alphabetic
    public void targetSort(){
        Comparator<targetStudent> targetSorter  = new Comparator<targetStudent>() {
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
        java.util.Collections.sort(targets, targetSorter);
    }

    @Override
    public Iterator<targetStudent> iterator() {
        return targets.iterator();
    }
}
