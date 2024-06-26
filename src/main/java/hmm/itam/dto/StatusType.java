package hmm.itam.dto;

public enum StatusType {

    신규("신규"),
    출고("출고"),
    입고("입고"),
    기타("기타(기타 및 원장외)"),
    매각("매각(실물 없음)");

    private final String description;

    StatusType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
