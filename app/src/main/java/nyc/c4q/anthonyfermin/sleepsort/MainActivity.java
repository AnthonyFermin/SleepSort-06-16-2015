package nyc.c4q.anthonyfermin.sleepsort;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<Integer> numbers;
    ArrayList<Integer> sortedNumbers;
    EditText numInputView;
    TextView outView;
    Button nextButton;
    Button sortButton;
    final int NOTIFICATION_ID = 1234;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        initializeViews();
        setListeners();
    }

    private void initializeViews(){
        numInputView = (EditText) findViewById(R.id.numInputView);
        outView = (TextView) findViewById(R.id.outView);
        nextButton = (Button) findViewById(R.id.nextButton);
        sortButton = (Button) findViewById(R.id.sortButton);
    }

    private void initializeData(){
        numbers = new ArrayList<>();
        handler = new Handler();
    }

    private void setListeners(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort();
            }
        });
    }

    /* takes "numbers" array and uses sleep sort to sort all elements into "sortedNumbers" array
     * when array is sorted, clears "numbers" array, appends message to outView, and sends notification
     * only runs bulk of code if there are at least 2 elements in array
     */
    private void sort(){
        if(numbers.size() > 1){
            sortedNumbers = new ArrayList<>();

            for(int i = 0; i < numbers.size(); i++){
                final int index = i;
                handler.postDelayed(new Runnable() {
                    int num = numbers.get(index);

                    @Override
                    public void run() {

                        sortedNumbers.add(num);
                        outView.setText(sortedNumbers.get(0) + "");
                        for (int i = 1; i < sortedNumbers.size(); i++) {
                            outView.append(", " + sortedNumbers.get(i));
                        }
                        if (sortedNumbers.size() == numbers.size()) {

                            outView.append(" : Numbers Sorted!");
                            nextButton.setVisibility(Button.VISIBLE);
                            numbers = new ArrayList<>();

                            NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getApplicationContext());
                            notifBuilder.setContentTitle("Sleep Sort")
                                    .setContentText("Array Sorted: " + outView.getText().toString())
                                    .setAutoCancel(true)
                                    .setSmallIcon(R.drawable.abc_dialog_material_background_light);

                            notifManager.notify(NOTIFICATION_ID, notifBuilder.build());
                        }
                    }
                }, numbers.get(i));
            }
        }else{
            (Toast.makeText(getApplicationContext(),"Need at least 2 numbers",Toast.LENGTH_LONG)).show();
        }
    }


    /* Adds a new Integer element to the "numbers" array, up to a maximum of 10
     * Appends each added element to "numInputView" for user to view
     */
    public void next(){

        if(numInputView.getText().toString().length() > 0){
            int num = Integer.parseInt(numInputView.getText().toString());
            numbers.add(num);
            numInputView.setText("");
            if(numbers.size() == 1){
                outView.setText(num + "");
            }else {
                outView.append(", " + num);
            }

            if(numbers.size() >= 10) {
                (Toast.makeText(this, "Maximum numbers reached (10)", Toast.LENGTH_LONG)).show();
                nextButton.setVisibility(Button.INVISIBLE);
            }
        }
    }

}
