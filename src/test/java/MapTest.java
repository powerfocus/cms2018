import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    @Test
    public void test3() {
        Map<String, String> map = new HashMap<>();
        map.put("|-", "编程");
        map.put("---", "c++");
        map.put("------", "qt");
        map.put("------", "win32_mfc");
        map.put("---", "java");
        map.put("------", "javaee");
        map.put("------", "javase");
        map.put("---------", "swing");
        map.put("------", "spring");
        map.put("---------", "spring boot");
        map.put("---", "c#");
        map.put("---", "h5");
        map.put("|-", "设计");
        map.put("---", "photoshop");
        map.put("---", "3dmax");
        map.put("---", "flash");
        map.put("---", "---dreamweaver");
    }
}
