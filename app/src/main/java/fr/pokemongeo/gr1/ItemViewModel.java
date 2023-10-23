package fr.pokemongeo.gr1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ItemViewModel extends BaseObservable {
    private Item item;
    public void setItem(Item item) {
        this.item = item;
        notifyChange();
    }
    @Bindable
    public String getName() {
        return item.getName();
    }
    public Drawable getImage(Context context, String nom) {
        int resId = context.getResources().getIdentifier(nom, "drawable", context.getPackageName());
        if (resId != 0) {
            return ContextCompat.getDrawable(context, resId);
        } else {
            return null; // Ou renvoyez un Drawable par défaut
        }
    }

    @Bindable
    public String getFront() {
        return item.getFront();
    }

}
