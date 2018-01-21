/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmdata.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author student
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o")
    , @NamedQuery(name = "Orders.findByOrderNumber", query = "SELECT o FROM Orders o WHERE o.orderNumber = :orderNumber")
    , @NamedQuery(name = "Orders.findByOrderDate", query = "SELECT o FROM Orders o WHERE o.orderDate = :orderDate")
    , @NamedQuery(name = "Orders.findByRequiredDate", query = "SELECT o FROM Orders o WHERE o.requiredDate = :requiredDate")
    , @NamedQuery(name = "Orders.findByShippedDate", query = "SELECT o FROM Orders o WHERE o.shippedDate = :shippedDate")
    , @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "orderNumber")
    private Integer orderNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orderDate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requiredDate")
    @Temporal(TemporalType.DATE)
    private Date requiredDate;
    @Column(name = "shippedDate")
    @Temporal(TemporalType.DATE)
    private Date shippedDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "status")
    private String status;
    @Lob
    @Size(max = 65535)
    @Column(name = "comments")
    private String comments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orders")
    private Collection<Orderdetails> orderdetailsCollection;
    @JoinColumn(name = "customerNumber", referencedColumnName = "customerNumber")
    @ManyToOne(optional = false)
    private Customers customerNumber;

    public Orders() {
    }

    public Orders(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Orders(Integer orderNumber, Date orderDate, Date requiredDate, String status) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.status = status;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @XmlTransient
    public Collection<Orderdetails> getOrderdetailsCollection() {
        return orderdetailsCollection;
    }

    public void setOrderdetailsCollection(Collection<Orderdetails> orderdetailsCollection) {
        this.orderdetailsCollection = orderdetailsCollection;
    }

    public Customers getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Customers customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderNumber != null ? orderNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderNumber == null && other.orderNumber != null) || (this.orderNumber != null && !this.orderNumber.equals(other.orderNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cmdata.model.Orders[ orderNumber=" + orderNumber + " ]";
    }
    
}
