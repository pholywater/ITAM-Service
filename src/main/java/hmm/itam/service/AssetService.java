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
import java.util.Date;
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


    /*잘 안쓰는거 */
    /*DataTables Server-side 조회 설정*/
    private HeaderSearchDto HeaderSearchDto;

    public HeaderSearchDto HeaderSearchDto(String navSearch) {
        return HeaderSearchDto;
    }

    /*상단 해더 검색과 서버 사이드 서치 검색어 통합 로직*/
    private String combineSearch(String navSearch, String search) {
        if (navSearch != null && !navSearch.isEmpty() && search != null && !search.isEmpty()) {
            return navSearch + " " + search;
        } else if (navSearch != null && !navSearch.isEmpty()) {
            return navSearch;
        } else {
            return search;
        }
    }

    public PageDto<List<String>> findAssetByPagination(PageDto pageDto) {
        int startNo = pageDto.getStart();
        int length = pageDto.getLength();
        int rowNo = pageDto.getStart();
        String navSearch = pageDto.getNavSearch();
        String search = pageDto.getSearch(); // DataTables 검색어
        // 검색 조건 통합 (예: navSearch + search)
        String combinedSearch = combineSearch(navSearch, search);
        // 총 레코드 수 (검색 조건 포함)
        int total = AssetMapper.countTotalAsset(combinedSearch);
        pageDto.setRecordsTotal(total);
        pageDto.setRecordsFiltered(total);
        log.info("Draw 받은 숫자 : {}", pageDto.getDraw());
        log.info("사용자한테 받은 페이지 Start : {}", pageDto.getStart());
        log.info("디비에 넘기기 위해 계산한 시작 번호 startNo : {}", startNo);
        log.info("디비에 넘기는 검색 조건 navSearch : {}", navSearch);
        log.info("DataTables 검색어 search[value] : {}", search);

        List<AssetVo> data;
        if (length == -1) {
            data = AssetMapper.findAssetByPagination(0, total, combinedSearch);
        } else {
            data = AssetMapper.findAssetByPagination(startNo, length, combinedSearch);
        }

        List<List<String>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        for (AssetVo assetVo : data) {
            List<String> list = new ArrayList<>();
            rowNo++;

            list.add(String.valueOf(rowNo)); // No
            list.add(assetVo.getStatusType()); // 구분
            list.add(assetVo.getStatusAssetStatus()); // 장비상태
            list.add(assetVo.getMemberId()); // 사원번호
            list.add(assetVo.getDepartmentLocation()); // 위치
            list.add(assetVo.getDepartmentRegion()); // 지역
            list.add(assetVo.getDepartmentFloor()); // 층
            list.add(assetVo.getDepartmentName()); // 부서
            list.add(assetVo.getMemberName()); // 이름
            list.add(assetVo.getMemberRank()); // 직급
            list.add(assetVo.getMemberWorkingStatus()); // 직원상태
            list.add(assetVo.getStatusAssetUsage()); // 장비용도
            list.add(assetVo.getStatusAssetEtc1()); // 듀얼현황
            list.add(assetVo.getAssetNumber()); // 장비번호
            list.add(assetVo.getModelType()); // 장비타입
            list.add(assetVo.getModelManufacturer()); // 장비제조사
            list.add(assetVo.getAssetModelName()); // 모델명
            list.add(assetVo.getStatusAssetSpec1() + " / " + assetVo.getStatusAssetSpec2()); // 스펙
            list.add(assetVo.getAssetWiredMacAddress() + " / " + assetVo.getAssetWirelessMacAddress()); // MAC ADDRESS
            list.add(assetVo.getAssetSerialNumber()); // 장비시리얼
            list.add(assetVo.getAssetArrivalDate() != null ? sdf.format(assetVo.getAssetArrivalDate()) : ""); // 도입일
            list.add(assetVo.getAssetPaymentDate() != null ? sdf.format(assetVo.getAssetPaymentDate()) : ""); // 최초지급일
            list.add(assetVo.getAssetLastUpdateDate() != null ? sdf.format(assetVo.getAssetLastUpdateDate()) : ""); // 최근업데이트
            list.add(assetVo.getStatusAssetEtc2()); // 용도상세
            list.add(assetVo.getAssetDuration()); // 지급기한
            String formHtml = "<form th:action='@{/assetSearch}' th:object='${assetVo}' method='post'>" +
                    "<input type='hidden' id='assetNumber' name='assetNumber' th:value='${u.assetNumber}'/>" +
                    "<button type='submit' class='btn btn-outline-primary btn-sm' " +
                    "formaction='assetSearch' formmethod='post' formtarget='_blank'>Search</button>" +
                    "</form>";

            list.add(formHtml);

            result.add(list);
        }


        pageDto.setData(result);
        return pageDto;
    }


}

