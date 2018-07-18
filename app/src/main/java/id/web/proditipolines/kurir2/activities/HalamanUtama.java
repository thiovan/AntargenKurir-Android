package id.web.proditipolines.kurir2.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.web.proditipolines.kurir2.R;
import id.web.proditipolines.kurir2.fragments.HistoriFragment;
import id.web.proditipolines.kurir2.fragments.PengirimanFragment;
import id.web.proditipolines.kurir2.services.RefreshFirebaseTokenService;

public class HalamanUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check GPS is enabled
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Mohon nyalakan GPS anda terlebih dahulu", Toast.LENGTH_LONG).show();
            finish();
        }

        //Ambil session login dari shared preference
        SharedPreferences preferences = getSharedPreferences("SESSION",MODE_PRIVATE);
        if(preferences.getString("TOPIC", "").equals("")){
            Intent intent = new Intent(getApplicationContext(), RefreshFirebaseTokenService.class);
            startService(intent);
            Log.d("asdf", "if exec");
        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_pengiriman);

        //Ubah TextView header sesuai nama dan email di session login
        View headerView = navigationView.getHeaderView(0);
        TextView headerNama = (TextView) headerView.findViewById(R.id.header_nama);
        TextView headerEmail = (TextView) headerView.findViewById(R.id.header_email);
        headerNama.setText(preferences.getString("NAME", ""));
        headerEmail.setText(preferences.getString("EMAIL", ""));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.halaman_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return false;
    }

    public void displaySelectedScreen(int itemId){
        //creating fragment object
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (itemId) {
            case R.id.nav_pengiriman:
                fragment = new PengirimanFragment();
                break;
            case R.id.nav_histori:
                fragment = new HistoriFragment();
                break;
            case R.id.nav_logout:
                //Hapus session login dari shared preference
                SharedPreferences.Editor editor = getSharedPreferences("SESSION", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                //Intent untuk pindah activity ke LoginActivity
                Intent intent = new Intent(HalamanUtama.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode != PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Mohon aktifkan GPS sebelum menggunakan aplikasi ini.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
