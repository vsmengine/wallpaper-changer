package com.example.picture_changer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView display;
    Button button;
    public int i, toPhone, rdmIndex, rdmNum1, rdmNum2, userAns, actAns;
    TextView number1, number2, operator, operator2;
    EditText input;
    public String rdmOperator;
    public String[] operationArr = {"+", "-", "/", "*"};

    String SUCCESS_MSG = "Your wallpaper is successfully changed!.";
    String ERROR_MSG = "Your answer is wrong! Please try again.";
    String EXCEPTION_MSG = "Something went wrong! Please try again.";
    String VALIDATION_MSG = "Please enter valid number.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView display = (ImageView)findViewById(R.id.main_picture);
        final Button button = (Button)findViewById(R.id.button_1);
        final TextView number1 = (TextView)findViewById(R.id.textView1);
        final TextView operator = (TextView)findViewById(R.id.textView2);
        final TextView number2 = (TextView)findViewById(R.id.textView3);
        final TextView operator2 = (TextView)findViewById(R.id.textView4);
        final EditText input = (EditText) findViewById(R.id.edit_text1);

        button.setEnabled(false);
        input.setVisibility(View.INVISIBLE);
        operator2.setVisibility(View.INVISIBLE);

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
                    setRdmVal();
                    number1.setText(Integer.toString(rdmNum1));
                    number2.setText(Integer.toString(rdmNum2));
                    operator.setText(operationArr[rdmIndex]);
                    operator2.setVisibility(View.VISIBLE);
                    input.setVisibility(View.VISIBLE);
                    input.setEnabled(true);
                    button.setEnabled(true);

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
                if(!input.getText().toString().isEmpty()) {
                    rdmOperator = operationArr[rdmIndex];
                    userAns = Integer.parseInt(input.getText().toString());
                    actAns = calActAns();

                    if(userAns == actAns) {
                        InputStream is = getResources().openRawResource(toPhone);
                        Bitmap picture = BitmapFactory.decodeStream(is);
                        WallpaperManager myWall = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            myWall.setBitmap(picture);
                            toastMsg(SUCCESS_MSG);
                            input.setText(null);
                            button.setEnabled(false);
                            input.setEnabled(false);
                        } catch(IOException e) {
                            e.printStackTrace();
                            toastMsg(EXCEPTION_MSG);
                        }
                    } else {
                        toastMsg(ERROR_MSG);
                        setRdmVal();
                        input.setText(null);
                        number1.setText(Integer.toString(rdmNum1));
                        number2.setText(Integer.toString(rdmNum2));
                        operator.setText(operationArr[rdmIndex]);
                    }
                } else {
                    toastMsg(VALIDATION_MSG);
                }
                }
            });

        }
    }

    public void setRdmVal() {
        Random arrIndex = new Random();
        rdmIndex = arrIndex.nextInt(operationArr.length);
        rdmNum2 = (int)(Math.random()*10 + 1);
        rdmNum1 = (int)(Math.random()*100 + rdmNum2);
    }

    public void initMathLogic() {
        number1.setText(Integer.toString(rdmNum1));
        number2.setText(Integer.toString(rdmNum2));
        operator.setText(operationArr[rdmIndex]);
    }

    public int calActAns() {
        int calAns = 0;
        switch (rdmOperator) {
            case "+": calAns = rdmNum1 + rdmNum2;
                break;
            case "-": calAns = rdmNum1 - rdmNum2;
                break;
            case "*": calAns = rdmNum1 * rdmNum2;
                break;
            case "/": calAns = rdmNum1 / rdmNum2;
                break;
        }
        return calAns;
    }

    public void toastMsg(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void setInvisible() {
        number1.setVisibility(View.INVISIBLE);
        number2.setVisibility(View.INVISIBLE);
        operator.setVisibility(View.INVISIBLE);
        operator2.setVisibility(View.INVISIBLE);
        input.setVisibility(View.INVISIBLE);
    }

    public void setVisible() {
        number1.setVisibility(View.VISIBLE);
        number2.setVisibility(View.VISIBLE);
        operator.setVisibility(View.VISIBLE);
        input.setVisibility(View.VISIBLE);
    }

}