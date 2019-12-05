package com.shijiucheng.konghua.main.entity;

public class OwnerShop {



    private boolean isCheck;
    /**
     * store_id : 1237
     * store_name : 测试鲜花店
     * store_account : jua***ie
     * province_id_text :
     * city_id_text :
     * district_id_text :
     * add_time_text : 1970-01-01 08:00
     * add_authentication_time_text : 1970-01-01 08:00
     * store_contact_qq : null
     * store_contact_tel : null
     * delivery_district_ids_text :
     */

    private String store_id;
    private String store_name;
    private String store_account;
    private String province_id_text;
    private String city_id_text;
    private String district_id_text;
    private String add_time_text;
    private String add_authentication_time_text;
    private String store_contact_qq;
    private String store_contact_tel;
    private String delivery_district_ids_text;


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_account() {
        return store_account;
    }

    public void setStore_account(String store_account) {
        this.store_account = store_account;
    }

    public String getProvince_id_text() {
        return province_id_text;
    }

    public void setProvince_id_text(String province_id_text) {
        this.province_id_text = province_id_text;
    }

    public String getCity_id_text() {
        return city_id_text;
    }

    public void setCity_id_text(String city_id_text) {
        this.city_id_text = city_id_text;
    }

    public String getDistrict_id_text() {
        return district_id_text;
    }

    public void setDistrict_id_text(String district_id_text) {
        this.district_id_text = district_id_text;
    }

    public String getAdd_time_text() {
        return add_time_text;
    }

    public void setAdd_time_text(String add_time_text) {
        this.add_time_text = add_time_text;
    }

    public String getAdd_authentication_time_text() {
        return add_authentication_time_text;
    }

    public void setAdd_authentication_time_text(String add_authentication_time_text) {
        this.add_authentication_time_text = add_authentication_time_text;
    }

    public Object getStore_contact_qq() {
        return store_contact_qq;
    }

    public void setStore_contact_qq(String store_contact_qq) {
        this.store_contact_qq = store_contact_qq;
    }

    public Object getStore_contact_tel() {
        return store_contact_tel;
    }

    public void setStore_contact_tel(String store_contact_tel) {
        this.store_contact_tel = store_contact_tel;
    }

    public String getDelivery_district_ids_text() {
        return delivery_district_ids_text;
    }

    public void setDelivery_district_ids_text(String delivery_district_ids_text) {
        this.delivery_district_ids_text = delivery_district_ids_text;
    }
}
