package com.shijiucheng.konghua.main.entity;

import java.util.List;

public class GrabOrder {

    /**
     * order_id : 19306
     * order_sn : 20190926243159
     * order_status : 5
     * order_receive_store_id : 0
     * buyer : 杨林
     * buyer_tel : 13892307020
     * receiver : 接单后可看
     * receiver_tel : 接单后可看
     * receiver_country : 1
     * receiver_province : 28
     * receiver_city : 328
     * receiver_district : 3297
     * receiver_address : 人民路东风小学五年级
     * card_message :
     * order_remark : 同订单20190923547959一起配送
     * order_amount : 345.00
     * order_amount_add : 600.00
     * delivery_time : 按时段2019-09-26 20-22点
     * delivery_time_sort : 1569502740
     * store_pack_images : http://huamai.konghua.com
     * delivery_emp_uname :
     * delivery_emp_tel :
     * order_sign_images : http://huamai.konghua.com
     * order_sign_personnel_type : 1
     * order_sign_time :
     * order_sign_add_time : 0
     * order_sign_store_print_count : 0
     * order_cancel_add_time : 0
     * order_balance_add_time : 0
     * sale_admin_id : 0
     * delivery_admin_id : 0
     * add_admin_id : 0
     * add_time : 1569548070
     * update_time : 0
     * order_refuse_time : 0
     * order_add_type : 0
     * order_balance_remind_time : 0
     * order_balance_amount : 0.00
     * order_balance_communicate_content :
     * order_origin : ding
     * add_time_text : 2019-09-27 09:34
     * receiver_province_text : 陕西省
     * receiver_city_text : 渭南市
     * receiver_district_text : 白水县
     * delivery_time_text : 2019-09-26 20-22点
     * order_status_text : 请求涨价
     * auto_balance_time_text : 1970-01-08 08:00
     * is_order_balance_remind : 0
     * goods_list : [{"goods_id":"0","goods_name":"一起变老-99支精品红玫瑰","goods_number":"1","goods_thumb":"http://huamai.konghua.com/Upload/20190927/1569548070_77725.jpg","goods_material":"99支精品红玫瑰，搭配适量栀子叶。","goods_pack":"复古英文报纸扇形包装，外白色雾面纸托底，精美香槟色缎带束扎","add_time_text":"1970-01-01 08:00","goods_img":"http://huamai.konghua.com/Upload/20190927/1569548070_77725.jpg"},{"goods_id":"0","goods_name":"只为你疯狂-33支精品粉玫瑰","goods_number":"1","goods_thumb":"http://huamai.konghua.com/Upload/20190927/1569548070_42143.jpg","goods_material":"33支苏醒玫瑰，搭配适量白色石竹梅装饰。","goods_pack":"粉色雾面纸包装，精美香槟色蝴蝶结束扎。","add_time_text":"1970-01-01 08:00","goods_img":"http://huamai.konghua.com/Upload/20190927/1569548070_42143.jpg"},{"goods_id":"0","goods_name":"给你无尽的爱-19支精品红玫瑰","goods_number":"1","goods_thumb":"http://huamai.konghua.com/Upload/20190927/1569548071_25558.jpg","goods_material":"19支红玫瑰","goods_pack":"海瑟硬网纱+黑色雾面纸包装，灰色缎带束扎。","add_time_text":"1970-01-01 08:00","goods_img":"http://huamai.konghua.com/Upload/20190927/1569548071_25558.jpg"}]
     * delivery_time_count_text : -18小时
     * delivery_time_is_end : 1
     * order_amount_add_ratio : 73.91%
     */

    private String order_id;
    private String order_sn;
    private String order_status;
    private String order_receive_store_id;
    private String buyer;
    private String buyer_tel;
    private String receiver;
    private String receiver_tel;
    private String receiver_country;
    private String receiver_province;
    private String receiver_city;
    private String receiver_district;
    private String receiver_address;
    private String card_message;
    private String order_remark;
    private String order_amount;
    private String order_amount_add;
    private String delivery_time;
    private String delivery_time_sort;
    private String store_pack_images;
    private String delivery_emp_uname;
    private String delivery_emp_tel;
    private String order_sign_images;
    private String order_sign_personnel_type;
    private String order_sign_time;
    private String order_sign_add_time;
    private String order_sign_store_print_count;
    private String order_cancel_add_time;
    private String order_balance_add_time;
    private String sale_admin_id;
    private String delivery_admin_id;
    private String add_admin_id;
    private String add_time;
    private String update_time;
    private String order_refuse_time;
    private String order_add_type;
    private String order_balance_remind_time;
    private String order_balance_amount;
    private String order_balance_communicate_content;
    private String order_origin;
    private String add_time_text;
    private String receiver_province_text;
    private String receiver_city_text;
    private String receiver_district_text;
    private String delivery_time_text;
    private String order_status_text;
    private String auto_balance_time_text;
    private String is_order_balance_remind;
    private String delivery_time_count_text;
    private int delivery_time_is_end;
    private String order_amount_add_ratio;
    private List<GoodInfo> goods_list;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_receive_store_id() {
        return order_receive_store_id;
    }

    public void setOrder_receive_store_id(String order_receive_store_id) {
        this.order_receive_store_id = order_receive_store_id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyer_tel() {
        return buyer_tel;
    }

    public void setBuyer_tel(String buyer_tel) {
        this.buyer_tel = buyer_tel;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver_tel() {
        return receiver_tel;
    }

    public void setReceiver_tel(String receiver_tel) {
        this.receiver_tel = receiver_tel;
    }

    public String getReceiver_country() {
        return receiver_country;
    }

    public void setReceiver_country(String receiver_country) {
        this.receiver_country = receiver_country;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public void setReceiver_province(String receiver_province) {
        this.receiver_province = receiver_province;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getCard_message() {
        return card_message;
    }

    public void setCard_message(String card_message) {
        this.card_message = card_message;
    }

    public String getOrder_remark() {
        return order_remark;
    }

    public void setOrder_remark(String order_remark) {
        this.order_remark = order_remark;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_amount_add() {
        return order_amount_add;
    }

    public void setOrder_amount_add(String order_amount_add) {
        this.order_amount_add = order_amount_add;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getDelivery_time_sort() {
        return delivery_time_sort;
    }

    public void setDelivery_time_sort(String delivery_time_sort) {
        this.delivery_time_sort = delivery_time_sort;
    }

    public String getStore_pack_images() {
        return store_pack_images;
    }

    public void setStore_pack_images(String store_pack_images) {
        this.store_pack_images = store_pack_images;
    }

    public String getDelivery_emp_uname() {
        return delivery_emp_uname;
    }

    public void setDelivery_emp_uname(String delivery_emp_uname) {
        this.delivery_emp_uname = delivery_emp_uname;
    }

    public String getDelivery_emp_tel() {
        return delivery_emp_tel;
    }

    public void setDelivery_emp_tel(String delivery_emp_tel) {
        this.delivery_emp_tel = delivery_emp_tel;
    }

    public String getOrder_sign_images() {
        return order_sign_images;
    }

    public void setOrder_sign_images(String order_sign_images) {
        this.order_sign_images = order_sign_images;
    }

    public String getOrder_sign_personnel_type() {
        return order_sign_personnel_type;
    }

    public void setOrder_sign_personnel_type(String order_sign_personnel_type) {
        this.order_sign_personnel_type = order_sign_personnel_type;
    }

    public String getOrder_sign_time() {
        return order_sign_time;
    }

    public void setOrder_sign_time(String order_sign_time) {
        this.order_sign_time = order_sign_time;
    }

    public String getOrder_sign_add_time() {
        return order_sign_add_time;
    }

    public void setOrder_sign_add_time(String order_sign_add_time) {
        this.order_sign_add_time = order_sign_add_time;
    }

    public String getOrder_sign_store_print_count() {
        return order_sign_store_print_count;
    }

    public void setOrder_sign_store_print_count(String order_sign_store_print_count) {
        this.order_sign_store_print_count = order_sign_store_print_count;
    }

    public String getOrder_cancel_add_time() {
        return order_cancel_add_time;
    }

    public void setOrder_cancel_add_time(String order_cancel_add_time) {
        this.order_cancel_add_time = order_cancel_add_time;
    }

    public String getOrder_balance_add_time() {
        return order_balance_add_time;
    }

    public void setOrder_balance_add_time(String order_balance_add_time) {
        this.order_balance_add_time = order_balance_add_time;
    }

    public String getSale_admin_id() {
        return sale_admin_id;
    }

    public void setSale_admin_id(String sale_admin_id) {
        this.sale_admin_id = sale_admin_id;
    }

    public String getDelivery_admin_id() {
        return delivery_admin_id;
    }

    public void setDelivery_admin_id(String delivery_admin_id) {
        this.delivery_admin_id = delivery_admin_id;
    }

    public String getAdd_admin_id() {
        return add_admin_id;
    }

    public void setAdd_admin_id(String add_admin_id) {
        this.add_admin_id = add_admin_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getOrder_refuse_time() {
        return order_refuse_time;
    }

    public void setOrder_refuse_time(String order_refuse_time) {
        this.order_refuse_time = order_refuse_time;
    }

    public String getOrder_add_type() {
        return order_add_type;
    }

    public void setOrder_add_type(String order_add_type) {
        this.order_add_type = order_add_type;
    }

    public String getOrder_balance_remind_time() {
        return order_balance_remind_time;
    }

    public void setOrder_balance_remind_time(String order_balance_remind_time) {
        this.order_balance_remind_time = order_balance_remind_time;
    }

    public String getOrder_balance_amount() {
        return order_balance_amount;
    }

    public void setOrder_balance_amount(String order_balance_amount) {
        this.order_balance_amount = order_balance_amount;
    }

    public String getOrder_balance_communicate_content() {
        return order_balance_communicate_content;
    }

    public void setOrder_balance_communicate_content(String order_balance_communicate_content) {
        this.order_balance_communicate_content = order_balance_communicate_content;
    }

    public String getOrder_origin() {
        return order_origin;
    }

    public void setOrder_origin(String order_origin) {
        this.order_origin = order_origin;
    }

    public String getAdd_time_text() {
        return add_time_text;
    }

    public void setAdd_time_text(String add_time_text) {
        this.add_time_text = add_time_text;
    }

    public String getReceiver_province_text() {
        return receiver_province_text;
    }

    public void setReceiver_province_text(String receiver_province_text) {
        this.receiver_province_text = receiver_province_text;
    }

    public String getReceiver_city_text() {
        return receiver_city_text;
    }

    public void setReceiver_city_text(String receiver_city_text) {
        this.receiver_city_text = receiver_city_text;
    }

    public String getReceiver_district_text() {
        return receiver_district_text;
    }

    public void setReceiver_district_text(String receiver_district_text) {
        this.receiver_district_text = receiver_district_text;
    }

    public String getDelivery_time_text() {
        return delivery_time_text;
    }

    public void setDelivery_time_text(String delivery_time_text) {
        this.delivery_time_text = delivery_time_text;
    }

    public String getOrder_status_text() {
        return order_status_text;
    }

    public void setOrder_status_text(String order_status_text) {
        this.order_status_text = order_status_text;
    }

    public String getAuto_balance_time_text() {
        return auto_balance_time_text;
    }

    public void setAuto_balance_time_text(String auto_balance_time_text) {
        this.auto_balance_time_text = auto_balance_time_text;
    }

    public String getIs_order_balance_remind() {
        return is_order_balance_remind;
    }

    public void setIs_order_balance_remind(String is_order_balance_remind) {
        this.is_order_balance_remind = is_order_balance_remind;
    }

    public String getDelivery_time_count_text() {
        return delivery_time_count_text;
    }

    public void setDelivery_time_count_text(String delivery_time_count_text) {
        this.delivery_time_count_text = delivery_time_count_text;
    }

    public int getDelivery_time_is_end() {
        return delivery_time_is_end;
    }

    public void setDelivery_time_is_end(int delivery_time_is_end) {
        this.delivery_time_is_end = delivery_time_is_end;
    }

    public String getOrder_amount_add_ratio() {
        return order_amount_add_ratio;
    }

    public void setOrder_amount_add_ratio(String order_amount_add_ratio) {
        this.order_amount_add_ratio = order_amount_add_ratio;
    }

    public List<GoodInfo> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodInfo> goods_list) {
        this.goods_list = goods_list;
    }
}
