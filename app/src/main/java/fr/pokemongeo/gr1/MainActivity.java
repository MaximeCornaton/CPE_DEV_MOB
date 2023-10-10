package fr.pokemongeo.gr1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.pokemongeo.gr1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    OnClickOnNoteListener listener = new OnClickOnNoteListener(){
        @Override
        public void onClickOnNote(Pokemon pokemon){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new PokemonDetailFragment(pokemon))
                    .addToBackStack(null)
                    .commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (isFirstLaunch()) {
            // First app launch, show the WelcomeFragment
            showWelcomeFragment();
            setFirstLaunch(false);
        } else {
            // Not the first launch, show the main content
            showStartup();
        }
    }

    private boolean isFirstLaunch() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("firstLaunch", true);
    }

    private void showWelcomeFragment() {
        // Initialize the WelcomeFragment
        WelcomeFragment welcomeFragment = new WelcomeFragment();

        // Replace the fragment container with WelcomeFragment
        replaceFragment(welcomeFragment);
    }


    private void setFirstLaunch(boolean isFirstLaunch) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstLaunch", isFirstLaunch);
        editor.apply();
    }

    public void showStartup() {
        // Initialisation de la barre de navigation
        bottomNavigationView = binding.navigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        PokedexFragment fragment = new PokedexFragment();
        fragment.setOnClickOnNoteListener(listener);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            if (item.getItemId() == R.id.page_1) {
                fragment = new PokedexFragment();
                ((PokedexFragment) fragment).setOnClickOnNoteListener(listener);
                replaceFragment(fragment);
                return true;
            }
            if (item.getItemId() == R.id.page_2) {
                fragment = new MapFragment();
                replaceFragment(fragment);
                return true;
            }
            if (item.getItemId() == R.id.page_3) {
                fragment = new CaughtFragment();
                ((CaughtFragment) fragment).setOnClickOnNoteListener(listener);
                replaceFragment(fragment);
                return true;
            }
            if (item.getItemId() == R.id.page_4) {
                fragment = new PokedexFragment();
                replaceFragment(fragment);
                return true;
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void onPokemonSelected(String starter, Context context) {
        Database database = Database.getInstance(context);
        database.loadPokemonFromJSON(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put("capture", true);

        // Define the WHERE clause and arguments
        String whereClause = "name = ?";
        String[] whereArgs = new String[] { starter };

        // Update the rows in the "Pokemon" table
        database.update("Pokemon", updateValues, whereClause, whereArgs);

        showStartup();
    }
}