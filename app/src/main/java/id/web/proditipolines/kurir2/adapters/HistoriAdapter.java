package id.web.proditipolines.kurir2.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import id.web.proditipolines.kurir2.R;
import id.web.proditipolines.kurir2.fragments.HistoriFragment;
import id.web.proditipolines.kurir2.models.Pengiriman;
import id.web.proditipolines.kurir2.network.APIService;
import id.web.proditipolines.kurir2.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoriAdapter extends RecyclerView.Adapter<HistoriAdapter.MyViewHolder> {
    private List<Pengiriman> historiList;
    private HistoriFragment fragment;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvKode;
        TextView mTvKurir;
        TextView mTvTanggal;
        TextView mTvProduk;
        TextView mTvJumlah;
        TextView mTvStatus;
        LinearLayout container;

        public MyViewHolder(View view) {
            super(view);
            mTvKode = (TextView) itemView.findViewById(R.id.tv_kode);
            mTvKurir = (TextView) itemView.findViewById(R.id.tv_kurir);
            mTvTanggal = (TextView) itemView.findViewById(R.id.tv_tanggal);
            mTvProduk = (TextView) itemView.findViewById(R.id.tv_produk);
            mTvJumlah = (TextView) itemView.findViewById(R.id.tv_jumlah);
            mTvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            container = (LinearLayout) itemView.findViewById(R.id.container);
        }

    }

    public HistoriAdapter(List<Pengiriman> historiList, HistoriFragment fragment) {
        this.historiList = historiList;
        this.fragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pengiriman_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Pengiriman pengiriman = historiList.get(position);
        final Context mContext = holder.mTvKode.getContext();

        holder.mTvKode.setText("Pengiriman #" + pengiriman.getKodePesanan());
        holder.mTvKurir.setText(pengiriman.getNamaPelanggan());
        holder.mTvTanggal.setText(pengiriman.getUpdatedAt().substring(0, 10));
        holder.mTvProduk.setText("");
        String valueProduk = "";
        for (int i = 0; i < pengiriman.getNamaProduk().size(); i++) {
            if (i == pengiriman.getNamaProduk().size() - 1){
                valueProduk = valueProduk + "•" + pengiriman.getNamaProduk().get(i);
            } else {
                valueProduk = valueProduk + "•" + pengiriman.getNamaProduk().get(i) + "\n";
            }
        }
        holder.mTvProduk.setText(valueProduk);
        String valueJumlah = "";
        for (int i = 0; i < pengiriman.getJumlah().size(); i++) {
            if (i == pengiriman.getTotal().size() - 1){
                valueJumlah = valueJumlah + "Qty: " + pengiriman.getJumlah().get(i);
            } else {
                valueJumlah = valueJumlah + "Qty: " + pengiriman.getJumlah().get(i) + "\n";
            }
        }
        holder.mTvJumlah.setText(valueJumlah);
        if (pengiriman.getStatus().equals("0")){
            holder.mTvStatus.setText("Sedang Diproses");
        } else if (pengiriman.getStatus().equals("1")){
            holder.mTvStatus.setText("Pickup Barang");
        } else if (pengiriman.getStatus().equals("2")){
            holder.mTvStatus.setText("Dalam Pengiriman");
        } else {
            holder.mTvStatus.setText("Telah Diterima");
        }

        final String finalValueProduk = valueProduk;
        final String finalValueJumlah = valueJumlah;
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Membuat dialog dengan kustom layout
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_pengiriman);

                TextView nama = (TextView) dialog.findViewById(R.id.nama);
                TextView telepon = (TextView) dialog.findViewById(R.id.telepon);
                TextView alamat = (TextView) dialog.findViewById(R.id.alamat);
                TextView produk = (TextView) dialog.findViewById(R.id.produk);
                TextView jumlah = (TextView) dialog.findViewById(R.id.jumlah);
                TextView tagihan = (TextView) dialog.findViewById(R.id.tagihan);
                Button message = (Button) dialog.findViewById(R.id.message);
                Button call = (Button) dialog.findViewById(R.id.call);
                final Button aksi = (Button) dialog.findViewById(R.id.btn_aksi);

                nama.setText(": " + pengiriman.getNamaPelanggan());
                telepon.setText(": " + pengiriman.getTelepon());
                alamat.setText(": " + pengiriman.getAlamat());
                produk.setText(finalValueProduk);
                jumlah.setText(finalValueJumlah);
                Integer totalTagihan = 0;
                for (int i = 0; i < pengiriman.getTotal().size(); i++) {
                    totalTagihan = totalTagihan + Integer.parseInt(pengiriman.getTotal().get(i));
                }
                tagihan.setText("Total Tagihan: Rp. " + String.valueOf(totalTagihan));

                message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setData(Uri.parse("smsto:" + pengiriman.getTelepon()));
                        mContext.startActivity(smsIntent);
                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pengiriman.getTelepon(), null));
                        mContext.startActivity(intent);
                    }
                });

                aksi.setVisibility(View.GONE);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historiList.size();
    }

    public void updateStatusPesanan(final Dialog dialog, int kode, String status){
        APIService service = RetrofitClient.getClient().create(APIService.class);
        Call<String> userCall = service.updateStatusPesanan(kode, status);

        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                fragment.onRefresh();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
                fragment.onRefresh();
            }
        });
    }

}
