package hmm.itam.service;

import hmm.itam.dto.HeaderSearchDto;
import hmm.itam.dto.PageDto;
import hmm.itam.mapper.AssetMapper;
import hmm.itam.vo.AssetVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AssetService {
    @Autowired
    private AssetMapper AssetMapper;


    /*부서 리스트 자동완성*/
    public List<AssetVo> getDepartmentList() {
        return AssetMapper.getDepartmentList();
    }

    /*멤버 리스트 자동완성*/
    public List<AssetVo> getMemberList() {
        return AssetMapper.getMemberList();
    }

    /*재고장비 수량 차트 리스트*/
   /* public List<AssetVo> getChart1List() {
        return AssetMapper.getChart1List();
    }

    public List<AssetVo> getChart2List() {
        return AssetMapper.getChart2List();
    }*/


    /*장비 리스트 자동완성*/
    public List<AssetVo> getAssetList() {
        return AssetMapper.getAssetList();
    }

    /*전체 장비 리스트 업무용 장비*/
    public List<AssetVo> getAssetListAll() {
        return AssetMapper.getAssetListAll();
    }

    /*출고 0장비 리스트*/
    public List<AssetVo> getAssetListOutput() {
        return AssetMapper.getAssetListOutput();
    }

    /*장비 리스트 업무용 장비*/
    public List<AssetVo> getAssetListWork() {
        return AssetMapper.getAssetListWork();
    }

    /*장비 리스트 대여 장비*/
    public List<AssetVo> getAssetListRent() {
        return AssetMapper.getAssetListRent();
    }

    /*장비 리스트 공용 장비*/
    public List<AssetVo> getAssetListPublic() {
        return AssetMapper.getAssetListPublic();
    }

    /*장비 리스트 재고 장비*/
    public List<AssetVo> getAssetListInput() {
        return AssetMapper.getAssetListInput();
    }

    /*장비 리스트 재고 노트북 장비*/
    public List<AssetVo> getAssetListInputL() {
        return AssetMapper.getAssetListInputL();
    }

    /*장비 리스트 재고 장비*/
    public List<AssetVo> getAssetListInputM() {
        return AssetMapper.getAssetListInputM();
    }

    /*장비 리스트 신규 장비*/
    public List<AssetVo> getAssetListNew() {
        return AssetMapper.getAssetListNew();
    }

    /*장비 리스트 부산 재고*/
    public List<AssetVo> getAssetListBusanInventory() {
        return AssetMapper.getAssetListBusanInventory();
    }

    /*오늘 업데이트 내역 조회*/
    public List<AssetVo> getAssetUpdateToday() {
        return AssetMapper.getAssetUpdateToday();
    }

    /*searchAssetDetail 장비 리스트 조회 클라이언트 검색*/
    public List<AssetVo> searchAssetList(String search, String searchType) {
        return AssetMapper.searchAssetList(search, searchType);
    }

    /*searchMemberList 부서 및 직원 장비 리스트 조회 클라이언트 검색*/
    public List<AssetVo> searchMemberList(String searchMember) {
        return AssetMapper.searchMemberList(searchMember);
    }

    /*searchPaymentList 신규장비 및 전체 장비 지급일 리스트 조회 클라이언트 검색*/
    public List<AssetVo> assetPaymentList(String searchStart, String searchEnd) {
        return AssetMapper.searchPaymentList(searchStart, searchEnd);
    }

    /*상단 간편검색 - 이력관리 조회*/
    public List<AssetVo> historySearch(String navSearch) {
        return AssetMapper.getHistorySearch(navSearch);
    }


    /*장비 정보 등록*/
    public void assetAdd(AssetVo assetVo) {
        AssetMapper.insertAsset(assetVo);
    }

    /*이력 관리 등록*/
    public void historyAdd(AssetVo AssetVo) {
        AssetMapper.insertHistory(AssetVo);
    }

    /*장비 조회 1*/
    public AssetVo assetSearch(String assetNumber) {
        return AssetMapper.getAssetByAssetNumber(assetNumber);
    }

    /*장비정보 수정*/
    public void modifyInfo(AssetVo assetVo) {
        AssetMapper.updateAsset(assetVo);
    }

    /*장비 삭제하기*/
    public void withdraw(AssetVo assetVo) {
        AssetMapper.deleteAsset(assetVo);
    }


    /*DataTables Server-side 조회 설정*/
    private HeaderSearchDto HeaderSearchDto;

    public HeaderSearchDto HeaderSearchDto(String navSearch) {
        return HeaderSearchDto;
    }

    public PageDto<List<String>> findAssetByPagination(PageDto pageDto) {
        // 페이징 및 검색 파라미터 추출
        int startNo = pageDto.getStart();
        int length = pageDto.getLength();
        int rowNo = pageDto.getStart();
        String navSearch = pageDto.getNavSearch();
        String searchValue = pageDto.getSearch();

        // 정렬 파라미터 추출
        Integer orderColumn = pageDto.getOrderColumn(); // DataTables 기준 (0번은 ROW 번호)
        String orderDir = pageDto.getOrderDir();

        // 서버 컬럼 인덱스는 DataTables 기준에서 1을 뺀 값으로 보정
        String[] columnNames = {
                "asset_info.status_type",               // index 0 → DataTables index 1
                "asset_info.status_asset_status",       // index 1 → DataTables index 2
                "hmm_member.member_id",                 // index 2 → DataTables index 3
                "hmm_department.department_location",
                "hmm_department.department_region",
                "hmm_department.department_floor",
                "hmm_department.department_name",
                "hmm_member.member_name",
                "hmm_member.member_rank",
                "hmm_member.member_working_status",
                "asset_info.status_asset_usage",
                "asset_info.status_asset_etc1",
                "asset_info.asset_number",
                "asset_model.model_type",
                "asset_model.model_manufacturer",
                "asset_info.asset_model_name",
                "asset_info.status_asset_spec1",
                "asset_info.asset_wired_mac_address",
                "asset_info.asset_serial_number",
                "asset_info.asset_arrival_date",
                "asset_info.asset_payment_date",
                "asset_info.asset_last_update_date",
                "asset_info.status_asset_etc2",
                "asset_info.asset_duration"
        };

        // 정렬 컬럼 및 방향 설정
        String orderByColumn = null;
        String direction = null;

        if (orderColumn != null && orderColumn > 0 && orderColumn - 1 < columnNames.length) {
            orderByColumn = columnNames[orderColumn - 1]; // DataTables index → 서버 index 보정
            direction = "ASC";
            if ("desc".equalsIgnoreCase(orderDir)) {
                direction = "DESC";
            }
        }

        log.info("정렬 컬럼: {}", orderByColumn);
        log.info("정렬 방향: {}", direction);

        // 총 레코드 수 조회
        int total = AssetMapper.countTotalAsset(navSearch, searchValue);
        pageDto.setRecordsTotal(total);
        pageDto.setRecordsFiltered(total);

        // 데이터 조회
        List<AssetVo> data = (length == -1)
                ? AssetMapper.findAssetByPagination(0, total, navSearch, searchValue, orderByColumn, direction)
                : AssetMapper.findAssetByPagination(startNo, length, navSearch, searchValue, orderByColumn, direction);

        // 결과 가공
        List<List<String>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (AssetVo assetVo : data) {
            List<String> list = new ArrayList<>();
            rowNo++;

            list.add(String.valueOf(rowNo)); // No
            list.add(assetVo.getStatusType());
            list.add(assetVo.getStatusAssetStatus());
            list.add(assetVo.getMemberId());
            list.add(assetVo.getDepartmentLocation());
            list.add(assetVo.getDepartmentRegion());
            list.add(assetVo.getDepartmentFloor());
            list.add(assetVo.getDepartmentName());
            list.add(assetVo.getMemberName());
            list.add(assetVo.getMemberRank());
            list.add(assetVo.getMemberWorkingStatus());
            list.add(assetVo.getStatusAssetUsage());
            list.add(assetVo.getStatusAssetEtc1());
            list.add(assetVo.getAssetNumber());
            list.add(assetVo.getModelType());
            list.add(assetVo.getModelManufacturer());
            list.add(assetVo.getAssetModelName());
            list.add(assetVo.getStatusAssetSpec1() + " / " + assetVo.getStatusAssetSpec2());
            list.add(assetVo.getAssetWiredMacAddress() + " / " + assetVo.getAssetWirelessMacAddress());
            list.add(assetVo.getAssetSerialNumber());
            list.add(assetVo.getAssetArrivalDate() != null ? sdf.format(assetVo.getAssetArrivalDate()) : "");
            list.add(assetVo.getAssetPaymentDate() != null ? sdf.format(assetVo.getAssetPaymentDate()) : "");
            list.add(assetVo.getAssetLastUpdateDate() != null ? sdf.format(assetVo.getAssetLastUpdateDate()) : "");
            list.add(assetVo.getStatusAssetEtc2());
            list.add(assetVo.getAssetDuration());

            // 상세조회 버튼
            String formHtml = "<form action='/assetSearch' method='post' target='_blank'>" +
                    "<input type='hidden' name='assetNumber' value='" + assetVo.getAssetNumber() + "'/>" +
                    "<button type='submit' class='btn btn-outline-primary btn-sm'>Search</button>" +
                    "</form>";
            list.add(formHtml);

            result.add(list);
        }

        pageDto.setData(result);
        return pageDto;
    }


}

