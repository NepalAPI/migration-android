package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Country;

import java.util.List;

/**
 * Created by Nirazan-PC on 12/11/2015.
 */
public interface CountryListView extends LoadDataView {
    void renderCountryList(List<Country> pPosts);
}
