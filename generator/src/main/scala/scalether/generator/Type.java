package scalether.generator;

public enum Type {
    SCALA("", "", "", "", "", "cats.{Functor, Monad}", "cats.implicits._"),
    MONO("Mono", "reactor.core.publisher.Mono", "scalether.java.implicits._", "MonoTransactionSender", "MonoTransactionPoller");

    private final String f;
    private final String monadType;
    private final String monadImport;
    private final String transactionSender;
    private final String transactionPoller;
    private final String[] imports;

    Type(String f, String monadType, String monadImport, String transactionSender, String transactionPoller, String... imports) {
        this.f = f;
        this.monadType = monadType;
        this.monadImport = monadImport;
        this.transactionSender = transactionSender;
        this.transactionPoller = transactionPoller;
        this.imports = imports;
    }

    public String getF() {
        return f;
    }

    public String getMonadType() {
        return monadType;
    }

    public String getMonadImport() {
        return monadImport;
    }

    public String getTransactionSender() {
        return transactionSender;
    }

    public String getTransactionPoller() {
        return transactionPoller;
    }

    public String[] getImports() {
        return imports;
    }
}
