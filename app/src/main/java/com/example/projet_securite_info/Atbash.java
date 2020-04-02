package com.example.projet_securite_info;

import java.util.ArrayList;

public class Atbash {
    Ascii_extended ascci_extended = new Ascii_extended();
    ArrayList<String> alphabets = ascci_extended.getListe();
    String res = "";

    public Atbash(String mot) {
        int longueur_alpha = alphabets.size() - 1;
        String[] reverse_alpha = new String[alphabets.size()];

        for (int i = 0; i < alphabets.size(); i++) {
            reverse_alpha[i] = alphabets.get(longueur_alpha - i);
        }

        for (int i = 0; i < mot.length(); i++) {
            res += reverse_alpha[indexOf(alphabets, String.valueOf(mot.charAt(i)))];
        }
    }

    public String getResultat() {
        return res;
    }

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
        Atbash a1 = new Atbash("hello");
        Atbash a2 = new Atbash("ùÜôôÉ");
        System.out.println(a1.getResultat());
        System.out.println(a2.getResultat());
    }
}
