package com.hzxiaojietan.base.business.illegalparking.model.bean;

import com.google.gson.annotations.SerializedName;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by xiaojie.tan on 2017/10/26
 */
public class Tag extends DataSupport {

    @SerializedName("id")
    @Column(unique = true)
    private long tagId;
    private String name;
    private int type;
    private int visible;


    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
