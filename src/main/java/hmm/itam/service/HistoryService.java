package hmm.itam.service;

import hmm.itam.dto.HeaderSearchDto;
import hmm.itam.dto.PageDto;
import hmm.itam.mapper.HistoryMapper;
import hmm.itam.vo.HistoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    // 이력 전체 조회 (기간 필터)
    public List<HistoryVo> getHistoryList(String searchStart, String searchEnd) {
        return historyMapper.getHistoryList(searchStart, searchEnd);
    }

    // 헤더 검색 DTO 반환 (현재 미사용 상태)
    private HeaderSearchDto headerSearchDto;

    public HeaderSearchDto getHeaderSearchDto(String navSearch) {
        return headerSearchDto;
    }

    // 서버사이드 페이징 + 검색 + 정렬 처리
    public PageDto<List<String>> findHistoryByPagination(PageDto<List<String>> pageDto) {
        int startNo = pageDto.getStart();
        int length = pageDto.getLength();
        int rowNo = startNo;
        String navSearch = pageDto.getNavSearch();
        String searchValue = pageDto.getSearch();
        String searchStart = pageDto.getSearchStart();
        String searchEnd = pageDto.getSearchEnd();

        Integer orderColumn = pageDto.getOrderColumn();
        String orderDir = pageDto.getOrderDir();

        String[] columnNames = {
                "history_completion_date", "history_request_date", "history_asset_type", "history_type",
                "history_requester", "history_worker", "history_member_id", "department_name",
                "member_name", "member_rank", "history_asset_number", "asset_model_name",
                "history_request_details", "history_request_etc", "history_asset_etc1",
                "history_spec1", "history_spec2", "history_spec3"
        };

        String orderByColumn = null;
        String direction = null;

        if (orderColumn != null && orderColumn > 0 && orderColumn - 1 < columnNames.length) {
            orderByColumn = columnNames[orderColumn - 1];
            direction = "asc".equalsIgnoreCase(orderDir) ? "ASC" : "DESC";
        }

        log.info("정렬 컬럼: {}", orderByColumn);
        log.info("정렬 방향: {}", direction);

        int total = historyMapper.countTotalHistory(navSearch, searchValue, searchStart, searchEnd);
        pageDto.setRecordsTotal(total);
        pageDto.setRecordsFiltered(total);

        List<HistoryVo> data = (length == -1)
                ? historyMapper.findHistoryByPagination(0, total, navSearch, searchValue, searchStart, searchEnd, orderByColumn, direction)
                : historyMapper.findHistoryByPagination(startNo, length, navSearch, searchValue, searchStart, searchEnd, orderByColumn, direction);

        List<List<String>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (HistoryVo u : data) {
            List<String> row = new ArrayList<>();
            rowNo++;

            row.add(String.valueOf(rowNo));
            row.add(u.getHistoryCompletionDate() != null ? sdf.format(u.getHistoryCompletionDate()) : "");
            row.add(u.getHistoryRequestDate() != null ? sdf.format(u.getHistoryRequestDate()) : "");
            row.add(u.getHistoryAssetType());
            row.add(u.getHistoryType());
            row.add(u.getHistoryRequester());
            row.add(u.getHistoryWorker());
            row.add(u.getHistoryMemberId());
            row.add(u.getDepartmentName());
            row.add(u.getMemberName());
            row.add(u.getMemberRank());
            row.add(u.getHistoryAssetNumber());
            row.add(u.getAssetModelName());
            row.add(u.getHistoryRequestDetails());
            row.add(u.getHistoryRequestEtc());
            row.add(u.getHistoryAssetEtc1());
            row.add(u.getHistorySpec1());
            row.add(u.getHistorySpec2());
            row.add(u.getHistorySpec3());

            result.add(row);
        }

        pageDto.setData(result);
        return pageDto;
    }

    // 이력 등록
    public void historyAdd(HistoryVo historyVo) {
        historyMapper.insertHistory(historyVo);
    }

    // 등록 후 관리번호로 조회
    public List<HistoryVo> historyAddResult(String search) {
        return historyMapper.getHistoryAssetNumber(search);
    }

    // 이력 상세 검색
    public List<HistoryVo> historySearch(String search, String searchType) {
        return historyMapper.getHistorySearch(search, searchType);
    }

    // 자동완성용 직원 리스트
    public List<HistoryVo> getMemberList() {
        return historyMapper.getMemberList();
    }

    // 자동완성용 장비 리스트
    public List<HistoryVo> getAssetList() {
        return historyMapper.getAssetList();
    }

    public List<HistoryVo> getHistoryListAll() {
        return historyMapper.getHistoryListAll();
    }

    public List<HistoryVo> getHistoryListAsset() {
        return historyMapper.getHistoryListAsset();
    }

    public List<HistoryVo> getHistoryListChange() {
        return historyMapper.getHistoryListChange();
    }

    public List<HistoryVo> getHistoryListConsumables() {
        return historyMapper.getHistoryListConsumables();
    }

    public List<HistoryVo> getHistoryListInput() {
        return historyMapper.getHistoryListInput();
    }

    public List<HistoryVo> getHistoryListOutput() {
        return historyMapper.getHistoryListOutput();
    }

    public List<HistoryVo> getHistoryListRepair() {
        return historyMapper.getHistoryListRepair();
    }
}
