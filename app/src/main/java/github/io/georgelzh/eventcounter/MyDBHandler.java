package github.io.georgelzh.eventcounter;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import androidx.annotation.Nullable;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper{
    //initialize variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "events.db";
    private static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EVENTNAME = "eventName";
    public static final String COLUMN_EVENTDETAIL = "eventDetail";
    public static final String COLUMN_EVENTDATE = "eventDate"; //maybe this should be date data type.




    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //first time running this, the function below will run
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_EVENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENTNAME + " TEXT NOT NULL, " +
                COLUMN_EVENTDETAIL + " TEXT, " +
                COLUMN_EVENTDATE + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    //add a new row to the database
    public boolean addEvent(Events event){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
        values.put(COLUMN_EVENTNAME, event.get_eventName());
        values.put(COLUMN_EVENTDATE, event.get_eventDate());
        values.put(COLUMN_EVENTDETAIL, event.get_eventDetail());
        long result = db.insert(TABLE_EVENTS, null, values);
        return  result != 1;
    }

    //Delete a product from the database
    public void deleteEvent(String eventId){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EVENTS + " WHERE "
                + COLUMN_ID + " =\"" + eventId + "\"");
    }

    public ArrayList<Events> getAllData() throws Exception{
        ArrayList<Events> arrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
        while(cursor.moveToNext()){
            String id = cursor.getString(0);
            String eventName = cursor.getString(1);
            String eventDate = cursor.getString(3);
            Events event = new Events(id, eventName, null, eventDate);
            arrayList.add(event);
        }
        return arrayList;
    }

}


// how to insert multiple datas in sqlite
// https://stackoverflow.com/questions/7776497/inserting-the-multiple-values-in-the-database-on-onclicklistener-event
// sqlite android autoincrement https://www.sqlitetutorial.net/sqlite-autoincrement/
// create sqlite in android studio https://www.youtube.com/watch?v=lQIoxBq10xA
// listview create https://www.youtube.com/watch?v=6q4-Ge0UMKY
// convert milliseconds to hours, minutes and seconds https://stackoverflow.com/questions/4927856/how-to-calculate-time-difference-in-java
