package hmm.itam.mapper;

import hmm.itam.vo.HistoryVo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface HistoryMapper {
    List<HistoryVo> getHistoryList();

    void insertHistory(HistoryVo historyVo); // 히스토리 등록


}
