/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmdata.dao;

import cmdata.dao.exceptions.NonexistentEntityException;
import cmdata.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cmdata.model.Productlines;
import cmdata.model.Products;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author student
 */
public class ProductsJpaController implements Serializable {

    public ProductsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Products products) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productlines productLine = products.getProductLine();
            if (productLine != null) {
                productLine = em.getReference(productLine.getClass(), productLine.getProductLine());
                products.setProductLine(productLine);
            }
            em.persist(products);
            if (productLine != null) {
                productLine.getProductsCollection().add(products);
                productLine = em.merge(productLine);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducts(products.getProductCode()) != null) {
                throw new PreexistingEntityException("Products " + products + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Products products) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products persistentProducts = em.find(Products.class, products.getProductCode());
            Productlines productLineOld = persistentProducts.getProductLine();
            Productlines productLineNew = products.getProductLine();
            if (productLineNew != null) {
                productLineNew = em.getReference(productLineNew.getClass(), productLineNew.getProductLine());
                products.setProductLine(productLineNew);
            }
            products = em.merge(products);
            if (productLineOld != null && !productLineOld.equals(productLineNew)) {
                productLineOld.getProductsCollection().remove(products);
                productLineOld = em.merge(productLineOld);
            }
            if (productLineNew != null && !productLineNew.equals(productLineOld)) {
                productLineNew.getProductsCollection().add(products);
                productLineNew = em.merge(productLineNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = products.getProductCode();
                if (findProducts(id) == null) {
                    throw new NonexistentEntityException("The products with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products products;
            try {
                products = em.getReference(Products.class, id);
                products.getProductCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The products with id " + id + " no longer exists.", enfe);
            }
            Productlines productLine = products.getProductLine();
            if (productLine != null) {
                productLine.getProductsCollection().remove(products);
                productLine = em.merge(productLine);
            }
            em.remove(products);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Products> findProductsEntities() {
        return findProductsEntities(true, -1, -1);
    }

    public List<Products> findProductsEntities(int maxResults, int firstResult) {
        return findProductsEntities(false, maxResults, firstResult);
    }

    private List<Products> findProductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Products.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Products findProducts(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Products.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Products> rt = cq.from(Products.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
