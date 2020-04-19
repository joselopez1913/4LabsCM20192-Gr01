package co.edu.udea.compumovil.gr01_20192.lab4.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.edu.udea.compumovil.gr01_20192.lab4.Entities.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User where user LIKE :user AND password LIKE :password")
    User consulterUser(String user, String password);

    @Query("SELECT * FROM user where email LIKE  :email")
    User findByEmail(String email);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}