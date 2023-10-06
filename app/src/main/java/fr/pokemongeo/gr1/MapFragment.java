package fr.pokemongeo.gr1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import fr.pokemongeo.gr1.databinding.MapFragmentBinding;


public class MapFragment extends Fragment {
    private org.osmdroid.views.MapView mapView;
    private MapFragmentBinding binding;
    private MyLocationNewOverlay myLocationOverlay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.map_fragment, container, false);
        View rootView = binding.getRoot();
        Context context = rootView.getContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        mapView = rootView.findViewById(R.id.mapView); // Récupérer la référence du MapView à partir du layout
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mapView.getController().setZoom(18);
        // Initialiser le MapView avec la source de tuiles
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setupLocation();
        } else {
            // Demander la permission de localisation
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        return rootView; // Retourner la vue racine
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
        if (myLocationOverlay != null)
            myLocationOverlay.enableMyLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
        if (myLocationOverlay != null)
            myLocationOverlay.disableMyLocation();
    }

    private void setupLocation() {
        Context context = requireContext();
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), mapView);
        mLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(mLocationOverlay);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        LocationListener myLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location newLocation) {
                // Mettre à jour la position du marker en fonction de la nouvelle localisation
                if (mapView != null && newLocation != null) {
                    double latitude = newLocation.getLatitude();
                    double longitude = newLocation.getLongitude();

                    // Centrer la carte sur la nouvelle position de l'utilisateur
                    mapView.getController().setCenter(new org.osmdroid.util.GeoPoint(latitude, longitude));
                }
            }

            // Autres méthodes de l'interface LocationListener
        };

        // Demander les mises à jour de localisation
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120, 100, myLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120, 100, myLocationListener);
    }
}
