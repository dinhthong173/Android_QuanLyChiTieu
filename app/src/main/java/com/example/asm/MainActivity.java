package com.example.asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.asm.fragment.ChiFragment;
import com.example.asm.fragment.GTFragment;
import com.example.asm.fragment.TKFragment;
import com.example.asm.fragment.ThuFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);


        // Nạp fragment đầu tiên vào ứng dụng

        if (savedInstanceState == null) {
            TKFragment tk = new TKFragment();
            FragmentManager mn = getSupportFragmentManager();
            mn.beginTransaction()
                    .add(R.id.frame, tk)
                    .commit();
        }
        //Set toolbar lên action bar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.navView);
        //Thực hiện các chức năng cho menu, khi click vào icon menu sẽ show ra
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment frm = null;
                String title = "";
                switch (menuItem.getItemId()) {
                    case R.id.thongKe:
//                        Toast.makeText(MainActivity.this, "Bạn click Thống kê", Toast.LENGTH_SHORT).show();
                        frm = new TKFragment();
                        title = "THỐNG KÊ";
                        break;
                    case R.id.khoanThu:
                        title = "KHOẢN THU";
                        frm = new ThuFragment();
                        break;
                    case R.id.khoanChi:
                        title = "KHOẢN CHI";
                        frm = new ChiFragment();
                        break;
                    case R.id.gioiThieu:
                        title = "GIỚI THIỆU";
                        frm = new GTFragment();
                        break;
                    case R.id.thoat:
                        MainActivity.this.finish();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, frm).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolbar.setTitle(title);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}
