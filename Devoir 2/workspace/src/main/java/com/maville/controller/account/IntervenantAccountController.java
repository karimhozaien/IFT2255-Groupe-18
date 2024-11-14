package com.maville.controller.account;

import com.maville.model.Notification;
import com.maville.model.Resident;
import com.maville.model.User;
import java.util.List;

public class IntervenantAccountController implements AccountController {
    @Override
    public boolean updateAccount(User user) {
        // TODO
        return false; // replace
    }

    @Override
    public List<Notification> getNotifications(User user) {
        // TODO
        return null; // replace
    }

    public boolean activateAccount(Resident resident) {
        // TODO
        return false; // replace
    }

    public boolean deactivateAccount(Resident resident) {
        // TODO
        return false; // replace
    }
}
