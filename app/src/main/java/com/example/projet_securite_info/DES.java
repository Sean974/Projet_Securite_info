package com.example.projet_securite_info;

import java.math.BigInteger;

public class DES {
    // Permutation Initial avec un 1er K
    int[] PC1 = { 57, 49, 41, 33, 25,
            17, 9, 1, 58, 50, 42, 34, 26,
            18, 10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36, 63,
            55, 47, 39, 31, 23, 15, 7, 62,
            54, 46, 38, 30, 22, 14, 6, 61,
            53, 45, 37, 29, 21, 13, 5, 28,
            20, 12, 4 };

    // Permutation Initial avec un 2ième K
    int[] PC2 = { 14, 17, 11, 24, 1, 5, 3,
            28, 15, 6, 21, 10, 23, 19, 12,
            4, 26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32 };

    // Permutation Initial soit P
    int[] PI = { 58, 50, 42, 34, 26, 18,
            10, 2, 60, 52, 44, 36, 28, 20,
            12, 4, 62, 54, 46, 38,
            30, 22, 14, 6, 64, 56,
            48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17,
            9, 1, 59, 51, 43, 35, 27,
            19, 11, 3, 61, 53, 45,
            37, 29, 21, 13, 5, 63, 55,
            47, 39, 31, 23, 15, 7 };

    // Inverse de la Permutation Initial soit P^(-1)
    int[] PI_reverse = { 40, 8, 48, 16, 56, 24, 64,
            32, 39, 7, 47, 15, 55,
            23, 63, 31, 38, 6, 46,
            14, 54, 22, 62, 30, 37,
            5, 45, 13, 53, 21, 61,
            29, 36, 4, 44, 12, 52,
            20, 60, 28, 35, 3, 43,
            11, 51, 19, 59, 27, 34,
            2, 42, 10, 50, 18, 58,
            26, 33, 1, 41, 9, 49,
            17, 57, 25 };

    // Expansion
    int[] E = { 32, 1, 2, 3, 4, 5, 4,
            5, 6, 7, 8, 9, 8, 9, 10,
            11, 12, 13, 12, 13, 14, 15,
            16, 17, 16, 17, 18, 19, 20,
            21, 20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29, 28,
            29, 30, 31, 32, 1 };

    // Permutation finale
    int[] P = { 16, 7, 20, 21, 29, 12, 28,
            17, 1, 15, 23, 26, 5, 18,
            31, 10, 2, 8, 24, 14, 32,
            27, 3, 9, 19, 13, 30, 6,
            22, 11, 4, 25 };

    // S_box
    int[][][] S_box = {
            //S1
            { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
            //S2
            { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
            //S3
            { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
            //S4
            { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
            //S5
            { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
            //S6
            { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
            //S7
            { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
            //S8
            { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };

    int[] decalage_itere = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

    public DES(String texte, String key, boolean crypter){
        if(crypter == true){
            System.out.println("Cryptage D.E.S :\n");
            String res = crypt(texte, key);
            System.out.println( "\nC =  " + res.toUpperCase() + "\n");
        }else{
            System.out.println("Decryptage D.E.S :\n");
            String res = decrypt(texte, key);
            System.out.println( "\nC =  " + res.toUpperCase() + "\n");
        }
    }

    // hexadecimal to binary
    public String hextoBin(String mot){
        int n = mot.length() * 4;
        mot = new BigInteger(mot, 16).toString(2);

        //Padding
        while (mot.length() < n)
            mot = "0" + mot;
        return mot;
    }

    // binary to hexadecimal
    public String binToHex(String mot){
        int n = (int)mot.length() / 4;
        mot = new BigInteger(mot, 2).toString(16);
        //Padding
        while (mot.length() < n)
            mot = "0" + mot;
        return mot;
    }

    public String permutation(int[] tab, String mot){
        String res = "";
        mot = hextoBin(mot);
        for (int i = 0; i < tab.length; i++)
            res += mot.charAt(tab[i] - 1);
        res = binToHex(res);
        return res;
    }

    public String Permutation_circulaire(String mot, int numBits){
        int n = mot.length() * 4;
        int P[] = new int[n];
        for (int i = 0; i < n - 1; i++) { P[i] = (i + 2); }
        P[n - 1] = 1;
        while (numBits > 0){
            mot = permutation(P, mot);
            numBits--;
        }
        return mot;
    }

    // xor 2 hexadecimal strings
    public String xor(String a, String b){
        long t_a = new BigInteger(a, 16).longValue();
        long t_b = new BigInteger(b, 16).longValue();
        // xor
        t_a = t_a ^ t_b;
        a = Long.toHexString(t_a);
        // Padding
        while (a.length() < b.length())
            a = "0" + a;
        return a;
    }

    // Generation de 16 clés différentes
    public String[] getKeys(String key){
        String keys[] = new String[16];
        // 1ère clé permuter
        key = permutation(PC1, key);
        for (int i = 0; i < 16; i++) {
            key = Permutation_circulaire( key.substring(0, 7), decalage_itere[i]) + Permutation_circulaire(key.substring(7, 14), decalage_itere[i]);
            // 2ième clé permuter
            keys[i] = permutation(PC2, key);
        }
        return keys;
    }

    // s-box
    public String sBox(String input){
        String output = "";
        input = hextoBin(input);
        for (int i = 0; i < 48; i += 6) {
            String temp = input.substring(i, i + 6);
            int num = i / 6;
            int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2);
            int col = Integer.parseInt(temp.substring(1, 5), 2);
            output += Integer.toHexString(S_box[num][row][col]);
        }
        return output;
    }

    // Iteration prévue
    public String round(String input, String key, int num){
        String E_R, P_B, A, B = "";
        input = hextoBin(input);
        String left = input.substring(0, 32);
        String temp = input.substring(32, 64);
        String right = temp;

        // Expansion
        temp = binToHex(temp);
        E_R = permutation(E, temp);

        // XOR de temp et la i-ième key
        A = xor(E_R, key);

        // les 8 blocs de 4 bits S=S1...S8
        B  = sBox(A);

        // Réordonne B par une dernière permutation
        P_B = permutation(P, B);

        // xor
        left = xor(binToHex(left), P_B);

        System.out.println("--------------------------------");
        System.out.println("\niteration "+ (num + 1) + " ");

        String S_B = hextoBin(B);
        String res8 = "";
        int n_s = 1;
        for(int i=0;i<S_B.length();i+=4){
            res8 +="S"+n_s+" = "+S_B.substring(i, i+4) + " ";
            n_s++;
        }

        System.out.println("K = "+espace_binaire(key.toUpperCase()));
        System.out.println("E(R) = "+espace_binaire(E_R));
        System.out.println("A = E(R) + K = "+espace_binaire(A));
        System.out.println(res8);
        System.out.println("B = "+espace_binaire(B));
        System.out.println("P(B) = "+espace_binaire(P_B));

        // Variable hexadecimal pour L[i] & R[i]
        left = hextoBin(left);
        long L_hexa = new BigInteger(left.toUpperCase(),2).longValue();
        long R_hexa = new BigInteger(right.toUpperCase(),2).longValue();

        // Affichage binaire
        String L = left.toUpperCase();
        String res1 = "";
        for(int i=0;i<L.length();i+=4){
            res1 += L.substring(i, i+4) + " ";
        }

        String R = right.toUpperCase();
        String res2 = "";
        for(int i=0;i<R.length();i+=4){
            res2 += R.substring(i, i+4) + " ";
        }

        System.out.println("L"+(num+1)+" = "+res2+ " = "+ String.format("0x%8X", R_hexa));
        System.out.println("R"+(num+1)+" = "+res1+ " = "+ String.format("0x%8X", L_hexa));


        // rassemblage
        return binToHex(right + left);
    }

    public String espace_binaire(String input){
        input = hextoBin(input);
        String res = "";
        for(int i=0;i<input.length();i+=4){
            res += input.substring(i, i+4) + " ";
        }
        return res;
    }

    public String crypt(String plainText, String key){
        int i;
        // 16 clés
        String keys[] = getKeys(key);

        // Permutation Initiale
        plainText = permutation(PI, plainText);

        // Affichage binaire
        String left = hextoBin(plainText).substring(0, 32);
        String right = hextoBin(plainText).substring(32, 64);

        // iteration L & R
        long L_hexa = new BigInteger(left.toUpperCase(),2).longValue();
        long R_hexa = new BigInteger(right.toUpperCase(),2).longValue();

        String L = left.toUpperCase();
        String res1 = "";
        for(int x=0;x<L.length();x+=4){
            res1 += L.substring(x, x+4) + " ";
        }

        String R = right.toUpperCase();
        String res2 = "";
        for(int x=0;x<R.length();x+=4){
            res2 += R.substring(x, x+4) + " ";
        }

        System.out.println("L0 = "+res1+ " = "+ String.format("0x%8X", L_hexa));
        System.out.println("R0 = "+res2+ " = "+ String.format("0x%8X", R_hexa));
        // 16 itérations
        for (i = 0; i < 16; i++) {
            plainText = round(plainText, keys[i], i);
        }

        // 32-bit swap
        plainText = hextoBin(plainText);
        plainText = plainText.substring(32, 64)  + plainText.substring(0, 32);

        // Permutation Finale
        plainText = binToHex(plainText);
        plainText = permutation(PI_reverse, plainText);

        //Valeur de C + son hexadecimal
        long C_hexa = new BigInteger(hextoBin(plainText).toUpperCase(),2).longValue();
        return espace_binaire(plainText) + " = "+String.format("0x%8X", C_hexa);
    }

    public String decrypt(String plainText, String key){
        int i;
        // 16 clés
        String keys[] = getKeys(key);

        // Permutation Initial
        plainText = permutation(PI, plainText);

        // Affichage binaire
        String left = hextoBin(plainText).substring(0, 32);
        String right = hextoBin(plainText).substring(32, 64);

        // iteration L & R
        long L_hexa = new BigInteger(left.toUpperCase(),2).longValue();
        long R_hexa = new BigInteger(right.toUpperCase(),2).longValue();

        String L = left.toUpperCase();
        String res1 = "";
        for(int x=0;x<L.length();x+=4){
            res1 += L.substring(x, x+4) + " ";
        }

        String R = right.toUpperCase();
        String res2 = "";
        for(int x=0;x<R.length();x+=4){
            res2 += R.substring(x, x+4) + " ";
        }

        System.out.println("L0 = "+res1+ " = "+ String.format("0x%8X", L_hexa));
        System.out.println("R0 = "+res2+ " = "+ String.format("0x%8X", R_hexa));

        // 16 iterations
        for (i = 15; i > -1; i--) {
            plainText = round(plainText, keys[i], 15 - i);
        }

        // 32-bit swap
        plainText = hextoBin(plainText);
        plainText = plainText.substring(32, 64) + plainText.substring(0, 32);

        plainText = binToHex(plainText);
        plainText = permutation(PI_reverse, plainText);
        //Valeur de C + son hexadecimal
        long C_hexa = new BigInteger(hextoBin(plainText).toUpperCase(),2).longValue();

        System.out.println(hexToAscii(String.format("%8X", C_hexa)));
        return espace_binaire(plainText) + " = "+String.format("%8X", C_hexa);
    }
    private String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }


    public static void main(String[] args) {
        String text = "23456789ABCDEF01";
        String key = "0123456789ABCDEF";

        DES d = new DES(text,key,true);
    }


}
