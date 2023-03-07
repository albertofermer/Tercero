package view;

import Model.Activity;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.Member1;
import Model.Trainer;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Alberto Fernández Merchán
 */
public class TableView {

    /**
     *
     */
    public TableView() {

    }

    /**
     *
     */
    public DefaultTableModel modelTableMember = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     *
     */
    public DefaultTableModel modelTableTrainer = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     *
     */
    public DefaultTableModel modelTableActivity = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * 
     * @param vMember
     */
    public void setupTableMember(ViewMember vMember) {
        vMember.MemberTable.setModel(modelTableMember);
        String[] columNames = {"Num", "Name", "Id", "Phone Number", "Birth Date", "Email", "Start Date", "Cat"};
        modelTableMember.setColumnIdentifiers(columNames);

        vMember.MemberTable.getTableHeader().setResizingAllowed(false);
        vMember.MemberTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        vMember.MemberTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        vMember.MemberTable.getColumnModel().getColumn(1).setPreferredWidth(240);
        vMember.MemberTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        vMember.MemberTable.getColumnModel().getColumn(3).setPreferredWidth(70);
        vMember.MemberTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        vMember.MemberTable.getColumnModel().getColumn(5).setPreferredWidth(240);
        vMember.MemberTable.getColumnModel().getColumn(6).setPreferredWidth(1);
    }

    /**
     *
     * @param vTrainer
     */
    public void setupTableTrainer(ViewTrainer vTrainer) {
        vTrainer.TrainerTable.setModel(modelTableTrainer);
        String[] columNames = {"Cod", "Name", "Surname 1", "Surname 2", "ID", "Phone", "Email", "Date", "Nick"};
        modelTableTrainer.setColumnIdentifiers(columNames);

        vTrainer.TrainerTable.getTableHeader().setResizingAllowed(true);
        vTrainer.TrainerTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vTrainer.TrainerTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        vTrainer.TrainerTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        vTrainer.TrainerTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        vTrainer.TrainerTable.getColumnModel().getColumn(3).setPreferredWidth(70);
        vTrainer.TrainerTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        vTrainer.TrainerTable.getColumnModel().getColumn(5).setPreferredWidth(70);
        vTrainer.TrainerTable.getColumnModel().getColumn(6).setPreferredWidth(70);
        vTrainer.TrainerTable.getColumnModel().getColumn(7).setPreferredWidth(240);
        vTrainer.TrainerTable.getColumnModel().getColumn(8).setPreferredWidth(20);
    }

    /**
     *
     * @param vActivity
     * @param a
     */
    public void setupTableActivity(ViewActivity vActivity, ArrayList<Activity> a) {
        String[] acID = new String[a.size()];
        String[] aName = new String[a.size()];
        for (int i = 0; i < a.size(); i++) {
            acID[i] = a.get(i).getAId();
            aName[i] = a.get(i).getAName();
        }
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(aName);
        vActivity.actList.setModel(model);
        vActivity.ActivityTable.setModel(modelTableActivity);
        String[] columNames = {"Activity's Name", "Member's Name", "Member's E-mail"};
        modelTableActivity.setColumnIdentifiers(columNames);

        vActivity.ActivityTable.getTableHeader().setResizingAllowed(true);
        vActivity.ActivityTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vActivity.ActivityTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        vActivity.ActivityTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        vActivity.ActivityTable.getColumnModel().getColumn(2).setPreferredWidth(50);
    }

    /**
     *
     * @param lmember
     */
    public void fillTableMember(ArrayList<Member1> lmember) {
        Object[] row = new Object[8];
        int numRows = lmember.size();
        cleanTableMember();

        for (int i = 0; i < numRows; i++) {
            row[0] = lmember.get(i).getMNum();
            row[1] = lmember.get(i).getMName();
            row[2] = lmember.get(i).getMId();
            row[3] = lmember.get(i).getMPhone();
            row[4] = lmember.get(i).getMBirhtdate();
            row[5] = lmember.get(i).getMEmailmember();
            row[6] = lmember.get(i).getMStartingdatemember();
            row[7] = lmember.get(i).getMCathegorymember();
            modelTableMember.addRow(row);
        }
    }

    /**
     *
     * @param ltrainer
     */
    public void fillTableTrainer(ArrayList<Trainer> ltrainer) {
        Object[] row = new Object[9];
        int numRows = ltrainer.size();

        cleanTableTrainer();

        for (int i = 0; i < numRows; i++) {
            row[0] = ltrainer.get(i).getTCod();
            row[1] = ltrainer.get(i).getTName();
            row[2] = ltrainer.get(i).getTSurname1();
            row[3] = ltrainer.get(i).getTSurname2();
            row[4] = ltrainer.get(i).getTIdnumber();
            row[5] = ltrainer.get(i).getTPhonenumber();
            row[6] = ltrainer.get(i).getTEmail();
            row[7] = ltrainer.get(i).getTDate();
            row[8] = ltrainer.get(i).getTNick();
            modelTableTrainer.addRow(row);
        }
    }

    /**
     *
     * @param ActName
     * @param lMember
     */
    public void fillTableActivity(String ActName,ArrayList<Member1> lMember) {
        Object[] row = new Object[3];
        int numRows = lMember.size();

        cleanTableActivity();

        for (int i = 0; i < numRows; i++) {
            row[0] = ActName;
            row[1] = lMember.get(i).getMName();
            row[2] = lMember.get(i).getMEmailmember();
            modelTableActivity.addRow(row);
        }
    }

    /**
     *
     */
    public void cleanTableMember() {

        for (int i = modelTableMember.getRowCount() - 1; i >= 0; i--) {
            modelTableMember.removeRow(i);
        }
    }

    /**
     *
     */
    public void cleanTableTrainer() {

        for (int i = modelTableTrainer.getRowCount() - 1; i >= 0; i--) {
            modelTableTrainer.removeRow(i);
        }
    }

    /**
     *
     */
    public void cleanTableActivity() {
        for (int i = modelTableActivity.getRowCount() - 1; i >= 0; i--) {
            modelTableActivity.removeRow(i);
        }
    }

}
