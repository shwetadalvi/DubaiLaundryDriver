package com.dldriver.driver.room;

import android.content.Context;

import com.dldriver.driver.models.Address;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters(DbTypeConverter.class)
@Database(entities = {Address.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getAppDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-db")
                            .build();
        }
        return INSTANCE;
    }


    public void destroyInstance() {
        INSTANCE = null;
    }
    public abstract AddressDao mAddressDao();
}
