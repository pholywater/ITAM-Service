package hmm.itam.service;

import hmm.itam.mapper.HistoryMapper;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper HistoryMapper;

    public List<HistoryVo> getHistoryList(String searchStart, String searchEnd) {

        return HistoryMapper.getHistoryList(searchStart, searchEnd);
    }

    /*히스토리 정보 등록*/
    public void historyAdd(HistoryVo historyVo) {
        HistoryMapper.insertHistory(historyVo);
        /*AssetMapper.insertModel(assetVo);*/
    }

    /*장비 등록 후 조회*/
    public List<HistoryVo> historyAddResult(String search) {
        return HistoryMapper.getHistoryAssetNumber(search);
    }

    /*이력 관리 상세 조회(동적쿼리로 변경 - 변수가 2개 필요했었음)*/
    public List<HistoryVo> historySearch(String search, String searchType) {
        return HistoryMapper.getHistorySearch(search, searchType);
    }

    public List<HistoryVo> getMemberList() {
        return HistoryMapper.getMemberList();
    }

    public List<HistoryVo> getAssetList() {
        return HistoryMapper.getAssetList();
    }


    /* 동적 쿼리문으로 변경 후 필여 없어짐
     *//*관리번호 조회*//*
    public List<HistoryVo> historyAssetSearch(String search) {
        return HistoryMapper.getHistoryAssetNumber(search);
    }

    *//*이름 조회*//*
    public List<HistoryVo> historyNameSearch(String search) {
        return HistoryMapper.getHistoryMemberName(search);
    }

    *//*사번 조회*//*
    public List<HistoryVo> historyIdSearch(String search) {
        return HistoryMapper.getHistoryMemberId(search);
    }*/
}
