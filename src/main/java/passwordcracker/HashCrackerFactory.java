package passwordcracker;

public class HashCrackerFactory {

    private static final String METHOD_BRUTE = "BRUTE";
    private static final String METHOD_DICO = "DICO";

    private static final String DEFAULT_DICTIONARY_PATH = "dict.txt";

    private HashCrackerFactory() {
    }

    public static HashCracker create(String method) {
        if (method == null) {
            throw new IllegalArgumentException("La méthode de cassage ne peut pas être nulle");
        }

        switch (method.toUpperCase()) {
            case METHOD_BRUTE:
                return new BruteForceHashCracker();
            case METHOD_DICO:
                return new DictionaryHashCracker(DEFAULT_DICTIONARY_PATH);
            default:
                throw new IllegalArgumentException(
                        "Méthode de cassage inconnue : " + method
                                + " (valeurs acceptées : " + METHOD_BRUTE + ", " + METHOD_DICO + ")");
        }
    }
}
