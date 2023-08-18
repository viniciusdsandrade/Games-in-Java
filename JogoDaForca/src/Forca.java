public class Forca {
    public static void main (String args [])
    {
        char continuar = ' '; 
        //Variável continuar 
        //usada para determina se o jogo vai se repetir?
        //ou vai ser finalizado

        do 
        //Permite o usuário realizar inúmeras partidas enquanto 'continuar == s'
        //caso continuar seja 'N' saimos desse loop
        {
            //A partir daqui está iniciada os comandos resposável pela realização de UMA partida

            Palavra palavra = BancoDePalavras.getPalavraSorteada(); 
            //Declara objeto da classe palavra onde guardamos uma palavra sorteada do banco de palavras
            //getPalavrasSorteas é um método static pois a classe chamante é 'BandoDePalavras'
            Tracinhos tracinhos = null;
            try
            {
                //tracinho representa as letras da palavras ocultadas até serem descobertas pelo usuário
                tracinhos = new Tracinhos(palavra.getTamanho());
                //Não é um método static pois é chamado apartir de um objeto
                //getTamanho método responsável por obter o tamanho da palavras digitada e colocar traços
            }
            catch (Exception erro)
            {}
            //Declaramos e instanciamos um objeto da classe "controladorDeLetrasJaDigitadas"
            //O Objetivo desse objeto é controlar quais foram as letras ja digitadas
            ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas ();

            
            ControladorDeErros controladorDeErros = null;
            try
            {
                //Declaramos e instanciamos um objeto da classe "controLadorDeErros e passamos para o contrutor do controlador de erro um número inteiro"
                //esse numero inteiro vai ser 0,6x o tamanho da palavra
                //Essa classe diz que o usuário pode errar no máximo um numero de letras que representa no máximo 60% da palavra sorteada
                //Como eventualmente 60% pode representar uma fração de uma letra, o método arredonda para o inteiro mais próximo
                //Caso a multiplicação por 0,6 seja decimal(não inteira)
                controladorDeErros = new ControladorDeErros ((int)(palavra.getTamanho()*0.6));
            }
            catch (Exception erro)
            {}
            //Enquanto não houver atingindo o número de erros máximo e ainda ter tracinhos na palavra
            //O jogo continua!
            while (tracinhos.isAindaComTracinhos() && !controladorDeErros.isAtingidoMaximoDeErros())
            {   //*******************Printamos o seguite MENU************************************************************
                //isAindaComTracinho método da classe tracinho responsável por verificar se ainda tem tracinhos
                //isAtingidoMaximoDeErros método da classe tracinho responsável por verificar se o número de erros máximo foi atingido
                //*******************Printamos o seguite MENU************************************************************
                System.out.println ("Palavra...: "+tracinhos); //tracinho do tamanho da palavra
                System.out.println ("Digitadas.: "+controladorDeLetrasJaDigitadas); //método que exibe todas as letras digitadas
                System.out.println ("Erros.....: "+controladorDeErros); //"ControladorDeErros" Exibe o número de erros do usuário e mostra a quantidade máxima de erros permitidos
                
                try
                {
                    System.out.print   ("Qual letra? "); //Printa para o usuário qual letra ele deseja colocar na mesma linha(não tem LN)
                    char letra = Character.toUpperCase (Teclado.getUmChar()); //Pegamos a letra digitada pelo teclado, colocamos em caixa alta e colocamos na variável 'letra'

                    if (controladorDeLetrasJaDigitadas.isJaDigitada (letra)) //Se a letra digitada pelo usuário tiver sido digitada anteiormente é printado a mensagem a seguir
                        System.err.println ("Essa letra ja foi digitada!\n");
                    else 
                    {   // Caso a letra digitada pelo usuário seja inédita:
                        // método registre, registra a letra no ' controladorDeLetrasJaDigitadas'
                        controladorDeLetrasJaDigitadas.registre (letra);

                        // O método getQuantidade vai descobrir QUANTAS letras iguais as digitadas tem na palavras 
                        //Guardamos na variável qtd do tipo inteira
                        int qtd = palavra.getQuantidade (letra);

                       //Se a letra digitada não estiver na palavra sorteada, o print abaixo ocorrerá
                        if (qtd==0)
                        { 
                            //print de alerta de erro!
                            System.err.println ("A palavra nao tem essa letra!\n");
                            controladorDeErros.registreUmErro (); //registro do erro
                        }
                        else
                        {   //Caso contrário, realizamos um 'for' que vai de zero até a quantidade de vezes da ocorrência da letra digitada pelo usuário na palavra sorteada
                            //
                            for (int i=0; i<qtd; i++)
                            {
                                //o método "getPosicaoDaIezimaOcorrencia" é responsável por colocar as letras digitadas 
                                //que ocorrem na palavra sorteada no lugar correto delas
                                int posicao = palavra.getPosicaoDaIezimaOcorrencia (i,letra);
                                tracinhos.revele (posicao, letra); 
                                // o método revele, da classe tracinho, revela a posição e letra descobertas
                            }
                            System.out.println ();
                        }
                    }
                }
                catch (Exception erro)
                {
                    System.err.println (erro.getMessage());
                }
            }
            
            //Caso o while tenha parado, temos as seguintes possibilidades:

            //1 - Caso o usuároio tenha atingido o máximo de erros será printado uma mensagem de erro!
            //2 - caso contrário o usuário tenha acertado todas as letras e portando não tenha mais tracinhos a serem descobertos!
            if (controladorDeErros.isAtingidoMaximoDeErros ()) 
                // Esse if procede caso ocorra a opção 1)
                System.out.println ("Que pena! Voce perdeu! A palavra era "+palavra+"\n");
            else // !tracinhos.isAindaComTracinhos()
                System.out.println ("Parabens! Voce ganhou! A palavra era mesmo "+palavra+"\n"); 
                //esse Else ocorre caso proceda a opção 2)

            for(;;) //'For' responsável pelo loop que questiona o usuário se ele deseja repetir a rodada ou sair do jogo!
            {
                try 
                {   //try para testar se o usuário vai digitar um char
                    System.out.print ("Deseja jogar de novo (S/N)? "); // Pergunta para o usuário se ele deseja jogar novamente e delimita 2 opções
                    continuar = Character.toUpperCase (Teclado.getUmChar()); // Variável 'continuar' responsável por ler o que o usuário digitar com método Uppercase
                    if (continuar!='S' && continuar!='N') //Caso se o usuário não digitar S ou N, printa mensagem de erro e reinicia o try
                        System.err.println ("Opcao invalida! Tente novamente...");
                    else
                        break; // Quando a opção digitada for correta o break garante a saída do loop
                }
                catch (Exception erro)
                {
                    System.err.println ("Opcao invalida! Tente novamente...");
                    //Se o usuário não um char como orienta o comando, o programa lança excessão
                    //E o programa reinicia o try, pedindo para o 
                }
            }
        }
        while (continuar=='S');
        //Se o usuário digiar "S" o jogo reinicia
        //Caso contrário, fim da aplicação!
    }
}