package com.pmoran.orderapi.services;

import com.pmoran.orderapi.entity.Order;
import com.pmoran.orderapi.entity.OrderLine;
import com.pmoran.orderapi.entity.Product;
import com.pmoran.orderapi.entity.User;
import com.pmoran.orderapi.exceptions.GeneralServiceException;
import com.pmoran.orderapi.exceptions.NoDataFoundException;
import com.pmoran.orderapi.exceptions.ValidateServiceException;
import com.pmoran.orderapi.repository.OrderLineRepository;
import com.pmoran.orderapi.repository.OrderRepository;
import com.pmoran.orderapi.repository.ProductRepository;
import com.pmoran.orderapi.security.UserPrincipal;
import com.pmoran.orderapi.validators.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderLineRepository orderLineRepo;

    @Autowired
    private ProductRepository productRepo;

    public List<Order> findAll(Pageable page){
        try{
            List<Order> orders = orderRepo.findAll(page).toList();
            if (orders == null) {
                throw new NoDataFoundException("None orders found.");
            }
            if (orders.isEmpty()) {
                throw new NoDataFoundException("None orders found.");
            }
            return orders;
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public Order findById(Long orderId){
        try{
            Order order = orderRepo.findById(orderId)
                    .orElseThrow(() -> new NoDataFoundException("Order no found."));
            return order;
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public Order save(Order order){
        try{
            OrderValidator.save(order);

            User user = UserPrincipal.getCurrentUser();

            double total = 0;
            for(OrderLine line : order.getLines()){
                Product product = productRepo.findById(line.getProduct().getId())
                        .orElseThrow(()-> new NoDataFoundException("Product not found."));
                line.setPrice(product.getPrice());
                line.setTotal(product.getPrice() * line.getQuantity());
                total += line.getTotal();
            }
            order.setTotal(total);

            order.getLines().forEach(line -> line.setOrder(order));

            if (order.getId() == null){
                order.setRegDate(LocalDateTime.now());
                order.setUser(user);
                Order newOrder = orderRepo.save(order);
                return newOrder;
            }

            Order updateOrder = orderRepo.findById(order.getId())
                    .orElseThrow(() -> new NoDataFoundException("Order not found."));
            order.setRegDate(updateOrder.getRegDate());

            List<OrderLine> deletedLines = updateOrder.getLines();
            deletedLines.removeAll(order.getLines());
            orderLineRepo.deleteAll(deletedLines);

            return orderRepo.save(order);
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long orderId){
        try{
            Order order = orderRepo.findById(orderId)
                    .orElseThrow(() -> new NoDataFoundException("Order no found."));
            orderRepo.delete(order);
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}
