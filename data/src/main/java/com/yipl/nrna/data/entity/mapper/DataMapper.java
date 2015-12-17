package com.yipl.nrna.data.entity.mapper;

import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.data.entity.CountryEntity;
import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.entity.PostDataEntity;
import com.yipl.nrna.data.entity.PostEntity;
import com.yipl.nrna.data.entity.QuestionEntity;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.PostData;
import com.yipl.nrna.domain.model.Question;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class DataMapper {

    @Inject
    public DataMapper() {
    }

    public LatestContent transformLatestContent(LatestContentEntity pEntity) {
        LatestContent latestContent = null;
        if (pEntity != null) {
            latestContent = new LatestContent();
            latestContent.setQuestions(transformQuestion(pEntity.getQuestions()));
            latestContent.setPosts(transformPost(pEntity.getPosts()));
        }
        return latestContent;
    }

    public Question transformQuestion(QuestionEntity pEntity) {
        if (pEntity != null) {
            Question question = new Question();
            question.setId(pEntity.getId());
            question.setTitle(pEntity.getTitle());
            question.setLanguage(pEntity.getLanguage());
            question.setTags(pEntity.getTags());
            question.setStage(pEntity.getStage());
            question.setCreatedAt(pEntity.getCreatedAt());
            question.setUpdatedAt(pEntity.getUpdatedAt());
            return question;
        }
        return null;
    }

    public List<Question> transformQuestion(List<QuestionEntity> pEntities) {
        List<Question> questionList = new ArrayList<>();
        if (pEntities != null) {
            for (QuestionEntity entity : pEntities) {
                Question question = transformQuestion(entity);
                if (question != null) {
                    questionList.add(question);
                }
            }
        }
        return questionList;
    }

    public Post transformPost(PostEntity pEntity) {
        if (pEntity != null) {
            Post post = new Post();
            post.setId(pEntity.getId());
            post.setType(pEntity.getType());
            post.setTitle(pEntity.getTitle());
            post.setDescription(pEntity.getDescription());
            post.setLanguage(pEntity.getLanguage());
            post.setSource(pEntity.getSource());
            post.setUpdatedAt(pEntity.getUpdatedAt());
            post.setCreatedAt(pEntity.getCreatedAt());
            post.setQuestionIdList(pEntity.getQuestionIdList());
            post.setTags(pEntity.getTags());
            post.setData(transformPostData(pEntity.getData()));
            post.setStage(pEntity.getStage());
            return post;
        }
        return null;
    }

    public List<Post> transformPost(List<PostEntity> pEntities) {
        List<Post> postList = new ArrayList<>();
        if (pEntities != null) {
            for (PostEntity entity : pEntities) {
                Post post = transformPost(entity);
                if (post != null)
                    postList.add(post);
            }
        }
        return postList;
    }

    public Country transformCountry(CountryEntity pCountry) {
        if (pCountry != null) {
            Country country = new Country();
            country.setId(pCountry.getId());
            country.setUpdatedAt(pCountry.getUpdatedAt());
            country.setCreatedAt(pCountry.getCreatedAt());
            country.setDescription(pCountry.getDescription());
            country.setImage(pCountry.getImage());
            country.setName(pCountry.getName());
            country.setCode(pCountry.getCode());
            return country;
        }
        return null;
    }

    public List<Country> transformCountry(List<CountryEntity> pEntities) {
        List<Country> countryList = new ArrayList<>();
        if (pEntities != null) {
            for (CountryEntity entity : pEntities) {
                Country country = transformCountry(entity);
                if (country != null)
                    countryList.add(country);
            }
        }
        return countryList;
    }

    public PostData transformPostData(PostDataEntity pEntity) {
        PostData data = null;
        if (pEntity != null) {
            data = new PostData();
            data.setContent(pEntity.getContent());
            data.setMediaUrl(pEntity.getMediaUrl());
            data.setDuration(pEntity.getDuration());
            data.setThumbnail(pEntity.getThumbnail());
        }
        return data;
    }
}
