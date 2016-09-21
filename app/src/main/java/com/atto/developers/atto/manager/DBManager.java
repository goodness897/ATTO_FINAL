package com.atto.developers.atto.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.atto.developers.atto.MyApplication;
import com.atto.developers.atto.networkdata.chatdata.ChatContract;
import com.atto.developers.atto.networkdata.dbdata.CategoryKeywordData;
import com.atto.developers.atto.networkdata.userdata.MyProfileData;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class DBManager extends SQLiteOpenHelper {
    private static DBManager instance;

    private static final String CATEGORY_TABLE_CREATE = "create table category (_id integer primary key autoincrement,"+ "name text not null);";
    private static final String KEYWORD_TABLE_CREATE="create table keyword (_id integer primary key autoincrement,"+ "name text not null);";
    private static final String DATABASE_NAME = "category_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBManager";

    public static DBManager getInstance(){
        if(instance ==null){
            instance = new DBManager();
        }
        return instance;
    }


    private DBManager() {
        super(MyApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + ChatContract.ChatUser.TABLE + "(" +
                ChatContract.ChatUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatUser.COLUMN_SERVER_ID + " INTEGER," +
                ChatContract.ChatUser.COLUMN_NAME + " TEXT," +
                ChatContract.ChatUser.COLUMN_EMAIL + " TEXT," +
                ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID + " INTEGER);";
        db.execSQL(sql);

        sql = "CREATE TABLE " + ChatContract.ChatMessage.TABLE + "(" +
                ChatContract.ChatMessage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatMessage.COLUMN_USER_ID + " INTEGER," +
                ChatContract.ChatMessage.COLUMN_TYPE + " INTEGER," +
                ChatContract.ChatMessage.COLUMN_MESSAGE + " TEXT," +
                ChatContract.ChatMessage.COLUMN_CREATED + " INTEGER);";
        db.execSQL(sql);

        db.execSQL(CATEGORY_TABLE_CREATE);
        db.execSQL(KEYWORD_TABLE_CREATE);


    }


    public long getCategoryId(long category_Id){
        String selection = "CREATE TABLE"+ CategoryKeywordData.Cateory._ID+"=?";
        String [] args = {""+category_Id};
        String [] columns = {CategoryKeywordData.Cateory._ID};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(CategoryKeywordData.Cateory.TABLE,columns,selection,args,null,null,null);

        return 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        Log.w(TAG, "Upgrading database from version " + old_version + " to " + new_version
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS keyword");
        onCreate(db);

    }

    public long getUserId(long serverId) {
        String selection = ChatContract.ChatUser.COLUMN_SERVER_ID + " = ?";
        String[] args = {""+serverId};
        String[] columns = {ChatContract.ChatUser._ID};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(ChatContract.ChatUser.TABLE, columns, selection, args, null, null, null);
        try {
            if (c.moveToNext()) {
                long id = c.getLong(c.getColumnIndex(ChatContract.ChatUser._ID));
                return id;
            }
        } finally {
            c.close();
        }
        return -1;
    }


    public long getLastReceiveDate() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {ChatContract.ChatMessage.COLUMN_CREATED};
        String selection = ChatContract.ChatMessage.COLUMN_TYPE + " = ?";
        String[] args = {"" + ChatContract.ChatMessage.TYPE_RECEIVE};
        String orderBy = ChatContract.ChatMessage.COLUMN_CREATED + " DESC";
        String limit = "1";
        Cursor c = db.query(ChatContract.ChatMessage.TABLE, columns, selection, args, null, null, orderBy, limit);
        try {
            if (c.moveToNext()) {
                long time = c.getLong(c.getColumnIndex(ChatContract.ChatMessage.COLUMN_CREATED));
                return time;
            }
        } finally {
            c.close();
        }
        return 0;
    }

    ContentValues values = new ContentValues();
    public long addUser(MyProfileData user) {
        if (getUserId(user.getMember_id()) == -1) {
            SQLiteDatabase db = getWritableDatabase();
            values.clear();
            values.put(ChatContract.ChatUser.COLUMN_SERVER_ID, user.getMember_id());
            values.put(ChatContract.ChatUser.COLUMN_NAME, user.getMember_alias());
            values.put(ChatContract.ChatUser.COLUMN_EMAIL, "consumer");
            return db.insert(ChatContract.ChatUser.TABLE, null, values);
        }
        throw new IllegalArgumentException("aleady user added");
    }

    Map<Long, Long> resolveUserId = new HashMap<>();
    public long addMessage(MyProfileData user, int type, String message) {
        return addMessage(user, type, message, new Date());
    }
    public long addMessage(MyProfileData user, int type, String message, Date date) {
        Long uid = resolveUserId.get(user.getMember_id());
        if (uid == null) {
            long id = getUserId(user.getMember_id());
            if (id == -1) {
                id = addUser(user);
            }
            resolveUserId.put(user.getMember_id(), id);
            uid = id;
        }

        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(ChatContract.ChatMessage.COLUMN_USER_ID, (long)uid);
        values.put(ChatContract.ChatMessage.COLUMN_TYPE, type);
        values.put(ChatContract.ChatMessage.COLUMN_MESSAGE, message);
        long current = date.getTime();
        values.put(ChatContract.ChatMessage.COLUMN_CREATED, current);
        try {
            db.beginTransaction();
            long mid = db.insert(ChatContract.ChatMessage.TABLE, null, values);

            values.clear();
            values.put(ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID, mid);
            String selection = ChatContract.ChatUser._ID + " = ?";
            String[] args = {"" + uid};
            db.update(ChatContract.ChatUser.TABLE, values, selection, args);
            db.setTransactionSuccessful();
            return mid;
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getChatUser() {
        String table = ChatContract.ChatUser.TABLE + " INNER JOIN " +
                ChatContract.ChatMessage.TABLE + " ON " +
                ChatContract.ChatUser.TABLE + "." + ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID + " = " +
                ChatContract.ChatMessage.TABLE + "." + ChatContract.ChatMessage._ID;
        String[] columns = {ChatContract.ChatUser.TABLE + "." + ChatContract.ChatUser._ID,
                ChatContract.ChatUser.COLUMN_SERVER_ID,
                ChatContract.ChatUser.COLUMN_EMAIL,
                ChatContract.ChatUser.COLUMN_NAME,
                ChatContract.ChatMessage.COLUMN_MESSAGE};
        String sort = ChatContract.ChatUser.COLUMN_NAME + " COLLATE LOCALIZED ASC";
        SQLiteDatabase db = getReadableDatabase();
        return db.query(table, columns, null, null, null, null, sort);
    }

    public Cursor getChatMessage(MyProfileData user) {
        long userid = -1;
        Long uid = resolveUserId.get(user.getMember_id());
        Log.d("userid",String.valueOf(user.getMember_id()));
        if (uid == null) {
            long id = getUserId(user.getMember_id());
            if (id != -1) {
                resolveUserId.put(user.getMember_id(), id);
                userid = id;
            }
        } else {
            userid = uid;
        }

        String[] columns = {ChatContract.ChatMessage._ID,
                ChatContract.ChatMessage.COLUMN_TYPE,
                ChatContract.ChatMessage.COLUMN_MESSAGE,
                ChatContract.ChatMessage.COLUMN_CREATED};
        String selection = ChatContract.ChatMessage.COLUMN_USER_ID + " = ?";
        String[] args = {"" + userid};
        String sort = ChatContract.ChatMessage.COLUMN_CREATED + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        return db.query(ChatContract.ChatMessage.TABLE, columns, selection, args, null, null, sort);
    }

}

