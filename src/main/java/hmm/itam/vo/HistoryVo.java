package hmm.itam.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class HistoryVo {
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
    private String historyMemberName;
    private String historyMemberId;
    private String historyAssetNumber;
    private String historyRequestUsage;
    private String historyRequestDetails;
    private String historyReqeustEtc;
    private String historyAssetEtc1;
    private String historySpec1;
    private String historySpec2;
    private String historySpec3;
    private String departmentName;
    private String memberName;
    private String memberRank;
    private String assetModelName;
}