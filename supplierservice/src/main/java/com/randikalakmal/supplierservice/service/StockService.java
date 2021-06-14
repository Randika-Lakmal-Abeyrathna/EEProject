package com.randikalakmal.supplierservice.service;

import com.randikalakmal.supplierservice.dto.StockRequest;
import com.randikalakmal.supplierservice.dto.StockUpdateRequest;
import com.randikalakmal.supplierservice.exception.StockException;
import com.randikalakmal.supplierservice.model.ProductDetails;
import com.randikalakmal.supplierservice.model.Status;
import com.randikalakmal.supplierservice.model.Stock;
import com.randikalakmal.supplierservice.model.SupplierInfo;
import com.randikalakmal.supplierservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductDetailsService productDetailsService;
    private final SupplierService supplierService;
    private final RestTemplate restTemplate;
    private final String ADMIN_SERVICE_URL = "http://admin-service/api/admin";

    public List<Stock> getAllStock(){
        return stockRepository.findAll();
    }

    public Stock getStockById(Integer id){
        return stockRepository.findById(id)
                .orElseThrow(()-> new StockException("Stock id "+id+" not found"));
    }

    public Stock addStock(StockRequest stockRequest){
        int productId =stockRequest.getProductId();
        int supplierId= stockRequest.getSupplierId();
        int statusId= stockRequest.getStatusId();

        ProductDetails productDetails = productDetailsService.getProductById(productId);
        SupplierInfo supplierInfo =supplierService.getSupplierById(supplierId);
        Status status =null;

        ResponseEntity<Status> statusResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL + "/status/find/" + statusId,
                HttpMethod.GET,
                null,
                Status.class);

        if (statusResponseEntity.getStatusCode() == HttpStatus.OK){
            status = statusResponseEntity.getBody();
        }else{
            throw new StockException("Error while retreating Status");
        }

        stockValidation(stockRequest);

        Stock stock = new Stock();
        stock.setQty(stockRequest.getQty());
        stock.setAvailability(stockRequest.isAvailability());
        stock.setStockPrice(stockRequest.getStockPrice());
        stock.setMarketPrice(stockRequest.getMarketPrice());
        stock.setDiscount(stockRequest.getDiscount());
        stock.setExpireDate(stockRequest.getExpireDate());
        stock.setSupplierInfo(supplierInfo);
        stock.setProductDetails(productDetails);
        stock.setStatus(status);

        return stockRepository.save(stock);
    }

    public Stock updateStock(StockUpdateRequest stockUpdateRequest){
        int productId =stockUpdateRequest.getProductId();
        int supplierId= stockUpdateRequest.getSupplierId();
        int statusId= stockUpdateRequest.getStatusId();
        int stockId=stockUpdateRequest.getId();

        ProductDetails productDetails = productDetailsService.getProductById(productId);
        SupplierInfo supplierInfo =supplierService.getSupplierById(supplierId);
        Status status =null;

        ResponseEntity<Status> statusResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL + "/status/find/" + statusId,
                HttpMethod.GET,
                null,
                Status.class);

        if (statusResponseEntity.getStatusCode() == HttpStatus.OK){
            status = statusResponseEntity.getBody();
        }else{
            throw new StockException("Error while retreating Status");
        }

        stockValidation(stockUpdateRequest);

        Stock stock =stockRepository.findById(stockId)
                .orElseThrow(()-> new StockException("Stock id "+stockId+" not found"));
        stock.setQty(stockUpdateRequest.getQty());
        stock.setAvailability(stockUpdateRequest.isAvailability());
        stock.setStockPrice(stockUpdateRequest.getStockPrice());
        stock.setMarketPrice(stockUpdateRequest.getMarketPrice());
        stock.setDiscount(stockUpdateRequest.getDiscount());
        stock.setExpireDate(stockUpdateRequest.getExpireDate());
        stock.setSupplierInfo(supplierInfo);
        stock.setProductDetails(productDetails);
        stock.setStatus(status);

        return stockRepository.save(stock);
    }

    private void stockValidation(StockRequest stockRequest){
        double qty = stockRequest.getQty();
        double stockPrice = stockRequest.getStockPrice();
        double marketPrice = stockRequest.getMarketPrice();
        Date expiryDate = stockRequest.getExpireDate();
        boolean availability = stockRequest.isAvailability();

        if (qty <0){
            throw new StockException("Quantity cannot be less than Zero (0)");
        }
        if (stockPrice>marketPrice){
            throw new StockException("Stock Price cannot be less than market Price");
        }
        if (expiryDate.before(new Date())){
            throw new StockException("Expiry Date cannot be before today");
        }
        if(qty==0 && availability){
            throw new StockException("If Quantity is zero (0) Availability should be false");
        }


    }

    


}
