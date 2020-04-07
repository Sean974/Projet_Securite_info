package com.example.projet_securite_info;

import java.math.BigInteger;
public class DES{
    // Tables pour l’algorithme DES 
    //  Permutation initiale PI
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

    // Expansion E 
    int[] E = { 32, 1, 2, 3, 4, 5, 4,
            5, 6, 7, 8, 9, 8, 9, 10,
            11, 12, 13, 12, 13, 14, 15,
            16, 17, 16, 17, 18, 19, 20,
            21, 20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29, 28,
            29, 30, 31, 32, 1 };

    // Permutation P 
    int[] P = { 16, 7, 20, 21, 29, 12, 28,
            17, 1, 15, 23, 26, 5, 18,
            31, 10, 2, 8, 24, 14, 32,
            27, 3, 9, 19, 13, 30, 6,
            22, 11, 4, 25 };

    // Permutation initiale inverse 
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

    // Permutation PC1
    int[] PC1 = { 57, 49, 41, 33, 25,
            17, 9, 1, 58, 50, 42, 34, 26,
            18, 10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36, 63,
            55, 47, 39, 31, 23, 15, 7, 62,
            54, 46, 38, 30, 22, 14, 6, 61,
            53, 45, 37, 29, 21, 13, 5, 28,
            20, 12, 4 };

    // Permutation PC2 
    int[] PC2 = { 14, 17, 11, 24, 1, 5, 3,
            28, 15, 6, 21, 10, 23, 19, 12,
            4, 26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32 };

    int[] rotations = { 1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1 };

    // S_box
    int[][][] S_box = {
            // S1
            { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
            // S2
            { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
            // S3
            { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
            // S4
            { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
            // S5
            { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
            // S6
            { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
            // S7
            { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
            // S8
            { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } }
    };


    public String AsciiToHex(String bin){
        String ascii = bin;
        // Step-1 - Convert ASCII string to char array
        char[] ch = ascii.toCharArray();

        // Step-2 Iterate over char array and cast each element to Integer.
        StringBuilder builder = new StringBuilder();

        for (char c : ch) {
            int i = (int) c;
            // Step-3 Convert integer value to hex using toHexString() method.
            builder.append(Integer.toHexString(i).toUpperCase());
        }

        while(builder.length() < 16){
            builder.append("0");
        }

        //Log
        System.out.println("ASCII = " + ascii);
        System.out.println("Hex = " + builder.toString());
        System.out.println("Bin = " + hextoBin(builder.toString()));
        System.out.println("Hex = " + binToHex(hextoBin(builder.toString())));

        return builder.toString();
    }

    // Prend en argument un Hexadecimale et le convertit en Binaire pour faciliter la conversion en ascii.
    public String HexToAscii(String Hex){
        String str = "";
        String bin = hextoBin(Hex);
        // Parcourt tous les 8 bits pour chercher le code ASCII 
        for(int y=0;y<bin.length(); y+=8){
            int charCode = Integer.parseInt(bin.substring(y,y+8), 2);
            str += new Character((char)charCode).toString();
        }
        return str;
    }


    // hexadecimal to binary
    public String hextoBin(String hex){
        // Suppose que le mot fait 16 bits donc : 16 * 4 = 64 bits.
        int n = hex.length() * 4;
        String bin ="";
        bin = Long.toBinaryString( Long.parseUnsignedLong(hex, 16));

        // Padding pour être à 64 btis
        while (bin.length() < n)
            bin = "0" + bin;
        return bin;
    }

    // binary to hexadecimal 
    public String binToHex(String bin){
        // Suppose que le mot fait 64 bits donc : 64 / 4 = 16 bits.
        int n = (int)bin.length() / 4;
        String hex = "";
        hex = Long.toHexString( Long.parseUnsignedLong(bin, 2));

        // Padding pour être à 16 btis
        while (hex.length() < n)
            hex = "0" + hex;
        return hex;
    }

    // Affichage d'espace tous les 4 bits
    public String espace_binaire(String input){
        input = hextoBin(input);
        String res = "";
        for(int i=0;i<input.length();i+=4){
            res += input.substring(i, i+4) + " ";
        }
        return res;
    }

    // Permutation entre le mot en hexadecimale et la table choisit.
    public String permutation(int[] sequence, String mot){
        String res = "";
        mot = hextoBin(mot);
        for (int i = 0; i < sequence.length; i++)
            res += mot.charAt(sequence[i] - 1);
        res = binToHex(res);
        return res;
    }

    // XOR entre 2 hexadecimaux 
    public String xor(String a, String b){
        // Conversion decimale (base 10) 
        long t_a = Long.parseUnsignedLong(a, 16);
        // Conversion decimale (base 10) 
        long t_b = Long.parseUnsignedLong(b, 16);
        // xor 
        t_a = t_a ^ t_b;
        // Retour à l'hexadecimale
        a = Long.toHexString(t_a);

        // padding
        while (a.length() < b.length())
            a = "0" + a;
        return a;
    }

    // Permutation circulaire
    public String Permutation_circulaire(String hex, int rotation){
        int n = hex.length() * 4;
        int perm[] = new int[n];
        for (int i = 0; i < n - 1; i++)
            perm[i] = (i + 2);
        perm[n - 1] = 1;
        while (rotation > 0){
            hex = permutation(perm, hex);
            rotation--;
        }
        return hex;
    }

    // Generer 16 clé différentes
    public String[] getKeys(String key){
        String keys[] = new String[16];
        // 1er clé permuter
        key = permutation(PC1, key);
        for (int i = 0; i < 16; i++) {
            key = Permutation_circulaire(  key.substring(0, 7), rotations[i]) + Permutation_circulaire(key.substring(7, 14), rotations[i]);
            // 2ième clé permuter 
            keys[i] = permutation(PC2, key);
        }
        return keys;
    }

    // Les 8 s-box
    public String sBox(String bin){
        String hex = "";

        bin = hextoBin(bin);
        for (int i = 0; i < 48; i += 6) {
            String temp = bin.substring(i, i + 6);
            int num = i / 6;
            int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2);
            int col = Integer.parseInt(temp.substring(1, 5), 2);
            hex += Integer.toHexString(S_box[num][row][col]);
        }
        return hex;
    }

    public String round(String hexa, String key, int num){
        String bin, E_R, P_B, A, B = "";
        bin = hextoBin(hexa);
        String left = bin.substring(0, 32);
        String temp = bin.substring(32, 64);
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

    public String crypt(String message, String key){

        // On traduit le message en hexadecimale
        // puis dans les fonctions permutation() et round() le message prendra en argument l'hexa mais sera traité en binaire.
        // d'où l'appel à la fonction bintoHex() à l'intérieur de ses fonctions.
        message = AsciiToHex(message);

        // Tableau de 16 clé 
        String keys[] = getKeys(key);

        // Permutation initiale
        message = permutation(PI, message);

        System.out.println("L0 = " + hextoBin(message).substring(0, 32) + " = 0x" + message.substring(0, 8).toUpperCase());
        System.out.println("R0 = " + hextoBin(message).substring(32, 64) + " = 0x" + message.substring(8, 16).toUpperCase());

        // 16 itérations 
        for (int i = 0; i < 16; i++) {
            message = round(message, keys[i], i);
        }

        // 32-bit swap 
        message = message.substring(8, 16) + message.substring(0, 8);

        // Permutation finale 
        message = permutation(PI_reverse, message);

        System.out.println("\n"+"C = "+espace_binaire(message) + " = 0x" + message.toUpperCase());
        return HexToAscii(message);
    }

    public String decrypt(String message, String key){

        // On traduit le message en hexadecimale
        // puis dans les fonctions permutation() et round() le message prendra en argument l'hexa mais sera traité en binaire.
        // d'où l'appel à la fonction bintoHex() à l'intérieur de ses fonctions.
        message = AsciiToHex(message);

        // Tableau de 16 clé 
        String keys[] = getKeys(key);

        // Permutation initiale
        message = permutation(PI, message);

        System.out.println("L0 = " + hextoBin(message).substring(0, 32) + " = 0x" + message.substring(0, 8).toUpperCase());
        System.out.println("R0 = " + hextoBin(message).substring(32, 64) + " = 0x" + message.substring(8, 16).toUpperCase());

        // 16 itérations 
        for (int i = 15; i > -1; i--) {
            message = round(message, keys[i], 15 - i);
        }

        // 32-bit swap 
        message = message.substring(8, 16) + message.substring(0, 8);

        // Permutation finale 
        message = permutation(PI_reverse, message);

        System.out.println("\n"+"C = "+espace_binaire(message) + " = 0x" + message.toUpperCase());
        return HexToAscii(message);
    }

    public DES(){}
    public static void main(String args[]){
        String mess = "Ok";
        String key  = "0123456789abcdef";

        DES d = new DES();
        System.out.println("Le message : "+mess+" à été crypter en : "+d.crypt(mess, key));


        String message_crypter = d.crypt(mess, key);
        System.out.println("Le message : "+message_crypter+" à été décrypter en : "+d.decrypt(message_crypter, key));
    }
}