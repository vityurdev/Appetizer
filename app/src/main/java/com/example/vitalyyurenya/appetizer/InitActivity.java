package com.example.vitalyyurenya.appetizer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InitActivity extends AppCompatActivity {

    private Intent forwardIntent;
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountManager = AccountManager.get(getApplicationContext());
        Account[] account = accountManager.getAccountsByType("com.vityur.appetizer.account");

        if (account.length < 1) {
            forwardIntent = new Intent(InitActivity.this, LoginActivity.class);
        } else {
            forwardIntent = new Intent(InitActivity.this, HomeActivity.class);
        }

        startActivity(forwardIntent);
        finish();

    }
}
