package hmm.itam.controller;

import com.sun.jdi.CharType;
import hmm.itam.service.AssetService;
import hmm.itam.service.MemberService;
import hmm.itam.service.UserService;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.ChartVo;
import hmm.itam.vo.MemberVo;
import hmm.itam.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static hmm.itam.service.AssetService.*;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String homeLogin(HttpSession session, UserVo userVo, Model model, String search, String search1) {
        Long idx = (Long) session.getAttribute("userId");
        if (idx != null) {
            UserVo userList = userService.getUserById(idx);
            model.addAttribute("user", userList);


            /*HEADER 화면 권한 체크*/
            /*th:if="${#strings.equals(user.authority, 'admin')}"*/
            /*authority = userList.getAuthority();
            log.info("화면 권한 : {}", authority);*/
            log.info("{}님께서 로그인 하였습니다.", userList.getName());
            /*return "/home";*/
            return "redirect:homeChart";
        }
        return "redirect:login"; // 정상 작동
    }

    @GetMapping("/chartInventory") // 초기 chart 작업(미사용)
    public String home(HttpSession session, UserVo userVo, Model model, String search, String search1) {
        search1 = String.valueOf("seoul");
        search = String.valueOf('D');
        List<AssetVo> chart1DLabel = userService.getChartList1(search, search1);
        List<AssetVo> chart1DPoint = userService.getChartCount1(search, search1);
        model.addAttribute("labelD", chart1DLabel);
        model.addAttribute("pointD", chart1DPoint);
        //log.info("labelD : {}", chart1DLabel);
        //log.info("pointD : {}", chart1DPoint);

        search1 = String.valueOf("seoul");
        search = String.valueOf('L');
        List<AssetVo> chart1LLabel = userService.getChartList1(search, search1);
        List<AssetVo> chart1LPoint = userService.getChartCount1(search, search1);
        model.addAttribute("labelL", chart1LLabel);
        model.addAttribute("pointL", chart1LPoint);
        //log.info("labelL : {}", chart1LLabel);
        //log.info("pointL : {}", chart1LPoint);

        search1 = String.valueOf("seoul");
        search = String.valueOf('M');
        List<AssetVo> chart1MLabel = userService.getChartList1(search, search1);
        List<AssetVo> chart1MPoint = userService.getChartCount1(search, search1);
        model.addAttribute("labelM", chart1MLabel);
        model.addAttribute("pointM", chart1MPoint);
        //log.info("labelM : {}", chart1MLabel);
        //log.info("pointM : {}", chart1MPoint);

        search1 = String.valueOf("busan1");
        List<AssetVo> chartBusan1Label = userService.getChartList1(search, search1);
        List<AssetVo> chartBusan1Point = userService.getChartCount1(search, search1);
        model.addAttribute("labelBusan1", chartBusan1Label);
        model.addAttribute("pointBusan1", chartBusan1Point);
        //log.info("labelBusan1 : {}", chartBusan1Label);
        //log.info("pointBusan1 : {}", chartBusan1Point);

        search1 = String.valueOf("busan2");
        List<AssetVo> chartBusan2Label = userService.getChartList1(search, search1);
        List<AssetVo> chartBusan2Point = userService.getChartCount1(search, search1);
        model.addAttribute("labelBusan2", chartBusan2Label);
        model.addAttribute("pointBusan2", chartBusan2Point);
        //log.info("labelBusan2 : {}", chartBusan2Label);
        //log.info("pointBusan2 : {}", chartBusan2Point);

        search1 = String.valueOf("newAsset");
        List<AssetVo> newAssetLabel = userService.getChartList1(search, search1);
        List<AssetVo> newAssetPoint = userService.getChartCount1(search, search1);
        model.addAttribute("newAssetLabel", newAssetLabel);
        model.addAttribute("newAssetPoint", newAssetPoint);
        //log.info("newAssetLabel : {}", newAssetLabel);
        //log.info("newAssetPoint : {}", newAssetPoint);

        search1 = String.valueOf("seoul");
        search = String.valueOf('D');
        List<AssetVo> chart2DLabel = userService.getChartList2(search, search1);
        List<AssetVo> chart2DPoint = userService.getChartCount2(search, search1);
        model.addAttribute("labelD1", chart2DLabel);
        model.addAttribute("pointD1", chart2DPoint);

        search1 = String.valueOf("seoul");
        search = String.valueOf('L');
        List<AssetVo> chart2LLabel = userService.getChartList2(search, search1);
        List<AssetVo> chart2LPoint = userService.getChartCount2(search, search1);
        model.addAttribute("labelL1", chart2LLabel);
        model.addAttribute("pointL1", chart2LPoint);

        search1 = String.valueOf("seoul");
        search = String.valueOf('M');
        List<AssetVo> chart2MLabel = userService.getChartList2(search, search1);
        List<AssetVo> chart2MPoint = userService.getChartCount2(search, search1);
        model.addAttribute("labelM1", chart2MLabel);
        model.addAttribute("pointM1", chart2MPoint);

        search1 = String.valueOf("busan");
        search = String.valueOf('A');
        List<AssetVo> chart2BusanLabel = userService.getChartList2(search, search1);
        List<AssetVo> chart2BusanPoint = userService.getChartCount2(search, search1);
        model.addAttribute("labelBusan3", chart2BusanLabel);
        model.addAttribute("pointBusan3", chart2BusanPoint);

        /*HEADER 화면 권한 체크*/
        /*th:if="${#strings.equals(user.authority, 'admin')}"*/
            /*authority = userList.getAuthority();
            log.info("화면 권한 : {}", authority);*/
        return "/home";

    }

    @GetMapping("/homeTable") // 장비 운영 현황 테이블
    public String getHomeTable(Model model) {
        List<AssetVo> tableList = userService.getChartTableList();
        model.addAttribute("list", tableList);
        List<AssetVo> table1List = userService.getTable1List();
        model.addAttribute("list1", table1List);
        List<AssetVo> table2List = userService.getTable2List();
        model.addAttribute("list2", table2List);
        return "homeTable";
    }

    @GetMapping("/homeTable1") // 장비 지급 수량 -  hometable에 통합됨
    public String getHomeTable1(Model model) {
        List<AssetVo> tableList = userService.getChartTableList();
        log.info("통계 테이블 불러오기");
        model.addAttribute("list", tableList);
        List<AssetVo> table1List = userService.getTable1List();
        log.info("통계 테이블 불러오기");
        model.addAttribute("list1", table1List);
        List<AssetVo> table2List = userService.getTable2List();
        model.addAttribute("list2", table2List);
        return "homeTable_test";
    }


    @GetMapping("/homeChart")
    public String getHomeChart(ModelMap map, Model model) {
        log.info("통계 차트 불러오기");

        String[] deviceCodes = {"D", "L", "LaptopOffice", "LaptopBusiness", "M", "Seoul", "Busan", "HOS"};
        String[] deviceNames = {"Desktop", "Laptop", "LaptopOffice", "LaptopBusiness", "Monitor", "Seoul", "Busan", "HOS"};
        String[] countTypes = {"totalCount", "outCount", "inCount"};

        for (int i = 0; i < deviceCodes.length; i++) {
            String deviceCode = deviceCodes[i];
            String deviceName = deviceNames[i];

            // 장비 리스트
            List<ChartVo> chartList = userService.getChartList(deviceCode, "totalCount");
            model.addAttribute("chart" + deviceName + "List", chartList);

            // 카운트 항목들
            for (String countType : countTypes) {
                List<ChartVo> chartCount = userService.getChartCount(deviceCode, countType);
                model.addAttribute("chart" + deviceName + capitalize(countType), chartCount);
            }
        }

        // 필요 시 추가 항목 처리 (예: hmmWork, hmmRent 등)
        String[] extraTypes = {
                "hmmWork", "hmmRent", "hmmPublic", "hmmHelp", "hmmBroken",
                "busanHelp", "busanBroken"
        };

        for (String extraType : extraTypes) {
            List<ChartVo> extraCount = userService.getChartCount("D", extraType);
            model.addAttribute("chartDesktop" + capitalize(extraType), extraCount);
        }

        return "homeChart";
    }

    // 첫 글자 대문자로 변환하는 헬퍼 메서드
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}