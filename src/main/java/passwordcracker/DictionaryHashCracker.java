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
        int attempts = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();
                attempts++;
                if (Md5Util.matches(word, hash)) {
                    System.out.println("  Tentatives : " + attempts);
                    return word;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du dictionnaire : " + e.getMessage());
        }
        System.out.println("  Tentatives : " + attempts);
        return null;
    }
}