package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Question;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public interface QuestionDetailActivityView extends PostListView {
    void renderQuestionDetail(Question pQuestion);
    void renderSubQuestions(String[] pChildIds);
}
