package hmm.itam.service;

import hmm.itam.mapper.HistoryMapper;
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
    public void historyAdd(HistoryVo historyVo){
        HistoryMapper.insertHistory(historyVo);
        /* AssetMapper.insertModel(assetVo);*/
    }


}
