package org.zeero.coffeeshopmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentOne extends Fragment {


	private GoogleMap googleMap;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		 try {
	            // Loading map
	            initilizeMap();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		

		View view = inflater.inflate(R.layout.fragment_layout_one, container,
				false);

		return view;
	}
	
	@Override
	public void onPause() {

	    Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.map);        
	    if (f != null) {
	        getFragmentManager().beginTransaction().remove(f).commit();
	    }

	    super.onPause();
	}

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                //Toast.makeText(getApplicationContext(),
                       // "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        //.show();
            }
        }
        
        googleMap.setMyLocationEnabled(true);
        
        googleMap.addMarker(new MarkerOptions().position(new LatLng(51.899442, -8.4676808)).title("Costa Coffee").snippet("Merchants Quay"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(51.897787, -8.4690005)).title("Mahers Pure Coffee").snippet("Oliver Plunket Street"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(51.8976877,-8.4696657)).title("Butlers Chocolate Cafe").snippet("Oliver Plunket Street"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(51.8967939,-8.4735388)).title("Caseys Cafe").snippet("Grand Parade"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(51.8977406,-8.4748906)).title("Cafe Gusto").snippet("Washington Street"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(51.9001173,-8.4715754)).title("Costa Coffee").snippet("Emmett Place"));
       final Marker Sugar = googleMap.addMarker(new MarkerOptions().position(new LatLng(51.896008,-8.473426)).title("The Sugar Cube").snippet("Grand Parade"));
        
        googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker arg0) {
				Intent intent = new Intent(getActivity(), InfoPage.class);
				intent.putExtra("Latitude", arg0.getPosition().latitude);
				intent.putExtra("Longitude", arg0.getPosition().longitude);
				Log.d("latitude", "" + arg0.getPosition().latitude);
			    startActivity(intent);
				
			}
        });
    }
            
 
    @Override
	public void onResume() {
        super.onResume();
        initilizeMap();
    }

}

