code src/main/java/passwordcracker/PasswordCracker.javapackage passwordcracker;

/**
 * PasswordCracker v1
 * Programme principal en ligne de commande.
 *
 * Usage :
 *   passwordCracker -m BRUTE -h e7247759c1633c0f9f1485f3690294a9
 *   passwordCracker -m DICO  -h e7247759c1633c0f9f1485f3690294a9
 *
 * Ce fichier ne fait AUCUNE instanciation directe des classes concrètes
 * (BruteForceHashCracker / DictionaryHashCracker) : il passe uniquement
 * par HashCrackerFactory.create(...), conformément à la contrainte du
 * mini-projet (création centralisée dans la fabrique).
 */
public class PasswordCracker {

    public static void main(String[] args) {
        String method = null;
        String hash = null;

        // --- Parsing simple des arguments ---
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-m":
                    if (i + 1 >= args.length) {
                        printUsageAndExit("Valeur manquante après -m");
                    }
                    method = args[++i];
                    break;
                case "-h":
                    if (i + 1 >= args.length) {
                        printUsageAndExit("Valeur manquante après -h");
                    }
                    hash = args[++i];
                    break;
                default:
                    printUsageAndExit("Argument inconnu : " + args[i]);
            }
        }

        if (method == null || hash == null) {
            printUsageAndExit("Les options -m et -h sont obligatoires");
        }

        // --- Récupération de la stratégie via la fabrique ---
        HashCracker cracker;
        try {
            cracker = HashCrackerFactory.create(method);
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
            printUsageAndExit(null);
            return; // inatteignable, mais requis par le compilateur
        }

        System.out.println("Méthode sélectionnée : " + method);
        System.out.println("Hash à casser        : " + hash);
        System.out.println("Recherche en cours...");
        System.out.println();

        // --- Exécution + mesure du temps ---
        long start = System.nanoTime();
        String result = cracker.crack(hash);
        long end = System.nanoTime();
        double elapsedMs = (end - start) / 1_000_000.0;

        System.out.println("--------------------------------------------------");
        if (result != null) {
            System.out.println("Password found: " + result);
        } else {
            System.out.println("Password not found");
        }
        System.out.printf("Temps d'exécution     : %.2f ms%n", elapsedMs);
        System.out.println("--------------------------------------------------");
    }

    private static void printUsageAndExit(String errorMessage) {
        if (errorMessage != null) {
            System.err.println("Erreur : " + errorMessage);
        }
        System.err.println("Usage : passwordCracker -m <BRUTE|DICO> -h <hashMD5>");
        System.err.println("Exemple : passwordCracker -m DICO -h e7247759c1633c0f9f1485f3690294a9");
        System.exit(1);
    }
}