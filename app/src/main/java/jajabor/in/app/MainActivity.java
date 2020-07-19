package jajabor.in.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tag,from,publisher;
    Animation top,bottom;
    ImageView tite;

    int SPLASH_TIME = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tite = findViewById(R.id.logo);
//        tag = findViewById(R.id.tagline);
//        from = findViewById(R.id.fromview);
//        publisher = findViewById(R.id.owner);

        top = AnimationUtils.loadAnimation(MainActivity.this,R.anim.top_animation );
//        bottom = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_animation );
        tite.setAnimation(top);
//        tag.setAnimation(bottom);
//        from.setAnimation(bottom);
//        publisher.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(MainActivity.this,login.class);
//                startActivity(intent);
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                finish();
            }
        },SPLASH_TIME);
    }
}