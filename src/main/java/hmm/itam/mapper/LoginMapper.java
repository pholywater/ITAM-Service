package hmm.itam.mapper;

import hmm.itam.vo.LoginVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {
    List<LoginVo> getLoginList();
}
