package com.kosemeci.ecommerce.service.impl;

import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.entity.SellerReport;
import com.kosemeci.ecommerce.repository.SellerReportRepository;
import com.kosemeci.ecommerce.service.SellerReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService{
    
    private final SellerReportRepository sellerReportRepository;

    @Override
    public SellerReport getSellerReport(Seller seller) {
        SellerReport sellerReport = sellerReportRepository.findBySellerId(seller.getId());
        if(sellerReport==null){
            SellerReport newSellerReport = new SellerReport();
            newSellerReport.setSeller(seller);
            return sellerReportRepository.save(newSellerReport);
        }
        return sellerReport;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }
    
}
