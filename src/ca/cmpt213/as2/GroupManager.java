// add only unique element
package ca.cmpt213.as2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
// GroupManager.java
// class that can iterate through different groups
public class GroupManager implements Iterable<Group> {
    private List<Group> groups = new ArrayList<>();

    int getSize(){
        return groups.size();
    }
    public void add(Group group){
        groups.add(group);
    }


    @Override
    public Iterator<Group> iterator() {
        return groups.iterator();
    }
}
