package org.aua.aoop.post.factory;

import org.aua.aoop.post.product.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import java.util.List;

public class ProductCrud {

    private SessionFactory sessionFactory;
    private StandardServiceRegistry serviceRegistry;

    public ProductCrud() {
        DBSetup db = DBSetup.getInstance();
        this.sessionFactory = db.getSessionFactory();
        this.serviceRegistry = db.getServiceRegistry();
    }

    public List<Product> getProductList() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Product> productList = null;
        try {
            tx = session.beginTransaction();
            productList = (List<Product>) session.createQuery("FROM Product").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return productList;
    }

    public Integer addProduct(String description, double price, String imagePath, int quantity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer productId = null;
        try {
            tx = session.beginTransaction();
            Product product = new Product(description, price, imagePath, quantity);
            productId = (Integer) session.save(product);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productId;
    }

    public void addOrUpdateProduct(Long productId, String description, String imagePath, double price, int quantity){

        if (productId == null) {
            this.addProduct(description, price, imagePath, quantity);
            return;
        }

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Product product = (Product) session.get(Product.class, productId);
            product.setDescription(description);
            product.setPrice(price);
            product.setImagePath(imagePath);
            product.setQuantity(quantity);

            session.update(product);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateProduct(Product product){
        this.addOrUpdateProduct(product.getId(), product.getDescription(), product.getImagePath(), product.getPrice(), product.getQuantity());
    }


        public void removeProduct(int productId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Product product = (Product) session.get(Product.class, productId);
            session.delete(product);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
