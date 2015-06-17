package nyc.c4q.anthonyfermin.sleepsort;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<Integer> numbers;
    ArrayList<Integer> sortedNumbers;
    EditText numInputView;
    TextView outView;
    final int NOTIFICATION_ID = 1234;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbers = new ArrayList<>();
        numInputView = (EditText) findViewById(R.id.numInputView);
        outView = (TextView) findViewById(R.id.outView);
        handler = new Handler();

    }

    public void sort(View view){
        if(numbers.size() > 0){
            sortedNumbers = new ArrayList<>();

            for(int i = 0; i < numbers.size(); i++){
                final int index = i;
                handler.postDelayed(new Runnable() {
                    int num = numbers.get(index);
                    @Override
                    public void run() {

                        sortedNumbers.add(num);
                        outView.setText(sortedNumbers.get(0) + "");
                        for(int i = 1; i < sortedNumbers.size(); i++){
                            outView.append(", " + sortedNumbers.get(i));
                        }
                        if(sortedNumbers.size() == numbers.size()){

                            outView.append(" : Numbers Sorted!");
                            numbers = new ArrayList<>();

                            NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getApplicationContext());
                            notifBuilder.setContentTitle("Sleep Sort").setContentText("Array Sorted: " + outView.getText().toString()).setAutoCancel(true).setSmallIcon(R.drawable.abc_dialog_material_background_light);

                            notifManager.notify(NOTIFICATION_ID, notifBuilder.build());
                        }
                    }
                },numbers.get(i));

            }
        }
    }

    public void next(View view){
        if(numbers.size() > 9){
            (Toast.makeText(this,"Maximum numbers reached (10)",Toast.LENGTH_LONG)).show();
        }else if(numInputView.getText().toString().length() > 0){
            int num = Integer.parseInt(numInputView.getText().toString());
            numbers.add(num);
            numInputView.setText("");
            if(numbers.size() == 1){
                outView.setText(num + "");
            }else {
                outView.append(", " + num);
            }
        }
    }

}
