package com.example.power.myapplication;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Kaito
 *
 */
public class HomeActivity extends FragmentActivity implements OnPageChangeListener, TabListener, OnCommunicateInterface {

    private ActionBar actionbar;
    private ViewPager viewpager;
    static int[] images = new int[10];
    static String[] Max = new String[10];
    static String[] Min = new String[10];
    static String[] Data = new String[10];
    static String[] Text = new String[10];
    static String Pais="Brasil";
    static String Estado="São Paulo";
    static List<Tempo> tempo;
    private Connection con = new Connection();
    PagerAdapter adapter;
    public static Context context;
    private Firebase firebase;

    private FireBase fireBase;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.home_activity);

        fireBase = new FireBase();
        firebase = FireBase.getFirebase();

        firebase.keepSynced(true);
        firebase.addValueEventListener(fireBase);

        context = getApplicationContext();
        adapter = new PagerAdapter(getSupportFragmentManager());
        actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewpager = (ViewPager) findViewById(R.id.home_activity_viewpager);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(this);
        viewpager.setOffscreenPageLimit(3);



        Tab movie = actionbar.newTab().setText("Clima").setTabListener(this);
        Tab music = actionbar.newTab().setText("Local").setTabListener(this);
        Tab grafico = actionbar.newTab().setText("Grafico").setTabListener(this);

        actionbar.addTab(movie);
        actionbar.addTab(music);
        actionbar.addTab(grafico);



    }
    public void PreencheList(String Pais, String Estado){
        try {

            FireBase.goSave(con.getData(Pais, Estado));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static Context getContext(){
        return context;
    }

    /* TabListener */
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        //...
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // Muda o fragment do ViewPager ao selecionar
        // uma nova aba
        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        //...
    }

    /* OnPageChangeListener */
    @Override
    public void onPageScrollStateChanged(int state) {
        //...
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //...
    }

    @Override
    public void onPageSelected(int position) {
        // Muda a aba selecionada ao trocar de fragment
        // com o ViewPager
        actionbar.setSelectedNavigationItem(position);
    }

    @Override
    public void onSetText(String pais, String estado) {
        Pais = pais;
        Estado = estado;
        PreencheList(pais,estado);
    }
}

