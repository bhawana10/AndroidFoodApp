package com.example.bhawanasingh.foodmood.Common;

import com.example.bhawanasingh.foodmood.Model.User;

public class Common {
    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Common.currentUser = currentUser;
    }
}
