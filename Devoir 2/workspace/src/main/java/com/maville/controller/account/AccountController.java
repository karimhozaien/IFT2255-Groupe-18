package com.maville.controller.account;

import com.maville.model.Notification;
import com.maville.model.User;
import java.util.List;

public interface AccountController {
    boolean updateAccount(User user);
    List<Notification> getNotifications(User user);
}
