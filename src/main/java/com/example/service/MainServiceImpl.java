package com.example.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService{

    @Autowired
	EntityManagerFactory emf;

    // 물품 삭제 시 이루어지는 트랜잭션
    @Override
    public void deleteProductTransaction(Long no) {
        
        EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String sql = "UPDATE PRODUCT SET PRODUCTIMAGE=NULL, IMAGENAME=NULL, IMAGETYPE=NULL WHERE PRODUCTCODE=:no";
        em.createNativeQuery(sql)
            .setParameter("no", no).executeUpdate();
        String sql1 = "DELETE FROM SUBIMAGE WHERE PRODUCT=:no";
        em.createNativeQuery(sql1)
            .setParameter("no", no).executeUpdate();
        String sql2 = "DELETE FROM QUESTION WHERE PRODUCT=:no";
        em.createNativeQuery(sql2)
            .setParameter("no", no).executeUpdate();
        String sql3 = "DELETE FROM REVIEW WHERE PRODUCT=:no";
        em.createNativeQuery(sql3)
            .setParameter("no", no).executeUpdate();
        String sql4 = "DELETE FROM CARTITEM WHERE PRODUCT=:no";
        em.createNativeQuery(sql4)
            .setParameter("no", no).executeUpdate();
		em.getTransaction().commit();

    }
    
}
