/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.FestesPopulars.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
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

    @ApiMethod(name = "storeTask")
    public void storeTask(TaskBean taskBean) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            Key taskBeanParentKey = KeyFactory.createKey("TaskBeanParent", "festespopulars.txt");
            Entity taskEntity = new Entity("TaskBean", taskBean.getPlace() + taskBean.getName(), taskBeanParentKey);
            taskEntity.setProperty("name", taskBean.getName());
            taskEntity.setProperty("place", taskBean.getPlace());
            taskEntity.setProperty("location", taskBean.getLocation());
            taskEntity.setProperty("date", taskBean.getDate());
            datastoreService.put(taskEntity);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @ApiMethod(name = "getTasks")
    public List<TaskBean> getTasks() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Key taskBeanParentKey = KeyFactory.createKey("TaskBeanParent", "festespopulars.txt");
        Query query = new Query(taskBeanParentKey);
        List<Entity> results = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
        ArrayList<TaskBean> taskBeans = new ArrayList<>();
        for (Entity result : results) {
            TaskBean taskBean = new TaskBean();
            taskBean.setName((String) result.getProperty("name"));
            taskBean.setPlace((String) result.getProperty("place"));
            taskBean.setLocation((String) result.getProperty("location"));
            taskBean.setDate((String) result.getProperty("date"));
            taskBeans.add(taskBean);
        }

        return taskBeans;
    }

    @ApiMethod(name = "clearTasks")
    public void clearTasks() {
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
