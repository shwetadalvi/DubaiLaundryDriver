package com.dldriver.driver.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.R;
import com.dldriver.driver.models.Address;
import com.dldriver.driver.models.Item;
import com.dldriver.driver.models.Order;
import com.dldriver.driver.models.Service;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.DefaultPrinter;
import com.mazenrashed.printooth.data.Printable;

import java.util.ArrayList;
import java.util.Locale;

import vpos.apipackage.PosApiHelper;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.dldriver.driver.utils.Constants.COMPANY_ADDRESS1;
import static com.dldriver.driver.utils.Constants.COMPANY_ADDRESS2;
import static com.dldriver.driver.utils.Constants.COMPANY_ADDRESS3;
import static com.dldriver.driver.utils.Constants.COMPANY_BILL_GREETING1;
import static com.dldriver.driver.utils.Constants.COMPANY_BILL_GREETING2;
import static com.dldriver.driver.utils.Constants.COMPANY_DATE;
import static com.dldriver.driver.utils.Constants.COMPANY_GROSS;
import static com.dldriver.driver.utils.Constants.COMPANY_ITEM_AMOUNT;
import static com.dldriver.driver.utils.Constants.COMPANY_ITEM_DESCRIPTION;
import static com.dldriver.driver.utils.Constants.COMPANY_ITEM_QUANTITY;
import static com.dldriver.driver.utils.Constants.COMPANY_ITEM_TOTAL;
import static com.dldriver.driver.utils.Constants.COMPANY_NAME;
import static com.dldriver.driver.utils.Constants.COMPANY_ORDER_NO;
import static com.dldriver.driver.utils.Constants.COMPANY_TAX;
import static com.dldriver.driver.utils.Constants.COMPANY_TELE;
import static com.dldriver.driver.utils.Constants.COMPANY_TELE2;
import static com.dldriver.driver.utils.Constants.COMPANY_TRN;
import static com.dldriver.driver.utils.Constants.COMPANY_VAT;
import static com.dldriver.driver.utils.Constants.TERMS1;
import static com.dldriver.driver.utils.Constants.TERMS3;
import static com.dldriver.driver.utils.Constants.TERMS4;
import static com.dldriver.driver.utils.Constants.TERMS5;
import static com.dldriver.driver.utils.Constants.TERMS6;
import static com.dldriver.driver.utils.Constants.TERMS7;
import static com.dldriver.driver.utils.Constants.TERMS_CONDITIONS;
import static com.dldriver.driver.utils.Constants.getDate;
import static com.dldriver.driver.utils.Constants.getTime;

public class PrintBill {

    private final PosApiHelper posApiHelper = PosApiHelper.getInstance();
    private int ret;
    private double mTotal;
    private Context context;

    public PrintBill(Context context) {
        this.context = context;
    }

    public void print(Address address, Order order, Bitmap bitmap) {
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                mTotal = 0;
                ret = posApiHelper.PrintInit(2, 24, 24, 0);
                ret = posApiHelper.PrintSetFont((byte) 24, (byte) 24, (byte) 0x00);
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintBmp(bitmap);
                posApiHelper.PrintStr(centerAlignment(COMPANY_TAX) + COMPANY_TAX);
                posApiHelper.PrintStr(centerAlignment(COMPANY_NAME) + COMPANY_NAME);
                posApiHelper.PrintStr(centerAlignment(COMPANY_ADDRESS1) + COMPANY_ADDRESS1);
                posApiHelper.PrintStr(centerAlignment(COMPANY_ADDRESS2) + COMPANY_ADDRESS2);
                posApiHelper.PrintStr(centerAlignment(COMPANY_ADDRESS3) + COMPANY_ADDRESS3);
                posApiHelper.PrintStr(centerAlignment(COMPANY_TRN) + COMPANY_TRN);
                posApiHelper.PrintStr(centerAlignment(COMPANY_TELE) + COMPANY_TELE);
                posApiHelper.PrintStr(centerAlignment(COMPANY_TELE2) + COMPANY_TELE2);

                posApiHelper.PrintStr("................................");
                posApiHelper.PrintStr(COMPANY_ORDER_NO + order.getOrder().getOrderNo());
                posApiHelper.PrintStr(COMPANY_DATE + getDate() + " " + getTime());
                if (Integer.parseInt(order.getOrder().getDeliveryType().toString()) == 2) {
                    posApiHelper.PrintStr("Express Laundry");
                }
                posApiHelper.PrintStr("................................");
                posApiHelper.PrintStr("Customer Name  :" + address.getFull_Name());
                posApiHelper.PrintStr("Mobile Number  :" + address.getMobile());
                posApiHelper.PrintStr("Address        :" + address.getArea());
                posApiHelper.PrintStr("................................");
                posApiHelper.PrintStr(COMPANY_ITEM_DESCRIPTION + createSpace(false,COMPANY_ITEM_DESCRIPTION, COMPANY_ITEM_DESCRIPTION.length()) +
                        COMPANY_ITEM_QUANTITY + createSpace(false,COMPANY_ITEM_QUANTITY, COMPANY_ITEM_QUANTITY.length()) +
                        COMPANY_ITEM_AMOUNT + createSpace(false,COMPANY_ITEM_AMOUNT, COMPANY_ITEM_AMOUNT.length()));
                posApiHelper.PrintStr("................................");

                for (Service service : order.getOrder().getService()) {
                    for (Item item : service.getItems()) {
                        posApiHelper.PrintStr(service.getServiceName());
                        posApiHelper.PrintStr(item.getOrderItems().getItemName() + createSpace(false,COMPANY_ITEM_DESCRIPTION, item.getOrderItems().getItemName().length()) +
                                item.getOrderItems().getQuantity() + createSpace(false,COMPANY_ITEM_QUANTITY, String.valueOf(item.getOrderItems().getQuantity()).length()) +
                                getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()) + createSpace(false,COMPANY_ITEM_AMOUNT, getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()).length()));
                    }
                }

                posApiHelper.PrintStr("................................");
                String total = String.valueOf(order.getOrder().getExlusivePrice());
                posApiHelper.PrintStr(COMPANY_ITEM_TOTAL + createSpace(false,COMPANY_ITEM_TOTAL.length(), total.length()) + total);
                String vat = String.valueOf(order
                        .getOrder().getVat());
                posApiHelper.PrintStr(COMPANY_VAT + createSpace(false,COMPANY_VAT.length(), vat.length()) + vat);
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                String gross = String.valueOf(order.getOrder().getPrice());
                posApiHelper.PrintStr("................................");
                posApiHelper.PrintStr(COMPANY_GROSS + createSpace(false,COMPANY_GROSS.length(), gross.length()) + gross);

                posApiHelper.PrintStr("................................");
                posApiHelper.PrintStr(centerAlignment(COMPANY_BILL_GREETING1));
                posApiHelper.PrintStr(centerAlignment(COMPANY_BILL_GREETING2));
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
//            posApiHelper.PrintStr(COMPANY_BILL_GREETING2);

                try {
                    ret = posApiHelper.PrintStart();
                } catch (Exception ex) {
                    BaseActivity.showSnackBar(null, "Printer cannot found", 1500);
                }

                Log.e(TAG, "Lib_PrnStart ret = " + ret);
            }
        }).start();
    }

    public void printBluetooth(Address address, Order order, Bitmap icon) {
        for (int i = 0; i < 2; i++) {
        ArrayList<Printable> printables = new ArrayList<>();
            printables.add(new Printable.PrintableBuilder()
                            .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setImage(R.drawable.icon,context.getResources())
                    .setNewLinesAfter(2)
                    .build());
        printables.add(new Printable.PrintableBuilder()
                .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                .setText(COMPANY_TAX.trim())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                .setText(COMPANY_NAME.trim())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                .setText(COMPANY_ADDRESS3.trim())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                .setText(COMPANY_TRN.trim())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                .setText(COMPANY_TELE.trim())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                .setText(COMPANY_TELE2.trim())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setText("................................................")
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setText(COMPANY_ORDER_NO.trim()+order.getOrder().getOrderNo())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setText(COMPANY_DATE.trim()+getDate() + " " + getTime())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(3)
                .build());

        printables.add(new Printable.PrintableBuilder()
                .setText("Customer Name  :" + address.getFull_Name())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setText("Mobile Number  :" + address.getMobile())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setText("Address        :" + address.getArea())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(4)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setText(COMPANY_ITEM_DESCRIPTION + createSpace(true,COMPANY_ITEM_DESCRIPTION, COMPANY_ITEM_DESCRIPTION.length()) +
                        COMPANY_ITEM_QUANTITY + createSpace(true,COMPANY_ITEM_QUANTITY, COMPANY_ITEM_QUANTITY.length()) +
                        COMPANY_ITEM_AMOUNT + createSpace(true,COMPANY_ITEM_AMOUNT, COMPANY_ITEM_AMOUNT.length()))
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setText("................................................")
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        for (Service service : order.getOrder().getService()) {
            for (Item item : service.getItems()) {
                printables.add(new Printable.PrintableBuilder()
                        .setText(service.getServiceName())
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
                printables.add(new Printable.PrintableBuilder()
                        .setText(item.getOrderItems().getItemName() + createSpace(true,COMPANY_ITEM_DESCRIPTION, item.getOrderItems().getItemName().length()) +
                                item.getOrderItems().getQuantity() + createSpace(true,COMPANY_ITEM_QUANTITY, String.valueOf(item.getOrderItems().getQuantity()).length()) +
                                getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()) + createSpace(true,COMPANY_ITEM_AMOUNT, getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()).length()))
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
            }
        }
        printables.add(new Printable.PrintableBuilder()
                .setText("................................................")
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
            String total = String.valueOf(order.getOrder().getExlusivePrice());
        printables.add(new Printable.PrintableBuilder()
                .setText(COMPANY_ITEM_TOTAL + createSpace(true,COMPANY_ITEM_TOTAL.length(), total.length()) + total)
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
            String vat = String.valueOf(order
                    .getOrder().getVat());
        printables.add(new Printable.PrintableBuilder()
                .setText(COMPANY_VAT + createSpace(true,COMPANY_VAT.length(), vat.length()) + vat)
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(4)
                .build());
            String gross = String.valueOf(order.getOrder().getPrice());
        printables.add(new Printable.PrintableBuilder()
                .setText(COMPANY_GROSS + createSpace(true,COMPANY_GROSS.length(), gross.length()) + gross)
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(4)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setText("................................................")
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS_CONDITIONS)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS1)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(Constants.TERMS2)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS3)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS4)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS5)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS6)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS7)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
        printables.add(new Printable.PrintableBuilder()
                .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                .setText(COMPANY_BILL_GREETING1)
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        printables.add(new Printable.PrintableBuilder()
                .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                .setText(COMPANY_BILL_GREETING2)
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setNewLinesAfter(2)
                .build());
        Printooth.INSTANCE.printer().print(printables);
        }
    }

    String totalAmount(String total, String vat) {
        double totalDouble = Double.parseDouble(total) + Double.parseDouble(vat);
        return String.format(Locale.US, "%.2f", totalDouble);
    }

    private String getVat(String total) {
        double totalDouble = Double.parseDouble(total);
        double vat = (totalDouble * 5) / 100;
        return String.format(Locale.US, "%.2f", vat);
    }

    private String totalCalculation(Order order) {
        for (Service service : order.getOrder().getService()) {
            for (Item item : service.getItems()) {
                mTotal = mTotal + item.getOrderItems().getQuantity() * item.getOrderItems().getPrice();
            }
        }
        return String.format(Locale.US, "%.2f", mTotal);
    }

    private String getPrice(int qty, double Price) {
        double price = qty * Price;
        return String.format(Locale.US, "%.2f", price);
    }

    private String centerAlignment(String item) {
        int totalLength = 32;
        int length = item.length();
        int offset = (totalLength - length) / 2;
        offset = (offset % 2 == 0) ? offset : offset + 1;
        return new String(new char[offset]).replace('\0', ' ');
    }

    private String createSpace(boolean isBluetooth, String item, int length) {
        int total;
        int num;
        switch (item) {
            case COMPANY_ITEM_DESCRIPTION:
                total =  isBluetooth ? 36 : 20;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
            case COMPANY_ITEM_QUANTITY:
                total = isBluetooth ? 5 : 5;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
            case COMPANY_ITEM_AMOUNT:
                total = isBluetooth ? 8 : 8;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
        }
        return null;
    }

    private String createSpace(boolean isBluetooth, int firstLength, int secondLegth) {
        int num =  isBluetooth ? 48-firstLength : 32 - firstLength;
        num = num - secondLegth;
        return new String(new char[num]).replace('\0', ' ');
    }
}
