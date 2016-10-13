
package com.example.power.myapplication;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.power.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * @author Kaito
 *
 */
public class MapsFragments extends Fragment implements OnMapReadyCallback {
    OnCommunicateInterface onCommunicate;
    GoogleMap mMap;
    EditText editText;
    Double Lat;
    Double Lon;
    ImageButton btnSearch;
    String TAG = "";
    View view;
    private LocationManager locationManager;
    SupportMapFragment supportMapFragment;

    public MapsFragments() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        editText = (EditText) view.findViewById(R.id.editText);
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment =  SupportMapFragment.newInstance();
        btnSearch =(ImageButton) view.findViewById(R.id.Button);
        btnSearch.setOnClickListener(btnListener);
        fm.beginTransaction().replace(R.id.mapView, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);
        return view;
    }
    private View.OnClickListener btnListener = new View.OnClickListener()
    {

        public void onClick(View v)
        {
            if(!editText.getText().equals("") || editText.getText()!=null) onSearch();
            else Toast.makeText(getActivity(), "Preencha o local!", Toast.LENGTH_LONG).show();
        }

    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
            onCommunicate = (OnCommunicateInterface) context;
        }
        catch (Exception e)
        {
            Log.e("onAttach",e.toString());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            Toast.makeText(getActivity(), "Provider: "+ provider, Toast.LENGTH_LONG).show();

            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);

        }catch(SecurityException ex){
            Log.e(TAG, "Error", ex);
        }
    }
    public void onSearch() {
        String location = editText.getText().toString();
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(view.getContext());
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList!=null) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(("Marker")));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                Lat = address.getLatitude();
                Lon = address.getLongitude();
                try {
                    List<Address> ad;
                    ad = geocoder.getFromLocation(Lat, Lon, 1);
                    Address ads = ad.get(0);
                    onCommunicate.onSetText(ads.getCountryName(), address.getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}