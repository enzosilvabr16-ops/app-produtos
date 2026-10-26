package Cotiinformatica;

import Cotiinformatica.entities.Categoria;
import Cotiinformatica.entities.Produto;
import Cotiinformatica.services.CategoriaServiceImpl;
import Cotiinformatica.services.ProdutoServiceImpl;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static void main() {

        var scanner = new Scanner(System.in);

        System.out.println("MENU DE OPÇÕES:\n");

        System.out.println("\t(1) CADASTRAR CATEGORIA");
        System.out.println("\t(2) CONSULTAR CATEGORIAS");
        System.out.println("\t(3) CADASTRAR PRODUTO");
        System.out.println("\t(4) CONSULTAR PRODUTOS");

        System.out.print("\nINFORME A OPÇÃO DESEJADA: ");
        var opcao = Integer.parseInt(scanner.nextLine());

        var categoriaService = new CategoriaServiceImpl();
        var produtoService = new ProdutoServiceImpl();

        switch (opcao) {
            case 1:
                categoriaService.cadastrar(new Categoria());
                break;
            case 2:
                var categorias = categoriaService.consultar();
                for(var c : categorias) {
                    System.out.println("...");
                    System.out.println("ID........: " + c.getId());
                    System.out.println("NOME......: " + c.getNome());
                    System.out.println("DESCRIÇÃO.: " + c.getDescricao());
                }
                break;
            case 3:
                var produto = new Produto();
                produto.setCategoria(new Categoria());

                produtoService.cadastrar(produto);
                break;
            case 4:
                var produtos = produtoService.consultar();
                for(var p : produtos) {
                    System.out.println("...");
                    System.out.println("ID........: " + p.getId());
                    System.out.println("NOME......: " + p.getNome());
                    System.out.println("PREÇO.....: " + p.getPreco());
                    System.out.println("QTD.......: " + p.getQuantidade());
                    System.out.println("TIPO......: " + p.getTipo());
                    System.out.println("CATEGORIA.: " + p.getCategoria().getNome());
                    System.out.println("DESCRIÇÃO.: " + p.getCategoria().getDescricao());
                }
                break;
        }
    }
}
