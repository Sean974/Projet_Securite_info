package com.example.projet_securite_info;

import java.util.ArrayList;
import java.util.List;

public class Hill {
    Ascii_extended ascci_extended = new Ascii_extended();
    ArrayList<String> alphabets = ascci_extended.getListe();
    ArrayList<Integer> pos_lettre = new ArrayList<Integer>();
    String crypter = " ";
    String decrypter = " ";

    public Hill(String mot, int m, int a, int b, int c, int d, boolean cryptage){
        pos_lettre = combinaison_lineaire(indice_de(mot, m), a, b, c, d);
        for (int i = 0; i < pos_lettre.size(); i++) {
            crypter += alphabets.get(pos_lettre.get(i));
        }

        if (cryptage == false) {
            int inv_A = detA(((a * d) - (b * c)));
            d = (inv_A * d) % 256;
            b = (((inv_A * (-b)) % 256) + 256) % 256;
            c = (((inv_A * (-c)) % 256) + 256) % 256;
            a = (inv_A * a) % 256;

            pos_lettre = combinaison_lineaire(indice_de(mot, m), d, b, c, a);

            for (int i = 0; i < pos_lettre.size(); i++) {
                decrypter += alphabets.get(pos_lettre.get(i));
            }
        }
    }

    public String getCryptage(){return crypter;}
    public String getDecryptage(){return decrypter;}

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
    public ArrayList<List> indice_de(String mot, int nb) {
        ArrayList<List> bloc = new ArrayList<List>();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < mot.length(); i = i + nb) {
            if (i < mot.length() - 1) {
                list = new ArrayList<Integer>();
                list.add(indexOf(alphabets, String.valueOf(mot.charAt(i))));
                list.add(indexOf(alphabets, String.valueOf(mot.charAt(i + 1))));
                bloc.add(list);
            } else {
                list = new ArrayList<Integer>();
                list.add(indexOf(alphabets, String.valueOf(mot.charAt(i))));
                list.add(indexOf(alphabets, "x"));
                bloc.add(list);
            }
        }
        return bloc;
    }

    public ArrayList<Integer> combinaison_lineaire(ArrayList<List> mot, int a, int b,
                                                   int c, int d) {
        ArrayList<Integer> res = new ArrayList<Integer>();

        for (int i = 0; i < mot.size(); i++) {
            final int x1 = (Integer) mot.get(i).get(0);
            final int x2 = (Integer) mot.get(i).get(1);
            final int y1 = ((a * x1) + (b * x2)) % 256;
            final int y2 = ((c * x1) + (d * x2)) % 256;
            res.add(y1);
            res.add(y2);
        }
        return res;
    }

    public ArrayList<Integer> pgcd(int a, int b) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        int r = a;
        int u = 1;
        int rp = b;
        int up = 0;
        while (rp != 0) {
            int q = r / rp;
            int rs = r;
            int us = u;
            r = rp;
            u = up;
            rp = (rs - q * rp);
            up = (us - q * up);
        }
        res.add(r);
        res.add(u);
        return res;
    }

    public int detA(int a) {
        ArrayList<Integer> res = pgcd(a, 256);
        int y = (int)res.get(1);
        return ((y%256) + 256 )% 256;
    }
    public static void main(String[] args) {
        Hill a1 = new Hill("chocolat",2,3,5,2,7,true);
        Hill a2 = new Hill("1₧<ôi╥gε",2,3,5,2,7,false);
        System.out.println(a1.getCryptage());
        System.out.println(a2.getDecryptage());
    }
}
