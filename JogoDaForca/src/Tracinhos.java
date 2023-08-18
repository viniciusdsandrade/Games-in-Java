import java.util.Arrays;

//essa classe 
public class Tracinhos implements Cloneable{

    private char texto []; 
    //vetor de caracteres que se chama texto
    //private char [] texto; --> maneira alternativa de declarar o vetor

    //Construtor da classe tracinho que recebe um inteiro 'qtd'
    public Tracinhos (int qtd) throws Exception
    {
        //Verifica se a qtd não é positiva. 
        //Se for o caso: lança-se uma excessão 
        if(qtd <= 0) {
            throw new Exception("Quantidade inválida !!");
        }
        else { 
            //Caso contrário, instancia um this.texto com um vetor com o tamanho igual qtd
            //faz um loop que preenche esse vetor com vetor this.texto com '_' .
            this.texto = new char[qtd];
            for (int i = 0; i < qtd; i++) {
                this.texto[i] = '_';
            }
        }
    }
    //Meétodo 'revele' recebe como parâmetro uma posição int e uma letra char
    public void revele (int posicao, char letra) throws Exception
    {   
        //verificar se a posição é negativa ou então igual ou maior que this.texto.length, 
        //caso proceda, lanca-se uma excessão.
        if(posicao < 0 || posicao >= this.texto.length){
            throw new Exception("Posição inválida !!");

        } //caso contrário, armazena a letra fornecida na posição também fornecida do vetor this.texto
          //no lugar do '_' que estva nessa posição
        for(int i = 0; i < this.texto.length; i++){
            if(posicao == i)
                this.texto[i] = letra;
        }
    }
    //Método do tipo Boolean
    public boolean isAindaComTracinhos ()
    {   //Percorrer o vetor de char this.texto e verifica se o mesmo ainda contem algum underline,
        //returnar true caso ainda reste algum underline, ou false caso contrário (só tenham letras)
        for(int i = 0; i < this.texto.length; i++){
            if(this.texto[i] == '_'){
                return true;
            }
        }
        return false;
    }

    public String toString ()
    {
        //Returna um String com todos os caracteres que há no vetor this.texto, intercalados com espaços em branco
        String ret = "";
        for(int i = 0; i < this.texto.length; i++){
            ret += " " + this.texto[i] + " ";
        }
        return ret;
    }

    public boolean equals (Object obj)
    {   
        //verifica se this e obj possuem o mesmo endereço de memória, 
        //Comparando o endereço de memória(==) contido no objeto chamante do método com o endereço de memória contido no objeto que foi parâmetro do método
        //se esses endereços de memória forem iguais, provamos que quando comparamos a mesma coisa, temos o mesmo resultado(endereços iguais)
        //retornando true no caso afirmativo 
        //e false no caso obj seja nulo 
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        //Testamos se a classe do objeto chamante do Equals for diferente da classe do objeto que foi passado como parâmetro
        //retornamos false
        //ex: Se eu passase como parâmetro uma string quando this é qualquer coisa diferente de String 

        return true;

    }

    public int hashCode ()
    {
        //calcular e retorna o Hashcode de this
        //int ret = super.hashCode() da classe da qual a classe herdou (caso ocorresse um extends outraClasse)
        //Qualquer numero natural > 0
        int ret = 11;
        for(int i = 0; i < this.texto.length; i++){
            ret = 11*ret + Arrays.hashCode(this.texto);
        }
        return ret;
    }

    //Construtor de cópia
    public Tracinhos (Tracinhos t) throws Exception //construtor de cópia
    {
        //Instanciar this.texto um vetor com o mesmo tamanho de t.texto
        //e compilar o conteúdo de t.texto para this.texto
        this.texto = t.texto;
    }

    //Método Clone
    public Object clone ()
    {
        //retorna uma cópia de this
        Tracinhos ret = null;
        try{
            ret = new Tracinhos(this);
        }
        catch(Exception erro){}

        return ret;
    }
}