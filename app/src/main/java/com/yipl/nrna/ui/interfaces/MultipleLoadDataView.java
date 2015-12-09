package com.yipl.nrna.ui.interfaces;

import android.content.Context;

public interface MultipleLoadDataView extends MvpView {
    void showLoadingView(int flag);
    void hideLoadingView(int flag);

    void showRetryView(int flag);
    void hideRetryView(int flag);

    void showErrorView(String pErrorMessage);
    void hideErrorView();

    void showEmptyView(int flag);
    void hideEmptyView(int flag);

    Context getContext();
}
