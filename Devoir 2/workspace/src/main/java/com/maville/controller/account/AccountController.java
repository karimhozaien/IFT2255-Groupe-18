package com.maville.controller.account;

import com.maville.model.Notification;
import com.maville.model.User;
import java.util.List;

public abstract class AccountController {
    protected abstract boolean updateAccount(User user);
    protected abstract List<Notification> getNotifications(User user);
}
