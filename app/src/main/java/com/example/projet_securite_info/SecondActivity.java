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
                        Atbash a = new Atbash(mess);
                        resultat.setText(a.getResultat());

                        // Au cas ou si on veut decrypter un mot de l'ASCII extended
                        mess_crypter.setText(resultat.getText());
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        Atbash a = new Atbash(mess);
                        resultat.setText(a.getResultat());

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
                        int decalage = Integer.parseInt(key.getText().toString());

                        Cesar a = new Cesar(mess,decalage,true);
                        resultat.setText(a.getResultat());

                        // Au cas ou si on veut decrypter un mot de l'ASCII extended
                        mess_crypter.setText(resultat.getText());
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        int decalage = Integer.parseInt(key.getText().toString());
                        Cesar a = new Cesar(mess,decalage,false);
                        resultat.setText(a.getResultat());
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

                        Vigenere a = new Vigenere(mess,cle,true);
                        resultat.setText(a.getResultat());

                        // Au cas ou si on veut decrypter un mot de l'ASCII extended
                        mess_crypter.setText(resultat.getText());
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        String cle = key.getText().toString();

                        Vigenere a = new Vigenere(mess,cle,false);
                        resultat.setText(a.getResultat());
                    }
                });
            }


            //PROGRAMME POLYBE
            if(this.getSupportActionBar().getTitle().equals("Homophone")){

                btn_crypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_clair.getText().toString();

                        Polybe a = new Polybe(mess);
                        List cryp = a.getCryptage();
                        String res = "";
                        for(int i=0;i<cryp.size();i++){
                            res += cryp.get(i);
                        }
                        resultat.setText(res);

                        // Au cas ou si on veut decrypter un mot de l'ASCII extended
                        mess_crypter.setText(resultat.getText());
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();

                        Polybe a = new Polybe(mess);
                        resultat.setText(a.getDecryptage());
                    }
                });
            }


            //PROGRAMME Hill
            if(this.getSupportActionBar().getTitle().equals("Hill")){

                btn_crypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_clair.getText().toString();
                        String cle = key.getText().toString();
                        int m = Integer.parseInt(Character.toString(cle.charAt(0)));
                        int a = Integer.parseInt(Character.toString(cle.charAt(1)));
                        int b = Integer.parseInt(Character.toString(cle.charAt(2)));
                        int c = Integer.parseInt(Character.toString(cle.charAt(3)));
                        int d = Integer.parseInt(Character.toString(cle.charAt(4)));

                        Hill a1 = new Hill(mess,m,a,b,c,d,true);
                        resultat.setText(a1.getCryptage());

                        // Au cas ou si on veut decrypter un mot de l'ASCII extended
                        mess_crypter.setText(resultat.getText());
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        String cle = key.getText().toString();
                        int m = Integer.parseInt(Character.toString(cle.charAt(0)));
                        int a = Integer.parseInt(Character.toString(cle.charAt(1)));
                        int b = Integer.parseInt(Character.toString(cle.charAt(2)));
                        int c = Integer.parseInt(Character.toString(cle.charAt(3)));
                        int d = Integer.parseInt(Character.toString(cle.charAt(4)));

                        Hill a2 = new Hill(mess,m,a,b,c,d,false);
                        resultat.setText(a2.getDecryptage());
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

                        DES d = new DES();
                        resultat.setText(d.crypt(mess,cle));

                        // Au cas ou si on veut decrypter un mot de l'ASCII extended
                        mess_crypter.setText(resultat.getText());
                    }
                });
                btn_decrypter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mess = mess_crypter.getText().toString();
                        String cle = key.getText().toString();

                        DES d = new DES();
                        resultat.setText(d.decrypt(mess,cle));
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
