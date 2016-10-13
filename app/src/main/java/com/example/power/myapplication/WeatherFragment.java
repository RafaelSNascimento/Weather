package com.example.power.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author Kaito
 *
 */

public class WeatherFragment extends Fragment {

    static ListView list;
    static String[] data = new String[10];
    static String[] max = new String[10];
    static String[] text = new String[10];
    static String[] min = new String[10];
    static int[] images = new int[10];
    static String Pais = "";
    static String Estado = "";
    static View view;
    static TextView local;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        local = (TextView) view.findViewById(R.id.txtLocal);
        Resources  res = getResources();
        list = (ListView) view.findViewById(R.id.listView);
        return view;
    }
    public static void Refresh(){
        Pais = HomeActivity.Pais;
        Estado = HomeActivity.Estado;
        local.setText(Pais+" - "+Estado);
        data = HomeActivity.Data;
        max = HomeActivity.Max;
        text = HomeActivity.Text;
        min = HomeActivity.Min;
        images = HomeActivity.images;
        VivzAdapter adapter = new VivzAdapter(view.getContext(), data, max, min, images, text);
        list.setAdapter(adapter);
    }
    static class VivzAdapter extends ArrayAdapter<String>{
        Context context;

        String [] arrayData;
        String [] arrayMax;
        String [] arrayMin;
        String [] arrayText;
        int [] images;
       VivzAdapter(Context c, String[] data, String[] max, String[] min, int[] img, String[] text){
           super(c, R.layout.single_row, R.id.txtDate, data);
           this.context = c;
           this.arrayText = text;
           this.arrayData = data;
           this.arrayMax = max;
           this.arrayMin = min;
           this.images = img;
       }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.single_row, parent, false);

            TextView Date = (TextView) row.findViewById(R.id.txtDate);
            TextView Max = (TextView) row.findViewById(R.id.txtMax);
            TextView Min = (TextView) row.findViewById(R.id.txtMin);
            TextView Status = (TextView) row.findViewById(R.id.txtStatus);
            try {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //your codes here
                    String url = "http://l.yimg.com/a/i/us/we/52/"+images[position]+".gif";
                    ImageView myImage = (ImageView) row.findViewById(R.id.imageView);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
                    myImage.setImageBitmap(bitmap);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Date.setText(arrayData[position]);
            Max.setText(arrayMax[position]);
            Min.setText(arrayMin[position]);
            Status.setText(arrayText[position]);
            return row;
        }

    }
    public void goSave(){
        FireBase.goSave(HomeActivity.tempo);
    }

}