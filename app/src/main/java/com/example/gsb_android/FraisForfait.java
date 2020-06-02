package com.example.gsb_android;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import static java.lang.System.out;

public class FraisForfait extends Activity {

    //Propriétes
    Date date = new Date();
    String moisActuelle = String.format("%1$tm/%1$tY", date);
    DataBase db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frais_forfait);
        db=new DataBase(this);
        viewData();
        cmdAjouter_clic();
        cmdModif_clic();
        cmdSuppr_clic();
        //Appel de la fonction rd_synchrony2
        rd_synchrony2((EditText)findViewById( R.id.editEtp),(RadioButton)findViewById( R.id.rdEtp));
        rd_synchrony2((EditText)findViewById( R.id.editRep),(RadioButton)findViewById( R.id.rdRep));
        rd_synchrony2((EditText)findViewById( R.id.editNui),(RadioButton)findViewById( R.id.rdNui));
        rd_synchrony2((EditText)findViewById( R.id.editKm),(RadioButton)findViewById( R.id.rdKm));
        //Appel de la fonction rd_synchrony
        rd_synchrony((RadioButton)findViewById( R.id.rdEtp),(EditText)findViewById( R.id.editEtp));
        rd_synchrony((RadioButton)findViewById( R.id.rdRep),(EditText)findViewById( R.id.editRep));
        rd_synchrony((RadioButton)findViewById( R.id.rdNui),(EditText)findViewById( R.id.editNui));
        rd_synchrony((RadioButton)findViewById( R.id.rdKm),(EditText)findViewById( R.id.editKm));
    }


    private void cmdAjouter_clic() {
        ( findViewById( R.id.btnAjouter)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                TextView editKm= findViewById(R.id.editKm);
                TextView editRep= findViewById(R.id.editRep);
                TextView editNui= findViewById(R.id.editNui);
                TextView editEtp= findViewById(R.id.editEtp);

                // On determine quel item a ete selectionne dans le spinner
                String typeFrais = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
                //on recupere la valeur saisie
                String valeur = ((EditText)findViewById(R.id.edtQuantite)).getText().toString();

                //en fonction de l'item selectionne, la valeur saisie est affichee dans la zone correspondante
                if (typeFrais.equals("KM") && editKm.length()==0) {
                    editKm.setText(valeur);
                } else if (typeFrais.equals("REP") && editRep.length()==0){
                    editRep.setText(valeur);
                }else if (typeFrais.equals("NUI") && editNui.length()==0){
                    editNui.setText(valeur);
                }else if(editEtp.length()==0){
                    editEtp.setText(valeur);
                }

                if (!valeur.equals("") && db.insertData(typeFrais,moisActuelle,valeur)) {
                    Toast.makeText(FraisForfait.this, "Frais ajouté!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(FraisForfait.this, "Frais déjà ajouté!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * Lorsqu on clique sur un button radio, la zone de texte correspondante est selectionnée
     * @param rdButton
     * @param text
     */
    public void rd_synchrony(final RadioButton rdButton, final EditText text){
        rdButton.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v) {
                if(rdButton.isChecked()){
                    text.requestFocus();
                }
            }
        });
    }

    /**
     * Lorsqu on clique sur la zone de texte, le radio button correspondant est coché
     * @param text
     * @param rdButton
     */
    public void rd_synchrony2(final EditText text, final RadioButton rdButton){
        text.setOnFocusChangeListener(new TextView.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                rdButton.setChecked(true);
            }
        });
    }

    /**
     * Cette fonction permet de modifier dans la BDD les donnes saisies, si l on appuie sir le boutton modifier
     */
    private  void cmdModif_clic() {
        (findViewById( R.id.btnModif)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                String editKm= ((TextView) findViewById(R.id.editKm)).getText().toString();
                String editRep= ((TextView) findViewById(R.id.editRep)).getText().toString();
                String editEtp= ((TextView) findViewById(R.id.editEtp)).getText().toString();
                String editNui= ((TextView) findViewById(R.id.editNui)).getText().toString();

                if(((RadioButton)findViewById(R.id.rdKm)).isChecked() && !editKm.equals("")){
                   db.modifDatafrais("KM",moisActuelle,editKm);
                   Toast.makeText(FraisForfait.this, "Frais Modifié", Toast.LENGTH_SHORT).show();
                }else if(((RadioButton)findViewById(R.id.rdRep)).isChecked() && !editRep.equals("")){
                    db.modifDatafrais("REP",moisActuelle,editRep);
                    Toast.makeText(FraisForfait.this, "Frais Modifié", Toast.LENGTH_SHORT).show();
                }else if(((RadioButton)findViewById(R.id.rdEtp)).isChecked() && !editEtp.equals("")){
                    db.modifDatafrais("ETP",moisActuelle,editEtp);
                    Toast.makeText(FraisForfait.this, "Frais Modifié", Toast.LENGTH_SHORT).show();
                }else if(!editNui.equals("")){
                    db.modifDatafrais("NUI",moisActuelle,editNui);
                    Toast.makeText(FraisForfait.this, "Frais Modifié", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void cmdSuppr_clic(){
        (findViewById( R.id.btnSuppr)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                String editKm= ((TextView) findViewById(R.id.editKm)).getText().toString();
                String editRep= ((TextView) findViewById(R.id.editRep)).getText().toString();
                String editEtp= ((TextView) findViewById(R.id.editEtp)).getText().toString();
                String editNui= ((TextView) findViewById(R.id.editNui)).getText().toString();

                if(((RadioButton)findViewById(R.id.rdKm)).isChecked() && !editKm.equals("")){
                    db.supprimDatafrais("KM",moisActuelle);
                    ((TextView)findViewById(R.id.editKm)).setText("");
                    Toast.makeText(FraisForfait.this, "Frais Supprimé", Toast.LENGTH_SHORT).show();
                }else if(((RadioButton)findViewById(R.id.rdRep)).isChecked() && !editRep.equals("")){
                    db.supprimDatafrais("REP",moisActuelle);
                    ((TextView)findViewById(R.id.editRep)).setText("");
                    Toast.makeText(FraisForfait.this, "Frais Supprimé", Toast.LENGTH_SHORT).show();
                }else if(((RadioButton)findViewById(R.id.rdEtp)).isChecked() && !editEtp.equals("")){
                    db.supprimDatafrais("ETP",moisActuelle);
                    ((TextView)findViewById(R.id.editEtp)).setText("");
                    Toast.makeText(FraisForfait.this, "Frais Supprimé", Toast.LENGTH_SHORT).show();
                }else if(!editNui.equals("")){
                    db.supprimDatafrais("NUI",moisActuelle);
                    ((TextView)findViewById(R.id.editNui)).setText("");
                    Toast.makeText(FraisForfait.this, "Frais Supprimé", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Affiche les montants issues de la BDD, et les affiche a cote du libelle correspondant
     */
    private void viewData() {
        Cursor cursor = db.viewData();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){

                if((cursor.getString(0)).equals("REP")){
                    ((TextView)findViewById(R.id.editRep)).setText(cursor.getString(2));
                }else if((cursor.getString(0)).equals("ETP")){
                    ((TextView)findViewById(R.id.editEtp)).setText(cursor.getString(2));
                }else if((cursor.getString(0)).equals("NUI")){
                    ((TextView)findViewById(R.id.editNui)).setText(cursor.getString(2));
                }else {
                    ((TextView)findViewById(R.id.editKm)).setText(cursor.getString(2));
                }
            }
        }
    }
}
