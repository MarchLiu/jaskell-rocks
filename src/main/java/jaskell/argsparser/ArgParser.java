package jaskell.argsparser;

import jaskell.util.Try;

import java.util.*;
import java.util.stream.Collectors;

/**
 * I don't want to import any external dependence like apache commons cli, so
 * create this class for some tiny tasks
 */
public class ArgParser {
    private final TreeMap<String, Parameter> parameterSlot = new TreeMap<>();
    private final Map<String, Option> optionSlot = new HashMap<>();
    private final Map<String, SwitchOption> switchSlot = new HashMap<>();
    private final Map<String, WithOption> withSlot = new HashMap<>();

    private final Map<String, String> parameters = new HashMap<>();
    private final Map<String, TreeSet<String>> options = new HashMap<>();
    private final Map<String, Boolean> switches = new HashMap<>();
    private final Set<String> withes = new HashSet<>();
    private final List<String> varargs = new ArrayList<>();
    private final Map<String, String> synonyms = new HashMap<>();

    private String header;
    private String formatter = "%1$-20s %2$-20s %3$-40s\n";
    private String footer;
    private boolean isHelp = false;

    public Try<Result> parse(String[] args) {
        List<String> buffer = Arrays.asList(args);
        List<Exception> errors = new ArrayList<>();
        if (buffer.stream().anyMatch(s -> s.equals("--help") || s.equals("-h") || s.equals("-?"))) {
            isHelp = true;
            return Try.success(result());
        }
        while (!buffer.isEmpty()) {
            String head = buffer.getFirst();
            String word = head;
            if (synonyms.containsKey(head)) {
                word = synonyms.get(head);
            }

            if (word.startsWith("--enable-")) {
                var result = turnOn(word);
                if (result.isOk()) {
                    buffer = buffer.subList(1, buffer.size());
                } else {
                    errors.add(result.error());
                }
            } else if (word.startsWith("--disable-")) {
                var result = turnOff(word);
                if (result.isOk()) {
                    buffer = buffer.subList(1, buffer.size());
                } else {
                    errors.add(result.error());
                }
            } else if (word.startsWith("--with-")) {
                var result = setWith(word);
                if (result.isOk()) {
                    buffer = buffer.subList(1, buffer.size());
                } else {
                    errors.add(result.error());
                }
            } else if (word.startsWith("--without-")) {
                var result = unsetWith(word);
                if (result.isOk()) {
                    buffer = buffer.subList(1, buffer.size());
                } else {
                    errors.add(result.error());
                }
            } else if (word.startsWith("--")) {
                if (buffer.size() < 2) {
                    errors.add(new EmptyOptionException(word));
                }
                var result = putOption(word, buffer.get(1));
                if (result.isOk()) {
                    buffer = buffer.subList(1, buffer.size());
                } else {
                    errors.add(result.error());
                }
            } else if (parameters.size() < parameterSlot.size()) {
                var result = putParameter(word);
                if (result.isOk()) {
                    buffer = buffer.subList(1, buffer.size());
                } else {
                    errors.add(result.error());
                    varargs.add(word);
                    buffer = buffer.subList(1, buffer.size());
                }
            } else {
                varargs.add(word);
                buffer = buffer.subList(1, buffer.size());
            }
        }
        // find slot missed, and fill default value or failed if required
        for (var name : optionSlot.keySet()) {
            if (!options.containsKey(name)) {
                var slot = optionSlot.get(name);
                if (slot.getDefaultValue() == null) {
                    if (slot.isRequired()) {
                        errors.add(new RequiredException("option", name));
                    }
                } else {
                    var values = options.getOrDefault(name, new TreeSet<>());
                    values.add(slot.getDefaultValue());
                    options.put(name, values);
                }
            }
        }

        for (var name : parameterSlot.keySet()) {
            if (!parameters.containsKey(name)) {
                var slot = parameterSlot.get(name);
                if (slot.getDefaultValue() == null) {
                    if (slot.isRequired()) {
                        errors.add(new RequiredException("named parameter", name));
                    }
                } else {
                    parameters.put(name, slot.getDefaultValue());
                }
            }
        }

        if (errors.isEmpty()) {
            return Try.success(this.result());
        } else {
            return Try.failure(new ErrorsStack(errors));
        }
    }

    public String getHeader() {
        return Objects.requireNonNullElse(header, "");
    }

    public ArgParser header(String value) {
        this.header = value;
        return this;
    }

    public String getFooter() {
        return Objects.requireNonNullElse(footer, "");
    }

    public ArgParser footer(String value) {
        this.footer = value;
        return this;
    }

    public Expression parser() {
        return () -> this;
    }

    public Result result() {
        return () -> this;
    }

    Try<String> turnOn(String name) {
        // size of "--enable-" is 9
        var label = name.substring(9);
        if (parser().hasSwitch(label)) {
            switches.put(label, true);
            return Try.success(label);
        } else {
            return Try.failure(new NotExistsException("switch", label, name));
        }
    }

    Try<String> turnOff(String name) {
        // size of "--disable-" is 10
        var label = name.substring(10);
        if (parser().hasSwitch(label)) {
            switches.put(label, true);
            return Try.success(label);
        } else {
            return Try.failure(new NotExistsException("switch", label, name));
        }
    }

    Try<String> putOption(String name, String value) {
        // size of "--" is 2
        var label = name.substring(2);
        if (parser().hasOption(label)) {
            return parser().option(label)
                    .recoverToTry(err -> Try.failure(new NotExistsException("option", label, name)))
                    .flatMap(option -> {
                        var values = options.getOrDefault(label, new TreeSet<>());
                        if (option.validate(value)) {
                            values.add(value);
                            options.put(label, values);
                            return Try.success(label);
                        } else {
                            return Try.failure(new OptionValidateFailedException("option", name, value, option.getHelp()));
                        }
                    });
        } else {
            return Try.failure(new NotExistsException("option", label, name));
        }
    }

    Try<String> setWith(String name) {
        // size of "--with-" is 7
        var label = name.substring(7);
        if (parser().hasWith(label)) {
            var slot = withSlot.get(label);
            withes.add(label);
            return Try.success(label);
        } else {
            return Try.failure(new NotExistsException("with", label, name));
        }
    }

    Try<String> unsetWith(String name) {
        // size of "--without-" is 10
        var label = name.substring(10);
        if (parser().hasWith(label)) {
            withes.remove(label);
            return Try.success(label);
        } else {
            return Try.failure(new NotExistsException("without", label, name));
        }
    }

    Try<String> putParameter(String value) {
        if (parameters.size() < parameterSlot.size()) {
            var slots = parameterSlot.values().stream().toList();
            var index = parameters.size();
            var slot = slots.get(index);
            if (slot.validate(value)) {
                parameters.put(slot.getName(), value);
                return Try.success(value);
            } else {
                return Try.failure(new OptionValidateFailedException("varargs",
                        "%s at (%d)".formatted(slot.getName(), index),
                        value,
                        slot.getHelp()));
            }
        } else {
            return Try.failure(
                    new NoMoreSlotException(
                            "all %d parameters had been filled. Maybe %s should be a varargs"
                                    .formatted(parameterSlot.size(), value)));
        }
    }

    public ArgParser() {

    }

    public ArgParser synonyms(String word, String target) {
        this.synonyms.put(word, target);
        return this;
    }

    public ArgParser option(String name) {
        optionSlot.put(name, Option.create(name));
        return this;
    }

    public ArgParser option(Option option) {
        optionSlot.put(option.getName(), option);
        return this;
    }

    public ArgParser parameter(String name) {
        parameterSlot.put(name, Parameter.create(name));
        return this;
    }

    public ArgParser parameter(Parameter parameter) {
        parameterSlot.put(parameter.getName(), parameter);
        return this;
    }

    public ArgParser with(String name) {
        withSlot.put(name, WithOption.create(name));
        return this;
    }

    public ArgParser with(WithOption with) {
        withSlot.put(with.getName(), with);
        return this;
    }

    public ArgParser presetWith(String name) {
        withSlot.put(name, WithOption.create(name).preset());
        withes.add(name);
        return this;
    }

    public ArgParser presetWith(WithOption with) {
        withSlot.put(with.getName(), with.preset());
        withes.add(with.getName());
        return this;
    }

    public ArgParser onoff(String name) {
        switchSlot.put(name, SwitchOption.create(name));
        return this;
    }

    public ArgParser onoff(SwitchOption onoff) {
        switchSlot.put(onoff.getName(), onoff);
        return this;
    }

    public ArgParser preset(String name) {
        var wOpt = WithOption.create(name).preset();
        withSlot.put(wOpt.getName(), wOpt);
        withes.add(wOpt.getName());
        return this;
    }

    public ArgParser preset(String name, String help) {
        var wOpt = WithOption.create(name).help(help).preset();
        withSlot.put(wOpt.getName(), wOpt);
        withes.add(wOpt.getName());
        return this;
    }

    public ArgParser preset(WithOption wOpt) {
        withSlot.put(wOpt.getName(), wOpt.preset());
        withes.add(wOpt.getName());
        return this;
    }

    public ArgParser unset(String name) {
        var wOpt = WithOption.create(name).unset();
        withSlot.put(name, wOpt);
        withes.remove(name);
        return this;
    }

    public ArgParser unset(WithOption wOpt) {
        withSlot.put(wOpt.getName(), wOpt.unset());
        withes.remove(wOpt.getName());
        return this;
    }

    public ArgParser unset(String name, String help) {
        var wOpt = WithOption.create(name).help(help).unset();
        withSlot.put(name, wOpt);
        withes.remove(name);
        return this;
    }

    public ArgParser formatter(String value) {
        this.formatter = value;
        return this;
    }

    String synonymString(String word) {
        if (synonyms.containsValue(word)) {
            return synonyms.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(word)).map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    public String getFormatter() {
        return formatter;
    }

    public static ArgParser create() {
        return new ArgParser();
    }

    public interface Expression {
        ArgParser parser();

        default boolean hasOption(String name) {
            var slot = parser().optionSlot;
            return slot.containsKey(name);
        }

        default boolean hasWith(String name) {
            var slot = parser().withSlot;
            return slot.containsKey(name);
        }

        default boolean hasSwitch(String name) {
            var slot = parser().switchSlot;
            return slot.containsKey(name);
        }

        default Try<Option> option(String name) {
            var slot = parser().optionSlot;
            if (slot.containsKey(name)) {
                return Try.success(slot.get(name));
            } else {
                return Try.failure(new Exception("option %s not found".formatted(name)));
            }
        }

        default Try<WithOption> with(String name) {
            var slot = parser().withSlot;
            if (slot.containsKey(name)) {
                return Try.success(slot.get(name));
            } else {
                return Try.failure(new Exception("with option %s not found".formatted(name)));
            }
        }

        default Try<WithOption> onoff(String name) {
            var slot = parser().withSlot;
            if (slot.containsKey(name)) {
                return Try.success(slot.get(name));
            } else {
                return Try.failure(new Exception("switch %s not found".formatted(name)));
            }
        }
    }

    public interface Result {
        ArgParser parser();

        default boolean isHelp() {
            return parser().isHelp;
        }

        default boolean hasOption(String name) {
            var slot = parser().options;
            return slot.containsKey(name);
        }

        default boolean hasSwitch(String name) {
            var slot = parser().switches;
            return slot.containsKey(name);
        }

        default boolean hasWith(String name) {
            var slot = parser().withes;
            return slot.contains(name);
        }

        default Try<TreeSet<String>> option(String name) {
            var slot = parser().options;
            if (slot.containsKey(name)) {
                return Try.success(slot.get(name));
            } else {
                return Try.failure(new Exception("option %s not found".formatted(name)));
            }
        }

        default Try<String> parameter(String name) {
            var slot = parser().parameters;
            if (slot.containsKey(name)) {
                return Try.success(slot.get(name));
            } else {
                return Try.failure(new Exception("parameter %s not found".formatted(name)));
            }
        }

        default Boolean with(String name) {
            var slot = parser().withSlot;
            return slot.containsKey(name);
        }

        default Try<Boolean> onoff(String name) {
            var slot = parser().switches;
            if (slot.containsKey(name)) {
                return Try.success(slot.get(name));
            } else {
                return Try.failure(new Exception("switch %s not found".formatted(name)));
            }
        }

        default List<String> withes() {
            return parser().withes.stream().toList();
        }

        default List<String> varargs() {
            return parser().varargs;
        }

        default String help() {

            StringBuilder sb = new StringBuilder();
            if (!parser().getHeader().isBlank()) {
                sb.append(parser().getHeader()).append("\n");
            }
            for (var slot : parser().optionSlot.values()) {
                sb.append(parser().formatter.formatted(slot.argString(),
                        parser().synonymString(slot.getName()),
                        slot.getHelp()));
            }
            for (var slot : parser().parameterSlot.values()) {
                sb.append(parser().formatter.formatted(slot.argString(),
                        parser().synonymString(slot.getName()),
                        slot.getHelp()));
            }
            for (var slot : parser().withSlot.values()) {
                sb.append(parser().formatter.formatted(slot.argString(),
                        parser().synonymString(slot.getName()),
                        slot.getHelp()));
            }
            for (var slot : parser().switchSlot.values()) {
                sb.append(parser().formatter.formatted(slot.argString(),
                        parser().synonymString(slot.getName()),
                        slot.getHelp()));
            }
            if (!parser().getFooter().isBlank()) {
                sb.append(parser().getFooter()).append("\n");
            }
            return sb.toString();
        }

        default void printHelp() {
            System.out.println(help());
        }

        default void autoHelp() {
            if (isHelp()) {
                printHelp();
                System.exit(0);
            }
        }
    }
}
