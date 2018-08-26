package com.truechain.task.model.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BsRecommendTask")
@DynamicUpdate
public class BsRecommendTask extends BaseEntity {

    /**
     * 被推荐人
     */
    @OneToOne
    @JoinColumn(name = "userId")
    private SysUser user;

    /**
     * 推荐人
     */
    @OneToOne
    @JoinColumn(name = "recommendUserId")
    private SysUser recommendUser;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysUser getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(SysUser recommendUser) {
        this.recommendUser = recommendUser;
    }
}
