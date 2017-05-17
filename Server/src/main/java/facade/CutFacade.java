package facade;

import dao.CutDao;
import domain.Cut;
import mappers.CutMapper;
import model.ArrayOfCut;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by podsh on 08.05.2017.
 */
public class CutFacade {
    private CutDao dao;

    public CutFacade() {
        dao = new CutDao();
    }
    public ArrayOfCut getByType(String type) {
        List<Cut> serverCuts = dao.loadCutsByType(type);
        ArrayOfCut arrayOfCut = new ArrayOfCut();
        List<model.Cut> clientCuts = serverCuts.stream().map(CutMapper::map).collect(Collectors.toList());
        arrayOfCut.setItem(clientCuts);
        return arrayOfCut;
    }

    public Cut getById(String cutId) {
        return dao.loadCutById(cutId);
    }
}
