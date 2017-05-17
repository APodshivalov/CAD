package facade;

import dao.MaterialDao;
import domain.Material;
import mappers.MaterialMapper;
import model.ArrayOfMaterial;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by podsh on 08.05.2017.
 */
public class MaterialFacade {
    private MaterialDao dao;

    public MaterialFacade() {
        dao = new MaterialDao();
    }
    public ArrayOfMaterial getMaterials(String type) {
        List<Material> materials = dao.getMaterialsByType(type);
        ArrayOfMaterial arrayOfMaterial = new ArrayOfMaterial();
        List<model.Material> facadeMaterials = materials.stream().map(MaterialMapper::map).collect(Collectors.toList());
        arrayOfMaterial.setItem(facadeMaterials);
        return arrayOfMaterial;
    }

    public Material getById(String id) {
        return dao.loadMaterialById(id);
    }
}
