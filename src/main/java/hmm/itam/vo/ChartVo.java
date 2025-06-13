package hmm.itam.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class ChartVo {

    private String modelType;
    private String assetModelName;
    private String assetNumber;
    private String modelReplacementType;
    private String departmentLocation;
    private String departmentRegion;
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
