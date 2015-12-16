package com.yipl.nrna.ui.interfaces;

import android.content.Context;

public interface LoadDataView extends MvpView {
    void showLoadingView();

    void hideLoadingView();

    void showRetryView();

    void hideRetryView();

    void showErrorView(String pErrorMessage);

    void hideErrorView();

    void showEmptyView();

    void hideEmptyView();

    Context getContext();
}
