package application.modle.analysis;

/**
 * @author xiachenhang
 */
public class GeneralProvider {
    /**
     * 装饰者模式修改，改为保存数据
     * @param args
     */
    public static void main(String[] args) {
        Parse parse = new GeneralParse();
        JsonParseDecorator json = new JsonParseDecorator(parse);
        XmlParseDecorator xml = new XmlParseDecorator(parse);
        json.solveTheFile();
    }
}
