
package com.example.power.myapplication;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.io.InputStreamReader;
import java.util.LinkedList;
import org.json.JSONObject;

public class Connection{
	// HTTP GET request
	public List<Tempo> getData(String pais, String estado) throws JSONException {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);


			final StringBuilder result = new StringBuilder();

			URL url;
			HttpURLConnection urlConnection = null;
			try {

				String query= "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"" +pais+","+estado+"\")";
                String url1 = "http://query.yahooapis.com/v1/public/yql?q="+ URLEncoder.encode(query, "UTF-8") +"&format=json";
                url = new URL(url1);

				urlConnection = (HttpURLConnection) url
						.openConnection();

				InputStream in = urlConnection.getInputStream();

				InputStreamReader isw = new InputStreamReader(in);

				int data = isw.read();
				while (data != -1) {
					char current = (char) data;
					data = isw.read();
					result.append(current);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					urlConnection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<Tempo> finalResult = generateJSON(result.toString());
			return finalResult;
		}

	public List<Tempo> generateJSON(String json){
        List<Tempo> found = new LinkedList<Tempo>();
		try {
			JSONObject trendList = new JSONObject(json);
			if(trendList.getJSONObject("query").optJSONObject("results")!=null){

				JSONObject trendList1 = trendList.getJSONObject("query").optJSONObject("results");


            JSONObject trendList2 = trendList1.optJSONObject("channel");
            JSONObject trendList3 = trendList2.optJSONObject("item");
            JSONArray trendList4 = trendList3.optJSONArray("forecast");
			for (int i = 0; i < trendList4.length(); i++) {
				double maxC = (Double.parseDouble(trendList4.getJSONObject(i).getString("high"))-32) / 1.8;
				double minC = (Double.parseDouble(trendList4.getJSONObject(i).getString("low"))-32) / 1.8;
                found.add(new Tempo(trendList4.getJSONObject(i).getString("date"),
                        trendList4.getJSONObject(i).getString("day"),
						String.valueOf(Math.round(minC)),
						String.valueOf(Math.round(maxC)),
                        trendList4.getJSONObject(i).getString("text"),
						trendList4.getJSONObject(i).getInt("code")));

			}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

        return found;
	}

}
	
