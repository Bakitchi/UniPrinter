package JavaBean;

/**
 * @Author: michael
 * @Date: 16-10-21 下午4:15
 * @Project: Uni-Pinter
 * @Package: JavaBean
 */
public class InfoBean {
    private String info;
    private String warning;
    private String error;
//    private boolean initialized = false;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
