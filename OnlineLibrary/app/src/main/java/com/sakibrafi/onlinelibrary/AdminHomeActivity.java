package com.sakibrafi.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchBookActivity();
            }
        });


        Button addBook = (Button) findViewById(R.id.addBook);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddBookActivity();
            }
        });
    }

    public void openSearchBookActivity()
    {
        Intent intent = new Intent(this, SearchBookActivity.class);
        startActivity(intent);
    }

    public void openAddBookActivity()
    {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }
}