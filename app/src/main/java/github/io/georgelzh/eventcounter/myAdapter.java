package github.io.georgelzh.eventcounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class myAdapter extends BaseAdapter {

    String dateDifference = "";
    Context context;
    ArrayList<Events> arrayList;

    public myAdapter(Context context, ArrayList<Events> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mycustomlistview, null);
            TextView tv_id = (TextView)convertView.findViewById(R.id.id_txt);
            TextView tv_eventName = (TextView)convertView.findViewById(R.id.event_name);
            TextView tv_eventDate = (TextView)convertView.findViewById(R.id.event_date);
            TextView tv_eventCountDown = (TextView)convertView.findViewById(R.id.event_countdown);

            //get event at position
            Events event = arrayList.get(position);
            String newEventDate = String.valueOf(event.get_eventDate());
            String newEventId = String.valueOf(event.get_id());
            String newEventName = String.valueOf(event.get_eventName());

            //set up the event info in ViewList
            tv_id.setText(newEventId);
            tv_eventName.setText(newEventName);
            tv_eventDate.setText(newEventDate);

            //put this event date we are trying to add, then put the TextView for showing the countdown.
            showEventCountdown(newEventDate, tv_eventCountDown);

        return convertView;
    }

    //get count of the Event List.
    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    //
    public void showEventCountdown(String newEventDate, TextView tv_eventCountDown){
        //get eventdate as a date and do the subtration and output it
        if(newEventDate.length() > 9){
            //convert eventDate from string to date class and do subtraction.
            String pattern = "MM-dd-yyyy HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date currentTime = new Date();


            //extract year, month and day from user input.
            String newEventYear = newEventDate.substring(6,10);
            String newEventMonth = newEventDate.substring(0,2);
            String newEventDay = newEventDate.substring(3,5);

            try {
                //convert the extracted info to Date type in order to calculate time difference
                //between current time and event time.
                Date eventDateDateType = dateFormat.parse(newEventMonth + "-" + newEventDay + "-" +
                        newEventYear + " 00:00:00");
                long differenceHour = (eventDateDateType.getTime() - currentTime.getTime())/(1000*3600);
                long differenceMinutes = (eventDateDateType.getTime() - currentTime.getTime())/(1000*60) % 60;
                long differenceSeconds = (eventDateDateType.getTime() - currentTime.getTime())/1000 % 60;

                //convert Long time to String type.
                String strDifferenceHour = Long.toString(differenceHour);
                String strDifferenceMinutes = Long.toString(differenceMinutes);
                String strDifferenceSeconds = Long.toString(differenceSeconds);

                //show the countdown for users.
                tv_eventCountDown.setText(strDifferenceHour + " h " + strDifferenceMinutes + " m " +
                        strDifferenceSeconds + "s");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}

//insert value into database;
//https://www.youtube.com/watch?v=T0ClYrJukPA&list=PLS1QulWo1RIaRdy16cOzBO5Jr6kEagA07&index=3
//understanding how class Events becomes a media pass information between database and user
//is important!