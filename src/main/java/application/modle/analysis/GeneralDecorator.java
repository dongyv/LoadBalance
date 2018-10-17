package application.modle.analysis;

/**
 * 装饰器
 * @author xiachenhang
 */
public abstract class GeneralDecorator implements Parse{

    protected Parse parse;

    public GeneralDecorator(Parse parse){
        this.parse = parse;
    }

    @Override
    public void solveTheFile() {
        parse.solveTheFile();
    }
}
