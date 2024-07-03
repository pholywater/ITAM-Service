package hmm.itam.vo;

import lombok.Data;

@Data
public class UserVo {
    private Long idx;
    private String hmmId;
    private String name;
    private String comment;
    private String department;
    private String password;
    private String authority;
}
