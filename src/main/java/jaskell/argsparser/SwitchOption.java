package jaskell.argsparser;

public class SwitchOption implements Arg {
    private String help;
    final private String name;
    private boolean defaultValue;
    private boolean required;

    @Override
    public String getHelp() {
        return help;
    }


    public String getName() {
        return name;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public SwitchOption(String name, String help, boolean defaultValue, boolean required) {
        this.name = name;
        this.help = help;
        this.defaultValue = defaultValue;
        this.required = required;
    }

    @Override
    public String argString() {
        return "--<enable|disable>-"+name;
    }

    public SwitchOption help(String value) {
        this.help = value;
        return this;
    }

    public SwitchOption enable() {
        this.defaultValue = true;
        return this;
    }

    public SwitchOption disable() {
        this.defaultValue = false;
        return this;
    }

    public SwitchOption required(boolean value) {
        this.required = value;
        return this;
    }

    public SwitchOption defaultValue(Boolean value) {
        this.defaultValue = value;
        return this;
    }

    public static SwitchOption create(String name) {
        return new SwitchOption(name, null, false, false);
    }
}
