package fr.pokemongeo.gr1;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import fr.pokemongeo.gr1.databinding.MapFragmentBinding;


public class MapFragment extends Fragment {
    private MapFragmentBinding binding;
    private MyLocationNewOverlay myLocationOverlay;
    private boolean spawnPokemonHandlerPosted = false;
    private final Handler handler = new Handler();
    private final Runnable spawnPokemonRunnable = new Runnable() {
        @Override
        public void run() {
            spawnRandomPokemon(myLocationOverlay.getMyLocation());
            handler.postDelayed(this, 30000); // Attendre 30 secondes avant la prochaine apparition
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.map_fragment, container, false);
        View rootView = binding.getRoot();
        Context context = rootView.getContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        binding.mapView.setTileSource(TileSourceFactory.MAPNIK); // Fix the method call here

        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), binding.mapView) {
            @Override
            public void onLocationChanged(Location location, IMyLocationProvider source) {
                super.onLocationChanged(location, source);
                if (location != null) {
                    GeoPoint myLocation = new GeoPoint(location);
                    binding.mapView.getController().animateTo(myLocation);
                    if (!spawnPokemonHandlerPosted) {
                        spawnPokemonHandlerPosted = true;
                        handler.post(spawnPokemonRunnable);
                    }
                }
            }
        };
        myLocationOverlay.enableMyLocation();
        binding.mapView.getOverlays().add(myLocationOverlay);
        binding.mapView.getController().setZoom(18);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
        if (myLocationOverlay != null) {
            myLocationOverlay.enableMyLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
        if (myLocationOverlay != null) {
            myLocationOverlay.disableMyLocation();
        }
    }

    public GeoPoint generateRandomLocation(GeoPoint playerLocation, double radius) {
        // Conversion du rayon en degrés de latitude et longitude
        double latDegrees = Math.toDegrees(radius / 111319.9); // Environ 111,320 mètres par degré de latitude
        double lonDegrees = Math.toDegrees(radius / (111319.9 * Math.cos(Math.toRadians(playerLocation.getLatitude()))));

        double lat = playerLocation.getLatitude() + (Math.random() * 2 - 1) * latDegrees;
        double lon = playerLocation.getLongitude() + (Math.random() * 2 - 1) * lonDegrees;

        return new GeoPoint(lat, lon);
    }
    public void spawnRandomPokemon(GeoPoint playerPosition) {
        // Générez un emplacement aléatoire autour du joueur
        GeoPoint randomLocation = generateRandomLocation(playerPosition, 1);

        // Sélectionnez un Pokémon aléatoire depuis votre base de données
        Pokemon randomPokemon = getRandomPokemonFromDatabase();

        // Ajoutez le Pokémon sur la carte
        Marker marker = new Marker(binding.mapView);
        marker.setPosition(randomLocation);
        marker.setIcon(getResources().getDrawable(randomPokemon.getFrontResource()));
        marker.setTitle(randomPokemon.getName());
        binding.mapView.getOverlays().add(marker);

        // Rafraîchissez la carte
        binding.mapView.invalidate();
    }

    private Pokemon getRandomPokemonFromDatabase() {
        Database db = Database.getInstance(getContext());

        // Sélectionnez un Pokémon aléatoire qui n'a pas encore été capturé
        String[] columns = {"ordre", "name", "capture", "image", "height", "weight", "type1", "type2"};
        Cursor cursor = db.query("Pokemon", columns, null, null, null, null, "RANDOM()");

        if (cursor.moveToFirst()) {
            int ordre = cursor.getInt(cursor.getColumnIndex("ordre"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            int frontResource = getResources().getIdentifier(image, "drawable", getContext().getPackageName());
            double height = cursor.getDouble(cursor.getColumnIndex("height"));
            double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
            String type1 = cursor.getString(cursor.getColumnIndex("type1"));
            String type2 = cursor.getString(cursor.getColumnIndex("type2"));
            boolean isCapture = cursor.getInt(cursor.getColumnIndex("capture")) == 1;

            POKEMON_TYPE enumType1 = POKEMON_TYPE.valueOf(type1);
            POKEMON_TYPE enumType2 = (type2 != null) ? POKEMON_TYPE.valueOf(type2) : null;
            Pokemon pokemon = new Pokemon(ordre, name, frontResource, enumType1, enumType2, weight, height);
            cursor.close();
            return pokemon;
        }

        cursor.close();
        return null;
    }


}

