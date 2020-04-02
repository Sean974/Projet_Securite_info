package com.example.projet_securite_info;

import java.util.ArrayList;

public class Cesar {
    Ascii_extended ascci_extended = new Ascii_extended();
    ArrayList<String> alphabets = ascci_extended.getListe();
    String res = "";
    public Cesar(String mot, int decalage, boolean crypter){

        if (crypter == false) {
            for (int i = 0; i < mot.length(); i++) {
                res += alphabets.get(indexOf(alphabets, String.valueOf(mot.charAt(i))) - decalage);
            }
        } else {
            for (int i = 0; i < mot.length(); i++) {
                if (indexOf(alphabets, String.valueOf(mot.charAt(i))) + decalage >= alphabets.size()) {
                    res += alphabets.get((indexOf(alphabets, String.valueOf(mot.charAt(i))) + decalage) - alphabets.size());
                } else {
                    res += alphabets.get(indexOf(alphabets, String.valueOf(mot.charAt(i))) + decalage);
                }
            }
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
        Cesar a1 = new Cesar("Sécurité {Informatique}!", 3, true);
        Cesar a2 = new Cesar("Vàfxulwà#~LqirupdwltxhÇ$", 3, false);
        System.out.println(a1.getResultat());
        System.out.println(a2.getResultat());
    }
}
