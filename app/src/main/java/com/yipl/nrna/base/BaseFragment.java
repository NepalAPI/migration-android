package com.yipl.nrna.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    public abstract int getLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null){
            ((BaseActivity) getActivity()).getPreferences().removeFilterChoices();
        }
        //setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void showNewContentInfo() {

    }
}
