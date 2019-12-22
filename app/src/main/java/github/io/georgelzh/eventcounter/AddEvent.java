package github.io.georgelzh.eventcounter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;


public class AddEvent extends AppCompatActivity {
    String date = "";
    private static final String TAG = "AddEvent";
    TextView tvShowDate;
    Button btnAdd, btnSelectDate, btnDelete;
    EditText etEventName, etEventId;
    String eventName = "", eventDate = "", eventId;
    MyDBHandler dbHandler;
    //setup the date picker dialog.
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        tvShowDate = findViewById(R.id.tvShowDate);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        etEventName = findViewById(R.id.etEventName);
        etEventId = findViewById(R.id.etEventId);

        dbHandler = new MyDBHandler(this, null, null,1);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                date = month + "-" + day + "-" + year;
                eventDate = date + " 00:00:00";
                tvShowDate.setText(eventDate);
            }
        };

    }


    //when users click the set time button, select date dialog will pop out.
    public void btnSelectDateOnClick(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog  = new DatePickerDialog(AddEvent.this, android.R.style.
                Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    };


    //add a row of data to sqlite when add button clicked.
    public void btnConfirmAddOnClick(View view){
        //store event name and save it in SQlite.
        eventName = etEventName.getText().toString();

        if (eventName.length() <= 0){
            Toast.makeText(this, "Please Enter Event Name!", Toast.LENGTH_SHORT).show();
        } else if(eventDate == ""){
            Toast.makeText(this, "Please Enter Event Date!", Toast.LENGTH_SHORT).show();
        }else{
            Events event = new Events(null ,eventName, null, eventDate);
            dbHandler.addEvent(event);
            Toast.makeText(this, "Successfully Added One Event!", Toast.LENGTH_SHORT).show();
            //take user back to the Event;
            Intent intentConfirmAdd = new Intent(this, MainActivity.class);
            startActivity(intentConfirmAdd);
        }
    }


    public void btnDeleteOnClick(View view) {
        //if user entered eventName, delete the eventNumber
        if (etEventId.length() <= 0) {
            Toast.makeText(this, "Please Enter Event ID!", Toast.LENGTH_SHORT).show();
        } else {
            eventId = etEventId.getText().toString();
            dbHandler.deleteEvent(eventId);
            Toast.makeText(this, "Successfully Deleted One Event!", Toast.LENGTH_SHORT).show();

            //take user back to the Event;
            Intent intentConfirmDeleted = new Intent(this, MainActivity.class);
            startActivity(intentConfirmDeleted);
        }
    }

    public void btnCancelAddOnClick(View view){
        Intent intentCancel = new Intent(this, MainActivity.class);
        startActivity(intentCancel);
    }
}


//get date from user
//https://www.youtube.com/watch?v=hwe1abDO2Ag