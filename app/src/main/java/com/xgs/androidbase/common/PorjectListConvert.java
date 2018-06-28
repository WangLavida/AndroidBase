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
     * @param baseWanBean
     * @param projectTreeDB
     * @return
     */
    public static List<ProjectTreeBean> convertList(BaseWanBean<ProjectTreeBean> baseWanBean, List<ProjectTreeBean> projectTreeDB) {
        List<ProjectTreeBean> projectTreeBeans = new ArrayList<ProjectTreeBean>();
        for (ProjectTreeBean projectTreeBean : baseWanBean.getData()) {
            for (ProjectTreeBean projectTreeBean1 : projectTreeDB) {
                if (projectTreeBean.getId().equals(projectTreeBean1.getId())) {
                    projectTreeBean.setFollow(projectTreeBean1.isFollow());
                }
            }
            projectTreeBeans.add(new ProjectTreeBean(projectTreeBean.getId(), projectTreeBean.getName(), projectTreeBean.getOrder(), projectTreeBean.getParentChapterId(), projectTreeBean.getVisible(), projectTreeBean.isFollow()));
        }
        if (projectTreeDB.size() == 0) {
            for (int i = 0; i <= 3; i++) {
                projectTreeBeans.get(i).setFollow(true);
            }
        }
        return projectTreeBeans;
    }
}
