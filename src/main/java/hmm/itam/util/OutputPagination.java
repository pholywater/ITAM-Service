package hmm.itam.util;

public class OutputPagination {
    public String outputServletPagination(int choicePage, int limitRow,  int totalRow, String executionFunction) {

        int totalPage = (int)((totalRow - 1) / limitRow);

        int lastPage = 0;

        int prevPage = (int)((choicePage - 1) / 5) * 5;

        if((prevPage + 4) > totalPage) {
            lastPage = totalPage;
        } else {
            lastPage = prevPage + 4;
        }
        String pagination = "<ul class='pagination'>";

        if(prevPage == 0) {
            pagination += "<li class='pagination-btn'><a class='pagination-link' href='javascript:;'>첫페이지</a></li>";
            pagination += "<li class='pagination-btn'><a class='pagination-link' href='javascript:;'>&lt;&lt;</a></li>";
        } else {
            pagination += String.format("<li class='pagination-btn'><a class='pagination-link' href='javascript:;' onClick='%s(%s);'>첫페이지</a></li>", executionFunction, 1);
            pagination += String.format("<li class='pagination-btn'><a class='pagination-link' href='javascript:;' onClick='%s(%s);'>&lt;&lt;</a></li>", executionFunction, prevPage);
        }

        // 페이징 INDEX를 출력한다.
        for(int pageNum = prevPage; pageNum <= lastPage; pageNum++) {
            int disPage = pageNum + 1;
            if(disPage == choicePage) {
                pagination += String.format("<li class='pagination-btn active'><a class='pagination-link' href='javascript:;'>%s</a></li>", disPage);
            } else {
                pagination += String.format("<li class='pagination-btn'><a class='pagination-link' href='javascript:;' onClick=\"%s(%s);\">%s</a></li>", executionFunction, disPage, disPage);
            }
        }

        if(lastPage != totalPage) {
            int nextPage = prevPage + 6;
            pagination += String.format("<li class='pagination-btn'><a class='pagination-link' href='javascript:;' onClick='%s(%s);'>&gt;&gt;</a></li>", executionFunction, nextPage);
            pagination += String.format("<li class='pagination-btn'><a class='pagination-link' href='javascript:;' onClick='%s(%s);'>끝페이지</a></li>", executionFunction, totalPage + 1);
        } else  {
            pagination += "<li class='pagination-btn'><a class='pagination-link' href='javascript:;'>&gt;&gt;</a></li>";
            pagination += "<li class='pagination-btn'><a class='pagination-link' href='javascript:;'>끝페이지</a></li>";
        }

        String returnNav = pagination  + "</ul>";
        return returnNav;
    }
}
