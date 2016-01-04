package com.yipl.nrna.ui.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yipl.nrna.R;
import com.yipl.nrna.domain.model.Answer;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.ui.fragment.AnswerPostFragment;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;
import com.yipl.nrna.util.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_ANSWER;

public class FragmentListAdapter<T extends BaseModel> extends RecyclerView.Adapter<RecyclerView
        .ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    List<T> mDataCollection;
    ListClickCallbackInterface mListener;

    public FragmentListAdapter(Context pContext, ListClickCallbackInterface pListener) {
        mContext = pContext;
        this.mLayoutInflater = (LayoutInflater) pContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        this.mListener = pListener;
    }

    public void setDataCollection(List<T> pDataCollection) {
        mDataCollection = pDataCollection;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ANSWER:
                View view = mLayoutInflater.inflate(R.layout.item_fragment_holder, parent, false);
                return new AnswerViewHolder(view);
            default:
                throw new IllegalArgumentException("Not a valid data type...");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ANSWER:
                Answer answer = (Answer) mDataCollection.get(position);
                ((AnswerViewHolder) holder).bindAnswer(answer);
                break;
            default:
                throw new IllegalArgumentException("Not a valid data type...");
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

    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.container)
        FrameLayout mContainer;

        public AnswerViewHolder(View pView) {
            super(pView);
            ButterKnife.bind(this, pView);
        }

        public void bindAnswer(Answer pAnswer) {
            try {
                mContainer.removeAllViews();
                mContainer.setId(pAnswer.getId().intValue());
                Logger.d("AnswerViewHolder_bindAnswer", "ID:" + mContainer.getId());
                ((AppCompatActivity) mContext).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(mContainer.getId(), AnswerPostFragment.newInstance(pAnswer),
                                "answer_" + pAnswer.getTitle())
                        .commit();
            } catch (Exception e) {
                Logger.e("AnswerViewHolder_bindAnswer", "exception occurred..");
                e.printStackTrace();
            }
        }
    }
}
