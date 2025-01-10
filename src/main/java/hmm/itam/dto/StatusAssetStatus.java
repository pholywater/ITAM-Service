package hmm.itam.dto;

public enum StatusAssetStatus {

    양호("양호"),
    불량("불량"),
    분실("분실"),
    기타("기타"),
    매각("매각");

    private final String description;

    StatusAssetStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
