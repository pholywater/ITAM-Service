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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.SimpleTimeZone;

import static java.time.LocalTime.now;

@Controller
@Slf4j
public class AssetController {

    @Autowired
    private AssetService AssetService;


    /*@GetMapping("/homeChart") // 전체 자산 리스트 검색 시 사용(매각, 신규 제외)*/
    /*
    public String getChart1List(Model model, AssetVo assetVo) {
        List<AssetVo> chart1Label = AssetService.getChart1List();
        model.addAttribute("label", chart1Label);
        List<AssetVo> chart1Point = AssetService.getChart2List();
        model.addAttribute("point", chart1Point);
        log.info("label : {}", chart1Label);
        log.info("point : {}", chart1Point);
        model.addAttribute("point1", chart1Label);
       search = "불량";
        List<AssetVo> chart2Point = AssetService.getChart2List();
        model.addAttribute("point2", chart2Point);

        log.info("chart1Point: {}", chart1Point);
        log.info("chart2Point: {}", chart2Point);
        log.info("홈 화면");


        return "itam/asset/homeChart";
    }*/

    @GetMapping("/assetList") // 전체 자산 리스트 검색 시 사용(매각, 신규 제외)
    public String getAssetList(Model model) {
        List<AssetVo> assetList = AssetService.getAssetList();
        /*System.out.println(assetList);*/
        log.info("전체 자산 리스트 조회 : 출고,입고,기타");
        model.addAttribute("list", assetList);
        return "itam/asset/assetList";
    }

    @GetMapping("/assetListRent") // 전체 자산 리스트 검색 시 사용(매각, 신규 제외)
    public String getAssetListRent(Model model) {
        List<AssetVo> assetList = AssetService.getAssetListRent();
        log.info("대여장비 리스트 : DB VIEW TABLE 조회");
        model.addAttribute("list", assetList);
        return "itam/asset/assetList";
    }


    @GetMapping("/headerSearch") // 해더 드롭다운 href Server-Side 검색
    public String HeaderSearch(AssetVo assetVo, HttpSession session, String navSearch, Model model) {
        session.setAttribute("navSearch", navSearch);

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
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 draw 값 {} ", draw);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 start 값 {} ", start);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 length 값 {} ", length);
        log.info("ajax: '/assets' 실행 후 js에서 받아오는 search 값 {} ", search);
        log.info("해더에서 넘겨 받은 getNavSearch 값 {} ", navSearch);
        PageDto rs = new PageDto();
        rs.setDraw(draw);
        rs.setStart(start);
        rs.setLength(length);
        rs.setNavSearch(navSearch);
        return AssetService.findAssetByPagination(rs);
    }

    @GetMapping("/searchAssetPage") // 자산 등록 후 화면
    public String searchAssetDetailPage(AssetVo assetVo, Model model) {
        log.info("장비 리스트 조회 화면입니다.");

        /*상세조회 datalist 부서 검색 자동완성 작업*/
        List<AssetVo> departmentList = AssetService.getDepartmentList();
        model.addAttribute("departList", departmentList);

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/assetList";
    }

    @GetMapping("/searchAssetList") // 장비 상세 조회(24.06.29)
    public String searchAssetDetail(AssetVo assetVo, HttpSession session, String searchType, String search, String navSearch, Model model) {
        log.info("searchType : {}", searchType);
        log.info("navSearch : {}", navSearch);
        log.info("search : {}", search);
        search = navSearch;

        /*장비 전체 리스트 조회 할 경우 예외처리*/
        if (Objects.equals(searchType, "assetAll")) {
            log.info("장비 전체 리스트 조회");
            List<AssetVo> searchAssetList = AssetService.searchAssetList(search, searchType);
            model.addAttribute("list", searchAssetList);
            return "itam/asset/assetList"; //
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
            return "itam/history/historySearch";
        }

        /*상단 검색에서 백앤드 장비 조회 시*/
        if (Objects.equals(searchType, "serverSide")) {
            session.setAttribute("navSearch", navSearch);
            log.info("상단 간편검색 : {}", navSearch);

            return "itam/asset/headerSearchList"; // html 불러온 후 js ajax 호출
        }

        /*빈 값 입력 시*/
        if (search == "") {
            log.info("검색 창 빈값 처리");
            return "itam/asset/assetList";
        }

        /*조회 한 값 넘겨주기*/
        List<AssetVo> searchAssetDetail = AssetService.searchAssetList(search, searchType);
        model.addAttribute("list", searchAssetDetail);

        /*상세조회 datalist 부서 검색 자동완성 작업*/
        List<AssetVo> departmentList = AssetService.getDepartmentList();
        model.addAttribute("departList", departmentList);

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/assetList"; //
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
        return "itam/asset/searchAssetList"; //
    }
*/


    @GetMapping("/searchMemberUpdatePage") // 부서 및 직원 장비 리스트 조회 화면
    public String searchMemberListUpdatePage(AssetVo assetVo, Model model) {
        log.info("직원 장비 검색 조회 화면입니다.");

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "itam/asset/searchMemberList";
    }


    @GetMapping("/searchMemberList") // 부서 및 직원 장비 리스트 조회 화면(처리)
    public String searchMemberList(AssetVo assetVo, String searchMember, Model model) {
        log.info("searchMember : {}", searchMember);

        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);

        /*조회 한 값 넘겨주기*/
        List<AssetVo> searchMemberList = AssetService.searchMemberList(searchMember);
        model.addAttribute("list", searchMemberList);


        return "itam/asset/searchMemberList"; //
    }

    @GetMapping("/searchMemberUpdate") // 부서 및 직원 장비 리스트 조회 화면(처리)
    public String searchMemberUpdate(AssetVo assetVo, String searchMember, Model model, String updateCheck, String assetNumber) {
        log.info("searchMember : {}", searchMember);

        log.info("업데이트 체크박스 : {}", updateCheck);
        log.info("업데이트 장비번호 : {}", assetNumber);
        if (Objects.equals(updateCheck, "on")) {
            log.info("업데이트 장비번호 : {}", assetNumber);
            AssetService.modifyInfo(assetVo);
            AssetVo assetNum = AssetService.assetSearch(assetNumber);
            model.addAttribute("asset", assetNum);
            log.info("업데이트 체크박스 정보를 변경 하였습니다.");
        } else {
            log.info("정보를 변경 하지 않았습니다.");
        }


        /*상세조회 datalist 직원(사번) 검색 자동완성 작업*/
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);

        /*조회 한 값 넘겨주기*/
        List<AssetVo> searchMemberList = AssetService.searchMemberList(searchMember);
        model.addAttribute("list", searchMemberList);


        return "itam/asset/searchMemberList"; //
    }


    @GetMapping("/searchPaymentList") // 장비 지급일 리스트 조회 화면(처리)
    public String searchPaymentList(AssetVo assetVo, String searchStart, String searchEnd, Model model) {
        log.info("searchStart : {}", searchStart);
        log.info("searchEnd : {}", searchEnd);
        log.info("장비 지급일 리스트 조회 화면입니다.");
        /*조회 한 값 넘겨주기*/
        List<AssetVo> assetPaymentList = AssetService.assetPaymentList(searchStart, searchEnd);
        model.addAttribute("list", assetPaymentList);
        if (Objects.equals(searchStart, "change")) {
            /*return "itam/asset/searchAssetChangeList";*/
            return "itam/asset/searchPaymentList";
        }
        return "itam/asset/searchPaymentList";
    }

    @GetMapping("/searchNewPaymentList") // 신규 장비 출고 리스트 조회 화면(처리)
    public String searchNewPaymentList(AssetVo assetVo, String searchStart, String searchEnd, Model model) {
        log.info("신규 장비 출고 리스트 조회 화면입니다.");
        log.info("searchStart : {}", searchStart);
        log.info("searchEnd : {}", searchEnd);

        /*조회 한 값 넘겨주기*/
        List<AssetVo> assetPaymentList = AssetService.assetPaymentList(searchStart, searchEnd);
        model.addAttribute("list", assetPaymentList);
        return "itam/asset/searchNewPaymentList"; //
    }


    @GetMapping("/assetAdd") // 자산 등록 화면
    public String toAssetAddPage(AssetVo assetVo, Model model) {
        /* datalist 직원(사번) 검색 자동완성 */
        List<AssetVo> memberList = AssetService.getMemberList();
        model.addAttribute("memberList", memberList);
        /* datalist 장비번호 검색 자동완성 */
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("assetList", assetList);
        log.info("장비 등록 화면입니다.");
        return "itam/asset/assetAdd";
    }

    @PostMapping("/assetAdd") // 자산 등록 입력 처리(간단하게 정리해야함)
    public String assetAdd(AssetVo assetVo, String assetNumber, Model model) {
        if (assetNumber == null || assetNumber.isEmpty() || assetNumber.isBlank()) {
            System.out.println("NullPointerException err : " + assetNumber); // null 값 입력 확인
            System.out.println("assetNumber.isEmpty() : " + assetNumber.isEmpty()); // "" 빈 값 입력
            System.out.println("assetNumber.isBlank() : " + assetNumber.isBlank()); // "   "공백 입력 체크
            return "redirect:assetAdd"; // null & 빈 값 처리 반환
        }
        try {
            AssetService.assetAdd(assetVo);
            System.out.println("controll.check.assetNumber.addComplete:" + assetNumber);
        } catch (DuplicateKeyException e) {
            System.out.println("DuplicateKeyException err : " + assetNumber); // 중복값 입력 확인
            return "redirect:assetAdd"; // 장비 추가 중복값 처리 반환
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception err" + assetNumber); // 중복값 입력 확인
            return "redirect:assetAdd"; // assetadd?error_code=-99";
        }
        model.addAttribute("asset", assetVo);
        return "itam/asset/assetResult"; // 자산 등록 후 보여질 화면
    }

    @GetMapping("/assetSearch") // 자산 등록 후 화면 & 검색 화면(조회를 먼저 하고 수정하는 방향)
    public String searchPage(AssetVo assetVo, Model model) {
        log.info("장비 정보 조회 화면입니다.");
        /* datalist 장비번호 검색 자동완성 */
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("assetList", assetList);
        return "itam/asset/assetSearch";
    }

    @PostMapping("/assetSearch") // 자산 내역 검색 및 수정 처리 화면
    public String searchResult(AssetVo assetVo, String assetNumber, String memberId, Model model) {
        AssetVo assetNum = AssetService.assetSearch(assetNumber);
        /* datalist 장비번호 검색 자동완성 */
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("assetList", assetList);

        if (assetNum == null) { // 관리번호 일치 항목 없을 경우 에러 처리
            log.info("조회하기 : 일치하는 관리번호 없음");
            return "redirect:assetSearch";
        }
        model.addAttribute("asset", assetNum);
        log.info("장비 정보를 조회합니다. 관리번호 : {}", assetNum.getAssetNumber());

        log.info("사번 정보 : {}", assetNum.getMemberId());
        String search = assetNum.getMemberId();
        String searchType = "memberId";
        log.info("search : {}", search);
        log.info("searchType : {}", searchType);
        List<AssetVo> searchAssetList = AssetService.searchAssetList(search, searchType);
        model.addAttribute("list", searchAssetList);


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

    @ModelAttribute("AssetSupplies")
    public AssetSupplies[] assetSupplies() { // enum은 values를 반환하면 value 값들을 배열로 넘겨준다.
        return AssetSupplies.values();
    }

    @PostMapping("/assetUpdate") // 장비 수정 작업 화면
    public String updatePage(AssetVo assetVo, String assetNumber, Model model) {
        /*AssetService.modifyInfo(assetVo);*/
        AssetVo asset = AssetService.assetSearch(assetNumber);
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

        /* datalist 장비번호 검색 자동완성 */
        List<AssetVo> assetList = AssetService.getAssetList();
        model.addAttribute("assetList", assetList);

        AssetVo asset = AssetService.assetSearch(assetNumber);
        model.addAttribute("asset", assetVo);  // 수정 후 변경 내역 다시 보기 위한 값 가져오기


        return "itam/asset/assetSearch";
        /*return "redirect:assetSearch";*/
        /*return "redirect:/assetSearch";*/
        /*return "itam/asset/assetResult";*/
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
