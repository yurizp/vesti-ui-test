package mobi.vesti.factory;

import java.util.Random;

public class ContatoFactory {
    private static final Random RANDOM = new Random();
    private static final char[] LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] CARACTERES_ESPECIAIS = "!#$%¨&*()<>:?^`".toCharArray();

    public static String primeiroNome() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int ch = RANDOM.nextInt (LETRAS.length);
            sb.append (LETRAS[ch]);
        }
        return sb.toString().toLowerCase();
    }

    public static String caracteresEspeciais() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int ch = RANDOM.nextInt (CARACTERES_ESPECIAIS.length);
            sb.append (CARACTERES_ESPECIAIS[ch]);
        }
        return sb.toString().toLowerCase();
    }

    public static String email() {
        return String.format("%s@test.com", primeiroNome());
    }

    public static String emailComCaracteresEspeciaisENumeros() {
        return String.format("%s@test.com", primeiroNome()+RANDOM.nextInt(5)+caracteresEspeciais());
    }

    public static String nomeSobrenome() {
        return String.format("%s %s", primeiroNome(), primeiroNome());

    }

    public static String celular(int numeroDeCaracteres) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numeroDeCaracteres; i++) {
            int ch = RANDOM.nextInt(10);
            sb.append(ch);
        }
        return sb.toString().toLowerCase();
    }
}
