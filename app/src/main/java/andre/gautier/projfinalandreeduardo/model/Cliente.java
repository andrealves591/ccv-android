package andre.gautier.projfinalandreeduardo.model;


public class Cliente {

    private String keyCliente;
    private String nome;
    private long cpf;
    private String sexo;
    private int idade;
    private Endereco endereco;


    public Cliente() {
    }

    public Cliente(String keyCliente, String nome, long cpf, String sexo, int idade, Endereco endereco) {
        this.keyCliente = keyCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.idade = idade;
        this.endereco = endereco;
    }

    public String getKeyCliente() {
        return keyCliente;
    }

    public void setKeyCliente(String keyCliente) {
        this.keyCliente = keyCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Dados do Cliente: " +
                "\nKEY: " + keyCliente +
                "\nNome: " + nome +
                "\nCPF: " + cpf +
                "\nSexo: " + sexo +
                "\nIdade: " + idade +
                "\nEndereço:\n" + endereco;
    }
}
