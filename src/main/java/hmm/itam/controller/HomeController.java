package hmm.itam.controller;

import com.sun.jdi.CharType;
import hmm.itam.service.AssetService;
import hmm.itam.service.MemberService;
import hmm.itam.service.UserService;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.MemberVo;
import hmm.itam.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    public String home(HttpSession session, UserVo userVo, Model model, String search, String search1/*, String authority*/) {
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


            List<AssetVo> chart2Label = userService.getChartList2();
            List<AssetVo> chart2Point = userService.getChartCount2();
            model.addAttribute("label1", chart2Label);
            model.addAttribute("point1", chart2Point);
            //log.info("label1 : {}", chart2Label);
            //log.info("point1 : {}", chart2Point);

            /*HEADER 화면 권한 체크*/
            /*th:if="${#strings.equals(user.authority, 'admin')}"*/
            /*authority = userList.getAuthority();
            log.info("화면 권한 : {}", authority);*/
            log.info("{}님께서 로그인 하였습니다.", userList.getName());
            return "/home";
        }
        return "redirect:/login"; // 정상 작동
    }

}