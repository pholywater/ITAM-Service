package hmm.itam.mapper;

import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {
    List<HistoryVo> getHistoryList(); // 전체 이력 조회

    void insertHistory(HistoryVo historyVo); // 히스토리 등록

    List<HistoryVo> getHistoryMemberName(String search); // 이름 검색

    List<HistoryVo> getHistoryMemberId(String search); // 사번 검색

    List<HistoryVo> getHistoryAssetNumber(String search); // 관리번호로 검색

    List<HistoryVo> getHistorySearch(String search); // 간편 검색


}
