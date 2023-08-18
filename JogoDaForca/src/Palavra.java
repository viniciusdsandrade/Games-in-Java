public class Palavra {

    //Atributo privado string denominado 'texto'
    private String texto;

    //construtor da classe palavra que vai receber uma string tbm chamada 'texto'
    public Palavra (String texto) throws Exception 
    {
        if(texto == "" || texto == null) //verifica se o texto recebido é nulo ou então vazio
            throw new Exception("Texto inválido !!"); //caso ocorra, lança uma excessão!

        this.texto = texto; //caso contrário armaza o texto recebido em this.texto
    }

    //Método getQuantidade que vai receber como parâmetro um caracter chamado 'letra'
    public int getQuantidade (char letra)
    {   
        //percorre o String this.texto, 
        //conta e retorna quantas letras existem nele iguais a letra fornecida
        int cont = 0;
        String txt = this.texto;
        for(int i = 0; i < txt.length(); i++){
            if(txt.charAt(i) == letra)
                cont++;
            //Método charAt() retorna o caractere no especificado no índice  em uma sequência.
            //O índice do primeiro caractere é 0, o segundo caractere é 1 e assim por diante. 
        }
        return cont;
    }
    //Método retornar um int e receber int i e char letra
    public int getPosicaoDaIezimaOcorrencia (int i, char letra) throws Exception
    {   //descobrir a posição 'int' da Iezinha ocorrência 'int i' do 'char letra' na string 'texto'
        //Método descobre qual a posição da iezima letra no texto fornecido 
        int aparicao = -1;
        for(int j = 0; j < this.texto.length(); j++){
            if(this.texto.charAt(j) == letra){
                aparicao++;
            }
            if(aparicao == i)
                return j;
        }
        //lançar uma excessão caso não encontre a Iézima aparição da letra fornecida
        throw new Exception("Posição não encontrada!!");
    }

    //retornar o comprimento do 'texto' 
    public int getTamanho ()
    {
        return this.texto.length();
    }
//-------------------//
//**Quando você escreve uma classe que não herda de nenhuma herança, ela herda de object
//**Nesse caso Compulsoriamente herda de object
//**oString/Equals/Hascode/getClass/wait/notify etc....
//-------------------//

    //Método retorna o atributo 'texto' em String
    // @Override--> indica sobreescrição sobre o método em questão compuslsoriamente implementado para o então atual implementado
    @Override 
    public String toString ()
    {
        return this.texto;
    }

    //Quando você escreve uma classe que não herda de nenhuma herança, ela herda de object
    //Nesse caso Compulsoriamente herda de object
    //ToString/Equals/Hascode/getClass/wait/notify etc....
    public boolean equals (Object obj)
    {   
        //verifica se this e obj possuem o mesmo endereço de memória, 
        //Comparando o endereço de memória(==) contido no objeto chamante do método com o endereço de memória contido no objeto que foi parâmetro do método
        //se esses endereços de memória forem iguais, provamos que quando comparamos a mesma coisa, temos o mesmo resultado(endereços iguais)
        //retornando true no caso afirmativo 
        //e false no caso obj seja nulo 
        if(this == obj) return true;
        if(obj == null) return false;

        //Testamos se a classe do objeto chamante do Equals for diferente da classe do objeto que foi passado como parâmetro
        //retornamos false
        //ex: Se eu passase como parâmetro uma string quando this é qualquer coisa diferente de String 
        if(this.getClass() != obj.getClass()) return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        //calcular e retorna o Hashcode de this
        //int ret = super.hashCode() da classe da qual a classe herdou (caso ocorresse um extends outraClasse)
        //Qualquer numero natural > 0
        int ret = 11; 

        ret = 11*ret + new String(this.texto).hashCode();

        if(ret < 0) ret = -ret;

        return ret;
    }

    //Vale ressaltar que o compareTo do método é da classe palavra (classe mãe)
    public int compareTo (Palavra palavra)
    {
        //This.texto e comparei com palavra.texto e o resultado dessa comparação sendo negativo, nulo ou positivo, vamos retornar
        return this.texto.compareTo(palavra.texto);
        //Já esse 'compareTo' é da classe string
    }
}