package JavaBean;

/**
 * @Author: michael
 * @Date: 16-10-21 下午4:50
 * @Project: Uni-Pinter
 * @Package: JavaBean
 */
public class ItemBean {
    private int id;
    private int uid;
    private String name;
    private String info;
    private int rank;
    private Item type;

    public static final short ID = 1;
    public static final short UID = 2;
    public static final short NAME = 3;
    public static final short INFO = 4;
    public static final String RANK = "rank";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Item getType() {
        return type;
    }

    public void setType(Item sort) {
        this.type = type;
    }
}
