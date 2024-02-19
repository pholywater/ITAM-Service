package hmm.itam.service;


import hmm.itam.dto.HeaderSearchDto;
import hmm.itam.dto.PageDto;
import hmm.itam.mapper.AssetMapper;
import hmm.itam.vo.AssetVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AssetService {
    @Autowired
    private AssetMapper AssetMapper;


    public List<AssetVo> getAssetList() {

        return AssetMapper.getAssetList();
    }

    public List<AssetVo> assetHeaderSearch(HeaderSearchDto headerSearchDto) {
        return AssetMapper.assetHeaderSearch(headerSearchDto);
    }


    public PageDto<List<String>> findAssetByPagination(PageDto pageDto) {
        pageDto.setRecordsTotal(AssetMapper.countTotalAsset());
        pageDto.setRecordsFiltered(pageDto.getRecordsTotal());
        log.info("Draw - 숫자 : {}", pageDto.getDraw());
        log.info("사용자한테 받은 페이지 Start : {}", pageDto.getStart());
        int startNo = (pageDto.getStart());
        int length = pageDto.getLength();
        int rowNo = pageDto.getStart();
        List<AssetVo> data = new ArrayList<>();
        if (length == -1) {
            //전체
            //TODO 전체 조회 쿼리 명시
            data = AssetMapper.findAssetByPagination(0, pageDto.getRecordsTotal());
        } else {
            //페이징
            data = AssetMapper.findAssetByPagination(startNo, length);
        }
        log.info("디비에 넘기기 위해 계산한 시작 번호 : {}", startNo);
        List<List<String>> result = new ArrayList<>();
        for (AssetVo assetVo : data) {
            List<String> list = new ArrayList<>();
            rowNo = rowNo + 1;
            list.add(String.valueOf(rowNo));
            list.add(assetVo.getStatusType());
            list.add(assetVo.getStatusAssetStatus());
            list.add(assetVo.getMemberId());
            list.add(assetVo.getMemberName());
            list.add(assetVo.getMemberRank());
            list.add(assetVo.getStatusAssetUsage());
            list.add(assetVo.getAssetNumber());
            list.add(assetVo.getModelType());
            list.add(assetVo.getModelManufacturer());
            list.add(assetVo.getAssetModelName());

            Date date = new AssetVo.getAssetPaymentDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String AssetPaymentDate = simpleDateFormat.format(date);
            list.add(AssetPaymentDate); // format 변경 해야함.

            result.add(list);
        }
        pageDto.setData(result);
        //log.info("디비로 부터 받은 값: {}", data);
        log.info("값 변환 후: {}", result);
        return pageDto;
    }

    /*장비 정보 등록*/
    public void assetAdd(AssetVo assetVo) {
        AssetMapper.insertAsset(assetVo);
    }

    /*멤버 장비 사용 정보 조회*/
    public List<AssetVo> navbarSearch(String statusMemberId) {
        return AssetMapper.searchByNavbar(statusMemberId);
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


}

