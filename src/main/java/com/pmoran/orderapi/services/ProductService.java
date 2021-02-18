package com.pmoran.orderapi.services;

import com.pmoran.orderapi.entity.Product;
import com.pmoran.orderapi.exceptions.GeneralServiceException;
import com.pmoran.orderapi.exceptions.NoDataFoundException;
import com.pmoran.orderapi.exceptions.ValidateServiceException;
import com.pmoran.orderapi.repository.ProductRepository;
import com.pmoran.orderapi.validators.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> findAll(Pageable page){
        try{
            List<Product> products = productRepo.findAll(page).toList();
            if (products==null){
                throw new NoDataFoundException("None products found.");
            }
            if (products.isEmpty()){
                throw new NoDataFoundException("None products found.");
            }
            return products;
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public Product findById(Long productId){
        try{
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new NoDataFoundException("Product no found."));
            return product;
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public Product save(Product product){
        try{
            ProductValidator.save(product);

            if (product.getId() == null) {
                Product newProduct = productRepo.save(product);
                return newProduct;
            }

            Product updateProduct = productRepo.findById(product.getId())
                    .orElseThrow(() -> new NoDataFoundException("Product no found."));

            updateProduct.setName(product.getName());
            updateProduct.setPrice(product.getPrice());

            productRepo.save(updateProduct);

            return updateProduct;
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long productId){
        try{
            Product deleteProduct = productRepo.findById(productId)
                    .orElseThrow(() -> new NoDataFoundException("Product no found."));

            productRepo.delete(deleteProduct);
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}
