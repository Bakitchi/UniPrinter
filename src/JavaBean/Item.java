package JavaBean;

/**
 * @Author: michael
 * @Date: 16-10-22 下午12:26
 * @Project: Uni-Pinter
 * @Package: JavaBean
 */

public enum Item {
    TShirt("T-shirt", 1), Lighter("lighter", 2), Shell("shell",3),
    Image("image", 4), Brand("brand", 5), Model("model", 6),
    Admin("admin", 7);
    private int num;
    private String name;

    private Item(String name, int num) {
        this.name = name;
        this.num = num;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getNum() {
        return num;
    }
}