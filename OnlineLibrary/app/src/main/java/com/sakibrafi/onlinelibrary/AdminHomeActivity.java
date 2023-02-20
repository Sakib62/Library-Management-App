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

        Button removeBook = (Button) findViewById(R.id.removeBook);
        removeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRemoveBookActivity();
            }
        });

        Button issueBook = (Button) findViewById(R.id.issueBook);
        issueBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIssueBookActivity();
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

    public void openRemoveBookActivity()
    {
        Intent intent = new Intent(this, RemoveBookActivity.class);
        startActivity(intent);
    }

    public void openIssueBookActivity()
    {
        Intent intent = new Intent(this, IssueBookActivity.class);
        startActivity(intent);
    }
}