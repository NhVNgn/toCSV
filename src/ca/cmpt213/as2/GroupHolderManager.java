package ca.cmpt213.as2;

import java.util.*;
// GroupHolderManager.java
// array for different groupHolder
public class GroupHolderManager implements Iterable<GroupHolder>{
    List<GroupHolder> holderArr = new ArrayList<GroupHolder>();

    public void add(GroupHolder aHolder){
        Set<String> setOfParam = new HashSet<String>();
        for (String aString : aHolder){
            setOfParam.add(aString);
        }
        boolean flag = false;
        for (GroupHolder groupHolder : holderArr)
        {
            Set<String> setOfThis = new HashSet<String>();
            // compare if aHolder is equal to any element in holderArr if equal means--> 2 set has same String element
            for (String aString : groupHolder){
                setOfThis.add(aString);
            }
            if (setOfParam.equals(setOfThis))
                flag = true;
        }
        if (!flag)
            holderArr.add(aHolder);
    }
    @Override
    public Iterator<GroupHolder> iterator() {
        return holderArr.iterator();
    }
}
