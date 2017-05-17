package dao;

import domain.CadUser;
import model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by podsh on 06.05.2017.
 */
public class CadUserDao {
    public CadUser findCadUserByLoginAndPassword(String login, String password) {
        Session session = SessionHelper.startTransaction();
        Query<CadUser> query = session.createQuery("from CadUser where login = :login and password = :password", CadUser.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        List<CadUser> list = query.list();
        SessionHelper.endTransaction();

        if (list.size() != 1){
            return null;
        } else {
            return list.get(0);
        }
    }

    public void update(CadUser user) {
        Session session = SessionHelper.startTransaction();
        session.update(user);
        SessionHelper.endTransaction();
    }

    public CadUser getCadUserBySessionId(String sessionId) {
        Session session = SessionHelper.startTransaction();
        Query<CadUser> query = session.createQuery("from CadUser where sessionid = :sessionId", CadUser.class);
        query.setParameter("sessionId", sessionId);
        List<CadUser> list = query.list();
        SessionHelper.endTransaction();

        if (list.size() != 1){
            return null;
        } else {
            return list.get(0);
        }
    }
}
