package fr.pokemongeo.gr1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

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
        // Initialisation de la barre de navigation

        bottomNavigationView = binding.navigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        showStartup();
    }


    public void showStartup() {
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

}