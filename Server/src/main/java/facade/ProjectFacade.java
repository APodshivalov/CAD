package facade;

import dao.ProjectDao;
import domain.CadUser;
import domain.ProjectInfo;
import mappers.BarMapper;
import mappers.ProjectInfoMapper;
import model.ArrayOfProjectInfo;
import model.Bar;
import model.Project;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by podsh on 08.05.2017.
 */
public class ProjectFacade {
    private ProjectDao projectDao;

    public ProjectFacade() {
        projectDao = new ProjectDao();
    }

    public model.ProjectInfo createProject(model.ProjectInfo project, CadUser user) {
        return projectDao.createProject(project, user);
    }

    public void save(Project project) {
        ProjectInfo projectInfoServer = projectDao.getProjectInfoById(project.getProjectInfo().getId());
        if (projectInfoServer != null){
            for (Bar bar: project.getBars()){
                projectDao.save(BarMapper.map(bar, projectInfoServer));
            }
        }
    }

    public ArrayOfProjectInfo getProjectInfoByUser(CadUser user) {
        ArrayOfProjectInfo arrayOfProjectInfo = new ArrayOfProjectInfo();
        List<ProjectInfo> serverProjectInfo = projectDao.loadProjectInfoByUser(user);
        List<model.ProjectInfo> facadeProjectInfo = serverProjectInfo.stream().map(ProjectInfoMapper::map)
                .collect(Collectors.toList());
        arrayOfProjectInfo.setItem(facadeProjectInfo);
        return arrayOfProjectInfo;
    }

    public Project loadProject(model.ProjectInfo projectInfo) {
        List<domain.Bar> serverBar = projectDao.loadProjectById(projectInfo.getId());
        List<Bar> clientBars = serverBar.stream().map(BarMapper::map).collect(Collectors.toList());
        Project project = new Project();
        project.setBars(clientBars);
        project.setProjectInfo(projectInfo);
        return project;
    }
}
