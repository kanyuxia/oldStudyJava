package array;

import java.util.Comparator;

/**
 * Created by kanyuxia on 2017/1/13.
 */
public class PeopleComparator implements Comparator<People>{

    @Override
    public int compare(People o1, People o2) {
        return o1.getAge()>o2.getAge() ? -1 : (o1.getAge() == o2.getAge() ? 0 : 1);
    }

}
