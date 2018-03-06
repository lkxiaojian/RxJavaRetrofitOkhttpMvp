package com.wzrd.m.db.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.wzrd.m.been.EmoticonCode;
import com.wzrd.m.been.Poem;
import com.wzrd.m.been.TEXTIFORMATION;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.been.TSYSUSER;
import com.wzrd.m.been.User;
import com.wzrd.m.been.Video;
import com.wzrd.m.been.VideoContent;

import com.wzrd.m.db.gen.EmoticonCodeDao;
import com.wzrd.m.db.gen.PoemDao;
import com.wzrd.m.db.gen.TEXTIFORMATIONDao;
import com.wzrd.m.db.gen.TSYSCONTANTSDao;
import com.wzrd.m.db.gen.TSYSUSERDao;
import com.wzrd.m.db.gen.UserDao;
import com.wzrd.m.db.gen.VideoDao;
import com.wzrd.m.db.gen.VideoContentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig emoticonCodeDaoConfig;
    private final DaoConfig poemDaoConfig;
    private final DaoConfig tEXTIFORMATIONDaoConfig;
    private final DaoConfig tSYSCONTANTSDaoConfig;
    private final DaoConfig tSYSUSERDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig videoDaoConfig;
    private final DaoConfig videoContentDaoConfig;

    private final EmoticonCodeDao emoticonCodeDao;
    private final PoemDao poemDao;
    private final TEXTIFORMATIONDao tEXTIFORMATIONDao;
    private final TSYSCONTANTSDao tSYSCONTANTSDao;
    private final TSYSUSERDao tSYSUSERDao;
    private final UserDao userDao;
    private final VideoDao videoDao;
    private final VideoContentDao videoContentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        emoticonCodeDaoConfig = daoConfigMap.get(EmoticonCodeDao.class).clone();
        emoticonCodeDaoConfig.initIdentityScope(type);

        poemDaoConfig = daoConfigMap.get(PoemDao.class).clone();
        poemDaoConfig.initIdentityScope(type);

        tEXTIFORMATIONDaoConfig = daoConfigMap.get(TEXTIFORMATIONDao.class).clone();
        tEXTIFORMATIONDaoConfig.initIdentityScope(type);

        tSYSCONTANTSDaoConfig = daoConfigMap.get(TSYSCONTANTSDao.class).clone();
        tSYSCONTANTSDaoConfig.initIdentityScope(type);

        tSYSUSERDaoConfig = daoConfigMap.get(TSYSUSERDao.class).clone();
        tSYSUSERDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        videoDaoConfig = daoConfigMap.get(VideoDao.class).clone();
        videoDaoConfig.initIdentityScope(type);

        videoContentDaoConfig = daoConfigMap.get(VideoContentDao.class).clone();
        videoContentDaoConfig.initIdentityScope(type);

        emoticonCodeDao = new EmoticonCodeDao(emoticonCodeDaoConfig, this);
        poemDao = new PoemDao(poemDaoConfig, this);
        tEXTIFORMATIONDao = new TEXTIFORMATIONDao(tEXTIFORMATIONDaoConfig, this);
        tSYSCONTANTSDao = new TSYSCONTANTSDao(tSYSCONTANTSDaoConfig, this);
        tSYSUSERDao = new TSYSUSERDao(tSYSUSERDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        videoDao = new VideoDao(videoDaoConfig, this);
        videoContentDao = new VideoContentDao(videoContentDaoConfig, this);

        registerDao(EmoticonCode.class, emoticonCodeDao);
        registerDao(Poem.class, poemDao);
        registerDao(TEXTIFORMATION.class, tEXTIFORMATIONDao);
        registerDao(TSYSCONTANTS.class, tSYSCONTANTSDao);
        registerDao(TSYSUSER.class, tSYSUSERDao);
        registerDao(User.class, userDao);
        registerDao(Video.class, videoDao);
        registerDao(VideoContent.class, videoContentDao);
    }
    
    public void clear() {
        emoticonCodeDaoConfig.getIdentityScope().clear();
        poemDaoConfig.getIdentityScope().clear();
        tEXTIFORMATIONDaoConfig.getIdentityScope().clear();
        tSYSCONTANTSDaoConfig.getIdentityScope().clear();
        tSYSUSERDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
        videoDaoConfig.getIdentityScope().clear();
        videoContentDaoConfig.getIdentityScope().clear();
    }

    public EmoticonCodeDao getEmoticonCodeDao() {
        return emoticonCodeDao;
    }

    public PoemDao getPoemDao() {
        return poemDao;
    }

    public TEXTIFORMATIONDao getTEXTIFORMATIONDao() {
        return tEXTIFORMATIONDao;
    }

    public TSYSCONTANTSDao getTSYSCONTANTSDao() {
        return tSYSCONTANTSDao;
    }

    public TSYSUSERDao getTSYSUSERDao() {
        return tSYSUSERDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public VideoDao getVideoDao() {
        return videoDao;
    }

    public VideoContentDao getVideoContentDao() {
        return videoContentDao;
    }

}
