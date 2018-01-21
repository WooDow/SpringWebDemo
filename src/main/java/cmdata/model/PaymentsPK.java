/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmdata.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author student
 */
@Embeddable
public class PaymentsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "customerNumber")
    private int customerNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "checkNumber")
    private String checkNumber;

    public PaymentsPK() {
    }

    public PaymentsPK(int customerNumber, String checkNumber) {
        this.customerNumber = customerNumber;
        this.checkNumber = checkNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customerNumber;
        hash += (checkNumber != null ? checkNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentsPK)) {
            return false;
        }
        PaymentsPK other = (PaymentsPK) object;
        if (this.customerNumber != other.customerNumber) {
            return false;
        }
        if ((this.checkNumber == null && other.checkNumber != null) || (this.checkNumber != null && !this.checkNumber.equals(other.checkNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cmdata.model.PaymentsPK[ customerNumber=" + customerNumber + ", checkNumber=" + checkNumber + " ]";
    }
    
}
