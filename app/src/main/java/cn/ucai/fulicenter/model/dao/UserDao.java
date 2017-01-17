package cn.ucai.fulicenter.model.dao;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.User;

/**
 * Created by Administrator on 2017/1/17.
 */

public class UserDao {
    public static String USER_TABLE_NAME = "t_filicenter_user";
    public static String USER_COLUMN_NAME = "m_user_name";
    public static String USER_COLUMN_NICK = "m_user_nick";
    public static String USER_COLUMN_AVATAR_ID = "m_user_avatar_id";
    public static String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
    public static String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static String USER_COLUMN_AVATAR_UPDATE_TIME = "m_user_avatar_update_time";

    public static UserDao instance;
    public static UserDao getInstance(){
        if(instance==null){
            instance=new UserDao();
        }
        return instance;
    }
    public UserDao(){
       DBManager.onInit(FuLiCenterApplication.getInstance());
    }
    public boolean savaUser(User user){
        return DBManager.getInstance().saveUser(user);
    }
}
