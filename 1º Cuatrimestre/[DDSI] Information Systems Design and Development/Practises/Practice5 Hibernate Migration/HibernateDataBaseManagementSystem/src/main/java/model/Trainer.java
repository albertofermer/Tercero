/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "TRAINER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trainer.findAll", query = "SELECT t FROM Trainer t"),
    @NamedQuery(name = "Trainer.findByTCod", query = "SELECT t FROM Trainer t WHERE t.tCod = :tCod"),
    @NamedQuery(name = "Trainer.findByTName", query = "SELECT t FROM Trainer t WHERE t.tName = :tName"),
    @NamedQuery(name = "Trainer.findByTSurname1", query = "SELECT t FROM Trainer t WHERE t.tSurname1 = :tSurname1"),
    @NamedQuery(name = "Trainer.findByTSurname2", query = "SELECT t FROM Trainer t WHERE t.tSurname2 = :tSurname2"),
    @NamedQuery(name = "Trainer.findByTIdnumber", query = "SELECT t FROM Trainer t WHERE t.tIdnumber = :tIdnumber"),
    @NamedQuery(name = "Trainer.findByTPhonenumber", query = "SELECT t FROM Trainer t WHERE t.tPhonenumber = :tPhonenumber"),
    @NamedQuery(name = "Trainer.findByTEmail", query = "SELECT t FROM Trainer t WHERE t.tEmail = :tEmail"),
    @NamedQuery(name = "Trainer.findByTDate", query = "SELECT t FROM Trainer t WHERE t.tDate = :tDate"),
    @NamedQuery(name = "Trainer.findByTNick", query = "SELECT t FROM Trainer t WHERE t.tNick = :tNick")})
public class Trainer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "T_COD")
    private String tCod;
    @Basic(optional = false)
    @Column(name = "T_NAME")
    private String tName;
    @Basic(optional = false)
    @Column(name = "T_SURNAME1")
    private String tSurname1;
    @Column(name = "T_SURNAME2")
    private String tSurname2;
    @Basic(optional = false)
    @Column(name = "T_IDNUMBER")
    private String tIdnumber;
    @Column(name = "T_PHONENUMBER")
    private String tPhonenumber;
    @Column(name = "T_EMAIL")
    private String tEmail;
    @Column(name = "T_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tDate;
    //private String tDate;
    @Column(name = "T_NICK")
    private String tNick;
    @OneToMany(mappedBy = "aTrainerincharge")
    private Set<Activity> activitySet = new HashSet<Activity>();

    /**
     *
     */
    public Trainer() {
    }

    /**
     *
     * @param tCod
     */
    public Trainer(String tCod) {
        this.tCod = tCod;
    }

    /**
     *
     * @param tCod
     * @param tName
     * @param tSurname1
     * @param tIdnumber
     */
    public Trainer(String tCod, String tName, String tSurname1, String tIdnumber) {
        this.tCod = tCod;
        this.tName = tName;
        this.tSurname1 = tSurname1;
        this.tIdnumber = tIdnumber;
    }

//    public Trainer(String tCod, String tName, String tSurname1, String tSurname2, String tIdnumber, String tPhonenumber, String tEmail, String tDate, String tNick) {
//        this.tCod = tCod;
//        this.tName = tName;
//        this.tSurname1 = tSurname1;
//        this.tSurname2 = tSurname2;
//        this.tIdnumber = tIdnumber;
//        this.tPhonenumber = tPhonenumber;
//        this.tEmail = tEmail;
//        this.tDate = tDate;
//        this.tNick = tNick;
//    }

    /**
     *
     * @param tCod
     * @param tName
     * @param tSurname1
     * @param tSurname2
     * @param tIdnumber
     * @param tPhonenumber
     * @param tEmail
     * @param tDate
     * @param tNick
     */
    public Trainer(String tCod, String tName, String tSurname1, String tSurname2, String tIdnumber, String tPhonenumber, String tEmail, Date tDate, String tNick) {
        this.tCod = tCod;
        this.tName = tName;
        this.tSurname1 = tSurname1;
        this.tSurname2 = tSurname2;
        this.tIdnumber = tIdnumber;
        this.tPhonenumber = tPhonenumber;
        this.tEmail = tEmail;
        this.tDate = tDate;
        this.tNick = tNick;
    }
    
    /**
     *
     * @return
     */
    public String getTCod() {
        return tCod;
    }

    /**
     *
     * @param tCod
     */
    public void setTCod(String tCod) {
        this.tCod = tCod;
    }

    /**
     *
     * @return
     */
    public String getTName() {
        return tName;
    }

    /**
     *
     * @param tName
     */
    public void setTName(String tName) {
        this.tName = tName;
    }

    /**
     *
     * @return
     */
    public String getTSurname1() {
        return tSurname1;
    }

    /**
     *
     * @param tSurname1
     */
    public void setTSurname1(String tSurname1) {
        this.tSurname1 = tSurname1;
    }

    /**
     *
     * @return
     */
    public String getTSurname2() {
        return tSurname2;
    }

    /**
     *
     * @param tSurname2
     */
    public void setTSurname2(String tSurname2) {
        this.tSurname2 = tSurname2;
    }

    /**
     *
     * @return
     */
    public String getTIdnumber() {
        return tIdnumber;
    }

    /**
     *
     * @param tIdnumber
     */
    public void setTIdnumber(String tIdnumber) {
        this.tIdnumber = tIdnumber;
    }

    /**
     *
     * @return
     */
    public String getTPhonenumber() {
        return tPhonenumber;
    }

    /**
     *
     * @param tPhonenumber
     */
    public void setTPhonenumber(String tPhonenumber) {
        this.tPhonenumber = tPhonenumber;
    }

    /**
     *
     * @return
     */
    public String getTEmail() {
        return tEmail;
    }

    /**
     *
     * @param tEmail
     */
    public void setTEmail(String tEmail) {
        this.tEmail = tEmail;
    }

//    public String getTDate() {
//        return tDate;
//    }

    /**
     *
     * @return
     */
    public Date getTDate() {
        return tDate;
    }

    /**
     *
     * @param tDate
     */
    public void setTDate(Date tDate) {
        this.tDate = tDate;
    }
//    public void setTDate(String tDate) {
//        this.tDate = tDate;
//    }

    /**
     *
     * @return
     */
    public String getTNick() {
        return tNick;
    }

    /**
     *
     * @param tNick
     */
    public void setTNick(String tNick) {
        this.tNick = tNick;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public Set<Activity> getActivitySet() {
        return activitySet;
    }

    /**
     *
     * @param activitySet
     */
    public void setActivitySet(Set<Activity> activitySet) {
        this.activitySet = activitySet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tCod != null ? tCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trainer)) {
            return false;
        }
        Trainer other = (Trainer) object;
        if ((this.tCod == null && other.tCod != null) || (this.tCod != null && !this.tCod.equals(other.tCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Trainer[ tCod=" + tCod + " ]";
    }
    
}
