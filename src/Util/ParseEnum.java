package Util;

/**
 * @Author: michael
 * @Date: 16-11-2 上午10:36
 * @Project: Uni-Pinter
 * @Package: Util
 */
public class ParseEnum {
    public static <T extends Enum<?>> T parseEnum(Class<T> enumeration, String search) throws IllegalArgumentException {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }
}
