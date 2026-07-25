package passwordcracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Md5Util {

    // Classe utilitaire : pas d'instanciation
    private Md5Util() {
    }

    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestBytes = md.digest(input.getBytes("UTF-8"));
            return bytesToHex(digestBytes);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Erreur lors du calcul du hash MD5", e);
        }
    }

    public static boolean matches(String candidate, String targetHash) {
        if (targetHash == null) {
            return false;
        }
        return hash(candidate).equalsIgnoreCase(targetHash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
