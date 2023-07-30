package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private EntityManager entityManager;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(" +
                    "id int not null auto_increment," +
                    "name VARCHAR(50)," +
                    "lastName VARCHAR(50)," +
                    "age int," + "PRIMARY KEY(id))").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().getCurrentSession()) {
            User user;
            transaction = session.beginTransaction();
            user = (User) session.load(User.class,id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = getSessionFactory().getCurrentSession()) {
            entityManager = getSessionFactory().createEntityManager();
            TypedQuery<User> query = entityManager.createQuery("FROM User ", User.class);
            list = query.getResultList();
            entityManager.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
