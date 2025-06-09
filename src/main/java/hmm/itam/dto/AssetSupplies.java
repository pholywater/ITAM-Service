package hmm.itam.dto;

public enum AssetSupplies {

    ASSET_900_001("900-001", "무선 키보드 마우스 세트"),
    ASSET_900_002("900-002", "유선 키보드(K120) "),
    ASSET_900_003("900-003", "무선 마우스(M331)"),
    ASSET_900_008("900-008", "C to HDMI 케이블"),
    ASSET_900_011("900-011", "RAM DDR4(L) 16GB"),
    ASSET_900_004("900-004", "USB허브(A) 4포트"),
    ASSET_900_005("900-005", "USB허브(A LAN) 3포트"),
    ASSET_900_006("900-006", "USB허브(C LAN) 3포트"),
    ASSET_900_007("900-007", "USB허브(A SD카드) 3포트"),
    ASSET_900_009("900-009", "NB 나노 시건장치"),
    ASSET_900_010("900-010", "RAM DDR4(L) 8GB"),
    ASSET_900_012("900-012", "DP포트 더미 플러그"),
    ASSET_900_013("900-013", "무선 키보드 마우스 세트");

    private final String code;
    private final String description;

    AssetSupplies(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}