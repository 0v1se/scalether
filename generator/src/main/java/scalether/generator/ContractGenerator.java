package scalether.generator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import scalether.generator.domain.TruffleContract;
import scalether.generator.domain.Type;
import scalether.generator.templates.ResourceTemplateLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContractGenerator {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

    public ContractGenerator() {
        configuration.setTemplateLoader(new ResourceTemplateLoader("templates"));
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
    }

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    public String generate(TruffleContract contract, String packageName, Type type) throws IOException, TemplateException {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream(); Writer writer = new OutputStreamWriter(bytes)) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("F", type.getF());
            model.put("monadType", type.getMonadType());
            model.put("monadImport", type.getMonadImport());
            model.put("transactionSender", type.getTransactionSender());
            model.put("transactionPoller", type.getTransactionPoller());
            model.put("imports", Arrays.asList(type.getImports()));
            model.put("preparedTransaction", type.getPreparedTransaction());
            model.put("truffle", contract);
            model.put("package", packageName);
            model.put("abi", escape(mapper.writeValueAsString(contract.getAbi())));
            generate("contract", model, writer);
            writer.flush();
            bytes.flush();
            return new String(bytes.toByteArray());
        }
    }

    private void generate(String template, Map<String, Object> model, Writer writer) throws IOException, TemplateException {
        configuration.getTemplate(template).process(model, writer);
    }

    private String escape(String raw) {
        return "\"" + raw.replace("\"", "\\\"") + "\"";
    }

    public static void main(String[] args) {

    }

    public static void generate(String jsonPath, String sourcePath, String packageName, Type type) {

    }

    /*

    object ContractGenerator {
  private val generator = new ContractGenerator

  def main(args: Array[String]): Unit = {
    generate(args(0), args(1), args(2), if (args.length > 3) Type.valueOf(args(3)) else Type.SCALA)
  }

  def generate(jsonPath: String, sourcePath: String, packageName: String, `type`: Type): Unit = {
    val truffle = generator.converter.fromJson[TruffleContract](Source.fromFile(jsonPath).mkString)
    val source = generator.generate(truffle, packageName, `type`)
    val resultPath = sourcePath + "/" + packageName.replace(".", "/")
    new File(resultPath).mkdirs()
    Files.write(Paths.get(resultPath + "/" + truffle.name + ".scala"), source.getBytes())
  }
}

object ContractByAbiGenerator {
  private val generator = new ContractGenerator

  def main(args: Array[String]): Unit = {
    generate(args(0), args(1), args(2), args(3), if (args.length > 4) Type.valueOf(args(4)) else Type.SCALA)
  }

  def generate(name: String, jsonPath: String, sourcePath: String, packageName: String, `type`: Type): Unit = {
    val abi = generator.converter.fromJson[List[AbiItem]](Source.fromFile(jsonPath).mkString)
    val truffle = TruffleContract(name, abi, "0x")
    val source = generator.generate(truffle, packageName, `type`)
    val resultPath = sourcePath + "/" + packageName.replace(".", "/")
    new File(resultPath).mkdirs()
    Files.write(Paths.get(resultPath + "/" + truffle.name + ".scala"), source.getBytes())
  }
}

    */
}
