package com.dlgdev.teachers.helpbook.db;

import com.dlgdev.teachers.helpbook.OrmApplication;

import ollie.OllieProvider;

public class TeachersProvider extends OllieProvider {
    @Override
    protected String getDatabaseName() {
        return OrmApplication.DB_NAME;
    }

    @Override
    protected int getDatabaseVersion() {
        return OrmApplication.DB_VERSION;
    }
}
