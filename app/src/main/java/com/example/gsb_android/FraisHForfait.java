package com.example.gsb_android;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Date;

import static java.lang.System.out;

public class FraisHForfait<fieldValidatorTextWatcher> extends Activity {

    Date date = new Date();
    String moisActuelle = String.format("%1$tm/%1$tY", date);
    DataBase db;
    FraisForfait unFrais = new FraisForfait();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frais_hors_forfait);
        db=new DataBase(this);
        String[] id = viewDataHF();
        btnAjouter_clic();
        cmdModif_clic(id);
        cmdSuppr_clic(id);

        unFrais.rd_synchrony2((EditText)findViewById( R.id.editMontant),(RadioButton)findViewById( R.id.rd1));
        unFrais.rd_synchrony2((EditText)findViewById( R.id.editMontant2),(RadioButton)findViewById( R.id.rd2));
        unFrais.rd_synchrony2((EditText)findViewById( R.id.editMontant3),(RadioButton)findViewById( R.id.rd3));

        //Appel de la fonction rd_synchrony
        unFrais.rd_synchrony((RadioButton)findViewById( R.id.rd1),(EditText) findViewById( R.id.editMontant));
        unFrais.rd_synchrony((RadioButton)findViewById( R.id.rd2),(EditText)findViewById( R.id.editMontant2));
        unFrais.rd_synchrony((RadioButton)findViewById( R.id.rd3),(EditText)findViewById( R.id.editMontant3));
    }


    /**
     *  Methode qui affiche les valeurs saisies par l utilisateur, tt en controlant les données recues
     */
    private void btnAjouter_clic() {
        (findViewById( R.id.btnAjouter)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //on recupere les valeurs saisies
                String Date= ((EditText)findViewById(R.id.editDate)).getText().toString();
                String Libelle= ((EditText)findViewById(R.id.edtLibelle)).getText().toString();
                String Montant= ((EditText)findViewById(R.id.edtQuantite)).getText().toString();

                //on verifie que les champs sont bien remplis
                if(Libelle.equals("") || Montant.equals("") || Date.equals("")){
                    Toast.makeText(FraisHForfait.this, "Les champs doivent etre remplis!", Toast.LENGTH_LONG).show();
                }else {
                    //on retire le slash de la date pour traiter les variables separemment, ce qui donne un tableau a 3 lignes (jour/mois/annee)
                    String[] newDate = Date.split("/");
                    //on recupere la taille du  jour/mois/annee
                    int jourTaille = newDate[0].length();
                    int moisTaille = newDate[1].length();
                    int anneeTaille = newDate[2].length();
                    int anneeActuelle = Integer.valueOf(String.format("%1$tY", date));

                    //on recupere les valeurs du jour, mois & annee
                    Integer jour = Integer.valueOf(newDate[0]);
                    Integer mois = Integer.valueOf(newDate[1]);
                    Integer annee = Integer.valueOf(newDate[2]);

                    //on teste le format de la date
                    if (jour > 31 || mois > 12 || jourTaille != 2 || moisTaille != 2 || anneeTaille != 4 || annee != anneeActuelle ) {
                        Toast.makeText(FraisHForfait.this, "Date saisie incorrecte!", Toast.LENGTH_LONG).show();
                    } else {
                        //on verifie que la ligne d affichage est vide, et sinon on affiche dans la ligne suivante les donnes saisies
                        if(((TextView) findViewById(R.id.editMontant)).length()==0) {
                            //Cette ligne insere dans les BDD les donnees saisies
                            db.insertDatafraisHF(moisActuelle, Libelle, Date, Montant);
                            ((TextView) findViewById(R.id.editMontant)).setText(Libelle + ": " + Montant + "€");
                            ((TextView) findViewById(R.id.Date1)).setText(Date);
                        }else if(((TextView) findViewById(R.id.editMontant2)).length()==0){
                            db.insertDatafraisHF(moisActuelle, Libelle, Date, Montant);
                            ((TextView) findViewById(R.id.editMontant2)).setText(Libelle + ": " + Montant + "€");
                            ((TextView) findViewById(R.id.Date2)).setText(Date);
                        }else if(((TextView) findViewById(R.id.editMontant3)).length()==0){
                            db.insertDatafraisHF(moisActuelle, Libelle, Date, Montant);
                            ((TextView) findViewById(R.id.editMontant3)).setText(Libelle + ": " + Montant + "€");
                            ((TextView) findViewById(R.id.Date3)).setText(Date);
                        }else{
                            Toast.makeText(FraisHForfait.this, "Le nombre maximum de Frais hors Forfait ajouté est atteint! ", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    /**
     * Affiche les montants issues de la BDD, et les affiche a cote du libelle correspondant
     */
    private String[] viewDataHF() {
        String[] tab= new String[3];
        Cursor cursor = db.viewDataFraisHF();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext() && (cursor.getString(1)).equals(moisActuelle)){

                //Si la 1ere ligne d affichage est vide, alors on affiche date, montant,libelle sur celle ci
                if(((TextView)findViewById(R.id.Date1)).length()==0){
                    tab[0] = cursor.getString(0);
                    ((TextView) findViewById(R.id.Date1)).setText(cursor.getString(3));
                    ((TextView) findViewById(R.id.editMontant)).setText(cursor.getString(2)+ ": "+ cursor.getString(4)+ "€");
                } else if(((TextView)findViewById(R.id.Date2)).length()==0){
                    tab[1]= cursor.getString(0);
                    ((TextView) findViewById(R.id.Date2)).setText(cursor.getString(3));
                    ((TextView) findViewById(R.id.editMontant2)).setText(cursor.getString(2)+ ": "+ cursor.getString(4)+ "€");
                } else if(((TextView)findViewById(R.id.Date3)).length()==0){
                    tab[2]= cursor.getString(0);
                    ((TextView) findViewById(R.id.Date3)).setText(cursor.getString(3));
                    ((TextView) findViewById(R.id.editMontant3)).setText(cursor.getString(2)+ ": "+ cursor.getString(4)+ "€");
                }
            }
        }
        return tab;
    }


    private  void cmdModif_clic(final String[] id) {
        (findViewById( R.id.btnModif)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //on recupere le tableau recu en parametre contenant les id, de chaque fraisHfprfait
                String id1= id[0];
                String id2= id[1];
                String id3= id[2];

                String edit1 = ((TextView) findViewById(R.id.editMontant)).getText().toString();
                String edit2 = ((TextView) findViewById(R.id.editMontant2)).getText().toString();
                String edit3 = ((TextView) findViewById(R.id.editMontant3)).getText().toString();

                //le split permet de separer les variables lorsqu il y a un ":", on recupere donc le libelle
                String[] libelle = edit1.split(":");
                String[] libelle2 = edit2.split(":");
                String[] libelle3 = edit3.split(":");
                //le split permet de separer les variables lorsqu il y a un "€", on recupere donc le montant
                String[] montant = libelle[1].split("€");
                String[] montant2 = libelle2[1].split("€");
                String[] montant3 = libelle3[1].split("€");

                    if (((RadioButton) findViewById(R.id.rd1)).isChecked() && !edit1.equals("")) {
                        db.modifDatafraisHF(id1, libelle[0], montant[0]);
                        Toast.makeText(FraisHForfait.this, "Frais Modifié", Toast.LENGTH_SHORT).show();
                    } else if (((RadioButton) findViewById(R.id.rd2)).isChecked() && !edit2.equals("")) {
                        db.modifDatafraisHF(id2, libelle2[0], montant2[0]);
                        Toast.makeText(FraisHForfait.this, "Frais Modifié", Toast.LENGTH_SHORT).show();
                    } else if (!edit3.equals("")) {
                        db.modifDatafraisHF(id3, libelle3[0], montant3[0]);
                        Toast.makeText(FraisHForfait.this, "Frais Modifié", Toast.LENGTH_SHORT).show();
                    }
                }

        });
    }

    /**
     * Suupprime le frais hors forfait selctionné
     * @param id
     */
    private  void cmdSuppr_clic(final String[] id) {
        (findViewById( R.id.btnSupprHF)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //on recupere le tableau recu en parametre contenant les id, de chaque fraisHForfait
                String id1= id[0];
                String id2= id[1];
                String id3= id[2];

                String edit1 = ((TextView) findViewById(R.id.editMontant)).getText().toString();
                String edit2 = ((TextView) findViewById(R.id.editMontant2)).getText().toString();
                String edit3 = ((TextView) findViewById(R.id.editMontant3)).getText().toString();

                if (((RadioButton) findViewById(R.id.rd1)).isChecked() && !edit1.equals("")) {
                    db.supprimDatafraisHF(id1);
                    Toast.makeText(FraisHForfait.this, "Frais Supprimé", Toast.LENGTH_SHORT).show();
                } else if (((RadioButton) findViewById(R.id.rd2)).isChecked() && !edit2.equals("")) {
                    db.supprimDatafraisHF(id2);
                    Toast.makeText(FraisHForfait.this, "Frais Supprimé", Toast.LENGTH_SHORT).show();
                } else if (!edit3.equals("")) {
                    db.supprimDatafraisHF(id3);
                    Toast.makeText(FraisHForfait.this, "Frais Supprimé", Toast.LENGTH_SHORT).show();
                }
                //on supprime tout et on reafficeh tout afin de mettre a jour la page
                ((TextView) findViewById(R.id.Date1)).setText("");
                ((TextView) findViewById(R.id.editMontant)).setText("");
                ((TextView) findViewById(R.id.Date2)).setText("");
                ((TextView) findViewById(R.id.editMontant2)).setText("");
                ((TextView) findViewById(R.id.Date3)).setText("");
                ((TextView) findViewById(R.id.editMontant3)).setText("");
                viewDataHF();
            }
        });
    }
}
