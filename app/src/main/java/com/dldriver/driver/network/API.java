package com.dldriver.driver.network;

import com.dldriver.driver.models.CategoryItems;
import com.dldriver.driver.models.NewOrderItem;
import com.dldriver.driver.models.Order;
import com.dldriver.driver.models.PriceDetails;
import com.dldriver.driver.models.Response;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @Multipart
    @POST("/login/driverlogin")
    Call<Response> driverLogin(@Part("pin") RequestBody pin, @Part("registrationToken") RequestBody token);

    @Multipart
    @POST("/orderfordriver/list")
    Call<Order> getOrders(@Part("orderId")RequestBody requestBodyOrderId);

    @Multipart
    @POST("/order/update")
    Call<Response> trackingOn(@Part("orderId")RequestBody requestBodyOrderId,@Part("status")RequestBody RequestBodyStatusCode);

    @Multipart
    @POST("/order/update")
  //  Call<Response> rejectOrder(@Part("status")RequestBody RequestBodyStatusCode,@Part("orderId")RequestBody requestBodyOrderId,@Part("driverId")RequestBody RequestBodyDriverId);
    Call<Response> rejectOrder(@Part("status")RequestBody RequestBodyStatusCode,@Part("orderId")RequestBody requestBodyOrderId);

    @Multipart
    @POST("/order/update/collection")
    Call<Response> updatePaymentStatus(@Part("isCashRecieved")RequestBody isCashRecievedBody, @Part("orderId")RequestBody orderIdBody, @Part("id")RequestBody idBody);

    @POST("/order/new")
    Call<Response> makeNewOrder(@Body NewOrderItem newOrderItem);

    @POST("/itemcategory/list")
    Call<CategoryItems> fetchCategories();

    @POST("/price/list")
    Call<PriceDetails> fetchPriceList();

    @Multipart
    @POST("/login/driverlogout")
    Call<Response> driverLogout(@Part("driverId")RequestBody requestBodyDriverId);
}
