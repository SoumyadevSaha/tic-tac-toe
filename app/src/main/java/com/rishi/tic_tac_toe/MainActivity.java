package com.rishi.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0 -> '0'
    // 1 -> 'X'
    int activePlayer = 0;
    int [] gameState = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int [][] win_pos = {{1,2,3}, {4,5,6}, {7,8,9}, // horizontal
                        {1,4,7}, {2,5,8}, {3,6,9}, // vertical
                        {1,5,9}, {3,5,7}}; // cris-cross.
    int gameActive = 1;
    int count  = 0;
    MediaPlayer player;
    public void win_star (int pos[])
    {
        ImageView win;
        for (int i : pos)
        {
            if (i == 1)
                win = findViewById(R.id.imageView1);
            else if (i == 2)
                win = findViewById(R.id.imageView2);
            else if (i == 3)
                win = findViewById(R.id.imageView3);
            else if (i == 4)
                win = findViewById(R.id.imageView4);
            else if (i == 5)
                win = findViewById(R.id.imageView5);
            else if (i == 6)
                win = findViewById(R.id.imageView6);
            else if (i == 7)
                win = findViewById(R.id.imageView7);
            else if (i == 8)
                win = findViewById(R.id.imageView8);
            else
                win = findViewById(R.id.imageView9);
            win.setTranslationY(-100f);
            win.setImageResource(R.drawable.star);
            win.animate().translationYBy(100f).setDuration(400);
        }
    }
    public void func (View v)
    {
        Toast.makeText(this, "Please Tap on a Grid Tile.", Toast.LENGTH_SHORT).show();
    }
    public void reset()
    {
        count = 0;
        gameActive = 2;
        activePlayer = 0;
        for (int i = 0; i< 9; i++)
            gameState[i] = -1;
        TextView stat = findViewById(R.id.textView);
        stat.setText("Tic-tac-toe by Soumya.");
        stat = findViewById(R.id.textView2);
        stat.setText("0\'s turn to Play.");
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView9)).setImageResource(0);
        if(player.isPlaying() == true)
        {
            player.stop();
            player = MediaPlayer.create(this, R.raw.tap);
        }
    }
    public void Taptap (View V)
    {
        if (gameActive == 0)
            reset();
        else
            player.start();
        ImageView img = (ImageView) V;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        if (gameState[tappedImage - 1] == -1 && gameActive == 1)
        {
            gameState[tappedImage - 1] = activePlayer;
            img.setTranslationY(-100f);
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.o1);
                activePlayer = 1;
                TextView stat = findViewById(R.id.textView2);
                stat.setText("X\'s turn to Play.");
            }
            else {
                img.setImageResource(R.drawable.x1);
                activePlayer = 0;
                TextView stat = findViewById(R.id.textView2);
                stat.setText("0\'s turn to Play.");
            }
            img.animate().translationYBy(100f).setDuration(400); // The picture will come from -1000f to 0 in 400 milli seconds.
            count ++;
        }
        if (gameActive == 2)
            gameActive = 1;
        // Check if won ->
        for (int [] pos : win_pos)
        {
            int k = gameState[pos[0] - 1];
            if (k == gameState[pos[1] - 1] && k == gameState[pos[2] - 1] && k != -1) {
                if(player.isPlaying() == true)
                {
                    player.stop();
                    player = MediaPlayer.create(this, R.raw.win);
                    player.start();
                }
                TextView stat1 = findViewById(R.id.textView2);
                stat1.setText("Tap To play again.");
                gameActive = 0;
                count = 0;
                TextView stat = findViewById(R.id.textView);
                if (k == 1)
                    stat.setText("Congrats, X has won !!!");
                else
                    stat.setText("Congrats, 0 has won !!!");

                // Showing star indicating winning posn ->
                win_star(pos);

                // Clearing the window on winning.
                break;
            }
        }
        if (count == 9)
        {
            if(player.isPlaying() == true)
            {
                player.stop();
                player = MediaPlayer.create(this, R.raw.lose);
                player.start();
            }
            TextView stat1 = findViewById(R.id.textView2);
            stat1.setText("Tap To play again.");
            TextView stat = findViewById(R.id.textView);
            stat.setText("Oops, It's a Draw :/ !!!");
            gameActive = 0;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this, R.raw.tap);
    }
}