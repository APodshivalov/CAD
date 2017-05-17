package mappers;

import domain.CadUser;
import model.ProjectInfo;

import java.util.UUID;

/**
 * Created by podsh on 08.05.2017.
 */
public class ProjectInfoMapper {
    public static domain.ProjectInfo map(ProjectInfo projectInfo, CadUser user) {
        domain.ProjectInfo projectInfoServer = new domain.ProjectInfo();
        projectInfoServer.setId(UUID.randomUUID().toString());
        projectInfoServer.setName(projectInfo.getName());
        projectInfoServer.setUser(user);
        return projectInfoServer;
    }

    public static ProjectInfo map(domain.ProjectInfo serverProjectInfo) {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setId(serverProjectInfo.getId());
        projectInfo.setName(serverProjectInfo.getName());
        return projectInfo;
    }
}
