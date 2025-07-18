package hmm.itam.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class bak_AssetDto {

    // 직원 정보 관련
    private String memberId;               // 사원번호
    private String memberName;             // 이름
    private String memberDepartment;       // 부서
    private String memberDepartmentDetail; // 부서 상세
    private String departmentFloor;        // 층
    private String departmentLocation;     // 위치
    private String departmentRegion;       // 지역
    private String memberRank;             // 직급
    private String memberWorkingStatus;    // 직원 상태

    // 장비 정보 관련
    private String assetNumber;            // 장비 번호
    private String modelType;              // 장비 타입
    private String modelManufacturer;      // 제조사
    private String assetModelName;         // 모델명
    private String statusAssetSpec1;       // SPEC1
    private String statusAssetSpec2;       // SPEC2
    private String assetWiredMacAddress;   // MAC (유선)
    private String assetWirelessMacAddress;// MAC (무선)
    private String assetSerialNumber;      // 시리얼 번호

    // 상태 및 용도
    private String statusType;             // 구분
    private String statusAssetStatus;      // 장비 상태
    private String statusAssetUsage;       // 용도
    private String statusAssetEtc1;        // 트리플
    private String statusAssetEtc2;        // 용도 상세

    // 날짜 관련
    private LocalDate assetPaymentDate;    // 지급일
    private LocalDate assetLastUpdateDate; // 최근 실사일

    // 기타
    private String assetDuration;          // 지급 기한
}
