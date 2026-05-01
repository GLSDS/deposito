import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level; 
import java.util.logging.Logger; 

public class app {

    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in); 
        while(true){ 
            System.out.println("╔════════════════════════════╗");
            System.out.println("║      SELECIONE UMA OPÇÃO   ║");
            System.out.println("╠════════════════════════════╣");
            System.out.println("║  1 - Produtos              ║");
            System.out.println("║  2 - Vendas                ║");
            System.out.println("║  3 - Sair                  ║");
            System.out.println("╚════════════════════════════╝");

            int opcao = sc.nextInt(); 
            switch(opcao){ 
                case 1 -> produtos(); 
                case 2 -> mostrarTodosProdutos(); 
                default -> System.out.println("Opção inválida"); 
            } 
        } 
    }

    private static void mostrarTodosProdutos() {
        Scanner sc = new Scanner(System.in); 
        String nomeFicheiro = "deposito.txt"; 

        String nomeProduto[] = new String[getNumeroLinhasFicheiro(nomeFicheiro)]; 
        String quantidade[] = new String[getNumeroLinhasFicheiro(nomeFicheiro)]; 
        String qualidade[] = new String[getNumeroLinhasFicheiro(nomeFicheiro)]; 
        String preco[] = new String[getNumeroLinhasFicheiro(nomeFicheiro)]; 

        leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, preco); 

        for (int i = 0; i < nomeProduto.length; i++) { 
            System.out.println("nome do produto: " + nomeProduto[i]); 
            System.out.println("Quantidade: " + quantidade[i]); 
            System.out.println("Qualidade: " + qualidade[i]); 
            System.out.println("Preço: " + preco[i] + "\n"); 
        } 
    } 

    private static void extraiDadosCsv(String[] nomeProduto, 
            String[] quantidade, 
            String[] qualidade, 
            String[] preco, 
            int numeroLinha, 
            String linha) { 
        int posicaoUltimaVirgula = -1; 
        int numeroVirgulas = 0; 
  
        for (int i = 0; i < linha.length(); i++) { 
            if (linha.charAt(i) == ',') { 
                System.out.println(linha.substring(posicaoUltimaVirgula + 1, i)); 
                switch (numeroVirgulas) { 
                    case 0 -> 
                        nomeProduto[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i); 
                    case 1 -> 
                        quantidade[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i); 
                    case 2 -> { 
                        qualidade[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i); 
                        preco[numeroLinha] = linha.substring(i + 1); 
                    } 
                } 
                posicaoUltimaVirgula = i; 
                numeroVirgulas++; 
            } 
        } 
    } 
  
    private static void escreveNoFicheiro(String texto, String nomeFicheiro) { 
  
        FileWriter fileWriter = null; 
        try { 
            fileWriter = new FileWriter(new File(nomeFicheiro)); 
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); 
            bufferedWriter.write(texto); 
            bufferedWriter.close(); 
            fileWriter.close(); 
        } catch (IOException ex) { 
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, null, ex); 
        } 
    } 
  
    private static String leFicheiro(String nomeFicheiro) { 
  
        FileReader fileReader = null; 
        String textoLido = ""; 
        try { 
            fileReader = new FileReader(new File(nomeFicheiro)); 
            BufferedReader bufferedReader = new BufferedReader(fileReader); 
            String linha = ""; 
            while ((linha = bufferedReader.readLine()) != null) { 
                textoLido += linha + "\n"; 
            } 

            bufferedReader.close(); 
            fileReader.close(); 
        } catch (FileNotFoundException ex) { 
            escreveNoFicheiro("", nomeFicheiro);
        } catch (IOException ex) { 
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, null, ex); 
        } 
        return textoLido; 
    } 

    private static String leExtraiCsvFicheiro(String nomeFicheiro, 
            String[] nomeProduto, 
            String[] quantidade, 
            String[] qualidade, 
            String[] preco) { 

        FileReader fileReader = null; 
        String textoLido = ""; 
        try { 
            fileReader = new FileReader(new File(nomeFicheiro)); 
            BufferedReader bufferedReader = new BufferedReader(fileReader); 
            String linha = ""; 
            int numeroLinha = 0; 
            while ((linha = bufferedReader.readLine()) != null) { 
                extraiDadosCsv(nomeProduto, quantidade, qualidade, preco, numeroLinha, linha); 
                numeroLinha++; 
            } 
            bufferedReader.close(); 
            fileReader.close(); 
        } catch (FileNotFoundException ex) { 
            escreveNoFicheiro("", nomeFicheiro); 
        } catch (IOException ex) { 
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, null, ex); 
        } 
        return textoLido; 
    } 

    private static int getNumeroLinhasFicheiro(String nomeFicheiro) { 

        FileReader fileReader = null; 
        int numeroLinhas = 0; 

        try { 

            fileReader = new FileReader(new File(nomeFicheiro)); 
            BufferedReader bufferedReader = new BufferedReader(fileReader); 
            String linha = ""; 
            while ((linha = bufferedReader.readLine()) != null) { 
                numeroLinhas++; 
            } 
            bufferedReader.close(); 
            fileReader.close(); 
        } catch (FileNotFoundException ex) { 
            escreveNoFicheiro("", nomeFicheiro); 
        } catch (IOException ex) { 
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, null, ex); 
        } 
        return numeroLinhas; 
    
    } 

        private static void produtos() {

                Scanner sc = new Scanner(System.in);
                System.out.println("╔════════════════════════════╗/n "
                                  +"║      SELECIONE UMA OPÇÃO   ║/n "
                                  +"╠════════════════════════════╣/n "
                                  +"║  1 - Adicionar produto     ║/n"
                                  +"║  2 - Mostrar produtos      ║/n"
                                  +"║  3 - Voltar                ║/n"
                                  +"╚════════════════════════════╝");
                int opcaoProdutos = sc.nextInt();
                switch(opcaoProdutos){
                    case 1 -> adicionarNovoproduto();
                    case 2 -> mostrarTodosProdutos();
                    case 3 -> break;
                    default -> System.out.println("Opção inválida");
                }
                if(opcaoProdutos == 3){
                    main(null);
                }
        }

    private static void adicionaTextoAoFicheiro(String deposito, String nomeFicheiro) { 
        String textoAntigo = leFicheiro(nomeFicheiro); 
        String textoNovo = textoAntigo + deposito; 
        escreveNoFicheiro(textoNovo, nomeFicheiro); 

    } 

    private static void adicionarNovoproduto() { 

        Scanner sc = new Scanner(System.in); 

  

        System.out.println("Introduza o nome do produto"); 
        String nomeProduto = sc.nextLine(); 
        System.out.println("Introduza o quantidade"); 
        String quantidade = sc.nextLine(); 
        System.out.println("Introduza a Qualidade"); 
        String qualidade = sc.nextLine(); 
        System.out.println("Introduza o Preço"); 
        String preco = sc.nextLine(); 

        String deposito = nomeProduto + (",") + quantidade + (",") + qualidade + (",") + preco; 

        adicionaTextoAoFicheiro(deposito, "deposito.txt"); 

    } 

} 

 