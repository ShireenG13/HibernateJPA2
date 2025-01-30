package org.shireen.controller;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.shireen.model.User;

import java.util.List;

public class UserController {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        //addUser(factory, session);
        //findUser(factory, session, 1);
        //updateUser(session, 3);
        //deleteUser(session, 6);
        //deleteUser(session, 7);
        //deleteUser(session, 8);
        //deleteUser(session, 9);
        //deleteUser(session, 10);
        //findUserHql(factory,session);
        //getRecordById(factory, session);
        //getRecords(session);
        //getMaxSalary(session);
        //getMaxSalaryGroupBy();
        namedQueryExample(session);
    }

        public static void addUser(SessionFactory factory, Session session){
            Transaction transaction = session.beginTransaction();
            User uOne = new User();
            uOne.setEmail("laura@panna.com");
            uOne.setFullName("Laura Santana");
            uOne.setPassword("Laura123");
            uOne.setSalary(3000.30);
            uOne.setAge(30);
            uOne.setCity("Atlanta");

            User uTwo = new User();
            uTwo.setEmail("betty@panna.com");
            uTwo.setFullName("Betty Joseph");
            uTwo.setPassword("Betty123");
            uTwo.setSalary(3050.30);
            uTwo.setAge(35);
            uTwo.setCity("Dallas");

            User uThree = new User();
            uThree.setEmail("Karen@panna.com");
            uThree.setFullName("Karen Chaplin");
            uThree.setPassword("Karen123");
            uThree.setSalary(3000.30);
            uThree.setAge(30);
            uThree.setCity("Chicago");

            User uFour = new User("Christ", "christ@gmail.com", "147852", 35, 35000.3, "NJ");
            User uFive = new User("Sid", "Sid", "s258", 29, 4000.36, "LA");
            session.persist(uOne);
            session.persist(uTwo);
            session.persist(uThree);
            session.persist(uFour);
            session.persist(uFive);
            transaction.commit();
            System.out.println("Successfully added users");
            factory.close();
            session.close();
    }

    public static void findUser(SessionFactory factory, Session session, int userId){
        Transaction tx = session.beginTransaction();
        User u = session.get(User.class, userId);
        System.out.println("FullName: " + u.getFullName());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Password: " + u.getPassword());
        tx.commit();
        session.close();
        factory.close();
    }

    public static void updateUser(Session session, int userId){
        Transaction tx = session.beginTransaction();
        User u = new User();
        u.setId(userId);
        u.setEmail("Bisan@gmail.com");
        u.setFullName("Bisan Champ");
        u.setPassword("Bisan123");
        u.setSalary(4000.30);
        u.setAge(30);
        u.setCity("CA");
        session.merge(u);
        session.getTransaction().commit();
        session.close();

    }
    public static void deleteUser(Session session, int userId){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Transaction tx = session.beginTransaction();
        User u = new User();
        u.setId(userId);
        session.remove(u);
        tx.commit();
        session.close();
        factory.close();

    }

    public static void findUserHql(SessionFactory factory, Session session){
        //Example of HQL to get all records of HQL class
        String hqlFrom = "FROM User";
        String hqlSelect = "Select u from User u";
        TypedQuery<User> query = session.createQuery(hqlSelect, User.class);
        List<User> results = query.getResultList();
        System.out.printf("%s%13s%17s%34s%n", "|User Id", "|Full Name", "|Email", "|Password");
        for(User u: results){
            System.out.printf(" %-10d %-20s %-30s %s %n", u.getId(), u.getFullName(), u.getEmail(), u.getPassword());
        }
        session.close();
        factory.close();
    }

    public static void getRecordById(SessionFactory factory, Session session){
        String hql = "FROm User u WHERE u.id > 2 ORDER BY u.salary DESC";
        TypedQuery query = session.createQuery(hql, User.class);
        List<User> results= query.getResultList();
        System.out.printf("%s%13s%17s%34s%21s%n", "|User Id", "|Full Name", "|Email", "|Password", "|Salary");
        for(User u: results){
            System.out.printf(" %-10d %-20s %-30s %-23s %s %n", u.getId(), u.getFullName(), u.getEmail(), u.getPassword(), u.getSalary());

        }
        session.close();
        factory.close();
    }

    public static void getRecords(Session session){
        TypedQuery<Object[]> query = session.createQuery("Select u.salary, u.fullName FROM User u", Object[].class);
        List<Object[]> results = query.getResultList();
        System.out.printf("%s%13s%n","Salary","City");
        for(Object[] a: results){
            System.out.printf("%-16s%s%n",a[0],a[1]);
        }


    }
    public static void getMaxSalary(Session session){
        String hql = "SELECT MAX(u.salary) FROM User u";
        TypedQuery<Object> query = session.createQuery(hql, Object.class);
        Object result = query.getSingleResult();
        System.out.printf("%s%s","Maximum Salary:",result);
        session.close();
    }

    public static void getMaxSalaryGroupBy(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "Select SUM(u.salary), u.city from User u group by u.city";
        TypedQuery query = session.createQuery(hql);
        List<Object[]> result = query.getResultList();
        for(Object[] ob : result){
            System.out.println("Total salary: " + ob[0] + "City: " +ob[1]);

        }
        session.close();
        factory.close();
    }

    public static void namedQueryExample(Session session){
        String hql = "FROm User u where u.id = :id";
        TypedQuery <User> query = session.createQuery( hql, User.class);
        query.setParameter("id", 2);
        List <User> result = query.getResultList();
        System.out.printf("%s%13s%17s%34s%21s%n", "|User Id", "|Full name", "|Email", "|Password", "|Salary");
        for (User u : result) {
            System.out.printf(" %-10d %-20s %-30s %-23s %s %n", u.getId(), u.getFullName(), u.getEmail(), u.getPassword(), u.getSalary());
        }
    }


}




