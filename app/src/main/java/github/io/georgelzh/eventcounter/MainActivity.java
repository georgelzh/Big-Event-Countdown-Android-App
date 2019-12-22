package github.io.georgelzh.eventcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView tvEvents;
    Button btnAddEvent;
    MyDBHandler dbHandler;
    Events event;
    ListView eventList;
    ArrayList<Events> arrayList;
    myAdapter myNewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddEvent = findViewById(R.id.btnAddDeleteEvent);
        eventList = findViewById(R.id.event_list);
        dbHandler = new MyDBHandler(this, null, null, 1);
        arrayList = new ArrayList<>();

        //to update event countdown in every second.
        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                   while(!isInterrupted()){
                       Thread.sleep(1000);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               try{
                                   loadDataInListView();
                               } catch (Exception e){
                                   e.printStackTrace();
                               }
                           }
                       });
                   }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    //go to the add/delete page whenn the button is clicked
    public void addDeleteEventButtonOnClick(View view) {
        Intent i = new Intent(this, AddEvent.class);
        startActivity(i);
    }

    //get all the Events object from SQLite database, and set them up as ViewList
    public void loadDataInListView()throws Exception{
        arrayList = dbHandler.getAllData();
        myNewAdapter = new myAdapter(this, arrayList);
        eventList.setAdapter(myNewAdapter);
        myNewAdapter.notifyDataSetChanged();
    }
}


// https://www.youtube.com/watch?v=WNBE_3ZizaA
// https://www.youtube.com/watch?v=mPGCLKRCG-8&list=PL6gx4Cwl9DGBsvRxJJOzG4r4k_zLKrnxl&index=36
// convert String time to date in a certain format https://www.javatpoint.com/java-string-to-date
// calculate time difference https://www.programcreek.com/2014/01/how-to-calculate-time-difference-in-java/
// how to update every seconds: https://www.youtube.com/watch?v=G6AkbQkfywE