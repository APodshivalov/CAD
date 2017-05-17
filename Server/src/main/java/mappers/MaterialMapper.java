package mappers;

import domain.Material;

/**
 * Created by podsh on 08.05.2017.
 */
public class MaterialMapper {
    public static model.Material map(Material material) {
        model.Material clientMaterial = new model.Material();
        clientMaterial.setId(material.getId());
        clientMaterial.setName(material.getName());
        return clientMaterial;
    }
}
