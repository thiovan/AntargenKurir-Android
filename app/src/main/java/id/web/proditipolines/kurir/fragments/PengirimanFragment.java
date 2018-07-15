package id.web.proditipolines.kurir.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import id.web.proditipolines.kurir.R;
import id.web.proditipolines.kurir.adapters.PengirimanAdapter;
import id.web.proditipolines.kurir.models.Pengiriman;
import id.web.proditipolines.kurir.models.PengirimanResponse;
import id.web.proditipolines.kurir.network.APIService;
import id.web.proditipolines.kurir.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PengirimanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    public PengirimanFragment() {
        // Required empty public constructor
    }
    
    View view;
    RecyclerView recyclerView;
    PengirimanAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pengiriman, container, false);
        getActivity().setTitle("Pengiriman");

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        //Deklarasi dan konfigurasi recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_pengiriman);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        SharedPreferences preferences = getActivity().getSharedPreferences("SESSION",MODE_PRIVATE);
        loadPengiriman(Integer.parseInt(preferences.getString("ID", "")));
        
        return view;
    }

    private void loadPengiriman(int id) {
        swipeRefreshLayout.setRefreshing(true);
        APIService service = RetrofitClient.getClient().create(APIService.class);
        Call<PengirimanResponse> userCall = service.getPengiriman(id);

        userCall.enqueue(new Callback<PengirimanResponse>() {
            @Override
            public void onResponse(Call<PengirimanResponse> call, Response<PengirimanResponse> response) {
                if (response.isSuccessful() && isAdded()){
                    swipeRefreshLayout.setRefreshing(false);
                    List<Pengiriman> pengiriman = response.body().getPesanans();
                    adapter = new PengirimanAdapter(pengiriman, PengirimanFragment.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PengirimanResponse> call, Throwable t) {
                if (isAdded()) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Gagal Terhubung Ke Server", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        SharedPreferences preferences = getActivity().getSharedPreferences("SESSION",MODE_PRIVATE);
        loadPengiriman(Integer.parseInt(preferences.getString("ID", "")));
    }
}
