package com.yipl.nrna.util;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class BindingUtil {
    @BindingAdapter("bind:imageUrl")
    public static void setImage(ImageView pView, String url) {
        try {
            pView.setImageURI(Uri.parse(url));
        } catch (NullPointerException e) {
        }
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

    @BindingAdapter("bind:fromHtml")
    public static void setFromHtml(TextView pView, String text) {
        pView.setText(Html.fromHtml(text));
    }
}
