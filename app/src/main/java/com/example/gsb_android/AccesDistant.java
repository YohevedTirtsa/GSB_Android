package com.example.gsb_android;

import android.util.Log;

import org.json.JSONArray;

public class AccesDistant implements AsyncResponse {

    //constante
    private static final String SERVEURADDR= "http://192.168.0.22:81/GSB_Android/serveur.php";

    public AccesDistant(){
        super();
    }
    /**
     * retour au serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        Log.d("serveur","*********"+output);
        //decoupage du message recu
        String[] message= output.split("%");
        //s'il y a 2 cases
        if(message.length>1){
            if(message[0].equals("comparaison")){
                Log.d("comparaison","*********"+message[1]);
            }
        }
    }

    public void envoi(String operation, JSONArray lesDonneesJSON){
        AccesHTTP accesDonnees = new AccesHTTP();
        //lien de delégation
        accesDonnees.delegate = this;
        //ajout parametre
        accesDonnees.addParam("operation",operation);
        accesDonnees.addParam("lesDonnees",lesDonneesJSON.toString());
        //appel au serveur
        accesDonnees.execute(SERVEURADDR);
    }
}
