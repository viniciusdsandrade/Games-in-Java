public class ControladorDeLetrasJaDigitadas implements Cloneable {

    // Atributo string chamado letras já digitadas
    private String letrasJaDigitadas;

    // Construtor da classe
    public ControladorDeLetrasJaDigitadas()

    { // Torna this,letrasJaDigitadas igual ao String vazio
        this.letrasJaDigitadas = "";
    }

    // Método que retornar um Boolean e tem parâmetro char.letra
    public boolean isJaDigitada(char letra) {
        // percorre o String this,letrasJaDigitadas e verificar se ele possui a letra
        // fornecida
        // retornar true em caso afirmativo ou false em caso negativo
        for (int i = 0; i < this.letrasJaDigitadas.length(); i++) {
            if (this.letrasJaDigitadas.charAt(i) == letra) {
                return true;
            }
        }
        return false;
    }

    // Métooo registre que vai receber um parâmetro char 'letra'
    public void registre(char letra) throws Exception {
        // verifica se a letra fornecida já foi digitada (por usar o método
        // this.IsJaDigita, para isso), lançando uma exceção
        // em caso afirmativo. concatena a letra fornecida a this.letrasJaDigitadas
        if (isJaDigitada(letra) == true)
            throw new Exception("Letra já digitada !!");

        //
        this.letrasJaDigitadas += letra + ",";
    }

    //
    public String toString() {
        // retorna um String com TODAS as letras presentes
        // em this.letrasJaDigitadas separadas por virgula (,)

        return this.letrasJaDigitadas;
    }

    public boolean equals(Object obj) {
        // verifica se this e obj possuem o mesmo endereço de memória,
        // Comparando o endereço de memória(==) contido no objeto chamante do método com
        // o endereço de memória contido no objeto que foi parâmetro do método
        // se esses endereços de memória forem iguais, provamos que quando comparamos a
        // mesma coisa, temos o mesmo resultado(endereços iguais)
        // retornando true no caso afirmativo
        // e false no caso obj seja nulo
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        // Testamos se a classe do objeto chamante do Equals for diferente da classe do
        // objeto que foi passado como parâmetro
        // retornamos false
        // ex: Se eu passase como parâmetro uma string quando this é qualquer coisa
        // diferente de String
        return true;
    }

    public int hashCode() {
        // calcular e retorna o Hashcode de this
        // int ret = super.hashCode() da classe da qual a classe herdou (caso ocorresse
        // um extends outraClasse)
        // Qualquer numero natural > 0
        int ret = 11;
        ret = 11 * ret + String.valueOf(this.letrasJaDigitadas).hashCode();

        return ret;
    }

    // Construtor de cópia ??
    public ControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas ControladorDeLetrasJaDigitadas)
            throws Exception {
        // copiar c.letrasJaDigitadas
        this.letrasJaDigitadas = ControladorDeLetrasJaDigitadas.letrasJaDigitadas;
    }

    // ?
    public Object clone() {
        // criar uma cópia do this com o construtor de cópia e retornar
        ControladorDeLetrasJaDigitadas ret = null;
        try {
            ret = new ControladorDeLetrasJaDigitadas(this);
        } catch (Exception erro) {
        }
        return ret;
    }
}
