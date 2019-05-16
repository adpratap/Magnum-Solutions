package com.adpratap11.apkpro;

import java.util.ArrayList;

public class dataclass {
    private String mName;
    private boolean mOnline;

    public dataclass(String name, boolean online) {
        mName = name;
        mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastContactId = 0;

    public static ArrayList<dataclass> createContactsList(int numContacts) {
        ArrayList<dataclass> contacts = new ArrayList<dataclass>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new dataclass("Person " + ++lastContactId, i <= numContacts / 2));
        }

        return contacts;
    }
}