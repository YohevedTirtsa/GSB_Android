package com.example.gsb_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

import static java.lang.System.out;

public class Connexion extends Activity {

    private static AccesDistant accesDistant;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        cmdAjout_clic();
        //LireLaBase(((TextView) findViewById(R.id.editLogin)).getText().toString(),((TextView) findViewById(R.id.editMdp)).getText().toString());
    }

    public static void LireLaBase() {

        /* Connexion à la base de données */
        String url = "jdbc:mysql://localhost:81/gsb_frais";
        String utilisateur = "userGsb";
        String motDePasse = "secret";
        Connection connexion = null;
        Statement statement= null;
        ResultSet rs= null;

        try {
            /* Chargement du driver JDBC pour MySQL */
            Class.forName("com.mysql.jdbc.Driver");
            /*Recuperation de la connexion*/
           connexion= DriverManager.getConnection(url, utilisateur, motDePasse);
           /*Etablissement d'un Statement*/
            statement= connexion.createStatement();
            String sql= "SELECT * FROM visiteur";
            /*Execution de la requete*/
            rs= statement.executeQuery(sql);
            /*Parcours du resultat*/
            while   (rs.next()){
                out.println(rs.getString("visiteur"));
                /*
                if(login.equals(rs.getString(3)) && mdp.equals(rs.getString(4))){
                    out.println("bravvvoo");
                }else{
                    //((TextView)findViewById(R.id.txtVerif)).setText("Login ou mot de passe incorect");
                    out.println("login ou mdp incorrect");
                }*/
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                /*Liberer les ressources de la memoire*/
                connexion.close();
                statement.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

    }


    private void cmdAjout_clic() {
        (findViewById(R.id.buttonValider)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                // ouvre l'activité
                Intent intent = new Intent(Connexion.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
