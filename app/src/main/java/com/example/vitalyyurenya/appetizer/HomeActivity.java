package com.example.vitalyyurenya.appetizer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitalyyurenya.appetizer.api.ProfileApi;
import com.example.vitalyyurenya.appetizer.fragments.ActivityFragment;
import com.example.vitalyyurenya.appetizer.fragments.SearchFragment;
import com.example.vitalyyurenya.appetizer.models.User;
import com.example.vitalyyurenya.appetizer.utils.BottomNavigationViewHelper;
import com.example.vitalyyurenya.appetizer.fragments.FeedFragment;
import com.example.vitalyyurenya.appetizer.fragments.ProfileFragment;
import com.example.vitalyyurenya.appetizer.utils.UrlConverter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private TextView feedTextView;
    private Button logoutButton;
    private BottomNavigationView bottomNav;
    private CircleImageView navHeaderImageView;
    private TextView navHeaderTextView;
    private DrawerLayout feedLayout;

    private AccountManager accountManager;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.i("lol", "HomeActivity, onCreate");

        feedLayout = findViewById(R.id.feed_drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        View navView = navigationView.inflateHeaderView(R.layout.nav_header);
        bottomNav = findViewById(R.id.navigation);

        navHeaderImageView = navView.findViewById(R.id.nav_header_image_view);
        navHeaderTextView = navView.findViewById(R.id.nav_header_text_view);

        BottomNavigationViewHelper.disableShiftMode(bottomNav);

        accountManager = AccountManager.get(getApplicationContext());
        final Account[] account = accountManager.getAccountsByType("com.vityur.appetizer.account");
        String username = account[0].name;
        currentUserId = accountManager.getUserData(account[0], "USER_ID");
        final String authToken = accountManager.peekAuthToken(account[0], "com.vityur.appetizer.account.token");




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProfileApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("x-access-token", authToken);
        Call<User> userCall = profileApi.getOwnProfile(headers);


        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    String profilePhotoUrl = UrlConverter.convert(response.body().getProfilePhotoUrl());
                    String firstAndLastName = response.body().getFirstName() + " " + response.body().getLastName();

                    navHeaderTextView.setText(firstAndLastName);
                    Picasso.get()
                            .load(profilePhotoUrl)
                            .resize(150, 150)
                            .into(navHeaderImageView);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_logout:
                        removeAccount(account[0], HomeActivity.this, new AccountManagerCallback<Bundle>() {
                            @Override
                            public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                                try {
                                    Bundle result = accountManagerFuture.getResult();
                                    boolean success = result.getBoolean(AccountManager.KEY_BOOLEAN_RESULT);

                                    if (success) {
                                        Intent redirIntent = new Intent(HomeActivity.this, LoginActivity.class);
                                        Log.i("lol", "success");
                                        startActivity(redirIntent);
                                        finish();
                                    } else {
                                        Log.i("lol", "Not an exception, but still a failure.");
                                    }
                                } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                                    Log.e("lol", e.getMessage());
                                }
                            }
                        }, null);
                }

                return false;
            }
        });



        final FragmentManager fm = getSupportFragmentManager();




        FeedFragment feedFragment = FeedFragment.newInstance(authToken);
        fm.beginTransaction()
                .add(R.id.feed_frame_layout, feedFragment)
                .commit();



        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        FeedFragment feedFragment = FeedFragment.newInstance(authToken);
                        fm.beginTransaction()
                                .replace(R.id.feed_frame_layout, feedFragment)
                                .commit();

                        break;

                    case R.id.menu_search:
                        //

                        SearchFragment searchFragment = SearchFragment.newInstance();

                        fm.beginTransaction()
                                .replace(R.id.feed_frame_layout, searchFragment)
                                .commit();
                        break;


                    case R.id.menu_profile:
                        //
                        ProfileFragment profileFragment = ProfileFragment.newInstance(authToken);
                        fm.beginTransaction()
                                .replace(R.id.feed_frame_layout, profileFragment)
                                .commit();
                        break;
                }

                return true;
            }
        });
    }

    public void removeAccount(
            @NonNull Account account,
            @NonNull Activity activity,
            @Nullable final AccountManagerCallback<Bundle> callback,
            @Nullable Handler handler
    ) {
        AccountManager accountManager = AccountManager.get(activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            accountManager.removeAccount(account, activity, callback, handler);
        } else {
            accountManager.removeAccount(
                    account,
                    new AccountManagerCallback<Boolean>() {
                        @Override
                        public void run(final AccountManagerFuture<Boolean> accountManagerFuture) {
                            if (callback == null)
                                return;

                            callback.run(new AccountManagerFuture<Bundle>() {
                                @Override
                                public boolean cancel(boolean mayInterruptIfRunning) {
                                    return accountManagerFuture.cancel(mayInterruptIfRunning);
                                }

                                @Override
                                public boolean isCancelled() {
                                    return accountManagerFuture.isCancelled();
                                }

                                @Override
                                public boolean isDone() {
                                    return accountManagerFuture.isDone();
                                }

                                @Override
                                public Bundle getResult() throws OperationCanceledException, IOException, AuthenticatorException {
                                    Bundle result = new Bundle();
                                    result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, accountManagerFuture.getResult());
                                    return result;
                                }

                                @Override
                                public Bundle getResult(long timeout, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
                                    Bundle result = new Bundle();
                                    result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, accountManagerFuture.getResult(timeout, timeUnit));
                                    return result;
                                }
                            });
                        }
                    },
                    handler
            );
        }
    }
}
