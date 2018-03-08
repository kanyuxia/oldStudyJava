package io;

import java.io.*;

/**
 * Created by kanyuxia on 2017/3/5.
 */
public class StudySerializable{
    public static void main(String[] args)
            throws IOException, ClassNotFoundException{
        User user = new User("kanyuxia@outlook.com ", "123456");

        System.out.println("serialize");
        System.out.println(user);
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("E:\\java\\user.dat"));
        out.writeObject(user);

        System.out.println("deserialize");
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("E:\\java\\user.dat"));
        User user1 = (User) in.readObject();
        System.out.println(user1);
    }
}
class User implements Serializable {
    private static final long serialVersionUID = 4936874859415237692L;
    private String userName;
    private transient String password;

    private void writeObject(ObjectOutputStream outputStream)
            throws IOException, ClassNotFoundException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(password);
    }

    private void readObject(ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        password = (String) inputStream.readObject();
    }

    User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "userName: " + userName + " password: " + password;
    }
}

