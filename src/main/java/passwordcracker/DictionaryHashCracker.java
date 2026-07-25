package passwordcracker;

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
                if (Md5Util.matches(word, hash)) {
                    return word;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du dictionnaire : " + e.getMessage());
        }
        return null;
    }
}