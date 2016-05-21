package plataformesenxarxa.marcclua.joelmonne.festespopulars.models;

import com.example.festespopulars.backend.festespopularsAPI.model.EventBean;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Event implements Serializable {
    private String name;
    private String description;
    private String place;
    private LatLng location;
    private String date;

    public static EventBean eventToEventBean(Event event) {
        EventBean eventBean = new EventBean();
        eventBean.setName(event.getName());
        eventBean.setDescription(event.getDescription());
        eventBean.setPlace(event.getPlace());
        eventBean.setLocation(event.getLocation().toString());
        eventBean.setDate(event.getDate());
        return eventBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
