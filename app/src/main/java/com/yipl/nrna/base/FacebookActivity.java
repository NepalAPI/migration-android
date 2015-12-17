package com.yipl.nrna.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.yipl.nrna.R;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.util.MyConstants;

/**
 * Created by Nirazan-PC on 12/17/2015.
 */
public abstract class FacebookActivity extends BaseActivity {

    FacebookCallback<Sharer.Result> mFbCallBack;
    private CallbackManager mCallback;
    ShareDialog mShareDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }


    private void initialize() {
        mCallback = CallbackManager.Factory.create();
        mFbCallBack = new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.i("fbshare", "success:// " + result.getPostId());
            }

            @Override
            public void onCancel() {
                Log.i("fbshare","canceled");

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("fbshare", "error: " + error.getMessage());
                error.printStackTrace();

            }
        };
        LoginManager.getInstance().registerCallback(mCallback, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                Log.i("fbshare", "login successful: ");

            }

            @Override
            public void onCancel() {
                Log.i("fbshare", "login calceled: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("fbshare", "login error: " + error.getMessage());
                error.printStackTrace();
            }
        });

        mShareDialog = new ShareDialog(this);
        mShareDialog.registerCallback(mCallback,mFbCallBack);
    }

    public ShareOpenGraphContent getShareContent(BaseModel pModel){
        String title, description;
        switch (pModel.getDataType()){
            default:
            case MyConstants.Adapter.TYPE_AUDIO:
            case MyConstants.Adapter.TYPE_VIDEO:
            case MyConstants.Adapter.TYPE_TEXT:
                title = ((Post) pModel).getTitle();
                description = ((Post) pModel).getDescription();
                break;
        }

        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type","nrnaapp:post")
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

//        ShareLinkContent content = new ShareLinkContent.Builder()
//                .setContentTitle(pModel.getTitle())
////                .setContentDescription(pModel.getDescription())
//                .setContentUrl(null)
//                .build();
        return content;
    }

    public void showShareDialog(final BaseModel pModel){
        if(mShareDialog.canShow(ShareOpenGraphContent.class)){
            mShareDialog.show(getShareContent(pModel));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallback.onActivityResult(requestCode, resultCode, data);
    }
}
