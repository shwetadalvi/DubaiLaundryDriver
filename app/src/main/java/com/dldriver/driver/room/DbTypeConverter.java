package com.dldriver.driver.room;

import androidx.room.TypeConverter;

import com.dldriver.driver.models.Item;
import com.dldriver.driver.models.OrderItems;
import com.dldriver.driver.models.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DbTypeConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<Service> servicelistToJson(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Service>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String serviceStringToJson(List<Service> data) {
        return gson.toJson(data);
    }

    @TypeConverter
    public static List<Item> ListItemToString(String data) {
        if (data == null) {
            return null;
        }

        Type objectType = new TypeToken<List<Item>>() {
        }.getType();

        return gson.fromJson(data, objectType);
    }

    @TypeConverter
    public static String jsonToPrice(List<Item> data) {
        return gson.toJson(data);
    }

    @TypeConverter
    public static List<OrderItems> jsonToListOfItem(String items) {
        if (items == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<OrderItems>>() {
        }.getType();

        return gson.fromJson(items, listType);
    }

    @TypeConverter
    public static String itemJsonListToString(List<OrderItems> items) {
        return gson.toJson(items);
    }
}
