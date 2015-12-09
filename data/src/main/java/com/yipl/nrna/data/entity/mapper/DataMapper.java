package com.yipl.nrna.data.entity.mapper;

import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.entity.PostEntity;
import com.yipl.nrna.data.entity.QuestionEntity;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class DataMapper {

    @Inject
    public DataMapper() {
    }

    public LatestContent transformLatestContent(LatestContentEntity pEntity){
        LatestContent latestContent = null;
        if(pEntity != null){
            latestContent = new LatestContent();
            latestContent.setQuestions(transformQuestion(pEntity.getQuestions()));
            latestContent.setPosts(transformPost(pEntity.getPosts()));
        }
        return latestContent;
    }

    public Question transformQuestion(QuestionEntity pEntity){
        if(pEntity != null){
            Question question = new Question();
            return question;
        }
        return null;
    }

    public List<Question>  transformQuestion(List<QuestionEntity> pEntities){
        List<Question> questionList = new ArrayList<>();
        if(pEntities != null) {
            for (QuestionEntity entity : pEntities) {
                Question question = transformQuestion(entity);
                if (question != null) {
                    questionList.add(question);
                }
            }
        }
        return questionList;
    }

    public Post transformPost(PostEntity pEntity){
        if(pEntity != null){
            Post post = new Post();
            return post;
        }
        return null;
    }

    public List<Post> transformPost(List<PostEntity> pEntities){
        List<Post> postList = new ArrayList<>();
        if(pEntities != null) {
            for (PostEntity entity : pEntities) {
                Post post = transformPost(entity);
                if (post != null)
                    postList.add(post);
            }
        }
        return postList;
    }
}
