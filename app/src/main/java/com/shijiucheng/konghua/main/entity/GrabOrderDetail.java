package com.shijiucheng.konghua.main.entity;

import java.util.List;

public class GrabOrderDetail {

    /**
     * total_count : 1
     * rows : [
     * {"order_id":"19306","order_sn":"20190926243159","order_status":"5","order_receive_store_id":"0","buyer":"杨林","buyer_tel":"13892307020","receiver":"接单后可看","receiver_tel":"接单后可看","receiver_country":"1","receiver_province":"28","receiver_city":"328","receiver_district":"3297","receiver_address":"人民路东风小学五年级","card_message":"","order_remark":"同订单20190923547959一起配送","order_amount":"345.00","order_amount_add":"600.00","delivery_time":"按时段2019-09-26 20-22点","delivery_time_sort":"1569502740","store_pack_images":"http://huamai.konghua.com","delivery_emp_uname":"","delivery_emp_tel":"","order_sign_images":"http://huamai.konghua.com","order_sign_personnel_type":"1","order_sign_time":"","order_sign_add_time":"0","order_sign_store_print_count":"0","order_cancel_add_time":"0","order_balance_add_time":"0","sale_admin_id":"0","delivery_admin_id":"0","add_admin_id":"0","add_time":"1569548070","update_time":"0","order_refuse_time":"0","order_add_type":"0","order_balance_remind_time":"0","order_balance_amount":"0.00","order_balance_communicate_content":"","order_origin":"ding","add_time_text":"2019-09-27 09:34","receiver_province_text":"陕西省","receiver_city_text":"渭南市","receiver_district_text":"白水县","delivery_time_text":"2019-09-26 20-22点","order_status_text":"请求涨价","auto_balance_time_text":"1970-01-08 08:00","is_order_balance_remind":"0","goods_list":[{"goods_id":"0","goods_name":"一起变老-99支精品红玫瑰","goods_number":"1","goods_thumb":"http://huamai.konghua.com/Upload/20190927/1569548070_77725.jpg","goods_material":"99支精品红玫瑰，搭配适量栀子叶。","goods_pack":"复古英文报纸扇形包装，外白色雾面纸托底，精美香槟色缎带束扎","add_time_text":"1970-01-01 08:00","goods_img":"http://huamai.konghua.com/Upload/20190927/1569548070_77725.jpg"},{"goods_id":"0","goods_name":"只为你疯狂-33支精品粉玫瑰","goods_number":"1","goods_thumb":"http://huamai.konghua.com/Upload/20190927/1569548070_42143.jpg","goods_material":"33支苏醒玫瑰，搭配适量白色石竹梅装饰。","goods_pack":"粉色雾面纸包装，精美香槟色蝴蝶结束扎。","add_time_text":"1970-01-01 08:00","goods_img":"http://huamai.konghua.com/Upload/20190927/1569548070_42143.jpg"},{"goods_id":"0","goods_name":"给你无尽的爱-19支精品红玫瑰","goods_number":"1","goods_thumb":"http://huamai.konghua.com/Upload/20190927/1569548071_25558.jpg","goods_material":"19支红玫瑰","goods_pack":"海瑟硬网纱+黑色雾面纸包装，灰色缎带束扎。","add_time_text":"1970-01-01 08:00","goods_img":"http://huamai.konghua.com/Upload/20190927/1569548071_25558.jpg"}],"delivery_time_count_text":"-18小时","delivery_time_is_end":1,"order_amount_add_ratio":"73.91%"}]
     * all_count : 0
     * order_pool_amount_count : 1
     */

    private String total_count;
    private String all_count;
    private String order_pool_amount_count;
    private List<GrabOrder> rows;

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getAll_count() {
        return all_count;
    }

    public void setAll_count(String all_count) {
        this.all_count = all_count;
    }

    public String getOrder_pool_amount_count() {
        return order_pool_amount_count;
    }

    public void setOrder_pool_amount_count(String order_pool_amount_count) {
        this.order_pool_amount_count = order_pool_amount_count;
    }

    public List<GrabOrder> getRows() {
        return rows;
    }

    public void setRows(List<GrabOrder> rows) {
        this.rows = rows;
    }
}
