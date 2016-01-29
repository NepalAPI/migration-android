package com.yipl.nrna.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yipl.nrna.R;
import com.yipl.nrna.databinding.ArticleDataBinding;
import com.yipl.nrna.databinding.AudioDataBinding;
import com.yipl.nrna.databinding.CountryDataBinding;
import com.yipl.nrna.databinding.CountryUpdateDataBinding;
import com.yipl.nrna.databinding.FooterDataBinding;
import com.yipl.nrna.databinding.QuestionDataBinding;
import com.yipl.nrna.databinding.VideoDataBinding;
import com.yipl.nrna.databinding.VideoGridDataBinding;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.domain.model.CountryUpdate;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_AUDIO;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_COUNTRY;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_COUNTRY_UPDATE;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_FOOTER;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_QUESTION;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_TEXT;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_VIDEO;

public class ListAdapter<T extends BaseModel> extends RecyclerView.Adapter<RecyclerView
        .ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    List<T> mDataCollection;
    ListClickCallbackInterface mListener;
    Boolean mIsGrid = false;

    public ListAdapter(Context pContext, ListClickCallbackInterface pListener) {
        this.mLayoutInflater = (LayoutInflater) pContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        this.mListener = pListener;
    }

    public ListAdapter(Context pContext, List<T> pDataCollection, ListClickCallbackInterface pListener) {
        mDataCollection = pDataCollection;
        this.mLayoutInflater = (LayoutInflater) pContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        this.mListener = pListener;
    }

    public List<T> getDataCollection() {
        return mDataCollection;
    }

    public void setDataCollection(List<T> pDataCollection) {
        mDataCollection = pDataCollection;
        notifyDataSetChanged();
    }

    public void setDataCollection(List<T> pDataCollection, boolean isGrid) {
        mDataCollection = pDataCollection;
        mIsGrid = true;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            default:
            case TYPE_QUESTION:
                QuestionDataBinding qBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout
                        .list_item_question, parent, false);
                viewHolder = new QuestionsViewHolder(qBinding);
                break;
            case TYPE_AUDIO:
                AudioDataBinding aBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout
                        .list_item_audio, parent, false);
                viewHolder = new AudioViewHolder(aBinding);
                break;
            case TYPE_VIDEO:
                if (mIsGrid) {
                    VideoGridDataBinding vGridBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout
                            .video_gidview_list, parent, false);
                    viewHolder = new VideoViewHolder(vGridBinding);
                } else {
                    VideoDataBinding vBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout
                            .list_item_video, parent, false);
                    viewHolder = new VideoViewHolder(vBinding);
                }

                break;
            case TYPE_TEXT:
                ArticleDataBinding articleBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout
                        .list_item_article, parent, false);
                viewHolder = new ArticleViewHolder(articleBinding);
                break;
            case TYPE_COUNTRY:
                CountryDataBinding countryBinding = DataBindingUtil.inflate(mLayoutInflater, R
                        .layout.list_item_country, parent, false);
                viewHolder = new CountryViewHolder(countryBinding);
                break;
            case TYPE_COUNTRY_UPDATE:
                CountryUpdateDataBinding updateBinding = DataBindingUtil.inflate(mLayoutInflater, R
                        .layout.list_item_update, parent, false);
                viewHolder = new CountryUpdateViewHolder(updateBinding);
                break;
            case TYPE_FOOTER:
                FooterDataBinding footerDataBinding = DataBindingUtil.inflate(mLayoutInflater,
                        R.layout.layout_see_all_countries, parent, false);
                viewHolder = new FooterViewHolder(footerDataBinding);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_QUESTION:
                ((QuestionsViewHolder) holder).mBinding.setQuestion((Question) mDataCollection
                        .get(position));
                break;
            case TYPE_AUDIO:
                ((AudioViewHolder) holder).mBinding.setAudio((Post) mDataCollection.get(position));
                break;
            case TYPE_VIDEO:
                if (mIsGrid)
                    ((VideoViewHolder) holder).mGridBinding.setVideo((Post) mDataCollection.get(position));
                else
                    ((VideoViewHolder) holder).mBinding.setVideo((Post) mDataCollection.get(position));
                break;
            case TYPE_TEXT:
                ((ArticleViewHolder) holder).mBinding.setArticle((Post) mDataCollection.get
                        (position));
                break;
            case TYPE_COUNTRY:
                ((CountryViewHolder) holder).mBinding.setCountry((Country) mDataCollection.get
                        (position));
                break;
            case TYPE_COUNTRY_UPDATE:
                ((CountryUpdateViewHolder) holder).mBinding.setUpdate((CountryUpdate)
                        mDataCollection.get(position));
                break;
            case TYPE_FOOTER:
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataCollection.get(position).getDataType();
    }

    @Override
    public int getItemCount() {
        return (mDataCollection != null) ? mDataCollection.size() : 0;
    }

    private int filterList(List<Post> selectedTracks, Post selection, int type) {
        int index = 0;
        for (BaseModel post : mDataCollection) {
            if (post != null && post.getDataType() == type) {
                selectedTracks.add((Post) post);
                if (post == selection)
                    index = selectedTracks.size() - 1;
            }
        }
        return index;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {

        public QuestionDataBinding mBinding;

        public QuestionsViewHolder(QuestionDataBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this, binding.getRoot());
            this.mBinding = binding;
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListItemSelected(mBinding.getQuestion());
                }
            });
        }
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder {

        public AudioDataBinding mBinding;

        public AudioViewHolder(AudioDataBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this, binding.getRoot());
            this.mBinding = binding;
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Post> selectedTracks = new ArrayList();
                    int index = filterList(selectedTracks, mBinding.getAudio(), MyConstants.Adapter
                            .TYPE_AUDIO);
                    mListener.onAudioItemSelected(selectedTracks, index);
                }
            });
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public VideoDataBinding mBinding;
        public VideoGridDataBinding mGridBinding;

        public VideoViewHolder(VideoDataBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this, binding.getRoot());
            this.mBinding = binding;
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListItemSelected(mBinding.getVideo());
                }
            });
        }

        public VideoViewHolder(VideoGridDataBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this, binding.getRoot());
            this.mGridBinding = binding;
            mGridBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListItemSelected(mGridBinding.getVideo());
                }
            });
        }
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        public ArticleDataBinding mBinding;

        public ArticleViewHolder(ArticleDataBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this, binding.getRoot());
            this.mBinding = binding;
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListItemSelected(mBinding.getArticle());
                }
            });
        }
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        public CountryDataBinding mBinding;

        public CountryViewHolder(CountryDataBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this, binding.getRoot());
            this.mBinding = binding;
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListItemSelected(mBinding.getCountry());
                }
            });
        }
    }

    public class CountryUpdateViewHolder extends RecyclerView.ViewHolder {

        public CountryUpdateDataBinding mBinding;

        public CountryUpdateViewHolder(CountryUpdateDataBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this, binding.getRoot());
            this.mBinding = binding;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterDataBinding mBinding;

        public FooterViewHolder(FooterDataBinding pBinding) {
            super(pBinding.getRoot());
            ButterKnife.bind(this, pBinding.getRoot());
            this.mBinding = pBinding;
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Country country = new Country();
                    country.setDataType(TYPE_FOOTER);
                    mListener.onListItemSelected(country);
                }
            });
        }
    }
}
