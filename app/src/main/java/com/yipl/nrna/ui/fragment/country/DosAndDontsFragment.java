package com.yipl.nrna.ui.fragment.country;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.databinding.CountryDosDontsDataBinding;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.domain.util.MyConstants;

public class DosAndDontsFragment extends BaseFragment {

    CountryDosDontsDataBinding mBinding;
    Country mCountry;

    public DosAndDontsFragment() {
        // Required empty public constructor
    }

    public static DosAndDontsFragment newInstance(Country pCountry) {
        DosAndDontsFragment fragment = new DosAndDontsFragment();
        Bundle data = new Bundle();
        data.putSerializable(MyConstants.Extras.KEY_Country, pCountry);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_country_dos_donts;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mBinding.setCountry(mCountry);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCountry = (Country) getArguments().getSerializable(MyConstants.Extras.KEY_Country);
    }
}
