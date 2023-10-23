package fr.pokemongeo.gr1;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.pokemongeo.gr1.databinding.MapFragmentBinding;


public class MapFragment extends Fragment {
    private MapFragmentBinding binding;
    private MyLocationNewOverlay myLocationOverlay;
    private Map<GeoPoint, Pokemon> markerPokemonMap = new HashMap<>();
    private boolean spawnPokemonHandlerPosted = false;
    private final Handler handler = new Handler();
    private final Runnable spawnPokemonRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (spawnPokemonHandlerPosted && myLocationOverlay.getMyLocation() != null) {
                        spawnRandomPokemon(myLocationOverlay.getMyLocation());
                    }
                }
            }, 3000); // Wait for 3 seconds before the first appearance

            handler.postDelayed(this, 30000); // Wait for 30 seconds before the next appearance
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MapFragmentBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        Context context = rootView.getContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);

        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getContext()), binding.mapView) {
            @Override
            public void onLocationChanged(Location location, IMyLocationProvider source) {
                super.onLocationChanged(location, source);
                if (location != null) {
                    GeoPoint myLocation = new GeoPoint(location);
                    binding.mapView.getController().setCenter(myLocation);
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
        // Génère un emplacement aléatoire autour du joueur
        GeoPoint randomLocation = generateRandomLocation(playerPosition, 1);
        Pokemon randomPokemon = getRandomPokemonFromDatabase();

        // Ajoute le Pokémon sur la carte
        Marker marker = new Marker(binding.mapView);
        marker.setPosition(randomLocation);
        marker.setIcon(getResources().getDrawable(randomPokemon.getFrontResource()));
        marker.setTitle(randomPokemon.getName());
        markerPokemonMap.put(randomLocation, randomPokemon);
        binding.mapView.getOverlays().add(marker);
        addListenerToMarker(marker, randomPokemon);

        // Met à jour la carte
        binding.mapView.invalidate();
    }

    private Pokemon getRandomPokemonFromDatabase() {
        Database db = Database.getInstance(getContext());

        // Sélectionnez un Pokémon aléatoire dans la base de données
        String[] columns = {"id", "ordre", "name", "capture", "image", "height", "weight", "type1", "type2"};
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
            pokemon.setId(cursor.getInt(cursor.getColumnIndex("id")));
            cursor.close();
            return pokemon;
        }

        cursor.close();
        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
        if (myLocationOverlay != null) {
            myLocationOverlay.disableMyLocation();
        }

        // Sauvegarde markerPokemonMap dans les préférences partagées
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(markerPokemonMap);
        editor.putString("markerPokemonMap", json);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
        if (myLocationOverlay != null) {
            myLocationOverlay.enableMyLocation();
        }

        // Restaure markerPokemonMap depuis les préférences partagées
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String json = preferences.getString("markerPokemonMap", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Pokemon>>() {
            }.getType();
            Map<String, Pokemon> mapFromJson = gson.fromJson(json, type);

            // Convertir les clés (GeoPoint) de String en GeoPoint si nécessaire
            Map<GeoPoint, Pokemon> convertedMap = new HashMap<>();
            for (String geoPointStr : mapFromJson.keySet()) {
                GeoPoint geoPoint = GeoPoint.fromDoubleString(geoPointStr, ',');
                convertedMap.put(geoPoint, mapFromJson.get(geoPointStr));
            }
            markerPokemonMap = convertedMap;
        }
        Database database = Database.getInstance(getContext());
        for (Map.Entry<GeoPoint, Pokemon> entry : markerPokemonMap.entrySet()) {
            GeoPoint geoPoint = entry.getKey();
            Pokemon pokemon = entry.getValue();

            String[] columns = {"capture"};
            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(pokemon.getId())};
            Cursor cursor = database.query("Pokemon", columns, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                boolean isCapture = cursor.getInt(cursor.getColumnIndex("capture")) == 1;
                if (!isCapture) {
                    Log.d("aaaaaa", "onResume: "+pokemon.getName()+" ;;; "+pokemon.isCapture());
                    // Créer un marqueur à la localisation du GeoPoint
                    Marker marker = new Marker(binding.mapView);
                    marker.setPosition(geoPoint);
                    marker.setIcon(getResources().getDrawable(pokemon.getFrontResource()));
                    marker.setTitle(pokemon.getName());
                    binding.mapView.getOverlays().add(marker);
                    addListenerToMarker(marker, pokemon);
                }
            }
        }
        // Met à jour la carte
        binding.mapView.invalidate();
    }

    private void addListenerToMarker(Marker marker, Pokemon pokemon) {
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                CaptureFragment captureFragment = new CaptureFragment(pokemon);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, captureFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            }
        });
    }
}

