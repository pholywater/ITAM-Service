package hmm.itam.vo;

import lombok.Data;

@Data
public class AssetVo {
    private int idx;
    private String asset_number;
    private String asset_model_code;
    private String asset_model_name;
    private String asset_serial_number;
    private String asset_billing_date;
    private String asset_arrival_date;
    private String asset_payment_date;
    private String asset_payment_member_id;
    private String asset_payment_department;
    private String asset_payment_member_name;
    private String asset_payment_member_rank;
    private String asset_payment_details;
    private String asset_wired_mac_address;
    private String asset_wireless_mac_address;
    private String status_type;
    private String status_asset_status;
    private String status_member_id;
    private String status_asset_usage;
    private String status_asset_etc1;
    private String status_asset_spec1;
    private String status_asset_spec2;
    private String status_asset_spec3;
    private String status_asset_due_diligence;
    private String status_asset_etc2;
    private String asset_duration;
    private String asset_repair_history;
    private String asset_last_update_date;
    private String model_code;
    private String model_type;
    private String model_manufacturer;
    private String model_size;
    private String model_spec1;
    private String model_spec2;
    private String model_spec3;
    private String model_spec4;
    private String model_spec5;
    private String model_spec6;
    private String model_replaycement_type;
    private String member_id;
    private String member_name;
    private String member_rank;
    private String member_status;
    private String tgate_member_email;

    private Boolean open;


}