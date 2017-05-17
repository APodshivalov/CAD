package facade;

import dao.CadUserDao;
import domain.CadUser;
import mappers.UserMapper;
import model.LoginInfo;
import model.User;

import java.util.UUID;

/**
 * Created by podsh on 08.05.2017.
 */
public class UserFacade {
    private CadUserDao dao;

    public UserFacade() {
        dao = new CadUserDao();
    }

    public User validateUser(LoginInfo logInUser) {
        CadUser serverUser = dao.findCadUserByLoginAndPassword(logInUser.getLogin(), logInUser.getPassword());
        if (serverUser != null) {
            String sessionID = UUID.randomUUID().toString();
            serverUser.setSessionId(sessionID);
            dao.update(serverUser);
            User facadeUser = new User();
            UserMapper.map(serverUser, facadeUser);
            return facadeUser;
        } else {
            return null;
        }
    }

    public CadUser getCadUserBySessionId(String sessionId) {
        return dao.getCadUserBySessionId(sessionId);
    }
}
