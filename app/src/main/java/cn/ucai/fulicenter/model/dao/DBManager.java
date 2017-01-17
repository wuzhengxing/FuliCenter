package cn.ucai.fulicenter.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.User;

/**
 * Created by Administrator on 2017/1/17.
 */

public class DBManager {
    private static DBOpenHelper openHelper;
    static DBManager dbManager = new DBManager();

    public DBManager() {
    }

    public static void onInit(Context context) {
        openHelper = new DBOpenHelper(context);
    }

    public synchronized static DBManager getInstance() {
        if (openHelper == null) {
            Log.i("main", "没有调用onInit");
        }
        return dbManager;
    }

    public boolean saveUser(User user) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
            values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
            values.put(UserDao.USER_COLUMN_AVATAR_ID, user.getMavatarId());
            values.put(UserDao.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
            values.put(UserDao.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
            values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
            values.put(UserDao.USER_COLUMN_AVATAR_UPDATE_TIME, user.getMavatarLastUpdateTime());
            return db.replace(UserDao.USER_TABLE_NAME, null, values) != -1;
        }
        return false;
    }

    public User getUser(String userName) {
        User user;
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db.isOpen()) {
            String sql = "SELECT * FROM " + UserDao.USER_TABLE_NAME +
                    " WHERE " + UserDao.USER_COLUMN_NAME + "=?";
            Cursor cursor = db.rawQuery(sql, new String[]{userName});
            if (cursor.moveToNext()) {
                user = new User();
                user.setMuserName(userName);
                user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
                user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_ID)));
                user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
                user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
                user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
                user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_UPDATE_TIME)));
                return user;
            }
        }
        return null;
    }
}
