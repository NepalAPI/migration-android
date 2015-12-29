package com.yipl.nrna.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.yipl.nrna.R;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.util.Logger;

/**
 * Created by Nirazan-PC on 12/17/2015.
 */
public abstract class FacebookActivity extends BaseActivity {

    FacebookCallback<Sharer.Result> mFbCallBack;
    ShareDialog mShareDialog;
    private CallbackManager mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        menu.findItem(R.id.action_share).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon
                .gmd_share).color(Color.WHITE)
                .actionBar());
        return true;
    }


    private void initialize() {
        mCallback = CallbackManager.Factory.create();
        mFbCallBack = new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Logger.d("FacebookActivity_onSuccess", "Share successful: " + result.getPostId());
            }

            @Override
            public void onCancel() {
                Logger.d("FacebookActivity_onCancel", "Share canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Logger.e("FacebookActivity_onError", error.getMessage());
                error.printStackTrace();
            }
        };
        LoginManager.getInstance().registerCallback(mCallback, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                Logger.d("FacebookActivity_onSuccess", "Login successful");
            }

            @Override
            public void onCancel() {
                Logger.d("FacebookActivity_onCancel", "Login cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Logger.e("FacebookActivity_onError", error.getMessage());
                error.printStackTrace();
            }
        });

        mShareDialog = new ShareDialog(this);
        mShareDialog.registerCallback(mCallback, mFbCallBack);
    }

    public ShareOpenGraphContent getShareContent(BaseModel pModel) {
        String title, description;
        switch (pModel.getDataType()) {
            default:
            case MyConstants.Adapter.TYPE_AUDIO:
            case MyConstants.Adapter.TYPE_VIDEO:
            case MyConstants.Adapter.TYPE_TEXT:
                title = ((Post) pModel).getTitle();
                description = ((Post) pModel).getDescription();
                break;
        }

        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "nrnaapp:post")
                .putString("og:title", title)
                .putString("og:description", description)
                .build();
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("nrnaapp:share")
                .putObject("post", object)
                .build();
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("post")
                .setAction(action)
                .build();
        return content;
    }

    public void showShareDialog(final BaseModel pModel) {
        if (mShareDialog.canShow(ShareOpenGraphContent.class)) {
            mShareDialog.show(getShareContent(pModel));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallback.onActivityResult(requestCode, resultCode, data);
    }
}
