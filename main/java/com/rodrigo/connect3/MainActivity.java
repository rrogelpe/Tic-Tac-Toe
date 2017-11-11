package com.rodrigo.connect3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    boolean isPlayerOneTurn = true;
    boolean isGameOver = false;
    int movesLeft = 9;
    int[] state = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int[][] winningPositions = new int[][] {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int rScore = 0;
    int yScore = 0;

    public boolean checkForWinners(int[][] w, int[] s){
        for (int[] row: w){
            if((s[row[0]] == 1 || s[row[0]] == 0) && s[row[0]] == s[row[1]] && s[row[1]] == s[row[2]]) {
                return true;
            }
        }
        return false;
    }

    public void resetGame(View view){
        Button button = (Button) findViewById(R.id.button);
        button.setVisibility(View.GONE);
        Arrays.fill(state,-1);

        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        for (int i=0; i<grid.getChildCount(); i++){
            ((ImageView) grid.getChildAt(i)).setImageDrawable(null);
        }

        TextView text = (TextView) findViewById(R.id.winner);

        //text.setBackgroundColor(0x12f1bd);
        text.setText("");
        text.setVisibility(View.INVISIBLE);
        isGameOver = false;
        movesLeft = 9;
    }

    public void drop(View view){
        Button button = (Button) findViewById(R.id.button);
        TextView winnerPost = (TextView) findViewById(R.id.winner);
        if (!isGameOver) {
            ImageView img = (ImageView) view;
            String position = img.getTag().toString();
            int index = Integer.parseInt(position);

            if (img.getDrawable() == null){
                img.setTranslationY(-500f);

                if (isPlayerOneTurn){
                    img.setImageResource(R.drawable.red);
                    state[index] = 1;
                }else{
                    img.setImageResource(R.drawable.yellow);
                    state[index] = 0;
                }
                img.animate().translationYBy(500f).setDuration(500);
                movesLeft--;
                //Log.d("id",Integer.toString(movesLeft));
            }

            if (checkForWinners(winningPositions, state)){
                String player;
                if (isPlayerOneTurn){
                    player = "Red";
                }else{
                    player = "Yellow";
                }

                if (isPlayerOneTurn){
                    TextView redScore = (TextView) findViewById(R.id.redScore);
                    redScore.setText("R: " + ++rScore);
                    winnerPost.setBackgroundColor(Color.parseColor("#FDFE101F"));
                }else{
                    TextView redScore = (TextView) findViewById(R.id.yellowScore);
                    redScore.setText("Y: " + ++yScore);
                    winnerPost.setBackgroundColor(Color.parseColor("#FDFEF610"));
                }
                winnerPost.setVisibility(View.VISIBLE);
                winnerPost.setText(player + " Won!");
                button.setVisibility(View.VISIBLE);
                isGameOver = true;
            }

            else if(movesLeft == 0){
                //Log.d("endgame:","tie");
                winnerPost.setBackgroundColor(Color.parseColor("#12f1bd"));
                winnerPost.setVisibility(View.VISIBLE);
                winnerPost.setText("It's a Draw!");
                button.setVisibility(View.VISIBLE);
                isGameOver = true;
            }

            isPlayerOneTurn = !isPlayerOneTurn;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
