package hmm.itam.controller;

import hmm.itam.dto.AssetSupplies;
import hmm.itam.dto.PageDto;
import hmm.itam.service.HistoryService;
import hmm.itam.vo.HistoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.*;


@Controller
@Slf4j
public class HistoryController {

    @Autowired
    private HistoryService HistoryService;


    /*@GetMapping("/historyList") // 웹 URL 매핑(전체 리스트 시간 오래 걸리는 단점. 조회 기간 설정 필요)
    public String getHistoryList(HistoryVo historyVo, String searchStart, String searchEnd, Model model) {

        log.info("searchStart 초기 값 : {}", searchStart);
        log.info("searchEnd 초기 값 : {}", searchEnd);

        List<HistoryVo> historyList = HistoryService.getHistoryList(searchStart, searchEnd);
        model.addAttribute("list", historyList);
        return "itam/history/historyList"; // 실제 HTML 경로
    }*/

    /*@GetMapping("/historyList{type}")// 클라이언트 사이드 방식 속도 느림
    public String getHistoryListByType(@PathVariable String type, HistoryVo historyVo, Model model, String searchStart, String searchEnd) {
        List<HistoryVo> historyList;

        switch (type) {
            case "All":
                historyList = HistoryService.getHistoryListAll();
                log.info("전체 이력 리스트 조회");
                break;
            case "Asset":
                historyList = HistoryService.getHistoryListAsset();
                log.info("장비 관련 이력 리스트 조회");
                break;
            case "Change":
                historyList = HistoryService.getHistoryListChange();
                log.info("변경 이력 리스트 조회");
                break;
            case "Consumables":
                historyList = HistoryService.getHistoryListConsumables();
                log.info("소모품 이력 리스트 조회");
                break;
            case "Input":
                historyList = HistoryService.getHistoryListInput();
                log.info("입고 이력 리스트 조회");
                break;
            case "Output":
                historyList = HistoryService.getHistoryListOutput();
                log.info("출고 이력 리스트 조회");
                break;
            case "Repair":
                historyList = HistoryService.getHistoryListRepair();
                log.info("수리 이력 리스트 조회");
                break;
            default:
                log.warn("잘못된 이력 리스트 요청: {}", type);
                historyList = Collections.emptyList();
                break;
        }

        model.addAttribute("list", historyList);
        return "itam/history/historyList";
    }*/

    @GetMapping("/historyList")
    public String getHistoryList(HttpSession session,
                                 PageDto dto,
                                 @RequestParam(value = "navSearch", required = false) String navSearch,
                                 @RequestParam(value = "navSearchHistory", required = false) String navSearchHistory,
                                 @RequestParam(value = "searchType", required = false) String searchType,
                                 @RequestParam(value = "viewType", required = false) String viewType,
                                 String search,
                                 @RequestParam(value = "searchStart", required = false) String searchStart,
                                 @RequestParam(value = "searchEnd", required = false) String searchEnd,
                                 Model model) {


        if (viewType != null && !viewType.isEmpty()) {
            session.setAttribute("navSearch", null); // 세션에서 초기화
            session.setAttribute("navSearchHistory", null); // 세션에서 초기화
            dto.setNavSearch(null); // DTO에서 초기화
            dto.setNavSearchHistory(null); // DTO에서 초기화
            log.info("(/historyList) 초기화 navSearch: {}", dto.getNavSearch());
            log.info("(/historyList) 초기화 navSearchHistory: {}", dto.getNavSearch());
            String tableName;
            switch (viewType) {
                case "All":
                    tableName = "history_list_all";
                    break;
                case "Asset":
                    tableName = "history_list_asset";
                    break;
                case "Change":
                    tableName = "history_list_change";
                    break;
                case "Consumables":
                    tableName = "history_list_consumables";
                    break;
                case "Input":
                    tableName = "history_list_input";
                    break;
                case "Output":
                    tableName = "history_list_output";
                    break;
                case "Repair":
                    tableName = "history_list_repair";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid history viewType: " + viewType);
            }

            session.setAttribute("viewType", viewType.trim());
            model.addAttribute("viewType", viewType.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(viewType) 확인: {}", viewType);
            session.setAttribute("tableName", tableName.trim());
            model.addAttribute("tableName", tableName.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(tableName) 확인: {}", tableName);
        } else {
            log.warn("(getHistoryList) viewType 값이 null입니다. 세션에 저장하지 않습니다.");
            log.warn("(getHistoryList) tableName 값이 null입니다. 세션에 저장하지 않습니다.");
        }
        // 세션과 모델에 값 받기
        /*if (navSearch != null) {
            session.setAttribute("navSearch", navSearch.trim());
            model.addAttribute("navSearch", navSearch.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(navSearch) 확인: {}", navSearch);
        } else {
            session.setAttribute("navSearch", navSearchHistory);
            model.addAttribute("navSearch", navSearchHistory);
            log.info("(getHistoryList) navSearchHistory 값을 navSearch로 변경확인: {}", navSearch);
        }
        session.setAttribute("navSearch", navSearchHistory);
        model.addAttribute("navSearch", navSearchHistory);*/
/*

        if (navSearchHistory != null) {
            session.setAttribute("navSearchHistory", navSearchHistory.trim());
            model.addAttribute("navSearchHistory", navSearchHistory.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(navSearch) 확인: {}", navSearchHistory);
        } else {
            log.warn("(getHistoryList) navSearch 값이 null입니다. 세션에 저장하지 않습니다.");
        }*/
        if (searchType != null) {
            session.setAttribute("searchType", searchType.trim());
            model.addAttribute("searchType", searchType.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(searchType) 확인: {}", searchType);
        } else {
            log.warn("(getHistoryList) searchType 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        if (search != null) {
            session.setAttribute("search", search.trim());
            model.addAttribute("search", search.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(search) 확인: {}", search);
        } else {
            log.warn("(getHistoryList) search 값이 null입니다. 세션에 저장하지 않습니다.");
        }
        if (searchStart != null) {
            session.setAttribute("searchStart", searchStart.trim());
            model.addAttribute("searchStart", searchStart.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(searchStart) 확인: {}", searchStart);
        } else {
            log.warn("(getHistoryList) searchStart 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        if (searchEnd != null) {
            session.setAttribute("searchEnd", searchEnd.trim());
            model.addAttribute("searchEnd", searchEnd.trim());
            log.info("(getHistoryList) 히스토리 이력 조회 검색어(searchEnd) 확인: {}", searchEnd);
        } else {
            log.warn("(getHistoryList) searchEnd 값이 null입니다. 세션에 저장하지 않습니다.");
        }

        return "itam/history/historySearch";
    }

    /*@GetMapping("/historySearch")
    public String historySearchList(@RequestParam(required = false) String viewType,
                                    HttpSession session,
                                    Model model) {

        // navSearch 초기화
        session.setAttribute("navSearch", null);

        // DTO가 있다면 예: PageDto dto
        PageDto dto = new PageDto();
        dto.setNavSearch(null);
        dto.setViewType(viewType); // viewType 유지

        // 필요 시 model에 DTO 전달
        model.addAttribute("pageDto", dto);

        // viewType 파라미터를 유지한 채 리다이렉트
        return "redirect:/historyList?viewType=" + viewType;
    }*/


    @PostMapping("/api/historySearch")
    @ResponseBody
    public PageDto<List<String>> getHistorySearch(@RequestBody PageDto<?> dto,
                                                  HttpSession session) {


        if (dto.getSearchType() == null || dto.getSearchType().isEmpty()) {
            dto.setSearchType((String) session.getAttribute("searchType"));
        }
        if (dto.getViewType() == null || dto.getViewType().isEmpty()) {
            dto.setViewType((String) session.getAttribute("viewType"));
            if (dto.getNavSearchHistory() == null || dto.getNavSearchHistory().isEmpty()) {
                dto.setNavSearch((String) session.getAttribute("navSearchHistory"));
                dto.setNavSearchHistory((String) session.getAttribute("navSearchHistory"));
                log.info("(/api/historySearch) viewType null navSearch: {}", dto.getNavSearch());
            }
        }
        if (dto.getViewType() != null && !dto.getViewType().isEmpty()) {
            // viewType이 null이 아니고 비어 있지 않을 때 실행
            session.setAttribute("navSearch", null); // 세션에서 초기화
            session.setAttribute("navSearchHistory", null); // 세션에서 초기화
            dto.setNavSearch(null); // DTO에서 초기화
            dto.setNavSearchHistory(null); // DTO에서 초기화
            log.info("(/api/historySearch) 초기화 navSearch: {}", dto.getNavSearch());
            log.info("(/api/historySearch) 초기화 navSearchHistory: {}", dto.getNavSearch());
        }

        if (dto.getTableName() == null || dto.getTableName().isEmpty()) {
            dto.setTableName((String) session.getAttribute("tableName"));
        }

        // 로그 출력
        log.info("(/api/historySearch) 컨트롤러 최종 로그");
        log.info("(/api/historySearch) draw: {}", dto.getDraw());
        log.info("(/api/historySearch) start: {}", dto.getStart());
        log.info("(/api/historySearch) length: {}", dto.getLength());
        log.info("(/api/historySearch) search: {}", dto.getSearch());
        log.info("(/api/historySearch) navSearch: {}", dto.getNavSearch());
        log.info("(/api/historySearch) navSearchHistory: {}", dto.getNavSearchHistory());
        log.info("(/api/historySearch) searchType: {}", dto.getSearchType());
        log.info("(/api/historySearch) viewType: {}", dto.getViewType());
        log.info("(/api/historySearch) tableName: {}", dto.getTableName());
        log.info("(/api/historySearch) orderColumn: {}", dto.getOrderColumn());
        log.info("(/api/historySearch) orderDir: {}", dto.getOrderDir());
        log.info("(/api/historySearch) searchStart: {}", dto.getSearchStart());
        log.info("(/api/historySearch) searchEnd: {}", dto.getSearchEnd());

        // 서비스 계층 호출
        return HistoryService.findHistoryByPagination(dto);
    }


    @GetMapping("/historyAdd") // 자산 이력 등록 화면
    public String toHistoryAddPage(HistoryVo historyVo, Model model) {
        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<HistoryVo> memberList = HistoryService.getMemberList();
        model.addAttribute("memberList", memberList);
        /* datalist 장비번호 검색 자동완성 */
        List<HistoryVo> assetList = HistoryService.getAssetList();
        model.addAttribute("assetList", assetList);

        return "itam/history/historyAdd";
    }

    @GetMapping("/historyAddRepair") // 자산 이력 등록 화면
    public String toHistoryAddRepairPage(HistoryVo historyVo, Model model) {
        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<HistoryVo> memberList = HistoryService.getMemberList();
        model.addAttribute("memberList", memberList);
        /* datalist 장비번호 검색 자동완성 */
        List<HistoryVo> assetList = HistoryService.getAssetList();
        model.addAttribute("assetList", assetList);

        return "itam/history/historyAddRepair";
    }

    @GetMapping("/historyAddSupplies") // 자산 이력 등록 화면
    public String toHistoryAddSuppliesPage(HistoryVo historyVo, Model model) {
        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<HistoryVo> memberList = HistoryService.getMemberList();
        model.addAttribute("memberList", memberList);
        /* datalist 장비번호 검색 자동완성 */
        List<HistoryVo> assetList = HistoryService.getAssetList();
        model.addAttribute("assetList", assetList);

        return "itam/history/historyAddSupplies";
    }

    @ModelAttribute("AssetSupplies")
    public AssetSupplies[] assetSupplies() { // enum은 values를 반환하면 value 값들을 배열로 넘겨준다.
        return AssetSupplies.values();
    }

    @PostMapping("/historyAdd") // 자산 입출고 관련 이력 입력 처리 // null 관련 처리 추가 해야함.
    public String historyAdd(HistoryVo historyVo, String historyAssetNumber, String historyMemberId, Model model) {
        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<HistoryVo> memberList = HistoryService.getMemberList();
        model.addAttribute("memberList", memberList);
        /* datalist 장비번호 검색 자동완성 */
        List<HistoryVo> assetList = HistoryService.getAssetList();
        model.addAttribute("assetList", assetList);

        if (historyAssetNumber == null || historyAssetNumber.isEmpty() || historyAssetNumber.isBlank() ||
                historyMemberId == null || historyMemberId.isEmpty() || historyMemberId.isBlank()) {
            System.out.println("NullPointerException historyAssetNumber err : " + historyAssetNumber); // null 값 입력 확인
            System.out.println("NullPointerException historyMemberId err : " + historyMemberId); // null 값 입력 확인
            System.out.println("assetNumber.isEmpty() : " + historyAssetNumber.isEmpty()); // "" 빈 값 입력
            System.out.println("assetNumber.isEmpty() : " + historyMemberId.isEmpty()); // "" 빈 값 입력
            System.out.println("assetNumber.isBlank() : " + historyAssetNumber.isBlank()); // "   "공백 입력 체크
            System.out.println("assetNumber.isBlank() : " + historyMemberId.isBlank()); // "   "공백 입력 체크
            return "redirect:historyAdd"; // null & 빈 값 처리 반환
        }
        HistoryService.historyAdd(historyVo);
        System.out.println("historyAssetNumber : " + historyAssetNumber);
        String search = historyAssetNumber;
        List<HistoryVo> resultList = HistoryService.historyAddResult(search);
        model.addAttribute("list", resultList);
        /*try { 입출력 이력 등록은 중복 값 체크 하지 않음
            HistoryService.historyAdd(historyVo);
        } catch (DuplicateKeyException e) {
            return "redirect:historyadd?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:historyadd?error_code=-99";
        }*/
        return "itam/history/historyAdd"; // 자산 등록 후 보여질 화면
    }

  /*  @GetMapping("/historySearch") // 이력 조회 화면(기본 화면)
    public String historySearch(HistoryVo historyVo, Model model, String search, String searchType) {
        log.info("searchType : {}", searchType);
        log.info("search : {}", search);

        List<HistoryVo> resultList = HistoryService.historySearch(search, searchType);
        model.addAttribute("list", resultList);
        log.info("간편 조회하기 : {}", search);
        return "itam/history/historySearch";
    }*/

}