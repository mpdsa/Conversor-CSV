package com.trybe.conversorcsv;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** Classe conversor. */
public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou
   *                     gravar os arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados
   * na pasta de saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas Pasta em que serão colocados os arquivos gerados no formato
   *                      requerido pelo subsistema.
   *
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou
   *                     gravar os arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    final String tituloArquivo = "Nome completo,Data de nascimento,Email,CPF";
    
    for (int i = 0; i < pastaDeEntradas.list().length; i++) {
      String entradaDiretorio = pastaDeEntradas.getPath()  + "/" + pastaDeEntradas.list()[i];
      String saidaDiretorio = pastaDeSaidas.getPath() + "/"  + pastaDeEntradas.list()[i];
      
      File lerArquivoCsv = new File(entradaDiretorio);
      FileReader lerArquivo = null;
      BufferedReader buffArquivo = null;
      FileWriter novoArquivo = null;
      BufferedWriter buffEscreverArquivo = null;
      
      try {
        if (lerArquivoCsv.canRead() && lerArquivoCsv.isFile()) {
          lerArquivo = new FileReader(lerArquivoCsv);
          buffArquivo = new BufferedReader(lerArquivo);
        }
        
        File criaArquivo = new File(saidaDiretorio);
        if (!criaArquivo.exists()) {
          criaArquivo.createNewFile();
        }
        
        novoArquivo = new FileWriter(criaArquivo);
        buffEscreverArquivo = new BufferedWriter(novoArquivo);          

        buffEscreverArquivo.write(tituloArquivo + '\n');
        buffEscreverArquivo.flush();
        
        String lendoArquivo = buffArquivo.readLine();
        while (lendoArquivo != null) {
          if (!lendoArquivo.equals(tituloArquivo)) {
            buffEscreverArquivo.write(formataLinha(lendoArquivo));
          }
          
          lendoArquivo = buffArquivo.readLine();
        }
        
      } catch (IOException e) {
        e.printStackTrace();
      
      } finally {
        buffEscreverArquivo.close();
        buffArquivo.close();
        
      }
    }
    
    
  }
  
  /** Formata Linha. */
  public String formataLinha(String texto) {
    if (texto.isEmpty()) {
      return "";
    }

    String[] linhaParticionada = texto.split(",");
    String[] dataFormatada = linhaParticionada[1].split("/");
    String cpf = linhaParticionada[3].substring(0, 3) + '.'
                        + linhaParticionada[3].substring(3, 6) + '.'
                        + linhaParticionada[3].substring(6, 9) + '-'
                        + linhaParticionada[3].substring(9, 11);
        
    String nome = linhaParticionada[0].toUpperCase();
    String data = dataFormatada[2] + '-' + dataFormatada[1] + '-' + dataFormatada[0];
    String email = linhaParticionada[2];
    
    return (nome + ',' + data + ',' + email + ',' + cpf + '\n');
  }
  
}