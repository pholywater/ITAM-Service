package hmm.itam.mapper;

import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {
    List<HistoryVo> getHistoryList(String searchStart, String searchEnd); // 전체 이력 조회

    void insertHistory(HistoryVo historyVo); // 히스토리 등록

    List<HistoryVo> getHistoryAssetNumber(String search); // 관리번호로 검색

    List<HistoryVo> getHistorySearch(String search, String searchType); // 이력 관리 상세 검색

    List<HistoryVo> getMemberList(); // datalist 자동완성 직원 이름 검색 리스트

}
