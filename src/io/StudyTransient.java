//package io;
//
//import java.io.*;
//
///**
// * Created by kanyuxia on 2017/3/5.
// */
//public class StudyTransient {
//    public static void main(String[] args) throws IOException, ClassNotFoundException{
//        User user = new User("kanyuxia@outlook.com","maying3010");
//        System.out.println(user);
//
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\java\\user.dat"));
//        out.writeObject(user);
//
//        ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\java\\user.dat"));
//        User user1 = (User) in.readObject();
//        System.out.println(user1);
//    }
//}
//
//class User implements Serializable {
//    private static final long serialVersionUID = 1234567890L;
//    private String userName;
//    private transient String userPassword;
//    User(String userName, String userPassword){
//        this.userName = userName;
//        this.userPassword = userPassword;
//    }
//    @Override
//    public String toString() {
//        return "userName: " + userName + "\t" + "userPassword: " + userPassword;
//    }
//    private void writeObject(ObjectOutputStream stream) throws IOException {
//        System.out.println("writeObject() is invoked");
//        stream.defaultWriteObject();
//        stream.writeObject(userPassword);
//    }
//    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
//        System.out.println("readObject() is invoked");
//        stream.defaultReadObject();
//        userPassword = (String) stream.readObject();
//    }
//}