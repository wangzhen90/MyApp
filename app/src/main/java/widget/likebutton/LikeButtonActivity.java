package widget.likebutton;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wz.myapp.R;

public class LikeButtonActivity extends AppCompatActivity implements OnLikeListener {



    LikeButton starButton;

    LikeButton likeButton;

    LikeButton thumbButton;

    LikeButton smileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        starButton = (LikeButton) findViewById(R.id.star_button);
        likeButton = (LikeButton) findViewById(R.id.heart_button);
        thumbButton = (LikeButton) findViewById(R.id.thumb_button);
        smileButton = (LikeButton) findViewById(R.id.smile_button);

        starButton.setOnLikeListener(this);
        likeButton.setOnLikeListener(this);
        smileButton.setOnLikeListener(this);
        thumbButton.setOnLikeListener(this);

        thumbButton.setLiked(true);

//        usingCustomIcons();

    }

//    public void usingCustomIcons() {
//
//        //shown when the button is in its default state or when unLiked.
//        smileButton.setUnlikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(this, CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.darker_gray).sizeDp(25).toBitmap()));
//
//        //shown when the button is liked!
//        smileButton.setLikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(this, CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.holo_purple).sizeDp(25).toBitmap()));
//    }



    @Override
    public void liked(LikeButton likeButton) {
        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Toast.makeText(this, "Disliked!", Toast.LENGTH_SHORT).show();
    }
}
