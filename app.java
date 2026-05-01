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
    
    // Definindo as cores
    public static enum Color {
        RESET("\u001b[0m"),
        BLACK("\u001b[30m"),
        RED("\u001b[31m"),
        GREEN("\u001b[32m"),
        YELLOW("\u001b[33m"),
        BLUE("\u001b[34m"),
        PURPLE("\u001b[35m"),
        CYAN("\u001b[36m"),
        WHITE("\u001b[37m"),
        
        // Cores brilhantes (bold)
        BRIGHT_RED("\u001b[91m"),
        BRIGHT_GREEN("\u001b[92m"),
        BRIGHT_YELLOW("\u001b[93m"),
        BRIGHT_BLUE("\u001b[94m"),
        BRIGHT_CYAN("\u001b[96m");

        private final String code;

        private Color(String code) {
            this.code = code;
        }
        
        @Override
        public String toString() {
            return code;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while(true) {
            // Menu principal com cores
            System.out.println(Color.BRIGHT_CYAN + "╔════════════════════════════╗" + Color.RESET);
            System.out.println(Color.BRIGHT_YELLOW + "║      SELECIONE UMA OPÇÃO   ║" + Color.RESET);
            System.out.println(Color.BRIGHT_CYAN + "╠════════════════════════════╣" + Color.RESET);
            System.out.println(Color.GREEN + "║  1 - Produtos              ║" + Color.RESET);
            System.out.println(Color.BLUE + "║  2 - Vendas                ║" + Color.RESET);
            System.out.println(Color.RED + "║  3 - Sair                  ║" + Color.RESET);
            System.out.println(Color.BRIGHT_CYAN + "╚════════════════════════════╝" + Color.RESET);
            System.out.print(Color.YELLOW + "Opção: " + Color.RESET);
            
            int opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    boolean sair = produtos();
                    if (sair) {
                        System.out.println(Color.BRIGHT_RED + "A sair..." + Color.RESET);
                        return;
                    }
                    break;
                case 2:
                    vendas();
                    break;
                case 3:
                    System.out.println(Color.BRIGHT_RED + "A sair..." + Color.RESET);
                    return;
                default:
                    System.out.println(Color.RED + "Opção inválida!" + Color.RESET);
            }
        }
    }
    
    private static void vendas() {
        Scanner sc = new Scanner(System.in);
        System.out.println(Color.BRIGHT_CYAN + "\n╔════════════════════════════╗" + Color.RESET);
        System.out.println(Color.BRIGHT_YELLOW + "║        MENU VENDAS         ║" + Color.RESET);
        System.out.println(Color.BRIGHT_CYAN + "╠════════════════════════════╣" + Color.RESET);
        System.out.println(Color.GREEN + "║  1 - Buscar produto        ║" + Color.RESET);
        System.out.println(Color.GREEN + "║  2 - Registrar venda       ║" + Color.RESET);
        System.out.println(Color.RED + "║  3 - Voltar                ║" + Color.RESET);
        System.out.println(Color.BRIGHT_CYAN + "╚════════════════════════════╝" + Color.RESET);
        
        System.out.print(Color.YELLOW + "Opção: " + Color.RESET);
        int opcao = sc.nextInt();
        
        switch (opcao) {
            case 1:
                buscarProdutoPorNome();
                break;
            case 2:
                System.out.print("Nome do produto para vender: ");
                sc.nextLine(); // limpar buffer
                String nome = sc.nextLine();
                System.out.print("Quantidade: ");
                int qtd = sc.nextInt();
                atualizarEstoque(nome, qtd);
                break;
            case 3:
                return;
            default:
                System.out.println(Color.RED + "Opção inválida!" + Color.RESET);
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
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
    }
    
    private static String leFicheiro(String nomeFicheiro) {
        FileReader fileReader = null;
        String textoLido = "";
        
        try {
            fileReader = new FileReader(new File(nomeFicheiro));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String linha;
            for(linha = ""; (linha = bufferedReader.readLine()) != null; textoLido = textoLido + linha + "\n") {
            }
            
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException var5) {
            escreveNoFicheiro("", nomeFicheiro);
        } catch (IOException ex) {
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
        
        return textoLido;
    }
    
    private static String leExtraiCsvFicheiro(String nomeFicheiro, String[] nomeProduto, String[] quantidade, String[] qualidade, String[] precoF) {
        FileReader fileReader = null;
        String textoLido = "";
        
        try {
            fileReader = new FileReader(new File(nomeFicheiro));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
            
            for(int numeroLinha = 0; (linha = bufferedReader.readLine()) != null; ++numeroLinha) {
                extraiDadosCsv(nomeProduto, quantidade, qualidade, precoF, numeroLinha, linha);
            }
            
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException var10) {
            escreveNoFicheiro("", nomeFicheiro);
        } catch (IOException ex) {
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
        
        return textoLido;
    }
    
    private static int getNumeroLinhasFicheiro(String nomeFicheiro) {
        FileReader fileReader = null;
        int numeroLinhas = 0;
        
        try {
            fileReader = new FileReader(new File(nomeFicheiro));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            for(String linha = ""; bufferedReader.readLine() != null; ++numeroLinhas) {
            }
            
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException var5) {
            escreveNoFicheiro("", nomeFicheiro);
        } catch (IOException ex) {
            Logger.getLogger(app.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
        
        return numeroLinhas;
    }
    
    private static void extraiDadosCsv(String[] nomeProduto, String[] quantidade, String[] qualidade, String[] preco, int numeroLinha, String linha) {
        int posicaoUltimaVirgula = -1;
        int numeroVirgulas = 0;
        
        for(int i = 0; i < linha.length(); ++i) {
            if (linha.charAt(i) == ',') {
                switch (numeroVirgulas) {
                    case 0:
                        nomeProduto[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i);
                        break;
                    case 1:
                        quantidade[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i);
                        break;
                    case 2:
                        qualidade[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i);
                        preco[numeroLinha] = linha.substring(i + 1);
                }
                
                posicaoUltimaVirgula = i;
                ++numeroVirgulas;
            }
        }
    }
    
    private static void mostrarTodosProdutos() {
        String nomeFicheiro = "deposito.txt";
        int numLinhas = getNumeroLinhasFicheiro(nomeFicheiro);
        
        if (numLinhas == 0) {
            System.out.println(Color.RED + "❌ Nenhum produto cadastrado!" + Color.RESET);
            return;
        }
        
        String[] nomeProduto = new String[numLinhas];
        String[] quantidade = new String[numLinhas];
        String[] qualidade = new String[numLinhas];
        String[] preco = new String[numLinhas];
        leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, preco);
        
        System.out.println(Color.BRIGHT_CYAN + "\n╔════════════════════════════════════════╗" + Color.RESET);
        System.out.println(Color.BRIGHT_YELLOW + "║          LISTA DE PRODUTOS            ║" + Color.RESET);
        System.out.println(Color.BRIGHT_CYAN + "╠════════════════════════════════════════╣" + Color.RESET);
        
        for(int i = 0; i < nomeProduto.length; ++i) {
            System.out.println(Color.GREEN + "║ " + (i+1) + " - " + nomeProduto[i] + Color.RESET);
            System.out.println("║   Quantidade: " + Color.BRIGHT_BLUE + quantidade[i] + Color.RESET);
            System.out.println("║   Qualidade: " + Color.PURPLE + qualidade[i] + Color.RESET);
            System.out.println("║   Preço: R$ " + Color.BRIGHT_GREEN + preco[i] + Color.RESET);
            System.out.println(Color.BRIGHT_CYAN + "║────────────────────────────────────║" + Color.RESET);
        }
        System.out.println(Color.BRIGHT_CYAN + "╚════════════════════════════════════════╝\n" + Color.RESET);
    }
    
    private static void editarProduto() {
        Scanner sc = new Scanner(System.in);
        String nomeFicheiro = "deposito.txt";
        int numLinhas = getNumeroLinhasFicheiro(nomeFicheiro);
        
        if (numLinhas == 0) {
            System.out.println(Color.RED + "Não existem produtos para editar!" + Color.RESET);
            return;
        }
        
        String[] nomeProduto = new String[numLinhas];
        String[] quantidade = new String[numLinhas];
        String[] qualidade = new String[numLinhas];
        String[] precoF = new String[numLinhas];
        leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, precoF);
        
        System.out.println(Color.BRIGHT_CYAN + "\n╔════════════════════════════════════╗" + Color.RESET);
        System.out.println(Color.BRIGHT_YELLOW + "║       PRODUTOS DISPONÍVEIS         ║" + Color.RESET);
        System.out.println(Color.BRIGHT_CYAN + "╠════════════════════════════════════╣" + Color.RESET);
        
        for(int i = 0; i < nomeProduto.length; ++i) {
            System.out.printf(Color.GREEN + "║ %d - %-30s ║\n" + Color.RESET, i + 1, nomeProduto[i]);
            System.out.printf("║     Quantidade: %-20s ║\n", quantidade[i]);
            System.out.printf("║     Qualidade: %-21s ║\n", qualidade[i]);
            System.out.printf("║     Preço: R$%-23s ║\n", precoF[i]);
            System.out.println(Color.BRIGHT_CYAN + "║────────────────────────────────║" + Color.RESET);
        }
        
        System.out.println(Color.BRIGHT_CYAN + "╚════════════════════════════════════╝" + Color.RESET);
        System.out.print(Color.YELLOW + "Selecione o número do produto que deseja editar: " + Color.RESET);
        int indice = sc.nextInt();
        sc.nextLine();
        
        if (indice >= 1 && indice <= nomeProduto.length) {
            int indiceArray = indice - 1;
            System.out.println(Color.BRIGHT_CYAN + "\n╔════════════════════════════════════╗" + Color.RESET);
            System.out.println(Color.BRIGHT_YELLOW + "║        EDITANDO PRODUTO            ║" + Color.RESET);
            System.out.println(Color.BRIGHT_CYAN + "╠════════════════════════════════════╣" + Color.RESET);
            System.out.println("║ Dados atuais:                      ║");
            System.out.println("║ Nome: " + Color.GREEN + nomeProduto[indiceArray] + Color.RESET);
            System.out.println("║ Quantidade: " + Color.BLUE + quantidade[indiceArray] + Color.RESET);
            System.out.println("║ Qualidade: " + Color.PURPLE + qualidade[indiceArray] + Color.RESET);
            System.out.println("║ Preço: " + Color.BRIGHT_GREEN + precoF[indiceArray] + Color.RESET);
            System.out.println(Color.BRIGHT_CYAN + "╚════════════════════════════════════╝" + Color.RESET);
            
            System.out.println(Color.YELLOW + "\nO que deseja editar?" + Color.RESET);
            System.out.println("1 - Nome");
            System.out.println("2 - Quantidade");
            System.out.println("3 - Qualidade");
            System.out.println("4 - Preço");
            System.out.println("5 - Editar tudo");
            System.out.print(Color.YELLOW + "Opção: " + Color.RESET);
            
            int opcaoEdicao = sc.nextInt();
            sc.nextLine();
            
            switch (opcaoEdicao) {
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
                    precoF[indiceArray] = sc.nextLine();
                    break;
                case 5:
                    System.out.print("Novo nome: ");
                    nomeProduto[indiceArray] = sc.nextLine();
                    System.out.print("Nova quantidade: ");
                    quantidade[indiceArray] = sc.nextLine();
                    System.out.print("Nova qualidade: ");
                    qualidade[indiceArray] = sc.nextLine();
                    System.out.print("Novo preço: ");
                    precoF[indiceArray] = sc.nextLine();
                    break;
                default:
                    System.out.println(Color.RED + "Opção inválida!" + Color.RESET);
                    return;
            }
            
            String novoConteudo = "";
            for(int i = 0; i < nomeProduto.length; ++i) {
                novoConteudo = novoConteudo + nomeProduto[i] + "," + quantidade[i] + "," + qualidade[i] + "," + precoF[i];
                if (i < nomeProduto.length - 1) {
                    novoConteudo = novoConteudo + "\n";
                }
            }
            
            escreveNoFicheiro(novoConteudo, nomeFicheiro);
            System.out.println(Color.BRIGHT_GREEN + "\n✅ Produto editado com sucesso!" + Color.RESET);
        } else {
            System.out.println(Color.RED + "Índice inválido!" + Color.RESET);
        }
    }
    
    private static void adicionarMaisProdutos() {
        Scanner sc = new Scanner(System.in);
        System.out.print(Color.YELLOW + "Quantos produtos deseja adicionar? " + Color.RESET);
        int quantidade = sc.nextInt();
        sc.nextLine();
        
        for(int i = 0; i < quantidade; ++i) {
            System.out.println(Color.BRIGHT_CYAN + "\n--- Produto " + (i + 1) + " ---" + Color.RESET);
            System.out.print("Nome do produto: ");
            String nomeProduto = sc.nextLine();
            System.out.print("Quantidade: ");
            String quantidadeProd = sc.nextLine();
            System.out.print("Qualidade: ");
            String qualidade = sc.nextLine();
            System.out.print("Preço: ");
            String preco = sc.nextLine();
            int precoNum = Integer.parseInt(preco) * Integer.parseInt(quantidadeProd);
            String precoF = Integer.toString(precoNum);
            System.out.println(Color.GREEN + "Valor final do produto: " + precoNum + Color.RESET);
            String deposito = nomeProduto + "," + quantidadeProd + "," + qualidade + "," + precoF;
            adicionaTextoAoFicheiro(deposito + "\n", "deposito.txt");
        }
        
        System.out.println(Color.BRIGHT_GREEN + "\n✅ " + quantidade + " produto(s) adicionado(s) com sucesso!" + Color.RESET);
    }
    
    private static void adicionarNovoproduto() {
        Scanner sc = new Scanner(System.in);
        System.out.println(Color.BRIGHT_CYAN + "\n╔════════════════════════════════════╗" + Color.RESET);
        System.out.println(Color.BRIGHT_YELLOW + "║        ADICIONAR PRODUTO           ║" + Color.RESET);
        System.out.println(Color.BRIGHT_CYAN +   "╚════════════════════════════════════╝" + Color.RESET);
        
        System.out.print("Introduza o nome do produto: ");
        String nomeProduto = sc.nextLine();
        System.out.print("Introduza a quantidade: ");
        String quantidade = sc.nextLine();
        System.out.print("Introduza a Qualidade: ");
        String qualidade = sc.nextLine();
        System.out.print("Introduza o Preço unitário: ");
        String preco = sc.nextLine();
        int precoNum = Integer.parseInt(preco) * Integer.parseInt(quantidade);
        String precoF = Integer.toString(precoNum);
        System.out.println(Color.GREEN + "Valor final do produto: " + precoNum + Color.RESET);
        System.out.println(Color.BRIGHT_GREEN + "✅ Produto adicionado com sucesso!" + Color.RESET);
        String deposito = nomeProduto + "," + quantidade + "," + qualidade + "," + precoF + "\n";
        adicionaTextoAoFicheiro(deposito, "deposito.txt");
    }
    
    private static void buscarProdutoPorNome() {
        Scanner sc = new Scanner(System.in);
        String nomeFicheiro = "deposito.txt";
        int numLinhas = getNumeroLinhasFicheiro(nomeFicheiro);
        
        if (numLinhas == 0) {
            System.out.println(Color.BRIGHT_RED + "❌ Não existem produtos cadastrados!" + Color.RESET);
            return;
        }
        
        String[] nomeProduto = new String[numLinhas];
        String[] quantidade = new String[numLinhas];
        String[] qualidade = new String[numLinhas];
        String[] preco = new String[numLinhas];
        leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, preco);
        
        System.out.print(Color.YELLOW + "Introduza o nome do produto para buscar: " + Color.RESET);
        String nomeBusca = sc.nextLine().toLowerCase();
        boolean encontrado = false;
        int contador = 0;
        
        System.out.println(Color.BRIGHT_CYAN + "\n╔════════════════════════════════════════════════╗" + Color.RESET);
        System.out.println(Color.BRIGHT_YELLOW + "║           RESULTADOS DA BUSCA                  ║" + Color.RESET);
        System.out.println(Color.BRIGHT_CYAN + "╠════════════════════════════════════════════════╣" + Color.RESET);
        
        for(int i = 0; i < nomeProduto.length; ++i) {
            if (nomeProduto[i].toLowerCase().contains(nomeBusca)) {
                encontrado = true;
                ++contador;
                int qtd = Integer.parseInt(quantidade[i]);
                String status = qtd > 0 ? Color.GREEN + "✅ DISPONÍVEL" : Color.BRIGHT_RED + "❌ INDISPONÍVEL";
                System.out.println(Color.GREEN + "║ [" + contador + "] " + nomeProduto[i] + Color.RESET);
                System.out.println("║     Quantidade: " + Color.BRIGHT_BLUE + quantidade[i] + " unidades" + Color.RESET);
                System.out.println("║     Status: " + status + Color.RESET);
                System.out.println("║     Preço: R$ " + Color.BRIGHT_GREEN + preco[i] + Color.RESET);
                System.out.println("║     Qualidade: " + Color.PURPLE + qualidade[i] + Color.RESET);
                if (qtd > 0 && qtd < 5) {
                    System.out.println(Color.YELLOW + "║     ⚠️  ESTOQUE BAIXO! Restam apenas " + qtd + " unidades" + Color.RESET);
                }
                System.out.println(Color.BRIGHT_CYAN + "║────────────────────────────────────────────║" + Color.RESET);
            }
        }
        
        if (!encontrado) {
            System.out.println(Color.BRIGHT_RED + "║    ❌ Nenhum produto encontrado com o nome: " + nomeBusca + Color.RESET);
        } else {
            System.out.println(Color.BRIGHT_GREEN + "║    📊 Total de produtos encontrados: " + contador + Color.RESET);
        }
        System.out.println(Color.BRIGHT_CYAN + "╚════════════════════════════════════════════════╝\n" + Color.RESET);
    }
    
    private static void atualizarEstoque(String nomeProdutoVenda, int quantidadeVendida) {
        String nomeFicheiro = "deposito.txt";
        int numLinhas = getNumeroLinhasFicheiro(nomeFicheiro);
        
        if (numLinhas == 0) {
            System.out.println(Color.RED + "❌ Não existem produtos cadastrados!" + Color.RESET);
            return;
        }
        
        String[] nomeProduto = new String[numLinhas];
        String[] quantidade = new String[numLinhas];
        String[] qualidade = new String[numLinhas];
        String[] preco = new String[numLinhas];
        leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, preco);
        boolean encontrado = false;
        
        for(int i = 0; i < nomeProduto.length; ++i) {
            if (nomeProduto[i].equalsIgnoreCase(nomeProdutoVenda)) {
                encontrado = true;
                int qtdAtual = Integer.parseInt(quantidade[i]);
                if (qtdAtual >= quantidadeVendida) {
                    int novaQtd = qtdAtual - quantidadeVendida;
                    quantidade[i] = String.valueOf(novaQtd);
                    System.out.println(Color.BRIGHT_GREEN + "✅ Venda registrada! Novo estoque de " + nomeProduto[i] + ": " + novaQtd + " unidades" + Color.RESET);
                    
                    String novoConteudo = "";
                    for(int j = 0; j < nomeProduto.length; ++j) {
                        novoConteudo = novoConteudo + nomeProduto[j] + "," + quantidade[j] + "," + qualidade[j] + "," + preco[j];
                        if (j < nomeProduto.length - 1) {
                            novoConteudo = novoConteudo + "\n";
                        }
                    }
                    escreveNoFicheiro(novoConteudo, nomeFicheiro);
                } else {
                    System.out.println(Color.BRIGHT_RED + "❌ Estoque insuficiente! Disponível: " + qtdAtual + " unidades" + Color.RESET);
                }
                break;
            }
        }
        
        if (!encontrado) {
            System.out.println(Color.BRIGHT_RED + "❌ Produto não encontrado!" + Color.RESET);
        }
    }
    
    private static boolean produtos() {
        Scanner sc = new Scanner(System.in);
        System.out.println(Color.BRIGHT_CYAN + "\n╔════════════════════════════════════╗" + Color.RESET);
        System.out.println(Color.BRIGHT_YELLOW + "║           MENU DE PRODUTOS         ║" + Color.RESET);
        System.out.println(Color.BRIGHT_CYAN +   "╠════════════════════════════════════╣" + Color.RESET);
        System.out.println(Color.GREEN +         "║ 1 - Registrar produto              ║" + Color.RESET);
        System.out.println(Color.GREEN +         "║ 2 - Mostrar produtos               ║" + Color.RESET);
        System.out.println(Color.GREEN +         "║ 3 - Adicionar mais produtos        ║" + Color.RESET);
        System.out.println(Color.GREEN +         "║ 4 - Editar produto                 ║" + Color.RESET);
        System.out.println(Color.RED +           "║ 5 - Sair                           ║" + Color.RESET);
        System.out.println(Color.BRIGHT_CYAN +   "╚════════════════════════════════════╝" + Color.RESET);
        System.out.print(Color.YELLOW +         "Opção: " + Color.RESET);
        
        int opcaoProdutos = sc.nextInt();
        switch (opcaoProdutos) {
            case 1:
                adicionarNovoproduto();
                break;
            case 2:
                mostrarTodosProdutos();
                break;
            case 3:
                adicionarMaisProdutos();
                break;
            case 4:
                editarProduto();
                break;
            case 5:
                return true;
            default:
                System.out.println(Color.BRIGHT_RED + "Opção inválida!" + Color.RESET);
        }
        return false;
    }
    
    private static void adicionaTextoAoFicheiro(String deposito, String nomeFicheiro) {
        String textoAntigo = leFicheiro(nomeFicheiro);
        String textoNovo = textoAntigo + deposito;
        escreveNoFicheiro(textoNovo, nomeFicheiro);
    }
}