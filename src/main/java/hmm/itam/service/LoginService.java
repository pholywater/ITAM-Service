package hmm.itam.service;

import hmm.itam.mapper.LoginMapper;
import hmm.itam.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    public List<LoginVo> getLoginList() {
        return loginMapper.getLoginList();
    }
}
