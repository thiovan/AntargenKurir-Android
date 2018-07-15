package id.web.proditipolines.kurir.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 6/4/2018.
 */

public class Login {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("pelanggan")
    @Expose
    public Integer pelanggan;
    @SerializedName("kurir")
    @Expose
    public Integer kurir;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("pesan")
    @Expose
    public String pesan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Integer pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Integer getKurir() {
        return kurir;
    }

    public void setKurir(Integer kurir) {
        this.kurir = kurir;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
