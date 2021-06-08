package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager em;


    public List<Role> getRoles() {
        String hql = "FROM Role";
        Query query = em.createQuery(hql, Role.class);
        return query.getResultList();
    }
}
