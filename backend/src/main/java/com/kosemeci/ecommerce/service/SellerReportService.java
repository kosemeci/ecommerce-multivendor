package com.kosemeci.ecommerce.service;

import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.entity.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
