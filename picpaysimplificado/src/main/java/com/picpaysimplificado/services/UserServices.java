package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServices {
    @Autowired
    private UserRepository repository;

   public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("User do tipo Merchant não esta autorizado a realizar transação");
        }

        if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(()-> new Exception("User não encontrado"));
    }

    public User createUser(UserDTO data){
       User newUser = new User(data);
       this.saveUser(newUser);
       return newUser;
    }

    public List<User> getAllUsers(){
       return this.repository.findAll();
    }

    public void saveUser(User user){
        this.repository.save(user);
    }
}
