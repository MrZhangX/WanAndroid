package com.example.a10850.wanandroid.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/***
 * 创建时间：2020/2/29 9:58
 * 创建人：10850
 * 功能描述：
 */
@Entity(nameInDb = "HISTORY_SEARCH")
public class HistorySearchDao {

    @Id(autoincrement = true)
    private Long id;
    private String history;
    @Generated(hash = 1775342009)
    public HistorySearchDao(Long id, String history) {
        this.id = id;
        this.history = history;
    }
    @Generated(hash = 728115128)
    public HistorySearchDao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHistory() {
        return this.history;
    }
    public void setHistory(String history) {
        this.history = history;
    }


}
