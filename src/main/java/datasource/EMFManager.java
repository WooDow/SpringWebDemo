/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author student
 * Singleton Design Pattern
 */
public class EMFManager {
    private static EntityManagerFactory emf = null; 
    
    static {
        emf = Persistence.createEntityManagerFactory("cm");                    
    }
    
    public static EntityManagerFactory getEMF() {
        if( emf == null ) {
            emf = Persistence.createEntityManagerFactory("cm");                    
        }
        return emf;
    }
    
    public static EntityManager getEntityManager() {
        return getEMF().createEntityManager();
    }
    
}
