package com.wzrd.m.db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.wzrd.m.been.TEXTIFORMATION;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEXTIFORMATION".
*/
public class TEXTIFORMATIONDao extends AbstractDao<TEXTIFORMATION, String> {

    public static final String TABLENAME = "TEXTIFORMATION";

    /**
     * Properties of entity TEXTIFORMATION.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property T_pm_id = new Property(0, String.class, "t_pm_id", true, "T_PM_ID");
        public final static Property T_pm_userid = new Property(1, String.class, "t_pm_userid", false, "T_PM_USERID");
        public final static Property T_pm_username = new Property(2, String.class, "t_pm_username", false, "T_PM_USERNAME");
        public final static Property T_pm_type = new Property(3, String.class, "t_pm_type", false, "T_PM_TYPE");
        public final static Property T_pm_imagepath = new Property(4, int.class, "t_pm_imagepath", false, "T_PM_IMAGEPATH");
        public final static Property T_pm_modify_time = new Property(5, String.class, "t_pm_modify_time", false, "T_PM_MODIFY_TIME");
        public final static Property T_pm_modify_id = new Property(6, String.class, "t_pm_modify_id", false, "T_PM_MODIFY_ID");
        public final static Property T_pm_message = new Property(7, String.class, "t_pm_message", false, "T_PM_MESSAGE");
    };


    public TEXTIFORMATIONDao(DaoConfig config) {
        super(config);
    }
    
    public TEXTIFORMATIONDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEXTIFORMATION\" (" + //
                "\"T_PM_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: t_pm_id
                "\"T_PM_USERID\" TEXT," + // 1: t_pm_userid
                "\"T_PM_USERNAME\" TEXT," + // 2: t_pm_username
                "\"T_PM_TYPE\" TEXT," + // 3: t_pm_type
                "\"T_PM_IMAGEPATH\" INTEGER NOT NULL ," + // 4: t_pm_imagepath
                "\"T_PM_MODIFY_TIME\" TEXT," + // 5: t_pm_modify_time
                "\"T_PM_MODIFY_ID\" TEXT," + // 6: t_pm_modify_id
                "\"T_PM_MESSAGE\" TEXT);"); // 7: t_pm_message
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEXTIFORMATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TEXTIFORMATION entity) {
        stmt.clearBindings();
 
        String t_pm_id = entity.getT_pm_id();
        if (t_pm_id != null) {
            stmt.bindString(1, t_pm_id);
        }
 
        String t_pm_userid = entity.getT_pm_userid();
        if (t_pm_userid != null) {
            stmt.bindString(2, t_pm_userid);
        }
 
        String t_pm_username = entity.getT_pm_username();
        if (t_pm_username != null) {
            stmt.bindString(3, t_pm_username);
        }
 
        String t_pm_type = entity.getT_pm_type();
        if (t_pm_type != null) {
            stmt.bindString(4, t_pm_type);
        }
        stmt.bindLong(5, entity.getT_pm_imagepath());
 
        String t_pm_modify_time = entity.getT_pm_modify_time();
        if (t_pm_modify_time != null) {
            stmt.bindString(6, t_pm_modify_time);
        }
 
        String t_pm_modify_id = entity.getT_pm_modify_id();
        if (t_pm_modify_id != null) {
            stmt.bindString(7, t_pm_modify_id);
        }
 
        String t_pm_message = entity.getT_pm_message();
        if (t_pm_message != null) {
            stmt.bindString(8, t_pm_message);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TEXTIFORMATION entity) {
        stmt.clearBindings();
 
        String t_pm_id = entity.getT_pm_id();
        if (t_pm_id != null) {
            stmt.bindString(1, t_pm_id);
        }
 
        String t_pm_userid = entity.getT_pm_userid();
        if (t_pm_userid != null) {
            stmt.bindString(2, t_pm_userid);
        }
 
        String t_pm_username = entity.getT_pm_username();
        if (t_pm_username != null) {
            stmt.bindString(3, t_pm_username);
        }
 
        String t_pm_type = entity.getT_pm_type();
        if (t_pm_type != null) {
            stmt.bindString(4, t_pm_type);
        }
        stmt.bindLong(5, entity.getT_pm_imagepath());
 
        String t_pm_modify_time = entity.getT_pm_modify_time();
        if (t_pm_modify_time != null) {
            stmt.bindString(6, t_pm_modify_time);
        }
 
        String t_pm_modify_id = entity.getT_pm_modify_id();
        if (t_pm_modify_id != null) {
            stmt.bindString(7, t_pm_modify_id);
        }
 
        String t_pm_message = entity.getT_pm_message();
        if (t_pm_message != null) {
            stmt.bindString(8, t_pm_message);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TEXTIFORMATION readEntity(Cursor cursor, int offset) {
        TEXTIFORMATION entity = new TEXTIFORMATION( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // t_pm_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // t_pm_userid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // t_pm_username
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // t_pm_type
            cursor.getInt(offset + 4), // t_pm_imagepath
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // t_pm_modify_time
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // t_pm_modify_id
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // t_pm_message
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TEXTIFORMATION entity, int offset) {
        entity.setT_pm_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setT_pm_userid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setT_pm_username(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setT_pm_type(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setT_pm_imagepath(cursor.getInt(offset + 4));
        entity.setT_pm_modify_time(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setT_pm_modify_id(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setT_pm_message(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TEXTIFORMATION entity, long rowId) {
        return entity.getT_pm_id();
    }
    
    @Override
    public String getKey(TEXTIFORMATION entity) {
        if(entity != null) {
            return entity.getT_pm_id();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}