package com.example.vitalyyurenya.appetizer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitalyyurenya.appetizer.utils.BottomNavigationViewHelper;
import com.example.vitalyyurenya.appetizer.fragments.FeedFragment;
import com.example.vitalyyurenya.appetizer.fragments.ProfileFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    private TextView feedTextView;
    private Button logoutButton;
    private BottomNavigationView bottomNav;
    private ImageView navHeaderImageView;
    private TextView navHeaderTextView;
    private DrawerLayout feedLayout;

    private AccountManager accountManager;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */

        // feedTextView = findViewById(R.id.feed_tv);
        // logoutButton = findViewById(R.id.feed_logout_btn);

        feedLayout = findViewById(R.id.feed_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navView = navigationView.inflateHeaderView(R.layout.nav_header);

        navHeaderImageView = (ImageView) navView.findViewById(R.id.nav_header_image_view);
        navHeaderTextView = (TextView) navView.findViewById(R.id.nav_header_text_view);




        bottomNav = findViewById(R.id.navigation);

        /*
        Glide.with(getApplicationContext())
                .load("https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg")
                .into(navHeaderImageView);*/

        navHeaderTextView.setText("hello");

        Picasso.get()
                .load("https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg")
                .resize(200, 200)
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop().into(navHeaderImageView);

        /*
        Glide.with(this)
                .load("https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg")
                .into(navHeaderImageView);*/



        BottomNavigationViewHelper.disableShiftMode(bottomNav);

        accountManager = AccountManager.get(getApplicationContext());



        final Account[] account = accountManager.getAccountsByType("com.vityur.appetizer.account");

        String username = account[0].name;

        userId = accountManager.getUserData(account[0], "USER_ID");
        final String authToken = accountManager.peekAuthToken(account[0], "com.vityur.appetizer.account.token");


        final FragmentManager fm = getSupportFragmentManager();

        FeedFragment feedFragment = FeedFragment.newInstance(authToken);
        fm.beginTransaction()
                .add(R.id.feed_frame_layout, feedFragment)
                .commit();



        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String msg = "Default";
                // MyFragment myFragment;


                switch (item.getItemId()) {
                    case R.id.menu_home:
                        FeedFragment feedFragment = FeedFragment.newInstance(authToken);
                        fm.beginTransaction()
                                .replace(R.id.feed_frame_layout, feedFragment)
                                .commit();

                        break;

                    case R.id.menu_trending:
                        //
                        msg = "Trending";
                        // myFragment = MyFragment.newInstance(msg);
                        /*
                        fm.beginTransaction()
                                .replace(R.id.feed_frame_layout, myFragment)
                                .commit();*/
                        break;

                    case R.id.menu_activity:
                        //
                        msg = "Activity";
                        /*

                        fm.beginTransaction()
                                .replace(R.id.feed_frame_layout, myFragment)
                                .commit();
                        */
                        break;

                    case R.id.menu_profile:
                        //
                        ProfileFragment profileFragment = ProfileFragment.newInstance(authToken);
                        fm.beginTransaction()
                                .replace(R.id.feed_frame_layout, profileFragment)
                                .commit();
                        break;
                }
                /*
                MyFragment myFragment =  MyFragment.newInstance(msg);

                fm.beginTransaction()
                        .replace(R.id.feed_frame_layout, myFragment)
                        .commit();
                */


                return true;
            }
        });





        /*
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        */
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
