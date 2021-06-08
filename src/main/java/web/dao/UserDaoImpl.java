package web.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        em.persist(user);
    }

    @Override
    public void update(User user) {
        String oldPass = show(user.getId()).getPassword();
        String newPass = user.getPassword();
        if(!Objects.equals(oldPass, bCryptPasswordEncoder.encode(newPass))){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        em.merge(user);
    }

    @Override
    public List<User> getListUsers() {
        String hql = "FROM User";
        Query query = em.createQuery(hql, User.class);
        return query.getResultList();
    }



    @Override
    public void remove(long id) {
        em.remove(show(id));
    }

    @Override
    public User show(long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username){
        String hql = "FROM User where first_name=:username";
        Query query = em.createQuery(hql, User.class)
                .setParameter("username", username);
        return (User) query.getResultList().get(0);
    }
}
