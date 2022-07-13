package com.atguigu.base;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
/*
Alt+鼠标 纵向选择
->  <- 单个字符移动
Ctrl+->  ctrl+<- 单个单词移动
Ctrl+shift+->  ctrl+shift+<- 单个单词选中
Shift+end 选中到行尾所有内容
 */
@SuppressWarnings("unchecked")
public class BaseController {
    /**
     * 封装页面提交的分页参数及搜索条件
     * @param request
     * @return
     * roleName=manager&hobby=music&hobby=sport&hobby=art&pageNum=1&pageSize=10
     * roleName=manager&hobby=music&hobby=sport&hobby=art
     * roleName=manager
     * roleName=
     */
    protected Map<String, Object> getFilters(HttpServletRequest request) {
        //roleName hobby pageNum
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while(paramNames != null && paramNames.hasMoreElements()) {
            //roleName
            //hobby
            //pageNum
            String paramName = (String)paramNames.nextElement();
            //[manager]
            //[sport,music,art]
            //[1]
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    //hobby--->[sport,music,art]
                    filters.put(paramName, values);
                } else {
                    //roleName--->manager
                    //pageNum---->1
                    filters.put(paramName, values[0]);
                }
            }
        }
        if(!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1); //如果没有指定当前页号，默认第一页
        }
        if(!filters.containsKey("pageSize")) {
            filters.put("pageSize", 5);//如果没有指定每页的页码数，默认每页显示10
        }

        return filters;
    }
}
