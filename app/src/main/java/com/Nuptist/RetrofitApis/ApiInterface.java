package com.Nuptist.RetrofitApis;

import static com.Nuptist.RetrofitApis.BaseUrls.*;
import static com.Nuptist.RetrofitApis.BaseUrls.SIGNUP_URL;


import com.Nuptist.Models.CountryPackageModel;
import com.Nuptist.Models.DateModel;
import com.Nuptist.Models.OfferProductModel;
import com.Nuptist.Models.OfferServiceModel;
import com.Nuptist.Models.PackageByRatingModel;
import com.Nuptist.Models.ServiceOfferModel;
import com.Nuptist.Models.ServiceProductModel;
import com.Nuptist.Models.TermsAndConditionModel;
import com.Nuptist.calender.AddOnceNew.FinalAddonsNewModel;
import com.Nuptist.Chat.ChatWithUsers.ChatListUserModel;
import com.Nuptist.Models.AccountModel;
import com.Nuptist.Models.AddOncesModelNew;
import com.Nuptist.Models.BookedDatesModel;
import com.Nuptist.Models.ForgotPasswordModel;
import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.LoginModel;
import com.Nuptist.Models.MyBidsModel;
import com.Nuptist.Models.MyBookingModel;
import com.Nuptist.Models.MyFavoritePackageModel;
import com.Nuptist.Models.MyOffersBidModel;
import com.Nuptist.Models.NotificationModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.PackageViewModel;
import com.Nuptist.Models.PrivacyPolicyModel;
import com.Nuptist.Models.RecentPackagesModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.Models.SearchPackageModel;
import com.Nuptist.Models.SignUpModel;
import com.Nuptist.Models.VendorBidsModel;
import com.Nuptist.Models.VendorFcmModel;
import com.Nuptist.Models.VendorProfileModel;
import com.Nuptist.Models.VenuModel;
import com.Nuptist.Models.VenueImagesModel;
import com.Nuptist.MyBookings.MyBookingDetailsModel;
import com.Nuptist.firebase.firebase.NotiModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST(get_terms_condition)
    Call<TermsAndConditionModel> termsAndCondition(
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(SIGNUP_URL)
    Call<SignUpModel> signup(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("lat") String lat,
            @Field("lang") String lang,
            @Field("fcm_id") String fcm_id,
            @Field("country_code") String country_code
    );

    @FormUrlEncoded
    @POST(LOGIN_URL)
    Call<LoginModel> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("fcm_id") String fcm_id
    );


    @FormUrlEncoded
    @POST(LOGOUT_URL)
    Call<LogOutModel> logout(
            @Field("userid") String user_id
    );

    @FormUrlEncoded
    @POST(GET_PROFILE_URL)
    Call<AccountModel> get_profile(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(VENDOR_PROFILE)
    Call<VendorProfileModel> get_vendor_profile(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(FORGOT_PASSWORD_URL)
    Call<ForgotPasswordModel> forgot_password(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST(GOOGLE_LOGIN)
    Call<AccountModel> google_signin(
            @Field("email") String email,
            @Field("name") String name,
            @Field("fcm_id") String fcm_id,
            @Field("google_id") String google_id
    );


    @FormUrlEncoded
    @POST(FACEBOOK_LOGIN)
    Call<AccountModel> facebook_sign_in(
            @Field("name") String name,
            @Field("fcm_id") String fcm_id,
            @Field("facebook_id") String facebook_id
    );


    @GET(GET_PRIVACY_POLICY)
    Call<PrivacyPolicyModel> get_privacy();

    @FormUrlEncoded
    @POST(CHANGE_PASSWORD_URL)
    Call<AccountModel> changePassword(
            @Field("user_id") String user_id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password,
            @Field("confirm_password") String confirm_password
    );


    @FormUrlEncoded
    @POST(VENDOR_UPDATE_PROFILE)
    Call<VendorProfileModel> update_vendor(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("mname") String mname,
            @Field("lname") String lname,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("services_address") String services_address,
            @Field("service_abailble") String service_abailble,
            @Field("image") String image
    );


    @FormUrlEncoded
    @POST(GET_VENUE)
    Call<VenuModel> get_venue(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(UPDATE_VENUE)
    Call<VenuModel> update_venue(
            @Field("user_id") String user_id
    );


    @GET(GET_PACKAGES)
    Call<GetPackageModel> getPackage();


    @FormUrlEncoded
    @POST(GET_PACKAGES_DETAILS)
    Call<PackageDetailsModel> getPackagesDetails(
            @Field("package_id") String package_id,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(PACKAGE_VIEW)
    Call<PackageViewModel> packageView(
            @Field("user_id") String user_id,
            @Field("package_id") String package_id,
            @Field("vender_id") String vender_id
    );

    @FormUrlEncoded
    @POST(FAVORITE_PACKAGES)
    Call<PackageViewModel> favoritePackage(
            @Field("user_id") String user_id,
            @Field("package_id") String package_id
    );

    @FormUrlEncoded
    @POST(FAVORITE_lISTING)
    Call<MyFavoritePackageModel> favoriteList(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(get_venue_image)
    Call<VenueImagesModel> getVenueImages(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(add_bids)
    Call<LogOutModel> addBid(
            @Field("package_id") String package_id,
            @Field("user_id") String user_id,
            @Field("vender_id") String vender_id,
            @Field("offers") String offers,
            @Field("minimum_price") String minimum_price,
            @Field("maximum_price") String maximum_price,
            @Field("add_service_id") String add_service_id,
            @Field("add_product_id") String add_product_id,
            @Field("offer_product_id") String offer_product_id,
            @Field("offer_service_id") String offer_service_id
    );


    @FormUrlEncoded
    @POST(get_all_my_bids)
    Call<MyBidsModel> getMyAllBids(
            @Field("vender_id") String vender_id
    );


    @FormUrlEncoded
    @POST(GET_PACKAGES_BY_CITY)
    Call<GetPackageModel> getPackageByCity(
            @Field("city") String city
    );


    @FormUrlEncoded
    @POST(get_package_by_country_code)
    Call<CountryPackageModel> getPackageByCountry(
            @Field("country_code") String country_code
    );


    @FormUrlEncoded
    @POST(ADD_VIEW)
    Call<LogOutModel> addView(
            @Field("vender_id") String vender_id,
            @Field("package_id") String package_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_package_view)
    Call<RecentPackagesModel> getRecentPackaes(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(add_booking_date)
    Call<LogOutModel> addBookingDates(
            @Field("package_id") String package_id,
            @Field("user_id") String user_id,
            @Field("vender_id") String vender_id,
            @Field("total_package") String total_package,
            @Field("total_amount") String total_amount,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("transaction_id") String transaction_id,
            @Field("payment_method") String payment_method,
            @Field("offer_id") String offer_id,
            @Field("addonce_id") String addonce_id,
            @Field("package_name") String package_name,
            @Field("service_id") String service_id,
            @Field("product_id") String product_id,
            @Field("service_offer_details_id") String service_offer_details_id,
            @Field("product_offer_details_id") String product_offer_details_id,
            @Field("bids_addonce") String bids_addonce,
            @Field("bids_offer") String bids_offer

    );


    @FormUrlEncoded
    @POST(get_my_booking_package)
    Call<MyBookingModel> getMyBooking(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(ad_once_list_new)
    Call<AddOncesModelNew> getAllAddOnce(
            @Field("package_id") String package_id,
            @Field("vender_id") String vender_id
    );


    @FormUrlEncoded
    @POST(ad_once_list_new)
    Call<FinalAddonsNewModel> getFinalAllAddOnce(
            @Field("package_id") String package_id,
            @Field("vender_id") String vender_id
    );


    @FormUrlEncoded
    @POST(get_package_booking_date_by_list)
    Call<BookedDatesModel> getPackageBookedDates(
            @Field("package_id") String package_id
    );


    @FormUrlEncoded
    @POST(my_bids_booking_details)
    Call<MyBookingDetailsModel> getMyBookingDetails(
            @Field("booking_id") String booking_id
    );


    @FormUrlEncoded
    @POST(cancel_user_booking)
    Call<MyBookingDetailsModel> cancelMyBooking(
            @Field("booking_id") String booking_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_review_list)
    Call<ReviewModel> packageReview(
            @Field("package_id") String package_id
    );


    @FormUrlEncoded
    @POST(add_review)
    Call<LogOutModel> addReview(
            @Field("package_id") String package_id,
            @Field("user_id") String user_id,
            @Field("rating") String rating,
            @Field("message") String message,
            @Field("user_name") String user_name,
            @Field("user_image") String user_image,
            @Field("vendor_id") String vendor_id,
            @Field("date") String date,
            @Field("booking_id") String booking_id
    );

    @FormUrlEncoded
    @POST(get_seaech_city_by_package)
    Call<SearchPackageModel> getSearchPackageByCity(
            @Field("city") String city
    );


    @FormUrlEncoded
    @POST(get_all_offer_bid)
    Call<MyOffersBidModel> getMyOfferAllBids(
            @Field("vender_id") String vender_id
    );

    @FormUrlEncoded
    @POST(user_send_message)
    Call<LogOutModel> sendSmsToAdmin(
            @Field("user_id") String user_id,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST(vender_user_message)
    Call<LogOutModel> sendSmsToUserVendor(
            @Field("user_id") String user_id,
            @Field("vender_id") String vender_id,
            @Field("message") String message
    );


    @FormUrlEncoded
    @POST(get_booking_by_vender)
    Call<MyBookingModel> getVendorsBooking(
            @Field("vender_id") String vendor_id
    );

    @FormUrlEncoded
    @POST(get_accepted_bids_list)
    Call<VendorBidsModel> getAcceptedVendors(
            @Field("package_id") String package_id,
            @Field("addonce_service_id") String addonce_service_id,
            @Field("addonce_product_id") String addonce_product_id,
            @Field("offer_service_id") String offer_service_id,
            @Field("offer_product_id") String offer_product_id
    );


    @GET(notifications_list_show)
    Call<NotificationModel> getNotifications();


    @FormUrlEncoded
    @POST(user_vender_chat_list)
    Call<ChatListUserModel> getVendorsList(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(vender_user_chat_list)
    Call<ChatListUserModel> getUsersList(
            @Field("vender_id") String user_id
    );


    @POST("send")
    Call<NotiModel> sendNotification(
            @Header("Authorization") String key,
            @Header("Content-Type") String contentType,
            @Body Map<String, Object> body
    );


    @FormUrlEncoded
    @POST(get_fcm_by_vender)
    Call<VendorFcmModel> getVendorFcm(
            @Field("vender_id") String vender_id
    );


    @FormUrlEncoded
    @POST(service_product_details)
    Call<ServiceOfferModel> getServiceProduct(
            @Field("add_service_id") String add_service_id,
            @Field("add_product_id") String add_product_id,
            @Field("offer_service_id") String offer_service_id,
            @Field("offer_product_id") String offer_product_id
    );



    @FormUrlEncoded
    @POST(service_product_details)
    Call<ServiceProductModel> getServiceOffer(
            @Field("add_service_id") String add_service_id,
            @Field("add_product_id") String add_product_id,
            @Field("offer_service_id") String offer_service_id,
            @Field("offer_product_id") String offer_product_id
    );


    @FormUrlEncoded
    @POST(service_product_details)
    Call<OfferProductModel> getOfferProduct(
            @Field("add_service_id") String add_service_id,
            @Field("add_product_id") String add_product_id,
            @Field("offer_service_id") String offer_service_id,
            @Field("offer_product_id") String offer_product_id
    );

    @FormUrlEncoded
    @POST(service_product_details)
    Call<OfferServiceModel> getOfferService(
            @Field("add_service_id") String add_service_id,
            @Field("add_product_id") String add_product_id,
            @Field("offer_service_id") String offer_service_id,
            @Field("offer_product_id") String offer_product_id
    );


    @GET(get_package_by_review)
    Call<PackageByRatingModel> getPackageByRating();

    @FormUrlEncoded
    @POST(get_packages_by_date)
    Call<SearchPackageModel> getPackageByDate(
            @Field("start_date") String start_date,
            @Field("city") String city
    );

}
