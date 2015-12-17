package com.yipl.nrna.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.yipl.nrna.domain.model.Post;

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

    public ShareOpenGraphContent getShareContent(Post pPost){

        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type","mypost:post")
                .putString("og:title",pPost.getTitle())
                .putString("og:description", pPost.getDescription())
                .build();
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("mypost:reads")
                .putObject("post", object)
                .build();
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("post")
                .setAction(action)
                .build();

//        ShareLinkContent content = new ShareLinkContent.Builder()
//                .setContentTitle(pPost.getTitle())
////                .setContentDescription(pPost.getDescription())
//                .setContentUrl(null)
//                .build();
        return content;
    }

    public void showShareDialog(final Post pPost){
        if(mShareDialog.canShow(ShareOpenGraphContent.class)){
            mShareDialog.show(getShareContent(pPost));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallback.onActivityResult(requestCode, resultCode, data);
    }
}
