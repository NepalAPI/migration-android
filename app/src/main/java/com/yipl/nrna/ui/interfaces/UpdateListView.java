package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.CountryUpdate;

import java.util.List;

public interface UpdateListView extends LoadDataView {
    void renderUpdates(List<CountryUpdate> pUpdates);
}
