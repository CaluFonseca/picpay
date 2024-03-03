package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServicesTest {

    @Mock
    private UserServices userServices;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authService;
    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionServices transactionServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception {
        User sender = new User(1L, "Maria", "Souza", "9999999901", "maria@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L, "João", "Souza", "9999999902", "joao@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);

        when(userServices.findUserById(1L)).thenReturn(sender);
        when(userServices.findUserById(2L)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
        transactionServices.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(userServices, times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(20));
        verify(userServices, times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender, "Transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotification(receiver, "Transação recebida com sucesso");
    }

    @Test
    @DisplayName("Should throw Exception when transaction is not allowed")
    void createTransactionCase2() throws Exception {
        User sender = new User(1L, "Maria", "Souza", "9999999901", "maria@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L, "João", "Souza", "9999999902", "joao@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);

        when(userServices.findUserById(1L)).thenReturn(sender);
        when(userServices.findUserById(2L)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
            transactionServices.createTransaction(request);
        });

        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());

    }
}