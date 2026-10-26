package Cotiinformatica.services;

import Cotiinformatica.entities.Categoria;
import Cotiinformatica.entities.Produto;
import Cotiinformatica.enums.TipoProduto;
import Cotiinformatica.interfaces.ProdutoService;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProdutoServiceImpl implements ProdutoService {

    private final String arquivo = "produtos.txt";

    @Override
    public void cadastrar(Produto produto) {

        produto.setId(UUID.randomUUID());

        var scanner = new Scanner(System.in);

        System.out.println("\nCADASTRO DE PRODUTO:\n");

        System.out.print("INFORME O NOME........: ");
        produto.setNome(scanner.nextLine());

        System.out.print("INFORME O PREÇO.......: ");
        produto.setPreco(Double.parseDouble(scanner.nextLine()));

        System.out.print("INFORME O QUANTIDADE..: ");
        produto.setQuantidade(Integer.parseInt(scanner.nextLine()));

        System.out.println("TIPO DO PRODUTO:");
        System.out.println("\t(1) Perecivel");
        System.out.println("\t(2) Não perecivel");
        System.out.println("\t(3) Digital");

        System.out.print("INFORME O TIPO........: ");
        var tipo = scanner.nextLine();

        switch (Integer.parseInt(tipo)) {
            case 1:
                produto.setTipo(TipoProduto.PERECIVEL);
                break;
            case 2:
                produto.setTipo(TipoProduto.NAO_PERECIVEL);
                break;
            case 3:
                produto.setTipo(TipoProduto.DIGITAL);
                break;
        }

        produto.getCategoria().setId(UUID.randomUUID());

        System.out.print("NOME DA CATEGORIA.....: ");
        produto.getCategoria().setNome(scanner.nextLine());

        System.out.print("DESCRIÇÃO DA CATEGORIA: ");
        produto.getCategoria().setDescricao(scanner.nextLine());

        try (var printWriter = new PrintWriter(new FileWriter(arquivo, true))) {
            printWriter.write(produto.getId() + "\n");
            printWriter.write(produto.getNome() + "\n");
            printWriter.write(produto.getPreco() + "\n");
            printWriter.write(produto.getQuantidade() + "\n");
            printWriter.write(produto.getTipo() + "\n");
            printWriter.write(produto.getCategoria().getId() + "\n");
            printWriter.write(produto.getCategoria().getNome() + "\n");
            printWriter.write(produto.getCategoria().getDescricao() + "\n");
            printWriter.write("...");

            System.out.println("\nDADOS GRAVADOS COM SUCESSO!");
        } catch (Exception e) {
            System.out.println("\nFALHA AO GRAVAR ARQUIVO: " + e.getMessage());
        }
    }

    @Override
    public List<Produto> consultar() {

        System.out.println("\nCONSULTA DE PRODUTOS:\n");

        var produtos = new ArrayList<Produto>();

        try (var scanner = new Scanner(new File(arquivo))) {

            //Ler cada linha do arquivo
            while(scanner.hasNextLine()) {

                //Ler a linha do arquivo
                var linha = scanner.nextLine().trim();

                // Remove o separador, inclusive dos registros antigos
                // em que "..." ficou junto ao ID da próxima categoria.
                if (linha.startsWith("...")) {
                    linha = linha.substring(3).trim();
                }

                if (linha.isEmpty()) {
                    continue;
                }

                var produto = new Produto();
                produto.setCategoria(new Categoria());

                produto.setId(UUID.fromString(linha));
                produto.setNome(scanner.nextLine());
                produto.setPreco(Double.parseDouble(scanner.nextLine()));
                produto.setQuantidade(Integer.parseInt(scanner.nextLine()));
                produto.setTipo(TipoProduto.valueOf(scanner.nextLine()));
                produto.getCategoria().setId(UUID.fromString(scanner.nextLine()));
                produto.getCategoria().setNome(scanner.nextLine());
                produto.getCategoria().setDescricao(scanner.nextLine());

                produtos.add(produto);
            }
        }
        catch(Exception e) {
            System.out.println("\nFALHA AO LER ARQUIVO: " + e.getMessage());
        }

        return produtos;

    }
}


