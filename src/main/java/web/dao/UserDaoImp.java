package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
   @PersistenceContext
   private EntityManager entityManager;

   public void add(User user) {
      entityManager.persist(user);
   }

   public User getUser(Long id) {
      return entityManager.find(User.class, id);
   }
   public List<User> listUsers() {
      return (List<User>) entityManager.createQuery("select w from users w").getResultList();
   }
   public void update(Long id, User updateUser) {
      User userToBeUpdated = getUser((Long) id);
      userToBeUpdated.setFirstName(updateUser.getFirstName());
      userToBeUpdated.setLastName(updateUser.getLastName());
      userToBeUpdated.setEmail(updateUser.getEmail());

   }


//   @Override
//   public void add(User user) {
//      sessionFactory.getCurrentSession().save(user);
//   }
//
//   @Override
//   @SuppressWarnings("unchecked")
//   public List<User> listUsers() {
//      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
//      return query.getResultList();
//   }
//   @Override
//   public User getUser(String model, int series) {
//      Session session = sessionFactory.openSession();
//      String hql = "FROM User user LEFT OUTER JOIN FETCH user.car WHERE user.car.series = :series and user.car.model = :model";
//      User user = (User) session.createQuery(hql).setParameter("series", series).setParameter("model", model).uniqueResult();
//      return user;
//   }

}
