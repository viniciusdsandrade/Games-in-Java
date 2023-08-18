public class ControladorDeErros implements Cloneable
// tem método clone e método construtor de cópia,
// além de metodo não estático,
// além dos construtores que alteram algum atributo do this
{

    // atributos do tipo inteiro
    private int qtdMax, qtdErr = 0;
    // qtdMax = quantidade máxima de erros permitadas durante a partida
    // quantidade de erros cometido

    // Contrutor vai receber um valor para qtdMax fornecido não é positivo
    public ControladorDeErros(int qtdMax) throws Exception {
        // Verifica se qtdMax fornecida não é positiva, lançando uma excessão
        if (qtdMax < 0)
            throw new Exception("Quantidade inválida !!");
        // armazena qtdMax fornecida no atributo this.qtdMax
        this.qtdMax = qtdMax;
    }

    public void registreUmErro() throws Exception {
        // verifica se this.qtdErr ja é igual a this.qtdMax,
        // Lançando excesão em caso positivo ou
        // incrementando this.qtdErr em caso negativo
        if (this.qtdMax == this.qtdErr)
            throw new Exception("Quantidade máxima de erros atingida !!");
        this.qtdErr++;
    }

    public boolean isAtingidoMaximoDeErros() {
        // retorne true se this.qtdErr for igual a this.qtdMax,
        // ou então false, caso contrário.
        if (this.qtdMax == this.qtdErr)
            return true;
        return false;
    }

    public String toString() {
        // Retorna uma String com a quantidade de erros cometidas / quantidade de erros
        // máxima permitidas
        return this.qtdErr + "/" + this.qtdMax;
    }

    public boolean equals(Object obj) {
        // verificar se this e obj possuem o mesmo conteúdo,
        // retornando true no caso afirmativo ou false no caso negativo

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        return true;
    }

    public int hashCode() { // ***************************************************************************************************************************************//
                            // Já um hashcode(código hash) é um valor inteiro associado com todos os objetos
                            // em Java.
                            // Então, para obter esse hashcode precisamos utilizar o método hashCode(), esse
                            // método retornará um inteiro para o objeto passado.
                            // Sendo assim, se dois objetos possuem o mesmo código hash, provavelmente são
                            // iguais ou no mínimo parecidos(a depender da implementação).
                            // *****************************************************************************************************************************************//

        // calcular e retorna o Hashcode de this
        // int ret = super.hashCode() da classe da qual a classe herdou (caso ocorresse
        // um extends outraClasse)
        // Qualquer numero natural > 0
        String str = this.qtdErr + "/" + this.qtdMax;
        int ret = 11;
        ret = 11 * ret + Integer.parseInt(str);
        return ret;
    }

    public ControladorDeErros(ControladorDeErros c) throws Exception {
        this.qtdMax = c.qtdMax;
        this.qtdErr = c.qtdErr;
    }

    public Object clone() {
        ControladorDeErros ret = null;
        try {
            ret = this;
        } catch (Exception erro) {
        }
        return ret;
    }
}