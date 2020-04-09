package com.example.projet_securite_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    Button btn_crypter, btn_decrypter, btn_retour;
    EditText mess_clair, mess_crypter, key;
    TextView resultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Les boutons
        btn_crypter = (Button) findViewById(R.id.btn_crypter);
        btn_decrypter = (Button) findViewById(R.id.btn_decrypter);
        btn_retour = (Button) findViewById(R.id.btn_retour);

        //Zone d'écriture
        mess_clair = (EditText) findViewById(R.id.message_clair);
        mess_crypter = (EditText) findViewById(R.id.message_crypter);
        key = (EditText) findViewById(R.id.key);

        //Affichage du message crypter/decrypter
        resultat = (TextView) findViewById(R.id.resultat);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            this.getSupportActionBar().setTitle(bundle.getString("crypto"));

            // PROGRAMME ATBASH
            if(this.getSupportActionBar().getTitle().equals("Atbash")){
                key.setEnabled(false);
                key.setHint("No key for Atbash");
                btn_crypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_clair.getText().toString();
                        if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte en clair' ", Toast.LENGTH_SHORT).show();

                        }else {
                            Atbash a = new Atbash(mess);
                            resultat.setText(a.getResultat());

                            // Au cas ou si on veut decrypter un mot de l'ASCII extended
                            mess_crypter.setText(resultat.getText());
                        }
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte crypter", Toast.LENGTH_SHORT).show();

                        }else {
                            Atbash a = new Atbash(mess);
                            resultat.setText(a.getResultat());
                        }
                    }
                });
            }


            //PROGRAMME CESAR
            if(this.getSupportActionBar().getTitle().equals("César")){
                //Empecher l'utilisateur d'entrée autre chose qu'un chiffre ou un nombre
                key.setInputType(InputType.TYPE_CLASS_NUMBER);

                key.setHint("Entrer un décalage. exemple : 3");

                btn_crypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_clair.getText().toString();


                        if(mess.isEmpty() && key.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message et un entier dans 'Texte en clair' et 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte en clair'", Toast.LENGTH_SHORT).show();
                        }else if(key.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un entier dans 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else{
                            int decalage = Integer.parseInt(key.getText().toString());
                            Cesar a = new Cesar(mess,decalage,true);
                            resultat.setText(a.getResultat());

                            // Au cas ou si on veut decrypter un mot de l'ASCII extended
                            mess_crypter.setText(resultat.getText());
                        }
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();

                        if(mess.isEmpty() && key.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message et un entier dans 'Texte crypté' et 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte crypté'", Toast.LENGTH_SHORT).show();
                        }else if(key.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un entier dans 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else{
                            int decalage = Integer.parseInt(key.getText().toString());
                            Cesar a = new Cesar(mess,decalage,false);
                            resultat.setText(a.getResultat());
                        }
                    }
                });
            }

            //PROGRAMME VIGENERE
            if(this.getSupportActionBar().getTitle().equals("Vigenère")){

                btn_crypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_clair.getText().toString();
                        String cle = key.getText().toString();
                        if(mess.isEmpty() && cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message et un entier dans 'Texte en clair' et 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte en clair'", Toast.LENGTH_SHORT).show();
                        }else if(cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un mot dans 'Clé'", Toast.LENGTH_SHORT).show();
                        }else {
                            Vigenere a = new Vigenere(mess, cle, true);
                            resultat.setText(a.getResultat());

                            // Au cas ou si on veut decrypter un mot de l'ASCII extended
                            mess_crypter.setText(resultat.getText());
                        }
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        String cle = key.getText().toString();
                        if(mess.isEmpty() && cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message et un entier dans 'Texte crypté' et 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte crypté'", Toast.LENGTH_SHORT).show();
                        }else if(cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un mot dans 'Clé'", Toast.LENGTH_SHORT).show();
                        }else {
                            Vigenere a = new Vigenere(mess, cle, false);
                            resultat.setText(a.getResultat());
                        }
                    }
                });
            }


            //PROGRAMME POLYBE
            if(this.getSupportActionBar().getTitle().equals("Homophone")){
                key.setEnabled(false);
                key.setHint("No key for Homophone Polybe");
                btn_crypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_clair.getText().toString();
                        if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte en clair'", Toast.LENGTH_SHORT).show();
                        }else {
                            Polybe a = new Polybe(mess);

                            //On transforme la matrice + les coordonnées en 1 chaine de caractère.
                            ArrayList<List> cryp = a.getCryptage();
                            String res = "";
                            for (int i = 0; i < cryp.get(0).size(); i++) {
                                res += cryp.get(0).get(i);
                            }
                            for (int i = 0; i < cryp.get(1).size(); i++) {
                                res += cryp.get(1).get(i);
                            }

                            // Le mot crypté
                            resultat.setText(res);

                            // Au cas ou si on veut decrypter un mot de l'ASCII extended
                            mess_crypter.setText(resultat.getText());
                        }
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        if(mess.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte crypté'", Toast.LENGTH_SHORT).show();
                        }else {
                            Polybe a = new Polybe(mess);
                            resultat.setText(a.getDecryptage());
                        }
                    }
                });
            }


            //PROGRAMME Hill
            if(this.getSupportActionBar().getTitle().equals("Hill")){
                key.setInputType(InputType.TYPE_CLASS_NUMBER);
                btn_crypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_clair.getText().toString();
                        String cle = key.getText().toString();
                        if(mess.isEmpty() && cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message et un entier dans 'Texte en clair' et 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte en clair'", Toast.LENGTH_SHORT).show();
                        }else if(cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un chiffre sans espace dans 'Clé '", Toast.LENGTH_SHORT).show();
                        }else if(cle.length() != 5){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer 5 chiffre sans espace dans 'Clé '", Toast.LENGTH_SHORT).show();
                        }else {
                            int m = Integer.parseInt(Character.toString(cle.charAt(0)));
                            int a = Integer.parseInt(Character.toString(cle.charAt(1)));
                            int b = Integer.parseInt(Character.toString(cle.charAt(2)));
                            int c = Integer.parseInt(Character.toString(cle.charAt(3)));
                            int d = Integer.parseInt(Character.toString(cle.charAt(4)));

                            Hill a1 = new Hill(mess, m, a, b, c, d, true);
                            resultat.setText(a1.getCryptage());

                            // Au cas ou si on veut decrypter un mot de l'ASCII extended
                            mess_crypter.setText(resultat.getText());
                        }
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        String cle = key.getText().toString();
                        if(mess.isEmpty() && cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message et un entier dans 'Texte crypté' et 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte crypté'", Toast.LENGTH_SHORT).show();
                        }else if(cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer 5 chiffre sans espace dans 'Clé '", Toast.LENGTH_SHORT).show();
                        }else if(cle.length() != 5){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer 5 chiffre sans espace dans 'Clé '", Toast.LENGTH_SHORT).show();
                        }else {
                            int m = Integer.parseInt(Character.toString(cle.charAt(0)));
                            int a = Integer.parseInt(Character.toString(cle.charAt(1)));
                            int b = Integer.parseInt(Character.toString(cle.charAt(2)));
                            int c = Integer.parseInt(Character.toString(cle.charAt(3)));
                            int d = Integer.parseInt(Character.toString(cle.charAt(4)));

                            Hill a2 = new Hill(mess, m, a, b, c, d, false);
                            resultat.setText(a2.getDecryptage());
                        }
                    }
                });
            }


            //PROGRAMME DES
            if(this.getSupportActionBar().getTitle().equals("DES")){

                btn_crypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_clair.getText().toString();
                        String cle = key.getText().toString();
                        if(mess.isEmpty() && cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message et un entier dans 'Texte en clair' et 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte en clair'", Toast.LENGTH_SHORT).show();
                        }else if(cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un hexadecimal dans 'Clé '", Toast.LENGTH_SHORT).show();
                        }else if(cle.length() != 16){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un hexadecimal de 16 caractères dans 'Clé '", Toast.LENGTH_SHORT).show();
                        }else {
                            DES d = new DES();
                            resultat.setText(d.crypt(mess, cle));

                            // Au cas ou si on veut decrypter un mot de l'ASCII extended
                            mess_crypter.setText(resultat.getText());
                        }
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        String cle = key.getText().toString();
                        if(mess.isEmpty() && cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message et un entier dans 'Texte en clair' et 'Entrer un decalage ... '", Toast.LENGTH_SHORT).show();
                        }else if(mess.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un message dans 'Texte en clair'", Toast.LENGTH_SHORT).show();
                        }else if(cle.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un hexadecimal dans 'Clé '", Toast.LENGTH_SHORT).show();
                        }else if(cle.length() != 16){
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un hexadecimal de 16 caractères dans 'Clé '", Toast.LENGTH_SHORT).show();
                        }else {
                            DES d = new DES();
                            resultat.setText(d.decrypt(mess, cle));
                        }
                    }
                });
            }


            btn_retour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
