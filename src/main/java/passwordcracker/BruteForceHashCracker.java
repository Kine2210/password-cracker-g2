package passwordcracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BruteForceHashCracker implements HashCracker {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_LENGTH = 4;

    @Override
    public String crack(String hash) {
        for (int longueur = 1; longueur <= MAX_LENGTH; longueur++) {
            char[] combinaison = new char[longueur];
            String resultat = essayerCombinaisons(combinaison, 0, longueur, hash);
            if (resultat != null) {
                return resultat;
            }
        }
        return null;
    }

    private String essayerCombinaisons(char[] combinaison, int index, int longueur, String hashCible) {
        if (index == longueur) {
            String mot = new String(combinaison);
            String hashCalcule = calculerMD5(mot);
            if (hashCalcule != null && hashCalcule.equalsIgnoreCase(hashCible)) {
                return mot;
            }
            return null;
        }

        for (int i = 0; i < ALPHABET.length(); i++) {
            combinaison[index] = ALPHABET.charAt(i);
            String resultat = essayerCombinaisons(combinaison, index + 1, longueur, hashCible);
            if (resultat != null) {
                return resultat;
            }
        }
        return null;
    }

    private String calculerMD5(String texte) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(texte.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}