package fr.pokemongeo.gr1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import fr.pokemongeo.gr1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements OnClickOnNoteListener {
    private ActivityMainBinding binding;
    OnClickOnNoteListener listener = new OnClickOnNoteListener(){
        @Override
        public void onClickOnNote(int noteId){
            showNoteDetail(noteId);
        }
    };

    private void showNoteDetail(int noteId) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        showStartup();
    }

    public void showStartup() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        PokedexFragment fragment = new PokedexFragment();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
        fragment.setOnClickOnNoteListener(listener);
    }

    @Override
    public void onClickOnNote(int pokemonId) {
        // Créez un bundle pour transmettre l'ID du Pokémon sélectionné au nouveau fragment.
        Bundle bundle = new Bundle();
        bundle.putInt("pokemonId", pokemonId);

        // Créez une instance du fragment de détails du Pokémon.
        PokemonDetailFragment detailFragment = new PokemonDetailFragment();
        detailFragment.setArguments(bundle);

        // Remplacez le fragment actuel par le fragment de détails du Pokémon.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}