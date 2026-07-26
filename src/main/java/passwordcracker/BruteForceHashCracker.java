package passwordcracker;

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
            if (Md5Util.matches(mot, hashCible)) {
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
}