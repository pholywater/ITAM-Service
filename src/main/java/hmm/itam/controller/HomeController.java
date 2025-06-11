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
    public String home(HttpSession session, UserVo userVo, Model model, String search, String search1) {
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

        String[] deviceCodes = {"D", "L", "LaptopOffice", "LaptopBusiness", "M"};
        String[] deviceNames = {"Desktop", "Laptop", "LaptopOffice", "LaptopBusiness", "Monitor"};
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