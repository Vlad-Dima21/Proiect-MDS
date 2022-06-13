package com.example.proiectmds;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.proiectmds.domain.Client;
import com.example.proiectmds.persistence.ClientRepository;
import com.example.proiectmds.services.ClientService;

public class LoginUnitTest {

    @Test
    public void clientLoginIsCorrect() {
        Client loginClient = new ClientRepository().get(1);
        ClientService test = new ClientService();

        System.out.println(loginClient.getPassword());

        assertEquals(loginClient, test.loginClient(loginClient.getEmail(), loginClient.getPassword()));
        assertNull(test.loginClient(loginClient.getEmail() + 'a', loginClient.getPassword()));
        assertNull(test.loginClient(loginClient.getEmail(), loginClient.getPassword() + 'b'));
    }
}
