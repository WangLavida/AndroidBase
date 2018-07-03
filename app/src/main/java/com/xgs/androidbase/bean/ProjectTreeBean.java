package com.xgs.androidbase.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by W.J on 2018/6/26.
 */
@Entity
public class ProjectTreeBean implements Serializable{
    static final long serialVersionUID = 42L;
    /**
     * children : []
     * courseId : 13
     * id : 294
     * name : 完整项目
     * order : 145000
     * parentChapterId : 293
     * visible : 0
     */

    @Id(autoincrement = true)
    private Long lid;
    private int courseId;
    private Long id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;
    //关注
    private boolean isFollow;
    //固定
    private boolean isFixed;

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    @Generated(hash = 1618667555)
    public ProjectTreeBean(Long lid, int courseId, Long id, String name, int order,
            int parentChapterId, int visible, boolean isFollow, boolean isFixed) {
        this.lid = lid;
        this.courseId = courseId;
        this.id = id;
        this.name = name;
        this.order = order;
        this.parentChapterId = parentChapterId;
        this.visible = visible;
        this.isFollow = isFollow;
        this.isFixed = isFixed;
    }

    public ProjectTreeBean(Long id, String name, int order,
                           int parentChapterId, int visible,boolean isFollow) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.parentChapterId = parentChapterId;
        this.visible = visible;
        this.isFollow = isFollow;
    }
    @Generated(hash = 1341939973)
    public ProjectTreeBean() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public boolean getIsFollow() {
        return this.isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

    public boolean getIsFixed() {
        return this.isFixed;
    }

    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }


}
