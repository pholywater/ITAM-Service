package hmm.itam.service;

import hmm.itam.dto.HeaderSearchDto;
import hmm.itam.dto.PageDto;
import hmm.itam.mapper.AssetMapper;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AssetService {
    @Autowired
    private AssetMapper assetMapper;


    /*부서 리스트 자동완성*/
    public List<AssetVo> getDepartmentList() {
        return assetMapper.getDepartmentList();
    }

    /*멤버 리스트 자동완성*/
    public List<AssetVo> getMemberList() {
        return assetMapper.getMemberList();
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
        return assetMapper.getAssetList();
    }

    /*전체 장비 리스트 업무용 장비*/
    public List<AssetVo> getAssetListAll() {
        return assetMapper.getAssetListAll();
    }

    /*출고 0장비 리스트*/
    public List<AssetVo> getAssetListOutput() {
        return assetMapper.getAssetListOutput();
    }

    /*장비 리스트 업무용 장비*/
    public List<AssetVo> getAssetListWork() {
        return assetMapper.getAssetListWork();
    }

    /*장비 리스트 대여 장비*/
    public List<AssetVo> getAssetListRent() {
        return assetMapper.getAssetListRent();
    }

    /*장비 리스트 공용 장비*/
    public List<AssetVo> getAssetListPublic() {
        return assetMapper.getAssetListPublic();
    }

    /*장비 리스트 재고 장비*/
    public List<AssetVo> getAssetListInput() {
        return assetMapper.getAssetListInput();
    }

    /*장비 리스트 재고 노트북 장비*/
    public List<AssetVo> getAssetListInputL() {
        return assetMapper.getAssetListInputL();
    }

    /*장비 리스트 재고 장비*/
    public List<AssetVo> getAssetListInputM() {
        return assetMapper.getAssetListInputM();
    }

    /*장비 리스트 신규 장비*/
    public List<AssetVo> getAssetListNew() {
        return assetMapper.getAssetListNew();
    }

    /*장비 리스트 부산 재고*/
    public List<AssetVo> getAssetListBusanInventory() {
        return assetMapper.getAssetListBusanInventory();
    }

    /*오늘 업데이트 내역 조회*/
    public List<AssetVo> getAssetListUpdateToday() {
        return assetMapper.getAssetListUpdateToday();
    }

    /*searchAssetDetail 장비 리스트 조회 클라이언트 검색*/
    public List<AssetVo> searchAssetList(String search, String searchType) {
        return assetMapper.searchAssetList(search, searchType);
    }

    /*searchMemberList 부서 및 직원 장비 리스트 조회 클라이언트 검색*/
    public List<AssetVo> searchMemberList(String searchMember) {
        return assetMapper.searchMemberList(searchMember);
    }

    /*searchPaymentList 신규장비 및 전체 장비 지급일 리스트 조회 클라이언트 검색*/
    public List<AssetVo> assetPaymentList(String searchStart, String searchEnd) {
        return assetMapper.searchPaymentList(searchStart, searchEnd);
    }

    /*상단 간편검색 - 이력관리 조회*/
    public List<AssetVo> historySearch(String navSearch) {
        return assetMapper.getHistorySearch(navSearch);
    }


    /*장비 정보 등록*/
    public void assetAdd(AssetVo assetVo) {
        assetMapper.insertAsset(assetVo);
    }

    /*이력 관리 등록*/
    public void historyAdd(AssetVo AssetVo) {
        assetMapper.insertHistory(AssetVo);
    }

    /*장비 조회 1*/
    public AssetVo assetSearch(String assetNumber) {
        return assetMapper.getAssetByAssetNumber(assetNumber);
    }

    /*장비정보 수정*/
    public void modifyInfo(AssetVo assetVo) {
        assetMapper.updateAsset(assetVo);
    }

    /*장비 삭제하기*/
    public void withdraw(AssetVo assetVo) {
        assetMapper.deleteAsset(assetVo);
    }

    private PageDto pageDto;
    /*DataTables Server-side 조회 설정*/
    private HeaderSearchDto HeaderSearchDto;

    public HeaderSearchDto HeaderSearchDto(String navSearch) {
        return HeaderSearchDto;
    }

    public PageDto<List<String>> findAssetByPagination(PageDto pageDto) {
        this.pageDto = pageDto;
        int startNo = pageDto.getStart();
        int length = pageDto.getLength();
        int rowNo = startNo;
        String navSearchHistory = pageDto.getNavSearchHistory();

        // ✅ 해더 우측 상단 검색
        String navSearch = pageDto.getNavSearch();
        String searchType = pageDto.getSearchType();
        // ✅ 해더 파라미터 접근 검색
        String viewType = pageDto.getViewType();
        String tableName = pageDto.getTableName();
        // ✅ dataTables Searching 창(2차 검색)
        String search = pageDto.getSearch();
        // ✅ 날짜별 추가 검색 변수
        String searchStart = pageDto.getSearchStart();
        String searchEnd = pageDto.getSearchEnd();

        // ✅ 테이블 이름 화이트리스트 검증
        Set<String> allowedTables = Set.of(
                "asset_list_all", "asset_list_busan_inventory", "asset_list_input",
                "asset_list_input_l", "asset_list_input_m", "asset_list_new", "asset_list_output",
                "asset_list_output_work", "asset_list_public", "asset_list_rent", "asset_list_update_today", "asset_list_work"
        );

        if (tableName == null || !allowedTables.contains(tableName)) {
            /*throw new IllegalArgumentException("허용되지 않은 테이블 이름입니다: " + tableName);*/
            tableName = ""; // 기본값 설정
        }
        // ✅ 정렬 컬럼 처리
        String[] columnNames = {
                "status_type",
                "status_asset_status",
                "member_id",
                "department_location",
                "department_region",
                "department_floor",
                "department_name",
                "member_name",
                "member_rank",
                "member_working_status",
                "status_asset_usage",
                "status_asset_etc1",
                "asset_number",
                "model_type",
                "model_manufacturer",
                "asset_model_name",
                "status_asset_spec1",
                "asset_wired_mac_address",
                "asset_serial_number",
                "asset_arrival_date",
                "asset_payment_date",
                "asset_last_update_date",
                "status_asset_etc2",
                "asset_duration"
        };

        // ✅ 컬럼 정렬 처리
        String orderDir = pageDto.getOrderDir();
        Integer orderColumn = pageDto.getOrderColumn();
        String orderByColumn = null;
        String direction = null;
        if (orderColumn != null && orderColumn > 0 && orderColumn - 1 < columnNames.length) {
            orderByColumn = columnNames[orderColumn - 1];
            direction = "ASC";
            if ("desc".equalsIgnoreCase(orderDir)) {
                direction = "DESC";
            }
        }

        log.info("(findAssetByPagination) 정렬 컬럼 orderColumn : {}", orderColumn);
        log.info("(findAssetByPagination) 정렬 방향 orderDir : {}", orderDir);
        log.info("(findAssetByPagination) viewType: {}", viewType);
        log.info("(findAssetByPagination) tableName: {}", tableName);
        log.info("(findAssetByPagination) navSearch: {}", navSearch);
        log.info("(findAssetByPagination) navSearchHistory: {}", navSearchHistory);
        log.info("(findAssetByPagination) searchType: {}", searchType);
        log.info("(findAssetByPagination) search: {}", search);
        log.info("(findAssetByPagination) searchStart: {}", searchStart);
        log.info("(findAssetByPagination) searchEnd: {}", searchEnd);

        // ✅ 총 레코드 수 조회
        int total = assetMapper.countTotalAsset(pageDto, searchType, navSearch, search, viewType, tableName, searchStart, searchEnd);
        pageDto.setRecordsTotal(total);
        pageDto.setRecordsFiltered(total);

        // ✅ 데이터 조회 패이징 처리시 LIMIT LENGTH 값 오류 나는 케이스 예외 처리(전체 보기)
        List<AssetVo> data = (length == -1)
                ? assetMapper.findAssetByPagination(pageDto, tableName, 0, total, searchType, navSearch, navSearchHistory, search, searchStart, searchEnd, orderByColumn, direction)
                : assetMapper.findAssetByPagination(pageDto, tableName, startNo, length, searchType, navSearch, navSearchHistory, search, searchStart, searchEnd, orderByColumn, direction);

        // ✅ 결과 포맷 변환
        List<List<String>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (AssetVo u : data) {
            List<String> list = new ArrayList<>();
            rowNo++;

            list.add(String.valueOf(rowNo));
            list.add(u.getStatusType());
            list.add(u.getStatusAssetStatus());
            list.add(u.getMemberId());
            list.add(u.getDepartmentLocation());
            list.add(u.getDepartmentRegion());
            list.add(u.getDepartmentFloor());
            list.add(u.getDepartmentName());
            list.add(u.getMemberName());
            list.add(u.getMemberRank());
            list.add(u.getMemberWorkingStatus());
            list.add(u.getStatusAssetUsage());
            list.add(u.getStatusAssetEtc1());
            list.add(u.getAssetNumber());
            list.add(u.getModelType());
            list.add(u.getModelManufacturer());
            list.add(u.getAssetModelName());
            list.add(u.getStatusAssetSpec1() + " / " + u.getStatusAssetSpec2());
            list.add(u.getAssetWiredMacAddress() + " / " + u.getAssetWirelessMacAddress());
            list.add(u.getAssetSerialNumber());
            list.add(u.getAssetArrivalDate() != null ? sdf.format(u.getAssetArrivalDate()) : "");
            list.add(u.getAssetPaymentDate() != null ? sdf.format(u.getAssetPaymentDate()) : "");
            list.add(u.getAssetLastUpdateDate() != null ? sdf.format(u.getAssetLastUpdateDate()) : "");
            list.add(u.getStatusAssetEtc2());
            list.add(u.getAssetDuration());

            String formHtml = "<form action='/assetSearch' method='post' target='_blank' style='margin:0px; padding:0px;'>" +
                    "<input type='hidden' name='assetNumber' value='" + u.getAssetNumber() + "'/>" +
                    "<button type='submit' class='btn btn-outline-primary btn-sm m-0 p-1'>Search</button>" +
                    "</form>";
            list.add(formHtml);

            result.add(list);
        }

        pageDto.setData(result);
        return pageDto;
    }


}

