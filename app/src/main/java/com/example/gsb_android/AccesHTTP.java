package com.example.gsb_android;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AccesHTTP extends AsyncTask<String,Integer,Long> {

    private ArrayList<NameValuePair> parametres;
    private String ret=null;
    public AsyncResponse delegate = null;
    /**
     * Constrcuteur
     */
    public AccesHTTP(){
        parametres =new ArrayList<NameValuePair>();
    }

    /**
     * Ajout d'un parametre POST
     * @param nom
     * @param valeur
     */
    public  void addParam(String nom, String valeur){
        parametres.add(new BasicNameValuePair(nom,valeur));
    }

    /**
     * Connexion en tâche de fond dans un thread séparé
     * @param strings
     * @return
     */
    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHttp = new DefaultHttpClient();
        HttpPost paramCnx = new HttpPost(strings[0]);

        try {
            //encodage des parametres
            paramCnx.setEntity(new UrlEncodedFormEntity(parametres));
            //connexion et envoi des parmetre, attente de reponse
            HttpResponse reponse= cnxHttp.execute(paramCnx);
            //Transformation de la reponse
            ret = EntityUtils.toString(reponse.getEntity());

        } catch (UnsupportedEncodingException e) {
            Log.d("Erreur d'encodage","**********"+e.toString());
        } catch (ClientProtocolException e) {
            Log.d("Erreur de protocole","**********"+e.toString());
        } catch (IOException e) {
            Log.d("Erreur I/O","**********"+e.toString());
        }

        return null;
    }
    @Override
    protected void onPostExecute(Long result){
        delegate.processFinish(ret.toString());
    }
}
