package com.yipl.nrna.presenter;

import com.yipl.nrna.ui.interfaces.MvpView;

public interface Presenter {
    void resume();

    void pause();

    void destroy();

    void initialize();

    void attachView(MvpView view);
}
