package com.xgs.androidbase.common;

import com.xgs.androidbase.bean.BaseWanBean;
import com.xgs.androidbase.bean.ProjectTreeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by W.J on 2018/6/28.
 */

public class PorjectListConvert {
    /**
     * 分类列表和数据库合并
     *
     * @param baseWanBean
     * @param projectTreeDB
     * @return
     */
    public static List<ProjectTreeBean> convertList(BaseWanBean<ProjectTreeBean> baseWanBean, List<ProjectTreeBean> projectTreeDB) {
        List<ProjectTreeBean> projectTreeBeans = new ArrayList<ProjectTreeBean>();
        for (ProjectTreeBean projectTreeBean : baseWanBean.getData()) {
            boolean isExist = false;
            for (ProjectTreeBean projectTreeBean1 : projectTreeDB) {
                if (projectTreeBean.getId().equals(projectTreeBean1.getId())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                projectTreeBeans.add(projectTreeBean);
            }
        }
        if (projectTreeDB.size() == 0) {
            for (int i = 0; i <= 2; i++) {
                projectTreeBeans.get(i).setFollow(true);
                projectTreeBeans.get(i).setFixed(true);
            }
        }
        return projectTreeBeans;
    }
}
