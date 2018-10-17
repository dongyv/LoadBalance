package application.modle.analysis;

public class XmlParseDecorator extends GeneralDecorator{

    public XmlParseDecorator(Parse parse) {
        super(parse);
    }

    public void methodA(){
        System.out.println("被装饰器xml扩展的功能");
    }

    @Override
    public void solveTheFile() {
        System.out.println("针对该方法加一层xml包装");
        super.solveTheFile();
        System.out.println("xml解析包装结束");
    }
}
