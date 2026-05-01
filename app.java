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

        Scanner scanner = new Scanner(System.in); 

        while(true){ 

            System.out.println("Selecione uma das opções:\n" 

                    + "1 - Inserir Aluno\n" 

                    + "2 - Consultar todos os alunos"); 

            int opcao = scanner.nextInt(); 

            switch(opcao){ 

                case 1 -> adicionarNovoAluno(); 

                case 2 -> mostrarTodosAlunos(); 

                default -> System.out.println("Opção inválida"); 

            } 

        } 

    } 

  

    private static void mostrarTodosAlunos() { 

        Scanner scanner = new Scanner(System.in); 

        String nomeFicheiro = "alunos.txt"; 

  

        String nome[] = new String[getNumeroLinhasFicheiro(nomeFicheiro)]; 

        String numero[] = new String[getNumeroLinhasFicheiro(nomeFicheiro)]; 

        String turma[] = new String[getNumeroLinhasFicheiro(nomeFicheiro)]; 

        String anoEscolar[] = new String[getNumeroLinhasFicheiro(nomeFicheiro)]; 

  

        leExtraiCsvFicheiro(nomeFicheiro, nome, anoEscolar, turma, numero); 

  

        for (int i = 0; i < nome.length; i++) { 

            System.out.println("Nome: " + nome[i]); 

            System.out.println("Número: " + numero[i]); 

            System.out.println("Turma: " + turma[i]); 

            System.out.println("Ano Escolar: " + anoEscolar[i] + "\n"); 

        } 

    } 

  

    private static void extraiDadosCsv(String[] nome, 

            String[] anoEscolar, 

            String[] turma, 

            String[] numero, 

            int numeroLinha, 

            String linha) { 

        int posicaoUltimaVirgula = -1; 

        int numeroVirgulas = 0; 

  

        for (int i = 0; i < linha.length(); i++) { 

            if (linha.charAt(i) == ',') { 

                System.out.println(linha.substring(posicaoUltimaVirgula + 1, i)); 

                switch (numeroVirgulas) { 

                    case 0 -> 

                        nome[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i); 

                    case 1 -> 

                        numero[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i); 

                    case 2 -> { 

                        turma[numeroLinha] = linha.substring(posicaoUltimaVirgula + 1, i); 

                        anoEscolar[numeroLinha] = linha.substring(i + 1); 

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

            String[] nome, 

            String[] anoEscolar, 

            String[] turma, 

            String[] numero) { 

  

        FileReader fileReader = null; 

        String textoLido = ""; 

        try { 

            fileReader = new FileReader(new File(nomeFicheiro)); 

            BufferedReader bufferedReader = new BufferedReader(fileReader); 

            String linha = ""; 

            int numeroLinha = 0; 

            while ((linha = bufferedReader.readLine()) != null) { 

                extraiDadosCsv(nome, anoEscolar, turma, numero, numeroLinha, linha); 

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

  

    private static void adicionaTextoAoFicheiro(String texto, String nomeFicheiro) { 

        String textoAntigo = leFicheiro(nomeFicheiro); 

        String textoNovo = textoAntigo + texto; 

        escreveNoFicheiro(textoNovo, nomeFicheiro); 

    } 

  

    private static void adicionarNovoAluno() { 

        Scanner scanner = new Scanner(System.in); 

  

        System.out.println("Introduza o nome"); 

        String nome = scanner.nextLine(); 

        System.out.println("Introduza o número"); 

        String numero = scanner.nextLine(); 

        System.out.println("Introduza a turma"); 

        String turma = scanner.nextLine(); 

        System.out.println("Introduza o ano escolar"); 

        String anoEscolar = scanner.nextLine(); 

  

        String texto = nome + (",") + numero + (",") + turma + (",") + anoEscolar; 

        adicionaTextoAoFicheiro(texto, "alunos.txt"); 

    } 

} 

 