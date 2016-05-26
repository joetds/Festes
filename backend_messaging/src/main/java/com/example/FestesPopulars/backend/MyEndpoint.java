/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.FestesPopulars.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "festespopularsAPI",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.FestesPopulars.example.com",
                ownerName = "backend.FestesPopulars.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    @ApiMethod(name = "storeEvent")
    public void storeEvent(EventBean eventBean) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            Key taskBeanParentKey = KeyFactory.createKey("TaskBeanParent", "festespopulars.txt");
            Entity taskEntity = new Entity("EventBean", eventBean.getName(), taskBeanParentKey);
            taskEntity.setProperty("name", eventBean.getName());
            taskEntity.setProperty("description", eventBean.getDescription());
            taskEntity.setProperty("place", eventBean.getPlace().toLowerCase());
            taskEntity.setProperty("location", eventBean.getLocation());
            taskEntity.setProperty("date", eventBean.getDate());
            taskEntity.setProperty("favourite", eventBean.isFavourite());
            datastoreService.put(taskEntity);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @ApiMethod(name = "getEventByPlace")
    public List<EventBean> getEventByPlace(@Named("place") String place) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Key taskBeanParentKey = KeyFactory.createKey("TaskBeanParent", "festespopulars.txt");
        Query query = new Query("EventBean").setAncestor(taskBeanParentKey).setFilter(new Query.FilterPredicate("place", Query.FilterOperator.EQUAL, place.toLowerCase()));
        List<Entity> results = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
        return getEventBeans(results);
    }

    @ApiMethod(name = "getAllEvents")
    public List<EventBean> getAllEvents() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Key taskBeanParentKey = KeyFactory.createKey("TaskBeanParent", "festespopulars.txt");
        Query query = new Query(taskBeanParentKey);
        List<Entity> results = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
        return getEventBeans(results);
    }

    private List<EventBean> getEventBeans(List<Entity> results) {
        ArrayList<EventBean> eventBeen = new ArrayList<>();
        for (Entity result : results) {
            EventBean eventBean = new EventBean();
            eventBean.setName((String) result.getProperty("name"));
            eventBean.setDescription((String) result.getProperty("description"));
            eventBean.setPlace((String) result.getProperty("place"));
            eventBean.setLocation((String) result.getProperty("location"));
            eventBean.setDate((String) result.getProperty("date"));
            eventBean.setFavourite((boolean) result.getProperty("favourite"));
            eventBeen.add(eventBean);
        }
        return eventBeen;
    }

    @ApiMethod(name = "clearEvents")
    public void clearEvents() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            Key taskBeanParentKey = KeyFactory.createKey("TaskBeanParent", "festespopulars.txt");
            Query query = new Query(taskBeanParentKey);
            List<Entity> results = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
            for (Entity result : results) {
                datastoreService.delete(result.getKey());
            }
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

}
