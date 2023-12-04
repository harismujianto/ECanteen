package com.jungkatjungkit.ecanteen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jungkatjungkit.ecanteen.R;
import com.jungkatjungkit.ecanteen.config.menu.Menu;
import com.jungkatjungkit.ecanteen.config.menu.MenuAdapter;
import com.jungkatjungkit.ecanteen.config.menu.MenuApiService;
import com.jungkatjungkit.ecanteen.config.menu.MenuResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutletActivity extends AppCompatActivity implements MenuAdapter.OnMenuClickListener {

    private TextView outletNameTextView;
    private RecyclerView menuRecyclerView;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);

        outletNameTextView = findViewById(R.id.namaOutlet);
        menuRecyclerView = findViewById(R.id.menuRecyclerView);

        // Set up RecyclerView for the menu
        menuAdapter = new MenuAdapter(new ArrayList<>(), this);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.setAdapter(menuAdapter);

        // Retrieve outlet details from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("OUTLET_ID") && intent.hasExtra("OUTLET_NAME")) {
            int outletId = intent.getIntExtra("OUTLET_ID", -1);
            String outletName = intent.getStringExtra("OUTLET_NAME");

            outletNameTextView.setText(outletName);
            Log.d("outletid", "outletId: " + outletId);
            // Fetch menu data for the specific outlet
            fetchMenuData(outletId);
        }
    }

    private void fetchMenuData(int outletId) {
        MenuApiService menuApiService = Menu.getMenuApiService();
        Call<List<MenuResponse>> call = menuApiService.getMenu(outletId);

        call.enqueue(new Callback<List<MenuResponse>>() {
            @Override
            public void onResponse(Call<List<MenuResponse>> call, Response<List<MenuResponse>> response) {
                if (response.isSuccessful()) {
                    List<MenuResponse> menuData = response.body();
                    Log.d("TestLog", "This is a test log");
                    Log.d("menudata", "Response Body: " + response.body());
                    menuAdapter.updateData(menuData);
                    Log.d("menudata", "menuData: " + menuData.size());
                } else {
                    // Handle unsuccessful response
                    Log.e("OutletActivity", "Failed to fetch menu data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<MenuResponse>> call, Throwable t) {
                // Handle network call failure
                Log.e("OutletActivity", "Network call failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void onMenuClick(MenuResponse menu) {
        // Handle menu item click if needed
        // For example, open a new activity or show additional information
    }
}
