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


    @GetMapping("/homeChart") // 장비 운영 현황 차트
    public String getHomeChart(ModelMap map, Model model, String search, String search1) {
        log.info("통계 차트 불러오기");

        search = String.valueOf('D');
        search1 = String.valueOf("totalCount");

        List<ChartVo> chartDesktopList = userService.getChartList(search, search1);
        model.addAttribute("chartDesktopList", chartDesktopList);
        /*log.info("chartDesktopList : {}", chartDesktopList);*/

        search1 = String.valueOf("totalCount");
        List<ChartVo> chartDesktopTotalCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopTotalCount", chartDesktopTotalCount);
        /*log.info("chartDesktopTotalCount : {}", chartDesktopTotalCount);*/

        search1 = String.valueOf("outCount");
        List<ChartVo> chartDesktopOutCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopOutCount", chartDesktopOutCount);
        /*log.info("chartDesktopOutCount : {}", chartDesktopOutCount);*/

        search1 = String.valueOf("inCount");
        List<ChartVo> chartDesktopInCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopInCount", chartDesktopInCount);
        /*log.info("chartDesktopInCount : {}", chartDesktopInCount);*/

        search = String.valueOf('L');
        search1 = String.valueOf("totalCount");

        List<ChartVo> chartLaptopList = userService.getChartList(search, search1);
        model.addAttribute("chartLaptopList", chartLaptopList);
        /*log.info("chartLaptopList : {}", chartLaptopList);*/

        search1 = String.valueOf("totalCount");
        List<ChartVo> chartLaptopTotalCount = userService.getChartCount(search, search1);
        model.addAttribute("chartLaptopTotalCount", chartLaptopTotalCount);
        /*log.info("chartLaptopTotalCount : {}", chartLaptopTotalCount);*/

        search1 = String.valueOf("outCount");
        List<ChartVo> chartLaptopOutCount = userService.getChartCount(search, search1);
        model.addAttribute("chartLaptopOutCount", chartLaptopOutCount);
        /*log.info("chartLaptopOutCount : {}", chartLaptopOutCount);*/

        search1 = String.valueOf("inCount");
        List<ChartVo> chartLaptopInCount = userService.getChartCount(search, search1);
        model.addAttribute("chartLaptopInCount", chartLaptopInCount);
        /*log.info("chartLaptopInCount : {}", chartLaptopInCount);*/

        search = String.valueOf('M');
        search1 = String.valueOf("totalCount");

        List<ChartVo> chartMonitorList = userService.getChartList(search, search1);
        model.addAttribute("chartMonitorList", chartMonitorList);
        /*log.info("chartMonitorList : {}", chartMonitorList);*/

        search1 = String.valueOf("totalCount");
        List<ChartVo> chartMonitorTotalCount = userService.getChartCount(search, search1);
        model.addAttribute("chartMonitorTotalCount", chartMonitorTotalCount);
        /*log.info("chartMonitorTotalCount : {}", chartMonitorTotalCount);*/

        search1 = String.valueOf("outCount");
        List<ChartVo> chartMonitorOutCount = userService.getChartCount(search, search1);
        model.addAttribute("chartMonitorOutCount", chartMonitorOutCount);
        /*log.info("chartMonitorOutCount : {}", chartMonitorOutCount);*/

        search1 = String.valueOf("inCount");
        List<ChartVo> chartMonitorInCount = userService.getChartCount(search, search1);
        model.addAttribute("chartMonitorInCount", chartMonitorInCount);
        /*log.info("chartMonitorInCount : {}", chartMonitorInCount);*/

        /*search1 = String.valueOf("hmmWork");
        List<ChartVo> chartDesktopHmmWorkCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopHmmWorkCount", chartDesktopHmmWorkCount);
        search1 = String.valueOf("hmmRent");
        List<ChartVo> chartDesktopHmmRentCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopHmmRentCount", chartDesktopHmmRentCount);
        search1 = String.valueOf("hmmPublic");
        List<ChartVo> chartDesktopHmmPublicCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopHmmPublicCount", chartDesktopHmmPublicCount);
        search1 = String.valueOf("hmmHelp");
        List<ChartVo> chartDesktopHmmHelpCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopHmmHelpCount", chartDesktopHmmHelpCount);
        search1 = String.valueOf("hmmBroken");
        List<ChartVo> chartDesktopHmmBrokenCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopHmmBrokenCount", chartDesktopHmmBrokenCount);
        search1 = String.valueOf("busanHelp");
        List<ChartVo> chartDesktopBusanHelpCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopBusanHelpCount", chartDesktopBusanHelpCount);
        search1 = String.valueOf("busanBroken");
        List<ChartVo> chartDesktopBusanBrokenCount = userService.getChartCount(search, search1);
        model.addAttribute("chartDesktopBusanBrokenCount", chartDesktopBusanBrokenCount);*/


        /*model.addAttribute("count", chartCount);*/
        return "homeChart";
    }
}