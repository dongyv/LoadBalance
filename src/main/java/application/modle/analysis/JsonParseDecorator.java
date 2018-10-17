package application.modle.analysis;

public class JsonParseDecorator extends GeneralDecorator{

    public JsonParseDecorator(Parse parse) {
        super(parse);
    }

    public void methodB(){
        System.out.println("被装饰器json扩展的功能");
    }

    @Override
    public void solveTheFile() {
        System.out.println("针对该方法加一层json包装");
        super.solveTheFile();
        System.out.println("json解析包装结束");
    }
}
