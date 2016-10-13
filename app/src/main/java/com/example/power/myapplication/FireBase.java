package com.example.power.myapplication;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Power on 01/09/2016.
 */
public class FireBase implements ValueEventListener {
    private static Firebase firebase;

    public static Firebase getFirebase(){
        if(firebase == null){
            firebase = new Firebase("https://weather-4e645.firebaseio.com/");
        }
        return firebase;
    }


    public static void goSave(List<Tempo> tempo){
        //Criando o mapa para adicionar o forecast inteiro
        final Map<String, Map<String, String>> Tempo = new HashMap<String, Map<String, String>>();
        //vou percorrer o mapa e passar os itens com chave e valor
        int i = 0;
        for(Tempo marker: tempo){
            //Criando o mapa para adicionar os dias
            Map<String, String> Dia = new HashMap<String, String>();

            Dia.put("Max",marker.getHigh());
            Dia.put("Min",marker.getLow());
            Dia.put("Condition",marker.getText());
            Dia.put("Code", String.valueOf(marker.getCode()));
            i=i+1;
            Tempo.put(marker.getData(), Dia);
        }

        Map<String, String> local = new HashMap<String, String>();
        local.put("Pais", HomeActivity.Pais);
        local.put("Estado", HomeActivity.Estado);

        Map<String,Object> finalMap = new HashMap<>();
        finalMap.put("Clima",Tempo);
        finalMap.put("Local",local);
        firebase.setValue(finalMap);
        Toast.makeText(HomeActivity.getContext(), "Local Definido como Padrão: "+ HomeActivity.Pais +" - "+HomeActivity.Estado, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
            Map<String, String> Local = new TreeMap<String, String>();
            Local = dataSnapshot.child("Local").getValue(TreeMap.class);
            if (Local != null) {
                HomeActivity.Pais = Local.get("Pais");
                HomeActivity.Estado = Local.get("Estado");
                Map<String, Map<String, String>> Tempo = new TreeMap<String, Map<String, String>>();
                Tempo = dataSnapshot.child("Clima").getValue(TreeMap.class);
                if (Tempo != null) {
                    int i = 0;
                    for (Map.Entry<String, Map<String, String>> tempo : Tempo.entrySet()) {
                        Map<String, String> Dia = tempo.getValue();
                        HomeActivity.Data[i] = ("Date: " + tempo.getKey());
                        HomeActivity.Max[i] = ("Max: " + Dia.get("Max"));
                        HomeActivity.Min[i] = ("Min: " + Dia.get("Min"));
                        HomeActivity.Text[i] = ("Condition:" + Dia.get("Condition"));
                        HomeActivity.images[i] = (Integer.parseInt(Dia.get("Code")));
                        i += 1;
                    }
                    WeatherFragment.Refresh();
                    FragmentoGrafico.Refresh();
                }
            }
        }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
