package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import view.MessageView;

/**
 *
 * @author Alberto Fernández
 */
public class TrainerDAO {

    private Session s = null;
    private MessageView vMessage = null;

    /**
     * Constructor of the class.
     * @param s
     */
    public TrainerDAO(Session s) {
        vMessage = new MessageView();
        this.s = s;
    }

    /**
     * Adds a trianer into the database.
     * @param t
     * @return
     */
    public boolean insertTrainer(Trainer t) {
        boolean insertedOK = false;
        Transaction transaction = s.beginTransaction();
        try {
            s.save(t);
            transaction.commit();
            insertedOK = true;
        } catch (Exception e) {
            transaction.rollback();
            vMessage.Message("error", "Error: " + e.getMessage());
            System.out.println("INSERT ROLLBACK!!");
        }

        return insertedOK;

    }

    /**
     * Updates a trainer from the database.
     * @param codTrainer
     * @param t
     * @return
     */
    public boolean UpdateTrainer(String codTrainer, Trainer t) {
        Transaction transaction = s.beginTransaction();
        boolean updateOK = false;
        try {
            Trainer m = s.get(Trainer.class, codTrainer);
            m.setTCod(t.getTCod());
            m.setTName(t.getTName());
            m.setTDate(t.getTDate());
            m.setTIdnumber(t.getTIdnumber());
            m.setTNick(t.getTNick());
            m.setTSurname1(t.getTSurname1());
            m.setTSurname2(t.getTSurname2());
            m.setActivitySet(t.getActivitySet());
            s.update(m);
            
            transaction.commit();
            System.out.println("UPDATE OK");
            updateOK = true;

        } catch (Exception e) {
            transaction.rollback();
            vMessage.Message("error", "Error: " + e.getMessage());
            System.out.println("UPDATE ROLLBACK!");
        }

        return updateOK;
    }

    /**
     * Deletes a trainer from the database.
     * @param codTrainer
     * @return
     */
    public boolean DeleteTrainer(String codTrainer) {
        boolean deleteOK = false;

        Transaction transaction = s.beginTransaction();
        Trainer t = s.get(Trainer.class, codTrainer);

        if (t.getActivitySet().isEmpty()) {
            try {
                //Trainer t = s.get(Trainer.class, codTrainer);
                s.delete(t);
                transaction.commit();
                deleteOK = true;
            } catch (Exception e) {
                transaction.rollback();
                vMessage.Message("error", "Error: " + e.getMessage());
                System.out.println("DELETE ROLLBACK!!");
            }
        } else {
            try {
                Set<Activity> activitySet = t.getActivitySet();
                if (s.get(Trainer.class, "M999") == null) {
                    Trainer genericTrainer = new Trainer("M999", "Generic", "Trainer", "00000000A");
                    genericTrainer.setActivitySet(activitySet);
                    s.save(genericTrainer);
                }

                for (Activity a : activitySet) {
                    a.setATrainerincharge(s.get(Trainer.class, "M999"));
                }

                s.delete(t);
                transaction.commit();
            } catch (Exception e) {
                vMessage.Message("error", "Error: " + e.getMessage());
                System.out.println("GENERIC MONITOR IS IN CHARGE OF MANY ACTIVITIES!!");
                transaction.rollback();
            }

        }

        return deleteOK;

    }

    /**
     * Removes all the trainers from the database.
     * @return
     * @throws SQLException
     */
    public boolean CleanTrainers() throws SQLException {
        Transaction transaction = s.beginTransaction();
        boolean cleanOK = false;
        try {
            s.createNativeQuery("DELETE FROM TRAINER").executeUpdate();
            transaction.commit();
            cleanOK = true;
        } catch (Exception e) {
            transaction.rollback();
            vMessage.Message("error", "Error: " + e.getMessage());
            System.out.println("CLEAN ROLLBACK!!");
        }

        return cleanOK;
    }

    /**
     * Gets a list of all the trainers in the database.
     * @return
     * @throws SQLException
     */
    public ArrayList<Trainer> listAllTrainers() throws SQLException {
        Transaction tr = s.beginTransaction();
        ArrayList<Trainer> T = new ArrayList();
        try {
            T = (ArrayList<Trainer>) s.createNativeQuery("SELECT * FROM TRAINER", Trainer.class).list();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            vMessage.Message("error", "Error: " + e.getMessage());
            System.out.println("LIST ROLLBACK!!");
        }

        return T;
    }

    /**
     * Gets a set of all the activities in which a trainer is in charge.
     * @param TrainerID
     * @return
     */
    public Set<Activity> getTrainerActivities(String TrainerID) {
        return s.get(Trainer.class, TrainerID).getActivitySet();
       
    }

}
