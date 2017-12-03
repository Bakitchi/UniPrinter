package JavaBean;

import java.sql.Timestamp;

/**
 * @Author: michael
 * @Date: 16-10-21 下午5:01
 * @Project: Uni-Pinter
 * @Package: JavaBean
 */
public class AdminBean {
    private int id;
    private String username;
    private String password;
    private boolean authority;
    private String tel;
    private String email;
    private String license;
    private Timestamp expiration;
    private String info;
    private boolean status;

    public static final short ID = 1;
    public static final short USERNAME = 2;
    public static final short PASSWORD = 3;
    public static final short AUTHORITY = 4;
    public static final short TEL = 5;
    public static final short EMAIL = 6;
    public static final short LICENSE = 7;
    public static final short EXPIRATION = 8;
    public static final short INFO = 9;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthority() {
        return authority;
    }

    public void setAuthority(boolean authority) {
        this.authority = authority;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
