package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by podsh on 08.05.2017.
 */
@Entity
@Table(name = "project_info")
public class ProjectInfo implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    @ManyToOne(targetEntity = CadUser.class)
    @JoinColumn(name = "user_id")
    private CadUser user;

    public ProjectInfo() {
    }

    public ProjectInfo(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CadUser getUser() {
        return user;
    }

    public void setUser(CadUser user) {
        this.user = user;
    }
}
