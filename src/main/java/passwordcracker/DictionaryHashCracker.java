package passwordcracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryHashCracker implements HashCracker {

    private final String dictionaryPath;

    public DictionaryHashCracker(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    @Override
    public String crack(String hash) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath))) {
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