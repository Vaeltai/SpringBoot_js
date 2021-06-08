package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.List;

@Repository
public interface RoleDao {

    List<Role> getRoles();
}
