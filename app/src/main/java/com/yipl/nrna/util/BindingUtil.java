package com.yipl.nrna.util;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

public class BindingUtil {
    @BindingAdapter("bind:imageUrl")
    public static void setImage(ImageView pView, String url) {
        pView.setImageURI(Uri.parse(url));
    }
}
