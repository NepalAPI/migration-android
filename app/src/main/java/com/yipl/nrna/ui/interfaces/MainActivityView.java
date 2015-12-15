package com.yipl.nrna.ui.interfaces;

/**
 * Created by julian on 12/9/15.
 */
public interface MainActivityView extends LoadDataView {
    void informCurrentFragmentForUpdate();
    void onListItemSelected(int pType, Long pId);
}
