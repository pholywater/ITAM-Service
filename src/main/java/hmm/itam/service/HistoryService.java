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
import java.util.Set;

@Service
@Slf4j
public class HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    // 헤더 검색 DTO 반환 (현재 미사용 상태)
    private HeaderSearchDto headerSearchDto;
    private PageDto pageDto;

    public HeaderSearchDto getHeaderSearchDto(String navSearch) {
        return headerSearchDto;
    }

    // 이력 전체 조회 (기간 필터)
/*    public List<HistoryVo> getHistoryList(String searchStart, String searchEnd) {
        return historyMapper.getHistoryListDate(searchStart, searchEnd);
    }*/


    // 서버사이드 페이징 + 검색 + 정렬 처리
    public PageDto<List<String>> findHistoryByPagination(PageDto pageDto) {
        this.pageDto = pageDto;
        int startNo = pageDto.getStart();
        int length = pageDto.getLength();
        int rowNo = startNo;

        // 해더 우측 상단 검색
        String navSearch = pageDto.getNavSearch();
        String searchType = pageDto.getSearchType();
        // 해더 파라미터 접근 검색
        String viewType = pageDto.getViewType();
        String tableName = pageDto.getTableName();
        //dataTables Searching 창(2차 검색)
        String search = pageDto.getSearch();
        //날짜별 추가 검색 변수
        String searchStart = pageDto.getSearchStart();
        String searchEnd = pageDto.getSearchEnd();


        // ✅ 테이블 이름 화이트리스트 검증
        Set<String> allowedTables = Set.of(
                "history_list_asset", "history_list_input", "history_list_output",
                "history_list_repair", "history_list_consumables", "history_list_change", "history_list_all"
        );

        if (tableName == null || !allowedTables.contains(tableName)) {
            /*throw new IllegalArgumentException("허용되지 않은 테이블 이름입니다: " + tableName);*/
            tableName = ""; // 기본값 설정
        }

        // ✅ 정렬 컬럼 처리
        String[] columnNames = {
                "history_completion_date", "history_request_date", "history_asset_type", "history_type",
                "history_requester", "history_worker", "history_member_id", "department_name",
                "member_name", "member_rank", "history_asset_number", "asset_model_name",
                "history_request_details", "history_request_etc"
                /*"history_spec1", "history_spec2", "history_spec3", "history_asset_etc1"*/
        };

        // 컬럼 정렬 처리
        String orderDir = pageDto.getOrderDir();
        Integer orderColumn = pageDto.getOrderColumn();
        String orderByColumn = null;
        String direction = null;

        if (orderColumn != null && orderColumn > 0 && orderColumn - 1 < columnNames.length) {
            orderByColumn = columnNames[orderColumn - 1];
            direction = "ASC";
            if ("desc".equalsIgnoreCase(orderDir)) {
                direction = "DESC";
            }
        }


        log.info("(findHistoryByPagination) 정렬 컬럼 orderColumn : {}", orderColumn);
        log.info("(findHistoryByPagination) 정렬 방향 orderDir : {}", orderDir);
        log.info("(findHistoryByPagination) tableName: {}", tableName);
        log.info("(findHistoryByPagination) navSearch: {}", navSearch);
        log.info("(findHistoryByPagination) searchType: {}", searchType);
        log.info("(findHistoryByPagination) search: {}", search);

        // ✅ 총 레코드 수 조회
        int total = historyMapper.countTotalHistory(pageDto, navSearch, searchType, searchStart, searchEnd, viewType, tableName);
        pageDto.setRecordsTotal(total);
        pageDto.setRecordsFiltered(total);

        // ✅ 데이터 조회
        List<HistoryVo> data = (length == -1)
                ? historyMapper.findHistoryByPagination(pageDto, 0, total, navSearch, orderByColumn, direction)
                : historyMapper.findHistoryByPagination(pageDto, startNo, length, navSearch, orderByColumn, direction);

        // ✅ 결과 포맷 변환
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
            row.add(u.getModelName());
            row.add(u.getHistoryRequestDetails());
            row.add(u.getHistoryRequestEtc());
            /*row.add(u.getHistorySpec1());
            row.add(u.getHistorySpec2());
            row.add(u.getHistorySpec3());
            row.add(u.getHistoryAssetEtc1());*/
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
