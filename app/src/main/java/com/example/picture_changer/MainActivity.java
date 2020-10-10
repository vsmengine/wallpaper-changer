package com.example.picture_changer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView display;
    Button button;
    public int i, toPhone;
    TextView number1, number2, operator;
    EditText input;
    public int rdmIndex, rdmNum1, rdmNum2, userAns, actAns;
    public String rdmOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] operationArr = {"+", "-", "/", "*"};

        final ImageView display = (ImageView)findViewById(R.id.main_picture);
        Button button = (Button)findViewById(R.id.button_1);
        final TextView number1 = (TextView)findViewById(R.id.textView1);
        final TextView operator = (TextView)findViewById(R.id.textView2);
        final TextView number2 = (TextView)findViewById(R.id.textView3);
        final EditText input = (EditText) findViewById(R.id.edit_text1);

        Context context = getApplicationContext();
        CharSequence text = "Your answer is wrong! Please try again.";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration);


        for (i = 1; i <= 13; i++){
            final ImageView iv = new ImageView(this);
            String a = "smpic" + Integer.toString(i);
            int id = getResources().getIdentifier(a,"drawable", getPackageName());

            iv.setImageResource(id);
            iv.setId(i);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            lp.setMargins(15, 30, 15, 30);
            iv.setLayoutParams(lp);

            LinearLayout liner = (LinearLayout)findViewById(R.id.linear_layout);
            liner.addView(iv);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Random arrIndex = new Random();
                    rdmIndex = arrIndex.nextInt(operationArr.length);
                    rdmNum1 = (int)(Math.random()*100 + rdmNum2);
                    rdmNum2 = (int)(Math.random()*10 + 1);
                    number1.setText(Integer.toString(rdmNum1));
                    number2.setText(Integer.toString(rdmNum2));
                    operator.setText(operationArr[rdmIndex]);

                    int ide = iv.getId();
                    String b = "pic" + Integer.toString(ide);
                    int id = getResources().getIdentifier(b, "drawable", getPackageName());
                    display.setImageResource(id);
                    toPhone = id;
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userAns = Integer.parseInt(input.getText().toString());
                    rdmOperator = operationArr[rdmIndex];
                    switch (rdmOperator) {
                        case "+": actAns = rdmNum1 + rdmNum2;;
                            break;
                        case "-": actAns = rdmNum1 - rdmNum2;;
                            break;
                        case "*": actAns = rdmNum1 * rdmNum2;;
                            break;
                        case "/": actAns = rdmNum1 / rdmNum2;;
                            break;
                    }

                    if(userAns == actAns) {
                        InputStream is = getResources().openRawResource(toPhone);
                        Bitmap picture = BitmapFactory.decodeStream(is);
                        WallpaperManager myWall = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            myWall.setBitmap(picture);
                            input.setText(null);
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        toast.show();
                    }

//
                }
            });
        }

    }
}