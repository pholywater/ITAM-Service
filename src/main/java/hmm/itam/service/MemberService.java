package hmm.itam.service;

import hmm.itam.mapper.MemberMapper;
import hmm.itam.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;

    public List<MemberVo> getMemberList() {

        return memberMapper.getMemberList();
    }
    public void memberAdd(MemberVo memberVo){
        memberMapper.insertMember(memberVo);
    }
}
