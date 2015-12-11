package com.yipl.nrna.data.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yipl.nrna.data.entity.PostDataEntity;
import com.yipl.nrna.data.entity.PostEntity;
import com.yipl.nrna.data.entity.QuestionEntity;
import com.yipl.nrna.domain.model.PostData;
import com.yipl.nrna.domain.util.MyConstants;

import org.json.JSONArray;

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

    public PostEntity convertCursorToPostEntity(Cursor cursor) {

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
                    posts.add(convertCursorToPostEntity(cursor));
                }
                return posts;
            }
        };
        cursor.close();
        return makeObservable(c);
    }

    public Observable<PostEntity> getPostById(Long pId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_POST.TABLE_NAME +
                " where " + MyConstants.DATABASE.TABLE_POST.COLUMN_ID + " = " + pId +
                " LIMIT = 1 ";
        Cursor cursor = db.rawQuery(query, null);

        Callable<PostEntity> callable = new Callable<PostEntity>() {
            @Override
            public PostEntity call() throws Exception {
                PostEntity post = null;
                if (cursor.moveToFirst()) {
                    post = convertCursorToPostEntity(cursor);
                }
                return post;
            }
        };
        cursor.close();
        return makeObservable(callable);
    }

    public Observable<List<PostEntity>> getPostByType(String type) {
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_POST.TABLE_NAME + " where " +
                MyConstants.DATABASE.TABLE_POST.COLUMN_TYPE + " = '" + type + "'" +
                " order by " + MyConstants.DATABASE.TABLE_POST.COLUMN_UPDATED_AT +
                " DESC";
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<PostEntity>> c = new Callable<List<PostEntity>>() {
            @Override
            public List<PostEntity> call() throws Exception {
                List<PostEntity> posts = new ArrayList<PostEntity>();
                while (cursor.moveToNext()) {
                    posts.add(convertCursorToPostEntity(cursor));
                }
                return posts;
            }
        };
        cursor.close();
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
        question.setQuestion(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID)));

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
                return questions;
            }
        };
        cursor.close();
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
                return questions;
            }
        };
        cursor.close();
        return makeObservable(callable);
    }

    public Observable<QuestionEntity> getQuestionById(int pId) {
        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_QUESTION.TABLE_NAME +
                " where " + MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID + " = " + pId +
                "  LIMIT = 1 ";
        Cursor cursor = db.rawQuery(query, null);
        Callable<QuestionEntity> callable = new Callable<QuestionEntity>() {
            @Override
            public QuestionEntity call() throws Exception {
                QuestionEntity question = null;
                if (cursor.moveToFirst()) {
                    question = convertCursorToQuestionEntity(cursor);
                }
                return question;
            }
        };
        cursor.close();
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

    public void savePostQuestionRelation(Long postId, List<String> questionIds) {
        if (questionIds != null) {
            for (String id : questionIds) {
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
