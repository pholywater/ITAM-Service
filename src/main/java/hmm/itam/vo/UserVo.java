package hmm.itam.vo;

import lombok.Data;

@Data
public class UserVo {

    private Long idx;
    private String hmm_id;
    private String name;
    private String department;
    private String password;
}
