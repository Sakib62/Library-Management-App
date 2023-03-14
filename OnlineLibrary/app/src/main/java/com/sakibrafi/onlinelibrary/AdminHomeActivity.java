package com.sakibrafi.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

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

        Button returnBook = (Button) findViewById(R.id.returnBook);
        returnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReturnBookActivity();
            }
        });

        Button collectFine = (Button) findViewById(R.id.collectFine);
        collectFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCollectFineActivity();
            }
        });

        Button logOut = (Button) findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openMainActivity();
                finish();
            }
        });
    }

    public void openSearchBookActivity() {
        Intent intent = new Intent(this, SearchBookActivity.class);
        startActivity(intent);
    }

    public void openAddBookActivity() {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }

    public void openRemoveBookActivity() {
        Intent intent = new Intent(this, RemoveBookActivity.class);
        startActivity(intent);
    }

    public void openIssueBookActivity() {
        Intent intent = new Intent(this, IssueBookActivity.class);
        startActivity(intent);
    }

    public void openReturnBookActivity() {
        Intent intent = new Intent(this, ReturnBookActivity.class);
        startActivity(intent);
    }

    public void openCollectFineActivity() {
        Intent intent = new Intent(this, CollectFineActivity.class);
        startActivity(intent);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}