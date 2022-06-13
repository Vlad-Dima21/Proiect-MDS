package com.example.proiectmds.data;

import android.widget.Toast;

import com.example.proiectmds.data.model.LoggedInUser;
import com.example.proiectmds.domain.Client;
import com.example.proiectmds.domain.Manager;
import com.example.proiectmds.services.ClientService;
import com.example.proiectmds.services.ManagerService;
import com.example.proiectmds.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */



public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {

            ClientService clientService = new ClientService();
            ManagerService managerService = new ManagerService();

            if (clientService.checkMail(username))
            {
                Client client = clientService.loginClient(username,password);
                if (client == null) {
                    return new Result.Error(new Exception("Error Invalid Password"));
                }
                LoggedInUser newUser = new LoggedInUser(java.util.UUID.randomUUID().toString(),
                        client.getEmail());
                return new Result.Success<>(newUser);
            }
            else if (managerService.checkMail(username))
            {
                Manager manager = managerService.loginManager(username,password);
                if (manager == null)
                {

                   return new Result.Error(new Exception("Error Invalid Password"));
                }
                LoggedInUser newUser = new LoggedInUser(java.util.UUID.randomUUID().toString(),
                        manager.getEmail());
                return new Result.Success<>(newUser);
            }
            else
            {
                clientService.registerClient(username,password);
                LoggedInUser newUser = new LoggedInUser(java.util.UUID.randomUUID().toString(),
                        username);
                return new Result.Success<>(newUser);
            }


        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}