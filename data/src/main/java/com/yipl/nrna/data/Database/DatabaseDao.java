package com.yipl.nrna.data.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yipl.nrna.data.entity.CountryEntity;
import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.entity.PostDataEntity;
import com.yipl.nrna.data.entity.PostEntity;
import com.yipl.nrna.data.entity.QuestionEntity;
import com.yipl.nrna.domain.util.MyConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_COUNTRY;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_POST;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_POST_QUESTION;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_QUESTION;

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

    public CountryEntity mapCursorToCountryEntity(Cursor pCursor) {
        CountryEntity country = new CountryEntity();
        country.setId(pCursor.getLong(pCursor.getColumnIndex(MyConstants.DATABASE.TABLE_COUNTRY
                .COLUMN_ID)));
        country.setCreatedAt(pCursor.getLong(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COLUMN_CREATED_AT)));
        country.setUpdatedAt(pCursor.getLong(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COlUMN_UPDATED_AT)));
        country.setName(pCursor.getString(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COLUMN_NAME)));
        country.setDescription(pCursor.getString(pCursor.getColumnIndex(
                TABLE_COUNTRY.COLUMN_DESCRIPTION)));
        country.setImage(pCursor.getString(pCursor.getColumnIndex(MyConstants.DATABASE.TABLE_COUNTRY
                .COLUMN_IMAGE)));
        country.setCode(pCursor.getString(pCursor.getColumnIndex(TABLE_COUNTRY
                .COLUMN_CODE)));
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

    public QuestionEntity mapCursorToQuestionEntity(Cursor cursor) {

        QuestionEntity question = new QuestionEntity();
        question.setId(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID)));
        question.setCreatedAt(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_CREATED_AT)));
        question.setUpdatedAt(cursor.getLong(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COlUMN_UPDATED_AT)));
        question.setTags(new Gson().fromJson(cursor.getString(cursor.getColumnIndex
                (MyConstants.DATABASE.TABLE_QUESTION.COLUMN_TAGS)), new TypeToken<List<String>>() {
        }.getType()));
        question.setLanguage(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_LANGUAGE)));
        question.setTitle(cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.TABLE_QUESTION.COLUMN_QUESTION)));

        return question;
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
                " DESC Limit " + pLimit;
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
                " DESC Limit " + pLimit;
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
                    questions.add(mapCursorToQuestionEntity(cursor));
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
                    questions.add(mapCursorToQuestionEntity(cursor));
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
                    question = mapCursorToQuestionEntity(cursor);
                }
                cursor.close();
                return question;
            }
        };
        return makeObservable(callable);
    }

    public Observable<List<PostEntity>> getPostByQuestionAndType(Long pId, String pType) {

        String query = "Select * from " + MyConstants.DATABASE.TABLE_POST.TABLE_NAME + " p join " +
                MyConstants.DATABASE.TABLE_POST_QUESTION.TABLE_NAME + " pq on p." + MyConstants.DATABASE.TABLE_POST.COLUMN_ID +
                " = pq." + MyConstants.DATABASE.TABLE_POST_QUESTION.COLUMN_POST_ID + " join " + MyConstants.DATABASE.TABLE_QUESTION.TABLE_NAME +
                " q on q." + MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID + " = pq." + MyConstants.DATABASE.TABLE_POST_QUESTION.COLUMN_QUESTION_ID +
                " where q." + MyConstants.DATABASE.TABLE_QUESTION.COLUMN_ID + " = " + pId;

        if (pType != null || !pType.isEmpty()) {
            query += " and type = '" + pType + "'";
        }

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

    /*======= Save Operations ==========*/

    public void insertUpdate(LatestContentEntity pLatestContent) {
        saveAllQuestion(pLatestContent.getQuestions());
        saveAllPost(pLatestContent.getPosts());
        saveAllCountries(pLatestContent.getCountries());
    }

    public void saveAllPost(List<PostEntity> posts) {
        if (posts != null) {
            for (PostEntity post : posts) {
                if (post != null)
                    insertUpdatePost(post);
            }
        }
    }

    public long insertUpdatePost(PostEntity pPost) {
        Cursor cursor;
        String query = "Select count(*) as count from " +
                MyConstants.DATABASE.TABLE_POST.TABLE_NAME +
                " where " + MyConstants.DATABASE.TABLE_POST.COLUMN_ID + " = " + pPost.getId();

        cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        long count = cursor.getLong(0);
        cursor.close();

        if (count > 0) {
            return updateOnePost(pPost);
        } else {
            return saveOnePost(pPost);
        }
    }

    public long saveOnePost(PostEntity post) {
        long insertId = db.insert(TABLE_POST.TABLE_NAME, null, getContentValues(post));
        savePostQuestionRelation(post.getId(), post.getQuestionIdList());
        return insertId;
    }

    public long updateOnePost(PostEntity post) {
        long insertId = db.update(TABLE_POST.TABLE_NAME, getContentValues(post), TABLE_POST
                .COLUMN_ID + " = " + post.getId(), null);
        return insertId;
    }

    public void savePostQuestionRelation(Long postId, List<Long> questionIds) {
        if (questionIds != null) {
            for (Long id : questionIds) {
                if (id != null) {
                    ContentValues values = new ContentValues();
                    values.put(TABLE_POST_QUESTION.COLUMN_POST_ID, postId);
                    values.put(TABLE_POST_QUESTION.COLUMN_QUESTION_ID, id);
                    db.insert(TABLE_POST_QUESTION.TABLE_NAME, null, values);
                }
            }
        }
    }

    public void saveAllQuestion(List<QuestionEntity> questions) {

        if (questions != null) {
            for (QuestionEntity question : questions) {
                if (question != null)
                    insertUpdateQuestion(question);
            }
        }
    }

    public long insertUpdateQuestion(QuestionEntity pQuestion) {
        Cursor cursor;
        String query = "Select count(*) as count from " +
                MyConstants.DATABASE.TABLE_QUESTION.TABLE_NAME + " where " + MyConstants.DATABASE
                .TABLE_QUESTION.COLUMN_ID + " = " + pQuestion.getId();

        cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        long count = cursor.getLong(0);
        cursor.close();

        if (count > 0) {
            return updateOneQuestion(pQuestion);
        } else {
            return saveOneQuestion(pQuestion);
        }
    }

    public long saveOneQuestion(QuestionEntity question) {
        return db.insert(TABLE_QUESTION.TABLE_NAME, null, getContentValues(question));
    }

    public long updateOneQuestion(QuestionEntity question) {
        return db.update(TABLE_QUESTION.TABLE_NAME, getContentValues(question), TABLE_QUESTION
                .COLUMN_ID + " = " + question.getId(), null);
    }

    public void saveAllCountries(List<CountryEntity> pCountries) {
        if (pCountries != null) {
            for (CountryEntity country : pCountries) {
                if (country != null)
                    insertUpdateCountry(country);
            }
        }
    }

    public long insertUpdateCountry(CountryEntity pCountry) {
        Cursor cursor;
        String query = "Select count(*) as count from " +
                MyConstants.DATABASE.TABLE_COUNTRY.TABLE_NAME + " where " + MyConstants.DATABASE
                .TABLE_COUNTRY.COLUMN_ID + " = " + pCountry.getId();

        cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        long count = cursor.getLong(0);
        cursor.close();
        if (count > 0) {
            return updateOneCountry(pCountry);
        } else {
            return saveOneCountry(pCountry);
        }
    }

    public long saveOneCountry(CountryEntity pCountry) {
        return db.insert(TABLE_COUNTRY.TABLE_NAME, null, getContentValues(pCountry));
    }

    public long updateOneCountry(CountryEntity pCountry) {
        return db.update(TABLE_COUNTRY.TABLE_NAME, getContentValues(pCountry), TABLE_QUESTION
                .COLUMN_ID + " = " + pCountry.getId(), null);
    }

    private ContentValues getContentValues(PostEntity pPost) {
        ContentValues values = new ContentValues();
        Gson gson = new Gson();
        values.put(TABLE_POST.COLUMN_ID, pPost.getId());
        values.put(TABLE_POST.COLUMN_CREATED_AT, pPost.getUpdatedAt());
        values.put(TABLE_POST.COLUMN_UPDATED_AT, pPost.getCreatedAt());
        values.put(TABLE_POST.COLUMN_DATA, gson.toJson(pPost.getData()));
        values.put(TABLE_POST.COLUMN_TYPE, pPost.getType());
        values.put(TABLE_POST.COLUMN_LANGUAGE, pPost.getLanguage());
        values.put(TABLE_POST.COLUMN_TAGS, gson.toJson(pPost.getTags()));
        values.put(TABLE_POST.COLUMN_SOURCE, pPost.getSource());
        values.put(TABLE_POST.COLUMN_DESCRIPTION, pPost.getDescription());
        values.put(TABLE_POST.COLUMN_TITLE, pPost.getTitle());
        values.put(TABLE_POST.COLUMN_STAGE, pPost.getStage());
        return values;
    }

    private ContentValues getContentValues(QuestionEntity pQuestion) {
        ContentValues values = new ContentValues();
        values.put(TABLE_QUESTION.COLUMN_ID, pQuestion.getId());
        values.put(TABLE_QUESTION.COlUMN_UPDATED_AT, pQuestion.getUpdatedAt());
        values.put(TABLE_QUESTION.COLUMN_CREATED_AT, pQuestion.getCreatedAt());
        values.put(TABLE_QUESTION.COLUMN_TAGS, new Gson().toJson(pQuestion.getTags()));
        values.put(TABLE_QUESTION.COLUMN_LANGUAGE, pQuestion.getLanguage());
        values.put(TABLE_QUESTION.COLUMN_STAGE, pQuestion.getStage());
        values.put(TABLE_QUESTION.COLUMN_QUESTION, pQuestion.getTitle());
        return values;
    }

    private ContentValues getContentValues(CountryEntity pCountry) {
        ContentValues values = new ContentValues();
        values.put(TABLE_COUNTRY.COLUMN_ID, pCountry.getId());
        values.put(TABLE_COUNTRY.COlUMN_UPDATED_AT, pCountry.getUpdatedAt());
        values.put(TABLE_COUNTRY.COLUMN_CREATED_AT, pCountry.getCreatedAt());
        values.put(TABLE_COUNTRY.COLUMN_DESCRIPTION, pCountry.getDescription());
        values.put(TABLE_COUNTRY.COLUMN_IMAGE, pCountry.getImage());
        values.put(TABLE_COUNTRY.COLUMN_NAME, pCountry.getName());
        values.put(TABLE_COUNTRY.COLUMN_CODE, pCountry.getCode());
        return values;
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
