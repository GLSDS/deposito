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
   public app() {
   }

   public static enum Color {
      RESET("\u001b[0m"),
      BLACK("\u001b[30m"),
      RED("\u001b[31m"),
      GREEN("\u001b[32m"),
      YELLOW("\u001b[33m"),
      BLUE("\u001b[34m"),
      PURPLE("\u001b[35m"),
      CYAN("\u001b[36m"),
      WHITE("\u001b[37m");

      private final String code;

      private Color(String code) {
         this.code = code;
      }

      public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);

         while(true) {
            System.out.println("╔════════════════════════════╗");
            System.out.println("║      SELECIONE UMA OPÇÃO   ║");
            System.out.println("╠════════════════════════════╣");
            System.out.println("║  1 - Produtos              ║");
            System.out.println("║  2 - Vendas                ║");
            System.out.println("║  3 - Sair                  ║");
            System.out.println("╚════════════════════════════╝");
            int opcao = sc.nextInt();
            switch (opcao) {
               case 1:
                  boolean sair = produtos();
                  if (sair) {
                     System.out.println("A sair...");
                     return;
                  }
                  break;
               case 3:
                  System.out.println("A sair...");
                  return;
               default:
                  System.out.println("Opção inválida");
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
               System.out.println(linha.substring(posicaoUltimaVirgula + 1, i));
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
         new Scanner(System.in);
         String nomeFicheiro = "deposito.txt";
         String[] nomeProduto = new String[getNumeroLinhasFicheiro(nomeFicheiro)];
         String[] quantidade = new String[getNumeroLinhasFicheiro(nomeFicheiro)];
         String[] qualidade = new String[getNumeroLinhasFicheiro(nomeFicheiro)];
         String[] preco = new String[getNumeroLinhasFicheiro(nomeFicheiro)];
         leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, preco);

         for(int i = 0; i < nomeProduto.length; ++i) {
            System.out.println("nome do produto: " + nomeProduto[i]);
            System.out.println("Quantidade: " + quantidade[i]);
            System.out.println("Qualidade: " + qualidade[i]);
            System.out.println("Preço: " + preco[i] + "\n");
         }

      }

      private static void editarProduto() {
         Scanner sc = new Scanner(System.in);
         String nomeFicheiro = "deposito.txt";
         int numLinhas = getNumeroLinhasFicheiro(nomeFicheiro);
         if (numLinhas == 0) {
            System.out.println("Não existem produtos para editar!");
         } else {
            String[] nomeProduto = new String[numLinhas];
            String[] quantidade = new String[numLinhas];
            String[] qualidade = new String[numLinhas];
            String[] precoF = new String[numLinhas];
            leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, precoF);
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       PRODUTOS DISPONÍVEIS         ║");
            System.out.println("╠════════════════════════════════════╣");

            for(int i = 0; i < nomeProduto.length; ++i) {
               System.out.printf("║ %d - %-30s ║\n", i + 1, nomeProduto[i]);
               System.out.printf("║     Quantidade: %-20s ║\n", quantidade[i]);
               System.out.printf("║     Qualidade: %-21s ║\n", qualidade[i]);
               System.out.printf("║     Preço: R$%-23s ║\n", precoF[i]);
               System.out.println("║────────────────────────────────║");
            }

            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Selecione o número do produto que deseja editar: ");
            int indice = sc.nextInt();
            sc.nextLine();
            if (indice >= 1 && indice <= nomeProduto.length) {
               int indiceArray = indice - 1;
               System.out.println("\n╔════════════════════════════════════╗");
               System.out.println("║        EDITANDO PRODUTO            ║");
               System.out.println("╠════════════════════════════════════╣");
               System.out.println("║ Dados atuais:                      ║");
               System.out.println("║ Nome: " + nomeProduto[indiceArray]);
               System.out.println("║ Quantidade: " + quantidade[indiceArray]);
               System.out.println("║ Qualidade: " + qualidade[indiceArray]);
               System.out.println("║ Preço: " + preco[indiceArray]);
               System.out.println("╚════════════════════════════════════╝");
               System.out.println("\nO que deseja editar?");
               System.out.println("1 - Nome");
               System.out.println("2 - Quantidade");
               System.out.println("3 - Qualidade");
               System.out.println("4 - Preço");
               System.out.println("5 - Editar tudo");
               System.out.print("Opção: ");
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
                     precoF[indiceArray] = sc.nextLine();
                     break;
                  default:
                     System.out.println("Opção inválida!");
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
               System.out.println("\n✅ Produto editado com sucesso!");
            } else {
               System.out.println("Índice inválido!");
            }
         }
      }

      private static void adicionarMaisProdutos() {
         Scanner sc = new Scanner(System.in);
         System.out.print("Quantos produtos deseja adicionar? ");
         int quantidade = sc.nextInt();
         sc.nextLine();

         for(int i = 0; i < quantidade; ++i) {
            System.out.println("\n--- Produto " + (i + 1) + " ---");
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
            System.out.println("Valor final do produto: " + precoNum);
            String deposito = nomeProduto + "," + quantidadeProd + "," + qualidade + "," + precoF;
            adicionaTextoAoFicheiro(deposito + "\n", "deposito.txt");
         }

         System.out.println(quantidade + " produto(s) adicionado(s) com sucesso!");
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
         int precoNum = Integer.parseInt(preco) * Integer.parseInt(quantidade);
         String precoF = Integer.toString(precoNum);
         System.out.println("Valor final do produto: " + precoNum);
         System.out.println("Produto adicionado com sucesso!");
         String deposito = nomeProduto + "," + quantidade + "," + qualidade + "," + precoF + "\n";
         adicionaTextoAoFicheiro(deposito, "deposito.txt");
      }

      private static void venderProduto() {
         new Scanner(System.in);
      }

      private static void buscarProdutoPorNome() {
         Scanner sc = new Scanner(System.in);
         String nomeFicheiro = "deposito.txt";
         int numLinhas = getNumeroLinhasFicheiro(nomeFicheiro);
         if (numLinhas == 0) {
            System.out.println("❌ Não existem produtos cadastrados!");
         } else {
            String[] nomeProduto = new String[numLinhas];
            String[] quantidade = new String[numLinhas];
            String[] qualidade = new String[numLinhas];
            String[] preco = new String[numLinhas];
            leExtraiCsvFicheiro(nomeFicheiro, nomeProduto, quantidade, qualidade, preco);
            System.out.println("Introduza o nome do produto para vender:\n");
            String nomeBusca = sc.nextLine().toLowerCase();
            boolean encontrado = false;
            int contador = 0;
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║           RESULTADOS DA BUSCA                  ║");
            System.out.println("╠════════════════════════════════════════════════╣");

            for(int i = 0; i < nomeProduto.length; ++i) {
               if (nomeProduto[i].toLowerCase().contains(nomeBusca)) {
                  encontrado = true;
                  ++contador;
                  int qtd = Integer.parseInt(quantidade[i]);
                  String status = qtd > 0 ? "✅ DISPONÍVEL" : "❌ INDISPONÍVEL";
                  System.out.println("║ [" + contador + "] " + nomeProduto[i]);
                  System.out.println("║     Quantidade: " + quantidade[i] + " unidades");
                  System.out.println("║     Status: " + status);
                  System.out.println("║     Preço: R$ " + preco[i]);
                  System.out.println("║     Qualidade: " + qualidade[i]);
                  if (qtd > 0 && qtd < 5) {
                     System.out.println("║     ⚠️  ESTOQUE BAIXO! Restam apenas " + qtd + " unidades");
                  }

                  System.out.println("║────────────────────────────────────────────║");
               }
            }

            if (!encontrado) {
               System.out.println("║    ❌ Nenhum produto encontrado com o nome: " + nomeBusca);
            } else {
               System.out.println("║    \ud83d\udcca Total de produtos encontrados: " + contador);
            }

            System.out.println("╚════════════════════════════════════════════════╝");
         }
      }

      private static void atualizarEstoque(String nomeProdutoVenda, int quantidadeVendida) {
         String nomeFicheiro = "deposito.txt";
         int numLinhas = getNumeroLinhasFicheiro(nomeFicheiro);
         if (numLinhas == 0) {
            System.out.println("❌ Não existem produtos cadastrados!");
         } else {
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
                     System.out.println("✅ Venda registrada! Novo estoque de " + nomeProduto[i] + ": " + novaQtd + " unidades");
                     String novoConteudo = "";

                     for(int j = 0; j < nomeProduto.length; ++j) {
                        novoConteudo = novoConteudo + nomeProduto[j] + "," + quantidade[j] + "," + qualidade[j] + "," + preco[j];
                        if (j < nomeProduto.length - 1) {
                           novoConteudo = novoConteudo + "\n";
                        }
                     }

                     escreveNoFicheiro(novoConteudo, nomeFicheiro);
                  } else {
                     System.out.println("❌ Estoque insuficiente! Disponível: " + qtdAtual + " unidades");
                  }
                  break;
               }
            }

            if (!encontrado) {
               System.out.println("❌ Produto não encontrado!");
            }

         }
      }

      private static boolean produtos() {
         Scanner sc = new Scanner(System.in);
         System.out.println("╔════════════════════════════╗ \n║      SELECIONE UMA OPÇÃO   ║ \n╠════════════════════════════╣ \n║ 1 - registra produto       ║ \n║ 2 - adicionar produtos     ║ \n║ 3 - Vender produto         ║ \n║ 4 - Mostrar produtos       ║ \n║ 5 - Adicionar mais produtos║ \n║ 6 - Editar produto         ║ \n║ 7 - Sair                   ║ \n╚════════════════════════════╝ ");
         int opcaoProdutos = sc.nextInt();
         switch (opcaoProdutos) {
            case 1:
               adicionarNovoproduto();
               break;
            case 2:
            default:
               System.out.println("Opção inválida");
               break;
            case 3:
               mostrarTodosProdutos();
               break;
            case 4:
               adicionarMaisProdutos();
               break;
            case 5:
               editarProduto();
               break;
            case 6:
               return true;
         }

         return false;
      }

      private static void adicionaTextoAoFicheiro(String deposito, String nomeFicheiro) {
         String textoAntigo = leFicheiro(nomeFicheiro);
         String textoNovo = textoAntigo + deposito;
         escreveNoFicheiro(textoNovo, nomeFicheiro);
      }
   }
}