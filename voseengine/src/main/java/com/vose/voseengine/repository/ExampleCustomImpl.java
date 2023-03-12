package com.vose.voseengine.repository;

import com.vose.voseengine.model.entity.Kariyer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

public class ExampleCustomImpl { //implements ExampleRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    //@Override
    public Kariyer getNextKariyer(Long bayiId) {
        try {
            return em.createQuery(
                    "SELECT k FROM " +
                            "Bayi b, Kariyer k " +
                            "WHERE " +
                            "b.id = :bayiId AND " +
                            "k.siraNo = (b.kariyer.siraNo+1)",
                    Kariyer.class)
                    .setParameter("bayiId", bayiId)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
}
