package com.example.requisicoeshttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button recuperar;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recuperar = findViewById(R.id.buttonRecuperarDados);
        result = findViewById(R.id.resultado);

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mytask task = new Mytask();
                String UrlApi = "https://blockchain.info/ticker";

                String cep = "01310100";
                String urlCep = "https://viacep.com.br/ws/"+cep+"/json/";

                task.execute(UrlApi);
            }
        });

    }

    class Mytask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL(stringUrl);
               HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                //Recupera dados em Bytes
               inputStream = conexao.getInputStream();

               //Lê os dados em bytes e docotifica para caracteres
               inputStreamReader = new InputStreamReader(inputStream);


               //Objeto utilicado para leitura dos caracteres do inputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

              while((linha =  reader.readLine()) != null){
                  buffer.append(linha);
              }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            String logradouro = null;
//            String cep = null;
//            String bairro = null;
//            String localidade= null;
            String brasil = null;
            String valorMoeda = null;
            String simbolo = null;


            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                brasil = jsonObject.getString("BRL");
                JSONObject jsonObjectReal = new JSONObject(brasil);

                valorMoeda = jsonObjectReal.getString("last");
                simbolo = jsonObjectReal.getString("symbol");

//                logradouro = jsonObject.getString("logradouro");
//                cep = jsonObject.getString("cep");
//                bairro = jsonObject.getString("bairro");
//                localidade = jsonObject.getString("localidade");

            } catch (JSONException e) {
                e.printStackTrace();
            }


           // result.setText(cep +"\n"+logradouro+"\n" + bairro+"\n" + localidade);
            result.setText(simbolo + valorMoeda);

        }
    }
}