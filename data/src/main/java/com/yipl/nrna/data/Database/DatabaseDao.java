package com.yipl.nrna.data.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yipl.nrna.data.entity.PostEntity;
import com.yipl.nrna.data.entity.QuestionEntity;
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
        this.db = helper.getWritableDatabase();
    }

    public Observable<List<PostEntity>> getAllPosts(int limit) {

        String query = "Select * from " +
                MyConstants.DATABASE.TABLE_POST.TABLE_NAME +
                " order by " + MyConstants.DATABASE.TABLE_POST.COLUMN_UPDATED_AT +
                " DESC LIMIT " + limit;
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<PostEntity>> c = new Callable<List<PostEntity>>() {
            @Override
            public List<PostEntity> call() throws Exception {

                List<PostEntity> posts = new ArrayList<PostEntity>();

                while (cursor.moveToNext()) {

                    PostEntity post = new PostEntity();

                    post.setId(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_ID)));
                    post.setCreatedAt(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_CREATED_AT)));
                    post.setDescription(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_POST.COLUMN_DESCRIPTION)));


                    posts.add(post);
                }
                return posts;
            }
        };


        return makeObservable(c);

    }

    public Observable<PostEntity> getPostById(int Id) {


        Cursor cursor = null;

        Callable<PostEntity> callable = new Callable<PostEntity>() {
            @Override
            public PostEntity call() throws Exception {

                PostEntity p = null;

                if (cursor.moveToFirst()) {

                    p = new PostEntity();


                }
                return p;

            }
        };

        return makeObservable(callable);

    }

    public Observable<List<QuestionEntity>> getAllQuestions(int limit) {

        Cursor cursor = null;

        Callable<List<QuestionEntity>> callable = new Callable<List<QuestionEntity>>() {
            @Override
            public List<QuestionEntity> call() throws Exception {

                List<QuestionEntity> questions = new ArrayList<>();

                while (cursor.moveToNext()) {


                }

                return questions;
            }
        };

        return makeObservable(callable);


    }


    public Observable<QuestionEntity> getQuestionById(int Id) {


        Cursor cursor = null;

        Callable<QuestionEntity> callable = new Callable<QuestionEntity>() {
            @Override
            public QuestionEntity call() throws Exception {

                QuestionEntity p = null;

                if (cursor.moveToFirst()) {

                    p = new QuestionEntity();


                }
                return p;

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
                "data place" + MyConstants.DATABASE.COMMA +
                "type place" + MyConstants.DATABASE.COMMA +
                post.getLanguage() + MyConstants.DATABASE.COMMA +
                post.getTags() + MyConstants.DATABASE.COMMA +
                post.getSource() + MyConstants.DATABASE.COMMA +
                post.getDescription() + MyConstants.DATABASE.COMMA +
                post.getTitle() +
                " )";

        db.execSQL(sqlQuery);


    }

    public void saveAllPost(List<PostEntity> posts) {

        for (PostEntity post : posts) {

            saveOnePost(post);

        }
    }

    public void saveOneQuestion(QuestionEntity question) {


        String sqlQuery = "Insert into ";


    }

    public void saveAllQuestion(List<QuestionEntity> questions) {

        for (QuestionEntity question : questions) {

            saveOneQuestion(question);

        }
    }

    public void updateOnePost(PostEntity post) {

        String sqlQuery = "Update into";


    }

    public void updateAllPosts(List<PostEntity> posts) {

        for (PostEntity post : posts) {

            updateOnePost(post);

        }

    }

    public void updateOneQuestion(QuestionEntity question) {

        String sqlQuery = "Update into";


    }

    public void updateAllQuestions(List<QuestionEntity> questions) {

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
