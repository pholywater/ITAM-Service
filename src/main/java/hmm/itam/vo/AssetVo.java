package hmm.itam.vo;

import lombok.Data;

@Data
public class AssetVo {
    private Long idx;
    private String status_type;
    private String status_asset_status;
    private String status_asset_number;
    private String status_member_id;
    private String status_asset_usage;
    private String model_manufacturer;
    private String member_id;
    private String member_name;
    private String member_rank;
    private String member_status;
    private String member_etc1;
    private String member_etc2;
    private String asset_number;
    private String asset_serial_number;
    private String asset_arrival_date;
    private String asset_model_name;
    private String model_name;
    private String model_type;
    private String tgate_member_email;
    private String tgate_member_department;
}
