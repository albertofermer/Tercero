/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "MEMBER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Member1.findAll", query = "SELECT m FROM Member1 m"),
    @NamedQuery(name = "Member1.findByMNum", query = "SELECT m FROM Member1 m WHERE m.mNum = :mNum"),
    @NamedQuery(name = "Member1.findByMName", query = "SELECT m FROM Member1 m WHERE m.mName = :mName"),
    @NamedQuery(name = "Member1.findByMId", query = "SELECT m FROM Member1 m WHERE m.mId = :mId"),
    @NamedQuery(name = "Member1.findByMBirhtdate", query = "SELECT m FROM Member1 m WHERE m.mBirhtdate = :mBirhtdate"),
    @NamedQuery(name = "Member1.findByMPhone", query = "SELECT m FROM Member1 m WHERE m.mPhone = :mPhone"),
    @NamedQuery(name = "Member1.findByMEmailmember", query = "SELECT m FROM Member1 m WHERE m.mEmailmember = :mEmailmember"),
    @NamedQuery(name = "Member1.findByMStartingdatemember", query = "SELECT m FROM Member1 m WHERE m.mStartingdatemember = :mStartingdatemember"),
    @NamedQuery(name = "Member1.findByMCathegorymember", query = "SELECT m FROM Member1 m WHERE m.mCathegorymember = :mCathegorymember")})
public class Member1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "M_NUM")
    private String mNum;
    @Basic(optional = false)
    @Column(name = "M_NAME")
    private String mName;
    @Basic(optional = false)
    @Column(name = "M_ID")
    private String mId;
    @Column(name = "M_BIRHTDATE")
    private String mBirhtdate;
    @Column(name = "M_PHONE")
    private String mPhone;
    @Column(name = "M_EMAILMEMBER")
    private String mEmailmember;
    @Column(name = "M_STARTINGDATEMEMBER")
    private String mStartingdatemember;
    @Basic(optional = false)
    @Column(name = "M_CATHEGORYMEMBER")
    private String mCathegorymember;
    @ManyToMany(mappedBy = "member1Set")
    private Set<Activity> activitySet = new HashSet<Activity>();

    /**
     *
     */
    public Member1() {
    }

    /**
     *
     * @param mNum
     */
    public Member1(String mNum) {
        this.mNum = mNum;
    }

    /**
     *
     * @param mNum
     * @param mName
     * @param mId
     * @param mCathegorymember
     */
    public Member1(String mNum, String mName, String mId, String mCathegorymember) {
        this.mNum = mNum;
        this.mName = mName;
        this.mId = mId;
        this.mCathegorymember = mCathegorymember;
    }

    /**
     *
     * @param mNum
     * @param mName
     * @param mId
     * @param mBirhtdate
     * @param mPhone
     * @param mEmailmember
     * @param mStartingdatemember
     * @param mCathegorymember
     */
    public Member1(String mNum, String mName, String mId, String mBirhtdate, String mPhone, String mEmailmember, String mStartingdatemember, String mCathegorymember) {
        this.mNum = mNum;
        this.mName = mName;
        this.mId = mId;
        this.mBirhtdate = mBirhtdate;
        this.mPhone = mPhone;
        this.mEmailmember = mEmailmember;
        this.mStartingdatemember = mStartingdatemember;
        this.mCathegorymember = mCathegorymember;
    }

    /**
     *
     * @return
     */
    public String getMNum() {
        return mNum;
    }

    /**
     *
     * @param mNum
     */
    public void setMNum(String mNum) {
        this.mNum = mNum;
    }

    /**
     *
     * @return
     */
    public String getMName() {
        return mName;
    }

    /**
     *
     * @param mName
     */
    public void setMName(String mName) {
        this.mName = mName;
    }

    /**
     *
     * @return
     */
    public String getMId() {
        return mId;
    }

    /**
     *
     * @param mId
     */
    public void setMId(String mId) {
        this.mId = mId;
    }

    /**
     *
     * @return
     */
    public String getMBirhtdate() {
        return mBirhtdate;
    }

    /**
     *
     * @param mBirhtdate
     */
    public void setMBirhtdate(String mBirhtdate) {
        this.mBirhtdate = mBirhtdate;
    }

    /**
     *
     * @return
     */
    public String getMPhone() {
        return mPhone;
    }

    /**
     *
     * @param mPhone
     */
    public void setMPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    /**
     *
     * @return
     */
    public String getMEmailmember() {
        return mEmailmember;
    }

    /**
     *
     * @param mEmailmember
     */
    public void setMEmailmember(String mEmailmember) {
        this.mEmailmember = mEmailmember;
    }

    /**
     *
     * @return
     */
    public String getMStartingdatemember() {
        return mStartingdatemember;
    }

    /**
     *
     * @param mStartingdatemember
     */
    public void setMStartingdatemember(String mStartingdatemember) {
        this.mStartingdatemember = mStartingdatemember;
    }

    /**
     *
     * @return
     */
    public String getMCathegorymember() {
        return mCathegorymember;
    }

    /**
     *
     * @param mCathegorymember
     */
    public void setMCathegorymember(String mCathegorymember) {
        this.mCathegorymember = mCathegorymember;
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
        hash += (mNum != null ? mNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Member1)) {
            return false;
        }
        Member1 other = (Member1) object;
        if ((this.mNum == null && other.mNum != null) || (this.mNum != null && !this.mNum.equals(other.mNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Member1[ mNum=" + mNum + " ]";
    }
    
}
