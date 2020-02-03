package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.CommunicationLogs;
import models.CommunicationTransaction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import util.DataBase;

import java.util.List;

public class Repository extends DataBase {

    public void logTransaction(){
        Session session = getSessionFactory().openSession();
        session.getTransaction();

        session.close();
    }

    public void getCommunicationLogsByMobile(String mobileNumber){
        Session session  = getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "FROM CommunicationLogs cl WHERE cl.mobileNumber = :mobileNumber";
        Query query = session.createQuery(hql);
        query.setParameter("mobileNumber",mobileNumber);
        List results = query.list();
        CommunicationLogs logs = (CommunicationLogs) results.get(0);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Create Communication Logs
     * @param t
     */
    public void createCommunicationLogs(CommunicationTransaction t){
        Session session  = getSessionFactory().openSession();
        session.beginTransaction();
        ModelMapper mapper = new ModelMapper();
        CommunicationLogs logs = mapper.map(t, CommunicationLogs.class);
        session.save(logs);
        System.out.printf("[createCommunicationLogs] CommunicationTransaction created "+t.toString());
        session.getTransaction().commit();
        session.close();
    }
}
