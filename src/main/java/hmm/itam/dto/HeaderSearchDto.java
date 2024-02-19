package hmm.itam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class HeaderSearchDto {
    private String statusType;
    private String statusAssetUsage;
    private String assetType;
}
