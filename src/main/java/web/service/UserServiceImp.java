package web.service;

import web.dao.UserDao;
import web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;
   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }
   @Transactional
   @Override
   public User getUser(Long id) {
      return userDao.getUser(id);
   }

   @Transactional
   @Override
   public void update(Long id, User updateUser) {
      User userToBeUpdated = getUser((Long) id);
      userToBeUpdated.setFirstName(updateUser.getFirstName());
      userToBeUpdated.setLastName(updateUser.getLastName());
      userToBeUpdated.setEmail(updateUser.getEmail());

   }


}
