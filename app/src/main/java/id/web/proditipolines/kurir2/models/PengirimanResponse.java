package id.web.proditipolines.kurir2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PengirimanResponse {
    @SerializedName("pesanans")
    @Expose
    public List<Pengiriman> pesanans = null;

    public List<Pengiriman> getPesanans() {
        return pesanans;
    }
}
