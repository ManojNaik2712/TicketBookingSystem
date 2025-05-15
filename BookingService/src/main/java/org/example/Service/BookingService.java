package org.example.Service;

import org.example.Client.InventoryServiceClient;
import org.example.DTO.BookingRequest;
import org.example.DTO.BookingResponse;
import org.example.DTO.InventoryResponse;
import org.example.Entity.Customer;
import org.example.Repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    InventoryServiceClient inventoryServiceClient;

    public BookingResponse createBooking(BookingRequest request) {
        //Checking if user exist or not
        final Customer customer=customerRepository.findById(request.getUserId()).orElse(null);
        if(customer == null){
            throw new RuntimeException("user not found");
        }

        //check if there is enough inventory
        final InventoryResponse inventoryResponse=inventoryServiceClient.getInventory(request.getEventId());
        System.out.println("Inventory Service Response" + inventoryResponse);

        return BookingResponse.builder().build();
    }
}
