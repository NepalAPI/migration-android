package com.yipl.nrna.presenter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.yipl.nrna.R;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.FileData;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.PostDetailView;
import com.yipl.nrna.util.AppPreferences;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class PostDetailPresenter implements Presenter {

    UseCase mUseCase;
    PostDetailView mView;

    DownloadManager mDownloadManager;
    AppPreferences mPreferences;

    @Inject
    public PostDetailPresenter(@Named("postDetails") UseCase pUseCase, AppPreferences pPreferences) {
        mUseCase = pUseCase;
        mPreferences = pPreferences;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadArticle();
        mDownloadManager = (DownloadManager) mView.getContext().getSystemService(Context
                .DOWNLOAD_SERVICE);
    }

    @Override
    public void attachView(MvpView view) {
        mView = (PostDetailView) view;
    }

    private void loadArticle() {
        getArticle();
    }

    private File getDocumentFile(String fileName, String folderName) {
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File dir = new File(root, folderName);
        dir.mkdirs();
        return new File(dir, fileName);
    }

    public void downloadDocument(FileData fileData) {
        String docUrl = fileData.getFileUrl().replace(" ", "%20");
        String fileName = docUrl.substring(docUrl.lastIndexOf("/") + 1);
        String folderName = mView.getContext().getString(R.string.app_name);
        File audioFile = getDocumentFile(fileName, folderName);

        if (!audioFile.exists()) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(docUrl));
            request.setNotificationVisibility(DownloadManager.Request
                    .VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(fileData.getDescription());
            request.setDescription(mView.getContext().getResources().getString(R.string
                    .download_description));

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, folderName +
                    "/" + fileName);
            request.setVisibleInDownloadsUi(true);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager
                    .Request.NETWORK_MOBILE);
            mDownloadManager.enqueue(request);
        } else {
            mView.onDownloadStarted(mView.getContext().getString(R.string.download_already,
                    fileData.getDescription()));
        }
    }

    private void getArticle() {
        this.mUseCase.execute(new ArticleDetailSubscriber());
    }

    private final class ArticleDetailSubscriber extends DefaultSubscriber<Post> {
        @Override
        public void onCompleted() {
            PostDetailPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            PostDetailPresenter.this.mView.hideLoadingView();
            PostDetailPresenter.this.mView.showErrorView(e.getLocalizedMessage());
            PostDetailPresenter.this.mView.showRetryView();
            e.printStackTrace();
        }

        @Override
        public void onNext(Post pPost) {
            PostDetailPresenter.this.mView.renderPostDetail(pPost);
        }
    }

}
