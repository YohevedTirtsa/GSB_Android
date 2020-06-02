package com.example.gsb_android;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.System.out;

public class Synthese extends Activity {

    //Propriétes
    DataBase db;
    Date date = new Date();
    String moisActuelle = String.format("%1$tm/%1$tY", date);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.synthese);
        db = new DataBase(this);
        viewData();
        montantTotal();
    }


    /**
     * Affiche les montants issues de la BDD, et les affiche a cote du libelle correspondant
     */
    private void viewData() {
        Cursor cursor = db.viewData();
        Cursor cursorHF = db.viewDataFraisHF();

        //Affichage des Frais Forfaits
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext() && (cursor.getString(1)).equals(moisActuelle)) {
                ((TextView) findViewById(R.id.txtMois)).setText(cursor.getString(1));

                if ((cursor.getString(0)).equals("REP")) {
                    ((TextView) findViewById(R.id.txtRep)).setText(cursor.getString(2));
                } else if ((cursor.getString(0)).equals("ETP")) {
                    ((TextView) findViewById(R.id.txtEtp)).setText(cursor.getString(2));
                } else if ((cursor.getString(0)).equals("NUI")) {
                    ((TextView) findViewById(R.id.txtNui)).setText(cursor.getString(2));
                } else {
                    ((TextView) findViewById(R.id.txtKm)).setText(cursor.getString(2));
                }
            }
        }
        //Affichages des frais Hors Forfaits
        if (cursorHF.getCount() != 0) {
            while (cursorHF.moveToNext() && (cursorHF.getString(1)).equals(moisActuelle)) {
                //Si la 1ere ligne d affichage est vide, alors on affiche date, montant,libelle sur celle ci
                if(((TextView)findViewById(R.id.textViewDate1)).length()==0){
                    ((TextView) findViewById(R.id.textViewDate1)).setText(cursorHF.getString(3)+ "  "+cursorHF.getString(2));
                    ((TextView) findViewById(R.id.textViewMontant1)).setText(" : " +cursorHF.getString(4)+ "€");
                } else if(((TextView)findViewById(R.id.textViewDate2)).length()==0){
                    ((TextView) findViewById(R.id.textViewDate2)).setText(cursorHF.getString(3)+ "  "+cursorHF.getString(2));
                    ((TextView) findViewById(R.id.textViewMontant2)).setText(" : " +cursorHF.getString(4)+ "€");
                } else if(((TextView)findViewById(R.id.textViewDate3)).length()==0){
                    ((TextView) findViewById(R.id.textViewDate3)).setText(cursorHF.getString(3)+ "  "+cursorHF.getString(2));
                    ((TextView) findViewById(R.id.textViewMontant3)).setText(" : " +cursorHF.getString(4)+ "€");
                }
            }
        }
    }


    private void montantTotal(){
        Cursor cursor = db.viewData();
        Cursor cursorHF = db.viewDataFraisHF();
        double somme= 0.00d, rep= 0.00d,etp= 0.00f,nui= 0.00d,km= 0.00d,sommeHF= 0.00d;

        //On parcourt les frais forfaits
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext() && (cursor.getString(1)).equals(moisActuelle)) {
                //on recupere les qunatites des frais qu on multiplie avec le montant correspondant
                if((cursor.getString(0)).equals("REP")){
                    rep= Float.parseFloat(cursor.getString(2))*25;
                }else if((cursor.getString(0)).equals("ETP")){
                    etp= Float.parseFloat(cursor.getString(2))*110;
                }else if((cursor.getString(0)).equals("NUI")){
                    nui= Float.parseFloat(cursor.getString(2))*80;
                }else{
                    km= Float.parseFloat(cursor.getString(2))*0.62;
                }
                somme= rep+etp+nui+km;
            }
            //On parcourt les frais hors forfaits
            if (cursorHF.getCount() != 0) {
                while (cursorHF.moveToNext() && (cursorHF.getString(1)).equals(moisActuelle)) {
                    //on additionne les montants des frais hors forfaits
                    sommeHF = Float.parseFloat(cursorHF.getString(4)) + sommeHF;
                }
            }
        }else{
            ((TextView) findViewById(R.id.txtMontantTotal)).setText("0 €");
        }
        ((TextView) findViewById(R.id.txtMontantTotal)).setText(somme+sommeHF+" €");
    }
}
