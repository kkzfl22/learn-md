package org.example.webflux.entity;

/**
 * 实体信息
 *
 * @author nullnull
 * @since 2024/12/11
 */
public class Profile {

    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;


    public Profile(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
