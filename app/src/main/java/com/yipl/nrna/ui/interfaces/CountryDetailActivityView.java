package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Country;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public interface CountryDetailActivityView extends LoadDataView {
    void renderCountryDetail(Country pCountry);
}
