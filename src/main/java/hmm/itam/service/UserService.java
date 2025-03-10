package hmm.itam.service;

import hmm.itam.mapper.UserMapper;
import hmm.itam.vo.AssetVo;
import hmm.itam.vo.ChartVo;
import hmm.itam.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /*회원 정보 리스트*/
    public List<UserVo> getUserList() {
        return userMapper.getUserList();
    }

    public List<AssetVo> getChartTableList() {
        return userMapper.getChartTableList();
    }

    public List<AssetVo> getTable1List() {
        return userMapper.getTable1List();
    }

    public List<AssetVo> getTable2List() {
        return userMapper.getTable2List();
    }


    public List<ChartVo> getChartList(String search, String search1) {
        return userMapper.getChartList(search, search1);
    }

    public List<ChartVo> getChartCount(String search, String search1) {
        return userMapper.getChartCount(search, search1);
    }


    public List<AssetVo> getChartList1(String search, String search1) {
        return userMapper.getChartList1(search, search1);
    }

    public List<AssetVo> getChartCount1(String search, String search1) {
        return userMapper.getChartCount1(search, search1);
    }

    public List<AssetVo> getChartList2(String search, String search1) {
        return userMapper.getChartList2(search, search1);
    }

    public List<AssetVo> getChartCount2(String search, String search1) {
        return userMapper.getChartCount2(search, search1);
    }

    /*메인 로그인 화면 아이디, 패스워드 확인 처리 작업*/
    public Long login(String hmmId, String password) {
        UserVo userVo = userMapper.getUserByHmmId(hmmId);

        /* NullPointerException 처리(ID, PW 불일치 및 없음) */
        if (userVo == null) {
            return null;
        }

        if (userVo.getPassword().equals(password)) {
            return userVo.getIdx();
/*
        System.out.println("service.check.hmmId :"+ hmm_id);
        System.out.println("service.check.password :"+ password);
        System.out.println("service.check.vo.password :"+ userVo.getPassword());
        System.out.println("service.check.getIdx :"+ userVo.getIdx());
*/
        }
        return null;
    }

    /*회원 정보 등록*/
    public void signup(UserVo userVo) {

        userMapper.insertUser(userVo);
    }

    /*홈 화면 정보 확인*/
    public UserVo getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    /*회원 정보 수정*/
    public void modifyInfo(UserVo userVo) {
        userMapper.updateUser(userVo);
    }

    /*탈퇴하기*/
    public void withdraw(Long id) {
        userMapper.deleteUser(id);
    }
}
