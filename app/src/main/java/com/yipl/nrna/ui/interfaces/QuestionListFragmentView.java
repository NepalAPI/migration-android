package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Question;

import java.util.List;

/**
 * Created by Nirazan-PC on 12/15/2015.
 */
public interface QuestionListFragmentView extends LoadDataView {
    public void renderQuestionList(List<Question> pQuestionList);
}
