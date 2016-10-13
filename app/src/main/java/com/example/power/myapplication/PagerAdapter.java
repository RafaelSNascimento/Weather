package com.example.power.myapplication;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Kaito
 *
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    String[] data = new String[10];
    String[] max = new String[10];
    String[] min = new String[10];



    public void setData(String[] data) {
        this.data = data;
    }



    public void setMax(String[] max) {
        this.max = max;
    }


    public void setMin(String[] min) {
        this.min = min;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment Weather;
        Fragment Maps;
        Fragment Grafico;

        switch (position) {
            case 0 :
                Weather = new WeatherFragment();
                return Weather;
            case 1 : Maps = new MapsFragments();
                return Maps;
            case 2: Grafico = new FragmentoGrafico();
                return Grafico;
            default:
                Weather = new WeatherFragment();
                Weather.setRetainInstance(true);
                return Weather;
        }

    }

    public PagerAdapter(FragmentManager fm) {
        super(fm);

    }

}