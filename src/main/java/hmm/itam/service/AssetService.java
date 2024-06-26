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
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AssetService {
    @Autowired
    private AssetMapper AssetMapper;
    private HeaderSearchDto HeaderSearchDto;

    public List<AssetVo> assetHeaderSearch(String navbarSearch) {
        return AssetMapper.searchByNavbar(navbarSearch);
    }

    public List<AssetVo> getAssetList() {
        return AssetMapper.getAssetList();
    }

    /*NavbarSearch 메인 화면 우측 상단 클라이언트 검색*/
    public List<AssetVo> navbarSearch(String navbarSearch) {
        return AssetMapper.searchByNavbar(navbarSearch);
    }

    public List<AssetVo> historySearch(String navbarSearch) {
        return AssetMapper.getHistorySearch(navbarSearch);
    }

    /*DataTables Server-side 조회 설정*/
    public PageDto<List<String>> findAssetByPagination(PageDto pageDto) {
        int startNo = pageDto.getStart();
        int length = pageDto.getLength();
        int rowNo = pageDto.getStart();
        String navSearch = pageDto.getNavSearch();
        pageDto.setRecordsTotal(AssetMapper.countTotalAsset(navSearch));
        pageDto.setRecordsFiltered(pageDto.getRecordsTotal());

        log.info("Draw 받은 숫자 : {}", pageDto.getDraw());
        log.info("사용자한테 받은 페이지 Start : {}", pageDto.getStart());
        log.info("디비에 넘기기 위해 계산한 시작 번호 startNo : {}", startNo);
        log.info("디비에 넘기는 검색 조건 navSearch : {}", pageDto.getNavSearch());
        //log.info("디비로 넘길 SearchBox search[value] 값: {}", pageDto.getSearch());

        List<AssetVo> data = new ArrayList<>();
        if (length == -1) {
            //전체
            //TODO 전체 조회 쿼리 명시
            data = AssetMapper.findAssetByPagination(0, pageDto.getRecordsTotal(), navSearch);
        } else {
            //페이징
            data = AssetMapper.findAssetByPagination(startNo, length, navSearch);
        }

        List<List<String>> result = new ArrayList<>();
        for (AssetVo assetVo : data) {
            List<String> list = new ArrayList<>();
            rowNo = rowNo + 1;
            list.add(String.valueOf(rowNo));
            list.add(assetVo.getMemberId());
            list.add(assetVo.getDepartmentLocation());
            list.add(assetVo.getDepartmentRegion());
            list.add(assetVo.getDepartmentFloor());
            list.add(assetVo.getDepartmentName());
            list.add(assetVo.getMemberName());
            list.add(assetVo.getMemberRank());
            list.add(assetVo.getAssetNumber());
            list.add(assetVo.getModelType());
            list.add(assetVo.getModelManufacturer());
            list.add(assetVo.getAssetModelName());
            list.add(assetVo.getStatusAssetSpec1());
            list.add(assetVo.getStatusAssetSpec2());
            Date date = new AssetVo.getAssetPaymentDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String AssetPaymentDate = simpleDateFormat.format(date);
            list.add(AssetPaymentDate); // db 날짜 yyy-MM-dd 포맷 가져오기
            list.add(assetVo.getStatusType());
            list.add(assetVo.getStatusAssetUsage());
            list.add(assetVo.getStatusAssetStatus());
            list.add(assetVo.getAssetLastUpdateDate());
            list.add(assetVo.getStatusAssetEtc2());
            result.add(list);
        }
        pageDto.setData(result);
        log.info("디비로 부터 받은 값: {}", data);
        log.info("값 변환 후: {}", result);
        return pageDto;
    }

    public HeaderSearchDto HeaderSearchDto(String navSearch) {
        return HeaderSearchDto;
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


    /* public List<AssetVo> assetHeaderSearch(HeaderSearchDto headerSearchDto, PageDto pageDto, String navSearch) {
        pageDto.setRecordsTotal(AssetMapper.countTotalAsset(navSearch));
        pageDto.setRecordsFiltered(pageDto.getRecordsTotal());
        int startNo = (pageDto.getStart());
        int length = pageDto.getLength();
        int rowNo = pageDto.getStart();
        log.info("Draw 받은 숫자 : {}", pageDto.getDraw());
        log.info("사용자한테 받은 페이지 Start : {}", pageDto.getStart());
        log.info("디비에 넘기기 위해 계산한 시작 번호 startNo : {}", startNo);
        List<AssetVo> data = new ArrayList<>();
        if (length == -1) {
            //전체
            //TODO 전체 조회 쿼리 명시
            data = AssetMapper.findAssetByPagination(0, pageDto.getRecordsTotal(), navSearch);
        } else {
            //페이징
            data = AssetMapper.findAssetByPagination(startNo, length, navSearch);
        }

        List<List<String>> result = new ArrayList<>();
        for (AssetVo assetVo : data) {
            List<String> list = new ArrayList<>();
            rowNo = rowNo + 1;
            list.add(String.valueOf(rowNo));
            list.add(assetVo.getMemberId());
            list.add(assetVo.getMemberName());
            list.add(assetVo.getMemberRank());
            list.add(assetVo.getAssetNumber());
            list.add(assetVo.getModelType());
            list.add(assetVo.getModelManufacturer());
            list.add(assetVo.getAssetModelName());
            Date date = new AssetVo.getAssetPaymentDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String AssetPaymentDate = simpleDateFormat.format(date);
            list.add(AssetPaymentDate); // format 변경 해야함.
            list.add(assetVo.getStatusType());
            list.add(assetVo.getStatusAssetUsage());
            list.add(assetVo.getStatusAssetStatus());
            result.add(list);
        }
        pageDto.setData(result);
        log.info("디비로 부터 받은 값: {}", data);
        log.info("값 변환 후: {}", result);
        return data;
    }
*/


}

