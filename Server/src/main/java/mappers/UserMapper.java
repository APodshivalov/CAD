package mappers;

import domain.CadUser;
import model.User;

/**
 * Created by podsh on 07.05.2017.
 */
public class UserMapper {
    public static void map(CadUser serverUser, User facadeUser) {
        facadeUser.setId(serverUser.getId());
        facadeUser.setFirstName(serverUser.getFirstName());
        facadeUser.setLastName(serverUser.getLastName());
        facadeUser.setSessionId(serverUser.getSessionId());
    }
}
