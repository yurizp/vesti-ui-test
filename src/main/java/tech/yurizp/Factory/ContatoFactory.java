package tech.yurizp.Factory;

import java.util.Random;

public class ContatoFactory {
    private static Random rand = new Random();
    private static char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String primeiroNome() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int ch = rand.nextInt (letras.length);
            sb.append (letras [ch]);
        }
        return sb.toString().toLowerCase();
    }

    public static String email() {
        return String.format("%s@test.com", primeiroNome());
    }

    public static String nomeSobrenome() {
        return String.format("%s %s", primeiroNome(), primeiroNome());

    }

    public static String celular(int numeroDeCaracteres) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numeroDeCaracteres; i++) {
            int ch = rand.nextInt(10);
            sb.append(ch);
        }
        return sb.toString().toLowerCase();
    }
}
