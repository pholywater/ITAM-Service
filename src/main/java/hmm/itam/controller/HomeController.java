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
            log.info("{}님께서 로그인 하였습니다.", userList.getName());
            /*return "/home";*/
            return "redirect:/homeChart";
        }
        return "redirect:/login"; // 정상 작동
    }

    @GetMapping("/homeTable") // 장비 운영 현황 테이블
    public String getHomeTable(Model model) {
        List<AssetVo> tableList = userService.getChartTableList();
        model.addAttribute("list", tableList);
        List<AssetVo> table1List = userService.getTable1List();
        model.addAttribute("list1", table1List);
        List<AssetVo> table2List = userService.getTable2List();
        model.addAttribute("list2", table2List);
        return "/homeTable";
    }

    @GetMapping("/homeTable1") // 장비 지급 수량
    public String getHomeTable1(Model model) {
        List<AssetVo> tableList = userService.getChartTableList();
        log.info("통계 테이블 불러오기");
        model.addAttribute("list", tableList);
        List<AssetVo> table1List = userService.getTable1List();
        log.info("통계 테이블 불러오기");
        model.addAttribute("list1", table1List);
        List<AssetVo> table2List = userService.getTable2List();
        model.addAttribute("list2", table2List);
        return "/homeTable_test";
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
        return "/homeChart";
    }
}