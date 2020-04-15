package com.example.mymovieapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button likeButton;
    TextView likeCountView;
    int likeCount = 1;
    boolean likeState = false;

    Button hateButton;
    TextView hateCountView;
    int hateCount = 1;
    boolean hateState = false;

    Button button;
    RatingBar ratingBar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar) ;

        button = (Button) findViewById(R.id.writeCommentButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showCommentWriteActivity();
            }
        });


        likeButton = (Button) findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(likeState){
                    decrLikeCount();
                }else{
                    incrLikeCount();
                    if(hateState) { //hate가 눌려있을 때
                        decrHateCount();
                        hateState = !hateState;
                    }
                }

                likeState = !likeState;
            }
        });

        likeCountView = (TextView) findViewById(R.id.likeCountView);


        hateButton = (Button) findViewById(R.id.hateButton);
        hateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hateState){
                    decrHateCount();
                }else{
                    incrHateCount();
                    if(likeState) { //like가 눌려있을 때
                        decrLikeCount();
                        likeState = !likeState;
                    }
                }

                hateState = !hateState;
            }
        });

        hateCountView = (TextView) findViewById(R.id.hateCountView);

        listView = (ListView)findViewById(R.id.listView);
        CommentAdapter adapter = new CommentAdapter();
        listView.setAdapter(adapter);
    }

    class CommentAdapter extends BaseAdapter{

    }

    public void showCommentWriteActivity(){
        float rating = ratingBar.getRating();

        Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
        intent.putExtra("rating", rating);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 101){
            if(intent != null){
                String contents = intent.getStringExtra("contents");
                //listView.setAdapter(contents);
            }
        }


    }

    public void incrLikeCount(){
        likeCount += 1;
        likeCountView.setText(String.valueOf(likeCount));

        likeButton.setBackgroundResource(R.drawable.ic_thumb_up_selected);
    }

    public void decrLikeCount(){
        likeCount -= 1;
        likeCountView.setText(String.valueOf((likeCount)));

        likeButton.setBackgroundResource(R.drawable.thumbs_up_selector);
    }

    public void incrHateCount(){
        hateCount += 1;
        hateCountView.setText(String.valueOf(hateCount));

        hateButton.setBackgroundResource(R.drawable.ic_thumb_down_selected);
    }

    public void decrHateCount(){
        hateCount -= 1;
        hateCountView.setText(String.valueOf((hateCount)));

        hateButton.setBackgroundResource(R.drawable.thumbs_down_selector);
    }
}
