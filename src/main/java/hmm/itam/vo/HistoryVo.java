package hmm.itam.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class HistoryVo {
    private String historyId;
    private String assetSupplies;
    private String search;
    private String searchStart;
    private String searchEnd;
    private String searchType;
    private String historySearch;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyCompletionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyRequestDate;

    private String historyAssetType;
    private String historyType;
    private String historyRequester;
    private String historyWorker;
    private String historyOperatorId;
    private String historyOperatorName;
    private String historyMemberName;
    private String historyMemberId;
    private String historyAssetNumber;
    private String historyRequestUsage;
    private String historyRequestDetails;
    private String historyRequestEtc;
    private String historyRemarks;

    private String historyAssetEtc1;
    private String historyAssetEtc2;
    private String historyAssetEtc3;

    private String historySpec1;
    private String historySpec2;
    private String historySpec3;

    private String departmentName;
    private String memberDepartment;
    private String memberDepartmentDetail;
    private String departmentFloor;
    private String memberId;
    private String memberName;
    private String memberRank;

    private String modelName;
    private String AssetModelName;
    private String assetTypeCategory;
    private String assetLocation;
    private String assetStatus;
    private String statusAssetUsage;
    private String statusAssetEtc;
    private String statusAssetEtc1;
    private String statusAssetSpec2;
    private String statusAssetSpec3;
    private String assetNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyInsertDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyUpdateDate;
    private String tableName;


}
