package JavaBean;

import com.sun.deploy.ref.AppModel;
import com.sun.rowset.CachedRowSetImpl;
import java.util.List;

/**
 * @Author: michael
 * @Date: 16-10-21 下午5:09
 * @Project: Uni-Pinter
 * @Package: JavaBean
 */
public class ShowListBean {
    private CachedRowSetImpl rowSet = null;
    private int pageSize = 10;
    private int pageAllCount = 0;
    private int showPage;
    private boolean initialized = false;
    private boolean havePrev = false;
    private boolean haveNext = false;
    private Item type;
    private List beanSet;

    public CachedRowSetImpl getRowSet() {
        return rowSet;
    }

    public void setRowSet(CachedRowSetImpl rowSet) {
        this.rowSet = rowSet;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageAllCount() {
        return pageAllCount;
    }

    public void setPageAllCount(int pageAllCount) {
        this.pageAllCount = pageAllCount;
    }

    public int getShowPage() {
        return showPage;
    }

    public void setShowPage(int showPage) {
        this.showPage = showPage;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isHavePrev() {
        return havePrev;
    }

    public void setHavePrev(boolean havePrev) {
        this.havePrev = havePrev;
    }

    public boolean isHaveNext() {
        return haveNext;
    }

    public void setHaveNext(boolean haveNext) {
        this.haveNext = haveNext;
    }

    public Item getType() {
        return type;
    }

    public void setType(Item type) {
        this.type = type;
    }

    public List getBeanSet() {
        return beanSet;
    }

    public void setBeanSet(List beanSet) {
        this.beanSet = beanSet;
    }
}
