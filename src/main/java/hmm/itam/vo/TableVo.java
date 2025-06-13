package hmm.itam.vo;

import lombok.Data;

import java.util.Date;


@Data
public class TableVo {

    private String modelType;
    private String assetModelName;
    private String statusAssetUsage;
    private String modelReplacementType;
    private String departmentLocation;
    private String departmentRegion;
    private int totalCount;
    private int hmmWork;
    private int hmmRent;
    private int hmmPublic;
    private int hmmEtc;
    private int hmmHelp;
    private int hmmBroken;
    private int busanHelp;
    private int busanBroken;
    private int desktop;
    private int laptop;
    private int monitor;


    public static class getAssetPaymentDate extends Date {
    }

    public static class getAssetLastUpdateDate extends Date {
    }
}
