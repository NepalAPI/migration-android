package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Answer;

import java.util.List;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public interface AnswerListView extends LoadDataView {
    public void renderAnswerList(List<Answer> pAnswers);
}
