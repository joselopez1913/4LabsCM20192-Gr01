package co.edu.udea.compumovil.gr01_20192.lab4.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Poi {

    private int pid;
    private String namep;
    private String description;
    private String point;
    private String image;

    public Poi(int pid, String namep, String description, String point, String image) {
        this.pid = pid;
        this.namep = namep;
        this.description = description;
        this.point = point;
        this.image = image;
    }

    public Poi(int pid, String namep, String description, String point) {
        this.pid = pid;
        this.namep = namep;
        this.description = description;
        this.point = point;
    }

    public Poi() {
    }

    public Poi(String namepoi, String descpoi, String pointpoi) {
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getNamep() {
        return namep;
    }

    public void setNamep(String namep) {
        this.namep = namep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
