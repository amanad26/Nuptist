package com.Nuptist.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("data")
    @Expose
    private AccountData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AccountData getData() {
        return data;
    }

    public void setData(AccountData data) {
        this.data = data;
    }



    public  class AccountData{


        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("lname")
        @Expose
        private String lname;
        @SerializedName("mname")
        @Expose
        private String mname;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("verify_otp")
        @Expose
        private String verifyOtp;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lang")
        @Expose
        private String lang;
        @SerializedName("services_address")
        @Expose
        private String servicesAddress;
        @SerializedName("service_abailble")
        @Expose
        private String serviceAbailble;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("service_provider")
        @Expose
        private String serviceProvider;
        @SerializedName("email_status")
        @Expose
        private String emailStatus;
        @SerializedName("phone_status")
        @Expose
        private String phoneStatus;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("fcm_id")
        @Expose
        private String fcmId;
        @SerializedName("offline")
        @Expose
        private String offline;
        @SerializedName("profession_id")
        @Expose
        private String professionId;
        @SerializedName("facebook_id")
        @Expose
        private String facebookId;
        @SerializedName("google_id")
        @Expose
        private String googleId;
        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("video2")
        @Expose
        private String video2;
        @SerializedName("video3")
        @Expose
        private String video3;
        @SerializedName("id_proof")
        @Expose
        private String idProof;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("skills")
        @Expose
        private String skills;
        @SerializedName("qualification")
        @Expose
        private String qualification;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("pr_hours")
        @Expose
        private String prHours;
        @SerializedName("pr_days")
        @Expose
        private String prDays;
        @SerializedName("min_charge")
        @Expose
        private String minCharge;
        @SerializedName("extra_charge")
        @Expose
        private String extraCharge;
        @SerializedName("profile")
        @Expose
        private String profile;
        @SerializedName("job_points")
        @Expose
        private String jobPoints;
        @SerializedName("online_status")
        @Expose
        private String onlineStatus;
        @SerializedName("vender_id")
        @Expose
        private String vender_id;
        @SerializedName("country_code")
        @Expose
        private String country_code;

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getVender_id() {
            return vender_id;
        }

        public void setVender_id(String vender_id) {
            this.vender_id = vender_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getVerifyOtp() {
            return verifyOtp;
        }

        public void setVerifyOtp(String verifyOtp) {
            this.verifyOtp = verifyOtp;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getServicesAddress() {
            return servicesAddress;
        }

        public void setServicesAddress(String servicesAddress) {
            this.servicesAddress = servicesAddress;
        }

        public String getServiceAbailble() {
            return serviceAbailble;
        }

        public void setServiceAbailble(String serviceAbailble) {
            this.serviceAbailble = serviceAbailble;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getServiceProvider() {
            return serviceProvider;
        }

        public void setServiceProvider(String serviceProvider) {
            this.serviceProvider = serviceProvider;
        }

        public String getEmailStatus() {
            return emailStatus;
        }

        public void setEmailStatus(String emailStatus) {
            this.emailStatus = emailStatus;
        }

        public String getPhoneStatus() {
            return phoneStatus;
        }

        public void setPhoneStatus(String phoneStatus) {
            this.phoneStatus = phoneStatus;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getFcmId() {
            return fcmId;
        }

        public void setFcmId(String fcmId) {
            this.fcmId = fcmId;
        }

        public String getOffline() {
            return offline;
        }

        public void setOffline(String offline) {
            this.offline = offline;
        }

        public String getProfessionId() {
            return professionId;
        }

        public void setProfessionId(String professionId) {
            this.professionId = professionId;
        }

        public String getFacebookId() {
            return facebookId;
        }

        public void setFacebookId(String facebookId) {
            this.facebookId = facebookId;
        }

        public String getGoogleId() {
            return googleId;
        }

        public void setGoogleId(String googleId) {
            this.googleId = googleId;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getVideo2() {
            return video2;
        }

        public void setVideo2(String video2) {
            this.video2 = video2;
        }

        public String getVideo3() {
            return video3;
        }

        public void setVideo3(String video3) {
            this.video3 = video3;
        }

        public String getIdProof() {
            return idProof;
        }

        public void setIdProof(String idProof) {
            this.idProof = idProof;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getSkills() {
            return skills;
        }

        public void setSkills(String skills) {
            this.skills = skills;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getPrHours() {
            return prHours;
        }

        public void setPrHours(String prHours) {
            this.prHours = prHours;
        }

        public String getPrDays() {
            return prDays;
        }

        public void setPrDays(String prDays) {
            this.prDays = prDays;
        }

        public String getMinCharge() {
            return minCharge;
        }

        public void setMinCharge(String minCharge) {
            this.minCharge = minCharge;
        }

        public String getExtraCharge() {
            return extraCharge;
        }

        public void setExtraCharge(String extraCharge) {
            this.extraCharge = extraCharge;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getJobPoints() {
            return jobPoints;
        }

        public void setJobPoints(String jobPoints) {
            this.jobPoints = jobPoints;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }




    }
}
