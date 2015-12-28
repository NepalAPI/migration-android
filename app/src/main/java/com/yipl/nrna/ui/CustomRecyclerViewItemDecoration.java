package com.yipl.nrna.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Nirazan-PC on 12/24/2015.
 */
public class CustomRecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private final int mSpace = 8;

    public CustomRecyclerViewItemDecoration(){

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) < parent.getAdapter().getItemCount() - 2) {
            outRect.bottom = mSpace;
        }
        if(parent.getChildAdapterPosition(view)%2==0){
            outRect.right = mSpace;
        }
    }
}
