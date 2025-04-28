package com.kosemeci.ecommerce.entity;

import com.kosemeci.ecommerce.domain.AccountStatus;
import com.kosemeci.ecommerce.domain.USER_ROLE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sellerName;
    private String mobile;

    @Column(nullable=false,unique=true)
    private String email;
    private String password;
    private boolean isEmailVerified;

    @Embedded
    private BusinessDetails businessDetails= new BusinessDetails();

    @Embedded
    private BankDetails bankDetails= new BankDetails();

    @OneToOne(cascade=CascadeType.ALL)
    private Address pickUpAddress = new Address();
    private String GSTIN;

    private USER_ROLE role = USER_ROLE.ROLE_SELLER;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
    

}
