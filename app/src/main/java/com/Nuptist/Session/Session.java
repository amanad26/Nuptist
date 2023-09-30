package com.Nuptist.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Session {

    private static String fileName = "user_data";
    private static String user_id = "user_id";
    private static String user_id_vendor = "user_id_vendor";
    private static String user_vendor_email = "user_vendor_email";
    private static String user_vendor_name = "user_vendor_name";
    private static String user_name = "user_name";
    private static String user_type = "user_type";
    private static String email = "email";
    private static String mobile = "mobile";
    private static String image = "image";
    private static String vendor_image = "vendor_image";
    private static String type = "type";
    private static String bank_details = "bank";
    private static String kyc_details = "kyc";
    private static String venue_detail = "venue";
    private static String kyc_images_url = "kyc_image_url";
    private static String filter_car_list = "f_c_list";
    private static String lat = "lat";
    private static String lang = "lang";
    private static String selectedDate = "selectedDate";

    private static String c_lat = "c_lat";
    private static String c_lang = "c_lang";
    private static String address = "address";
    private static String county = "county";
    private static String mycity = "mtcity";
    private static String gallery_image = "gallery_image";
    private static String userLastName = "userLastName";
    private static String userFirsName = "userFirsName";
    private static String userAddress = "userAddress";

    private static String ispersonal = "personal";

    Context context;
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }



    /*
     *   set and get  UserName
     *  */

    public void setUserName(String name) {
        editor.putString(user_name, name);
        editor.commit();
    }

    public void setUserLastName(String name) {
        editor.putString(userLastName, name);
        editor.commit();
    }

    public void setUserFirsName(String name) {
        editor.putString(userFirsName, name);
        editor.commit();
    }


    public void setIsPersonal(boolean is) {
        editor.putBoolean(ispersonal, is);
        editor.commit();
        editor.apply();
    }


    public void setCounty(String c) {
        editor.putString(county, c);
        editor.commit();
        editor.apply();
    }

    public void setSelectedDate(String c) {
        editor.putString(selectedDate, c);
        editor.commit();
        editor.apply();
    }

    public boolean getIsPersonal() {
        return sharedPreferences.getBoolean(ispersonal, false);
    }

    public void setMyCity(String c) {
        editor.putString(mycity, c);
        editor.commit();
        editor.apply();
    }


    public void setUserAddress(String c) {
        editor.putString(userAddress, c);
        editor.commit();
        editor.apply();
    }


    public void removeVenue() {

        editor.remove(gallery_image);
        editor.remove(venue_detail);
        editor.commit();
        editor.apply();
    }





    /*
     * set and get Vendor UserName
     *  */


    public void setUserVendorName(String name) {
        editor.putString(user_vendor_name, name);
        editor.commit();
    }


    public void setUserVendorEmail(String name) {
        editor.putString(user_vendor_email, name);
        editor.commit();
    }

    public void setAddress(String address1) {
        editor.putString(address, address1);
        editor.commit();
        editor.apply();
    }

    public void setLat(String lat1) {
        editor.putString(lat, lat1);
        editor.commit();
        editor.apply();
    }

    public void setC_lang(String lang1) {
        editor.putString(c_lang, lang1);
        editor.commit();
        editor.apply();
    }

    public void setC_lat(String lat1) {
        editor.putString(c_lat, lat1);
        editor.commit();
        editor.apply();
    }

    public void setLang(String lang1) {
        editor.putString(lang, lang1);
        editor.commit();
        editor.apply();
    }

    public String getAddress() {
        return sharedPreferences.getString(address, "");
    }

    public String getUserAddress() {
        return sharedPreferences.getString(userAddress, "");
    }

    public String getselectedDate() {
        return sharedPreferences.getString(selectedDate, "");
    }

    public String getCounty() {
        return sharedPreferences.getString(county, "");
    }

    public String getMycity() {
        return sharedPreferences.getString(mycity, "");
    }

    public String getLat() {
        return sharedPreferences.getString(lat, "");
    }

    public String getC_lat() {
        return sharedPreferences.getString(c_lat, "");
    }

    public String getLang() {
        return sharedPreferences.getString(lang, "");
    }

    public String getC_lang() {
        return sharedPreferences.getString(c_lang, "");
    }

    public void setVenue() {
        editor.putString(venue_detail, "venue_added");
        editor.commit();
        editor.apply();
    }

    public void setGalleryImage(String image) {
        editor.putString(gallery_image, image);
        editor.commit();
        editor.apply();
    }


    public String getGalleryImage() {
        return sharedPreferences.getString(gallery_image, "");
    }

    public void setUserImage(String image2) {
        editor.putString(image, image2);
        editor.commit();
        editor.apply();
    }

    public void setVendorImage(String image2) {
        editor.putString(vendor_image, image2);
        editor.commit();
        editor.apply();
    }


    public String getUserImage() {
        return sharedPreferences.getString(image, "");
    }

    ///  uncomment when we need differrent image for both accounts
    public String getVendorImage() {
        return sharedPreferences.getString(vendor_image, "");
     }

//    public String getVendorImage() {
//        return sharedPreferences.getString(image, "");
//    }

    public String getVenue() {
        return sharedPreferences.getString(venue_detail, "");
    }


    public String getUserName() {
        return sharedPreferences.getString(user_name, "");
    }

    public String getUserLastName() {
        return sharedPreferences.getString(userLastName, "");
    }

    public String getUserFirstName() {
        return sharedPreferences.getString(userFirsName, "");
    }


    public String getUserVendorName() {
        return sharedPreferences.getString(user_vendor_name, "");
    }

    public void setUser_id(String id) {
        editor.putString(user_id, id);
        editor.commit();
    }

    public void setUserType(String type) {
        editor.putString(user_type, type);
        editor.commit();
    }

    public String getUserType() {
        return sharedPreferences.getString(user_type, "");
    }

    public void setUser_id_vendor(String id) {
        editor.putString(user_id_vendor, id);
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString(user_id, "");
    }


    public String getUserId_Vendor() {
        return sharedPreferences.getString(user_id_vendor, "");
    }

    public void setEmail(String em) {
        editor.putString(email, em);
        editor.commit();
    }

    public String getEmail() {
        return sharedPreferences.getString(email, "");
    }

    public String getVendorEmail() {
        return sharedPreferences.getString(user_vendor_email, "");
    }

    public void setMobile(String mob) {
        editor.putString(mobile, mob);
        editor.commit();
        editor.apply();
    }

    public String getMobile() {
        return sharedPreferences.getString(mobile, "");
    }


    public void setImage(String img) {
        editor.putString(image, img);
        editor.commit();
    }

    public String getImage() {
        return sharedPreferences.getString(image, "");
    }


    public void setType(String tp) {
        editor.putString(type, tp);
        editor.commit();
    }

    public String getType() {
        return sharedPreferences.getString(type, "");
    }


    public void logOut() {
        editor.clear();
        editor.commit();
        editor.apply();
    }

    public void setFilter_car_list(Object o) {
        Gson gson = new Gson();
        setValue(filter_car_list, gson.toJson(o));
    }

    public void deletLatlong() {
        editor.remove(lang);
        editor.remove(lat);
        editor.remove(address);
        editor.commit();
        editor.apply();
    }

    public void deletAddress() {
        editor.remove(address);
        editor.commit();
        editor.apply();
    }


    public void setValue(String key, String Value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, Value);
        editor.commit();
        editor.apply();
    }


    public String getValue(String Key) {
        return sharedPreferences.getString(Key, "");
    }

    public void setBank_details(String bank) {
        editor.putString(bank_details, bank);
        editor.commit();
        editor.apply();
    }

    public String getBank() {
        return sharedPreferences.getString(bank_details, "");
    }

    public void setKyc(String key) {
        editor.putString(kyc_details, key);
        editor.commit();
        editor.apply();
    }

    public String getKyc() {
        return sharedPreferences.getString(kyc_details, "");
    }

    public void setKycUrl(String key) {
        editor.putString(kyc_images_url, key);
        editor.commit();
        editor.apply();
    }

    public String getKycUrl() {
        return sharedPreferences.getString(kyc_images_url, "");
    }


}
