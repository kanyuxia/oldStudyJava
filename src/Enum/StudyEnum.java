package Enum;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by kanyuxia on 2017/3/6.
 */
public class StudyEnum {
    public static void main(String[] args){
//        for(int index = 0; index < 10; index++){
//            Week week = Week.getWeek(index);
//            System.out.println(week);
//        }
//        EnumSet<Week> set = EnumSet.noneOf(Week.class);
//        System.out.println(set);
//        set.add(Week.MONDAY);
//        System.out.println(set);
//        set.addAll(EnumSet.of(Week.TUESDAY,Week.WEDNESDAY));
//        System.out.println(set);
//        set = EnumSet.allOf(Week.class);
//        System.out.println(set);
//        System.out.println(set.contains(Week.FRIDAY));
//        System.out.println(set.size());
//        System.out.println(set.remove(Week.FRIDAY));
//        System.out.println(set);
        Class c1 = Week.class;
        for(Class c : c1.getInterfaces()){
            System.out.println(c.getName());
        }
        Set<String> weekSet = new TreeSet<>();
        Set<String> methods = new TreeSet<>();
        for(Method method : c1.getMethods()){
            weekSet.add(method.getName());
//            if(method.getName().equals("values")){
//                System.out.println(method);
//            }
        }
        for(Method method : c1.getDeclaredMethods()){
            methods.add(method.getName());
        }
        System.out.println(weekSet);
        System.out.println(methods);

        Class c2 = c1.getSuperclass();
        System.out.println(c2.getName());
        for(Class c : c2.getInterfaces()){
            System.out.println(c.getName());
        }
        Set<String> superSet = new TreeSet<>();
        for(Method method : c2.getMethods()){
            superSet.add(method.getName());

        }
        System.out.println(superSet);
        weekSet.removeAll(superSet);
        System.out.println(weekSet);
    }
}

enum Week {
    MONDAY(1, "周一"), TUESDAY(2, "周二"), WEDNESDAY(3, "周三"),
    THURSDAY(4, "周四"), FRIDAY(5, "周五"), SATURDAY(6, "周六"), SUNDAY(7, "周天");

    public String getDescription() {
        return description;
    }

    public int getIndex() {
        return index;
    }

    public static Week getWeek(int index){
        for(Week week : Week.values()){
            if(Objects.equals(week.getIndex(), index)){
                return week;
            }
        }
        return null;
    }

    private int index;
    private String description;

    Week(int number,String description) {
        this.index = number;
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
