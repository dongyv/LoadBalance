package application.config;

public class ModuleConfig {

    public static final String user = "/user";

    public static final String member = "/member";

    public enum ParamEnum {
        SIGN("sign"),;
        private String name;

        ParamEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
}
