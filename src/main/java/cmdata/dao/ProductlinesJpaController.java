/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmdata.dao;

import cmdata.dao.exceptions.IllegalOrphanException;
import cmdata.dao.exceptions.NonexistentEntityException;
import cmdata.dao.exceptions.PreexistingEntityException;
import cmdata.model.Productlines;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cmdata.model.Products;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author student
 */
public class ProductlinesJpaController implements Serializable {

    public ProductlinesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productlines productlines) throws PreexistingEntityException, Exception {
        if (productlines.getProductsCollection() == null) {
            productlines.setProductsCollection(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : productlines.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getProductCode());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            productlines.setProductsCollection(attachedProductsCollection);
            em.persist(productlines);
            for (Products productsCollectionProducts : productlines.getProductsCollection()) {
                Productlines oldProductLineOfProductsCollectionProducts = productsCollectionProducts.getProductLine();
                productsCollectionProducts.setProductLine(productlines);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldProductLineOfProductsCollectionProducts != null) {
                    oldProductLineOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldProductLineOfProductsCollectionProducts = em.merge(oldProductLineOfProductsCollectionProducts);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductlines(productlines.getProductLine()) != null) {
                throw new PreexistingEntityException("Productlines " + productlines + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productlines productlines) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productlines persistentProductlines = em.find(Productlines.class, productlines.getProductLine());
            Collection<Products> productsCollectionOld = persistentProductlines.getProductsCollection();
            Collection<Products> productsCollectionNew = productlines.getProductsCollection();
            List<String> illegalOrphanMessages = null;
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Products " + productsCollectionOldProducts + " since its productLine field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Products> attachedProductsCollectionNew = new ArrayList<Products>();
            for (Products productsCollectionNewProductsToAttach : productsCollectionNew) {
                productsCollectionNewProductsToAttach = em.getReference(productsCollectionNewProductsToAttach.getClass(), productsCollectionNewProductsToAttach.getProductCode());
                attachedProductsCollectionNew.add(productsCollectionNewProductsToAttach);
            }
            productsCollectionNew = attachedProductsCollectionNew;
            productlines.setProductsCollection(productsCollectionNew);
            productlines = em.merge(productlines);
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Productlines oldProductLineOfProductsCollectionNewProducts = productsCollectionNewProducts.getProductLine();
                    productsCollectionNewProducts.setProductLine(productlines);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldProductLineOfProductsCollectionNewProducts != null && !oldProductLineOfProductsCollectionNewProducts.equals(productlines)) {
                        oldProductLineOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldProductLineOfProductsCollectionNewProducts = em.merge(oldProductLineOfProductsCollectionNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = productlines.getProductLine();
                if (findProductlines(id) == null) {
                    throw new NonexistentEntityException("The productlines with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productlines productlines;
            try {
                productlines = em.getReference(Productlines.class, id);
                productlines.getProductLine();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productlines with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Products> productsCollectionOrphanCheck = productlines.getProductsCollection();
            for (Products productsCollectionOrphanCheckProducts : productsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productlines (" + productlines + ") cannot be destroyed since the Products " + productsCollectionOrphanCheckProducts + " in its productsCollection field has a non-nullable productLine field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(productlines);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productlines> findProductlinesEntities() {
        return findProductlinesEntities(true, -1, -1);
    }

    public List<Productlines> findProductlinesEntities(int maxResults, int firstResult) {
        return findProductlinesEntities(false, maxResults, firstResult);
    }

    private List<Productlines> findProductlinesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productlines.class));
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

    public Productlines findProductlines(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productlines.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductlinesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productlines> rt = cq.from(Productlines.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
