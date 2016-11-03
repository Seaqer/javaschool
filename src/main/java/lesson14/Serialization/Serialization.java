package lesson14.Serialization;

import lesson14.Cache.Signature;

import java.io.*;

/**
 * Created by Артём on 22.10.2016.
 */
public class Serialization {

    public static void serialize(String filename, Signature searializObject) throws IOException, ClassNotFoundException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(searializObject);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Signature deserialize(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream in = new ObjectInputStream(fis)) {
            return  (Signature)in.readObject();
        }
    }
}
