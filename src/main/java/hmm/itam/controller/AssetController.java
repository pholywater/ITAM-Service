package hmm.itam.controller;

import hmm.itam.dto.*;
import hmm.itam.service.AssetService;
import hmm.itam.service.HistoryService;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.HistoryVo;
import hmm.itam.vo.MemberVo;
import hmm.itam.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.SimpleTimeZone;

import static java.time.LocalTime.now;

@Controller
@Slf4j
public class AssetController {

    @Autowired
    private AssetService AssetService;


    @GetMapping("/assetList") // 전체 자산 리스트 검색 시 사용(매각, 신규 제외)
    public String getAssetList(Model model) {
        List<AssetVo> assetList = AssetService.getAssetList();
        /*System.out.println(assetList);*/
        log.info("전체 자산 리스트 조회 : 출고,입고,기타");
        model.addAttribute("list", assetList);
        return "itam/asset/assetList";
    }


    @GetMapping("/headerSearch") // 해더 드롭다운 href Server-Side 검색
    public String HeaderSearch(AssetVo assetVo, HttpSession session, String navSearch, Model model) {
        session.setAttribute("navSearch", navSearch);

        //model.addAttribute("Searh", "navSearch");
        //String navSearch = headerSearchDto.getNavSearch();
        //List<AssetVo> navSearchList = AssetService.navbarSearch(navSearch);

        if (navSearch == "null") {
            return "redirect:/";
        }
        log.info("드롭다운 해더 검색어 NavSearch Controller 체크 : {}", navSearch);

        return "itam/asset/headerSearchList"; // html 불러온 후 js ajax 호출
    }


    @ResponseBody // Data로 받아오는 선언
    @PostMapping("/assets") // js ajax 호출로 Data로 들어갈 PageDto 값 정의
    public PageDto getAsset(int draw, int length, int start, String search, HttpSession session) {
        String navSearch = (String) session.getAttribute("navSearch");
        //session.setAttribute("filterSearch", Search);
        //String filterSearch = (String) session.getAttribute("filterSearch");
        //String searchValue = Request["search[value]"];
        //String search = Request.Form.GetValues("search[value]").FirstOrDefault();
        //String search = Request.QueryString["(search[value])"];
        //String filterSearch = (String) session.getAttribute("search[value]");
        //String search = Request.Form.GetValues("search[value]")[0];
        //String navSearch = (String) headerSearchDto.getNavSearch();
        //String search = navSearch;

        log.info("ajax: '/assets' 실행 후 js에서 받아오는 draw 값 {} ", draw);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 start 값 {} ", start);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 length 값 {} ", length);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 search 값 {} ", search);
        //log.info("ajax: '/assets' 실행 후 js에서 받아오는 filterSearch 값 {} ", filterSearch);
        log.info("해더에서 넘겨 받은 getNavSearch 값 {} ", navSearch);

        PageDto rs = new PageDto();

        rs.setDraw(draw);
        rs.setStart(start);
        rs.setLength(length);
        //rs.setSearch(search);
        rs.setNavSearch(navSearch);


        return AssetService.findAssetByPagination(rs);
    }

/*  아래의 장비 리스스 상세 검색과 통합 작업 완료.
    @GetMapping("/navbarSearch") // Navbar 우측 Get 클라이언트 검색
    public String navGetSearch(AssetVo assetVo, HistoryVo historyVo, MemberVo memberVo, String navbarSearch, String searchType, String search, Model model) {
        if (navbarSearch == "") { // 빈 값 입력 시
            log.info("검색어 빈값 : redirect:/ 처리");
            return "itam/asset/assetSearchList";
        }
        if (Objects.equals(searchType, "history")) {
            log.info("간편 이력 조회하기 : {}", navbarSearch);
            List<AssetVo> resultList = AssetService.historySearch(navbarSearch);
            model.addAttribute("list", resultList);
            return "/itam/history/historySearch";
        }
        List<AssetVo> navbarGetSearch = AssetService.navbarSearch(navbarSearch);
        log.info("검색어 : {}", navbarSearch);
        if (navbarGetSearch == null) { // 일치 항목 없을 경우 에러 처리
            return "redirect:/";
        }
        *//*조회 한 값 넘겨주기*//*
        model.addAttribute("list", navbarGetSearch);
        *//*상세조회 datalist 부서 검색 자동완성 작업*//*
        List<AssetVo> departmentList = AssetService.getDepartmentList();
        model.addAttribute("departList", departmentList);
        *//*상세조회 datalist 직원(사번) 검색 자동완성 작업*//*
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/assetSearchList"; //
    }*/

    @GetMapping("/searchAssetPage") // 자산 등록 후 화면
    public String searchAssetDetailPage(AssetVo assetVo, Model model) {
        log.info("장비 리스트 조회 화면입니다.");

        /*상세조회 datalist 부서 검색 자동완성 작업*/
        List<AssetVo> departmentList = AssetService.getDepartmentList();
        model.addAttribute("departList", departmentList);

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "/itam/asset/assetSearchList";
    }

    @GetMapping("/searchAssetDetail") // 장비 상세 조회(24.06.29)
    public String searchAssetDetail(AssetVo assetVo, String searchType, String search, String navSearch, Model model) {
        log.info("searchType : {}", searchType);
        log.info("navsearch : {}", navSearch);
        log.info("search : {}", search);

        /*장비 전체 리스트 조회 할 경우 예외처리*/
        if (Objects.equals(searchType, "assetAll")) {
            log.info("장비 전체 리스트 조회");
            List<AssetVo> searchAssetDetail = AssetService.searchAssetDetail(search, searchType);
            model.addAttribute("list", searchAssetDetail);
            return "itam/asset/assetSearchList"; //
        }
        /*상단 검색에서 장비 조회 시*/
        if (Objects.equals(searchType, "realTime")) {
            log.info("상단 간편검색 : {}", navSearch);
            search = navSearch;
        }
        /*상단 검색에서 이력 관리 조회 시*/
        if (Objects.equals(searchType, "history")) {
            log.info("간편 이력 조회하기 : {}", navSearch);
            List<AssetVo> resultList = AssetService.historySearch(navSearch);
            model.addAttribute("list", resultList);
            return "/itam/history/historySearch";
        }
        /*빈 값 입력 시*/
        if (search == "") {
            log.info("검색 창 빈값 처리");
            return "itam/asset/assetSearchList";
        }

        /*조회 한 값 넘겨주기*/
        List<AssetVo> searchAssetDetail = AssetService.searchAssetDetail(search, searchType);
        model.addAttribute("list", searchAssetDetail);

        /*상세조회 datalist 부서 검색 자동완성 작업*/
        List<AssetVo> departmentList = AssetService.getDepartmentList();
        model.addAttribute("departList", departmentList);

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/assetSearchList"; //
    }


    /*
    @PostMapping("/navbarSearch") // Navbar 우측 "Post" 검색(현재 미사용)
    public String navHeaderSearch(String navSearch, Model model) {
        List<AssetVo> navbarSearch = AssetService.navbarSearch(navSearch);
        log.info("검색어 : {}", navSearch);
        if (navbarSearch == null) { // 일치 항목 없을 경우 에러 처리
            return "redirect:/";
        }
        System.out.println(navbarSearch);
        model.addAttribute("list", navbarSearch);
        return "itam/asset/assetSearchList"; //
    }
*/


    @GetMapping("/searchPaymentPage") // 신규장비 출고 리스트 조회 화면
    public String searchPaymentListPage(AssetVo assetVo, Model model) {
        log.info("신규 장비 출고 리스트 조회 화면입니다.");
        return "/itam/asset/assetPaymentList";
    }


    @GetMapping("/searchPaymentList") // 신규장비 출고 리스트 조회 화면(처리)
    public String searchAssetDetail(AssetVo assetVo, String searchStart, String searchEnd, Model model) {
        log.info("searchStart : {}", searchStart);
        log.info("searchEnd : {}", searchEnd);

        /*빈 값 입력 시*/
        if (searchStart == "") {
            log.info("검색 창 빈값 처리");
            return "itam/asset/assetPaymentList";
        }
        if (searchEnd == "") {
            log.info("검색 창 빈값 처리");
            return "itam/asset/assetPaymentList";
        }

        /*조회 한 값 넘겨주기*/
        List<AssetVo> searchPaymentList = AssetService.searchPaymentList(searchStart, searchEnd);
        model.addAttribute("list", searchPaymentList);

        return "itam/asset/assetPaymentList"; //
    }


    @GetMapping("/assetAdd") // 자산 등록 화면
    public String toAssetAddPage(AssetVo assetVo, Model model) {
        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        log.info("장비 등록 화면입니다.");
        return "/itam/asset/assetAdd";
    }

    @PostMapping("/assetAdd") // 자산 등록 입력 처리
    public String assetAdd(AssetVo assetVo, String assetNumber, Model model) {
        if (assetNumber == null || assetNumber.isEmpty() || assetNumber.isBlank()) {
            System.out.println("NullPointerException err : " + assetNumber); // null 값 입력 확인
            System.out.println("assetNumber.isEmpty() : " + assetNumber.isEmpty()); // "" 빈 값 입력
            System.out.println("assetNumber.isBlank() : " + assetNumber.isBlank()); // "   "공백 입력 체크
            return "redirect:/assetAdd"; // null & 빈 값 처리 반환
        }
        try {
            AssetService.assetAdd(assetVo);
            System.out.println("controll.check.assetNumber.addComplete:" + assetNumber);
        } catch (DuplicateKeyException e) {
            System.out.println("DuplicateKeyException err : " + assetNumber); // 중복값 입력 확인
            return "redirect:/assetAdd"; // 장비 추가 중복값 처리 반환
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception err" + assetNumber); // 중복값 입력 확인
            return "redirect:/assetAdd"; // assetadd?error_code=-99";
        }
        model.addAttribute("asset", assetVo);
        return "itam/asset/assetResult"; // 자산 등록 후 보여질 화면
    }

    @GetMapping("/assetSearch") // 자산 등록 후 화면
    public String searchPage(AssetVo assetVo) {
        log.info("장비 정보 조회 화면입니다.");
        return "/itam/asset/assetSearch";
    }

    @PostMapping("/assetSearch") // 자산 내역 검색 및 수정 화면
    public String searchResult(AssetVo assetVo, String assetNumber, Model model) {
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        if (assetNum == null) { // 관리번호 일치 항목 없을 경우 에러 처리
            log.info("조회하기 : 일치하는 관리번호 없음");
            return "redirect:assetSearch";
        }
        model.addAttribute("asset", assetNum);
        log.info("장비 정보를 조회합니다. 관리번호 : {}", assetNum.getAssetNumber());
        return "itam/asset/assetResult"; //
    }

    @ModelAttribute("statusType")
    public StatusType[] statusType() { // enum은 values를 반환하면 value 값들을 배열로 넘겨준다.
        return StatusType.values();
    }

    @ModelAttribute("statusAssetStatus")
    public StatusAssetStatus[] statusAssetStatus() {
        return StatusAssetStatus.values();
    }

    @ModelAttribute("statusAssetUsage")
    public StatusAssetUsage[] statusAssetUsage() {
        return StatusAssetUsage.values();
    }


    @PostMapping("/assetUpdate") // 장비 수정 작업 화면
    public String updatePage(AssetVo assetVo, String asset_number, Model model) {
        /*AssetService.modifyInfo(assetVo);*/
        AssetVo asset = AssetService.assetSearch(asset_number);
        model.addAttribute("asset", assetVo);  // 수정 후 변경 내역 다시 보기 위한 값 가져오기
        log.info("장비 정보 수정 화면입니다.");
        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/assetUpdate";
    }

    @PostMapping("/assetUpdateResult") // 자산 내역 검색 및 수정 후 결과 화면 + 이력관리 체크박스 검증 및 추가
    public String modifyInfo(AssetVo assetVo, String assetNumber, Model model, String historyCheck) {
        AssetService.modifyInfo(assetVo);
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        model.addAttribute("asset", assetNum);
        log.info("이력 관리 체크박스 : {}", historyCheck);
        if (Objects.equals(historyCheck, "on")) {
            AssetService.historyAdd(assetVo);
            log.info("이력 관리 정보를 추가 하였습니다.");
        } else {
            log.info("이력 관리 정보를 추가 하지 않았습니다.");
        }
        log.info("장비 정보를 수정하였습니다. 관리번호 : {}", assetNum.getAssetNumber());

        return "itam/asset/assetSearch";
    }


    @PostMapping("/assetLogout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/assetDelete")
    public String delete(String assetNumber, Model model) {
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        model.addAttribute("asset", assetNum);
        AssetService.withdraw(assetNum);
        return "redirect:assetSearch";
    }
}
