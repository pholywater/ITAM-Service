package hmm.itam.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class AssetVo {
    private String CountUsage;
    private String chartType;
    private String headerSearch;
    private String navbarSearch;
    private String navSearch;
    private String searchType;
    private String search;
    private String searchStart;
    private String searchEnd;
    private String searchDepart;
    private String searchMember;
    private String searchName;
    private String navSelectBox;
    private String HeaderSearchDto;
    private String assetNum;
    private int idx;
    private int assetIdx;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date assetLastUpdateDate;
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
    private String memberDepartment;
    private String memberDepartmentDetail;
    private String memberRank;
    private String memberStatus;
    private String memberWorkingStatus;
    private String departmentDetail;
    private String departmentLocation;
    private String departmentRegion;
    private String departmentFloor;
    private String departmentName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyCompletionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyRequestDate;
    private String historyAssetType;
    private String historyType;
    private String historyRequester;
    private String historyWorker;
    private String historyMemberName;
    private String historyMemberId;
    private String historyAssetNumber;
    private String historyRequestDetails;
    private String historyRequestEtc;
    private String historySpec1;
    private String historySpec2;
    private String historySpec3;
    private String historyCheck;
    private String historyAssetEtc1;
    private String updateCheck;
    private String combineSearch;
    private int assetGrant;
    private int totalCount;
    private int hmmWork;
    private int hmmRent;
    private int hmmPublic;
    private int hmmHelp;
    private int hmmBroken;
    private int busanHelp;
    private int busanBroken;


    public static class getAssetPaymentDate extends Date {
    }

    public static class getAssetLastUpdateDate extends Date {
    }
}
