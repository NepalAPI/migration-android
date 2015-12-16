package com.yipl.nrna.data.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yipl.nrna.data.entity.CountryEntity;
import com.yipl.nrna.data.entity.PostDataEntity;
import com.yipl.nrna.data.entity.PostEntity;
import com.yipl.nrna.data.entity.QuestionEntity;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Nirazan-PC on 12/9/2015.
 */
public class DatabaseDao {

    DatabaseHelper helper;
    SQLiteDatabase db;

    @Inject
    public DatabaseDao(DatabaseHelper helper) {
        this.helper = helper;
        db = helper.getWritableDatabase();
    }

    public CountryEntity mapCursorToCountryEntity(Cursor pCursor){
        CountryEntity country = new CountryEntity();
        country.setId(pCursor.getLong(pCursor.getColumnIndex(MyConstants.DATABASE.TABLE_COUNTRY
                .COLUMN_ID)));
        country.setCreatedAt(pCursor.getLong(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COLUMN_CREATED_AT)));
        country.setUpdatedAt(pCursor.getLong(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COlUMN_UPDATED_AT)));
        country.setName(pCursor.getString(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COLUMN_NAME)));
        country.setAbout(pCursor.getString(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COLUMN_ABOUT)));
        country.setImageUrl(pCursor.getString(pCursor.getColumnIndex(MyConstants.DATABASE.TABLE_COUNTRY
                .COLUMN_IMAGE_URL)));
        return country;
    }

    public PostEntity mapCursorToPostEntity(Cursor cursor) {

        PostEntity post = new PostEntity();
        post.setId(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_ID)));
        post.setCreatedAt(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_CREATED_AT)));
        post.setUpdatedAt(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_UPDATED_AT)));
        post.setData(new Gson().fromJson(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_DATA)), PostDataEntity.class));
        post.setType(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_TYPE)));
        post.setLanguage(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_LANGUAGE)));
        post.setTags(new Gson().fromJson(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_TAGS)),
                new TypeToken<List<String>>() {
                }.getType()));
        post.setSource(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_SOURCE)));
        post.setDescription(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_DESCRIPTION)));
        post.setTitle(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_TITLE)));

        return post;
    }

    public Observable<List<PostEntity>> getAllPosts(int pLimit) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_POST.TABLE_NAME +
                " order by " + MyConstants.DATABASE.TABLE_POST.COLUMN_UPDATED_AT +
                " DESC LIMIT " + pLimit;
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<PostEntity>> c = new Callable<List<PostEntity>>() {
            @Override
            public List<PostEntity> call() throws Exception {
                List<PostEntity> posts = new ArrayList<PostEntity>();
                while (cursor.moveToNext()) {
                    posts.add(mapCursorToPostEntity(cursor));
                }
                cursor.close();
                return posts;
            }
        };
        return makeObservable(c);
    }

    public Observable<PostEntity> getPostById(Long pId) {
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_POST.TABLE_NAME +
                " where " + MyConstants.DATABASE.TABLE_POST.COLUMN_ID + " = " + pId +
                " LIMIT 1 ";
        Cursor cursor = db.rawQuery(query, null);

        Callable<PostEntity> callable = new Callable<PostEntity>() {
            @Override
            public PostEntity call() throws Exception {
                PostEntity post = null;
                if (cursor.moveToFirst()) {
                    post = mapCursorToPostEntity(cursor);
                }
                cursor.close();
                return post;
            }
        };

        return makeObservable(callable);
    }

    public Observable<List<PostEntity>> getPostByType(int pLimit, String type) {
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_POST.TABLE_NAME + " where " +
                MyConstants.DATABASE.TABLE_POST.COLUMN_TYPE + " = '" + type + "'" +
                " order by " + MyConstants.DATABASE.TABLE_POST.COLUMN_UPDATED_AT +
                " DESC Limit "+ pLimit;
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<PostEntity>> c = new Callable<List<PostEntity>>() {
            @Override
            public List<PostEntity> call() throws Exception {
                List<PostEntity> posts = new ArrayList<PostEntity>();
                while (cursor.moveToNext()) {
                    posts.add(mapCursorToPostEntity(cursor));
                }
                cursor.close();
                return posts;
            }
        };

        return makeObservable(c);
    }

    public Observable<List<PostEntity>> getPostByStage(int pLimit, String pStage) {
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_POST.TABLE_NAME + " where " +
                MyConstants.DATABASE.TABLE_POST.COLUMN_STAGE + " = '" + pStage + "'" +
                " order by " + MyConstants.DATABASE.TABLE_POST.COLUMN_UPDATED_AT +
                " DESC Limit "+ pLimit;
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<PostEntity>> c = new Callable<List<PostEntity>>() {
            @Override
            public List<PostEntity> call() throws Exception {
                List<PostEntity> posts = new ArrayList<PostEntity>();
                while (cursor.moveToNext()) {
                    posts.add(mapCursorToPostEntity(cursor));
                }
                cursor.close();
                return posts;
            }
        };

        return makeObservable(c);
    }

    public QuestionEntity convertCursorToQuestionEntity(Cursor cursor) {

        QuestionEntity question = new QuestionEntity();
        question.setId(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID)));
        question.setCreatedAt(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_CREATED_AT)));
        question.setUpdatedAt(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COlUMN_UPDATED_AT)));
        question.setTags(new Gson().fromJson(cursor.getString(cursor.getColumnIndex
                (MyConstants.DATABASE.TABLE_QUESTION.COLUMN_TAGS)), new TypeToken<List<String>>() {
        }.getType()));
        question.setLanguage(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_LANGUAGE)));
        question.setQuestion(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_QUESTION)));

        return question;
    }

    public Observable<List<QuestionEntity>> getAllQuestions(int pLimit) {

        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_QUESTION.TABLE_NAME +
                " order by " + MyConstants.DATABASE.TABLE_QUESTION.COlUMN_UPDATED_AT +
                " DESC LIMIT " + pLimit;
        Cursor cursor = db.rawQuery(query, null);
        Callable<List<QuestionEntity>> callable = new Callable<List<QuestionEntity>>() {
            @Override
            public List<QuestionEntity> call() throws Exception {
                List<QuestionEntity> questions = new ArrayList<>();
                while (cursor.moveToNext()) {
                    questions.add(convertCursorToQuestionEntity(cursor));
                }
                cursor.close();
                return questions;
            }
        };
        return makeObservable(callable);
    }

    public Observable<List<QuestionEntity>> getQuestionByStage(String mStage) {

        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_QUESTION.TABLE_NAME +
                " where " + MyConstants.DATABASE.TABLE_QUESTION.COLUMN_STAGE + " = '" + mStage +
                " order by " + MyConstants.DATABASE.TABLE_QUESTION.COlUMN_UPDATED_AT +
                " DESC ;";
        Cursor cursor = db.rawQuery(query, null);
        Callable<List<QuestionEntity>> callable = new Callable<List<QuestionEntity>>() {
            @Override
            public List<QuestionEntity> call() throws Exception {
                List<QuestionEntity> questions = new ArrayList<>();
                while (cursor.moveToNext()) {
                    questions.add(convertCursorToQuestionEntity(cursor));
                }
                cursor.close();
                return questions;
            }
        };
        return makeObservable(callable);
    }

    public Observable<QuestionEntity> getQuestionById(int pId) {
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_QUESTION.TABLE_NAME +
                " where " + MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID + " = " + pId +
                "  LIMIT 1 ";
        Cursor cursor = db.rawQuery(query, null);
        Callable<QuestionEntity> callable = new Callable<QuestionEntity>() {
            @Override
            public QuestionEntity call() throws Exception {
                QuestionEntity question = null;
                if (cursor.moveToFirst()) {
                    question = convertCursorToQuestionEntity(cursor);
                }
                cursor.close();
                return question;
            }
        };
        return makeObservable(callable);
    }

    public void saveOnePost(PostEntity post) {

        String sqlQuery = "Insert into " + MyConstants.DATABASE.TABLE_POST.TABLE_NAME + "( " +
                MyConstants.DATABASE.TABLE_POST.COLUMN_ID + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_UPDATED_AT + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_CREATED_AT + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_DATA + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_TYPE + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_LANGUAGE + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_TAGS + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_SOURCE + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_DESCRIPTION + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_POST.COLUMN_TITLE + MyConstants.DATABASE.COMMA +
                " values( " +
                post.getId() + MyConstants.DATABASE.COMMA +
                post.getUpdatedAt() + MyConstants.DATABASE.COMMA +
                post.getCreatedAt() + MyConstants.DATABASE.COMMA +
                new Gson().toJson(post.getData()) + MyConstants.DATABASE.COMMA +
                post.getType() + MyConstants.DATABASE.COMMA +
                post.getLanguage() + MyConstants.DATABASE.COMMA +
                post.getTags() + MyConstants.DATABASE.COMMA +
                post.getSource() + MyConstants.DATABASE.COMMA +
                post.getDescription() + MyConstants.DATABASE.COMMA +
                post.getTitle() +
                " )";
        savePostQuestionRelation(post.getId(), post.getQuestionIdList());
        db.execSQL(sqlQuery);
    }

    public void saveAllPost(List<PostEntity> posts) {
        if (posts != null) {
            for (PostEntity post : posts) {
                if (post != null)
                    saveOnePost(post);
            }
        }
    }

    public void saveOneQuestion(QuestionEntity question) {

        String sqlQuery = "Insert into " + MyConstants.DATABASE.TABLE_QUESTION.TABLE_NAME + "( " +
                MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_QUESTION.COlUMN_UPDATED_AT + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_QUESTION.COLUMN_CREATED_AT + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_QUESTION.COLUMN_TAGS + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_QUESTION.COLUMN_LANGUAGE + MyConstants.DATABASE.COMMA +
                MyConstants.DATABASE.TABLE_QUESTION.COLUMN_QUESTION +
                " ) values ( " +
                question.getId() + MyConstants.DATABASE.COMMA +
                question.getUpdatedAt() + MyConstants.DATABASE.COMMA +
                question.getCreatedAt() + MyConstants.DATABASE.COMMA +
                new Gson().toJson(question.getTags()) + MyConstants.DATABASE.COMMA +
                question.getLanguage() + MyConstants.DATABASE.COMMA +
                question.getQuestion() +
                " )";

        db.execSQL(sqlQuery);
    }

    public void saveAllQuestion(List<QuestionEntity> questions) {

        if (questions != null) {
            for (QuestionEntity question : questions) {
                if (question != null)
                    saveOneQuestion(question);
            }
        }
    }

    public void savePostQuestionRelation(Long postId, List<Long> questionIds) {
        if (questionIds != null) {
            for (Long id : questionIds) {
                if (id != null) {
                    String query = "Insert into " + MyConstants.DATABASE.TABLE_POST_QUESTION.TABLE_NAME + "( " +
                            MyConstants.DATABASE.TABLE_POST_QUESTION.COLUMN_POST_ID + MyConstants.DATABASE.COMMA +
                            MyConstants.DATABASE.TABLE_POST_QUESTION.COLUMN_QUESTION_ID +
                            " );";
                    db.execSQL(query);
                }
            }
        }
    }

    public Observable<List<PostEntity>> getPostByQuestionAndType(Long pId, String pType){

        String query = "Select * from " + MyConstants.DATABASE.TABLE_POST.TABLE_NAME + " p join " +
                MyConstants.DATABASE.TABLE_POST_QUESTION.TABLE_NAME + " pq on p."+ MyConstants.DATABASE.TABLE_POST.COLUMN_ID +
                " = pq."+ MyConstants.DATABASE.TABLE_POST_QUESTION.COLUMN_POST_ID + " join "+MyConstants.DATABASE.TABLE_QUESTION.TABLE_NAME +
                " q on q."+MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID + " = pq."+ MyConstants.DATABASE.TABLE_POST_QUESTION.COLUMN_QUESTION_ID +
                " where q." + MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID + " = " + pId;

        if(pType != null || !pType.isEmpty()){
            query += " and type = '" + pType + "'";
        }

        Cursor cursor = db.rawQuery(query, null);
        Log.i("query ", query +" / "+cursor.getCount());
        Callable<List<PostEntity>> c = new Callable<List<PostEntity>>() {
            @Override
            public List<PostEntity> call() throws Exception {
                List<PostEntity> posts = new ArrayList<PostEntity>();
                while (cursor.moveToNext()) {
                    posts.add(mapCursorToPostEntity(cursor));
                }
                cursor.close();
                return posts;
            }
        };
        return makeObservable(c);

    }

    public void updateOnePost(PostEntity post) {
        // TODO: 12/11/2015
        String sqlQuery = "Update into";
    }

    public void updateAllPosts(List<PostEntity> posts) {
        // TODO: 12/11/2015
        for (PostEntity post : posts) {
            updateOnePost(post);
        }
    }

    public void updateOneQuestion(QuestionEntity question) {
        // TODO: 12/11/2015
        String sqlQuery = "Update into";
    }

    public void updateAllQuestions(List<QuestionEntity> questions) {
        // TODO: 12/11/2015
        for (QuestionEntity question : questions) {
            updateOneQuestion(question);
        }
    }

    public Observable<List<CountryEntity>> getAllCountries(int pLimit) {
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_COUNTRY.TABLE_NAME +
                " order by " + MyConstants.DATABASE.TABLE_COUNTRY.COlUMN_UPDATED_AT +
                " DESC LIMIT " + pLimit;
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<CountryEntity>> c = new Callable<List<CountryEntity>>() {
            @Override
            public List<CountryEntity> call() throws Exception {
                List<CountryEntity> countryList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    countryList.add(mapCursorToCountryEntity(cursor));
                }
                cursor.close();
                return countryList;
            }
        };
        return makeObservable(c);
    }

    public Observable<CountryEntity> getCountryById(Long pId) {
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_COUNTRY.TABLE_NAME +
                " where " + MyConstants.DATABASE.TABLE_COUNTRY.COLUMN_ID + " = " + pId +
                " LIMIT 1 ";
        Cursor cursor = db.rawQuery(query, null);

        Callable<CountryEntity> callable = new Callable<CountryEntity>() {
            @Override
            public CountryEntity call() throws Exception {
                CountryEntity countryEntity = null;
                if (cursor.moveToFirst()) {
                    countryEntity = mapCursorToCountryEntity(cursor);
                }
                cursor.close();
                return countryEntity;
            }
        };

        return makeObservable(callable);
    }

    private <T> Observable<T> makeObservable(final Callable<T> callable) {

        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(callable.call());
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                });
    }
}
