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

public static void main(String[] args) {                            // MPrograma pricipal (menu principal; [onde o usuario interage com o programa e escolhe as opções para aceder às funcionalidades do programa])
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
                case 1 -> {
                    boolean sair = produtos();
                    if(sair){
                        System.out.println("A sair...");
                        return;
                    }
                } 
                case 2 -> mostrarTodosProdutos(); 
                case 3 -> {
                    System.out.println("A sair...");
                    return;
                }
                default -> System.out.println("Opção inválida"); 
            } 
        } 
    }

   

    
  
    private static void escreveNoFicheiro(String texto, String nomeFicheiro) {                  //subprograma que escreve o texto no ficheiro, para ser usado na função de adicionar texto ao ficheiro
  
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
  
    private static String leFicheiro(String nomeFicheiro) {                     //subprograma que lê o ficheiro e retorna o conteúdo como string, para ser usado na função de adicionar texto ao ficheiro, para não apagar o conteúdo antigo do ficheiro quando for adicionado um novo produto
  
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

    private static String leExtraiCsvFicheiro(String nomeFicheiro,                  //subprograma que lê o ficheiro csv e extrai os dados para arrays separados para cada campo
            String[] nomeProduto, 
            String[] quantidade, 
            String[] qualidade, 
            String[] precoF) { 

        FileReader fileReader = null; 
        String textoLido = ""; 
        try { 
            fileReader = new FileReader(new File(nomeFicheiro)); 
            BufferedReader bufferedReader = new BufferedReader(fileReader); 
            String linha = ""; 
            int numeroLinha = 0; 
            while ((linha = bufferedReader.readLine()) != null) { 
                extraiDadosCsv(nomeProduto, quantidade, qualidade, precoF, numeroLinha, linha); 
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

    private static int getNumeroLinhasFicheiro(String nomeFicheiro) {               //Mostra o número de linhas do ficheiro, para ser usado na criação dos arrays com o tamanho correto para armazenar os dados dos produtos

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

     /*****************************Subprogramas dos produtos*****************************/
    private static void extraiDadosCsv(String[] nomeProduto,                        //subprograma que extrai os dados do ficheiro csv e armazena em arrays separados para cada campo
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
    private static void editarProduto() {
    Scanner sc = new Scanner(System.in);
    String nomeFicheiro = "deposito.txt";
    
    // Carregar dados existentes
    int numLinhas = getNumeroLinhasFicheiro(nomeFicheiro);
    
    if (numLinhas == 0) {
        System.out.println("Não existem produtos para editar!");
        return;
    }
    
    String[] nomeProduto = new String[numLinhas];
    String[] quantidade = new String[numLinhas];
    String[] qualidade = new String[numLinhas];
    String[] preco = new String[numLinhas];
    
    leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, preco);
    
    // Mostrar produtos com índice
    System.out.println("\n╔════════════════════════════════════╗");
    System.out.println("║       PRODUTOS DISPONÍVEIS         ║");
    System.out.println("╠════════════════════════════════════╣");
    for (int i = 0; i < nomeProduto.length; i++) {
        System.out.printf("║ %d - %-30s ║\n", i + 1, nomeProduto[i]);
        System.out.printf("║     Quantidade: %-20s ║\n", quantidade[i]);
        System.out.printf("║     Qualidade: %-21s ║\n", qualidade[i]);
        System.out.printf("║     Preço: R$%-23s ║\n", preco[i]);
        System.out.println("║────────────────────────────────║");
    }
    System.out.println("╚════════════════════════════════════╝");
    
    // Selecionar produto para editar
    System.out.print("Selecione o número do produto que deseja editar: ");
    int indice = sc.nextInt();
    sc.nextLine(); // Limpar buffer
    
    if (indice < 1 || indice > nomeProduto.length) {
        System.out.println("Índice inválido!");
        return;
    }
    
    int indiceArray = indice - 1;
    
    // Mostrar dados atuais
    System.out.println("\n╔════════════════════════════════════╗");
    System.out.println("║        EDITANDO PRODUTO            ║");
    System.out.println("╠════════════════════════════════════╣");
    System.out.println("║ Dados atuais:                      ║");
    System.out.println("║ Nome: " + nomeProduto[indiceArray]);
    System.out.println("║ Quantidade: " + quantidade[indiceArray]);
    System.out.println("║ Qualidade: " + qualidade[indiceArray]);
    System.out.println("║ Preço: " + preco[indiceArray]);
    System.out.println("╚════════════════════════════════════╝");
    
    // Perguntar o que editar
    System.out.println("\nO que deseja editar?");
    System.out.println("1 - Nome");
    System.out.println("2 - Quantidade");
    System.out.println("3 - Qualidade");
    System.out.println("4 - Preço");
    System.out.println("5 - Editar tudo");
    System.out.print("Opção: ");
    int opcaoEdicao = sc.nextInt();
    sc.nextLine();
    
    // Aplicar edições
    switch(opcaoEdicao) {
        case 1:
            System.out.print("Novo nome: ");
            nomeProduto[indiceArray] = sc.nextLine();
            break;
        case 2:
            System.out.print("Nova quantidade: ");
            quantidade[indiceArray] = sc.nextLine();
            break;
        case 3:
            System.out.print("Nova qualidade: ");
            qualidade[indiceArray] = sc.nextLine();
            break;
        case 4:
            System.out.print("Novo preço: ");
            preco[indiceArray] = sc.nextLine();
            break;
        case 5:
            System.out.print("Novo nome: ");
            nomeProduto[indiceArray] = sc.nextLine();
            System.out.print("Nova quantidade: ");
            quantidade[indiceArray] = sc.nextLine();
            System.out.print("Nova qualidade: ");
            qualidade[indiceArray] = sc.nextLine();
            System.out.print("Novo preço: ");
            preco[indiceArray] = sc.nextLine();
            break;
        default:
            System.out.println("Opção inválida!");
            return;
    }
    
    // Reescrever o arquivo com os dados atualizados
    String novoConteudo = "";
    for (int i = 0; i < nomeProduto.length; i++) {
        novoConteudo += nomeProduto[i] + "," + quantidade[i] + "," + qualidade[i] + "," + preco[i];
        if (i < nomeProduto.length - 1) {
            novoConteudo += "\n";
        }
    }
    
    escreveNoFicheiro(novoConteudo, nomeFicheiro);
    System.out.println("\n✅ Produto editado com sucesso!");
}
private static void adicionarMaisProdutos() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Quantos produtos deseja adicionar? ");
    int quantidade = sc.nextInt();
    sc.nextLine();
    
    for (int i = 0; i < quantidade; i++) {
        System.out.println("\n--- Produto " + (i + 1) + " ---");
        System.out.print("Nome do produto: ");
        String nomeProduto = sc.nextLine();
        System.out.print("Quantidade: ");
        String quantidadeProd = sc.nextLine();
        System.out.print("Qualidade: ");
        String qualidade = sc.nextLine();
        System.out.print("Preço: ");
        String preco = sc.nextLine();
        
        String deposito = nomeProduto + "," + quantidadeProd + "," + qualidade + "," + preco;
        adicionaTextoAoFicheiro(deposito + "\n", "deposito.txt");
    }
    System.out.println(quantidade + " produto(s) adicionado(s) com sucesso!");
}
     /*********************************************************************************/
private static boolean produtos(){

                Scanner sc = new Scanner(System.in);
                System.out.println("╔════════════════════════════╗ \n"                  //Tabela das opções dos produtos
                                  +"║      SELECIONE UMA OPÇÃO   ║ \n"
                                  +"╠════════════════════════════╣ \n"
                                  +"║ 1 - Adicionar produto      ║ \n"
                                  +"║ 2 - Mostrar produtos       ║ \n"
                                  +"║ 3 - Adicionar mais produtos║ \n"
                                  +"║ 4 - Editar produto         ║ \n"
                                  +"║ 5 - Sair                   ║ \n"
                                  +"╚════════════════════════════╝ ");
                int opcaoProdutos = sc.nextInt();
                switch(opcaoProdutos){
                    case 1 -> adicionarNovoproduto();
                    case 2 -> mostrarTodosProdutos();
                    case 3 -> adicionarMaisProdutos();
                    case 4 -> editarProduto();
                    case 5 -> {   
                        return true;
                    }
                    default -> System.out.println("Opção inválida");
                }
                return false;
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

        int precoNum = Integer.parseInt(preco) * Integer.parseInt(quantidade);              //calcula o preço total do produto multiplicando o preço unitário pela quantidade
        String precoF = Integer.toString(precoNum);                                         //converte o preço total de volta para string para ser armazenado no ficheiro
        System.out.println("Valor final do produto: " + precoNum);
        System.out.println("Produto adicionado com sucesso!");

        String deposito = nomeProduto + (",") + quantidade + (",") + qualidade + (",") + precoF + "\n"; 

        adicionaTextoAoFicheiro(deposito, "deposito.txt"); 

    } 

} 