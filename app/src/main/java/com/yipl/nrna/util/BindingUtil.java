package com.yipl.nrna.util;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

public class BindingUtil {
    @BindingAdapter("bind:imageUrl")
    public static void setImage(ImageView pView, String url) {
        pView.setImageURI(Uri.parse(url));
    }

    @BindingAdapter("bind:mediaDuration")
    public static void setDuration(TextView pView, String duration) {
        if (duration != null) {
            String[] data = duration.split(":");
            if (data[0].equals("00")) {
                pView.setText(data[1] + ":" + data[2]);
            } else {
                pView.setText(duration);
            }
        }
    }
}
