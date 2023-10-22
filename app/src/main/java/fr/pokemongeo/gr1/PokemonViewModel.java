package fr.pokemongeo.gr1;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonViewModel extends BaseObservable {
    private Pokemon pokemon;
    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        notifyChange();
    }
    @Bindable
    public int getFront() {
        return pokemon.getFrontResource();
    }
    @Bindable
    public String getName() {
        return pokemon.getName();
    }
    @Bindable
    public String getType1() {
        return pokemon.getType1String();
    }
    @Bindable
    public String getType2() {
        if (pokemon.getType2() != null)
            return pokemon.getType2String();
        return "";
    }
    @Bindable
    public String getNumber() {
        return "#"+pokemon.getOrder();
    }
    public Drawable getImage(Context context, int res) {
        if(res != -1)
            return ResourcesCompat.getDrawable(context.getResources(),
                    res, context.getTheme());
        else
            return null;
    }

    public Drawable getTypeImage(Context context, String type) {
        if(type != null) {
            int typeResourceId = context.getResources().getIdentifier(type.toLowerCase(), "drawable", context.getPackageName());
            if (typeResourceId != 0) {
                return ResourcesCompat.getDrawable(context.getResources(), typeResourceId, context.getTheme());
            }
        }
        return null;
    }

    @Bindable
    public String getHeight() {
        return String.valueOf(pokemon.getHeight());
    }
    @Bindable
    public String getWeight() {
        return String.valueOf(pokemon.getWeight());
    }
}