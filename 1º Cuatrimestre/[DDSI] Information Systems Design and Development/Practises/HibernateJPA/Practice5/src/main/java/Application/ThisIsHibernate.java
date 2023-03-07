package Application;

import Model.Activity;
import Model.Member1;
import Model.Trainer;
import java.lang.reflect.Member;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Alberto Fernández
 */
public class ThisIsHibernate {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {

            Member1 member = new Member1("XX4", "newMember", "7777", 'A');
            Activity activity = session.get(Activity.class, "AC03");
            //activity.getMember1Set().add(member);
            //member.getActivitySet().add(activity);
            //activity.addMember(member);
            //session.save(member);

            List<Trainer> trainerNative = session.createNativeQuery("SELECT * FROM Trainer").addEntity(Trainer.class).list();

            for (Trainer t : trainerNative) {
                System.out.println(t.getTName());
            }

            List<Trainer> trainers = session.createQuery("FROM Trainer T").list();

            for (Trainer t : trainers) {
                System.out.println(t.getTName());
            }

        } catch (Exception e) {
            System.out.println("Deleting Problem: " + e.getMessage());
        }

//
//        try {
//            session.save(member);
//            //session.delete(member);
//        } catch (Exception e) {
//            System.out.println("Deleting Problem: " + e.getMessage());
//        }
        transaction.commit();
        System.out.println(" OK");

    }

}
