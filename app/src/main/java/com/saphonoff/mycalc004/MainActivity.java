package com.saphonoff.mycalc004;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public TextView screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        screen = (TextView) findViewById(R.id.screen);
        screen.setMovementMethod(new ScrollingMovementMethod());

        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_main);

        BtnClickHandler btnClickHandler = new BtnClickHandler();

        for (int i=0; i < contentLayout.getChildCount(); i++) {
            View v = contentLayout.getChildAt(i);
            if (v instanceof Button)
                v.setOnClickListener(btnClickHandler);
        }
    }

    private class BtnClickHandler implements View.OnClickListener {

        private boolean screenColor = false;

        private void checkColor(boolean sc){
            if (sc) {
                screen.setTextColor(getResources().getColor(R.color.smoothGrey));
                screenColor = false;
            }
        }

        public void onClick(View v) {
            Button btnClicked = (Button) v;

            String result = "";
            int length = screen.getText().toString().length();
            char last, penult;
            last = screen.getText().toString().charAt(length-1);
            if (length > 1) {
                penult = screen.getText().toString().charAt(length-2);
            } else penult = '0';

            switch (btnClicked.getId()) {
                case R.id.btnClearAll:
                    screen.setText("0");

                    checkColor(screenColor);
                    break;

                case R.id.btnEquals:
                    result = Utils.calculate( screen.getText().toString() );
                    if (result.equals("9.223372036854775E12"))
                        result = "\u221E";
                    screen.setText(result);

                    screen.setTextColor(getResources().getColor(R.color.orange));
                    screenColor = true;
                    break;

                case R.id.btnC:
                    result = screen.getText().toString().substring(0, length-1);
                    screen.setText(result);

                    checkColor(screenColor);
                    if (screen.getText().toString().equals(""))
                        screen.setText("0");
                    break;

                case R.id.btnMultiply:
                case R.id.btnDivide:
                    if ( screen.getText().toString().equals("0") ||
                         screen.getText().toString().equals("-") ||
                         screen.getText().toString().equals("+") ) {
//                        Toast.makeText(getApplicationContext(), "Cannot add " + btnClicked.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    }

                case R.id.btnMinus:
                case R.id.btnPlus:
                    if ( Utils.isOperator(last) ) {
                        if ((btnClicked.getText().equals("-")) && ( (last == '÷') || (last == '×') ) ) {
                            result = screen.getText() + "-";
                        } else if (last == '-' && Utils.isOperator(penult) && btnClicked.getText().equals("-")) {
                            result = screen.getText().toString();
                        } else if (last == '-' && Utils.isOperator(penult) && !btnClicked.getText().equals("-")) {
                            result = screen.getText().toString().substring(0, length-2) + btnClicked.getText();
                        } else {
                            result = screen.getText().toString().substring(0, length - 1) + btnClicked.getText();
                        }
                        screen.setText(result);

                        checkColor(screenColor);
                        break;
                    }

                case R.id.btnDot:
                    if ( Utils.hasDot( screen.getText().toString() ) &&
                         ( btnClicked.getId() == R.id.btnDot ) ) {
//                        Toast.makeText(getApplicationContext(), "Cannot add second \".\"", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if ( Utils.isOperator(last) )
                        screen.setText(screen.getText() + "0");

                case R.id.btn1:
                case R.id.btn2:
                case R.id.btn3:
                case R.id.btn4:
                case R.id.btn5:
                case R.id.btn6:
                case R.id.btn7:
                case R.id.btn8:
                case R.id.btn9:
                case R.id.btn0:
                    if ( screen.getText().toString().equals("0") && !(btnClicked.getId() == R.id.btnDot) )
                        screen.setText("");

                    screen.setText(screen.getText().toString() + btnClicked.getText());
                    checkColor(screenColor);
                    break;
            }
        }
    }

}
