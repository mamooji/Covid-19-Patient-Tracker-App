package com.example.dbmarch11;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//CLASS         : mapsActivity
//PURPOSE       : The maps page class for the application.
//                This class, creates the map fragment, as well as pinpoints the location
//                to desplay on the map using long and lat coordinates. defaulted to conestoga campus location
public class mapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;


    //FUNCTION          : onCreate
    //PARAMETERS        : Bundle savedInstanceState
    //RETURNS           : void
    //DESCRIPTION       : Handles the proper filling of the map view
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAPI);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);


    }

    //FUNCTION          : onMapReady
    //PARAMETERS        : GoogleMap googleMap
    //RETURNS           : void
    //DESCRIPTION       : Handles setting the location pin for the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
        LatLng Conestoga = new LatLng(43.479458, -80.519124);

        mapAPI.addMarker(new MarkerOptions().position(Conestoga).title("Conestoga"));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(Conestoga));


    }
}
