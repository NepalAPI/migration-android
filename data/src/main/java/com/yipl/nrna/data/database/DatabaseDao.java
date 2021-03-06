package com.yipl.nrna.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yipl.nrna.data.entity.AnswerEntity;
import com.yipl.nrna.data.entity.CountryEntity;
import com.yipl.nrna.data.entity.CountryUpdateEntity;
import com.yipl.nrna.data.entity.DeletedContentDataEntity;
import com.yipl.nrna.data.entity.DeletedContentEntity;
import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.entity.PostDataEntity;
import com.yipl.nrna.data.entity.PostEntity;
import com.yipl.nrna.data.entity.QuestionEntity;
import com.yipl.nrna.data.utils.Logger;
import com.yipl.nrna.domain.util.MyConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_ANSWER;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_COUNTRY;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_COUNTRY_UPDATE;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_POST;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_POST_ANSWER;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_POST_COUNTRY;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_POST_QUESTION;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_QUESTION;
import static com.yipl.nrna.domain.util.MyConstants.DATABASE.TABLE_TAGS;

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
        country.setId(pCursor.getLong(pCursor.getColumnIndex(TABLE_COUNTRY
                .COLUMN_ID)));
        country.setCreatedAt(pCursor.getLong(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COLUMN_CREATED_AT)));
        country.setUpdatedAt(pCursor.getLong(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COlUMN_UPDATED_AT)));
        country.setName(pCursor.getString(pCursor.getColumnIndex(MyConstants.DATABASE
                .TABLE_COUNTRY.COLUMN_NAME)));
        country.setDescription(pCursor.getString(pCursor.getColumnIndex(
                TABLE_COUNTRY.COLUMN_DESCRIPTION)));
        country.setContacts(pCursor.getString(pCursor.getColumnIndex(
                TABLE_COUNTRY.COLUMN_CONTACTS)));
        country.setDosAndDonts(pCursor.getString(pCursor.getColumnIndex(
                TABLE_COUNTRY.COLUMN_DOS_DONTS)));
        country.setImage(pCursor.getString(pCursor.getColumnIndex(TABLE_COUNTRY
                .COLUMN_IMAGE)));
        country.setCode(pCursor.getString(pCursor.getColumnIndex(TABLE_COUNTRY
                .COLUMN_CODE)));
        return country;
    }

    public PostEntity mapCursorToPostEntity(Cursor cursor) {

        PostEntity post = new PostEntity();
        post.setId(cursor.getLong(cursor.getColumnIndex(TABLE_POST.COLUMN_ID)));
        post.setCreatedAt(cursor.getLong(cursor.getColumnIndex(TABLE_POST.COLUMN_CREATED_AT)));
        post.setUpdatedAt(cursor.getLong(cursor.getColumnIndex(TABLE_POST.COLUMN_UPDATED_AT)));
        post.setData(new Gson().fromJson(cursor.getString(cursor.getColumnIndex(TABLE_POST.COLUMN_DATA)), PostDataEntity.class));
        post.setType(cursor.getString(cursor.getColumnIndex(TABLE_POST.COLUMN_TYPE)));
        post.setLanguage(cursor.getString(cursor.getColumnIndex(TABLE_POST.COLUMN_LANGUAGE)));
        post.setTags(new Gson().fromJson(cursor.getString(cursor.getColumnIndex(TABLE_POST.COLUMN_TAGS)),
                new TypeToken<List<String>>() {
                }.getType()));
        post.setSource(cursor.getString(cursor.getColumnIndex(TABLE_POST.COLUMN_SOURCE)));
        post.setDescription(cursor.getString(cursor.getColumnIndex(TABLE_POST.COLUMN_DESCRIPTION)));
        post.setTitle(cursor.getString(cursor.getColumnIndex(TABLE_POST.COLUMN_TITLE)));
        post.setStage(new Gson().fromJson(cursor.getString(cursor.getColumnIndex(TABLE_POST.COLUMN_STAGE)),
                new TypeToken<List<String>>() {
                }.getType()));
        post.setDownloadStatus(cursor.getInt(cursor.getColumnIndex(TABLE_POST
                .COLUMN_DOWNLOAD_STATUS)) == 1);
        post.setDownloadReference(cursor.getLong(cursor.getColumnIndex(TABLE_POST
                .COLUMN_DOWNLOAD_REF)));
        return post;
    }

    public AnswerEntity mapCursorToAnswerEntity(Cursor cursor) {
        AnswerEntity answer = new AnswerEntity();
        answer.setId(cursor.getLong(cursor.getColumnIndex(TABLE_ANSWER
                .COLUMN_ID)));
        answer.setCreatedAt(cursor.getLong(cursor.getColumnIndex(TABLE_ANSWER
                .COLUMN_CREATED_AT)));
        answer.setUpdatedAt(cursor.getLong(cursor.getColumnIndex(TABLE_ANSWER
                .COLUMN_UPDATED_AT)));
        answer.setTitle(cursor.getString(cursor.getColumnIndex(TABLE_ANSWER
                .COLUMN_TITLE)));
        answer.setQuestionId(cursor.getLong(cursor.getColumnIndex(TABLE_ANSWER
                .COLUMN_QUESTION_ID)));
        return answer;
    }

    public CountryUpdateEntity mapCursorToCountryUpdateEntity(Cursor cursor) {
        CountryUpdateEntity update = new CountryUpdateEntity();
        update.setId(cursor.getLong(cursor.getColumnIndex(TABLE_COUNTRY_UPDATE
                .COLUMN_ID)));
        update.setCreatedAt(cursor.getLong(cursor.getColumnIndex(TABLE_COUNTRY_UPDATE
                .COLUMN_CREATED_AT)));
        update.setUpdatedAt(cursor.getLong(cursor.getColumnIndex(TABLE_COUNTRY_UPDATE
                .COLUMN_UPDATED_AT)));
        update.setTitle(cursor.getString(cursor.getColumnIndex(TABLE_COUNTRY_UPDATE
                .COLUMN_TITLE)));
        update.setDescription(cursor.getString(cursor.getColumnIndex(TABLE_COUNTRY_UPDATE
                .COLUMN_DESCRIPTION)));
        update.setCountryId(cursor.getLong(cursor.getColumnIndex(TABLE_COUNTRY_UPDATE
                .COLUMN_COUNTRY_ID)));
        return update;
    }

    public QuestionEntity mapCursorToQuestionEntity(Cursor cursor) {

        QuestionEntity question = new QuestionEntity();
        question.setId(cursor.getLong(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_ID)));
        question.setCreatedAt(cursor.getLong(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_CREATED_AT)));
        question.setUpdatedAt(cursor.getLong(cursor.getColumnIndex(TABLE_QUESTION.COlUMN_UPDATED_AT)));
        question.setParentId(cursor.getLong(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_PARENT_ID)));
        question.setTags(new Gson().fromJson(cursor.getString(cursor.getColumnIndex
                (TABLE_QUESTION.COLUMN_TAGS)), new TypeToken<List<String>>() {
        }.getType()));
        question.setLanguage(cursor.getString(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_LANGUAGE)));
        question.setTitle(cursor.getString(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_TITLE)));
        question.setAnswer(cursor.getString(cursor.getColumnIndex(TABLE_QUESTION
                .COLUMN_ANSWER)));
        question.setStage(cursor.getString(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_STAGE)));
        question.setWeight(cursor.getLong(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_WEIGHT)));
        if(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_CHILD_IDS) != -1)
            question.setChildIds(cursor.getString(cursor.getColumnIndex(TABLE_QUESTION
                    .COLUMN_CHILD_IDS)));
        if(cursor.getColumnIndex(TABLE_QUESTION.COLUMN_CHILD_TITLES) != -1)
            question.setChildTitles(cursor.getString(cursor.getColumnIndex(TABLE_QUESTION
                    .COLUMN_CHILD_TITLES)));
        return question;
    }

    public Observable<PostEntity> getPostById(Long pId) {
        String query = "Select * from " +
                TABLE_POST.TABLE_NAME +
                " where " + TABLE_POST.COLUMN_ID + " = " + pId +
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

    private String addDownloadStatusToQueryCondition(String pQuery, int pDownloadStatus) {
        if (pDownloadStatus != -1) {
            if (!pQuery.contains("where")) {
                pQuery += " where ";
            } else {
                pQuery += " and ";
            }
            pQuery += " " + TABLE_POST.COLUMN_DOWNLOAD_STATUS + " = " + pDownloadStatus;
        }
        return pQuery;
    }

    public Observable<List<PostEntity>> getCurrentDownloads(){
        String query = "Select * from " + TABLE_POST.TABLE_NAME +
                /*" where " + TABLE_POST.COLUMN_DOWNLOAD_STATUS + " = 0" +
                " and " + TABLE_POST.COLUMN_DOWNLOAD_REF + "!= ''";*/
                " where " + TABLE_POST.COLUMN_DOWNLOAD_REF + "!= ''";
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

    public Observable<List<PostEntity>> getAllPosts(String pStage, String pType, int
            pDownloadStatus, int pLimit) {
        //SQLiteDatabase db = helper.getWritableDatabase();
        String query = "Select * from " + TABLE_POST.TABLE_NAME;
        if (pStage != null || pType != null) {
            query += " where ";
            if (pType != null) {
                query += TABLE_POST.COLUMN_TYPE + " = '" + pType + "'";
                if (pStage != null)
                    query += " and ";
            }
            if (pStage != null) {
                query += TABLE_POST.COLUMN_STAGE + " LIKE '%\"" + pStage + "\"%'";
            }
        }
        query = addDownloadStatusToQueryCondition(query, pDownloadStatus);
        query += " order by " + TABLE_POST.COLUMN_UPDATED_AT +
                " DESC LIMIT " + pLimit;
        Logger.d("DatabaseDao_getAllPosts", "query: " + query);
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

    public Observable<List<PostEntity>> getPostByQuestion(Long pQuestionId, String pStage, String
            pType, int pDownloadStatus, int pLimit, boolean includeChildContents) {
        String query = "Select p.*" +
                " from " + TABLE_POST.TABLE_NAME + " p join " +
                TABLE_POST_QUESTION.TABLE_NAME + " pq on p." + TABLE_POST.COLUMN_ID +
                " = pq." + TABLE_POST_QUESTION.COLUMN_POST_ID + " join " + TABLE_QUESTION.TABLE_NAME+
                " q on q." + TABLE_QUESTION.COLUMN_ID + " = pq." + TABLE_POST_QUESTION.COLUMN_QUESTION_ID +
                " where (q." + TABLE_QUESTION.COLUMN_ID + " = " + pQuestionId;
                if(includeChildContents)
                    query += " or q." + TABLE_QUESTION.COLUMN_PARENT_ID + " = " + pQuestionId + ")";
                else
                    query += ")";

        if (pStage != null || pType != null) {
            query += " and ";
            if (pType != null) {
                query += TABLE_POST.COLUMN_TYPE + " = '" + pType + "'";
                if (pStage != null)
                    query += " and ";
            }
            if (pStage != null) {
                query += TABLE_POST.COLUMN_STAGE + " LIKE '%\"" + pStage + "\"%'";
            }
        }
        query = addDownloadStatusToQueryCondition(query, pDownloadStatus);
        query += " order by " + TABLE_POST.COLUMN_UPDATED_AT +
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

    public Observable<List<PostEntity>> getPostByCountry(Long countryId, String pStage, String
            pType, int pDownloadStatus, int pLimit) {

        String query = "Select p.* from " +
                TABLE_POST.TABLE_NAME + " p join " + TABLE_POST_COUNTRY.TABLE_NAME +
                " pc on p." + TABLE_POST.COLUMN_ID + " = pc." + TABLE_POST_COUNTRY.COLUMN_POST_ID +
                " join " + TABLE_COUNTRY.TABLE_NAME + " c on c." + TABLE_COUNTRY.COLUMN_ID + " = pc." +
                TABLE_POST_COUNTRY.COLUMN_COUNTRY_ID + " where c." + TABLE_COUNTRY.COLUMN_ID + " " +
                "= " + countryId;
        if (pStage != null || pType != null) {
            query += " and ";
            if (pType != null) {
                query += TABLE_POST.COLUMN_TYPE + " = '" + pType + "'";
                if (pStage != null)
                    query += " and ";
            }
            if (pStage != null) {
                query += TABLE_POST.COLUMN_STAGE + " LIKE '%\"" + pStage + "\"%'";
            }
        }
        query = addDownloadStatusToQueryCondition(query, pDownloadStatus);
        query += " order by " + TABLE_POST.COLUMN_UPDATED_AT +
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

    public Observable<List<PostEntity>> getPostByAnswer(Long pAnswerId, String pStage, String
            pType, int pDownloadStatus, int pLimit) {
        String query = "Select p.*" +
                " from " + TABLE_POST.TABLE_NAME + " p join " +
                TABLE_POST_ANSWER.TABLE_NAME + " pa on p." + MyConstants
                .DATABASE.TABLE_POST.COLUMN_ID + " = pa." + MyConstants.DATABASE
                .TABLE_POST_ANSWER.COLUMN_POST_ID + " join " + TABLE_ANSWER
                .TABLE_NAME + " a on a." + TABLE_ANSWER.COLUMN_ID + " = pa."
                + TABLE_POST_ANSWER.COLUMN_ANSWER_ID +
                " where a." + TABLE_ANSWER.COLUMN_ID + " = " + pAnswerId;
        if (pStage != null || pType != null) {
            query += " and ";
            if (pType != null) {
                query += TABLE_POST.COLUMN_TYPE + " = '" + pType + "'";
                if (pStage != null)
                    query += " and ";
            }
            if (pStage != null) {
                query += TABLE_POST.COLUMN_STAGE + " LIKE '%\"" + pStage + "\"%'";
            }
        }
        query = addDownloadStatusToQueryCondition(query, pDownloadStatus);
        query += " order by " + TABLE_POST.COLUMN_UPDATED_AT +
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

    public Observable<QuestionEntity> getQuestionById(Long pId) {
        String query = "Select q1.*, " +
                "group_concat(q2.id, '::') as " + TABLE_QUESTION.COLUMN_CHILD_IDS + ", " +
                "group_concat(q2.title, '::') as " + TABLE_QUESTION.COLUMN_CHILD_TITLES +
                " from " +
                TABLE_QUESTION.TABLE_NAME + " as q1 left join " +
                TABLE_QUESTION.TABLE_NAME + " as q2 on q1.id = q2.parent_id" +
                " where q1.id = " + pId;
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

    public Observable<List<QuestionEntity>> getAllQuestions(String pStage, int pLimit) {
        String query = "Select * from " +
                TABLE_QUESTION.TABLE_NAME;

        query += " where coalesce(" + TABLE_QUESTION.COLUMN_PARENT_ID + ", '') = '' or " +
                TABLE_QUESTION.COLUMN_PARENT_ID + " = 0 ";
        if (pStage != null) {
            query += " and " +TABLE_QUESTION.COLUMN_STAGE + " = '" + pStage + "'";
        }
        query += " order by " + TABLE_QUESTION.COLUMN_WEIGHT +
                " ASC LIMIT " + pLimit;
        Logger.d("DatabaseDao_getAllQuestions", "question query: " + query);
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

    public Observable<CountryEntity> getCountryById(Long pId) {
        String query = "Select * from " +
                TABLE_COUNTRY.TABLE_NAME +
                " where " + TABLE_COUNTRY.COLUMN_ID + " = " + pId +
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

    public Observable<List<CountryEntity>> getAllCountries(int pLimit) {
        String query = "Select * from " +
                TABLE_COUNTRY.TABLE_NAME +
                " order by " + TABLE_COUNTRY.COlUMN_UPDATED_AT +
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

    public Observable<List<String>> getTags() {
        String query = "Select " + TABLE_TAGS.COLUMN_TAG + " from " + TABLE_TAGS.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<String>> callable = new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> tagsList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    tagsList.add(cursor.getString(0));
                }
                cursor.close();
                return tagsList;
            }
        };
        return makeObservable(callable);
    }

    public Observable<List<AnswerEntity>> getAllAnswers(int pLimit) {
        String query = "Select * from " +
                TABLE_ANSWER.TABLE_NAME +
                " order by " + TABLE_ANSWER.COLUMN_UPDATED_AT +
                " DESC LIMIT " + pLimit;
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<AnswerEntity>> c = new Callable<List<AnswerEntity>>() {
            @Override
            public List<AnswerEntity> call() throws Exception {
                List<AnswerEntity> answers = new ArrayList<>();
                while (cursor.moveToNext()) {
                    answers.add(mapCursorToAnswerEntity(cursor));
                }
                cursor.close();
                return answers;
            }
        };
        return makeObservable(c);
    }

    public Observable<List<AnswerEntity>> getAnswersByQuestion(Long pQuestionId, int pLimit) {
        String query = "Select *" +
                " from " + TABLE_ANSWER.TABLE_NAME +
                " where " + TABLE_ANSWER.COLUMN_QUESTION_ID + " = " +
                pQuestionId + " order by " + TABLE_ANSWER.COLUMN_UPDATED_AT +
                " DESC LIMIT " + pLimit;
        Cursor cursor = db.rawQuery(query, null);
        Callable<List<AnswerEntity>> c = new Callable<List<AnswerEntity>>() {
            @Override
            public List<AnswerEntity> call() throws Exception {
                List<AnswerEntity> posts = new ArrayList<>();
                while (cursor.moveToNext()) {
                    posts.add(mapCursorToAnswerEntity(cursor));
                }
                cursor.close();
                return posts;
            }
        };
        return makeObservable(c);
    }

    public Observable<List<CountryUpdateEntity>> getAllCountryUpdates(int pLimit) {
        String query = "Select * from " +
                TABLE_COUNTRY_UPDATE.TABLE_NAME +
                " order by " + TABLE_COUNTRY_UPDATE.COLUMN_UPDATED_AT +
                " DESC LIMIT " + pLimit;
        Cursor cursor = db.rawQuery(query, null);

        Callable<List<CountryUpdateEntity>> c = new Callable<List<CountryUpdateEntity>>() {
            @Override
            public List<CountryUpdateEntity> call() throws Exception {
                List<CountryUpdateEntity> updates = new ArrayList<>();
                while (cursor.moveToNext()) {
                    updates.add(mapCursorToCountryUpdateEntity(cursor));
                }
                cursor.close();
                return updates;
            }
        };
        return makeObservable(c);
    }

    public Observable<List<CountryUpdateEntity>> getUpdatesByCountry(Long pCountryId, int pLimit) {
        String query = "Select *" +
                " from " + TABLE_COUNTRY_UPDATE.TABLE_NAME +
                " where " + TABLE_COUNTRY_UPDATE.COLUMN_COUNTRY_ID + " = " +
                pCountryId + " order by " + TABLE_COUNTRY_UPDATE.COLUMN_UPDATED_AT +
                " DESC LIMIT " + pLimit;
        Cursor cursor = db.rawQuery(query, null);
        Callable<List<CountryUpdateEntity>> c = new Callable<List<CountryUpdateEntity>>() {
            @Override
            public List<CountryUpdateEntity> call() throws Exception {
                List<CountryUpdateEntity> posts = new ArrayList<>();
                while (cursor.moveToNext()) {
                    posts.add(mapCursorToCountryUpdateEntity(cursor));
                }
                cursor.close();
                return posts;
            }
        };
        return makeObservable(c);
    }

    /*======= Save Operations ==========*/

    public void insertUpdate(LatestContentEntity pLatestContent) {
        saveAllCountries(pLatestContent.getCountries());
        saveAllQuestions(pLatestContent.getQuestions());
        saveAllPosts(pLatestContent.getPosts());
        saveAllAnswers(pLatestContent.getAnswers());
        saveAllUpdates(pLatestContent.getUpdates());
    }

    public void saveAllPosts(List<PostEntity> posts) {
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
                TABLE_POST.TABLE_NAME +
                " where " + TABLE_POST.COLUMN_ID + " = " + pPost.getId();

        cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        long count = cursor.getLong(0);
        cursor.close();
        saveTags(pPost.getTags());
        if (count > 0) {
            return updateOnePost(pPost);
        } else {
            return saveOnePost(pPost);
        }
    }

    public long saveOnePost(PostEntity post) {
        long insertId = db.insert(TABLE_POST.TABLE_NAME, null, getContentValues(post));
        savePostQuestionRelation(post.getId(), post.getQuestionIdList());
        savePostCountryRelation(post.getId(), post.getCountryIdList());
        savePostAnswerRelation(post.getId(), post.getAnswerIdList());
        return insertId;
    }

    public long updateOnePost(PostEntity post) {
        long insertId = db.update(TABLE_POST.TABLE_NAME, getContentValues(post), TABLE_POST
                .COLUMN_ID + " = " + post.getId(), null);
        return insertId;
    }

    public long updateDownloadStatus(Long pReference, boolean pDownloadStatus) {
        ContentValues values = new ContentValues();
        values.put(TABLE_POST.COLUMN_DOWNLOAD_STATUS, pDownloadStatus ? 1 : 0);
        values.put(TABLE_POST.COLUMN_DOWNLOAD_REF, "");
        return db.update(TABLE_POST.TABLE_NAME, values, TABLE_POST.COLUMN_DOWNLOAD_REF + " = '" +
                pReference + "'", null);
    }

    public long setDownloadReference(Long pId, long pReference) {
        ContentValues values = new ContentValues();
        values.put(TABLE_POST.COLUMN_DOWNLOAD_REF, String.valueOf(pReference));
        return db.update(TABLE_POST.TABLE_NAME, values, TABLE_POST.COLUMN_ID + " = " + pId, null);
    }

    public void saveAllAnswers(List<AnswerEntity> answers) {
        if (answers != null) {
            for (AnswerEntity answer : answers) {
                if (answer != null)
                    insertUpdateAnswer(answer);
            }
        }
    }

    public long insertUpdateAnswer(AnswerEntity pAnswer) {
        Cursor cursor;
        String query = "Select count(*) as count from " + TABLE_ANSWER.TABLE_NAME +
                " where " + TABLE_ANSWER.COLUMN_ID + " = " + pAnswer.getId();

        cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        long count = cursor.getLong(0);
        cursor.close();

        if (count > 0) {
            return updateOneAnswer(pAnswer);
        } else {
            return saveOneAnswer(pAnswer);
        }
    }

    public long saveOneAnswer(AnswerEntity pAnswer) {
        long insertId = db.insert(TABLE_ANSWER.TABLE_NAME, null, getContentValues(pAnswer));
        return insertId;
    }

    public long updateOneAnswer(AnswerEntity pAnswer) {
        long insertId = db.update(TABLE_ANSWER.TABLE_NAME, getContentValues
                (pAnswer), TABLE_ANSWER.COLUMN_ID + " = " + pAnswer.getId(), null);
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

    public void savePostCountryRelation(Long postId, List<Long> countryIds) {
        if (countryIds != null) {
            for (Long id : countryIds) {
                if (id != null) {
                    ContentValues values = new ContentValues();
                    values.put(TABLE_POST_COUNTRY.COLUMN_POST_ID, postId);
                    values.put(TABLE_POST_COUNTRY.COLUMN_COUNTRY_ID, id);
                    db.insert(TABLE_POST_COUNTRY.TABLE_NAME, null, values);
                }
            }
        }
    }

    public void savePostAnswerRelation(Long postId, List<Long> answerIds) {
        if (answerIds != null) {
            for (Long id : answerIds) {
                if (id != null) {
                    ContentValues values = new ContentValues();
                    values.put(TABLE_POST_ANSWER.COLUMN_POST_ID, postId);
                    values.put(TABLE_POST_ANSWER.COLUMN_ANSWER_ID, id);
                    db.insert(TABLE_POST_ANSWER.TABLE_NAME, null, values);
                }
            }
        }
    }

    public void saveTags(List<String> pTagList) {

        if (pTagList != null) {
            for (String tag : pTagList) {
                if (tag != null) {
                    String query = "Select count(*) as count from " + TABLE_TAGS.TABLE_NAME +
                            " where " + TABLE_TAGS.COLUMN_TAG + " = '" + tag + "'";

                    Cursor cursor = db.rawQuery(query, null);
                    cursor.moveToFirst();
                    Integer count = cursor.getInt(0);
                    if (count == 0) {
                        ContentValues values = new ContentValues();
                        values.put(TABLE_TAGS.COLUMN_TAG, tag);
                        db.insert(TABLE_TAGS.TABLE_NAME, null, values);
                    }
                }
            }
        }
    }

    public void saveAllQuestions(List<QuestionEntity> questions) {

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
                TABLE_QUESTION.TABLE_NAME + " where " + MyConstants.DATABASE
                .TABLE_QUESTION.COLUMN_ID + " = " + pQuestion.getId();

        cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        long count = cursor.getLong(0);
        cursor.close();
        saveTags(pQuestion.getTags());
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
        String query = "Select count(*) as count from " + TABLE_COUNTRY.TABLE_NAME + " where " +
                TABLE_COUNTRY.COLUMN_ID + " = " + pCountry.getId();

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

    public void saveAllUpdates(List<CountryUpdateEntity> pUpdates) {
        if (pUpdates != null) {
            for (CountryUpdateEntity update : pUpdates) {
                if (update != null)
                    insertUpdateCountryUpdate(update);
            }
        }
    }

    public long insertUpdateCountryUpdate(CountryUpdateEntity pUpdateEntity) {
        Cursor cursor;
        String query = "Select count(*) as count from " + TABLE_COUNTRY_UPDATE.TABLE_NAME +
                " where " + TABLE_COUNTRY_UPDATE.COLUMN_ID + " = " + pUpdateEntity.getId();

        cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        long count = cursor.getLong(0);
        cursor.close();

        if (count > 0) {
            return updateOneCountryUpdate(pUpdateEntity);
        } else {
            return saveOneCountryUpdate(pUpdateEntity);
        }
    }

    public long saveOneCountryUpdate(CountryUpdateEntity pUpdate) {
        return db.insert(TABLE_COUNTRY_UPDATE.TABLE_NAME, null, getContentValues(pUpdate));
    }

    public long updateOneCountryUpdate(CountryUpdateEntity pUpdate) {
        long insertId = db.update(TABLE_COUNTRY_UPDATE.TABLE_NAME, getContentValues
                (pUpdate), TABLE_COUNTRY_UPDATE.COLUMN_ID + " = " + pUpdate.getId(), null);
        return insertId;
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
        values.put(TABLE_POST.COLUMN_STAGE, gson.toJson(pPost.getStage()));
        values.put(TABLE_POST.COLUMN_DOWNLOAD_STATUS, 0);
        return values;
    }

    private ContentValues getContentValues(QuestionEntity pQuestion) {
        ContentValues values = new ContentValues();
        values.put(TABLE_QUESTION.COLUMN_ID, pQuestion.getId());
        values.put(TABLE_QUESTION.COlUMN_UPDATED_AT, pQuestion.getUpdatedAt());
        values.put(TABLE_QUESTION.COLUMN_CREATED_AT, pQuestion.getCreatedAt());
        values.put(TABLE_QUESTION.COLUMN_PARENT_ID, pQuestion.getParentId());
        values.put(TABLE_QUESTION.COLUMN_TAGS, new Gson().toJson(pQuestion.getTags()));
        values.put(TABLE_QUESTION.COLUMN_LANGUAGE, pQuestion.getLanguage());
        values.put(TABLE_QUESTION.COLUMN_STAGE, pQuestion.getStage());
        values.put(TABLE_QUESTION.COLUMN_TITLE, pQuestion.getTitle());
        values.put(TABLE_QUESTION.COLUMN_ANSWER, pQuestion.getAnswer());
        values.put(TABLE_QUESTION.COLUMN_WEIGHT, pQuestion.getWeight());
        return values;
    }

    private ContentValues getContentValues(AnswerEntity pAnswer) {
        ContentValues values = new ContentValues();
        values.put(TABLE_ANSWER.COLUMN_ID, pAnswer.getId());
        values.put(TABLE_ANSWER.COLUMN_UPDATED_AT, pAnswer.getUpdatedAt());
        values.put(TABLE_ANSWER.COLUMN_CREATED_AT, pAnswer.getCreatedAt());
        values.put(TABLE_ANSWER.COLUMN_TITLE, pAnswer.getTitle());
        values.put(TABLE_ANSWER.COLUMN_QUESTION_ID, pAnswer.getQuestionId());
        return values;
    }

    private ContentValues getContentValues(CountryUpdateEntity pUpdate) {
        ContentValues values = new ContentValues();
        values.put(TABLE_COUNTRY_UPDATE.COLUMN_ID, pUpdate.getId());
        values.put(TABLE_COUNTRY_UPDATE.COLUMN_UPDATED_AT, pUpdate.getUpdatedAt());
        values.put(TABLE_COUNTRY_UPDATE.COLUMN_CREATED_AT, pUpdate.getCreatedAt());
        values.put(TABLE_COUNTRY_UPDATE.COLUMN_TITLE, pUpdate.getTitle());
        values.put(TABLE_COUNTRY_UPDATE.COLUMN_DESCRIPTION, pUpdate.getDescription());
        values.put(TABLE_COUNTRY_UPDATE.COLUMN_COUNTRY_ID, pUpdate.getCountryId());
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
        values.put(TABLE_COUNTRY.COLUMN_CONTACTS, pCountry.getContacts());
        values.put(TABLE_COUNTRY.COLUMN_DOS_DONTS, pCountry.getDosAndDonts());
        return values;
    }

    //delete

    public void deleteContents(DeletedContentDataEntity pContent) {
        String ids = "";
        for (DeletedContentEntity pPosts : pContent.getPosts()) {
            ids += pPosts.getId().toString() + ", ";
        }
        delete(TABLE_POST.TABLE_NAME, TABLE_POST.COLUMN_ID, ids);

        ids = "";
        for (DeletedContentEntity answer : pContent.getAnswers()) {
            ids += answer.getId().toString() + ", ";
        }
        delete(TABLE_ANSWER.TABLE_NAME, TABLE_ANSWER.COLUMN_ID, ids);

        ids = "";
        for (DeletedContentEntity question : pContent.getQuestions()) {
            ids += question.getId().toString() + ", ";
        }
        delete(TABLE_QUESTION.TABLE_NAME, TABLE_QUESTION.COLUMN_ID, ids);

    }

    private void delete(String pTableName, String pIdString, String pIds) {
        db.delete(pTableName, pIdString + " IN (" + pIds + "-1)", null);
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
