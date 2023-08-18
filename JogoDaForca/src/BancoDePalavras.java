 public class BancoDePalavras {
        //Declaar um atributo privativo , statico, vetor de String que se chama palavras 
        //contendo todas as palavras do banco de Palabras 
        private static String[] palavras =
                {
                        "JAVA",
                        "CLASSE",
                        "OBJETO",
                        "INSTANCIA",
                        "PUBLICO",
                        "PRIVATIVO",
                        "METODO",
                        "CONSTRUTOR",
                        "SETTER",
                        "GETTER",
                        "LUZ",
                        "PRAZER"
                };

        //o Método static getPlavraSorteda vai retornar uma palavra
        public static Palavra getPalavraSorteada ()
        {

            //Declaramos um objeto chamado palavra da classe Palavra 
            Palavra palavra = null;
    
            try
            {   //Instanciamos o objeto com uma nova palavra retirada do banco de palavras
                //O numero aleatório entre 0 e o tamanho do vetor de palavras menos 1 que será sorteado
                //Uma vez encontrada a palavra aleatória, instaciamos o objeto paravra com a mesma
                palavra = new Palavra (BancoDePalavras.palavras[(int)(Math.random() * BancoDePalavras.palavras.length)]);
            }
            catch (Exception e)
            {}
            
            //Retornar objeto da palavra da classe Palavra instanciada acima
            return palavra;
        }
}    