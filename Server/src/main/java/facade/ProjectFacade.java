package facade;

import dao.CutDao;
import dao.MaterialDao;
import dao.ProjectDao;
import domain.CadUser;
import domain.Cut;
import helpers.Matrix;
import helpers.MatrixPrinter;
import helpers.ProjectHelper;
import mappers.BarMapper;
import mappers.ProjectInfoMapper;
import model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by podsh on 08.05.2017.
 */
public class ProjectFacade {
    private ProjectDao projectDao;
    private ProjectHelper projectHelper;

    public ProjectFacade() {
        projectDao = new ProjectDao();
        projectHelper = new ProjectHelper();
    }

    public model.ProjectInfo createProject(model.ProjectInfo project, CadUser user) {
        return projectDao.createProject(project, user);
    }

    public void save(Project project) {
        domain.ProjectInfo projectInfoServer = projectDao.getProjectInfoById(project.getProjectInfo().getId());
        if (projectInfoServer != null) {
            for (Bar bar : project.getBars()) {
                projectDao.save(BarMapper.map(bar, projectInfoServer));
            }
        }
    }

    public ArrayOfProjectInfo getProjectInfoByUser(CadUser user) {
        ArrayOfProjectInfo arrayOfProjectInfo = new ArrayOfProjectInfo();
        List<domain.ProjectInfo> serverProjectInfo = projectDao.loadProjectInfoByUser(user);
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

    public ArrayOfResult calculate(Project project) {
        //save(project);
        return getResult(project.getBars());
    }

    private ArrayOfResult getResult(List<Bar> bars) {
        long statrTime = System.currentTimeMillis();
        MatrixPrinter printer = new MatrixPrinter(System.out);
        List<domain.Material> materials = getMaterialsFromServer(bars);
        List<domain.Cut> cuts = getCutsFromServer(bars);

        List<Point> points = projectHelper.getPoints(bars);

        Matrix RG = new Matrix(points.size() * 3, points.size() * 3);
        for (Bar bar : bars) {

            // Модуль Юнга
            double E = materials.stream()
                    .filter(material -> material.getId().equals(bar.getMaterial().getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Error!!"))
                    .getE();

            Optional<Cut> currentCut = cuts.stream()
                    .filter(cut -> cut.getId().equals(bar.getCut().getId()))
                    .findFirst();

            // Площадь поперечного сечения
            double F = currentCut.orElseThrow(() -> new RuntimeException("Error!!"))
                    .getF();
            // Момент инерции сечения
            double I = currentCut.orElseThrow(() -> new RuntimeException("Error!!"))
                    .getI();

            double length = bar.getLength();

            Matrix rISh = Matrix.getLocalStiffnessMatrix(E, F, I, length);

            System.out.println();
            System.out.println(bars.indexOf(bar));
            printer.print(rISh);
            System.out.println();

            Matrix v = Matrix.getRotationMatrix(bar);

            Matrix ri = v.transponate().multiple(rISh).multiple(v);

            int first = points.indexOf(bar.getFirstPoint());
            int second = points.indexOf(bar.getSecondPoint());

            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 3; column++) {
                    RG.add(first * 3 + row, first * 3 + column, ri.get(row, column));
                    RG.add(first * 3 + row, second * 3 + column, ri.get(row, column + 3));
                    RG.add(second * 3 + row, first * 3 + column, ri.get(row + 3, column));
                    RG.add(second * 3 + row, second * 3 + column, ri.get(row + 3, column + 3));
                }
            }
        }

        double[] P = new double[points.size() * 3];

        for (int i = 0; i < points.size(); i++) {
            P[i * 3] = points.get(i).getForce().getX();
            P[i * 3 + 1] = points.get(i).getForce().getY();
            P[i * 3 + 2] = points.get(i).getForce().getM();

            Reaction reaction = points.get(i).getReaction();

            if (reaction.getName().equals("Reac1")) {
                P[i * 3] = 0;
                P[i * 3 + 1] = 0;
                P[i * 3 + 2] = 0;
                RG.setRow(i * 3, 0);
                RG.setRow(i * 3 + 1, 0);
                RG.setRow(i * 3 + 2, 0);
                RG.setColumn(i * 3, 0);
                RG.setColumn(i * 3 + 1, 0);
                RG.setColumn(i * 3 + 2, 0);
                RG.set(i * 3, i * 3, 1);
                RG.set(i * 3 + 1, i * 3 + 1, 1);
                RG.set(i * 3 + 2, i * 3 + 2, 1);
            }

            if (reaction.getName().equals("Reac2")) {
                P[i * 3] = 0;
                P[i * 3 + 1] = 0;
                RG.setRow(i * 3, 0);
                RG.setRow(i * 3 + 1, 0);
                RG.setColumn(i * 3, 0);
                RG.setColumn(i * 3 + 1, 0);
                RG.set(i * 3, i * 3, 1);
                RG.set(i * 3 + 1, i * 3 + 1, 1);
            }

            if (reaction.getName().equals("Reac3")) {
                if (reaction.getAngle() == 0 || reaction.getAngle() == 180) {
                    P[i * 3 + 1] = 0;
                    RG.setRow(i * 3 + 1, 0);
                    RG.setColumn(i * 3 + 1, 0);
                    RG.set(i * 3 + 1, i * 3 + 1, 1);
                } else {
                    P[i * 3] = 0;
                    RG.setRow(i * 3, 0);
                    RG.setColumn(i * 3, 0);
                    RG.set(i * 3, i * 3, 1);
                }
            }
        }
        System.out.println("RG size " + RG.getWidth() + "x" + RG.getHeight());

        double[] Z = RG.gaoss(P);
        ArrayOfResult arrayOfResult = new ArrayOfResult();

        for (Bar bar : bars) {
            int first = points.indexOf(bar.getFirstPoint());
            int second = points.indexOf(bar.getSecondPoint());
            // Модуль Юнга
            double E = materials.stream()
                    .filter(material -> material.getId().equals(bar.getMaterial().getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Error!!"))
                    .getE();

            Optional<Cut> currentCut = cuts.stream()
                    .filter(cut -> cut.getId().equals(bar.getCut().getId()))
                    .findFirst();

            // Площадь поперечного сечения
            double F = currentCut.orElseThrow(() -> new RuntimeException("Error!!"))
                    .getF();
            // Момент инерции сечения
            double I = currentCut.orElseThrow(() -> new RuntimeException("Error!!"))
                    .getI();

            double length = bar.getLength();

            double[] Zi = new double[6];
            Zi[0] = Z[first * 3];
            Zi[1] = Z[first * 3 + 1];
            Zi[2] = Z[first * 3 + 2];
            Zi[3] = Z[second * 3];
            Zi[4] = Z[second * 3 + 1];
            Zi[5] = Z[second * 3 + 2];

            double[] Zish = Matrix.getRotationMatrix(bar).multiple(Zi);

            System.out.println();
            System.out.println(bars.indexOf(bar));
            System.out.println();

            double[] rish = Matrix.getLocalStiffnessMatrix(E, F, I, length).multiple(Zish);
            Result result = new Result();
            result.setBarId(bar.getId());
            result.setN1(Math.round(rish[0]*100)/100.0);
            result.setQ1(Math.round(rish[1]*100)/100.0);
            result.setM1(Math.round(-rish[2]*100)/100.0);
            result.setN2(Math.round(rish[3]*100)/100.0);
            result.setQ2(Math.round(-rish[4]*100)/100.0);
            result.setM2(Math.round(rish[5]*100)/100.0);

            if (result.getN1() > 0 && result.getN2() < 0 || result.getN1() < 0 && result.getN2() > 0) {
                result.setN1(-result.getN1());
            }

            arrayOfResult.add(result);
        }
        long endTime = System.currentTimeMillis() - statrTime;
        System.out.println("Total calc time: " + endTime);
        return arrayOfResult;
    }

    private List<domain.Cut> getCutsFromServer(List<Bar> bars) {
        CutDao dao = new CutDao();
        return bars.stream().map(bar -> bar.getCut().getId()).distinct().map(dao::loadCutById).collect(Collectors.toList());
    }

    private List<domain.Material> getMaterialsFromServer(List<Bar> bars) {
        MaterialDao dao = new MaterialDao();
        return bars.stream().map(bar -> bar.getMaterial().getId()).distinct().map(dao::loadMaterialById).collect(Collectors.toList());
    }
}
