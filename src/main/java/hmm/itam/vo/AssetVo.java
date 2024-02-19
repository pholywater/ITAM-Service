package hmm.itam.vo;

import hmm.itam.dto.HeaderSearchDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class AssetVo {
    private String headerSearch;
    private String HeaderSearchDto;
    private String assetNum;
    private int idx;
    private String assetNumber;
    private String assetModelCode;
    private String assetModelName;
    private String assetSerialNumber;
    private String assetBillingDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date assetArrivalDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date assetPaymentDate;
    private String assetPaymentMemberId;
    private String assetPaymentDepartment;
    private String assetPaymentMemberName;
    private String assetPaymentMemberRank;
    private String assetPaymentDetails;
    private String assetWiredMacAddress;
    private String assetWirelessMacAddress;
    private String statusType;
    private String statusAssetStatus;
    private String statusMemberId;
    private String statusAssetUsage;
    private String statusAssetEtc1;
    private String statusAssetSpec1;
    private String statusAssetSpec2;
    private String statusAssetSpec3;
    private String statusAssetDueDiligence;
    private String statusAssetEtc2;
    private String assetDuration;
    private String assetRepairHistory;
    private String assetLastUpdateDate;
    private String modelCode;
    private String modelType;
    private String modelManufacturer;
    private String modelSize;
    private String modelSpec1;
    private String modelSpec2;
    private String modelSpec3;
    private String modelSpec4;
    private String modelSpec5;
    private String modelSpec6;
    private String modelReplaycementType;
    private String memberId;
    private String memberName;
    private String memberRank;
    private String memberStatus;
    private String tgateMemberEmail;
    private String tgateMemberDepartment;

    public static class getAssetPaymentDate extends Date {
    }
}
