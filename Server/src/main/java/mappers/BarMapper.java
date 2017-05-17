package mappers;

import domain.Material;
import domain.ProjectInfo;
import facade.CutFacade;
import facade.MaterialFacade;
import model.Bar;
import domain.Cut;

/**
 * Created by podsh on 08.05.2017.
 */
public class BarMapper {
    private static CutFacade cutFacade = new CutFacade();
    private static MaterialFacade materialFacade = new MaterialFacade();

    public static domain.Bar map(Bar bar, ProjectInfo projectInfoServer) {
        domain.Bar serverBar = new domain.Bar();
        serverBar.setCutId(bar.getCut().getId());
        serverBar.setMaterialId(bar.getMaterial().getId());
        serverBar.setProjectInfo(projectInfoServer);
        serverBar.setFirstPoint(PointMapper.map(bar.getFirstPoint()));
        serverBar.setSecondPoint(PointMapper.map(bar.getSecondPoint()));
        serverBar.setId(bar.getId());
        return serverBar;
    }

    public static Bar map(domain.Bar bar) {
        Bar clientBar = new Bar();
        clientBar.setId(bar.getId());

        Cut serverCut = cutFacade.getById(bar.getCutId());
        clientBar.setCut(CutMapper.map(serverCut));

        Material serverMaterial = materialFacade.getById(bar.getMaterialId());
        clientBar.setMaterial(MaterialMapper.map(serverMaterial));

        clientBar.setFirstPoint(PointMapper.map(bar.getFirstPoint()));
        clientBar.setSecondPoint(PointMapper.map(bar.getSecondPoint()));

        return clientBar;
    }
}
