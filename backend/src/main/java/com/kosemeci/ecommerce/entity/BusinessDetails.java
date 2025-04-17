package com.kosemeci.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDetails {

    private String businessName;
    private String businessMail;
    private String businessMobile;
    private String businessAddress;
    private String logo;
    private String banner;
}
