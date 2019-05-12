package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText numeroDeControl, contrasenia;
    Button ingresar;
    RequestQueue solicitud;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numeroDeControl = findViewById(R.id.numeroControl);
        contrasenia = findViewById(R.id.pass);
        ingresar = findViewById(R.id.botonEntrar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuario("https://nitrogenous-trip.000webhostapp.com/index.php?usuario="+numeroDeControl.getText()+"&pass="+contrasenia.getText());
            }
        });
    }
    public void buscarUsuario(String URL){
        JsonArrayRequest peticionJason = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject object = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        object = response.getJSONObject(i);
                       prueba();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Access denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Access denied", Toast.LENGTH_SHORT).show();
            }
        });
        solicitud = Volley.newRequestQueue(this);
        solicitud.add(peticionJason);
    }
    public void prueba(){
        Intent intent = new Intent(this, StartedSession.class);
        String welcomeMessage = numeroDeControl.getText().toString();
        intent.putExtra("sendMessage", welcomeMessage);
        startActivity(intent);

    }
}
