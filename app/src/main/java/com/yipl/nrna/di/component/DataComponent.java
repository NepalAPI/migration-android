package com.yipl.nrna.di.component;

import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.di.module.ActivityModule;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.ui.activity.ArticleDetailActivity;
import com.yipl.nrna.ui.activity.AudioDetailActivity;
import com.yipl.nrna.ui.activity.CountryDetailActivity;
import com.yipl.nrna.ui.activity.MainActivity;
import com.yipl.nrna.ui.activity.PersonalizationActivity;
import com.yipl.nrna.ui.activity.QuestionDetailActivity;
import com.yipl.nrna.ui.activity.VideoDetailActivity;
import com.yipl.nrna.ui.fragment.AnswerListFragment;
import com.yipl.nrna.ui.fragment.AnswerPostFragment;
import com.yipl.nrna.ui.fragment.ArticleListFragment;
import com.yipl.nrna.ui.fragment.AudioListFragment;
import com.yipl.nrna.ui.fragment.CountryListFragment;
import com.yipl.nrna.ui.fragment.FilterDialogFragment;
import com.yipl.nrna.ui.fragment.HomeFragment;
import com.yipl.nrna.ui.fragment.InfoCenterContentFragment;
import com.yipl.nrna.ui.fragment.PostListFragment;
import com.yipl.nrna.ui.fragment.QuestionListFragment;
import com.yipl.nrna.ui.fragment.VideoListFragment;
import com.yipl.nrna.ui.fragment.country.RelatedContentFragment;
import com.yipl.nrna.ui.fragment.country.UpdatesFragment;
import com.yipl.nrna.ui.receivers.DownloadStateReceiver;

import dagger.Component;

/**
 * Created by julian on 12/7/15.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, DataModule.class}
)
public interface DataComponent extends ActivityComponent {
    void inject(HomeFragment pFragment);

    void inject(MainActivity pBaseActivity);

    void inject(PostListFragment pFragment);

    void inject(AudioListFragment pFragment);

    void inject(VideoListFragment pFragment);

    void inject(ArticleListFragment pFragment);

    void inject(AnswerListFragment pFragment);

    void inject(CountryListFragment pFragment);

    void inject(UpdatesFragment pFragment);

    void inject(InfoCenterContentFragment pFragment);

    void inject(QuestionListFragment pFragment);

    void inject(AnswerPostFragment pFragment);

    void inject(ArticleDetailActivity pActivity);

    void inject(CountryDetailActivity pActivity);

    void inject(QuestionDetailActivity pActivity);

    void inject(VideoDetailActivity pActivity);

    void inject(RelatedContentFragment pFragment);

    void inject(FilterDialogFragment pFragment);

    void inject(PersonalizationActivity pActivity);

    void inject(AudioDetailActivity pActivity);

    void inject(DownloadStateReceiver pReceiver);
}
