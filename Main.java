package banking;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = null;
        for (int i = 0; i < args.length;i++) {
            switch (args[i]) {
                case "-fileName" :
                    path = args[++i];
                    break;
                default:
                    break;
            }
        }
        if (path != null) {
            View view = new View(new Bank(path));
        }
    }

}
