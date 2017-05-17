package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by podsh on 06.05.2017.
 */
@XmlRootElement(name = "loginInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "login",
        "password"
})
public class LoginInfo {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
