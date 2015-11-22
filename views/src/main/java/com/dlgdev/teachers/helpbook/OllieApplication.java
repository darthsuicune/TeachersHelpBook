package com.dlgdev.teachers.helpbook;

import android.app.Application;

import com.dlgdev.teachers.helpbook.domain.db.TeachersProvider;

import ollie.Ollie;

public class OllieApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Ollie.with(this)
                .setName(TeachersProvider.DB_NAME)
                .setVersion(TeachersProvider.DB_VERSION)
				.setCacheSize(Ollie.DEFAULT_CACHE_SIZE)
				.setLogLevel(Ollie.LogLevel.FULL)
                .init();
    }
}
