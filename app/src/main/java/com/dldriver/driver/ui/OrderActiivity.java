package com.dldriver.driver.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dldriver.driver.Almoski;
import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.R;
import com.dldriver.driver.contracts.OrderContracts;
import com.dldriver.driver.databinding.ActivityOrderAciivityBinding;
import com.dldriver.driver.models.Address;
import com.dldriver.driver.models.BaseError;
import com.dldriver.driver.models.CategoryItems;
import com.dldriver.driver.models.Item;
import com.dldriver.driver.models.Order;
import com.dldriver.driver.models.Response;
import com.dldriver.driver.models.Service;
import com.dldriver.driver.presenter.OrderPesenterImpl;
import com.dldriver.driver.room.AppDatabase;
import com.dldriver.driver.ui.adapter.OrderItemAdapter;
import com.dldriver.driver.utils.PrefUtils;
import com.google.android.material.snackbar.Snackbar;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.DefaultPrinter;
import com.mazenrashed.printooth.data.Printable;
import com.mazenrashed.printooth.utilities.PrintingCallback;
import com.somesh.permissionmadeeasy.enums.Permission;
import com.somesh.permissionmadeeasy.helper.PermissionHelper;
import com.somesh.permissionmadeeasy.intefaces.PermissionListener;
import com.titanium.locgetter.main.LocationGetter;
import com.titanium.locgetter.main.LocationGetterBuilder;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextStyle;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import vpos.apipackage.PosApiHelper;

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
import static com.dldriver.driver.utils.Constants.DEFAULT_PREFERENCE;
import static com.dldriver.driver.utils.Constants.LOGIN_PREFERENCE_DRVER_ID;
import static com.dldriver.driver.utils.Constants.REQUEST_CODE_LOCATION;
import static com.dldriver.driver.utils.Constants.TERMS1;
import static com.dldriver.driver.utils.Constants.TERMS2;
import static com.dldriver.driver.utils.Constants.TERMS3;
import static com.dldriver.driver.utils.Constants.TERMS4;
import static com.dldriver.driver.utils.Constants.TERMS5;
import static com.dldriver.driver.utils.Constants.TERMS6;
import static com.dldriver.driver.utils.Constants.TERMS7;
import static com.dldriver.driver.utils.Constants.TERMS_CONDITIONS;
import static com.dldriver.driver.utils.Constants.getDate;
import static com.dldriver.driver.utils.Constants.getTime;

public class OrderActiivity extends BaseActivity implements OrderContracts.IOrderView, View.OnClickListener, PermissionListener, PrintingCallback, NetworkResponses<Response> {
    private final PosApiHelper posApiHelper = PosApiHelper.getInstance();
    private int ret;
    private double mTotal;
    private ActivityOrderAciivityBinding mBinding;
    private OrderPesenterImpl mOrderPesenter;
    private int mOrderId;
    private AppDatabase mDatabase;
    private Address mAddress;
    private PermissionHelper mPermissionHelper;
    private boolean isBUttonClicked;
    private boolean isPermissionGranted;
    private Order mOrder;
    private double mLat;
    private double mLng;
    private PrefUtils mPrefUtils;
    private static final int SCAN_REQUEST = 119;
    private static final int CHOOSE_SERVER_REQUEST = 120;
    private AlertDialog mAlertDialog;
    Bitmap icon;
    Printer mPrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = AppDatabase.getInstance(this);
        showProgress("");
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_aciivity);
        if (getIntent().getExtras() != null) {
            try {
                mOrderId = Integer.parseInt(getIntent().getExtras().getString("orderId"));

                Log.e("Tag :", "Inside orde :" + mOrderId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mPrefUtils = new PrefUtils(this);
        checkOrderLocal(mOrderId, 1);
        mOrderPesenter = new OrderPesenterImpl(this, mOrderId, mDatabase);
        mBinding.backButton.setOnClickListener(view -> onBackPressed());
        mBinding.done.setOnClickListener(view -> onReachedClicked());
        mBinding.btnPrint.setOnClickListener(view -> onPrintClicked());
        mBinding.btnEdit.setOnClickListener(View -> onEditOrderClicked());
        mBinding.btnReject.setOnClickListener(View -> onRejectClicked());
        //   mBinding.makeOrder.setOnClickListener(view -> onMakeNewOrderClicked());

        icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_logo);
        return;
    }

    private void onPrintClicked() {
        String printerType = mPrefUtils.getDefaultSharedPreferenceString("list_preference_1", "0");


        switch (printerType) {
            case "0":
                if (Printooth.INSTANCE.hasPairedPrinter()) {
                    //printBill.printBluetooth(mAddress, mOrder, icon);
                    printBluetooth(mAddress, mOrder, icon);

                } else {
                    showMessage("Cannot find any paired printer, select printer from settings");
                }
                break;
            case "1":
                print(mAddress, mOrder, icon);
                break;
            case "2":
                printForZK90(mAddress, mOrder, icon);
                break;
        }

    }

    private void onRejectClicked() {
        int driverId = Integer.parseInt(new PrefUtils(this).getStringPreference(DEFAULT_PREFERENCE, LOGIN_PREFERENCE_DRVER_ID));
        if (mAddress.getCategory().equals("Pickup"))
            mOrderPesenter.rejectOrder(0, driverId, mOrderId);
        else
            mOrderPesenter.rejectOrder(4, driverId, mOrderId);

        Intent go = new Intent(OrderActiivity.this, NextOrderActivity.class);
        go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(go);

    }

    private void onEditOrderClicked() {

        Intent go = new Intent(this, ItemListActivity.class);
        startActivity(go);
    }

    private void onMakeNewOrderClicked() {
        startActivity(new Intent(this, ItemListActivity.class));
    }

    private void checkOrderLocal(int orderId, int deliveryStatus) {
        LiveData<Address> liveData = mDatabase.mAddressDao().getAddresses(orderId);
        liveData.observe(this, address -> {
            if (address != null) {
                mOrderPesenter.getAddress(mOrderId);
                mOrderPesenter.getORderDetail();
                mBinding.startTrack.setOnClickListener(this);
            } else {
                mBinding.emptyOrder.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void success(String message) {

    }

    @Override
    public void failed(String message) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void parseOrderDetails(Order order) {
        setUpOrderColumn(order);

    }

    @Override
    protected void onResume() {
        //   clearItems();
        super.onResume();
    }

    private void clearItems() {
        Almoski.getInst().setIronList(null);
        Almoski.getInst().setWashList(null);
        Almoski.getInst().setDrycleanList(null);
    }

    @Override
    public void setUpFirstColumn(Address address) {
        mAddress = address;
        if (mAddress.getCategory().equals("Pickup"))
            mBinding.btnEdit.setVisibility(View.VISIBLE);
        else
            mBinding.btnEdit.setVisibility(View.GONE);

        System.out.println("ltLon" + address.getLatitude() + "###" + address.getLongitude());
        Almoski.getInst().setDeliveryType(address.getDelType());
        Almoski.getInst().setAddress(mAddress.getFullAddress());
        Almoski.getInst().setAddressId(mAddress.getAddressId());
        Almoski.getInst().setEmail(mAddress.getEmail());
        Almoski.getInst().setOrderId(Integer.parseInt(mAddress.getOrderId()));
        mBinding.pickupOrder.setText(mAddress.getCategory());
        if (mAddress.getPaymeentStatus() == 0) {
            mBinding.paymentStatus.setText("Unpaid");
            mBinding.layoutPaymentStatus.setBackground(getResources().getDrawable(R.drawable.rounded_corner_blue_rectangle));
        } else {
            mBinding.paymentStatus.setText("Paid");
            mBinding.layoutPaymentStatus.setBackground(getResources().getDrawable(R.drawable.rounded_corner_green_rectangle));
        }
        //  mBinding.paymentStatus.setText(mAddress.getPaymeentStatus() == 0 ? "Unpaid" : "Paid");
        if (!address.getCategory().equals("Pickup")) {
            mBinding.pickupTime.setText(address.getDelTime());
            mBinding.date2.setText(address.getDelDate());
        } else {
            if (address.getTimeSlot() != null || address.getTimeSlot().isEmpty()) {
                /*if (!address.getTimeSlot().equals("@")) {
                    String[] dateTime = address.getTimeSlot().split("@");
                    String pickupDate = dateTime[0].substring(0, dateTime[0].lastIndexOf(" "));
                    String pickupTime = dateTime[1];
                    mBinding.pickupTime.setText(pickupTime);
                    mBinding.date2.setText(pickupDate);
                    Almoski.getInst().setPickupDate(pickupDate);
                    Almoski.getInst().setPickupTime(pickupTime);
                }*/
                mBinding.delType.setText(address.getDelType());
                mBinding.deliveryTime.setText(address.getDelTime());
                mBinding.date.setText(address.getDelDate());
                mBinding.pickupTime.setText(address.getOrderTime());
                mBinding.date2.setText(address.getOrderDate());
                Almoski.getInst().setDelTime(address.getDelTime());
                Almoski.getInst().setDelDate(address.getDelDate());
                Almoski.getInst().setPickupTime(address.getOrderTime());
                Almoski.getInst().setPickupDate(address.getOrderTime());
                if (address.getArea().equals("NASAB")) {
                    Almoski.getInst().setNisabClub(true);
                } else {
                    Almoski.getInst().setNisabClub(false);
                }
            }
        }

        mBinding.customerName.setText(address.getFull_Name());
        mBinding.customerArea.setText(address.getArea());
        mBinding.customerStreet.setText(address.getStreet());
        mBinding.customerBlock.setText(address.getBlock());
        mBinding.customerFloor.setText(address.getFloor());
        mBinding.customerApt.setText(address.getApartment());
        mBinding.customerHouse.setText(address.getHouse());
        mBinding.customerAddress.setText(address.getFullAddress());
        mBinding.customerMobile.setText(address.getMobile());
    }

    private void onReachedClicked() {
        try {

            if (mAddress.getCategory().equals("Pickup")) {
                //  PrintBill printBill = new PrintBill(OrderActiivity.this);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Are you reached the destination ?");
                // showPaymentAlert();
                int driverId = Integer.parseInt(new PrefUtils(this).getStringPreference(DEFAULT_PREFERENCE, LOGIN_PREFERENCE_DRVER_ID));


           /* OrderInteracterImpl orderInteracter = new OrderInteracterImpl(this, mOrderPesenter);
            orderInteracter.updatePaymentStatus(Integer.parseInt(mAddress.getOrderId()), driverId,
                  0, this);*/


                alert.setPositiveButton("YES", (dialog, which) -> {
                    dialog.dismiss();
                    String printerType = mPrefUtils.getDefaultSharedPreferenceString("list_preference_1", "0");

                    mOrderPesenter.updateTrackingStatus(5);
                /*if (mAddress.getCategory().equals("Pickup")) {
                    mOrderPesenter.updateTrackingStatus(2);
                } else {

                    mOrderPesenter.updateTrackingStatus(6);
                }*/
                 /*   switch (printerType) {
                        case "0":
                            if (Printooth.INSTANCE.hasPairedPrinter()) {
                                //printBill.printBluetooth(mAddress, mOrder, icon);
                                printBluetooth(mAddress, mOrder, icon);

                            } else {
                                showMessage("Cannot find any paired printer, select printer from settings");
                            }
                            break;
                        case "1":
                            print(mAddress, mOrder, icon);
                            break;
                    }*/


                    Intent go = new Intent(OrderActiivity.this, NextOrderActivity.class);
                    go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(go);


                });
                alert.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });
                mAlertDialog = alert.create();
                mAlertDialog.setOnShowListener(dialog -> {
                    mAlertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setBackground(null);
                    mAlertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setBackground(null);
                });
                mAlertDialog.show();

            } else {
                //Toast.makeText(getApplicationContext(), mAddress.getCategory(), Toast.LENGTH_LONG);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Are you completed this order?");
                if (mAddress.getPaymeentStatus() == 0)
                    showPaymentAlert();
                alert.setPositiveButton("YES", (dialog, which) -> {
                    mOrderPesenter.updateTrackingStatus(6);


                    showMessage(getResources().getString(R.string.order_completed));
                    mBinding.startTrack.setEnabled(false);


                    dialog.dismiss();

                    Intent go = new Intent(OrderActiivity.this, NextOrderActivity.class);
                    go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(go);


                });


                alert.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();


                });
                mAlertDialog = alert.create();
                mAlertDialog.setOnShowListener(dialog -> {
                    mAlertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setBackground(null);
                    mAlertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setBackground(null);
                });
                alert.show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showPaymentAlert() {
        int driverId = Integer.parseInt(new PrefUtils(this).getStringPreference(DEFAULT_PREFERENCE, LOGIN_PREFERENCE_DRVER_ID));
        PaymentStatusDialog newFragment = PaymentStatusDialog.newInstance(mOrderPesenter, Integer.parseInt(mAddress.getOrderId()), driverId);
        newFragment.show(getSupportFragmentManager(), "dialog");

    }

    @Override
    public void trackingUpdated(String message) {

    }

    double calculateExclusiveVat(double total) {
        double vat = total - (total / ((100d + 5d) / 100d));
        return vat;
    }

    double calulateExclusivePrice(double total, double vat) {
        return total - vat;
    }

    private void setUpOrderColumn(Order order) {
        Log.d("Inside :", "Inside setUpOrderColumn :");
        hideProgress();
        mOrder = order;
        Almoski.getInst().setWashList(null);
        Almoski.getInst().setDrycleanList(null);
        Almoski.getInst().setIronList(null);
        try {
            Log.d("Inside :", "Inside setUpOrderColumn1 :" + order.getOrder().getService().size());
            if (0 != order.getOrder().getService().size()) {
                //   Log.d("Inside :", "Inside setUpOrderColumn getRemarks:"+order.getOrder().getRemarks());
                mBinding.easyOrderContainer.setVisibility(View.VISIBLE);
                mBinding.easyOrderDesc.setText("Remarks : " + order.getOrder().getRemarks());
                CategoryItems dto1 = new CategoryItems(); //this is for data to use in edit items
                CategoryItems.Detail dto11 = dto1.new Detail(); //this is for data to use in edit items


                ArrayList<CategoryItems.Detail.Item> drylisttmp = new ArrayList<>();// for using in edit items
                ArrayList<CategoryItems.Detail.Item> washlisttmp = new ArrayList<>();
                ArrayList<CategoryItems.Detail.Item> ironlisttmp = new ArrayList<>();

                for (int i = 0; i < order.getOrder().getService().size(); i++) {


                    if (order.getOrder().getService().get(i).getServiceName().equalsIgnoreCase("Wash + Iron")) {
                        if (order.getOrder().getService().get(i).getItems() != null) {
                            for (int j = 0; j < order.getOrder().getService().get(i).getItems().size(); j++) {
                                double total = 0;
                                CategoryItems.Detail.Item subdtotmp = dto11.new Item();


                                //for edit items

                                subdtotmp.setItemName(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getItemName());
                                subdtotmp.setItemId(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getItemId());
                                subdtotmp.setItemcount(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getQuantity());
                                subdtotmp.setAmount(String.valueOf(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getPrice()));

                                total = order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getQuantity() *
                                        order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getPrice();
                                subdtotmp.setTotal(String.valueOf(total));
                                //  total = total + (details.getResult().get(i).getPrice() * details.getResult().get(i).getQty());

                                // drylist.add(subdto);
                                washlisttmp.add(subdtotmp);

                            }
                        }
                    }

                    Almoski.getInst().setWashList(washlisttmp);
                    Log.d("Inside :", "Inside setUpOrderColumn1 washlisttmp :" + Almoski.getInst().getWashList().size());
                    if (order.getOrder().getService().get(i).getServiceName().equalsIgnoreCase("Dry Clean")) {
                        if (order.getOrder().getService().get(i).getItems() != null) {
                            for (int j = 0; j < order.getOrder().getService().get(i).getItems().size(); j++) {
                                double total = 0;
                                CategoryItems.Detail.Item subdtotmp = dto11.new Item();


                                //for edit items

                                subdtotmp.setItemName(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getItemName());
                                subdtotmp.setItemId(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getItemId());
                                subdtotmp.setItemcount(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getQuantity());
                                subdtotmp.setAmount(String.valueOf(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getPrice()));


                                //  total = total + (details.getResult().get(i).getPrice() * details.getResult().get(i).getQty());
                                total = order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getQuantity() *
                                        order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getPrice();
                                subdtotmp.setTotal(String.valueOf(total));

                                drylisttmp.add(subdtotmp);

                            }
                        }
                    }
                    Almoski.getInst().setDrycleanList(drylisttmp);
                    Log.d("Inside :", "Inside setUpOrderColumn1 drylisttmp :" + Almoski.getInst().getDrycleanList().size());

                    if (order.getOrder().getService().get(i).getServiceName().equalsIgnoreCase("Ironing")) {
                        if (order.getOrder().getService().get(i).getItems() != null) {
                            for (int j = 0; j < order.getOrder().getService().get(i).getItems().size(); j++) {
                                double total = 0;
                                CategoryItems.Detail.Item subdtotmp = dto11.new Item();


                                //for edit items

                                subdtotmp.setItemName(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getItemName());
                                subdtotmp.setItemId(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getItemId());
                                subdtotmp.setItemcount(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getQuantity());
                                subdtotmp.setAmount(String.valueOf(order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getPrice()));


                                //  total = total + (details.getResult().get(i).getPrice() * details.getResult().get(i).getQty());

                                // drylist.add(subdto);
                                total = order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getQuantity() *
                                        order.getOrder().getService().get(i).getItems().get(j).getOrderItems().getPrice();
                                subdtotmp.setTotal(String.valueOf(total));
                                ironlisttmp.add(subdtotmp);

                            }
                        }
                    }
                    Almoski.getInst().setIronList(ironlisttmp);
                    Log.d("Inside :", "Inside setUpOrderColumn1 ironlisttmp :" + Almoski.getInst().getIronList().size());
                }


                OrderItemAdapter adapter = new OrderItemAdapter(order.getOrder().getService());
                mBinding.recyclerViewOrderItems.setAdapter(adapter);
                mBinding.recyclerViewOrderItems.setHasFixedSize(true);
                ViewCompat.setNestedScrollingEnabled(mBinding.recyclerViewOrderItems,false);
               // mBinding.recyclerViewOrderItems.setNestedScrollingEnabled(false);
                double vat = calculateExclusiveVat(order.getOrder().getPrice() == null ? 0 : order.getOrder().getPrice());
                mBinding.vat5.setText(String.format("%.2f", vat));
                double price = calulateExclusivePrice(order.getOrder().getPrice(), Double.parseDouble(mBinding.vat5.getText().toString()));
                mBinding.orderTotal.setText(String.format("%.2f", price));
                mBinding.netTotal.setText(String.format("%.2f", order.getOrder().getPrice()));
                mBinding.textAmount.setText("Total: " + String.format("%.2f", order.getOrder().getPrice()));
                mOrder.getOrder().setVat(vat);
                mOrder.getOrder().setExlusivePrice(price);
            } else {
                //          mBinding.makeOrder.setVisibility(View.VISIBLE);
                mBinding.pricingContainer.setVisibility(View.GONE);
                mBinding.easyOrderContainer.setVisibility(View.VISIBLE);
                mBinding.easyOrderDesc.setText("Remarks : " + order.getOrder().getRemarks());
                // Log.d("Inside :", "Inside setUpOrderColumn getRemarks1:"+order.getOrder().getRemarks());

            }

       /*     if (order.getOrder().getOrderType() != 1) {
                OrderItemAdapter adapter = new OrderItemAdapter(order.getOrder().getService());
                mBinding.recyclerViewOrderItems.setAdapter(adapter);
                double vat = calculateExclusiveVat(order.getOrder().getPrice() == null ? 0 : order.getOrder().getPrice());
                mBinding.vat5.setText(String.format("%.2f", vat));
                double price = calulateExclusivePrice(order.getOrder().getPrice(), Double.parseDouble(mBinding.vat5.getText().toString()));
                mBinding.orderTotal.setText(String.format("%.2f", price));
                mBinding.netTotal.setText(String.format("%.2f", order.getOrder().getPrice()));
                mOrder.getOrder().setVat(vat);
                mOrder.getOrder().setExlusivePrice(price);
            } else {
                mBinding.makeOrder.setVisibility(View.VISIBLE);
                mBinding.pricingContainer.setVisibility(View.GONE);
                mBinding.easyOrderContainer.setVisibility(View.VISIBLE);
                mBinding.easyOrderDesc.setText(order.getOrder().getRemarks());
            }  */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        isBUttonClicked = true;
        mPermissionHelper = PermissionHelper.Builder()
                .with(this)
                .requestCode(REQUEST_CODE_LOCATION)
                .setPermissionResultCallback(this)
                .askFor(Permission.LOCATION)
                .rationalMessage("Location access is necessary to work with the routing") //Optional
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionHelper.requestPermissions();
        } else {
            isPermissionGranted = true;
        }
        if (isPermissionGranted) {
            findLoc();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("CheckResult")
    private void findLoc() {
        showProgress("Fetching location details...");
        LocationGetter locationGetter = new LocationGetterBuilder(getApplicationContext()).build();
        locationGetter.getLatestLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                    mLat = location.getLatitude();
                    mLng = location.getLongitude();
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", mAddress.getLatitude(), mAddress.getLongitude(), "", location.getLatitude(), location.getLongitude(), "");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                    hideProgress();
                }, Throwable::printStackTrace);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, ArrayList<
            String> grantedPermissionList) {
        isPermissionGranted = true;
        if (isBUttonClicked) {

        }
    }

    @Override
    public void onPermissionsDenied(int i, ArrayList<String> deniedPermissionList) {

    }

    private static final String TAG = "OrderActiivity";

    @Override
    public void connectingWithPrinter() {
        Log.d(TAG, "connectingWithPrinter: ");
    }

    @Override
    public void connectionFailed(@NotNull String s) {
        Log.d(TAG, "connectionFailed: ");
    }

    @Override
    public void onError(@NotNull String s) {

    }

    @Override
    public void onMessage(@NotNull String s) {

    }

    @Override
    public void printingOrderSentSuccessfully() {

    }

    public void printForZK90(Address address, Order order, Bitmap bitmap) {

        mTotal = 0;

        mPrinter = DriverManager.getInstance().getPrinter();


        new Thread(new Runnable() {
            @Override
            public void run() {

                int printStatus = mPrinter.getPrinterStatus();
                if (printStatus == SdkResult.SDK_PRN_STATUS_PAPEROUT) {

                            Toast.makeText(OrderActiivity.this,getResources().getString(R.string.printer_out_of_paper),Toast.LENGTH_SHORT).show();


                } else {
                    mPrinter.setPrintAppendBitmap(bitmap, Layout.Alignment.ALIGN_CENTER);
                    PrnStrFormat format = new PrnStrFormat();
                    format.setTextSize(25);
                    format.setAli(Layout.Alignment.ALIGN_CENTER);
                    format.setStyle(PrnTextStyle.BOLD);

                    mPrinter.setPrintAppendString(COMPANY_TAX, format);
                    mPrinter.setPrintAppendString(COMPANY_NAME, format);
                    mPrinter.setPrintAppendString(COMPANY_ADDRESS1, format);
                    mPrinter.setPrintAppendString(COMPANY_ADDRESS2, format);
                    mPrinter.setPrintAppendString(COMPANY_ADDRESS3, format);
                    format.setTextSize(25);
                    format.setStyle(PrnTextStyle.NORMAL);
                    format.setAli(Layout.Alignment.ALIGN_CENTER);
                    mPrinter.setPrintAppendString(COMPANY_TRN, format);
                    mPrinter.setPrintAppendString(COMPANY_TELE, format);
                    mPrinter.setPrintAppendString(COMPANY_TELE2, format);
                    format.setAli(Layout.Alignment.ALIGN_LEFT);
                    mPrinter.setPrintAppendString("................................................................", format);


                    format.setTextSize(25);
                    format.setStyle(PrnTextStyle.NORMAL);
                    mPrinter.setPrintAppendString(COMPANY_ORDER_NO + order.getOrder().getOrderNo(), format);
                    mPrinter.setPrintAppendString(COMPANY_DATE+ getDate() + " " + getTime(), format);


                    if (order.getOrder().getDeliveryType() == 2) {
                        mPrinter.setPrintAppendString("Express Laundry",format);
                    }
                    mPrinter.setPrintAppendString("................................................................", format);
                    mPrinter.setPrintAppendString("Customer Name  :" + address.getFull_Name(),format);
                    mPrinter.setPrintAppendString("Mobile Number  :" + address.getMobile(),format);
                    mPrinter.setPrintAppendString("Address        :" + address.getArea(),format);
                    mPrinter.setPrintAppendString("................................................................", format);
                    mPrinter.setPrintAppendString(COMPANY_ITEM_DESCRIPTION + createSpaceHeader(false, COMPANY_ITEM_DESCRIPTION, COMPANY_ITEM_DESCRIPTION.length()) +
                            COMPANY_ITEM_QUANTITY + createSpaceAmtPrinter(COMPANY_ITEM_QUANTITY.length(), COMPANY_ITEM_AMOUNT.length()) +
                            COMPANY_ITEM_AMOUNT,format);
                    mPrinter.setPrintAppendString("................................................................", format);
                    for (Service service : order.getOrder().getService()) {
                        for (Item item : service.getItems()) {
                            mPrinter.setPrintAppendString(service.getServiceName(),format);
                            mPrinter.setPrintAppendString(item.getOrderItems().getItemName() + createSpaceHeader(false, COMPANY_ITEM_DESCRIPTION, item.getOrderItems().getItemName().length()) +
                                    item.getOrderItems().getQuantity() + createSpaceAmtPrinter( getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()).length(), String.valueOf(item.getOrderItems().getQuantity()).length()) +
                                    getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()),format);

                        }
                    }
                    mPrinter.setPrintAppendString("................................................................", format);
                    format.setAli(Layout.Alignment.ALIGN_RIGHT);
                    String total = String.format("%.2f",order.getOrder().getExlusivePrice());
                    mPrinter.setPrintAppendString(COMPANY_ITEM_TOTAL + createSpaceZ90Printer(COMPANY_ITEM_TOTAL.length(), total.length()) + total,format);
                    String vat = String.format("%.2f",order.getOrder().getVat());
                    mPrinter.setPrintAppendString(COMPANY_VAT + createSpaceZ90Printer(COMPANY_VAT.length(), vat.length()) + vat,format);

                    String gross =String.format("%.2f",order.getOrder().getPrice());
                    mPrinter.setPrintAppendString("................................................................", format);
                    mPrinter.setPrintAppendString(COMPANY_GROSS + createSpaceZ90Printer( COMPANY_GROSS.length(), gross.length()) + gross,format);


                    mPrinter.setPrintAppendString("................................................................", format);
                    format.setAli(Layout.Alignment.ALIGN_LEFT);
                    mPrinter.setPrintAppendString(TERMS_CONDITIONS,format);
                    mPrinter.setPrintAppendString(TERMS1,format);
                    mPrinter.setPrintAppendString(TERMS2,format);
                    mPrinter.setPrintAppendString(TERMS3,format);
                    mPrinter.setPrintAppendString(TERMS4,format);
                    mPrinter.setPrintAppendString(TERMS5,format);
                    mPrinter.setPrintAppendString(TERMS6,format);
                    mPrinter.setPrintAppendString(TERMS7,format);
                    mPrinter.setPrintAppendString("................................................................", format);
                    mPrinter.setPrintAppendString("Remarks : " + order.getOrder().getRemarks(),format);
                    mPrinter.setPrintAppendString("Pickup Driver Name : " + order.getOrder().getPickupDriver(),format);
                    mPrinter.setPrintAppendString("Delivery Driver Name : " + order.getOrder().getDeliveryDriver(),format);
                    mPrinter.setPrintAppendString("................................................................", format);
                    format.setAli(Layout.Alignment.ALIGN_CENTER);
                    mPrinter.setPrintAppendString(centerAlignment(COMPANY_BILL_GREETING1),format);
                    mPrinter.setPrintAppendString(centerAlignment(COMPANY_BILL_GREETING2),format);
                    format.setAli(Layout.Alignment.ALIGN_LEFT);
                    mPrinter.setPrintAppendString("................................................................", format);

                   /* format.setAli(Layout.Alignment.ALIGN_NORMAL);
                    format.setStyle(PrnTextStyle.NORMAL);
                    format.setTextSize(25);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.acq_institute), format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.iss) + " ", format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.trans_type) + " ", format);
                    format.setTextSize(30);
                    format.setStyle(PrnTextStyle.BOLD);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.sale) + " (C) ", format);
                    format.setAli(Layout.Alignment.ALIGN_NORMAL);
                    format.setStyle(PrnTextStyle.NORMAL);
                    format.setTextSize(25);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.exe_date) + " 2030/10  ", format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.batch_no) + " 000335 ", format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.voucher_no) + " 000002 ", format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.date) + " 2018/05/28 ", format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.time) + " 00:00:01 ", format);
                    format.setTextSize(30);
                    format.setStyle(PrnTextStyle.BOLD);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.amount) + "￥0.01", format);
                    format.setStyle(PrnTextStyle.NORMAL);
                    format.setTextSize(25);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.reference) + " ", format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.cardholder_signature) + " ", format);
                    mPrinter.setPrintAppendString(" ", format);

                    mPrinter.setPrintAppendString(" -----------------------------", format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.print_remark) + " ", format);
                    mPrinter.setPrintAppendString(getResources().getString(R.string.cardholder_copy) + " ", format);
                    mPrinter.setPrintAppendString(" ", format);
                    mPrinter.setPrintAppendString(" ", format);
                    mPrinter.setPrintAppendString(" ", format);
                    mPrinter.setPrintAppendString(" ", format);*/
                    mPrinter.setPrintStart();
                                      }



            }
        }).start();
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

                posApiHelper.PrintStr(".................................");
                //  Log.d("print","del type :"+order.getOrder().getOrderNo());
                posApiHelper.PrintStr(COMPANY_ORDER_NO + order.getOrder().getOrderNo());
                posApiHelper.PrintStr(COMPANY_DATE + getDate() + " " + getTime());
                Log.d("print", "del type :" + order.getOrder().getOrderNo());

                if (order.getOrder().getDeliveryType() == 2) {
                    posApiHelper.PrintStr("Express Laundry");
                }
                posApiHelper.PrintStr(".................................");
                posApiHelper.PrintStr("Customer Name  :" + address.getFull_Name());
                posApiHelper.PrintStr("Mobile Number  :" + address.getMobile());
                posApiHelper.PrintStr("Address        :" + address.getArea());
                posApiHelper.PrintStr(".................................");
                posApiHelper.PrintStr(COMPANY_ITEM_DESCRIPTION + createSpace(false, COMPANY_ITEM_DESCRIPTION, COMPANY_ITEM_DESCRIPTION.length()) +
                        COMPANY_ITEM_QUANTITY + createSpace(false, COMPANY_ITEM_QUANTITY, COMPANY_ITEM_QUANTITY.length()) +
                        COMPANY_ITEM_AMOUNT + createSpace(false, COMPANY_ITEM_AMOUNT, COMPANY_ITEM_AMOUNT.length()));
                posApiHelper.PrintStr(".................................");

                for (Service service : order.getOrder().getService()) {
                    for (Item item : service.getItems()) {
                        posApiHelper.PrintStr(service.getServiceName());
                        posApiHelper.PrintStr(item.getOrderItems().getItemName() + createSpace(false, COMPANY_ITEM_DESCRIPTION, item.getOrderItems().getItemName().length()) +
                                item.getOrderItems().getQuantity() + createSpace(false, COMPANY_ITEM_QUANTITY, String.valueOf(item.getOrderItems().getQuantity()).length()) +
                                getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()) + createSpace(false, COMPANY_ITEM_AMOUNT, getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()).length()));


                    }
                }

                posApiHelper.PrintStr(".................................");
                String total = String.valueOf(order.getOrder().getExlusivePrice());
                posApiHelper.PrintStr(COMPANY_ITEM_TOTAL + createSpace(false, COMPANY_ITEM_TOTAL.length(), total.length()) + total);
                String vat = String.valueOf(order.getOrder().getVat());
                posApiHelper.PrintStr(COMPANY_VAT + createSpace(false, COMPANY_VAT.length(), vat.length()) + vat);
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                String gross = String.valueOf(order.getOrder().getPrice());
                posApiHelper.PrintStr("...............................");
                posApiHelper.PrintStr(COMPANY_GROSS + createSpace(false, COMPANY_GROSS.length(), gross.length()) + gross);


                posApiHelper.PrintStr(".................................");
                posApiHelper.PrintStr(TERMS_CONDITIONS);
                posApiHelper.PrintStr(TERMS1);
                posApiHelper.PrintStr(TERMS2);
                posApiHelper.PrintStr(TERMS3);
                posApiHelper.PrintStr(TERMS4);
                posApiHelper.PrintStr(TERMS5);
                posApiHelper.PrintStr(TERMS6);
                posApiHelper.PrintStr(TERMS7);
                posApiHelper.PrintStr(".................................");
                posApiHelper.PrintStr("Remarks : " + order.getOrder().getRemarks());
                posApiHelper.PrintStr("Pickup Driver Name : " + order.getOrder().getPickupDriver());
                posApiHelper.PrintStr("Delivery Driver Name : " + order.getOrder().getDeliveryDriver());
                posApiHelper.PrintStr(".................................");
                posApiHelper.PrintStr(centerAlignment(COMPANY_BILL_GREETING1));
                posApiHelper.PrintStr(centerAlignment(COMPANY_BILL_GREETING2));
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr("\n");
                posApiHelper.PrintStr(".................................");
                posApiHelper.PrintStr(COMPANY_BILL_GREETING2);


                try {

                    ret = posApiHelper.PrintStart();
                    // Log.e(TAG, "Lib_PrnStart ret = " + ret);
                } catch (Exception ex) {
                    BaseActivity.showSnackBar(null, "Printer cannot found", 1500);
                }


            }
        }).start();
    }


    public void printBluetooth(Address address, Order order, Bitmap icon) {
        for (int i = 0; i < 2; i++) {
            ArrayList<Printable> printables = new ArrayList<>();
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setImage(R.drawable.ic_logo, getResources())
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
                    .setText(COMPANY_ORDER_NO.trim() + order.getOrder().getOrderNo())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_DATE.trim() + getDate() + " " + getTime())
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
                    .setText(COMPANY_ITEM_DESCRIPTION + createSpace(true, COMPANY_ITEM_DESCRIPTION, COMPANY_ITEM_DESCRIPTION.length()) +
                            COMPANY_ITEM_QUANTITY + createSpace(true, COMPANY_ITEM_QUANTITY, COMPANY_ITEM_QUANTITY.length()) +
                            COMPANY_ITEM_AMOUNT + createSpace(true, COMPANY_ITEM_AMOUNT, COMPANY_ITEM_AMOUNT.length()))
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
                            .setText(item.getOrderItems().getItemName() + createSpace(true, COMPANY_ITEM_DESCRIPTION, item.getOrderItems().getItemName().length()) +
                                    item.getOrderItems().getQuantity() + createSpace(true, COMPANY_ITEM_QUANTITY, String.valueOf(item.getOrderItems().getQuantity()).length()) +
                                    getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()) + createSpace(true, COMPANY_ITEM_AMOUNT, getPrice(item.getOrderItems().getQuantity(), item.getOrderItems().getPrice()).length()))
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
                    .setText(COMPANY_ITEM_TOTAL + createSpace(true, COMPANY_ITEM_TOTAL.length(), total.length()) + total)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            String vat = String.valueOf(order
                    .getOrder().getVat());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_VAT + createSpace(true, COMPANY_VAT.length(), vat.length()) + vat)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(4)
                    .build());
            String gross = String.valueOf(order.getOrder().getPrice());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_GROSS + createSpace(true, COMPANY_GROSS.length(), gross.length()) + gross)
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
                    .setText(TERMS2)
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
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText("Remarks : " + order.getOrder().getRemarks())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText("Pickup Driver Name : " + order.getOrder().getPickupDriver())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText("Delivery Driver Name : " + order.getOrder().getDeliveryDriver())
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
                total = isBluetooth ? 36 : 20;
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
    private String createSpaceHeader(boolean isBluetooth, String item, int length) {
        int total;
        int num;
        switch (item) {
            case COMPANY_ITEM_DESCRIPTION:
                total = isBluetooth ? 36 : 26;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
            case COMPANY_ITEM_QUANTITY:
                total = isBluetooth ? 5 : 4;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
            case COMPANY_ITEM_AMOUNT:
                total = isBluetooth ? 8 : 8;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
        }
        return null;
    }
    private String createSpaceAmtPrinter(int firstLength, int secondLegth) {
        //   int num = 32 - firstLength;
        int num = 12 - firstLength;
        num = num - secondLegth;
        return new String(new char[num]).replace('\0', ' ');
    }
    private String createSpaceZ90Printer(int firstLength, int secondLegth) {
        //   int num = 32 - firstLength;
        int num = 38 - firstLength;
        num = num - secondLegth;
        return new String(new char[num]).replace('\0', ' ');
    }
    private String createSpace(boolean isBluetooth, int firstLength, int secondLegth) {
        int num = isBluetooth ? 48 - firstLength : 32 - firstLength;
        num = num - secondLegth;
        return new String(new char[num]).replace('\0', ' ');
    }

    @Override
    public void success(Response response) {
        AppDatabase mDatabase = AppDatabase.getInstance(this);
        new Thread(() -> {
          /*  if (isChecked)
                mDatabase.mAddressDao().updatePaymentStatus(mOrderId, 1);
            else {*/
            mDatabase.mAddressDao().updatePaymentStatus(mOrderId, 0);
            //  }
        }).start();

    }

    @Override
    public void failed(Response failedResonse) {
        Snackbar.make(mBinding.getRoot(), failedResonse.getMessage(), 1500).show();
    }

    @Override
    public void error(BaseError errorMessage) {
        Snackbar.make(mBinding.getRoot(), errorMessage.getMessage(), 1500).show();
    }
}
//    Wed Dec 19 2018 04:00:00 GMT+0400 (+04)