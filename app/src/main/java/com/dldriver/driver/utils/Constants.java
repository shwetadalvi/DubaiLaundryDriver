package com.dldriver.driver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    public static final String IS_UNREAD_NOTIFICATION = "IS_UNREAD_NOTIFICATION";
    public static final String DEFAULT_PREFERENCE = "default";
    public static final String LOGIN_PREFERENCE = "login";
    public static final String LOGIN_PREFERENCE_DRVER_ID = "driverId";
    public static final String REGISTRATION_TOKEN_PREFERENCE = "registration_token";
    public static final String PREFERENCE_BLUETOOTH_PRINTER_NAME = "bluetooth_printer_name";
    public static final String PREFERENCE_BLUETOOTH_PRINTER_ADDRESS = "bluetooth_printer_address";
    public static final String PUSH_TOKEN_PREFERENCE = "token";
    public static int REQUEST_CODE_LOCATION = 0x1234;
    public static final String COMPANY_NAME = "  ALMOSKY LAUNDRY  ";
    public static final String COMPANY_TELE = "   +971 434 40253   ";
    public static final String COMPANY_TELE2 = "  +9715 043 82940  ";
    public static final String COMPANY_ADDRESS1 = "   ARMADA TOWER 1   ";
    public static final String COMPANY_ADDRESS2 = "       BLOCK P      ";
    public static final String COMPANY_ADDRESS3 = "     JLT, DUBAI     ";
    public static final String COMPANY_TAX = "    TAX INVOICE    ";
    public static final String COMPANY_TRN = "TRN: 100316475100003";
    public static final String COMPANY_ORDER_NO = "INV. NO  : ";
    public static final String COMPANY_DATE = "INV. DATE : ";
    public static final String COMPANY_ITEM_DESCRIPTION = "Item";
    public static final String COMPANY_ITEM_QUANTITY = "Qty";
    public static final String COMPANY_ITEM_AMOUNT = "Amount";
    public static final String COMPANY_ITEM_TOTAL = "Total";
    public static final String COMPANY_BILL_GREETING1 = "Thank You";
    public static final String COMPANY_BILL_GREETING2 = "Visit again";
    public static final String COMPANY_VAT = "VAT (5%)";
    public static final String COMPANY_GROSS = "Gross Amount";
    public static final int REQUEST_CODE_ENABLE_BLUETOOTH = 0x1235;
    public static final int REQUEST_CODE_COARSE_LOCATION = 0x1240;


    public static final int BLUETOOTH_AUDIO_DEVICE = 0x1236;
    public static final int BLUETOOTH_COMPUTER = 0x1237;
    public static final int BLUETOOTH_PRINTER = 0x1238;
    public static final int BLUETOOTH_GENERAL = 0x1239;
    public static final String TERMS_CONDITIONS = "Terms and Conditions";
    public static final String TERMS1 = "* Articles not collected within 30 days will be disposed off.";
    public static final String TERMS2 = "* For articles damaged or lost the maximum sum payable will be five times the charge on the bill of the article.";
    public static final String TERMS3 = "* The laundry will not be responsible for shrinkage or fastness of colours.";
    public static final String TERMS4 = "* We are not responsible for any valuables left in the pockets.";
    public static final String TERMS5 = "* Please check your laundry while receiving,company will not be responsible for any lost items.";
    public static final String TERMS6 = "* Carpet or curtains will take time from 4-10 days.";
    public static final String TERMS7 = "* Minimum order is AED 45.";
    public static String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date().getTime());
    }

    public static String getTime() {
        return new SimpleDateFormat("hh:mm").format(new Date().getTime());
    }



}
