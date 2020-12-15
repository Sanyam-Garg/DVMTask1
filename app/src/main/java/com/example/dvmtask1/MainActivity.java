package com.example.dvmtask1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1turn = true;

    private int roundCount;

    private int player1points;
    private int player2points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referring the textViews.
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        //Referring the buttons with a nested loop.
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                //Setting what happens on clicking the 9 buttons.
                buttons[i][j].setOnClickListener(this);
            }
        }

        //Referring the reset button.
        Button buttonReset = findViewById(R.id.button_reset);
        //Setting what happens on clicking the reset button.
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 resetButton();
            }
        });
    }

    //What happens on clicking one of the 9 buttons.
    @Override
    public void onClick(View v) {
        //Checking if the clicked button has empty string or not.
         if(!((Button) v).getText().toString().equals("")){
             return;
         }

         //If it's player 1's turn , then on clicking a button set it's text to X
         if(player1turn){
             ((Button) v).setText("X");
             ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
         }
         // Else, set it to O.
         else{
             ((Button) v).setText("O");
         }
         //Increment round count.
         roundCount++;

         if(checkForWin()){
             if(player1turn){
                 player1Wins();
             } else{
                 player2Wins();
             }
         } else if(roundCount == 9){
             draw();
        } else{
             player1turn = !player1turn;
         }
    }

    //Now see if after clicking a button, has anyone won?
    private boolean checkForWin(){
        String[][] field = new String[3][3];

        //Storing the text of the buttons in a string array.
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //Checking if the strings in a row match.
        for(int i = 0; i < 3; i++){
            if(field[i][0] == field[i][1]
                    && field[i][0] == field[i][2]
                     && !field[i][0].equals("")){
                return true;
            }
        }

        //Checking if the strings in a column match.
        for(int i = 0; i < 3; i++){
            if(field[0][i] == field[1][i]
                    && field[0][i] == field[2][i]
                    && !field[0][i].equals("")){
                return true;
            }
        }

        //Checking if any two diagonals having matching strings.
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                 && !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1])
                && field[0][0].equals(field[2][0])
                && !field[0][0].equals("")){
            return true;
        }

        return false;

    }

    private void player1Wins(){
         player1points++;
        Toast.makeText(this, "Player One Wins!!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins(){
        player2points++;
        Toast.makeText(this, "Player 2 Wins!!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw(){
        player2points++;
        Toast.makeText(this, "Draw!!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        textViewPlayer1.setText("Player 1: " + player1points);
        textViewPlayer2.setText("Player 2: " + player2points);
    }

    private void resetBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1turn = true;
    }

    private void resetButton(){
        player1points = 0;
        player2points = 0;
        player1turn = true;
        Toast.makeText(this, "Game Reset", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1points", player1points);
        outState.putInt("player2points", player2points);
        outState.putBoolean("player1turn", player1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1Points");
        player2points = savedInstanceState.getInt("player2points");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }
}