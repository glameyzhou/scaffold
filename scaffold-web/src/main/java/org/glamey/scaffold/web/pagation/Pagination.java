package org.glamey.scaffold.web.pagation;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>分页计算
 * Create by zhouyang.zhou
 */
public class Pagination<E> {

    public static final int ROWS_PER_PAGE = 15;
    public static final int PAGE_INDEX_COUNT = 10;
    /**
     * 总条目数
     */
    private int maxRowCount;
    /**
     * 当前页码
     */
    private int curPage;
    /**
     * 每页大小
     */
    private int rowsPerPage;
    /**
     * 总页数
     */
    private int maxPage;
    /**
     * 是否有上一页
     */
    private boolean prePage;
    /**
     * 是否有下一页
     */
    private boolean nextPage;
    /**
     * 绑定的实体list
     */
    private List<E> data;
    /**
     * 用于显示页面的下标。例如上一页,1,2,3,4下一页
     */
    private List<Integer> pageNoList;

    /**
     * @param maxRowCount 总条目数
     * @param curPage     当前页号
     */
    public Pagination(int maxRowCount, int curPage) {
        this(maxRowCount, curPage, ROWS_PER_PAGE);
    }

    /**
     * @param maxRowCount 总条目数
     * @param curPage     当前页号
     * @param rowsPerPage 每页大小
     */
    public Pagination(int maxRowCount, int curPage, int rowsPerPage) {
        this.maxRowCount = maxRowCount;
        this.curPage = curPage;
        this.rowsPerPage = rowsPerPage;
        initTotalPage();
    }

    private Pagination() {
    }

    public static <K> Pagination<K> instance(int rowsCount, int curPage, int rowsPerPage) {
        return new Pagination<K>(rowsCount, curPage, rowsPerPage);
    }

    private void initTotalPage() {

        //设置最大页
        if (this.maxRowCount != 0) {
            if (this.maxRowCount % this.rowsPerPage == 0) {
                this.maxPage = this.maxRowCount / this.rowsPerPage;
            } else {
                this.maxPage = this.maxRowCount / this.rowsPerPage + 1;
            }
        }
        //设置是否有上一页和下一页 
        if (this.maxRowCount <= 1) {
            this.prePage = false;
            this.nextPage = false;
        } else {
            this.prePage = true;
            this.nextPage = true;
            if (this.curPage <= 1) {
                this.prePage = false;
            }
            if (this.curPage == this.maxPage) {// 最后一页
                this.nextPage = false;
            }
        }
        //设置页下标
        setPageNoList();

    }

    public List<Integer> getPageNoList() {
        return this.pageNoList;
    }

    public void setPageNoList() {
        List<Integer> listNo = new ArrayList<Integer>();
        if (this.maxPage <= PAGE_INDEX_COUNT) {
            for (int i = 1; i <= this.maxPage; i++) {
                listNo.add(Integer.valueOf(i));
            }
        } else if (getCurPage() == 0) {
            for (int i = 1; i <= PAGE_INDEX_COUNT; i++)
                listNo.add(Integer.valueOf(i));
        } else {
            for (int i = this.curPage; i < PAGE_INDEX_COUNT + this.curPage; i++) {
                if (i <= this.maxPage) {
                    listNo.add(Integer.valueOf(i));
                }
            }

            if ((listNo.size() > 0) &&
                    (listNo.size() < PAGE_INDEX_COUNT)) {
                List needNo = new ArrayList();
                int diff = PAGE_INDEX_COUNT - listNo.size();
                int co = 0;

                for (int m = (listNo.get(0)).intValue() - diff; m <= (listNo.get(0)).intValue() - diff + PAGE_INDEX_COUNT; m++) {
                    co++;
                    if (co <= diff)
                        needNo.add(Integer.valueOf(m));
                }
                needNo.addAll(listNo);
                listNo = needNo;
            }

        }

        this.pageNoList = listNo;
    }

    /**
     * 绑定list
     *
     * @param data the result data
     * @return {@link Pagination}
     */
    public Pagination bindData(List<E> data) {
        this.data = data;
        return this;
    }

    public int getStartOffset() {
        return (this.curPage - 1) * this.rowsPerPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public int getMaxRowCount() {
        return maxRowCount;
    }

    public int getMaxPage() {
        if (maxPage == 0)
            return 1;
        return maxPage;
    }

    public boolean isPrepage() {
        return this.prePage;
    }

    public boolean isNextpage() {
        return this.nextPage;
    }

    public List<E> getData() {
        return data;
    }

    public void setMaxRowCount(int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setPrePage(boolean prePage) {
        this.prePage = prePage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("rowsPerPage=").append(rowsPerPage).append(",")
                .append("curPage=").append(curPage).append(",")
                .append("maxRowCount=").append(maxRowCount).append(",")
                .append("maxPage=").append(maxPage).append(",")
                .append("prePage=").append(prePage).append(",")
                .append("nextPage=").append(nextPage).append(",")
                .append("pageNoList=").append(pageNoList).append(",")
                .append("data=").append(data)
                .toString();
    }
}
