package jaskell.argsparser;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ArgParserTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testSuccess() throws Exception {
        var parser = ArgParser.create()
                .option(Option.create("source").help("source file"))
                .synonyms("-s", "--source")
                .option(Option.create("output").help("output file"))
                .synonyms("-o", "--output");
        String[] args = new String[]{"-s", "ArgParser.java", "--output", "completion.json"};
        var result = parser.parse(args);
        assertTrue(result.isOk());
        var p = result.get();
        assertEquals("completion.json", p.option("output").get().getFirst());
        assertEquals("ArgParser.java", p.option("source").get().getFirst());
    }

    @Test
    public void testFailed() {
        var parser = ArgParser.create()
                .option(Option.create("source").help("source file"))
                .synonyms("-s", "--source")
                .option(Option.create("output").help("output file").required(true))
                .synonyms("-o", "--output");

        String[] args = new String[]{"-s", "ArgParser.java"};
        var result = parser.parse(args);
        assertTrue(result.isErr());
        assertThrowsExactly(ErrorsStack.class, result::get);
        logger.info(() -> result.error().getMessage());
    }

    @Test
    public void testMultiOption() throws Exception {
        var source = Option.create("source")
                .help("source project directory")
                .required(true);
        var target = Option.create("target")
                .help("where save lora train dataset")
                .required(true);

        var argParser = ArgParser.create()
                .option(source)
                .option(target);

        String[] args = new String[]{
                "--source", "sample1.java",
                "--source", "sample2.java",
                "--target", "output.json"};

        var result = argParser.parse(args);
        assertTrue(result.isOk());
        var parsed = result.get();
        assertTrue(parsed.hasOption("source"));
        assertTrue(parsed.hasOption("target"));
        assertTrue(parsed.option("source").get().contains("sample1.java"));
        assertTrue(parsed.option("source").get().contains("sample2.java"));
        assertTrue(parsed.option("target").get().contains("output.json"));
    }
}
