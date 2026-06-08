package com.thedevs.real_estate.service;

import com.thedevs.real_estate.dto.CreateSaleRequest;
import com.thedevs.real_estate.dto.UpdateSaleRequest;
import com.thedevs.real_estate.model.Sale;
import com.thedevs.real_estate.model.enums.ListingStatus;
import com.thedevs.real_estate.repository.PropertyRepository;
import com.thedevs.real_estate.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

   private final SaleRepository saleRepository;
   private final PropertyRepository propertyRepository;

   public SaleService(SaleRepository saleRepository, PropertyRepository propertyRepository) {
       this.saleRepository = saleRepository;
       this.propertyRepository = propertyRepository;
   }

   public List<Sale> getAllSales() {
       return (List<Sale>) saleRepository.findAll();
   }

   public Optional<Sale> getSaleById(Long id) {
       return saleRepository.findById(id);
   }

   public Sale createSale(CreateSaleRequest request) {
        return propertyRepository.findById(request.getPropertyId()).map(property -> {
            Sale sale = new Sale();
            sale.setProperty(property);
            sale.setAskingPrice(request.getAskingPrice());
            sale.setDescription(request.getDescription());
            sale.setListedAt(LocalDate.now());
            sale.setStatus(ListingStatus.active);
            return saleRepository.save(sale);
        }).orElseThrow(() -> new RuntimeException("Property not found"));
   }

   public Sale updateSale(Long id, UpdateSaleRequest request) {
       return saleRepository.findById(id).map(sale -> {
           if (request.getAskingPrice() != null) sale.setAskingPrice(request.getAskingPrice());
           if (request.getDescription() != null) sale.setDescription(request.getDescription());
           if (request.getStatus() != null) sale.setStatus(request.getStatus());
           sale.setUpdatedAt(LocalDate.now());
           return saleRepository.save(sale);
       }).orElseThrow(() -> new RuntimeException("Sale not found"));
   }

   public void deleteSale(Long id) {
       if (!saleRepository.existsById(id)) {
           throw new RuntimeException("Sale not found");
       }
       saleRepository.deleteById(id);
   }
}