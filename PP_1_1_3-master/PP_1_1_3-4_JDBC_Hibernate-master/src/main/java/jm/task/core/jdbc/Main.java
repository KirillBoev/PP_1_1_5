package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();

        userDao.saveUser("Max", "Jam", (byte) 16);
        userDao.saveUser("Bob", "Tiger", (byte) 32);
        userDao.saveUser("Lix", "Pix", (byte) 45);
        userDao.saveUser("Nees", "Reas", (byte) 8);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
