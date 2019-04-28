package com.dldriver.driver.utils;


public interface ApiConstants {
    // String BaseUrl = "https://abrlaundryapp.herokuapp.com/";
    String BaseUrl = "http://148.72.64.138:3007/";
    String loginUrl = "admin/login";
    String signUpUrl = "register/new";
    String enquiryUrl = "queries/new";
    String placeOrderUrl = "order/new";
    String normalorderUrl = "order/new";
    String editorderUrl = "order/edit";
    String easyorderUrl = "orderNote/new";
    String orderListUrl = "adminorder/list";
    String orderUpdateUrl = "order/update";
    String addresslistUrl = "Addresslist/list";
    String addAddressUrl = "Address/new";
    String orderSubListUrl = "ordersub/list";
    String addDriverUrl = "admin/new/driver";
    String driverListUrl = "admindriver/list";
    String getPriceListUrl = "price/list";
    String categoryUrl = "itemcategory/list";
    String general = "gen/general";
    String firebaseTokenId = "/update/token";

    String forgotPassEmailVerification = "gen/forgot";
    String forgotPassCodeVerification = "gen/forgot/code";
    String forgotPasswordUpdation = "gen/forgot/update";
    String getDays = "day/list";
    String getTimings = "admintime/list";
    String UpdateTimings = "adminTime/update";
    String remarkList = "adminremarks/list";
    String addRemark = "adminremarks/new";
    String deleteRemark = "adminremarks/delete";
    String updateRemark = "adminremarks/update";
    String query = "enquiry";
    String email = "email";
    String uid = "uid";
    String password = "password";
    String registrationToken = "registrationToken";
    String name = "name";
    String phone = "mobile";
    String UnpaidRemarkList = "orderremarks/list";

    String code = "code";


    String firstname = "firstname";
    String lastname = "lastname";


    String _id = "_id";
    String emirate = "emirate";
    String area = "area";
    String street = "street";
    String landmark = "landmark";
    String building = "building";
    String flat = "flat";
    String pickup = "pickup";
    String delivery = "delivery";

    String status = "status";


}
