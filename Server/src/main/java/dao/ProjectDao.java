package dao;

import domain.Bar;
import domain.CadUser;
import domain.Point;
import facade.BarFacade;
import facade.PointFacade;
import mappers.ProjectInfoMapper;
import model.ArrayOfProjectInfo;
import model.ProjectInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by podsh on 08.05.2017.
 */
public class ProjectDao {
    private PointFacade pointFacade;
    private BarFacade barFacade;

    public ProjectDao() {
        barFacade = new BarFacade();
        pointFacade = new PointFacade();
    }

    public ProjectInfo createProject(ProjectInfo projectInfo, CadUser user) {
        Session session = SessionHelper.startTransaction();
        domain.ProjectInfo serverProjectInfo = ProjectInfoMapper.map(projectInfo, user);
        session.save(serverProjectInfo);
        SessionHelper.endTransaction();
        return ProjectInfoMapper.map(serverProjectInfo);
    }

    public domain.ProjectInfo getProjectInfoById(String id) {
        Session session = SessionHelper.startTransaction();
        domain.ProjectInfo info = session.get(domain.ProjectInfo.class, id);
        SessionHelper.endTransaction();
        return info;
    }

    public void save(Bar bar) {
        pointFacade.save(bar.getFirstPoint());
        pointFacade.save(bar.getSecondPoint());
        barFacade.save(bar);
    }

    public List<domain.ProjectInfo> loadProjectInfoByUser(CadUser user) {
        Session session = SessionHelper.startTransaction();
        Query<domain.ProjectInfo> query = session.createQuery("from ProjectInfo as pi where pi.user = :user", domain.ProjectInfo.class);
        query.setParameter("user", user);
        List<domain.ProjectInfo> list = query.list();
        SessionHelper.endTransaction();
        return list;
    }

    public List<Bar> loadProjectById(String id) {
        Session session = SessionHelper.startTransaction();
        Query<Bar> query = session.createQuery("from Bar where projectinfo_id = :projectId", Bar.class);
        query.setParameter("projectId", id);
        List<Bar> list = query.list();
        SessionHelper.endTransaction();
        return list;
    }
}
