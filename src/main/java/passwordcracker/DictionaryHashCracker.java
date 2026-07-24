package passwordcracker;

import java.security.MessageDigest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryHashCracker implements HashCracker {

    @Override
    public String crack(String hash) {
        try (BufferedReader reader = new BufferedReader(new FileReader("dict.txt"))) {
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();
                String wordHash = md5(word);
                if (wordHash.equals(hash)) {
                    return word;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du dictionnaire : " + e.getMessage());
        }
        return null;
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}