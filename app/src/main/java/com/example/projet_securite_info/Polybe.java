package com.example.projet_securite_info;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polybe{
    static int LIGNE = 6;
    static int COLONNE = 6;
    static String mot;
    static String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";

    public Polybe(String mot) {
        this.mot = mot;
    }

    public ArrayList<List> getCryptage(){return Crypter(mot);}
    public String getDecryptage(){
        ArrayList<List> liste = new ArrayList<List>();
        ArrayList<String> matrice = new ArrayList<String>();
        ArrayList<String> cle = new ArrayList<String>();
        for(int i=0;i<36;i++){
            matrice.add(Character.toString(mot.charAt(i)));
        }
        for(int i=36;i<mot.length();i++){
            cle.add(Character.toString(mot.charAt(i)));
        }
        liste.add(matrice);
        liste.add(cle);
        return Decrypter(liste);
    }

    public String liste_sans_doublon(String text) {
        String res = "";
        for (int i = 0; i < text.length(); i++) {
            res += (res.contains(text.charAt(i) + "") ? "" : text.charAt(i) + "");
        }
        return res;
    }

    public String[][] init_matrice(String mot) {
        String[][] matrice = new String[LIGNE][COLONNE];
        String l = mot + alphabet;
        l = liste_sans_doublon(l);
        int id = 0;
        for (int x = 0; x < LIGNE; x++) {
            for (int y = 0; y < COLONNE; y++) {
                matrice[x][y] = String.valueOf(l.charAt(id));
                id++;
            }
        }

        System.out.println("\nLa MATRICE est :");
        for (int x = 0; x < LIGNE; x++) {
            for (int y = 0; y < COLONNE; y++) {
                System.out.print("\t"+matrice[x][y]);
            }
            System.out.println("");
        }

        return matrice;
    }
    public ArrayList<List> couple(String[][] matrice, String l) {
        final String lettre = String.valueOf(l);
        int line = 0;
        int col = 0;
        ArrayList<String> list_line = new ArrayList<String>();
        ArrayList<String> list_col = new ArrayList<String>();
        ArrayList<List> bloc = new ArrayList<List>();
        ArrayList<String> liste = new ArrayList<String>();
        for (int x = 0; x < LIGNE; x++) {
            for (int y = 0; y < COLONNE; y++) {
                if (matrice[x][y].equals(String.valueOf(lettre))) {
                    line = x;
                    col = y;
                }
            }
        }
        for (String i : matrice[line]) {
            if (!i.equals(lettre)) {
                list_line.add(i);
            }
        }
        for (int x = 0; x < LIGNE; x++) {
            String i = matrice[x][col];
            if (!i.equals(lettre)) {
                list_col.add(i);
            }
        }
        for (String x : list_line) {
            for (String y : list_col) {
                liste = new ArrayList<String>();
                liste.add(x);
                liste.add(y);
                bloc.add(liste);
            }
        }
        /*
         * for(int x = 0; x<bloc.size();x++){ System.out.print(bloc.get(x)); }
         */
        return bloc;

    }

    public ArrayList<List> Crypter(String mot) {
        ArrayList<List> liste = new ArrayList<List>();
        ArrayList<List> c = new ArrayList<List>();
        String[][] matrice = init_matrice(mot);
        for (int i = 0; i < mot.length(); i++) {
            c = couple(matrice, String.valueOf(mot.charAt(i)));
            Random rand = new Random();

            liste.add(c.get(rand.nextInt(c.size())));
        }
        /*for(int x = 0; x<liste.size();x++){
            System.out.print(liste.get(x));
        }*/


        ArrayList<List> test = new ArrayList<List>();
        ArrayList<String> mMatrice = new ArrayList<String>();
        ArrayList<String> mCode = new ArrayList<String>();
        for (int x = 0; x < LIGNE; x++) {
            for (int y = 0; y < COLONNE; y++) {
                mMatrice.add(matrice[x][y]);
            }
        }
        test.add(mMatrice);
        for(List e : liste){
            mCode.add((String) e.get(0));
            mCode.add((String) e.get(1));
        }

        test.add(mCode);
        return test;
    }

    public String Decrypter(ArrayList<List> liste_couples) {

        String nMatrice = "";
        List li = liste_couples.get(0);
        for(int i=0; i<li.size();i++){
            nMatrice += li.get(i);
        }
        String[][] matrice = init_matrice(nMatrice);

        List li2 = liste_couples.get(1);
        List nCouple;
        ArrayList<List> nListe = new ArrayList<List>();
        for(int i=0; i<li2.size();i+=2){
            nCouple  = new ArrayList<String>();
            nCouple.add(li2.get(i));
            nCouple.add(li2.get(i+1));
            nListe.add(nCouple);
        }

        liste_couples = nListe;

        String chaine = "";
        int lig = 0, col =0;
        for (List e : liste_couples) {
            for (int x = 0; x < LIGNE; x++) {
                for (int y = 0; y < COLONNE; y++) {
                    if(matrice[x][y].equals(e.get(0))){
                        lig = x;
                    }
                    if(matrice[x][y].equals(e.get(1))){
                        col = y;
                    }
                }
            }
            chaine += matrice[lig][col];
        }

        //System.out.println(chaine);
        return chaine;
    }

    public static void main(String[] args) {
        Polybe a1 = new Polybe("Heloabcdfghijkmnpqrstuvwxyz012345678bjbdato5a0");
        //System.out.println(a1.getCryptage());
        System.out.println(a1.getDecryptage());

    }
}
