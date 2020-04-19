package co.edu.udea.compumovil.gr01_20192.lab4.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import co.edu.udea.compumovil.gr01_20192.lab4.DAO.UserDao;
import co.edu.udea.compumovil.gr01_20192.lab4.Entities.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDB INSTANCE;

    public static UserDB getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), UserDB.class, "User").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}


