package com.dlgdev.teachers.helpbook.domain.db;

import ollie.OllieProvider;

public class TeachersProvider extends OllieProvider {
    public static final String DB_NAME = "teachersHelpbook.db";
    public static final int DB_VERSION = 1;
    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    protected int getDatabaseVersion() {
        return DB_VERSION;
    }
}
