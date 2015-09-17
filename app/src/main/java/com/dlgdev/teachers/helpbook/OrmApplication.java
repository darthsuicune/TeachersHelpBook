package com.dlgdev.teachers.helpbook;

import android.app.Application;

import ollie.Ollie;

public class OrmApplication extends Application {
    public static final String DB_NAME = "teachersHelpbook.db";
    public static final int DB_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        Ollie.with(this)
                .setName(DB_NAME)
                .setVersion(DB_VERSION)
                .init();
    }
}
