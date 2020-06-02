package com.example.gsb_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

import static java.lang.System.out;

public class DataBase extends SQLiteOpenHelper {

    //Propriétes
    private static final String NOMBDD= "GSB.db";
    private static final String TABLE_FRAIS = "CREATE TABLE FRAIS_FORFAIT (ID TEXT PRIMARY KEY, MOIS TEXT, QUANTITE TEXT)";
    private static final String TABLE_FRAISHF = "CREATE TABLE FRAISHFORFAIT (ID INTEGER PRIMARY KEY AUTOINCREMENT, MOIS TEXT, LIBELLE TEXT, DATE TEXT, MONTANT FLOAT)";


    /**
     * Constructeur
     * @param context
     */
    public DataBase(Context context) {
        super(context, NOMBDD, null, 1);
    }

    /**
     * Cree les tables FRAIS FORFAIT et FRAISHFORFAIT
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_FRAIS);
        db.execSQL(TABLE_FRAISHF);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FRAIS_FORFAIT");
        db.execSQL("DROP TABLE IF EXISTS FRAISHFORFAIT");
        onCreate(db);
    }

    /**
     * Insere les valeurs en parametres dans la table FRAIS FORFAIT
     * @param id
     * @param mois
     * @param quantite
     * @return
     */
    public boolean insertData(String id, String mois, String quantite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("MOIS",mois);
        contentValues.put("QUANTITE",quantite);
        long result = db.insert("FRAIS_FORFAIT", null, contentValues);
        return result != -1;
    }

    /**
     * Insere les valeurs en parametres dans le table FRAISHFORFAIT
     * @param libelle
     * @param date
     * @param montant
     * @return
     */
    public boolean insertDatafraisHF(String mois,String libelle, String date, String montant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MOIS",mois);
        contentValues.put("LIBELLE",libelle);
        contentValues.put("DATE", date);
        contentValues.put("MONTANT", montant);
        long result = db.insert("FRAISHFORFAIT", null, contentValues);
        return result != -1;
    }

    /**
     * Modifie le frais saisi
     * @param id
     * @param mois
     * @param quantite
     * @return
     */
    public boolean modifDatafrais(String id, String mois, String quantite){
        out.println(id+" "+mois+" "+quantite);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("QUANTITE",quantite);
        long result = db.update("FRAIS_FORFAIT", contentValues, "MOIS="+"'"+mois+"'"+" AND ID="+"'"+id+"'", null);
        return result != -1;
    }

    /**
     * Modifie le frais hors forfait saisi
     * @param id
     * @param libelle
     * @param montant
     * @return
     */
    public boolean modifDatafraisHF(String id, String libelle, String montant){
        out.println(id+" "+libelle+" "+montant);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("LIBELLE",libelle);
        contentValues.put("MONTANT",montant);
        long result = db.update("FRAISHFORFAIT", contentValues, "ID="+"'"+id+"'", null);
        return result != -1;
    }

    /**
     * supprime le frais selectionné
     * @param id
     * @param mois
     * @return
     */
    public boolean supprimDatafrais(String id, String mois){
        out.println(id+" "+mois);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("FRAIS_FORFAIT", "MOIS="+"'"+mois+"'"+" AND ID="+"'"+id+"'", null);
        return result != -1;
    }

    /**
     * supprime le frais hors forfait selectionné
     * @param id
     * @return
     */
    public boolean supprimDatafraisHF(String id){
        out.println(id);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("FRAISHFORFAIT","ID="+"'"+id+"'", null);
        return result != -1;
    }

    /**
     * recupere les valeurs de la table FRAIS FORFAIT
     * @return
     */
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from FRAIS_FORFAIT" ;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    /**
     * recupere les valeurs de la table FRAISHFORFAIT
     * @return
     */
    public Cursor viewDataFraisHF(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from FRAISHFORFAIT" ;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
