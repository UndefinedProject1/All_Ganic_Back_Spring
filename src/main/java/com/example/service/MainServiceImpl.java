package com.example.service;

import java.util.Date;

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

    @Override
    public void deleteMemberTransaction(String email, Date date) {
        EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
        String sql1 = "DELETE FROM CARTITEM WHERE CART=(SELECT CARTCODE FROM CART WHERE MEMBER=:email)";
        em.createNativeQuery(sql1)
            .setParameter("email", email).executeUpdate();
        String sql2 = "DELETE FROM CART WHERE MEMBER=:email";
        em.createNativeQuery(sql2)
            .setParameter("email", email).executeUpdate();
        String sql3 = "DELETE FROM CANCELHISTORY WHERE MEMBER=:email";
        em.createNativeQuery(sql3)
            .setParameter("email", email).executeUpdate();
        String sql4 = "UPDATE PAYHISTORY SET MEMBER='ghost' WHERE MEMBER=:email";
        em.createNativeQuery(sql4)
            .setParameter("email", email).executeUpdate();
        String sql6 = "DELETE FROM QUESTION WHERE MEMBER=:email";
        em.createNativeQuery(sql6)
            .setParameter("email", email).executeUpdate();
        String sql7 = "DELETE FROM REVIEW WHERE MEMBER=:email";
        em.createNativeQuery(sql7)
            .setParameter("email", email).executeUpdate();
        String sql = "DELETE FROM MEMBER WHERE LEAVECHECK=TRUE AND LEAVEDATE=:date AND USEREMAIL=:email";
        em.createNativeQuery(sql)
            .setParameter("email", email)
            .setParameter("date", date).executeUpdate();
		em.getTransaction().commit();
        
    }
    
}
