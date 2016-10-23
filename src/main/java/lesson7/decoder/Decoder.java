package lesson7.decoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Артём on 13.10.2016.
 */
public class Decoder {
    public static byte[] decryptFile(Path path, byte key) {
        try {
            return decrypt(Files.readAllBytes(path), key);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла");
        }
    }

    public static byte[] decrypt(byte[] file, byte key) {
        for (int i = 0; i < file.length; i++) {
            file[i] = (byte) (file[i] ^ key);
        }
        return file;
    }
}
