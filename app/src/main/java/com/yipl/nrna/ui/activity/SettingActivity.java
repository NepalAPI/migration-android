package com.yipl.nrna.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.domain.util.MyConstants;

import butterknife.Bind;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.personalization)
    Button btnPersonalization;

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       btnPersonalization.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getBaseContext(), PersonalizationActivity.class);
               i.putExtra(MyConstants.Extras.KEY_PERSONALIZATION_LAUNCH, false );
               startActivity(i);
           }
       });
    }

}
