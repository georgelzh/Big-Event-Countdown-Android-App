package github.io.georgelzh.eventcounter;

import androidx.annotation.NonNull;

public class Events {
    private String _id;
    private String _eventName;
    private String _eventDetail;
    private String _eventDate;

    public Events(){
    }

    public Events(String id, String eventName, String eventDetail, String eventDate) {
        this._id = id;
        this._eventName = eventName;
        this._eventDetail = eventDetail;
        this._eventDate = eventDate;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void set_eventName(String _eventName) {
        this._eventName = _eventName;
    }

    public void set_eventDetail(String _eventDetail) {
        this._eventDetail = _eventDetail;
    }

    public void set_eventDate(String _eventDate) {
        this._eventDate = _eventDate;
    }

    public String get_id() {
        return _id;
    }

    public String get_eventName() {
        return _eventName;
    }

    public String get_eventDetail() {
        return _eventDetail;
    }

    public String get_eventDate() {
        return _eventDate;
    }

}
