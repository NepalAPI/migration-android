package com.yipl.nrna.ui.fragment;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.PdfViewerDialogFragmentPresenter;
import com.yipl.nrna.ui.interfaces.PdfViewerDialogFragmentView;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nirazan-PC on 2/8/2016.
 */
public class PdfViewerDialogFragment extends AppCompatDialogFragment implements PdfViewerDialogFragmentView, OnPageChangeListener, View.OnClickListener {

    @Inject
    PdfViewerDialogFragmentPresenter mPresenter;

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.pdfViewer)
    PDFView mPDFView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.btnDownload)
    ImageView mBtnDownload;
    String mUrl;
    @Bind(R.id.page)
    TextView mTextViewPage;

    public PdfViewerDialogFragment() {

    }

    public static PdfViewerDialogFragment newInstance(String pUrl) {
        PdfViewerDialogFragment fragment = new PdfViewerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MyConstants.Extras.KEY_PDF_URL, pUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pdfviewer_dialog_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if(bundle!=null){
            mUrl = bundle.getString(MyConstants.Extras.KEY_PDF_URL);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initiaize();
        mToolbar.setTitle(Uri.parse(mUrl).getLastPathSegment());
        mPresenter.loadPdf(mUrl);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    private void initiaize() {
        DaggerDataComponent.builder()
                .dataModule(new DataModule())
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .applicationComponent(((BaseActivity) getActivity()).getApplicationComponent())
                .build()
        .inject(this);
        mPresenter.attachView(this);
        mBtnDownload.setOnClickListener(this);
    }



    @Override
    public void showPdf(File pFile) {
        mPDFView.fromFile(pFile)
                .defaultPage(1)
                .onPageChange(this)
                .load();
    }

    @Override
    public void downloadSuccess(File pFile) {
        Snackbar.make(getView(),getString(R.string.download_success, pFile.getName()), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetryView() {

    }

    @Override
    public void hideRetryView() {

    }

    @Override
    public void showErrorView(String pErrorMessage) {
        Snackbar.make(mPDFView,pErrorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        mTextViewPage.setText(page + " / "+ pageCount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDownload:
                mPresenter.copyFile(Uri.parse(mUrl).getLastPathSegment());
        }
    }
}
