package com.example.gsb_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cmdMenu_clic(((Button) findViewById(R.id.btnFraisForfait)), FraisForfait.class);
        cmdMenu_clic(((Button) findViewById(R.id.btnFraisHForfait)), FraisHForfait.class);
        cmdMenu_clic(((Button) findViewById(R.id.btnSynthese)), Synthese.class);
    }

    /**
     * Sur la sélection d'un bouton dans l'activité principale ouverture de l'activité correspondante
     */
    private void cmdMenu_clic(Button button, final Class classe) {
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // ouvre l'activité
                Intent intent = new Intent(MainActivity.this, classe);
                startActivity(intent);
            }
        });
    }


}
