package hmm.itam.service;

import hmm.itam.dto.HeaderSearchDto;
import hmm.itam.dto.PageDto;
import hmm.itam.mapper.HistoryMapper;
import hmm.itam.vo.HistoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    // ✅ 이력 등록
    public void addHistory(HistoryVo historyVo) {
        historyMapper.insertHistory(historyVo);
    }

    // ✅ 이력 수정
    public void updateHistory(HistoryVo historyVo) {
        historyMapper.updateHistory(historyVo);
    }

    // ✅ 이력 삭제
    public void deleteByIdx(Long idx) {
        historyMapper.deleteHistoryByIdx(idx);
    }

    // ✅ 단건 조회
    public HistoryVo findByIdx(Long idx) {
        return historyMapper.selectHistoryByIdx(idx);
    }

    // ✅ 등록 후 관리번호로 조회
    public List<HistoryVo> findByAssetNumber(String assetNumber) {
        return historyMapper.selectByAssetNumber(assetNumber);
    }

    // ✅ 이력 검색
    public List<HistoryVo> searchHistory(String keyword, String type) {
        return historyMapper.searchHistory(keyword, type);
    }

    // ✅ 직원 리스트 (자동완성용)
    public List<HistoryVo> getMemberList() {
        return historyMapper.selectMemberList();
    }

    // ✅ 장비 리스트 (자동완성용)
    public List<HistoryVo> getAssetList() {
        return historyMapper.selectAssetList();
    }

    // ✅ 전체 이력 리스트 (뷰 타입별)
    public List<HistoryVo> getHistoryListByType(String viewType) {
        switch (viewType) {
            case "All":
                return historyMapper.getHistoryListAll();
            case "Asset":
                return historyMapper.getHistoryListAsset();
            case "Change":
                return historyMapper.getHistoryListChange();
            case "Consumables":
                return historyMapper.getHistoryListConsumables();
            case "Input":
                return historyMapper.getHistoryListInput();
            case "Output":
                return historyMapper.getHistoryListOutput();
            case "Repair":
                return historyMapper.getHistoryListRepair();
            default:
                return Collections.emptyList();
        }
    }

    // ✅ 서버사이드 페이징 + 검색 + 정렬 처리
    public PageDto<List<String>> findHistoryByPagination(PageDto pageDto) {
        int startNo = pageDto.getStart();
        int length = pageDto.getLength();
        int rowNo = startNo;

        String tableName = Optional.ofNullable(pageDto.getTableName()).orElse("");
        Set<String> allowedTables = Set.of(
                "history_list_asset", "history_list_input", "history_list_output",
                "history_list_repair", "history_list_consumables", "history_list_change", "history_list_all"
        );
        if (!allowedTables.contains(tableName)) {
            tableName = "";
        }

        String[] columnNames = {
                "history_completion_date", "history_request_date", "history_asset_type", "history_type",
                "history_requester", "history_worker", "history_member_id", "department_name",
                "member_name", "member_rank", "history_asset_number", "model_name",
                "history_request_details", "history_request_etc"
        };

        String orderByColumn = null;
        String direction = "ASC";
        Integer orderColumn = pageDto.getOrderColumn();
        if (orderColumn != null && orderColumn > 0 && orderColumn - 1 < columnNames.length) {
            orderByColumn = columnNames[orderColumn - 1];
            if ("desc".equalsIgnoreCase(pageDto.getOrderDir())) {
                direction = "DESC";
            }
        }

        int total = historyMapper.countTotalHistory(
                pageDto, pageDto.getSearchType(), pageDto.getNavSearch(),
                pageDto.getSearch(), pageDto.getViewType(), tableName,
                pageDto.getSearchStart(), pageDto.getSearchEnd()
        );
        pageDto.setRecordsTotal(total);
        pageDto.setRecordsFiltered(total);

        List<HistoryVo> data = (length == -1)
                ? historyMapper.findHistoryByPagination(pageDto, tableName, 0, total,
                pageDto.getSearchType(), pageDto.getNavSearch(), pageDto.getNavSearchHistory(),
                pageDto.getSearch(), pageDto.getSearchStart(), pageDto.getSearchEnd(),
                orderByColumn, direction)
                : historyMapper.findHistoryByPagination(pageDto, tableName, startNo, length,
                pageDto.getSearchType(), pageDto.getNavSearch(), pageDto.getNavSearchHistory(),
                pageDto.getSearch(), pageDto.getSearchStart(), pageDto.getSearchEnd(),
                orderByColumn, direction);

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
            row.add(u.getIdx());
            String editHtml =
                    "<a href='/historyEdit?idx=" + u.getIdx() + "' class='btn btn-sm btn-warning'>수정</a>";


            String deleteHtml =
                    "<form action='/historyDelete' method='post' style='margin:0px; padding:0px;'>"
                            + "<input type='hidden' name='idx' value='" + u.getIdx() + "'/>"
                            + "<input type='hidden' name='historyAssetNumber' value='" + u.getHistoryAssetNumber() + "'/>"
                            + "<button type='submit' class='btn btn-sm btn-danger' onclick='return confirm(\"정말 삭제하시겠습니까?\");'>삭제</button>"
                            + "</form>";

            // 리스트에 추가
            row.add(editHtml);
            row.add(deleteHtml);

            result.add(row);
        }

        pageDto.setData(result);
        return pageDto;
    }
}
