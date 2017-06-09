package dao;

import domain.Material;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by podsh on 08.05.2017.
 */
public class MaterialDao {
    public List<Material> getMaterialsByType(String type) {
        Session session = SessionHelper.startTransaction();
        Query<Material> query = session.createQuery("from Material where type = :type", Material.class);
        query.setParameter("type", type);
        List<Material> list = query.list();
        SessionHelper.endTransaction();

        return list;
    }

    public Material loadMaterialById(String id) {
        Session session = SessionHelper.startTransaction();
        Material material = session.get(Material.class, id);
        SessionHelper.endTransaction();

        return material;
    }

    public void save(Material material) {
        Session session = SessionHelper.startTransaction();
        session.save(material);
        SessionHelper.endTransaction();
    }
}
