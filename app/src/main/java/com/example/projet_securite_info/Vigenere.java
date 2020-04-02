package com.example.projet_securite_info;

import java.util.ArrayList;

public class Vigenere {
    Ascii_extended ascci_extended = new Ascii_extended();
    ArrayList<String> alphabets = ascci_extended.getListe();
    String res = "";
    public Vigenere(String mot, String clef, boolean crypter){

        ArrayList<Integer> poids = new ArrayList<Integer>();
        int x = 0;
        int i = 0;

        if (crypter == true) {
            while (i < mot.length()) {
                if (x > clef.length() - 1) {
                    x = 0;
                }
                poids.add(indexOf(alphabets, String.valueOf(mot.charAt(i))) + indexOf(alphabets, String.valueOf(clef.charAt(x))));
                x += 1;
                i += 1;
            }
        }

        if (crypter == false) {
            int res = 0;
            while (i < mot.length()) {
                if (x > clef.length() - 1) {
                    x = 0;
                }
                if (res < 0) {
                    res += 26;
                    poids.add(res);
                    x += 1;
                    i += 1;
                } else {
                    res = indexOf(alphabets, String.valueOf(mot.charAt(i))) - indexOf(alphabets, String.valueOf(clef.charAt(x)));
                    poids.add(res);
                    x += 1;
                    i += 1;
                }
            }
        }
        for (int y = 0; y < poids.size(); y++) {
            res += alphabets.get(poids.get(y));
        }
    }
    public String getResultat(){return res;}

    public int indexOf(final ArrayList<String> tab, final String val) {
        int index = -1;
        for (int i = 0; i < tab.size(); i++) {
            if (tab.get(i).equals(val)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public static void main(String[] args) {
        Vigenere a1 = new Vigenere("Hola amigos !", "cle", true);
        Vigenere a2 = new Vigenere("½█╤─î╞╨╒╠╥▀àä", "cle", false);
        System.out.println(a1.getResultat());
        System.out.println(a2.getResultat());
    }
}
