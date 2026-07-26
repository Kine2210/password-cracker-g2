package passwordcracker;

/**
 * PasswordCracker v1
 * Programme principal en ligne de commande.
 *
 * Usage :
 *   passwordCracker -m BRUTE -h 098f6bcd4621d373cade4e832627b4f6
 *   passwordCracker -m DICO  -h 098f6bcd4621d373cade4e832627b4f6
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

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-m":
                    if (i + 1 >= args.length) printUsageAndExit("Valeur manquante après -m");
                    method = args[++i];
                    break;
                case "-h":
                    if (i + 1 >= args.length) printUsageAndExit("Valeur manquante après -h");
                    hash = args[++i];
                    break;
                default:
                    printUsageAndExit("Argument inconnu : " + args[i]);
            }
        }

        if (method == null || hash == null) {
            printUsageAndExit("Les options -m et -h sont obligatoires");
        }

        HashCracker cracker;
        try {
            cracker = HashCrackerFactory.create(method);
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
            printUsageAndExit(null);
            return;
        }

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         PASSWORD CRACKER v1 — Groupe 2          ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  Méthode : " + method);
        System.out.println("  Hash    : " + hash);
        System.out.println();
        System.out.println("  Recherche en cours...");
        System.out.println();

        long start = System.nanoTime();
        String result = cracker.crack(hash);
        long end = System.nanoTime();
        double elapsedMs = (end - start) / 1_000_000.0;

        System.out.println("╔══════════════════════════════════════════════════╗");
        if (result != null) {
            System.out.println("║  ✅ Password found : " + result);
        } else {
            System.out.println("║  ❌ Password not found");
        }
        System.out.printf("║  ⏱  Temps         : %.2f ms%n", elapsedMs);
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printUsageAndExit(String errorMessage) {
        if (errorMessage != null) {
            System.err.println("Erreur : " + errorMessage);
        }
        System.err.println("Usage   : passwordCracker -m <BRUTE|DICO> -h <hashMD5>");
        System.err.println("Exemple : passwordCracker -m DICO -h 098f6bcd4621d373cade4e832627b4f6");
        System.exit(1);
    }
}