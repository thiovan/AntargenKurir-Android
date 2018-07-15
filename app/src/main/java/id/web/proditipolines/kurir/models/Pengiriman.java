package id.web.proditipolines.kurir.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pengiriman {
    @SerializedName("kode_pesanan")
    @Expose
    public Integer kodePesanan;
    @SerializedName("id_user")
    @Expose
    public String idUser;
    @SerializedName("nama_pelanggan")
    @Expose
    public String namaPelanggan;
    @SerializedName("id_kurir")
    @Expose
    public String idKurir;
    @SerializedName("nama_kurir")
    @Expose
    public String namaKurir;
    @SerializedName("telepon")
    @Expose
    public String telepon;
    @SerializedName("alamat")
    @Expose
    public String alamat;
    @SerializedName("id_pesanan")
    @Expose
    public List<Integer> idPesanan = null;
    @SerializedName("nama_produk")
    @Expose
    public List<String> namaProduk = null;
    @SerializedName("total")
    @Expose
    public List<String> total = null;
    @SerializedName("jumlah")
    @Expose
    public List<Integer> jumlah = null;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    public Integer getKodePesanan() {
        return kodePesanan;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public String getIdKurir() {
        return idKurir;
    }

    public String getNamaKurir() {
        return namaKurir;
    }

    public List<Integer> getIdPesanan() {
        return idPesanan;
    }

    public List<String> getNamaProduk() {
        return namaProduk;
    }

    public List<String> getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public List<Integer> getJumlah() {
        return jumlah;
    }

    public String getTelepon() {
        return telepon;
    }

    public String getAlamat() {
        return alamat;
    }
}
