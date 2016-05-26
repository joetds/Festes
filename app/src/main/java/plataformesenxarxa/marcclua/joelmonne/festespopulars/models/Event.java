package plataformesenxarxa.marcclua.joelmonne.festespopulars.models;

import com.example.festespopulars.backend.festespopularsAPI.model.EventBean;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Event implements Serializable {
    public static final String event_key = "event";
    private String name;
    private String description;
    private String place;
    private String location;
    private String date;
    private boolean favourite;

    public Event(EventBean eventBean) {
        this.setName(eventBean.getName());
        this.setDescription(eventBean.getDescription());
        this.setPlace(eventBean.getPlace());
        this.setLocation(eventBean.getLocation());
        this.setDate(eventBean.getDate());
        this.setFavourite(eventBean.getFavourite());
    }

    public Event() {

    }

    public static EventBean eventToEventBean(Event event) {
        EventBean eventBean = new EventBean();
        eventBean.setName(event.getName());
        eventBean.setDescription(event.getDescription());
        eventBean.setPlace(event.getPlace().toLowerCase());
        eventBean.setLocation(event.getLocation());
        eventBean.setDate(event.getDate());
        eventBean.setFavourite(event.getFavourite());
        return eventBean;
    }

    public static LatLng getLocationAsLatlng(String location) {
        String[] split = location.split(",");
        double latitude = Double.parseDouble(split[0]);
        double longitude = Double.parseDouble(split[1]);
        return new LatLng(latitude, longitude);
    }

    public boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
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
        return place.substring(0, 1).toUpperCase() + place.substring(1);
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LatLng getLocationAsLatlng() {
        return getLocationAsLatlng(location);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
