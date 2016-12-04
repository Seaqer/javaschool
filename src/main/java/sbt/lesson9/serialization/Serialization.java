package sbt.lesson9.serialization;

import java.io.*;
import sbt.lesson9.cache.Signature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Артём on 22.10.2016.
 */
public class Serialization {
    final static Log LOGGER = LogFactory.getLog(Serialization.class);

    public static void serialize(String filename, Signature searializObject) throws IOException, ClassNotFoundException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(searializObject);
        } catch (Exception e){
            LOGGER.info(e);
            System.out.println(e.getMessage());
        }
    }

    public static Signature deserialize(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream in = new ObjectInputStream(fis)) {
            return  (Signature)in.readObject();
        }
    }
}
