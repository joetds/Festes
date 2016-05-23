package plataformesenxarxa.marcclua.joelmonne.festespopulars.models;

import com.example.festespopulars.backend.festespopularsAPI.model.EventBean;

import java.io.Serializable;

public class Event implements Serializable {
    public static final String event_key = "event";
    private String name;
    private String description;
    private String place;
    private String location;
    private String date;

    public Event(EventBean eventBean) {
        this.setName(eventBean.getName());
        this.setDescription(eventBean.getDescription());
        String place = eventBean.getPlace();
        place = place.substring(0, 1).toUpperCase() + place.substring(1);
        this.setPlace(place);
        this.setLocation(eventBean.getLocation());
        this.setDate(eventBean.getDate());
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
