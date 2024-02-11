package hmm.itam.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class HistoryVo {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyCompletionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyRequestDate;
    private String historyAssetType;
    private String historyType;
    private String historyRequester;
    private String historyWorker;
    private String historyMemberId;
    private String historyAssetNumber;
    private String historyRequestDetails;
    private String historyReqeustEtc;
    private String historySpec1;
    private String historySpec2;
    private String historySpec3;
}