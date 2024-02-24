package hmm.itam.dto;

import lombok.Data;


@Data
public class HeaderSearchDto {
    private String statusType;
    private String statusAssetUsage;
    private String modelType;
    private String memberName;
    private String memberRank;
    private String memberId;
    private String assetNumber;
    private String assetModelName;
    private String navSearch;

    /*public HeaderSearchDto(String navSearch) {
        this.navSearch = navSearch;

    }

    public static HeaderSearchDto of(final String navSearch) {
        return new HeaderSearchDto(navSearch);
    }*/
}
