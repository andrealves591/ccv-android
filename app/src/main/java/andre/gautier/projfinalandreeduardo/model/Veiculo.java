package andre.gautier.projfinalandreeduardo.model;

import java.util.ArrayList;

public class Veiculo {

    private String keyVeiculo;
    private String nome;
    private String marca;
    private int ano;
    private int modelo;
    private String combustivel;
    private double valorCusto;
    private String cor;
    private String portas;
    private ArrayList<String> complementos;

    public Veiculo() {
    }

    public Veiculo(String keyVeiculo, String nome, String marca, int ano, int modelo, String combustivel, double valorCusto, String cor, String portas, ArrayList<String> complementos) {
        this.keyVeiculo = keyVeiculo;
        this.nome = nome;
        this.marca = marca;
        this.ano = ano;
        this.modelo = modelo;
        this.combustivel = combustivel;
        this.valorCusto = valorCusto;
        this.cor = cor;
        this.portas = portas;
        this.complementos = complementos;
    }

    public String getKeyVeiculo() {
        return keyVeiculo;
    }

    public void setKeyVeiculo(String keyVeiculo) {
        this.keyVeiculo = keyVeiculo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public double getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(double valorCusto) {
        this.valorCusto = valorCusto;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPortas() {
        return portas;
    }

    public void setPortas(String portas) {
        this.portas = portas;
    }

    public ArrayList<String> getComplementos() {
        return complementos;
    }

    public void setComplementos(ArrayList<String> complementos) {
        this.complementos = complementos;
    }

    @Override
    public String toString() {
        return "Dados do Veículo:\n\n"
                + "KEY: " + keyVeiculo + "\n"
                + "Marca: " + marca + "\n"
                + "Nome: " + nome + "\n"
                + "Ano: " + ano + "\n"
                + "Modelo: " + modelo + "\n"
                + "Combustível: " + combustivel + "\n"
                + "Cor: " + cor + "\n"
                + "Portas: " + portas + "\n"
                + "Valor Custo: R$ " + valorCusto + "\n"
                + "Complementos: " + complementos;
    }
}
