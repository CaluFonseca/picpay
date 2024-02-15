package com.picpaysimplificado.services;

import com.picpaysimplificado.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServicesTest {

    @Mock
    private UserServices userServices;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authorizationService;

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
    void createTransactionCase1() {
        

    }

    @Test
    @DisplayName("Should throw Exception when transaction is not allowed")
    void createTransactionCase2() {
    }
}