package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user, Car car) {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.save(user);
      session.save(car);
      transaction.commit();
      session.close();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUser(String model, int series) {
      Session session = sessionFactory.openSession();
      String HQL = "FROM User WHERE car.model=:model AND car.series=:series";
      Query query = session.createQuery(HQL);
      query.setParameter("model", model);
      query.setParameter("series", series);
      User user = (User) query.getSingleResult();
      return user;
   }
}
