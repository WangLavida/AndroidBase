package com.xgs.androidbase.dao;

import android.content.Context;
import android.util.Log;

import com.xgs.androidbase.bean.ProjectTreeBean;

import java.util.List;

/**
 * Created by W.J on 2018/6/28.
 */

public class ProjectDaoUtil {
    private static final String TAG = ProjectDaoUtil.class.getSimpleName();
    private static DaoManager daoManager;

    public static ProjectDaoUtil  init(Context context) {
        daoManager = DaoManager.getInstance();
        daoManager.init(context);
        return new ProjectDaoUtil();
    }

    /**
     * 插入数据
     *
     * @param projectTreeBean
     * @return
     */
    public boolean insertProject(ProjectTreeBean projectTreeBean) {
        boolean flag = false;
        flag = daoManager.getDaoSession().getProjectTreeBeanDao().insert(projectTreeBean) == -1 ? false : true;
        Log.i(TAG, "insert projectTreeBean :" + flag);
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param projectList
     * @return
     */
    public boolean insertProjectList(final List<ProjectTreeBean> projectList) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (ProjectTreeBean meizi : projectList) {
                        daoManager.getDaoSession().insertOrReplace(meizi);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @param projectTreeBean
     * @return
     */
    public boolean updateProject(ProjectTreeBean projectTreeBean) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().update(projectTreeBean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 删除单条记录
     * @param projectTreeBean
     * @return
     */
    public boolean deleteProject(ProjectTreeBean projectTreeBean){
        boolean flag = false;
        try {
            //按照id删除
            daoManager.getDaoSession().delete(projectTreeBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            daoManager.getDaoSession().deleteAll(ProjectTreeBean.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<ProjectTreeBean> queryAllProject(){
        return daoManager.getDaoSession().loadAll(ProjectTreeBean.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public ProjectTreeBean queryMeiziById(long key){
        return daoManager.getDaoSession().load(ProjectTreeBean.class, key);
    }

}
