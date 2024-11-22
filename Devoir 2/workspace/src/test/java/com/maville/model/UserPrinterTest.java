package com.maville.model;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import com.maville.view.MenuView;

class UserPrinterTest {

    @Test
    void testPrint() {
        User user = new User.Builder<>() {
            @Override
            protected Builder self() { return this; }

            @Override
            public User build() { return new User(this); }
        }.name("John Doe")
                .email("john.doe@company.com")
                .password("john123")
                .id()
                .build();

        try (MockedStatic<MenuView> mockedMenuView = mockStatic(MenuView.class)) {
            user.print();


            mockedMenuView.verify(() -> MenuView.printMessage("Name: John Doe"));
            mockedMenuView.verify(() -> MenuView.printMessage("Email: john.doe@company.com"));
            mockedMenuView.verify(() -> MenuView.printMessage("Password: john123"));
        }
    }
}