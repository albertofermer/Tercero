package view;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Member;
import model.Trainer;

/**
 *
 * @author usuario
 */
public class TableView {

    public TableView() {

    }

    public DefaultTableModel modelTableMember = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public DefaultTableModel modelTableTrainer = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public void setupTableMember(ViewMember vMember) {
        vMember.MemberTable.setModel(modelTableMember);
        String[] columNames = {"Num", "Name", "Id", "Birth Date", "Phone Number", "Email", "Start Date", "Cat"};
        modelTableMember.setColumnIdentifiers(columNames);

        vMember.MemberTable.getTableHeader().setResizingAllowed(false);
        vMember.MemberTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vMember.MemberTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        vMember.MemberTable.getColumnModel().getColumn(1).setPreferredWidth(240);
        vMember.MemberTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        vMember.MemberTable.getColumnModel().getColumn(3).setPreferredWidth(70);
        vMember.MemberTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        vMember.MemberTable.getColumnModel().getColumn(5).setPreferredWidth(240);
        vMember.MemberTable.getColumnModel().getColumn(6).setPreferredWidth(1);
    }

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

    public void fillTableMember(ArrayList<Member> lmember) {
        Object[] row = new Object[lmember.size()];
        int numRows = lmember.size();

        cleanTableMember();

        for (int i = 0; i < numRows; i++) {
            row[0] = lmember.get(i).getM_num();
            row[1] = lmember.get(i).getM_name();
            row[2] = lmember.get(i).getM_id();
            row[3] = lmember.get(i).getM_phone();
            row[4] = lmember.get(i).getM_birthdate();
            row[5] = lmember.get(i).getM_emailMember();
            row[6] = lmember.get(i).getM_startingDateMember();
            row[7] = lmember.get(i).getM_cathegoryMember();
            modelTableMember.addRow(row);
        }
    }
    public void fillTableTrainer(ArrayList<Trainer> ltrainer) {
        Object[] row = new Object[ltrainer.size()];
        int numRows = ltrainer.size();

        cleanTableTrainer();

        for (int i = 0; i < numRows; i++) {
            row[0] = ltrainer.get(i).getT_cod();
            row[1] = ltrainer.get(i).getT_name();
            row[2] = ltrainer.get(i).getT_surname1();
            row[3] = ltrainer.get(i).getT_surname2();
            row[4] = ltrainer.get(i).getT_idnumber();
            row[5] = ltrainer.get(i).getT_phonenumber();
            row[6] = ltrainer.get(i).getT_email();
            row[7] = ltrainer.get(i).getT_date();
            row[8] = ltrainer.get(i).getT_nick();
            modelTableTrainer.addRow(row);
        }
    }

    public void cleanTableMember() {

        for (int i = modelTableMember.getRowCount() - 1; i >= 0; i--) {
            modelTableMember.removeRow(i);
        }
    }
    public void cleanTableTrainer() {

        for (int i = modelTableTrainer.getRowCount() - 1; i >= 0; i--) {
            modelTableTrainer.removeRow(i);
        }
    }

}
