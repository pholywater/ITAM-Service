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

    public List<HistoryVo> getHistoryList() {

        return HistoryMapper.getHistoryList();
    }

    /*히스토리 정보 등록*/
    public void historyAdd(HistoryVo historyVo) {
        HistoryMapper.insertHistory(historyVo);
        /*AssetMapper.insertModel(assetVo);*/
    }

    /*관리번호 조회*/
    public List<HistoryVo> historyAssetSearch(String search) {
        return HistoryMapper.getHistoryAssetNumber(search);
    }

    /*이름 조회*/
    public List<HistoryVo> historyNameSearch(String search) {
        return HistoryMapper.getHistoryMemberName(search);
    }

    /*사번 조회*/
    public List<HistoryVo> historyIdSearch(String search) {
        return HistoryMapper.getHistoryMemberId(search);
    }

    /*간편 조회*/
    public List<HistoryVo> historySearch(String search, String searchType) {
        return HistoryMapper.getHistorySearch(search, searchType);
    }

    /*장비 등록 후 조회*/
    public List<HistoryVo> historyAddResult(String search) {
        return HistoryMapper.getHistoryAssetNumber(search);
    }

}
